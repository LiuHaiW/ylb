package com.liu.ylb.front.vo;

import com.liu.ylb.front.dto.AppInfoDto;
import lombok.Data;

@Data
public class AppInfoVo {
    private String registerUsers;
    private String allBidMoney;
    private String averageRate;

    public AppInfoVo trans(AppInfoDto dto){
        this.setAverageRate(dto.getAverageRate().stripTrailingZeros().toPlainString());
        this.setAllBidMoney(dto.getAllBidMoney().stripTrailingZeros().toPlainString());
        this.setRegisterUsers(dto.getRegisterUserCount().toString());
        return this;
    }
}
