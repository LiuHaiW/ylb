package com.liu.ylb.front.service;

import com.liu.ylb.db.model.UserIncomeRecord;

import java.util.List;

public interface IncomeRecordService {
    List<UserIncomeRecord> queryIncomeRecordsByUid(Integer uid, Integer pageNo, Integer pageSize);
    void generateIncomePlan();
    void generateIncomeBack();
}
