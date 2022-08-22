package com.liu.ylb.front.enums;

public enum RCode {
    RESP_SUCC(1000,"成功"),
    RESP_FIAL(1001,"失败"),
    PRODUCT_TYPE_NOT_RANGE(1002,"无此产品类型");
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
