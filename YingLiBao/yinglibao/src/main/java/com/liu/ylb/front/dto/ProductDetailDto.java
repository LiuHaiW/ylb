package com.liu.ylb.front.dto;

import com.liu.ylb.db.entity.Product;
import com.liu.ylb.db.model.UserBid;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDetailDto {
    private Product product;
    private List<UserBid> userBidList;
    private BigDecimal countMoney;
}
