package com.zhenhappy.ems.manager.action.user;

import com.zhenhappy.ems.dto.BaseResponse;
import com.zhenhappy.ems.dto.Principle;
import com.zhenhappy.ems.entity.WCustomer;
import com.zhenhappy.ems.manager.action.BaseAction;
import com.zhenhappy.ems.manager.dao.QRcodeVisitorInfoDao;
import com.zhenhappy.ems.manager.dao.QrCodeScanDao;
import com.zhenhappy.ems.manager.dto.*;
import com.zhenhappy.ems.manager.entity.*;
import com.zhenhappy.ems.manager.service.*;
import com.zhenhappy.ems.manager.util.JdbcUtils_C3P0;
import com.zhenhappy.util.EmailPattern;
import com.zhenhappy.util.Page;
import com.zhenhappy.util.report.EchartMapResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangxd on 2016/4/5.
 */
@Controller
@RequestMapping(value = "user")
@SessionAttributes(value = ManagerPrinciple.MANAGERPRINCIPLE)
public class QRCodeScanAction extends BaseAction {
    private static Logger log = Logger.getLogger(QRCodeScanAction.class);

    @Autowired
    private CodeScanManagerService codeScanManagerService;
    @Autowired
    private FairInfoService fairInfoService;
    @Autowired
    private ColFieldInfoService colFieldInfoService;
    @Autowired
    private QRcodeVisitorInfoDao qRcodeVisitorInfoDao;
    @Autowired
    private QRcodeVisitorInfoService qRcodeVisitorInfoService;

