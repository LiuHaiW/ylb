package com.liu.ylb.front.exception;

import com.liu.ylb.front.enums.RCode;
import com.liu.ylb.front.vo.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler({ NullPointerException.class , IndexOutOfBoundsException.class })
    public CommonResult doException(NullPointerException ex){
        logger.error("发生了异常"+ex.getMessage());
        CommonResult commonResult = CommonResult.FAIL();
        commonResult.setRCode(RCode.EXCEPTION_NULLPOINT);
        return commonResult;
    }
    @ExceptionHandler
    public CommonResult doOtherException(Exception ex){
        logger.error("发生了异常"+ex.getMessage());
        CommonResult result = CommonResult.FAIL();
        result.setRCode(RCode.EXCEPTION_DEFAULT);
        return result;
    }
}
