package com.liu.ylb.front.vo.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvestVo {
    private Integer pid;
    private BigDecimal bidMoney;
    public boolean checkData(){
        boolean check = true;
        if(this.pid == null || pid < 1){
            check = false;
        }
        if(this.bidMoney == null || this.bidMoney.floatValue() < 100 || this.bidMoney.floatValue() % 100 != 0){
            check = false;
        }
        return check;
    }
}