    /**
     * 同步现场登记数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "syncOnSiteRegistrateData")
    public BaseResponse syncOnSiteRegistrateData() {
        BaseResponse response = new BaseResponse();
        List<FairInfo> fairInfoList = fairInfoService.loadAllEnableFair();
        if(fairInfoList != null && fairInfoList.size()>0){
            for(FairInfo fairInfo:fairInfoList){
                List<VisitorInfoTemp> visitorInfoTempList = initQRcodeVisitorInfoFromDataBase(fairInfo);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                fairInfo.setSync_onsite_start_time(sdf.format(new Date()));
                fairInfoService.updateFairInfo(fairInfo);
                if(visitorInfoTempList != null && visitorInfoTempList.size()>0){
                    for(VisitorInfoTemp visitorInfoTemp: visitorInfoTempList){
                        qRcodeVisitorInfoService.saveQRcodeVisitorInfo(visitorInfoTemp);
                    }
                }
            }
            response.setDescription("现场登记数据同步成功");
            response.setResultCode(0);
        }else{
            response.setDescription("无相关展会数据");
            response.setResultCode(1);
        }
        return response;
    }

    public List<VisitorInfoTemp> initQRcodeVisitorInfoFromDataBase(FairInfo fairInfo){
        JdbcUtils_C3P0 jdbcUtils_c3P0 = new JdbcUtils_C3P0();
        if(fairInfo != null){
            String url = fairInfo.getDatabase_url();
            return jdbcUtils_c3P0.executeDataBaseForSyncOnSiteRegistrateData("net.sourceforge.jtds.jdbc.Driver", url, "Jhx03SA",
                    "gogo03Jhx", "visitor_Info", fairInfo);
        }else{
            return null;
        }
    }

    /**
     * 查询所有已经激活的展会
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "loadAllEnableFairName")
    public List<FairInfo> loadAllEnableFairName() {
        List<FairInfo> fairInfoList = new ArrayList<FairInfo>();
        try {
            fairInfoList = fairInfoService.loadAllEnableFair();
        } catch (Exception e) {
            log.error("query enable fair error.", e);
        }
        return fairInfoList;
    }

    /**
     * 查询所有查询字段
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "loadAllColFieldList")
    public List<ColFieldInfo> loadAllColFieldList() {
        List<ColFieldInfo> colFieldInfoList = new ArrayList<ColFieldInfo>();
        try {
            colFieldInfoList = colFieldInfoService.loadAllColFieldList();
        } catch (Exception e) {
            log.error("query query col field error.", e);
        }
        return colFieldInfoList;
    }

    @ResponseBody
    @RequestMapping(value = "isValidPhone")
    public EchartMapResponse isValidPhone(@RequestParam(value = "phone") String phoneNum) {
        EchartMapResponse response = new EchartMapResponse();
        //http://oa.xicec.com/xicecwx/external/queryWxRegist.aspx
        //{"mobile": "13616027176"}

        JSONObject json = new JSONObject();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost method = new HttpPost("http://oa.xicec.com/xicecwx/external/queryWxRegist.aspx");

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("mobile", phoneNum);

        StringEntity entity = null;//解决中文乱码问题
        try {
            entity = new StringEntity(jsonParam.toString(),"utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            method.setEntity(entity);
            HttpResponse result = httpClient.execute(method);

            //请求结束
            String resData = EntityUtils.toString(result.getEntity());
            JSONObject jsonResult = JSONObject.fromObject(resData);
            for (Iterator iter = jsonResult.keys(); iter.hasNext();) {
                String key = (String)iter.next();
                if(key.equalsIgnoreCase("result") && jsonResult .getString(key).equalsIgnoreCase("1")){
                    QRCodeScanInfo qrCodeScanInfo = codeScanManagerService.loadQRCodeScanInfoByCheckNo(phoneNum);
                    if(qrCodeScanInfo != null) {
                        qrCodeScanInfo.setCount(qrCodeScanInfo.getCount()+1);
                        codeScanManagerService.modifyQRCodeScanInfo(qrCodeScanInfo);
                        response.setDescription(String.valueOf(qrCodeScanInfo.getCount() + 1));
                    }else{
                        qrCodeScanInfo = new QRCodeScanInfo();
                        qrCodeScanInfo.setCount(1);
                        qrCodeScanInfo.setMobilephone(phoneNum);
                        codeScanManagerService.addQRCodeScanInfo(qrCodeScanInfo);
                        response.setDescription("1");
                    }
                    break;
                }
            }
        } catch (Exception e) {
            response.setResultCode(1);
            response.setDescription("无效的预约登记号");
            e.printStackTrace();
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "queryDataReportTable")
    public EchartBarResponse queryDataReportTable() {
        EchartBarResponse response = new EchartBarResponse();
        List<QRCodeScanInfo> qrCodeScanList = codeScanManagerService.loadAllPhoneByPhone();
        EmailPattern pattern = new EmailPattern();
        int phoneCount = 0;
        int netCount = 0;
        if(qrCodeScanList != null && qrCodeScanList.size()>0) {
            for(int i=0;i<qrCodeScanList.size();i++){
                QRCodeScanInfo qrCodeScanInfo = qrCodeScanList.get(i);
                if(qrCodeScanInfo != null){
                    if(pattern.isMobileNO(qrCodeScanInfo.getMobilephone())){
                        phoneCount = phoneCount + qrCodeScanInfo.getCount();
                    } else{
                        netCount = netCount + qrCodeScanInfo.getCount();
                    }
                }
            }
            System.out.println("phoneCount: " + phoneCount + "   ,netCount: " + netCount);
            response.setPhoneType("微信登记");
            response.setPhoneValue(phoneCount);
            response.setNetType("官网登记");
            response.setNetValue(netCount);
            response.setResultCode(0);
        } else {
            response.setResultCode(1);
        }
        return response;
    }

    @RequestMapping(value = "dataReport")
    public ModelAndView directToDataDistribute() {
        ModelAndView modelAndView = new ModelAndView("user/main/datareport");
        return modelAndView;
    }

    /**
     * 测试扫描二维码
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "testPhoneAction")
    public EchartMapResponse testPhoneAction(@RequestParam(value = "phone", defaultValue = "") String phoneNum) {
        EchartMapResponse response = new EchartMapResponse();
        QRCodeScanInfo qrCodeScanInfo = codeScanManagerService.loadQRCodeScanInfoByCheckNo(phoneNum);
        if(qrCodeScanInfo != null) {
            qrCodeScanInfo.setCount(qrCodeScanInfo.getCount()+1);
            codeScanManagerService.modifyQRCodeScanInfo(qrCodeScanInfo);
            response.setDescription(String.valueOf(qrCodeScanInfo.getCount() + 1));
        }else{
            qrCodeScanInfo = new QRCodeScanInfo();
            qrCodeScanInfo.setCount(1);
            qrCodeScanInfo.setMobilephone(phoneNum);
            codeScanManagerService.addQRCodeScanInfo(qrCodeScanInfo);
            response.setDescription("1");
        }
        return response;
    }

    /**
     * 测试扫描二维码
     *
     * @databaseindex 1：佛事展；2：矿物展；3：健康展
     */
    @ResponseBody
    @RequestMapping(value = "queryVisitorInfoByCheckNo")
    public QueryVisitorInfoTempResponse queryVisitorInfoByCheckNo(@RequestParam(value = "checkno") String checkno,
                                                                  @RequestParam(value = "databaseindex") Integer databaseindex,
                                                                  @RequestParam(value = "type") Integer fairId) {
        QueryVisitorInfoTempResponse response = new QueryVisitorInfoTempResponse();

        QueryVisitorRequest request = new QueryVisitorRequest();
        request.setCheckingno(checkno);
        request.setType(databaseindex);
        try{
            response = queryQRcodeVisitorInfoListByPage(request);
            //judgetQRcodeVisitorInfo(checkno, fairId);
        }catch (Exception e){
            e.printStackTrace();
        }
        /*List<VisitorInfoTemp> visitorInfoTempList = getVisitorInfoByExhibitorTypeAndScanCode(databaseindex, checkno, hastakeproceed, request);
        Page page = new Page();
        page.setPageSize(request.getRows());
        page.setPageIndex(request.getPage());
        response.setResultCode(0);
        response.setRows(visitorInfoTempList);
        response.setTotal(page.getTotalCount());*/
        return response;
    }

