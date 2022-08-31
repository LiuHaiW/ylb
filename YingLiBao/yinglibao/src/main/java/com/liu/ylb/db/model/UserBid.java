package com.liu.ylb.db.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserBid {
    private Integer id;
    private String productName;
    private String phone;
    private BigDecimal bidMoney;
    private Date bidTime;
    private Integer status;
}
