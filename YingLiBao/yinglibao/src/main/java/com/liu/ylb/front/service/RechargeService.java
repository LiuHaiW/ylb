package com.liu.ylb.front.service;

import com.liu.ylb.db.entity.Recharge;

import java.util.List;

public interface RechargeService {
    List<Recharge> queryAllByUid(Integer uid, Integer pageNo, Integer pageSize);
}
