package com.zhenhappy.ems.manager.service;

import com.zhenhappy.ems.dao.CustomerInfoDao;
import com.zhenhappy.ems.entity.WCustomer;
import com.zhenhappy.ems.manager.dao.QrCodeScanDao;
import com.zhenhappy.ems.manager.entity.QRCodeScanInfo;
import com.zhenhappy.ems.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangxd on 2016-04-19.
 */
@Service
public class CodeScanManagerService extends ProductService {
    @Autowired
    private QrCodeScanDao qrCodeScanDao;
    @Autowired
    private CustomerInfoDao customerInfoDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;
	/**
	 * @return
	 */
	@Transactional
    public QRCodeScanInfo loadQRCodeScanInfoByCheckNo(String value) {
		List<QRCodeScanInfo> qrCodeScanInfoList = qrCodeScanDao.queryByHql("from QRCodeScanInfo where checkingno=?", new Object[]{value});
        if (qrCodeScanInfoList != null && qrCodeScanInfoList.size()>0){
            return qrCodeScanInfoList.get(0);
        } else{
            return null;
        }
    }

    /**
     * @return
     */
    @Transactional
    public QRCodeScanInfo loadQRCodeScanInfoByPhone(String value) {
        List<QRCodeScanInfo> groups = getHibernateTemplate().find("from QRCodeScanInfo where mobilephone=?", new Object[]{value});
        if (groups != null && groups.size()>0){
            return groups.get(0);
        } else{
            return null;
        }
    }

    /**
     * @return
     */
    @Transactional
    public List<QRCodeScanInfo> loadAllPhoneByPhone() {
        List<QRCodeScanInfo> groups = qrCodeScanDao.queryByHql("from QRCodeScanInfo", new Object[]{});
        return groups.size() > 0 ? groups : null;
    }

    @Transactional
    public WCustomer loadVisitorByCheckNo(String checkingno) {
        List<WCustomer> visitorInfoList = customerInfoDao.queryByHql("from WCustomer where checkingNo=?", new Object[]{ checkingno });
        if (visitorInfoList != null && visitorInfoList.size()>0){
            return visitorInfoList.get(0);
        } else{
            return null;
        }
    }

    @Transactional
    public WCustomer loadVisitorByPhone(String phone) {
        List<WCustomer> visitorInfoList = customerInfoDao.queryByHql("from WCustomer where mobilePhone=?", new Object[]{ phone });
        if (visitorInfoList != null && visitorInfoList.size()>0){
            return visitorInfoList.get(0);
        } else{
            return null;
        }
    }

    public boolean isExiistVisitor(String value) {
        WCustomer wCustomer1 = loadVisitorByCheckNo(value);
        WCustomer wCustomer2 = loadVisitorByPhone(value);
        if(wCustomer1 != null || wCustomer2 != null){
            return true;
        }else {
            return false;
        }
    }

	/**
	 * 添加手机
	 * @param qrCodeScanInfo
	 */
    @Transactional
    public void addQRCodeScanInfo(QRCodeScanInfo qrCodeScanInfo){
        hibernateTemplate.save(qrCodeScanInfo);
    }
    
    @Transactional
    public void modifyQRCodeScanInfo(QRCodeScanInfo qrCodeScanInfo){
        hibernateTemplate.saveOrUpdate(qrCodeScanInfo);
        //qrCodeScanDao.update(phone);
    }
}
