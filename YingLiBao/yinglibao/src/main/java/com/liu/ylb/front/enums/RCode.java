package com.liu.ylb.front.enums;

public enum RCode {
    RESP_SUCC(1000,"成功"),
    RESP_FIAL(1001,"失败"),
    PRODUCT_TYPE_NOT_RANGE(1002,"无此产品类型"),
    PHONE_FORMAT_ERR(1003,"手机号格式不正确"),
    ACTION_CMD_ERR(1004,"不能发送短信"),
    SMS_CODE_EXISTS(1005,"短信验证码可以继续使用"),
    SMS_SEND_FAIL(1006,"请重新发送验证码"),
    REQUEST_PARAM_ERR(1007,"请求参数错误"),
    SMS_CODE_ERR(1008,"无效的验证码"),
    PHONE_EXITS(1009,"手机号已经注册过");
    private int code;
    private String text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    RCode(int code, String text) {
        this.code = code;
        this.text = text;
    }
}
