package com.liu.ylb.front.service.impl;

import com.liu.ylb.db.mapper.IncomeRecordMapper;
import com.liu.ylb.db.model.UserIncomeRecord;
import com.liu.ylb.front.service.IncomeRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IncomeRecordServiceImpl implements IncomeRecordService {
    @Resource
    private IncomeRecordMapper incomeRecordMapper;

    @Override
    public List<UserIncomeRecord> queryIncomeRecordsByUid(Integer uid, Integer pageNo, Integer pageSize) {
        Integer offset = (pageNo - 1) * pageSize;
        return incomeRecordMapper.queryIncomeRecordsByUid(uid,offset,pageSize);
    }
}
