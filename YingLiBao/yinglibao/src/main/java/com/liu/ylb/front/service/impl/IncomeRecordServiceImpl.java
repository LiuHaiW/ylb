package com.liu.ylb.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liu.ylb.common.consts.AppConsts;
import com.liu.ylb.db.entity.Bid;
import com.liu.ylb.db.entity.IncomeRecord;
import com.liu.ylb.db.entity.Product;
import com.liu.ylb.db.mapper.BidMapper;
import com.liu.ylb.db.mapper.FinanceAccountMapper;
import com.liu.ylb.db.mapper.IncomeRecordMapper;
import com.liu.ylb.db.mapper.ProductMapper;
import com.liu.ylb.db.model.UserIncomeRecord;
import com.liu.ylb.front.service.IncomeRecordService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class IncomeRecordServiceImpl implements IncomeRecordService {
    @Resource
    private IncomeRecordMapper incomeRecordMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private BidMapper bidMapper;
    @Resource
    private FinanceAccountMapper accountMapper;

    @Override
    public List<UserIncomeRecord> queryIncomeRecordsByUid(Integer uid, Integer pageNo, Integer pageSize) {
        Integer offset = (pageNo - 1) * pageSize;
        return incomeRecordMapper.queryIncomeRecordsByUid(uid,offset,pageSize);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void generateIncomePlan() {
        Date date = new Date();
        Date begin =  DateUtils.truncate(DateUtils.addDays(date,-1), Calendar.DATE);
        Date end = DateUtils.truncate(date, Calendar.DATE);
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.ge("product_full_time",begin).le("product_full_time",end).
                eq("product_status",1).orderByAsc("id");
        List<Product> productList = productMapper.selectList(wrapper);
        int rows = 0;
        BigDecimal dateRate = new BigDecimal("0");
        BigDecimal incomeMoney  = new BigDecimal("0");
        Date incomeDate  = new Date();
        for (Product product : productList) {
            dateRate = product.getRate().divide(new BigDecimal(360*30),20, RoundingMode.HALF_UP);
            List<Bid> bidList = bidMapper.selectList(new QueryWrapper<Bid>().eq("prod_id",product.getId()).orderByAsc("id"));
            for (Bid bid : bidList) {
                if(product.getProductType() == AppConsts.TYPE_XINSHOUBAO){
                    incomeMoney = bid.getBidMoney().multiply(dateRate).multiply(new BigDecimal(product.getCycle()));
                    incomeDate = DateUtils.addDays(product.getProductFullTime(),1+product.getCycle());
                }else {
                    incomeMoney = bid.getBidMoney().multiply(dateRate).multiply(new BigDecimal(product.getCycle() * 30));
                    incomeDate = DateUtils.addDays(product.getProductFullTime(),1+product.getCycle() * 30);
                }
                IncomeRecord incomeRecord = new IncomeRecord();
                incomeRecord.setUid(bid.getUid());
                incomeRecord.setProdId(bid.getProdId());
                incomeRecord.setBidMoney(bid.getBidMoney());
                incomeRecord.setBidId(bid.getId());
                incomeRecord.setIncomeMoney(incomeMoney);
                incomeRecord.setIncomeDate(incomeDate);
                incomeRecord.setIncomeStatus(AppConsts.INCOME_STATUS_PLAN);
                incomeRecordMapper.insert(incomeRecord);
            }
            product.setProductStatus(AppConsts.PRODUCT_STATUS_PLAN);
            rows = productMapper.updateById(product);
            if(rows < 1) {
                throw new RuntimeException("生成收益计划，修改理财产品状态为2失败");
            }
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void generateIncomeBack() {
        List<IncomeRecord> incomeRecords = new ArrayList<>();
        Date date = DateUtils.truncate(DateUtils.addDays(new Date(),-1),Calendar.DATE);
        QueryWrapper<IncomeRecord> qw = new QueryWrapper<>();
        qw.eq("income_status",AppConsts.INCOME_STATUS_PLAN).eq("income_date",date).orderByAsc("id");
        incomeRecords = incomeRecordMapper.selectList(qw);
        int rows = 0;
        for (IncomeRecord incomeRecord : incomeRecords) {
            rows = accountMapper.updateByIncome(incomeRecord.getUid(),incomeRecord.getBidMoney(),incomeRecord.getIncomeMoney());
            if(rows<1){
                throw new RuntimeException("收益返还，更新账号资金失败");
            }
            incomeRecord.setIncomeStatus(AppConsts.INCOME_STATUS_BACK);
            rows = incomeRecordMapper.updateById(incomeRecord);
            if(rows<1){
                throw new RuntimeException("收益返还，更新收益状态失败");
            }
        }
    }

}
