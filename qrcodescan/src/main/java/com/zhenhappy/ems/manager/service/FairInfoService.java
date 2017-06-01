package com.zhenhappy.ems.manager.service;

import com.zhenhappy.ems.manager.dao.FairInfoDao;
import com.zhenhappy.ems.manager.entity.FairInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangxd on 2017-01-24.
 */
@Service
public class FairInfoService {
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private FairInfoDao fairInfoDao;

    @Transactional
    public List<FairInfo> loadAllFair(){
        List fairInfoList = hibernateTemplate.find("from FairInfo ", null);
        if(fairInfoList.size()==0){
            return null;
        }else{
            return fairInfoList;
        }
    }

    /**
     * 查询所有激活的展会  0：停用；1：可上传资料；2：已截止上传
     * @return
     */
    @Transactional
    public List<FairInfo> loadAllEnableFair(){
        List fairInfoList = hibernateTemplate.find("from FairInfo where fairenable =1", null);
        if(fairInfoList.size()==0){
            return null;
        }else{
            return fairInfoList;
        }
    }

    /**
     * 根据fairid查询对应的展会信息
     * @return
     */
    @Transactional
    public FairInfo loadFairInfoByFairId(Integer fairId){
        List<FairInfo> fairInfoList = hibernateTemplate.find("from FairInfo where fairid=? ", new Object[]{fairId});
        if(fairInfoList.size()==0){
            return null;
        }else{
            return fairInfoList.get(0);
        }
    }

    @Transactional
    public void updateFairInfo(FairInfo fairInfo){
        try {
            fairInfoDao.update(fairInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void saveFairInfo(FairInfo fairInfo){
        try {
            fairInfoDao.create(fairInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
