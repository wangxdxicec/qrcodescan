package com.zhenhappy.ems.manager.entity;

/**
 * Created by wangxd on 2017/4/17.
 */
public class QRCodeScanTemp {

    private String checkingno;
    private String mobilephone;

    public QRCodeScanTemp() {}

    public QRCodeScanTemp(String checkingno, String mobilephone) {
        this.checkingno = checkingno;
        this.mobilephone = mobilephone;
    }

    public String getCheckingno() {
        return checkingno;
    }

    public void setCheckingno(String checkingno) {
        this.checkingno = checkingno;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }
}
