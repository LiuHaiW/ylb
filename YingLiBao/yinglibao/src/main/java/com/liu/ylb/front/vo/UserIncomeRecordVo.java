package com.liu.ylb.front.vo;

import com.liu.ylb.db.model.UserIncomeRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class UserIncomeRecordVo {
    private Integer id;
    private String productName;
    private BigDecimal incomeMoney;
    private String incomeDate;
    private Integer incomeStatus;

    public UserIncomeRecordVo(UserIncomeRecord userIncomeRecord) {
        this.id = userIncomeRecord.getId();
        this.productName = userIncomeRecord.getProductName();
        this.incomeMoney = userIncomeRecord.getIncomeMoney();
        this.incomeStatus = userIncomeRecord.getIncomeStatus();
        if(userIncomeRecord.getIncomeDate() != null){
            this.incomeDate = new SimpleDateFormat("yyyy-MM-dd").format(userIncomeRecord.getIncomeDate());
        }
    }
}
