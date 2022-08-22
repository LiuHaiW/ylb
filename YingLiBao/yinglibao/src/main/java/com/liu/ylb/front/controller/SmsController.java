package com.liu.ylb.front.controller;

import com.liu.ylb.front.config.JdwxSmsConfig;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "短信业务")
@RestController
public class SmsController {
    @Resource
    private JdwxSmsConfig jdwxSmsConfig;
    @GetMapping("/sms/test")
    public String test(){
        String code = RandomStringUtils.randomNumeric(4);
        String format = String.format(jdwxSmsConfig.getRegText(),code);
        return format + "code = " + code;
    }
}
