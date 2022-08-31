package com.liu.ylb.db.mapper;

import com.liu.ylb.db.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.ylb.db.model.UserAccount;
import org.apache.ibatis.annotations.Param;

/**
* @author liu
* @description 针对表【u_user(用户表)】的数据库操作Mapper
* @createDate 2022-08-13 09:49:10
* @Entity com.liu.ylb.db.entity.User
*/
public interface UserMapper extends BaseMapper<User> {
   UserAccount selectUserAccount(@Param("uid") Integer uid);
}




