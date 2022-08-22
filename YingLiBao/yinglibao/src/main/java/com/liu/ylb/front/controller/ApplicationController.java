package com.liu.ylb.front.controller;

import com.liu.ylb.front.dto.AppInfoDto;
import com.liu.ylb.front.service.ApplicationService;
import com.liu.ylb.front.vo.AppInfoVo;
import com.liu.ylb.front.vo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Api(tags = "应用程序信息服务")
@RestController
public class ApplicationController {
    @Resource
    private ApplicationService applicationService;
    @ApiOperation(value = "统计三项数据",notes = "统计注册人数，累计成交金额，利率平均值")
    @ApiResponses({
            @ApiResponse(code = 200,message = "查询统计数据成功",
                    response = CommonResult.class),
            @ApiResponse(code = 400,message = "请求参数不正确"),
            @ApiResponse(code = 404,message = "请求地址不正确")
    })
    @GetMapping("/application/info")
    public CommonResult getAppInfo(){
        AppInfoDto appInfoDto = applicationService.getAppInfo();
        if( appInfoDto == null ) {
            appInfoDto = new AppInfoDto(89082L,
                    new BigDecimal("865790"),
                    new BigDecimal("5.2"));
        }
        AppInfoVo appInfoVo = new AppInfoVo().trans(appInfoDto);
        return CommonResult.SUCC(appInfoVo);
    }
}
