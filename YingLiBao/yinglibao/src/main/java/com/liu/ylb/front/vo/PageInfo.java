package com.liu.ylb.front.vo;

import lombok.Getter;

/**
 *
 */
@Getter
public class PageInfo {
    private Integer pageNo = 1;
    private Integer pageSize = 9 ;
    private Long totalRecord = 0L ;
    private Long totalPage = 1L;

    public PageInfo() {
    }

    public PageInfo(Integer pageNo, Integer pageSize, Long totalRecord) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        //计算总页数
        if( totalRecord % pageSize == 0){
            totalPage = totalRecord / pageSize;
        } else  {
            totalPage = totalRecord / pageSize + 1;
        }

    }
}
