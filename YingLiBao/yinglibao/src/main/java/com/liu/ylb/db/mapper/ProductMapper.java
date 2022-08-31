package com.liu.ylb.db.mapper;

import com.liu.ylb.db.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
* @author liu
* @description 针对表【b_product_info(产品信息表)】的数据库操作Mapper
* @createDate 2022-08-13 09:48:19
* @Entity com.liu.ylb.db.entity.Product
*/
public interface ProductMapper extends BaseMapper<Product> {
    Product selectByIdForUpdate(@Param("pid") Integer pid);
    int reduceLeftProductMoney(@Param("pid") Integer pid, @Param("bidMoney") BigDecimal bidMoney);
}




