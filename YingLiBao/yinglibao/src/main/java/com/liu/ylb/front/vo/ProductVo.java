package com.liu.ylb.front.vo;

import com.liu.ylb.db.entity.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductVo {
    private Integer id;
    private String productName;
    private BigDecimal rate;
    private Integer cycle;
    private Date releaseTime;
    private Integer productType;
    private String productNo;
    private BigDecimal productMoney;
    private BigDecimal leftProductMoney;
    private BigDecimal bidMinLimit;
    private BigDecimal bidMaxLimit;

    public ProductVo(Product product){
        if(product != null){
            this.id = product.getId();
            this.productName = product.getProductName();
            this.rate = product.getRate();
            this.cycle = product.getCycle();
            this.productType = product.getProductType();
            this.productNo = product.getProductNo();
            this.productMoney = product.getProductMoney();
            this.leftProductMoney = product.getLeftProductMoney();
            this.bidMinLimit = product.getBidMinLimit();
            this.bidMaxLimit = product.getBidMaxLimit();
        }
    }
}
