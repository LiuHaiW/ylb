package com.liu.ylb.front.vo;

import com.liu.ylb.db.entity.Recharge;
import lombok.Data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class RechargeVo {
    private Integer id;
    private String rechargeStatus;
    private BigDecimal rechargeMoney;
    private String rechargeTime;

    public RechargeVo(Recharge recharge) {
        if(recharge != null){
            this.id = recharge.getId();
            this.rechargeMoney = recharge.getRechargeMoney();
            if(recharge.getRechargeTime() != null){
                this.rechargeTime = new SimpleDateFormat("yyyy-MM-dd").format(recharge.getRechargeTime());
            }
            switch (recharge.getRechargeStatus()){
                case 0:
                    this.rechargeStatus = "充值中";
                    break;
                case 1:
                    this.rechargeStatus = "成功";
                    break;
                case 2:
                    this.rechargeStatus = "失败";
                    break;
            }
        }
    }
}
