package com.liu.ylb.front.vo;

import com.liu.ylb.db.model.UserBid;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;

@Data
public class UserBidVo {
    private Integer id;
    private String productName;
    private BigDecimal bidMoney;
    private String bidTime;
    private Integer status;
    public UserBidVo(UserBid bid){
        if( bid != null){
            this.id = bid.getId();
            this.productName = bid.getProductName();
            this.bidMoney = bid.getBidMoney();
            this.status = bid.getStatus();
            if(bid.getBidTime() != null){
                this.bidTime = DateFormatUtils.format(bid.getBidTime(),"yyyy-MM-dd");
            }
        }
    }
}