    private void judgetQRcodeVisitorInfo(String checkno, Integer fairId){
        QRCodeScanInfo qrCodeScanInfoTemp = codeScanManagerService.loadQRCodeScanInfoByCheckNo(checkno);
        VisitorInfoTemp qrcodeVisitorInfo = qRcodeVisitorInfoService.loadQRcodeVisitorInfoByCheckingnoAndFairid(checkno, fairId);
        if(qrCodeScanInfoTemp != null) {
            if(1 == qrCodeScanInfoTemp.getHastakeproceed()){
                qrCodeScanInfoTemp.setCount(qrCodeScanInfoTemp.getCount()==null?1: (qrCodeScanInfoTemp.getCount()+ 1));
                codeScanManagerService.modifyQRCodeScanInfo(qrCodeScanInfoTemp);
                //response.setResultCode(2);
                //response.setDescription("已经领过会刊");
            }else{
                qrCodeScanInfoTemp.setCount(qrCodeScanInfoTemp.getCount()==null?1: (qrCodeScanInfoTemp.getCount()+ 1));
                codeScanManagerService.modifyQRCodeScanInfo(qrCodeScanInfoTemp);
                //response.setResultCode(1);
                //response.setDescription(String.valueOf(qrCodeScanInfoTemp.getCount()));
            }
        }else{
            qrCodeScanInfoTemp = new QRCodeScanInfo();
            qrCodeScanInfoTemp.setFairid(fairId);
            qrCodeScanInfoTemp.setCheckingno(checkno);
            if(qrcodeVisitorInfo != null){
                qrCodeScanInfoTemp.setMobilephone(qrcodeVisitorInfo.getMobilePhone());
            }
            qrCodeScanInfoTemp.setCount(qrCodeScanInfoTemp.getCount()==null?1: (qrCodeScanInfoTemp.getCount()+ 1));
            qrCodeScanInfoTemp.setHastakeproceed(0);
            codeScanManagerService.addQRCodeScanInfo(qrCodeScanInfoTemp);
            //response.setDescription("1");
            //response.setDescription(String.valueOf(qrCodeScanInfoTemp.getCount()));
        }
    }

