package com.liu.ylb.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liu.ylb.db.entity.Recharge;
import com.liu.ylb.db.mapper.RechargeMapper;
import com.liu.ylb.front.service.RechargeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RechargeServiceImpl implements RechargeService {
    @Resource
    private RechargeMapper rechargeMapper;

    @Override
    public List<Recharge> queryAllByUid(Integer uid, Integer pageNo, Integer pageSize) {
        Integer offset = (pageNo - 1) * pageSize;
        QueryWrapper<Recharge> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",uid).orderByDesc("recharge_time").last("limit "+offset+","+pageSize);
        return rechargeMapper.selectList(wrapper);
    }
}
