package com.liu.ylb.front.controller;

import com.liu.ylb.db.entity.IncomeRecord;
import com.liu.ylb.db.model.UserIncomeRecord;
import com.liu.ylb.front.enums.RCode;
import com.liu.ylb.front.service.IncomeRecordService;
import com.liu.ylb.front.vo.CommonResult;
import com.liu.ylb.front.vo.UserIncomeRecordVo;
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

@Api(tags = "收益业务")
@RestController
public class IncomeRecordController extends BaseController{
    @Resource
    private IncomeRecordService incomeRecordService;
    @ApiOperation(value = "查询用户收益记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",value = "用户id"),
            @ApiImplicitParam(name = "pageNo",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "页大小"),
    })
    @GetMapping("/incomeRecord/more")
    public CommonResult queryIncomeRecordsByUid(@RequestHeader Integer uid, @RequestParam Integer pageNo, @RequestParam Integer pageSize){
        CommonResult commonResult = CommonResult.FAIL();
        if(checkUid(uid)) {
            List<UserIncomeRecord> incomeRecordList = incomeRecordService.queryIncomeRecordsByUid(uid,pageNo,pageSize);
            List<UserIncomeRecordVo> userIncomeRecordVoList = new ArrayList<>();
            incomeRecordList.forEach(userIncomeRecord -> userIncomeRecordVoList.add(new UserIncomeRecordVo(userIncomeRecord)));
            commonResult = CommonResult.SUCC(userIncomeRecordVoList);
        }else {
            commonResult.setRCode(RCode.REQUEST_PARAM_ERR);
        }
        return commonResult;
    }
}