    /**
     * 测试扫描二维码
     *
     * @databaseindex 1：佛事展；2：矿物展；3：健康展
     */
    @ResponseBody
    @RequestMapping(value = "queryCheckingNoByFieldValue")
    public QueryVisitorInfoTempResponse queryCheckingNoByFieldValue(@ModelAttribute QueryVisitorRequest request,
                                                                    @RequestParam(value = "fieldvalue") String fieldvalue,
                                                                    @RequestParam(value = "databaseindex") Integer fairId,
                                                                    @RequestParam(value = "hastakeproceed", defaultValue = "0") Integer hastakeproceed,
                                                                    @RequestParam(value = "field") String field) {
        QueryVisitorInfoTempResponse response = new QueryVisitorInfoTempResponse();
        List<VisitorInfoTemp> visitorInfoTempList = getVisitorInfoByExhibitorTypeAndField(fairId, fieldvalue, hastakeproceed, field, request);
        Page page = new Page();
        page.setPageSize(request.getRows());
        page.setPageIndex(request.getPage());
        response.setResultCode(0);
        response.setRows(visitorInfoTempList);
        response.setTotal(page.getTotalCount());
        return response;
    }

    private BaseResponse judgetScanCodeEntity(List<QRCodeScanTemp> qrCodeInfoList, Integer hastakeproceed, Integer databaseindex){
        BaseResponse response = new BaseResponse();
        if(qrCodeInfoList != null && qrCodeInfoList.size()>0){
            for(QRCodeScanTemp qrCodeScanInfo:qrCodeInfoList){
                QRCodeScanInfo qrCodeScanInfoTemp = codeScanManagerService.loadQRCodeScanInfoByCheckNo(qrCodeScanInfo.getCheckingno());
                if(qrCodeScanInfoTemp != null) {
                    if(1 == qrCodeScanInfoTemp.getHastakeproceed()){
                        qrCodeScanInfoTemp.setCount(qrCodeScanInfoTemp.getCount()==null?1: (qrCodeScanInfoTemp.getCount()+ 1));
                        response.setResultCode(2);
                        response.setDescription("已经领过会刊");
                    }else{
                        qrCodeScanInfoTemp.setCount(qrCodeScanInfoTemp.getCount()==null?1: (qrCodeScanInfoTemp.getCount()+ 1));
                        codeScanManagerService.modifyQRCodeScanInfo(qrCodeScanInfoTemp);
                        response.setResultCode(1);
                        response.setDescription(String.valueOf(qrCodeScanInfoTemp.getCount()));
                    }
                }else{
                    qrCodeScanInfoTemp = new QRCodeScanInfo();
                    qrCodeScanInfoTemp.setFairid(databaseindex);
                    qrCodeScanInfoTemp.setCheckingno(qrCodeScanInfo.getCheckingno());
                    qrCodeScanInfoTemp.setMobilephone(qrCodeScanInfo.getMobilephone());
                    qrCodeScanInfoTemp.setCount(qrCodeScanInfoTemp.getCount()==null?1: (qrCodeScanInfoTemp.getCount()+ 1));
                    qrCodeScanInfoTemp.setHastakeproceed(hastakeproceed);
                    codeScanManagerService.addQRCodeScanInfo(qrCodeScanInfoTemp);
                    response.setDescription("1");
                    response.setDescription(String.valueOf(qrCodeScanInfoTemp.getCount()));
                }
            }
        }else{
            response.setDescription("无相关记录");
            response.setResultCode(3);
        }
        return response;
    }

