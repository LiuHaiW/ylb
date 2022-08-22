package com.liu.ylb.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liu.ylb.common.consts.RedisKey;
import com.liu.ylb.db.entity.Bid;
import com.liu.ylb.db.entity.Product;
import com.liu.ylb.db.mapper.BidMapper;
import com.liu.ylb.db.mapper.ProductMapper;
import com.liu.ylb.db.mapper.UserMapper;
import com.liu.ylb.front.dto.AppInfoDto;
import com.liu.ylb.front.service.ApplicationService;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private UserMapper userMapper;
    private ProductMapper productMapper;
    private BidMapper bidMapper;

    public ApplicationServiceImpl(UserMapper userMapper, ProductMapper productMapper,BidMapper bidMapper) {
        this.userMapper = userMapper;
        this.productMapper = productMapper;
        this.bidMapper = bidMapper;
    }

    @Override
    public AppInfoDto getAppInfo() {
        AppInfoDto appInfoDto = null;
        BoundValueOperations<String,String> vo = stringRedisTemplate.boundValueOps(RedisKey.APPINFO_INDEX);
        try {
            String json = vo.get();
            if(StringUtils.isNotBlank(json)){
                appInfoDto = new ObjectMapper().readValue(json,AppInfoDto.class);
            }else{
                synchronized (this){
                    appInfoDto = getAppInfoDataFromDB();
                    json = new ObjectMapper().writeValueAsString(appInfoDto);
                    vo.set(json,4, TimeUnit.HOURS);
                }
            }
        } catch (JsonProcessingException e) {
            appInfoDto = null;
            e.printStackTrace();
        }
        return appInfoDto;
    }
    private AppInfoDto getAppInfoDataFromDB(){
        Long registerUsers = userMapper.selectCount(null);
        BigDecimal averageRate = productMapper.selectOne(new QueryWrapper<Product>().select("round(avg(rate),2) as rate")).getRate();
        BigDecimal allBidMoney = bidMapper.selectOne(new QueryWrapper<Bid>().select("sum(bid_money) as bidMoney")).getBidMoney();
        return new AppInfoDto(registerUsers,allBidMoney,averageRate);
    }
}
