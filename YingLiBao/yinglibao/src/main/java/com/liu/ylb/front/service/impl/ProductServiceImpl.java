package com.liu.ylb.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liu.ylb.common.consts.AppConsts;
import com.liu.ylb.common.consts.RedisKey;
import com.liu.ylb.db.entity.FinanceAccount;
import com.liu.ylb.db.entity.Product;
import com.liu.ylb.db.mapper.BidMapper;
import com.liu.ylb.db.mapper.FinanceAccountMapper;
import com.liu.ylb.db.mapper.ProductMapper;
import com.liu.ylb.db.model.UserBid;
import com.liu.ylb.front.dto.AppIndexProductsDto;
import com.liu.ylb.front.dto.ProductDetailDto;
import com.liu.ylb.front.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Resource
    private BidMapper bidMapper;
    @Resource
    private FinanceAccountMapper financeAccountMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public List<Product> selectProductByPage(Integer type, Integer pageNum, Integer papeSize) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        Integer offset = (pageNum - 1) * papeSize;
        queryWrapper.eq("product_type",type).orderByDesc("release_time").orderByDesc("rate").last("limit "+ offset + ","+papeSize);
        return productMapper.selectList(queryWrapper);
    }

    @Override
    public long ProductCountByType(Integer type) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_type",type);
        long count = productMapper.selectCount(queryWrapper);
        return count;
    }

    @Override
    public AppIndexProductsDto getInfoProducts() {
        BoundValueOperations<String,String> vo = stringRedisTemplate.boundValueOps(RedisKey.APPINFO_INDEX_PRODUCTS);
        AppIndexProductsDto appIndexProductsDto = null;
        try {
            String json = vo.get();
            if(StringUtils.isNotBlank(json)){
                appIndexProductsDto = new ObjectMapper().readValue(json,AppIndexProductsDto.class);
            }else {
                List<Product> xinList = selectProductByPage(AppConsts.TYPE_XINSHOUBAO,1,1);
                List<Product> youList = selectProductByPage(AppConsts.TYPE_YOUXUAN,1,3);
                List<Product> sanList = selectProductByPage(AppConsts.TYPE_SANBAIO,1,3);
                appIndexProductsDto = new AppIndexProductsDto(xinList,youList,sanList);
                vo.set(new ObjectMapper().writeValueAsString(appIndexProductsDto),4, TimeUnit.HOURS);
            }
        } catch (JsonProcessingException e) {
            appIndexProductsDto = null;
            e.printStackTrace();
        }
        return appIndexProductsDto;
    }

    @Override
    public ProductDetailDto selectDetail(Integer pid, Integer uid) {
        Product product = productMapper.selectById(pid);
        List<UserBid> userBidList = new ArrayList<>();
        BigDecimal countMoney = new BigDecimal(-1);
        if(product != null){
            userBidList = bidMapper.queryBidsByPid(pid,0,5);
            if( uid != null && uid > 0){
                QueryWrapper<FinanceAccount> qw = new QueryWrapper<>();
                qw.eq("uid",uid);
                FinanceAccount financeAccount = financeAccountMapper.selectOne(qw);
                if(financeAccount != null){
                    countMoney = financeAccount.getAvailableMoney();
                }
            }
        }
        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setProduct(product);
        productDetailDto.setUserBidList(userBidList);
        productDetailDto.setCountMoney(countMoney);
        return productDetailDto;
    }
}