    /**
     * type：表示展会类型
     *
     * @param tFairId 1：佛事展   type=2：矿物展    type=3：健康展
     * @return
     */
    public static List<VisitorInfoTemp> getVisitorInfoByExhibitorTypeAndField(Integer tFairId, String fieldvalue, Integer hastakeproceed, String field, QueryVisitorRequest request){
        JdbcUtils_C3P0 jdbcUtils_c3P0 = new JdbcUtils_C3P0();
        String url = "";
        if(1 == tFairId){
            url = "jdbc:jtds:sqlserver://10.33.0.224:1433;DatabaseName=foshi";
            return jdbcUtils_c3P0.executeDataBaseByTypeAndField("net.sourceforge.jtds.jdbc.Driver", url, "Jhx03SA",
                    "gogo03Jhx", "visitor_Info", tFairId, fieldvalue, hastakeproceed, field, request);
        }else if(2 == tFairId){
            url = "jdbc:jtds:sqlserver://10.33.0.224:1433;DatabaseName=Gemmineralfair";
            return jdbcUtils_c3P0.executeDataBaseByTypeAndField("net.sourceforge.jtds.jdbc.Driver", url, "Jhx03SA",
                    "gogo03Jhx", "t_visitor_Info", tFairId, fieldvalue, hastakeproceed, field, request);
        }else if(3 == tFairId){
            url = "jdbc:jtds:sqlserver://10.33.0.224:1433;DatabaseName=Healthfair";
            return jdbcUtils_c3P0.executeDataBaseByTypeAndField("net.sourceforge.jtds.jdbc.Driver", url, "Jhx03SA",
                    "gogo03Jhx", "t_visitor_Info", tFairId, fieldvalue, hastakeproceed, field, request);
        }
        return null;
    }

