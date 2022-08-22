package com.liu.ylb.front.vo;

import com.liu.ylb.front.enums.RCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "服务器应答结果对象",description = "统一应答结果json")
@Data
public class CommonResult {
    @ApiModelProperty(name = "code",notes = "自定义状态码")
    private int code;
    @ApiModelProperty(name = "message",notes = "文本说明")
    private String message;
    @ApiModelProperty(name = "info",notes = "应答数据")
    private Object info;

    public CommonResult() {
    }

    public CommonResult(RCode rCode) {
         setRCode(rCode);
         setInfo("");
    }

    public void setRCode(RCode rcode){
        this.code = rcode.getCode();
        this.message = rcode.getText();
    }

    public static CommonResult SUCC(){
        CommonResult result = new CommonResult();
        result.setRCode(RCode.RESP_SUCC);
        result.setInfo("");
        return result;
    }
    public static CommonResult FAIL(){
        CommonResult result = new CommonResult();
        result.setRCode(RCode.RESP_FIAL);
        result.setInfo("");
        return result;
    }
    public static CommonResult SUCC(Object info){
        CommonResult result = new CommonResult();
        result.setRCode(RCode.RESP_SUCC);
        result.setInfo(info);
        return result;
    }
    public static CommonResult FAIL(Object info){
        CommonResult result = new CommonResult();
        result.setRCode(RCode.RESP_FIAL);
        result.setInfo("");
        return result;
    }
}
