package com.liu.ylb.front.controller;

import com.liu.ylb.db.entity.Recharge;
import com.liu.ylb.front.enums.RCode;
import com.liu.ylb.front.service.RechargeService;
import com.liu.ylb.front.vo.CommonResult;
import com.liu.ylb.front.vo.RechargeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "充值业务")
@RestController
public class RechargeController extends BaseController{
    @Resource
    private RechargeService rechargeService;
    @ApiOperation(value = "查询用户充值记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",value = "用户id"),
            @ApiImplicitParam(name = "pageNo",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "页大小"),
    })
    @GetMapping("/recharge/more")
    public CommonResult queryAllByUid(@RequestHeader("uid") Integer uid,
                                      @RequestParam Integer pageNo, @RequestParam Integer pageSize){
        CommonResult commonResult =  CommonResult.FAIL();
        if (checkUid(uid)) {
            pageNo = defPageNum(pageNo);
            pageSize = defPageSize(pageSize);
            List<Recharge> rechargeList = rechargeService.queryAllByUid(uid,pageNo,pageSize);
            List<RechargeVo> rechargeVoList = new ArrayList<>();
            rechargeList.forEach(recharge -> rechargeVoList.add(new RechargeVo(recharge)) );
            commonResult = CommonResult.SUCC(rechargeVoList);
        }else{
            commonResult.setRCode(RCode.REQUEST_PARAM_ERR);
        }
        return commonResult;
    }
}
