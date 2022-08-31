package com.liu.ylb.front.vo;

import com.liu.ylb.db.model.UserAccount;
import lombok.Data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

@Data
public class UserAccountVo {
    private Integer id;
    private String name;
    private String phone;
    private String headerImg;
    private String loginTime;
    private BigDecimal avaiableMoney;

    public UserAccountVo(UserAccount userAccount) {
        if(userAccount != null) {
            this.id = userAccount.getId();
            this.name = userAccount.getName();
            this.phone = userAccount.getPhone();
            this.headerImg = userAccount.getHeaderImage();
            this.avaiableMoney= userAccount.getAvailableMoney();
            if(userAccount.getLastLoginTime() != null){
                this.loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(userAccount.getLastLoginTime());
            }
        }
    }
}
