package com.liu.ylb.front.vo.request;

import com.liu.ylb.common.util.AppPhoneUtil;
import lombok.Data;

@Data
public class UserVo {
    private String phone;
    private String secret;
    private String code;

    public boolean checkParam(){
        boolean check = false;
        if(AppPhoneUtil.checkPhone(this.phone) && this.secret != null && this.secret.length() ==32 && this.code != null && this.code.length() == 4){
            check =true;
        }
        return check;
    }
}
