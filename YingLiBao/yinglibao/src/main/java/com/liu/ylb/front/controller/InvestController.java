package com.liu.ylb.front.controller;

import com.liu.ylb.db.model.UserBid;
import com.liu.ylb.front.dto.InvestTopDto;
import com.liu.ylb.front.enums.RCode;
import com.liu.ylb.front.service.BidService;
import com.liu.ylb.front.vo.CommonResult;
import com.liu.ylb.front.vo.UserBidVo;
import com.liu.ylb.front.vo.request.InvestTopVo;
import com.liu.ylb.front.vo.request.InvestVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "投资模块")
@RestController
public class InvestController extends BaseController {
    @Resource
    private BidService bidService;
    @ApiOperation(value = "某个用户投资记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",value = "用户id"),
            @ApiImplicitParam(name = "pageNo",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "页大小"),
    })
    @GetMapping("/invests/more")
    public CommonResult queryBidsByUid(@RequestHeader Integer uid, @RequestParam Integer pageNo,@RequestParam Integer pageSize){
        CommonResult commonResult = CommonResult.FAIL();
        if (checkUid(uid)) {
            pageNo = defPageNum(pageNo);
            pageSize = defPageSize(pageSize);
            List<UserBid> userBidList = bidService.queryBidsByUid(uid,pageNo,pageSize);
            List<UserBidVo> userBidVoList = new ArrayList<>();
            userBidList.forEach(userBid -> userBidVoList.add(new UserBidVo(userBid)));
            commonResult = CommonResult.SUCC(userBidVoList);
        }else {
            commonResult.setRCode(RCode.REQUEST_PARAM_ERR);
        }
        return commonResult;
    }
    @ApiOperation(value = "投资理财产品")
    @PostMapping("/invest/product")
    public CommonResult invest(@RequestBody InvestVo investVo,@RequestHeader Integer uid){
        CommonResult commonResult = CommonResult.FAIL();
        if(checkUid(uid) && investVo.checkData()){
            String result = bidService.invest(uid,investVo.getPid(),investVo.getBidMoney());
            RCode rCode = RCode.valueOf(result);
            if(rCode.getCode() == 1016){
                commonResult = CommonResult.SUCC();
            }else {
                commonResult.setRCode(rCode);
            }
        }else{
            commonResult.setRCode(RCode.REQUEST_PARAM_ERR);
        }
        return commonResult;
    }
    @ApiOperation(value = "投资排行榜")
    @PostMapping("/invest/top")
    public CommonResult investTop(){
        CommonResult commonResult = CommonResult.FAIL();
        List<InvestTopDto> investTopDtos = bidService.investTop();
        List<InvestTopVo> investTopVoList = new ArrayList<>();
        if(investTopDtos.size() > 0){
            investTopDtos.forEach(investTopDto -> investTopVoList.add(new InvestTopVo(investTopDto)));
        }else{
            investTopVoList.add(new InvestTopVo("136******90",987800D));
            investTopVoList.add(new InvestTopVo("138******22",80000D));
            investTopVoList.add(new InvestTopVo("139******32",5000D));
        }
        commonResult = CommonResult.SUCC(investTopVoList);
        return commonResult;
    }
}