    /**
     * type：表示展会类型
     *
     * @param tFairId 1：佛事展   type=2：矿物展    type=3：健康展
     * @return
     */
    public static List<VisitorInfoTemp> getVisitorInfoByExhibitorTypeAndScanCode(Integer tFairId, String scancode, Integer hastakeproceed, QueryVisitorRequest request){
        JdbcUtils_C3P0 jdbcUtils_c3P0 = new JdbcUtils_C3P0();
        String url = "";
        if(1 == tFairId){
            url = "jdbc:jtds:sqlserver://10.33.0.224:1433;DatabaseName=foshi";
            return jdbcUtils_c3P0.executeDataBaseByTypeAndScanCode("net.sourceforge.jtds.jdbc.Driver", url, "Jhx03SA",
                    "gogo03Jhx", "visitor_Info", tFairId, scancode, hastakeproceed, request);
        }else if(2 == tFairId){
            url = "jdbc:jtds:sqlserver://10.33.0.224:1433;DatabaseName=Gemmineralfair";
            return jdbcUtils_c3P0.executeDataBaseByTypeAndScanCode("net.sourceforge.jtds.jdbc.Driver", url, "Jhx03SA",
                    "gogo03Jhx", "t_visitor_Info", tFairId, scancode, hastakeproceed, request);
        }else if(3 == tFairId){
            url = "jdbc:jtds:sqlserver://10.33.0.224:1433;DatabaseName=Healthfair";
            return jdbcUtils_c3P0.executeDataBaseByTypeAndScanCode("net.sourceforge.jtds.jdbc.Driver", url, "Jhx03SA",
                    "gogo03Jhx", "t_visitor_Info", tFairId, scancode, hastakeproceed, request);
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "setHastakeproceedByTelphoneValue")
    public BaseResponse setHastakeproceedByTelphoneValue(@RequestParam(value = "phone") String phone) {
        BaseResponse baseResponse = new BaseResponse();
        if(StringUtils.isNotEmpty(phone)){
            QRCodeScanInfo qrCodeScanInfo = codeScanManagerService.loadQRCodeScanInfoByPhone(phone);
            if(qrCodeScanInfo != null){
                qrCodeScanInfo.setHastakeproceed(1);
                codeScanManagerService.modifyQRCodeScanInfo(qrCodeScanInfo);
                baseResponse.setResultCode(0);
                baseResponse.setDescription("会刊领取成功");
            }else{
                baseResponse.setResultCode(1);
                baseResponse.setDescription("无相关的记录");
            }
        }else {
            baseResponse.setResultCode(1);
            baseResponse.setDescription("参数为空");
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "setHastakeproceedByCheckNo")
    public BaseResponse setHastakeproceedByCheckNo(@RequestParam(value = "checkno") String checkno) {
        BaseResponse baseResponse = new BaseResponse();
        if(StringUtils.isNotEmpty(checkno)){
            QRCodeScanInfo qrCodeScanInfo = codeScanManagerService.loadQRCodeScanInfoByCheckNo(checkno);
            if(qrCodeScanInfo != null){
                qrCodeScanInfo.setHastakeproceed(1);
                codeScanManagerService.modifyQRCodeScanInfo(qrCodeScanInfo);
                baseResponse.setResultCode(0);
                baseResponse.setDescription("会刊领取成功");
            }else{
                baseResponse.setResultCode(1);
                baseResponse.setDescription("无相关的记录");
            }
        }else {
            baseResponse.setResultCode(1);
            baseResponse.setDescription("参数为空");
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "getShowQRCodeVisitorInfoByCheckNo")
    public ShowQRCodeVisitorInfoResponse getShowQRCodeVisitorInfoByCheckNo(@RequestParam(value = "checkno") String checkno,
                                                                           @RequestParam(value = "fairId") Integer fairId) {
        ShowQRCodeVisitorInfoResponse baseResponse = new ShowQRCodeVisitorInfoResponse();
        if(StringUtils.isNotEmpty(checkno)){
            QRCodeScanInfo qrCodeScanInfo = codeScanManagerService.loadQRCodeScanInfoByCheckNo(checkno);

            if(qrCodeScanInfo != null) {
                if(1 == qrCodeScanInfo.getHastakeproceed()){
                    qrCodeScanInfo.setCount(qrCodeScanInfo.getCount()==null?1: (qrCodeScanInfo.getCount()+ 1));
                    codeScanManagerService.modifyQRCodeScanInfo(qrCodeScanInfo);
                }else{
                    qrCodeScanInfo.setCount(qrCodeScanInfo.getCount()==null?1: (qrCodeScanInfo.getCount()+ 1));
                    codeScanManagerService.modifyQRCodeScanInfo(qrCodeScanInfo);
                }
            }else{
                qrCodeScanInfo = new QRCodeScanInfo();
                qrCodeScanInfo.setFairid(fairId);
                qrCodeScanInfo.setCheckingno(checkno);
                qrCodeScanInfo.setCount(1);
                qrCodeScanInfo.setHastakeproceed(0);
                codeScanManagerService.addQRCodeScanInfo(qrCodeScanInfo);
            }
            baseResponse.setCheckingno(checkno);
            baseResponse.setScanNum(qrCodeScanInfo.getCount());
            baseResponse.setTakeFlag(qrCodeScanInfo.getHastakeproceed());
            VisitorInfoTemp visitorInfoTemp = qRcodeVisitorInfoService.loadQRcodeVisitorInfoByCheckingnoAndFairid(checkno, fairId);
            if(visitorInfoTemp != null){
                baseResponse.setFirstname(visitorInfoTemp.getFirstName());
                baseResponse.setCompany(visitorInfoTemp.getCompany());
            }
            baseResponse.setResultCode(0);
        }else {
            baseResponse.setResultCode(1);
            baseResponse.setDescription("无对应记录");
        }
        return baseResponse;
    }

    /**
     * 分页对应客商列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryCustomerListByFairIdAndPage")
    public QueryVisitorInfoTempResponse queryCustomerListByFairIdAndPage(@ModelAttribute QueryVisitorRequest request) {
        QueryVisitorInfoTempResponse response = new QueryVisitorInfoTempResponse();
        try {

            /*List<VisitorInfoTemp> visitorInfoTempList = getVisitorInfoByExhibitorTypeAndField(request);
            Page page = new Page();
            page.setPageSize(request.getRows());
            page.setPageIndex(request.getPage());
            response.setResultCode(0);
            response.setRows(visitorInfoTempList);
            response.setTotal(page.getTotalCount());*/
            response = queryQRcodeVisitorInfoListByPage(request);
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query customer list info error.", e);
        }
        return response;
    }

    @Transactional
    public QueryVisitorInfoTempResponse queryQRcodeVisitorInfoListByPage(QueryVisitorRequest request) throws UnsupportedEncodingException {
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
        conditions.add(" fairid = " + request.getType());
        String conditionsSql = StringUtils.join(conditions, " and ");
        String conditionsSqlNoOrder = "";
        if(StringUtils.isNotEmpty(conditionsSql)){
            conditionsSqlNoOrder = " where " + conditionsSql;
        }
        Page page = new Page();
        page.setPageSize(request.getRows());
        page.setPageIndex(request.getPage());
        List<VisitorInfoTemp> qrcodeVisitorInfoList = qRcodeVisitorInfoDao.queryPageByHQL("select count(*) from VisitorInfoTemp " + conditionsSqlNoOrder,
                "select new com.zhenhappy.ems.manager.entity.QRcodeVisitorInfo(id, firstName, email, checkingno, company, mobilePhone) "
                        + "from VisitorInfoTemp " + conditionsSqlNoOrder, new Object[]{}, page);
        QueryVisitorInfoTempResponse response = new QueryVisitorInfoTempResponse();
        response.setResultCode(0);
        response.setRows(qrcodeVisitorInfoList);
        response.setTotal(page.getTotalCount());
        return response;
    }

    /**
     * type：表示展会类型
     *
     * @param request  type 1：佛事展   type=2：矿物展    type=3：健康展
     * @return
     */
    public static List<VisitorInfoTemp> getVisitorInfoByExhibitorTypeAndField(QueryVisitorRequest request){
        JdbcUtils_C3P0 jdbcUtils_c3P0 = new JdbcUtils_C3P0();
        String url = "";
        if(1 == request.getType()){
            url = "jdbc:jtds:sqlserver://10.33.0.224:1433;DatabaseName=foshi";
            return jdbcUtils_c3P0.executeDataBaseByTypeAndFieldForVisitorInfoTemp("net.sourceforge.jtds.jdbc.Driver", url, "Jhx03SA",
                    "gogo03Jhx", "visitor_Info", request);
        }else if(2 == request.getType()){
            url = "jdbc:jtds:sqlserver://10.33.0.224:1433;DatabaseName=Gemmineralfair";
            return jdbcUtils_c3P0.executeDataBaseByTypeAndFieldForVisitorInfoTemp("net.sourceforge.jtds.jdbc.Driver", url, "Jhx03SA",
                    "gogo03Jhx", "t_visitor_Info", request);
        }else if(3 == request.getType()){
            url = "jdbc:jtds:sqlserver://10.33.0.224:1433;DatabaseName=Healthfair";
            return jdbcUtils_c3P0.executeDataBaseByTypeAndFieldForVisitorInfoTemp("net.sourceforge.jtds.jdbc.Driver", url, "Jhx03SA",
                    "gogo03Jhx", "t_visitor_Info", request);
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "getScanNumByCheckingNo")
    public QueryCustomerScancodeResponse getScanNumByCheckingNo(@ModelAttribute QueryVisitorByCheckingNoRequest request) {
        QueryCustomerScancodeResponse baseResponse = new QueryCustomerScancodeResponse();
        if(StringUtils.isNotEmpty(request.getCheckingno())){
            QRCodeScanInfo qrCodeScanInfo = codeScanManagerService.loadQRCodeScanInfoByCheckNo(request.getCheckingno());
            //QRCodeScanInfo qrCodeScanInfo = getQRCodeScanInfoByCheckingNo(request.getCheckingno());
            if(qrCodeScanInfo != null){
                //qrCodeScanInfo.setHastakeproceed(1);
                //codeScanManagerService.modifyPhone(qrCodeScanInfo);
                baseResponse.setScannum(qrCodeScanInfo.getCount());
                baseResponse.setTakeFileFlag(qrCodeScanInfo.getHastakeproceed());
                baseResponse.setResultCode(0);
            }else{
                baseResponse.setResultCode(1);
                baseResponse.setDescription("无相关的记录");
            }
        }else {
            baseResponse.setResultCode(1);
            baseResponse.setDescription("参数为空");
        }
        return baseResponse;
    }

    /**
     *
     * @param checkingno
     * @return
     */
    public static QRCodeScanInfo getQRCodeScanInfoByCheckingNo(String checkingno){
        JdbcUtils_C3P0 jdbcUtils_c3P0 = new JdbcUtils_C3P0();
        String url = "";
        url = "jdbc:jtds:sqlserver://10.33.0.224:1433;DatabaseName=QRcodeInfo";
        return jdbcUtils_c3P0.getQRCodeScanInfoByCheckingNo("net.sourceforge.jtds.jdbc.Driver", url, "Jhx03SA",
                "gogo03Jhx", "code_scan_info", checkingno);
    }

    @RequestMapping(value = "recognitioncardpage", method = RequestMethod.GET)
    public ModelAndView redirectRecognitionCardPage(HttpServletRequest request, Locale locale) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user/main/recognitioncard");
        //在这里进行保存操作
        return modelAndView;
    }

    //************************************************后台管理员相关功能*******************
    /**
     * redirect to project list page.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "admin/setpage", method = RequestMethod.GET)
    public ModelAndView redirectAdminProjectList(HttpServletRequest request, Locale locale) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user/main/reset_view");
        return modelAndView;
    }

    /**
     * 分页对应客商列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryResetViewInfoByPage")
    public QueryVisitorInfoTempResponse queryResetViewInfoByPage(@ModelAttribute QueryVisitorRequest request) {
        QueryVisitorInfoTempResponse response = new QueryVisitorInfoTempResponse();
        try {
            List<FairInfo> fairInfoList = fairInfoService.loadAllFair();
            Page page = new Page();
            page.setPageSize(request.getRows());
            page.setPageIndex(request.getPage());
            response.setResultCode(0);
            response.setRows(fairInfoList);
            response.setTotal(page.getTotalCount());
        } catch (Exception e) {
            response.setResultCode(1);
            log.error("query fair list info error.", e);
        }
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "modifyFairInfo", method = RequestMethod.POST)
    public BaseResponse modifyFairInfo(@ModelAttribute ModifyFairInfoRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            if(request.getFairId() != null && request.getFairId() != -1){
                FairInfo fairInfo = fairInfoService.loadFairInfoByFairId(request.getFairId());
                if(fairInfo != null){
                    fairInfo.setFairname(request.getFairname());
                    fairInfo.setFairalairname(request.getFairalairname());
                    fairInfo.setFairbegindate(sdf.parse(request.getBegintime()));
                    fairInfo.setFairenddate(sdf.parse(request.getEndtime()));
                    fairInfo.setFairenable(request.getFairenable());
                    fairInfo.setInit_data_flag(request.getFairinit());
                    fairInfo.setDatabase_url(request.getDatabase_url());
                    fairInfo.setLoad_data_begin_time(request.getLoad_data_begin_time());
                    fairInfo.setLoad_data_end_time(request.getLoad_data_end_time());
                    fairInfo.setSync_onsite_start_time(request.getSync_onsite_start_time());
                    fairInfoService.updateFairInfo(fairInfo);
                }else{
                    response.setResultCode(1);
                    response.setDescription("无对应的展会数据！");
                }
            }else if(request.getFairId() == -1){
                FairInfo fairInfo = new FairInfo();
                fairInfo.setFairname(request.getFairname());
                fairInfo.setFairalairname(request.getFairalairname());
                fairInfo.setFairbegindate(sdf.parse(request.getBegintime()));
                fairInfo.setFairenddate(sdf.parse(request.getEndtime()));
                fairInfo.setFairenable(request.getFairenable());
                fairInfo.setInit_data_flag(request.getFairinit());
                fairInfo.setDatabase_url(request.getDatabase_url());
                fairInfoService.saveFairInfo(fairInfo);
                fairInfo.setFairid(fairInfo.getId());
                fairInfoService.updateFairInfo(fairInfo);
            }
        } catch (Exception e) {
            log.error("modify fair info error.", e);
            response.setResultCode(1);
        }
        return response;
    }
}
