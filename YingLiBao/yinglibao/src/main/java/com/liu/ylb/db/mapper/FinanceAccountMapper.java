package com.liu.ylb.db.mapper;

import com.liu.ylb.db.entity.FinanceAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
* @author liu
* @description 针对表【u_finance_account(用户财务资金账户表)】的数据库操作Mapper
* @createDate 2022-08-13 09:48:55
* @Entity com.liu.ylb.db.entity.FinanceAccount
*/
public interface FinanceAccountMapper extends BaseMapper<FinanceAccount> {
    FinanceAccount selectByUidForUpdate(@Param("uid") Integer uid);
    int reduceAccountMoney(@Param("uid") Integer uid, @Param("bidMoney") BigDecimal bidMoney);
    int updateByIncome(@Param("uid") Integer uid, @Param("bidMoney") BigDecimal bidMoney, @Param("incomeMoney") BigDecimal incomeMoney);

    int updateMoneyByRecharge(@Param("uid") Integer uid, @Param("rechargeMoney") BigDecimal rechargeMoney);
}




