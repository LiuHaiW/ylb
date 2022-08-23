package com.liu.ylb.front.controller;

import com.liu.ylb.common.consts.AppConsts;
import com.liu.ylb.common.consts.RedisKey;
import com.liu.ylb.common.util.AppPhoneUtil;
import com.liu.ylb.front.config.JdwxSmsConfig;
import com.liu.ylb.front.enums.RCode;
import com.liu.ylb.front.service.impl.SmsService;
import com.liu.ylb.front.vo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "短信业务")
@RestController
public class SmsController {
    @Resource
    private JdwxSmsConfig jdwxSmsConfig;
    @Resource
    private SmsService service;
    @ApiOperation(value = "发送短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cmd",value = "动作标识，REG注册，LOGIN登录",required = true),
            @ApiImplicitParam(name = "phone",value = "手机号",required = true)
    })
    @GetMapping("/sms/code")
    public CommonResult sendSms(String cmd,String phone){
        CommonResult commonResult = new CommonResult(RCode.SMS_SEND_FAIL);
        if(AppPhoneUtil.checkPhone(phone)){
            if(AppConsts.REG_ACTION.equalsIgnoreCase(cmd)){
                String key = RedisKey.SMS_CODE_REG + phone;
                if(service.hasKey(key)){
                    commonResult.setRCode(RCode.SMS_CODE_EXISTS);
                }else{
                    boolean res = service.handleSms(cmd,phone);
                    if(res){
                        commonResult = CommonResult.SUCC();
                    }
                }
            }else if(AppConsts.LOGIN_ACTION.equalsIgnoreCase(cmd)){

            }else {
                commonResult.setRCode(RCode.ACTION_CMD_ERR);
            }
        }else {
            commonResult.setRCode(RCode.PHONE_FORMAT_ERR);
        }
        System.out.println(commonResult);
        return commonResult;
    }
}
