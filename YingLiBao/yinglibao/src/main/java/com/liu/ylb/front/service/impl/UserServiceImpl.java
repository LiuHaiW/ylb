package com.liu.ylb.front.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liu.ylb.db.entity.FinanceAccount;
import com.liu.ylb.db.entity.User;
import com.liu.ylb.db.mapper.FinanceAccountMapper;
import com.liu.ylb.db.mapper.UserMapper;
import com.liu.ylb.db.model.UserAccount;
import com.liu.ylb.front.config.JdwxRealNameConfig;
import com.liu.ylb.front.dto.RealNameDto;
import com.liu.ylb.front.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private FinanceAccountMapper financeAccountMapper;
    @Value("${secret.salt}")
    private String secretSalt;
    @Resource
    private JdwxRealNameConfig realNameConfig;
    @Override
    public int phoneRegister(String phone, String secret) {
        int result = 0;
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("phone",phone);
        User user = userMapper.selectOne(qw);
        if(user == null){
            String newSecret = DigestUtils.md5Hex(secret + secretSalt);
            user = new User();
            user.setPhone(phone);
            user.setLoginPassword(newSecret);
            user.setAddTime(new Date());
            userMapper.insert(user);

            FinanceAccount financeAccount = new FinanceAccount();
            financeAccount.setUid(user.getId());
            financeAccount.setAvailableMoney(new BigDecimal("0"));
            financeAccountMapper.insert(financeAccount);

            result = 1;
        }
        return result;
    }

    @Override
    public User selectByPhone(String phone, String secret) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        queryWrapper.eq("login_password",DigestUtils.md5Hex(secret + secretSalt));
        User user = userMapper.selectOne(queryWrapper);
        if(user != null){
            user.setLastLoginTime(new Date());
            userMapper.updateById(user);
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized int realName(RealNameDto realNameDto) {
        int res = 0;
        User user = userMapper.selectById(realNameDto.getId());
        if(user != null){
            if(StringUtils.isNotBlank(user.getName())){
                res = 2;
                return res;
            }
            if(!realNameDto.getPhone().equals(user.getPhone())){
                res = 3;
                return res;
            }
            Map<String,String> map = new HashMap<>();
            map.put("cardNo", realNameDto.getCardId());
            map.put("realName", realNameDto.getName());
            map.put("appkey",realNameConfig.getAppkey());
            boolean thirdCheck = false;
            try {
                //String json = HttpClientUtils.doGet(realNameConfig.getUrl(), map);
                String json="{\n" +
                        "    \"code\": \"10000\",\n" +
                        "    \"charge\": false,\n" +
                        "    \"remain\": 1305,\n" +
                        "    \"msg\": \"查询成功\",\n" +
                        "    \"result\": {\n" +
                        "        \"error_code\": 0,\n" +
                        "        \"reason\": \"成功\",\n" +
                        "        \"result\": {\n" +
                        "            \"realname\": \""+realNameDto.getName()+"\",\n" +
                        "            \"idcard\": \"350721197702134399\",\n" +
                        "            \"isok\": true\n" +
                        "        }\n" +
                        "    }\n" +
                        "}";
                if (StringUtils.isNotBlank(json)) {
                    JSONObject jsonObject = JSONObject.parseObject(json);
                    if("10000".equals(jsonObject.getString("code"))){
                        thirdCheck = jsonObject.getJSONObject("result").getJSONObject("result").getBooleanValue("isok");
                    }else{
                        res = 4;
                    }
                }
            } catch (Exception e) {
                thirdCheck = false;
                e.printStackTrace();
            }
            if(thirdCheck){
                User user1 = new User();
                user1.setName(realNameDto.getName());
                user1.setId(realNameDto.getId());
                user1.setIdCard(realNameDto.getCardId());
                int num = userMapper.updateById(user1);
                if(num < 1){
                    throw new RuntimeException("实名认证，更新user表失败");
                }
                res = 1;
            }
        }
        return res;
    }

    @Override
    public UserAccount selectUserAccount(Integer uid) {
        return userMapper.selectUserAccount(uid);
    }
}
