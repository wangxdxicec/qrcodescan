package com.zhenhappy.ems.manager.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zhenhappy.ems.manager.dto.QueryVisitorRequest;
import com.zhenhappy.ems.manager.entity.FairInfo;
import com.zhenhappy.ems.manager.entity.QRCodeScanInfo;
import com.zhenhappy.ems.manager.entity.QRCodeScanTemp;
import com.zhenhappy.ems.manager.entity.VisitorInfoTemp;
import com.zhenhappy.ems.manager.service.CodeScanManagerService;
import com.zhenhappy.ems.manager.service.QRcodeVisitorInfoService;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangxd on 2017/1/24.
 */
public class JdbcUtils_C3P0 {

    public List<VisitorInfoTemp> executeDataBaseByTypeAndField(String driverClass, String jdbcUrl, String username, String password,
                                                      String table, Integer tFairId, String fieldvalue, Integer hastakeproceed,
                                                               String field, QueryVisitorRequest request){
        ComboPooledDataSource ds = new ComboPooledDataSource();
        List<VisitorInfoTemp> visitorInfoTempList = new ArrayList<VisitorInfoTemp>();
        try {
            ds.setDriverClass(driverClass);
            ds.setJdbcUrl(jdbcUrl);
            ds.setUser(username);
            ds.setPassword(password);
            ds.setMaxPoolSize(400);
            ds.setInitialPoolSize(50);
            ds.setMaxIdleTime(2000);

            Connection cn = ds.getConnection();
            Statement st = cn.createStatement();

            //根据界面传入的值，先获取t_exhibitor_booth表里所需的字段值
            String sql = "select * from " + table + " where " + field + "='" + fieldvalue + "' and (convert(varchar, UpdateTime,120) >= '2016-10-25 00:00:00' and convert(varchar, UpdateTime,120) <= '2017-04-24 23:59:59' ) ";
            ResultSet exhibitorBooth = st.executeQuery(sql);
            while(exhibitorBooth.next()) {
                if(1 != request.getType()){
                    String email = exhibitorBooth.getString(2);
                    String checkingno = exhibitorBooth.getString(3);
                    String firstname = exhibitorBooth.getString(5);
                    String company = exhibitorBooth.getString(8);
                    String mobile = exhibitorBooth.getString(17);
                    VisitorInfoTemp visitorInfoTemp = new VisitorInfoTemp();
                    visitorInfoTemp.setCheckingno(checkingno);
                    if(StringUtils.isNotEmpty(email)){
                        visitorInfoTemp.setEmail(email);
                    }else{
                        visitorInfoTemp.setEmail("");
                    }
                    if(StringUtils.isNotEmpty(firstname)){
                        visitorInfoTemp.setFirstName(firstname);
                    }else {
                        visitorInfoTemp.setFirstName("");
                    }
                    if(StringUtils.isNotEmpty(company)){
                        visitorInfoTemp.setCompany(company);
                    }else {
                        visitorInfoTemp.setCompany("");
                    }
                    if(StringUtils.isNotEmpty(mobile)){
                        visitorInfoTemp.setMobilePhone(mobile);
                    }else {
                        visitorInfoTemp.setMobilePhone("");
                    }
                    visitorInfoTempList.add(visitorInfoTemp);
                }else {
                    String email = exhibitorBooth.getString(2);
                    String checkingno = exhibitorBooth.getString(3);
                    String firstname = exhibitorBooth.getString(6);
                    String company = exhibitorBooth.getString(9);
                    String mobile = exhibitorBooth.getString(17);
                    VisitorInfoTemp visitorInfoTemp = new VisitorInfoTemp();
                    visitorInfoTemp.setCheckingno(checkingno);
                    if(StringUtils.isNotEmpty(email)){
                        visitorInfoTemp.setEmail(email);
                    }
                    if(StringUtils.isNotEmpty(firstname)){
                        visitorInfoTemp.setFirstName(firstname);
                    }
                    if(StringUtils.isNotEmpty(company)){
                        visitorInfoTemp.setCompany(company);
                    }
                    if(StringUtils.isNotEmpty(mobile)){
                        visitorInfoTemp.setMobilePhone(mobile);
                    }
                    visitorInfoTempList.add(visitorInfoTemp);
                }
            }
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visitorInfoTempList;
    }

    public List<VisitorInfoTemp> executeDataBaseByTypeAndScanCode(String driverClass, String jdbcUrl, String username, String password,
                                                      String table, Integer tFairId, String scancode, Integer hastakeproceed, QueryVisitorRequest request){
        ComboPooledDataSource ds = new ComboPooledDataSource();
        List<VisitorInfoTemp> visitorInfoTempList = new ArrayList<VisitorInfoTemp>();
        try {
            ds.setDriverClass(driverClass);
            ds.setJdbcUrl(jdbcUrl);
            ds.setUser(username);
            ds.setPassword(password);
            ds.setMaxPoolSize(400);
            ds.setInitialPoolSize(50);
            ds.setMaxIdleTime(2000);

            Connection cn = ds.getConnection();
            Statement st = cn.createStatement();

            //根据界面传入的值，先获取t_exhibitor_booth表里所需的字段值
            String sql = "select * from " + table + " where CheckingNo=" + scancode + " and IsDisabled=0";
            ResultSet exhibitorBooth = st.executeQuery(sql);
            while(exhibitorBooth.next()) {
                if(1 != request.getType()){
                    String email = exhibitorBooth.getString(2);
                    String checkingno = exhibitorBooth.getString(3);
                    String firstname = exhibitorBooth.getString(5);
                    String company = exhibitorBooth.getString(8);
                    String mobile = exhibitorBooth.getString(17);
                    VisitorInfoTemp visitorInfoTemp = new VisitorInfoTemp();
                    visitorInfoTemp.setCheckingno(checkingno);
                    if(StringUtils.isNotEmpty(email)){
                        visitorInfoTemp.setEmail(email);
                    }else{
                        visitorInfoTemp.setEmail("");
                    }
                    if(StringUtils.isNotEmpty(firstname)){
                        visitorInfoTemp.setFirstName(firstname);
                    }else {
                        visitorInfoTemp.setFirstName("");
                    }
                    if(StringUtils.isNotEmpty(company)){
                        visitorInfoTemp.setCompany(company);
                    }else {
                        visitorInfoTemp.setCompany("");
                    }
                    if(StringUtils.isNotEmpty(mobile)){
                        visitorInfoTemp.setMobilePhone(mobile);
                    }else {
                        visitorInfoTemp.setMobilePhone("");
                    }
                    visitorInfoTempList.add(visitorInfoTemp);
                }else {
                    String email = exhibitorBooth.getString(2);
                    String checkingno = exhibitorBooth.getString(3);
                    String firstname = exhibitorBooth.getString(6);
                    String company = exhibitorBooth.getString(9);
                    String mobile = exhibitorBooth.getString(17);
                    VisitorInfoTemp visitorInfoTemp = new VisitorInfoTemp();
                    visitorInfoTemp.setCheckingno(checkingno);
                    if(StringUtils.isNotEmpty(email)){
                        visitorInfoTemp.setEmail(email);
                    }
                    if(StringUtils.isNotEmpty(firstname)){
                        visitorInfoTemp.setFirstName(firstname);
                    }
                    if(StringUtils.isNotEmpty(company)){
                        visitorInfoTemp.setCompany(company);
                    }
                    if(StringUtils.isNotEmpty(mobile)){
                        visitorInfoTemp.setMobilePhone(mobile);
                    }
                    visitorInfoTempList.add(visitorInfoTemp);
                }
            }
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visitorInfoTempList;
    }

    public List<VisitorInfoTemp> executeDataBaseByTypeAndFieldForVisitorInfoTemp(String driverClass, String jdbcUrl, String username,
                                                                                 String password, String table, QueryVisitorRequest request){
        ComboPooledDataSource ds = new ComboPooledDataSource();
        List<VisitorInfoTemp> visitorInfoTempList = new ArrayList<VisitorInfoTemp>();
        try {
            ds.setDriverClass(driverClass);
            ds.setJdbcUrl(jdbcUrl);
            ds.setUser(username);
            ds.setPassword(password);
            ds.setMaxPoolSize(400);
            ds.setInitialPoolSize(50);
            ds.setMaxIdleTime(2000);

            Connection cn = ds.getConnection();
            Statement st = cn.createStatement();

            //根据界面传入的值，先获取t_exhibitor_booth表里所需的字段值
            List<String> conditions = new ArrayList<String>();
            if(StringUtils.isNotEmpty(request.getCheckingno())){
                conditions.add(" (CheckingNo like '%" + request.getCheckingno() + "%' OR CheckingNo like '%" + new String(request.getCheckingno().getBytes("ISO-8859-1"),"GBK") + "%' OR CheckingNo like '%" + new String(request.getCheckingno().getBytes("ISO-8859-1"),"utf-8") + "%') ");
            }
            if(StringUtils.isNotEmpty(request.getCompany())){
                conditions.add(" (Company like '%" + request.getCompany() + "%' OR Company like '%" + new String(request.getCompany().getBytes("ISO-8859-1"),"GBK") + "%' OR Company like '%" + new String(request.getCompany().getBytes("ISO-8859-1"),"utf-8") + "%') ");
            }
            if(StringUtils.isNotEmpty(request.getFirstName())){
                conditions.add(" (FirstName like '%" + request.getFirstName() + "%' OR FirstName like '%" + new String(request.getFirstName().getBytes("ISO-8859-1"),"GBK") + "%' OR FirstName like '%" + new String(request.getFirstName().getBytes("ISO-8859-1"),"utf-8") + "%') ");
            }
            if(StringUtils.isNotEmpty(request.getMobilePhone())){
                conditions.add(" (Mobile like '%" + request.getMobilePhone() + "%' OR Mobile like '%" + new String(request.getMobilePhone().getBytes("ISO-8859-1"),"GBK") + "%' OR Mobile like '%" + new String(request.getMobilePhone().getBytes("ISO-8859-1"),"utf-8") + "%') ");
            }
            conditions.add(" (convert(varchar, UpdateTime,120) >= '2016-10-25 00:00:00' and convert(varchar, UpdateTime,120) <= '2017-04-24 23:59:59' ) ");
            String conditionsSql = StringUtils.join(conditions, " and ");
            conditionsSql = " where " + conditionsSql;
            //String sql = "select * from " + table + " where " + field + "='" + fieldvalue + "' and IsDisabled=0";
            String sql = "select * from " + table + conditionsSql;
            ResultSet exhibitorBooth = st.executeQuery(sql);
            while(exhibitorBooth.next()) {
                if(1 != request.getType()){
                    String email = exhibitorBooth.getString(2);
                    String checkingno = exhibitorBooth.getString(3);
                    String firstname = exhibitorBooth.getString(5);
                    String company = exhibitorBooth.getString(8);
                    String mobile = exhibitorBooth.getString(17);
                    VisitorInfoTemp visitorInfoTemp = new VisitorInfoTemp();
                    visitorInfoTemp.setCheckingno(checkingno);
                    if(StringUtils.isNotEmpty(email)){
                        visitorInfoTemp.setEmail(email);
                    }
                    if(StringUtils.isNotEmpty(firstname)){
                        visitorInfoTemp.setFirstName(firstname);
                    }
                    if(StringUtils.isNotEmpty(company)){
                        visitorInfoTemp.setCompany(company);
                    }
                    if(StringUtils.isNotEmpty(mobile)){
                        visitorInfoTemp.setMobilePhone(mobile);
                    }
                    visitorInfoTempList.add(visitorInfoTemp);
                }else {
                    String email = exhibitorBooth.getString(2);
                    String checkingno = exhibitorBooth.getString(3);
                    String firstname = exhibitorBooth.getString(6);
                    String company = exhibitorBooth.getString(9);
                    String mobile = exhibitorBooth.getString(17);
                    VisitorInfoTemp visitorInfoTemp = new VisitorInfoTemp();
                    visitorInfoTemp.setCheckingno(checkingno);
                    if(StringUtils.isNotEmpty(email)){
                        visitorInfoTemp.setEmail(email);
                    }
                    if(StringUtils.isNotEmpty(firstname)){
                        visitorInfoTemp.setFirstName(firstname);
                    }
                    if(StringUtils.isNotEmpty(company)){
                        visitorInfoTemp.setCompany(company);
                    }
                    if(StringUtils.isNotEmpty(mobile)){
                        visitorInfoTemp.setMobilePhone(mobile);
                    }
                    visitorInfoTempList.add(visitorInfoTemp);
                }
            }
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return visitorInfoTempList;
    }

    public QRCodeScanInfo getQRCodeScanInfoByCheckingNo(String driverClass, String jdbcUrl, String username,
                                                 String password, String table, String checkingno) {
        StringBuffer isExistBoothNumberBuffer = new StringBuffer();
        ComboPooledDataSource ds = new ComboPooledDataSource();
        QRCodeScanInfo qrCodeScanInfo = null;
        try {
            ds.setDriverClass(driverClass);
            ds.setJdbcUrl(jdbcUrl);
            ds.setUser(username);
            ds.setPassword(password);
            ds.setMaxPoolSize(400);
            ds.setInitialPoolSize(50);
            ds.setMaxIdleTime(2000);

            Connection cn = ds.getConnection();
            Statement st = cn.createStatement();
            String isCodeScanInfoSql = "select * from code_scan_info where checkingno=" + checkingno;
            ResultSet isCodeScanInfoSqlSet = st.executeQuery(isCodeScanInfoSql);
            while(isCodeScanInfoSqlSet.next()) {
                qrCodeScanInfo = new QRCodeScanInfo();
                qrCodeScanInfo.setHastakeproceed(Integer.parseInt(isCodeScanInfoSqlSet.getString(4)));
                qrCodeScanInfo.setCount(Integer.parseInt(isCodeScanInfoSqlSet.getString(5)));
            }
        }catch (Exception e){
            qrCodeScanInfo = null;
        }
        return qrCodeScanInfo;
    }

    public List<VisitorInfoTemp> executeDataBaseForInitQRcodeVisitorInfo(String driverClass, String jdbcUrl, String username,
                                                                                 String password, String table, FairInfo fairInfo){
        ComboPooledDataSource ds = new ComboPooledDataSource();
        List<VisitorInfoTemp> visitorInfoTempList = new ArrayList<VisitorInfoTemp>();
        try {
            ds.setDriverClass(driverClass);
            ds.setJdbcUrl(jdbcUrl);
            ds.setUser(username);
            ds.setPassword(password);
            ds.setMaxPoolSize(400);
            ds.setInitialPoolSize(50);
            ds.setMaxIdleTime(2000);

            Connection cn = ds.getConnection();
            Statement st = cn.createStatement();

            //根据界面传入的值，先获取t_exhibitor_booth表里所需的字段值
            List<String> conditions = new ArrayList<String>();
            conditions.add(" (convert(varchar, UpdateTime,120) >= '" + fairInfo.getLoad_data_begin_time() + "' and convert(varchar, UpdateTime,120) <= '" + fairInfo.getLoad_data_end_time() + "' ) ");
            String conditionsSql = StringUtils.join(conditions, " and ");
            conditionsSql = " where " + conditionsSql;
            //String sql = "select * from " + table + " where " + field + "='" + fieldvalue + "' and IsDisabled=0";
            String sql = "select * from " + table + conditionsSql;
            ResultSet exhibitorBooth = st.executeQuery(sql);
            while(exhibitorBooth.next()) {
                if(1 != fairInfo.getFairid()){
                    String email = exhibitorBooth.getString(2);
                    String checkingno = exhibitorBooth.getString(3);
                    String firstname = exhibitorBooth.getString(5);
                    String company = exhibitorBooth.getString(8);
                    String mobile = exhibitorBooth.getString(17);
                    VisitorInfoTemp visitorInfoTemp = new VisitorInfoTemp();
                    visitorInfoTemp.setFairid(fairInfo.getFairid());
                    visitorInfoTemp.setCheckingno(checkingno);
                    if(StringUtils.isNotEmpty(email)){
                        visitorInfoTemp.setEmail(email);
                    }
                    if(StringUtils.isNotEmpty(firstname)){
                        visitorInfoTemp.setFirstName(firstname);
                    }
                    if(StringUtils.isNotEmpty(company)){
                        visitorInfoTemp.setCompany(company);
                    }
                    if(StringUtils.isNotEmpty(mobile)){
                        visitorInfoTemp.setMobilePhone(mobile);
                    }
                    visitorInfoTempList.add(visitorInfoTemp);
                }else {
                    String email = exhibitorBooth.getString(2);
                    String checkingno = exhibitorBooth.getString(3);
                    String firstname = exhibitorBooth.getString(6);
                    String company = exhibitorBooth.getString(9);
                    String mobile = exhibitorBooth.getString(17);
                    VisitorInfoTemp visitorInfoTemp = new VisitorInfoTemp();
                    visitorInfoTemp.setFairid(fairInfo.getFairid());
                    visitorInfoTemp.setCheckingno(checkingno);
                    if(StringUtils.isNotEmpty(email)){
                        visitorInfoTemp.setEmail(email);
                    }
                    if(StringUtils.isNotEmpty(firstname)){
                        visitorInfoTemp.setFirstName(firstname);
                    }
                    if(StringUtils.isNotEmpty(company)){
                        visitorInfoTemp.setCompany(company);
                    }
                    if(StringUtils.isNotEmpty(mobile)){
                        visitorInfoTemp.setMobilePhone(mobile);
                    }
                    visitorInfoTempList.add(visitorInfoTemp);
                }
            }
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return visitorInfoTempList;
    }

    public List<VisitorInfoTemp> executeDataBaseForSyncOnSiteRegistrateData(String driverClass, String jdbcUrl, String username,
                                                                         String password, String table, FairInfo fairInfo){
        ComboPooledDataSource ds = new ComboPooledDataSource();
        List<VisitorInfoTemp> visitorInfoTempList = new ArrayList<VisitorInfoTemp>();
        try {
            ds.setDriverClass(driverClass);
            ds.setJdbcUrl(jdbcUrl);
            ds.setUser(username);
            ds.setPassword(password);
            ds.setMaxPoolSize(400);
            ds.setInitialPoolSize(50);
            ds.setMaxIdleTime(2000);

            Connection cn = ds.getConnection();
            Statement st = cn.createStatement();

            //根据界面传入的值，先获取t_exhibitor_booth表里所需的字段值
            List<String> conditions = new ArrayList<String>();
            String syncTime = "";
            if(fairInfo != null && fairInfo.getSync_onsite_start_time() != null && StringUtils.isNotEmpty(fairInfo.getSync_onsite_start_time())){
                syncTime = fairInfo.getSync_onsite_start_time();
            }else{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                syncTime = sdf.format(new Date());
            }
            conditions.add(" (convert(varchar, CreateTime,120) >= '" + syncTime + "' ) ");
            String conditionsSql = StringUtils.join(conditions, " and ");
            conditionsSql = " where " + conditionsSql;
            //String sql = "select * from " + table + " where " + field + "='" + fieldvalue + "' and IsDisabled=0";
            String sql = "select * from " + table + conditionsSql;
            ResultSet exhibitorBooth = st.executeQuery(sql);
            while(exhibitorBooth.next()) {
                if(1 != fairInfo.getFairid()){
                    String email = exhibitorBooth.getString(2);
                    String checkingno = exhibitorBooth.getString(3);
                    String firstname = exhibitorBooth.getString(5);
                    String company = exhibitorBooth.getString(8);
                    String mobile = exhibitorBooth.getString(17);
                    VisitorInfoTemp visitorInfoTemp = new VisitorInfoTemp();
                    visitorInfoTemp.setFairid(fairInfo.getFairid());
                    visitorInfoTemp.setCheckingno(checkingno);
                    if(StringUtils.isNotEmpty(email)){
                        visitorInfoTemp.setEmail(email);
                    }
                    if(StringUtils.isNotEmpty(firstname)){
                        visitorInfoTemp.setFirstName(firstname);
                    }
                    if(StringUtils.isNotEmpty(company)){
                        visitorInfoTemp.setCompany(company);
                    }
                    if(StringUtils.isNotEmpty(mobile)){
                        visitorInfoTemp.setMobilePhone(mobile);
                    }
                    visitorInfoTempList.add(visitorInfoTemp);
                }else {
                    String email = exhibitorBooth.getString(2);
                    String checkingno = exhibitorBooth.getString(3);
                    String firstname = exhibitorBooth.getString(6);
                    String company = exhibitorBooth.getString(9);
                    String mobile = exhibitorBooth.getString(17);
                    VisitorInfoTemp visitorInfoTemp = new VisitorInfoTemp();
                    visitorInfoTemp.setFairid(fairInfo.getFairid());
                    visitorInfoTemp.setCheckingno(checkingno);
                    if(StringUtils.isNotEmpty(email)){
                        visitorInfoTemp.setEmail(email);
                    }
                    if(StringUtils.isNotEmpty(firstname)){
                        visitorInfoTemp.setFirstName(firstname);
                    }
                    if(StringUtils.isNotEmpty(company)){
                        visitorInfoTemp.setCompany(company);
                    }
                    if(StringUtils.isNotEmpty(mobile)){
                        visitorInfoTemp.setMobilePhone(mobile);
                    }
                    visitorInfoTempList.add(visitorInfoTemp);
                }
            }
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return visitorInfoTempList;
    }
}
