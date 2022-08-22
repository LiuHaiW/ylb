package com.liu.ylb.front.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppInfoDto {
    private Long registerUserCount;
    private BigDecimal allBidMoney;
    private BigDecimal averageRate;
}
