package com.liu.ylb.db.mapper;

import com.liu.ylb.db.entity.IncomeRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.ylb.db.model.UserIncomeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author liu
* @description 针对表【b_income_record(收益记录表)】的数据库操作Mapper
* @createDate 2022-08-13 09:47:19
* @Entity com.liu.ylb.db.entity.Income
*/
public interface IncomeRecordMapper extends BaseMapper<IncomeRecord> {
    List<UserIncomeRecord> queryIncomeRecordsByUid(@Param("uid") Integer uid, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
}




