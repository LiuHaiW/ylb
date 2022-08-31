package com.liu.ylb.db.mapper;

import com.liu.ylb.db.entity.Bid;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.ylb.db.model.UserBid;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author liu
* @description 针对表【b_bid_info(投资记录表)】的数据库操作Mapper
* @createDate 2022-08-13 09:44:49
* @Entity com.liu.ylb.db.entity.Bid
*/
public interface BidMapper extends BaseMapper<Bid> {
     List<UserBid> queryBidsByUid(@Param("uid") Integer uid, @Param("offset") Integer offset,@Param("pageSize") Integer pageSize) ;
     List<UserBid> queryBidsByPid(@Param("pid") Integer pid,@Param("offset") Integer offset,@Param("pageSize") Integer pageSize);
}




