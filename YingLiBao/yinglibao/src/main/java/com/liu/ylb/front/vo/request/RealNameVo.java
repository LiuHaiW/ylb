package com.liu.ylb.front.vo.request;

import com.liu.ylb.common.util.AppUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

@Data
public class RealNameVo {
    private String name;
    private String cardId;
    private String phone;

    public boolean checkRealName(){
        boolean check = true;
        if(!AppUtil.checkPhone(this.phone)){
            check = false;
        }
        if(!Pattern.matches("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)",this.cardId)){
            check = false;
        }
        if(this.name.length()<2 || StringUtils.isBlank(name)){
            check = false;
        }
        return check;
    }

}
