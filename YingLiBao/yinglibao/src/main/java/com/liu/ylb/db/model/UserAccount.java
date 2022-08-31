package com.liu.ylb.db.model;

import com.liu.ylb.db.entity.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserAccount extends User {
    private BigDecimal availableMoney;
}
