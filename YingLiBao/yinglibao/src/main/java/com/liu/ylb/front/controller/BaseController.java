package com.liu.ylb.front.controller;

public class BaseController {
    public Boolean checkProductType(Integer type){
        if(type >= 0 && type < 3){
            return true;
        }
        return false;
    }
    public Integer defPageNum(Integer pageNum){
        if(pageNum == null || pageNum < 1){
            pageNum = 1;
        }
        return pageNum;
    }
    public Integer defPageSize(Integer pageSize){
        if(pageSize < 1 || pageSize == null || pageSize > 30){
            pageSize = 9;
        }
        return pageSize;
    }
    public  boolean checkUid(Integer uid){
        return (uid != null && uid >0) ? true :false;
    }
    public boolean checkPid(Integer pid){
        return (pid != null && pid >0) ? true : false;
    }
}
