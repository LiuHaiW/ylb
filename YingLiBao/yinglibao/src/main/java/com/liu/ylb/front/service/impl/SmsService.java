package com.liu.ylb.front.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.liu.ylb.common.consts.AppConsts;
import com.liu.ylb.common.consts.RedisKey;
import com.liu.ylb.common.util.HttpClientUtils;
import com.liu.ylb.front.config.JdwxSmsConfig;
import com.liu.ylb.front.enums.RCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SmsService {
    @Resource
    private JdwxSmsConfig jdwxSmsConfig;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    public boolean handleSms(String cmd,String phone){
        String code ="", key="" , content = "";
        boolean isOk = false,res = false;
        ValueOperations<String,String> vo = stringRedisTemplate.opsForValue();
        if(AppConsts.REG_ACTION.equalsIgnoreCase(cmd)){
            code = RandomStringUtils.randomNumeric(4);
            content = String.format(jdwxSmsConfig.getRegText(),code);
            isOk = sendSms(phone,content);
            if (isOk){
                key = RedisKey.SMS_CODE_REG + phone;
                vo.set(key,code,3, TimeUnit.MINUTES);
                if(StringUtils.isNotBlank(vo.get(key))){
                    res = true;
                }
            }
        }else if( AppConsts.LOGIN_ACTION.equals(cmd)){

        }
        return res;
    }
    private boolean sendSms(String phone,String content){
        boolean res = false;
        try {
            Map<String,String> map = new HashMap<>();
            map.put("mobile",phone);
            map.put("content",content);
            map.put("appkey",jdwxSmsConfig.getAppkey());
            String responseText = /*HttpClientUtils.doGet(jdwxSmsConfig.getUrl(),map)*/
                    "{\n" +
                            "    \"code\": \"10000\",\n" +
                            "    \"charge\": false,\n" +
                            "    \"remain\": 1305,\n" +
                            "    \"msg\": \"查询成功\",\n" +
                            "    \"result\": {\n" +
                            "        \"ReturnStatus\": \"Success\",\n" +
                            "        \"Message\": \"ok\",\n" +
                            "        \"RemainPoint\": 420842,\n" +
                            "        \"TaskID\": 18424321,\n" +
                            "        \"SuccessCounts\": 1\n" +
                            "    }\n" +
                            "}";

            if(StringUtils.isNotBlank(responseText)){
                JSONObject one = JSONObject.parseObject(responseText);
                if("10000".equalsIgnoreCase(one.getString("code"))){
                    if("success".equalsIgnoreCase(one.getJSONObject("result").getString("ReturnStatus"))){
                        res = true;
                    }
                }
            }
        } catch (Exception e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }
    public boolean hasKey(String key){
        return stringRedisTemplate.hasKey(key);
    }
}
