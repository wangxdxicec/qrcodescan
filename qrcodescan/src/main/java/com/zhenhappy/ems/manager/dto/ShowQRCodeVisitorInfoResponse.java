package com.zhenhappy.ems.manager.dto;

import com.zhenhappy.ems.dto.BaseResponse;

/**
 * query customers by page.
 * <p/>
 * Created by wangxd on 2016-05-31.
 */
public class ShowQRCodeVisitorInfoResponse extends BaseResponse {
    private String firstname;
    private String company;
    private String checkingno;
    private Integer scanNum;
    private Integer takeFlag;

    public ShowQRCodeVisitorInfoResponse() {
    }

    public ShowQRCodeVisitorInfoResponse(String firstname, String company, String checkingno, Integer scanNum, Integer takeFlag) {
        this.firstname = firstname;
        this.company = company;
        this.checkingno = checkingno;
        this.scanNum = scanNum;
        this.takeFlag = takeFlag;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCheckingno() {
        return checkingno;
    }

    public void setCheckingno(String checkingno) {
        this.checkingno = checkingno;
    }

    public Integer getScanNum() {
        return scanNum;
    }

    public void setScanNum(Integer scanNum) {
        this.scanNum = scanNum;
    }

    public Integer getTakeFlag() {
        return takeFlag;
    }

    public void setTakeFlag(Integer takeFlag) {
        this.takeFlag = takeFlag;
    }
}
