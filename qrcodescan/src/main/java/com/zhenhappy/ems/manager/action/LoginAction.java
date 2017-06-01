package com.zhenhappy.ems.manager.action;

import com.zhenhappy.ems.dto.BaseResponse;
import com.zhenhappy.ems.manager.dto.ManagerPrinciple;
import com.zhenhappy.ems.manager.entity.FairInfo;
import com.zhenhappy.ems.manager.entity.TAdminUser;
import com.zhenhappy.ems.manager.entity.VisitorInfoTemp;
import com.zhenhappy.ems.manager.service.AdminService;
import com.zhenhappy.ems.manager.service.FairInfoService;
import com.zhenhappy.ems.manager.service.QRcodeVisitorInfoService;
import com.zhenhappy.ems.manager.util.JdbcUtils_C3P0;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by wujianbin on 2014-04-22.
 */
@Controller
@RequestMapping(value = "/")
@SessionAttributes(ManagerPrinciple.MANAGERPRINCIPLE)
public class LoginAction extends BaseAction {

    private static Logger log = Logger.getLogger(LoginAction.class);

    @Autowired
    private AdminService adminService;
    @Autowired
    private FairInfoService fairInfoService;
    @Autowired
    private QRcodeVisitorInfoService qRcodeVisitorInfoService;

    /**
     * process login.
     * <p/>
     * if login success,put principle into session.
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseResponse login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            TAdminUser admin = adminService.login(username, password);
            if (admin != null) {
                baseResponse.setResultCode(0);
                ManagerPrinciple principle = new ManagerPrinciple();
                principle.setAdmin(admin);
                request.getSession().setAttribute(ManagerPrinciple.MANAGERPRINCIPLE, principle);

                if(admin.getUser_role() == null || 0 == admin.getUser_role()){
                    baseResponse.setResultCode(0);
                    //表示系统管理员登录，即跳转到后台系统界面
                }else if(1 == admin.getUser_role()){
                    baseResponse.setResultCode(2);
                }

                //登录时，先删掉原有的数据，再同步数据库里的数据
                List<FairInfo> fairInfoList = fairInfoService.loadAllEnableFair();
                if(fairInfoList != null && fairInfoList.size()>0){
                    for(FairInfo fairInfo:fairInfoList){
                        if(1 != fairInfo.getInit_data_flag() && fairInfo.getInit_data_flag() != null){
                            qRcodeVisitorInfoService.deleteAllQRcodeVisitorInfoByFairid(fairInfo.getId());
                            List<VisitorInfoTemp> visitorInfoTempList = initQRcodeVisitorInfoFromDataBase(fairInfo);
                            if(visitorInfoTempList != null && visitorInfoTempList.size()>0){
                                for(VisitorInfoTemp visitorInfoTemp: visitorInfoTempList){
                                    qRcodeVisitorInfoService.saveQRcodeVisitorInfo(visitorInfoTemp);
                                }
                            }
                        }
                        fairInfo.setInit_data_flag(1);
                        fairInfoService.updateFairInfo(fairInfo);
                    }
                }
            } else {
                baseResponse.setResultCode(1);
            }
        } catch (Exception e) {
            log.error("login error.", e);
            baseResponse.setResultCode(1);
        }
        return baseResponse;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "public/login";
    }

    /**
     * logout and remove principle from session.
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute(ManagerPrinciple.MANAGERPRINCIPLE);
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * type：表示展会类型
     *
     * @param fairInfo id  type 1：佛事展   type=2：矿物展    type=3：健康展
     * @return
     */
    public List<VisitorInfoTemp> initQRcodeVisitorInfoFromDataBase(FairInfo fairInfo){
        JdbcUtils_C3P0 jdbcUtils_c3P0 = new JdbcUtils_C3P0();
        if(fairInfo != null){
            String url = fairInfo.getDatabase_url();
            return jdbcUtils_c3P0.executeDataBaseForInitQRcodeVisitorInfo("net.sourceforge.jtds.jdbc.Driver", url, "Jhx03SA",
                    "gogo03Jhx", "visitor_Info", fairInfo);
        }else{
            return null;
        }
        /*String url = "";
        if(1 == fairId){
            url = "jdbc:jtds:sqlserver://10.33.0.224:1433;DatabaseName=foshi";
            return jdbcUtils_c3P0.executeDataBaseForInitQRcodeVisitorInfo("net.sourceforge.jtds.jdbc.Driver", url, "Jhx03SA",
                    "gogo03Jhx", "visitor_Info", fairId);
        }else if(2 == fairId){
            url = "jdbc:jtds:sqlserver://10.33.0.224:1433;DatabaseName=Gemmineralfair";
            return jdbcUtils_c3P0.executeDataBaseForInitQRcodeVisitorInfo("net.sourceforge.jtds.jdbc.Driver", url, "Jhx03SA",
                    "gogo03Jhx", "t_visitor_Info", fairId);
        }else if(3 == fairId){
            url = "jdbc:jtds:sqlserver://10.33.0.224:1433;DatabaseName=Healthfair";
            return jdbcUtils_c3P0.executeDataBaseForInitQRcodeVisitorInfo("net.sourceforge.jtds.jdbc.Driver", url, "Jhx03SA",
                    "gogo03Jhx", "t_visitor_Info", fairId);
        }
        return null;*/
    }
}
