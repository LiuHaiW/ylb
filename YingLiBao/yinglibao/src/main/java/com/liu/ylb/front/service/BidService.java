package com.liu.ylb.front.service;

import com.liu.ylb.db.model.UserBid;
import com.liu.ylb.front.dto.InvestTopDto;

import java.math.BigDecimal;
import java.util.List;

public interface BidService {
    List<UserBid> queryBidsByUid(Integer uid, Integer pageNo, Integer pageSize);

    String invest(Integer uid, Integer pid, BigDecimal bidMoney);

    List<InvestTopDto> investTop();
}
