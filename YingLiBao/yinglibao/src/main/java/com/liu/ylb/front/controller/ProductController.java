package com.liu.ylb.front.controller;

import com.liu.ylb.db.entity.Product;
import com.liu.ylb.front.dto.AppIndexProductsDto;
import com.liu.ylb.front.enums.RCode;
import com.liu.ylb.front.service.ProductService;
import com.liu.ylb.front.vo.CommonResult;
import com.liu.ylb.front.vo.PageInfo;
import com.liu.ylb.front.vo.ProductVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "理财产品服务")
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController{
    @Autowired
    private ProductService productService;
    @ApiOperation(value = "首页理财产品列表" , notes = "1个新手宝，3个优选，3个散标")
    @ApiResponses({
            @ApiResponse(code = 200,message = "查询统计数据成功",
                    response = CommonResult.class),
            @ApiResponse(code = 400,message = "请求参数不正确"),
            @ApiResponse(code = 404,message = "请求地址不正确")
    })
    @GetMapping("/index")
    public CommonResult showIndexProductList(){
        AppIndexProductsDto appIndexProductsDto = productService.getInfoProducts();
        List<ProductVo> xinVoList = new ArrayList<>();
        appIndexProductsDto.getXinList().forEach(product -> {xinVoList.add(new ProductVo(product));});
        List<ProductVo> youVoList = new ArrayList<>();
        appIndexProductsDto.getYouList().forEach(product -> {youVoList.add(new ProductVo(product));});
        List<ProductVo> sanVoList = new ArrayList<>();
        appIndexProductsDto.getSanList().forEach(product -> {sanVoList.add(new ProductVo(product));});

        CommonResult result = CommonResult.SUCC();
        Map<String,Object> productVoMap = new HashMap<>();
        productVoMap.put("xinVoList",xinVoList);
        productVoMap.put("youVoList",youVoList);
        productVoMap.put("sanVoList",sanVoList);
        result.setInfo(productVoMap);
        return result;
    }
    @ApiOperation(value = "按产品类别分页显示产品信息")
    @GetMapping("/more")
    public CommonResult selectByTypeAndPage(@RequestParam Integer type, @RequestParam  Integer pageNum,@RequestParam Integer pageSize){
        CommonResult result = CommonResult.FAIL();
        if(checkProductType(type)){
            List<ProductVo> productVoList = new ArrayList<>();
            PageInfo pageInfo = new PageInfo();
            long count = productService.ProductCountByType(type);
            if(count > 0 ){
                pageNum = defPageNum(pageNum);
                pageSize = defPageSize(pageSize);
                List<Product> productList = productService.selectProductByPage(type,pageNum,pageSize);
                productList.forEach(product -> {productVoList.add(new ProductVo(product));});
                pageInfo = new PageInfo(pageNum,pageSize,count);
                Map<String,Object> data = new HashMap<>();
                data.put("productList",productVoList);
                data.put("pageInfo",pageInfo);
                result = CommonResult.SUCC(data);
            }
        }else {
            result.setRCode(RCode.PRODUCT_TYPE_NOT_RANGE);
        }
        return result;
    }
}
