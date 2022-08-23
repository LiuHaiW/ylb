package com.liu.ylb.front.controller;

import com.liu.ylb.common.consts.AppConsts;
import com.liu.ylb.front.enums.RCode;
import com.liu.ylb.front.service.UserService;
import com.liu.ylb.front.service.impl.SmsService;
import com.liu.ylb.front.vo.CommonResult;
import com.liu.ylb.front.vo.request.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "用户接口功能")
@RestController
public class UserController {
    @Resource
    private SmsService smsService;
    @Resource
    private UserService userService;
    @ApiOperation(value = "手机号注册用户",notes = "手机号是唯一的")
    @PostMapping("/user/register")
    public CommonResult phoneRegister(@RequestBody UserVo userVo){
       CommonResult commonResult = CommonResult.FAIL();
        if(userVo.checkParam()){
            boolean check = smsService.checkSmsCodeValid(AppConsts.REG_ACTION,userVo.getPhone(),userVo.getCode());
            if(check){
                int count = userService.phoneRegister(userVo.getPhone(),userVo.getSecret());
                if(count ==0){
                    commonResult.setRCode(RCode.PHONE_EXITS);
                }else if(count == 1){
                    commonResult = CommonResult.SUCC();
                }
            }else{
                commonResult.setRCode(RCode.SMS_CODE_ERR);
            }
        }else{
            commonResult.setRCode(RCode.REQUEST_PARAM_ERR);
        }
        System.out.println(commonResult);
       return commonResult;
    }
}
