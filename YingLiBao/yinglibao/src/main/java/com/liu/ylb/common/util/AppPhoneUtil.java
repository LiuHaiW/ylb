package com.liu.ylb.common.util;

import java.util.regex.Pattern;

public class AppPhoneUtil {
    public static boolean checkPhone(String phone){
        boolean res = false;
        if( phone != null){
            res = Pattern.matches("^1[1-9]\\d{9}$",phone);
        }
        return res;
    }
}
