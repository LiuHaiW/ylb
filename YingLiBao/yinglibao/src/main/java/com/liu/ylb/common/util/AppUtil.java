package com.liu.ylb.common.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class AppUtil {
    public static boolean checkPhone(String phone){
        boolean res = false;
        if( phone != null){
            res = Pattern.matches("^1[1-9]\\d{9}$",phone);
        }
        return res;
    }
    public static boolean ge(BigDecimal n1, BigDecimal n2){
        if( n1 == null || n2 == null){
            throw new RuntimeException("判断两个BigDecimal参数为null");
        }
        return n1.compareTo(n2) >=0;
    }
}
