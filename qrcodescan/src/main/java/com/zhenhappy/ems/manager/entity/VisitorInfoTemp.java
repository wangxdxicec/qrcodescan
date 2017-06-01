package com.zhenhappy.ems.manager.entity;

import javax.persistence.*;

/**
 * Created by wangxd on 2017/4/17.
 */
@Entity
@Table(name = "qrcode_visitor_info", schema = "dbo")
public class VisitorInfoTemp {
    private Integer id;
    private String checkingno;
    private String firstName;
    private String email;
    private String company;
    private String mobilePhone;
    private Integer fairid;
    private String bakfield;

    public VisitorInfoTemp() {}

    public VisitorInfoTemp(Integer id, String firstName, String email, String checkingno, String company,
                           String mobilePhone, Integer fairid, String bakfield) {
        this.id = id;
        this.firstName = firstName;
        this.checkingno = checkingno;
        this.company = company;
        this.mobilePhone = mobilePhone;
        this.email = email;
        this.fairid = fairid;
        this.bakfield = bakfield;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "checkingno")
    public String getCheckingno() {
        return checkingno;
    }

    public void setCheckingno(String checkingno) {
        this.checkingno = checkingno;
    }

    @Column(name = "company")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "mobilePhone")
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "fairid")
    public Integer getFairid() {
        return fairid;
    }

    public void setFairid(Integer fairid) {
        this.fairid = fairid;
    }

    @Column(name = "bakfield")
    public String getBakfield() {
        return bakfield;
    }

    public void setBakfield(String bakfield) {
        this.bakfield = bakfield;
    }
}
