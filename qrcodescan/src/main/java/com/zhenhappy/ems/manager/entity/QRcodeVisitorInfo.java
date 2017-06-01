package com.zhenhappy.ems.manager.entity;

/**
 * Created by wangxd on 2017/4/17.
 */
public class QRcodeVisitorInfo {
    private Integer id;
    private String checkingno;
    private String firstName;
    private String email;
    private String company;
    private String mobilePhone;
    private Integer fairid;
    private String bakfield;

    public QRcodeVisitorInfo() {}

    public QRcodeVisitorInfo(Integer id, String firstName, String email, String checkingno, String company, String mobilePhone) {
        this.id = id;
        this.firstName = firstName;
        this.checkingno = checkingno;
        this.company = company;
        this.mobilePhone = mobilePhone;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCheckingno() {
        return checkingno;
    }

    public void setCheckingno(String checkingno) {
        this.checkingno = checkingno;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Integer getFairid() {
        return fairid;
    }

    public void setFairid(Integer fairid) {
        this.fairid = fairid;
    }

    public String getBakfield() {
        return bakfield;
    }

    public void setBakfield(String bakfield) {
        this.bakfield = bakfield;
    }
}
