package com.zhenhappy.ems.manager.entity;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wangxd on 2017/4/17.
 */
@Entity
@Table(name = "code_scan_info", schema = "dbo")
public class QRCodeScanInfo implements Serializable {
    private int id;
    private String checkingno;
    private String mobilephone;
    private Integer hastakeproceed;
    private Integer count;
    private Integer fairid;

    public QRCodeScanInfo() {
    }

    public QRCodeScanInfo(Integer id) {
        this.id = id;
    }

    public QRCodeScanInfo(Integer id, String mobilephone, Integer count) {
        this.id = id;
        this.mobilephone = mobilephone;
        this.count = count;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Column(name = "checkingno")
    public String getCheckingno() {
        return checkingno;
    }

    public void setCheckingno(String checkingno) {
        this.checkingno = checkingno;
    }

    @Column(name = "mobilephone")
    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    @Column(name = "hastakeproceed")
    public Integer getHastakeproceed() {
        return hastakeproceed;
    }

    public void setHastakeproceed(Integer hastakeproceed) {
        this.hastakeproceed = hastakeproceed;
    }

    @Column(name = "fairid")
    public Integer getFairid() {
        return fairid;
    }

    public void setFairid(Integer fairid) {
        this.fairid = fairid;
    }
}
