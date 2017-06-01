package com.zhenhappy.ems.manager.service;

import com.zhenhappy.ems.manager.dao.QRcodeVisitorInfoDao;
import com.zhenhappy.ems.manager.entity.ColFieldInfo;
import com.zhenhappy.ems.manager.entity.VisitorInfoTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangxd on 2017-05-22.
 */
@Service
public class QRcodeVisitorInfoService {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Autowired
    private QRcodeVisitorInfoDao qRcodeVisitorInfoDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 查询所有数据
     * @return
     */
    @Transactional
    public List<VisitorInfoTemp> loadAllQRcodeVisitorInfoList(){
        List<VisitorInfoTemp> qrcodeVisitorInfoList = hibernateTemplate.find("from VisitorInfoTemp ", null);
        return qrcodeVisitorInfoList;
    }

    /**
     * 删除所有数据
     * @return
     */
    @Transactional
    public void deleteAllQRcodeVisitorInfo(){
        jdbcTemplate.execute(" truncate table qrcode_visitor_info ");
    }

    /**
     * 根据Fairid删除数据
     * @return
     */
    @Transactional
    public void deleteAllQRcodeVisitorInfoByFairid(Integer fairId){
        jdbcTemplate.execute(" delete from qrcode_visitor_info where fairid=" + fairId);
    }

    @Transactional
    public void saveQRcodeVisitorInfo(VisitorInfoTemp qrcodeVisitorInfo) {
        try {
            qRcodeVisitorInfoDao.create(qrcodeVisitorInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public VisitorInfoTemp loadQRcodeVisitorInfoByCheckingnoAndFairid(String checkingno, Integer fairid){
        List<VisitorInfoTemp> qrcodeVisitorInfo = qRcodeVisitorInfoDao.queryByHql("from VisitorInfoTemp where checkingno=? and fairid=?", new Object[]{checkingno, fairid});
        return qrcodeVisitorInfo.size() > 0 ? qrcodeVisitorInfo.get(0) : null;
    }
}
