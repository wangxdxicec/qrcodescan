package com.zhenhappy.ems.manager.dto;

import com.zhenhappy.ems.dto.BaseResponse;

/**
 * query tags by page.
 * <p/>
 * Created by wangxd on 2017-04-20.
 */
public class QueryCustomerScancodeResponse extends BaseResponse {
    public Integer scannum;
    private Integer takeFileFlag;

    public QueryCustomerScancodeResponse() {
    }

    public Integer getScannum() {
        return scannum;
    }

    public void setScannum(Integer scannum) {
        this.scannum = scannum;
    }

    public Integer getTakeFileFlag() {
        return takeFileFlag;
    }

    public void setTakeFileFlag(Integer takeFileFlag) {
        this.takeFileFlag = takeFileFlag;
    }
}
