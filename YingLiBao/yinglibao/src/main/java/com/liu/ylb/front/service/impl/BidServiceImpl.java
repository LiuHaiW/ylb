package com.liu.ylb.front.service.impl;

import com.liu.ylb.common.consts.RedisKey;
import com.liu.ylb.common.util.AppUtil;
import com.liu.ylb.db.entity.Bid;
import com.liu.ylb.db.entity.FinanceAccount;
import com.liu.ylb.db.entity.Product;
import com.liu.ylb.db.mapper.BidMapper;
import com.liu.ylb.db.mapper.FinanceAccountMapper;
import com.liu.ylb.db.mapper.ProductMapper;
import com.liu.ylb.db.mapper.UserMapper;
import com.liu.ylb.db.model.UserBid;
import com.liu.ylb.front.dto.InvestTopDto;
import com.liu.ylb.front.enums.RCode;
import com.liu.ylb.front.service.BidService;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class BidServiceImpl implements BidService {
    @Resource
    private BidMapper bidMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private FinanceAccountMapper financeAccountMapper;
    @Resource
    private UserMapper userMapper;
    public List<UserBid> queryBidsByUid(Integer uid, Integer pageNo, Integer pageSize) {
        Integer offset = (pageNo - 1) * pageSize;
        return bidMapper.queryBidsByUid(uid,offset,pageSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized String invest(Integer uid, Integer pid, BigDecimal bidMoney) {
        RCode rCode = RCode.INVEST_DEFAULT;
        int rows = 0;
        FinanceAccount financeAccount = financeAccountMapper.selectByUidForUpdate(uid);
        if(financeAccount != null){
            if(AppUtil.ge(financeAccount.getAvailableMoney(),bidMoney)){
                Product product = productMapper.selectByIdForUpdate(pid);
                if(product != null){
                    if(product.getProductStatus() == 0){
                        if(AppUtil.ge(product.getLeftProductMoney(),bidMoney)&&
                        AppUtil.ge(bidMoney,product.getBidMinLimit())&&
                        AppUtil.ge(product.getBidMaxLimit(),bidMoney)){
                            rows = financeAccountMapper.reduceAccountMoney(uid,bidMoney);
                            if(rows < 1){
                                throw  new RuntimeException("?????????????????????????????????");
                            }
                            rows = productMapper.reduceLeftProductMoney(pid,bidMoney);
                            if(rows < 1){
                                throw new RuntimeException("??????????????????????????????????????????????????????");
                            }
                            Bid bid = new Bid();
                            bid.setBidMoney(bidMoney);
                            bid.setBidStatus(1);
                            bid.setBidTime(new Date());
                            bid.setUid(uid);
                            bid.setProdId(pid);
                            bidMapper.insert(bid);

                            Product product1 = productMapper.selectById(pid);
                            if(product1.getLeftProductMoney().compareTo(new BigDecimal("0")) == 0){
                                product1.setProductStatus(1);
                                product1.setProductFullTime(new Date());
                                rows = productMapper.updateById(product1);
                                if(rows < 1){
                                    throw new RuntimeException("????????????????????????????????????1?????????");
                                }
                            }
                            rCode = RCode.INVEST_SUCCESS;
                            BoundZSetOperations<String, String> zSetOperations = stringRedisTemplate.boundZSetOps(RedisKey.INVEST_TOP);
                            String phone = userMapper.selectById(uid).getPhone();
                            zSetOperations.incrementScore(phone,bidMoney.doubleValue());
                        }else{
                            rCode = RCode.INVEST_MONEY_ERR;
                        }
                    }else{
                        rCode = RCode.INVEST_PRODUCT_NOT_SELL;
                    }
                }else{
                    rCode = RCode.INVEST_RPODUCT_NOT_EXITS;
                }
            }else{
                rCode = RCode.INVEST_ACCOUNT_MONEY_NOT_ENOUGH;
            }
        }else{
            rCode = RCode.INVEST_ACCOUNT_NOT_EXITS;
        }
        return rCode.toString();
    }

    @Override
    public List<InvestTopDto> investTop() {
        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringRedisTemplate.boundZSetOps(RedisKey.INVEST_TOP).reverseRangeWithScores(0, 2);
        List<InvestTopDto> investTopDtos = new ArrayList<>();
        typedTuples.forEach(stringTypedTuple -> investTopDtos.add(new InvestTopDto(stringTypedTuple.getValue(),stringTypedTuple.getScore())));
        return investTopDtos;
    }
}
