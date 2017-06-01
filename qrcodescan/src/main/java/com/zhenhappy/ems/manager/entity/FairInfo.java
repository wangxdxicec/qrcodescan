package com.zhenhappy.ems.manager.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangxd on 2017/4/17.
 */
@Entity
@Table(name = "fair_info", schema = "dbo")
public class FairInfo implements Serializable {
    private Integer id;
    private Integer fairid;                     //展会ID
    private String fairname;                    //展会名称
    private String fairalairname;               //展会别名
    private Date fairbegindate;                 //展会结束时间
    private Date fairenddate;                   //展会开始时间
    private Integer fairenable;                 //展会是否启用
    private Integer init_data_flag;             //是否已经加载数据  1：表示不需要加载；0：表示需要加载
    private String database_url;                //加载数据对应的数据库URL
    private String load_data_begin_time;        //数据加载开始时间
    private String load_data_end_time;          //数据加载结束时间
    private String sync_onsite_start_time;      //现场同步数据时间点

    public FairInfo() {
    }

    public FairInfo(Integer id) {
        this.id = id;
    }

    public FairInfo(Integer id, Integer fairid, String fairname, String fairalairname, Date fairbegindate,
                    Date fairenddate, Integer fairenable, Integer init_data_flag, String database_url,
                    String load_data_begin_time, String load_data_end_time, String sync_onsite_start_time) {
        this.id = id;
        this.fairid = fairid;
        this.fairname = fairname;
        this.fairalairname = fairalairname;
        this.fairbegindate = fairbegindate;
        this.fairenddate = fairenddate;
        this.fairenable = fairenable;
        this.init_data_flag = init_data_flag;
        this.database_url = database_url;
        this.load_data_begin_time = load_data_begin_time;
        this.load_data_end_time = load_data_end_time;
        this.sync_onsite_start_time = sync_onsite_start_time;
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

    @Column(name = "fairid")
    public Integer getFairid() {
        return fairid;
    }

    public void setFairid(Integer fairid) {
        this.fairid = fairid;
    }

    @Column(name = "fairname")
    public String getFairname() {
        return fairname;
    }

    public void setFairname(String fairname) {
        this.fairname = fairname;
    }

    @Column(name = "fairbegindate")
    public Date getFairbegindate() {
        return fairbegindate;
    }

    public void setFairbegindate(Date fairbegindate) {
        this.fairbegindate = fairbegindate;
    }

    @Column(name = "fairenddate")
    public Date getFairenddate() {
        return fairenddate;
    }

    public void setFairenddate(Date fairenddate) {
        this.fairenddate = fairenddate;
    }

    @Column(name = "fairenable")
    public Integer getFairenable() {
        return fairenable;
    }

    public void setFairenable(Integer fairenable) {
        this.fairenable = fairenable;
    }

    @Column(name = "fairalairname")
    public String getFairalairname() {
        return fairalairname;
    }

    public void setFairalairname(String fairalairname) {
        this.fairalairname = fairalairname;
    }

    @Column(name = "init_data_flag")
    public Integer getInit_data_flag() {
        return init_data_flag;
    }

    public void setInit_data_flag(Integer init_data_flag) {
        this.init_data_flag = init_data_flag;
    }

    @Column(name = "database_url")
    public String getDatabase_url() {
        return database_url;
    }

    public void setDatabase_url(String database_url) {
        this.database_url = database_url;
    }

    @Column(name = "load_data_begin_time")
    public String getLoad_data_begin_time() {
        return load_data_begin_time;
    }

    public void setLoad_data_begin_time(String load_data_begin_time) {
        this.load_data_begin_time = load_data_begin_time;
    }

    @Column(name = "load_data_end_time")
    public String getLoad_data_end_time() {
        return load_data_end_time;
    }

    public void setLoad_data_end_time(String load_data_end_time) {
        this.load_data_end_time = load_data_end_time;
    }

    @Column(name = "sync_onsite_start_time")
    public String getSync_onsite_start_time() {
        return sync_onsite_start_time;
    }

    public void setSync_onsite_start_time(String sync_onsite_start_time) {
        this.sync_onsite_start_time = sync_onsite_start_time;
    }
}
