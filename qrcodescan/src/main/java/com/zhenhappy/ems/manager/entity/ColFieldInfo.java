package com.zhenhappy.ems.manager.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangxd on 2017/4/17.
 */
@Entity
@Table(name = "col_field_info", schema = "dbo")
public class ColFieldInfo implements Serializable {
    private Integer id;
    private String colname;
    private String colfield;

    public ColFieldInfo() {
    }

    public ColFieldInfo(Integer id) {
        this.id = id;
    }

    public ColFieldInfo(Integer id, String colname, String colfield) {
        this.id = id;
        this.colname = colname;
        this.colfield = colfield;
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

    @Column(name = "colname")
    public String getColname() {
        return colname;
    }

    public void setColname(String colname) {
        this.colname = colname;
    }

    @Column(name = "colfield")
    public String getColfield() {
        return colfield;
    }

    public void setColfield(String colfield) {
        this.colfield = colfield;
    }
}
