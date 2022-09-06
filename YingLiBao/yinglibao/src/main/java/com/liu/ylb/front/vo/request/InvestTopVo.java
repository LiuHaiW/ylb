package com.liu.ylb.front.vo.request;

import com.liu.ylb.common.util.AppUtil;
import com.liu.ylb.front.dto.InvestTopDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvestTopVo {
    private String phone;
    private double bidSumMoney;

    public InvestTopVo(InvestTopDto investTopDto) {
        if(investTopDto != null){
            this.bidSumMoney = investTopDto.getBidSumMoney();
            if(AppUtil.checkPhone(investTopDto.getPhone())){
                this.phone = investTopDto.getPhone().substring(0,3)+"******"+ investTopDto.getPhone().substring(9,11);
            }
        }
    }
}
