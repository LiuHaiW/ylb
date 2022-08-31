package com.liu.ylb.front.controller;

import com.liu.ylb.common.consts.AppConsts;
import com.liu.ylb.common.consts.RedisKey;
import com.liu.ylb.db.entity.User;
import com.liu.ylb.db.model.UserAccount;
import com.liu.ylb.front.dto.RealNameDto;
import com.liu.ylb.front.enums.RCode;
import com.liu.ylb.front.service.UserService;
import com.liu.ylb.front.service.impl.SmsService;
import com.liu.ylb.front.vo.CommonResult;
import com.liu.ylb.front.vo.UserAccountVo;
import com.liu.ylb.front.vo.request.RealNameVo;
import com.liu.ylb.front.vo.request.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Api(tags = "用户接口功能")
@RestController
public class UserController extends BaseController{
    @Resource
    private SmsService smsService;
    @Resource
    private UserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
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
       return commonResult;
    }
    @ApiOperation(value = "用户登录",notes = "手机号是唯一的")
    @PostMapping("/user/login")
    public CommonResult userLogin(@RequestBody UserVo userVo){
        CommonResult result = CommonResult.FAIL();
        if(userVo.checkParam()){
            boolean check = smsService.checkSmsCodeValid(AppConsts.LOGIN_ACTION,userVo.getPhone(),userVo.getCode());
            if(check){
                User user = userService.selectByPhone(userVo.getPhone(),userVo.getSecret());
                if(user != null){
                    String token = UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
                    String tokenKey = RedisKey.TOKEN_LOGIN + token;
                    BoundHashOperations<String, Object, Object> stringObjectObjectBoundHashOperations = stringRedisTemplate.boundHashOps(tokenKey);
                    Map<String,String> map = new HashMap<>();
                    map.put("id",String.valueOf(user.getId()));
                    map.put("name",user.getName());
                    map.put("phone",user.getPhone());
                    map.put("loginTime",String.valueOf(new Date().getTime()));
                    stringObjectObjectBoundHashOperations.putAll(map);
                    stringRedisTemplate.expire(tokenKey,1, TimeUnit.HOURS);

                    Map<String,Object> objectMap = new HashMap<>();
                    objectMap.put("id",user.getId());
                    objectMap.put("name",user.getName());
                    objectMap.put("phone",user.getPhone());
                    objectMap.put("token",token);
                    result = CommonResult.SUCC(objectMap);

                }else{
                    result.setRCode(RCode.USER_LOGIN_FAIL);
                }
            }else{
                result.setRCode(RCode.SMS_CODE_ERR);
            }
        }else{
            result.setRCode(RCode.REQUEST_PARAM_ERR);
        }
        System.out.println(result);
        return result;
    }
    @ApiOperation(value = "退出登录")
    @GetMapping("/user/loginUp")
    public CommonResult loginUp(@RequestHeader("Authorization") String auth){
        CommonResult commonResult = CommonResult.FAIL();
        if(StringUtils.isNotBlank(auth) && StringUtils.startsWith(auth,"Bearer")){
            String accessToken = auth.substring(7);
            if(StringUtils.isNotBlank(accessToken)){
                String key = RedisKey.TOKEN_LOGIN + accessToken.toUpperCase(Locale.ROOT);
                if(stringRedisTemplate.hasKey(key)){
                    String SmsKey = RedisKey.SMS_CODE_LOGIN + stringRedisTemplate.boundHashOps(key).get("phone");
                    stringRedisTemplate.delete(SmsKey);
                    boolean del = stringRedisTemplate.delete(key);
                    if(del){
                        commonResult = CommonResult.SUCC();
                    }
                }
            }
        }
        return commonResult;
    }

    @ApiOperation(value = "实名认证")
    @PostMapping("/user/realName")
    public CommonResult realName(@RequestBody RealNameVo realNameVo,
                                 @RequestHeader Integer uid){
        CommonResult commonResult = CommonResult.FAIL();
        if(realNameVo.checkRealName()){
            RealNameDto realNameDto = new RealNameDto();
            realNameDto.setName(realNameVo.getName());
            realNameDto.setPhone(realNameVo.getPhone());
            realNameDto.setCardId(realNameVo.getCardId());
            realNameDto.setId(uid);
            int res = userService.realName(realNameDto);
            switch (res){
                case 1:
                    commonResult = CommonResult.SUCC();
                    break;
                case 2:
                    commonResult.setRCode(RCode.REALNAME_DOUBLE);
                    break;
                case 3:
                    commonResult.setRCode(RCode.REALNAME_PHONE_ERR);
                    break;
                case 0:
                    commonResult.setRCode(RCode.USER_NOT_EXISTS);
                    break;
                case 4:
                    commonResult.setRCode(RCode.REALNAME_API_FAIL);
                    break;
            }
        }else {
            commonResult.setRCode(RCode.REQUEST_PARAM_ERR);
        }
        return commonResult;
    }
    @ApiOperation(value = "查询用户信息")
    @GetMapping("/user/info")
    public CommonResult selectUserInfo(@RequestHeader Integer uid){
        CommonResult commonResult = CommonResult.FAIL();
        if(checkUid(uid)){
            UserAccount userAccount = userService.selectUserAccount(uid);
            if(userAccount != null){
                UserAccountVo userAccountVo = new UserAccountVo(userAccount);
                commonResult = CommonResult.SUCC(userAccountVo);
            }else {
                commonResult.setRCode(RCode.USER_NOT_EXISTS);
            }
        }else {
            commonResult.setRCode(RCode.REQUEST_PARAM_ERR);
        }
        return commonResult;
    }

}
