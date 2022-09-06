package com.liu.ylb.front.service;

import com.liu.ylb.db.entity.User;
import com.liu.ylb.db.model.UserAccount;
import com.liu.ylb.front.dto.RealNameDto;

public interface UserService {
    int phoneRegister(String phone,String secret);

    User selectByPhone(String phone, String secret);

    int realName(RealNameDto realNameDto);

    UserAccount selectUserAccount(Integer uid);

    User selectById(Integer uid);
}
