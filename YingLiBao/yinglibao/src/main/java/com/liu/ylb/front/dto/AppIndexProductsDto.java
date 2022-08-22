package com.liu.ylb.front.dto;

import com.liu.ylb.db.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppIndexProductsDto {
    private List<Product> xinList;
    private List<Product> youList;
    private List<Product> sanList;
}
