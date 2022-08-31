package com.liu.ylb.front.vo;

import com.liu.ylb.common.util.AppUtil;
import com.liu.ylb.db.model.UserBid;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;

@Data
public class ProductBidVo {
    private Integer id;
    private String phone;
    private BigDecimal bidMoney;
    private String bidTime;

    public ProductBidVo(UserBid userBid){
        if(userBid != null){
            this.id = userBid.getId();
            this.bidMoney = userBid.getBidMoney();
            if(AppUtil.checkPhone(userBid.getPhone())){
                this.phone = userBid.getPhone().substring(0,3)+"******"+userBid.getPhone().substring(9,11);
            }
            if(userBid.getBidTime() != null){
                this.bidTime = DateFormatUtils.format(userBid.getBidTime(),"yyyy-MM-dd HH:mm:ss");
            }
        }
    }
}
