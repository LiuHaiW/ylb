package com.liu.ylb.front.service;

import com.liu.ylb.db.entity.Product;
import com.liu.ylb.front.dto.AppIndexProductsDto;
import com.liu.ylb.front.dto.ProductDetailDto;

import java.util.List;

public interface ProductService {
    List<Product>  selectProductByPage(Integer type,Integer pageNum,Integer papeSize);
    long ProductCountByType(Integer type);
    AppIndexProductsDto getInfoProducts();
    ProductDetailDto selectDetail(Integer pid,Integer uid);
}
