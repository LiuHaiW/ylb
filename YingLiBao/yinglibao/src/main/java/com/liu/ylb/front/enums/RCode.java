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
    PHONE_EXITS(1009,"手机号已经注册过"),
    TOKEN_INVALID(2000,"token无效"),
    USER_LOGIN_FAIL(1010,"用户名或密码错误"),
    REALNAME_DOUBLE(1011,"不能重复认证"),
    REALNAME_PHONE_ERR(1012,"认证的手机号不正确"),
    USER_NOT_EXISTS(1013,"用户不存在"),
    REALNAME_API_FAIL(1014,"认证失败"),
    INVEST_DEFAULT(1015,"请重新投资"),
    INVEST_SUCCESS(1016,"投资成功"),
    INVEST_MONEY_ERR(1017,"投资金额不满足要求"),
    INVEST_PRODUCT_NOT_SELL(1018,"产品不可售卖"),
    INVEST_RPODUCT_NOT_EXITS(1019,"理财产品不存在"),
    INVEST_ACCOUNT_MONEY_NOT_ENOUGH(1020,"账号金额不足"),
    INVEST_ACCOUNT_NOT_EXITS(1021,"资金账号不存在"),
    EXCEPTION_NULLPOINT(1022,"发生异常" ),
    EXCEPTION_DEFAULT(1023,"发生了其他异常" );

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
