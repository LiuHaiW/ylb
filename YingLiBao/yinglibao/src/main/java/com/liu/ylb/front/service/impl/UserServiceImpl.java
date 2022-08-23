package com.liu.ylb.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liu.ylb.db.entity.FinanceAccount;
import com.liu.ylb.db.entity.User;
import com.liu.ylb.db.mapper.FinanceAccountMapper;
import com.liu.ylb.db.mapper.UserMapper;
import com.liu.ylb.front.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private FinanceAccountMapper financeAccountMapper;
    @Value("${secret.salt}")
    private String secretSalt;
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
}
