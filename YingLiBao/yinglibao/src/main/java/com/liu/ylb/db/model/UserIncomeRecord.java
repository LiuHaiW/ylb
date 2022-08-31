package com.liu.ylb.db.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserIncomeRecord {
    private Integer id;
    private String productName;
    private BigDecimal incomeMoney;
    private Date incomeDate;
    private Integer incomeStatus;
}
