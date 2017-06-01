package com.zhenhappy.ems.manager.service;

import com.zhenhappy.ems.manager.entity.ColFieldInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangxd on 2017-01-24.
 */
@Service
public class ColFieldInfoService {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    /**
     * 查询所有查询字段
     * @return
     */
    @Transactional
    public List<ColFieldInfo> loadAllColFieldList(){
        List colFieldList = hibernateTemplate.find("from ColFieldInfo ", null);
        if(colFieldList.size()==0){
            return null;
        }else{
            return colFieldList;
        }
    }
}
