/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.model.PartnerLoginInfo;
import com.aepan.sysmgr.model.Store;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.model._enum.CacheObject;
import com.aepan.sysmgr.model.packageinfo.PackageStat;
import com.aepan.sysmgr.service.CacheService;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.PackageService;
import com.aepan.sysmgr.service.PackageStatService;
import com.aepan.sysmgr.service.UserService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.DateUtil;
import com.aepan.sysmgr.util.EncryptUtil;
import com.aepan.sysmgr.util.StringUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Doris
 * 2015年9月25日上午11:49:43
 */
@Controller
public class PartnerController extends DataTableController{
	
	private static final Logger logger = LoggerFactory.getLogger(PartnerController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private PackageService packageService;
	
	@Autowired
	private PackageStatService packageStatService;
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	CacheService cacheService;
	@RequestMapping(value="/partner/freezeOrActive", method = {RequestMethod.GET,RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public void freezeOrActiveUser(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		String partnerIdStr = request.getParameter("partner_id");//第三房合作方id
		String partnerAccountIdStr = request.getParameter("user_id");//第三方系统唯一账号Id
		String action = request.getParameter("action");//操作  0:冻结;1:激活 
		String sign = request.getParameter("sign");//签名
		String timestamp = request.getParameter("timestamp");//时间戳
		logger.debug("freezeUser partnerId:"+partnerIdStr+",partnerAccountId:"+partnerAccountIdStr+",sign:"+sign);
		
		
		try {
			Date timeStampDate = DateUtil.getDate(timestamp);
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -2);
			
			if(timeStampDate.before(cal.getTime())){
				logger.warn("timeStamp out of date.timestamp:"+timestamp+",cal:"+cal.getTime());
	       		AjaxResponseUtil.returnData(response,  "{\"success\":false, \"msg\":\"timestamp out date !\"}");
	       		return;
			}
			
		} catch (ParseException e1) {
			logger.warn(e1.getMessage(),e1);
       		AjaxResponseUtil.returnData(response,  "{\"success\":false, \"msg\":\"timestamp valid!\"}");
       		return;
		}
		
		
		if(StringUtil.isEmpty(partnerIdStr)||StringUtil.isEmpty(partnerAccountIdStr)){
			logger.warn("partnerId or partnerAccountId is empty.");
       		AjaxResponseUtil.returnData(response,  "{\"success\":false, \"msg\":\"not valid!\"}");
       		return;
		}
		   
		try {
			String md5Sign = EncryptUtil.getMd5Str(partnerAccountIdStr + timestamp);
        	if(!sign.equals(md5Sign)){
        		logger.warn(" sign valid.mysign"+md5Sign+",sign:"+sign+","+sign.equals(md5Sign));	
           		AjaxResponseUtil.returnData(response,  "{\"success\":false, \"msg\":\"sign not valid!\"}");
        		return;
        	}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
       		AjaxResponseUtil.returnData(response,  "{\"success\":false, \"msg\":\"not valid!\"}");
       		return;
		}
			
       	Integer partnerId = Integer.valueOf(partnerIdStr);
       	Integer partnerAccountId = Integer.valueOf(partnerAccountIdStr);
       	
       	User user = userService.partnerLogin(partnerId, partnerAccountId);
       	if(user==null){
			logger.warn("freezeUser failed.,partnerIdStr:"+partnerIdStr+",partnerAccountIdStr:"+partnerAccountIdStr);
			AjaxResponseUtil.returnData(response,  "{\"success\":false, \"msg\":\"freezeUser failed!\"}");
			return;
		}else{
			int status = Integer.parseInt(action);
			user.setStatus(status==0?User.STATUS_FREEZE:User.STATUS_OK);
			userService.update(user);
			//添加或删除用户播放器缓存（索引，redis（to do 需求待定））
			if(user.getStatus()==User.STATUS_OK){
				cacheService.addByUserId(CacheObject.STOREINFO, user.getId());
			}else{
				cacheService.deleteByUserId(CacheObject.STOREINFO, user.getId());
			}
			AjaxResponseUtil.returnData(response,  "{\"success\":true, \"msg\":\"freezeUser userId="+user.getId()+" partnerAccountIdStr="+partnerAccountIdStr+"\"}");
			return;
		}
	}
	@RequestMapping(value="/partner/videoUser", method = {RequestMethod.GET,RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public void videoUser(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		String partnerIdStr = request.getParameter("partner_id");//第三房合作方id
		String partnerAccountIdStr = request.getParameter("user_id");//第三方系统唯一账号Id
		Integer partnerId = Integer.valueOf(partnerIdStr);
       	Integer partnerAccountId = Integer.valueOf(partnerAccountIdStr);
       	User user = userService.partnerLogin(partnerId, partnerAccountId);
       	AjaxResponseUtil.returnData(response, user==null?"{\"success\":false}":"{\"success\":true}");
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/partner/login", method = {RequestMethod.GET,RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public void partnerLogin(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		
		String partnerIdStr = request.getParameter("partner_id");//第三房合作方id
		String partnerAccountIdStr = request.getParameter("user_id");//第三方系统唯一账号Id
		String subUserId = request.getParameter("subuser_id");//第三方系统子账号Id，用于获取商户权限
		String partneraAccountName = request.getParameter("user_name");//商家名
		String memberId= request.getParameter("member_id");//预览播放器时需要使用买家id
		String partnerMobile= request.getParameter("cellphone");
		String sign = request.getParameter("sign");//签名
		String timestamp = request.getParameter("timestamp");//时间戳
		String qqidKey = request.getParameter("qqidkey");
		String ispage = request.getParameter("ispage");
		String email = request.getParameter("email");
		
		HttpSession session = request.getSession();
		logger.debug("partnerId:"+partnerIdStr+",partnerAccountId:"+partnerAccountIdStr+",sign:"+sign+",shop_name:"+partneraAccountName+",email:"+email);
		
		
		try {
			Date timeStampDate = DateUtil.getDate(timestamp);
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -2);
			
			if(timeStampDate.before(cal.getTime())){
				logger.warn("timeStamp out of date.timestamp:"+timestamp+",cal:"+cal.getTime());
	       		AjaxResponseUtil.returnData(response,  "{\"success\":false, \"msg\":\"timestamp out date !\"}");
	       		return;
			}
			
		} catch (ParseException e1) {
			logger.warn(e1.getMessage(),e1);
       		AjaxResponseUtil.returnData(response,  "{\"success\":false, \"msg\":\"timestamp valid!\"}");
       		return;
		}
		
		
		if(StringUtil.isEmpty(partnerIdStr)||StringUtil.isEmpty(partnerAccountIdStr)){
			logger.warn("partnerId or partnerAccountId is empty.");
       		AjaxResponseUtil.returnData(response,  "{\"success\":false, \"msg\":\"not valid!\"}");
       		return;
		}
		   
		try {
			String md5Sign = EncryptUtil.getMd5Str(partnerAccountIdStr + timestamp);
        	if(!sign.equals(md5Sign)){
        		logger.warn(" sign valid.mysign"+md5Sign+",sign:"+sign+","+sign.equals(md5Sign));	
           		AjaxResponseUtil.returnData(response,  "{\"success\":false, \"msg\":\"sign not valid!\"}");
        		return;
        	}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
       		AjaxResponseUtil.returnData(response,  "{\"success\":false, \"msg\":\"not valid!\"}");
       		return;
		}
			
       	Integer partnerId = Integer.valueOf(partnerIdStr);
       	Integer partnerAccountId = Integer.valueOf(partnerAccountIdStr);
       	
       	User user = userService.partnerLogin(partnerId, partnerAccountId);
       	logger.info("loginuser:"+user+",sessionId:"+session.getId());
       	if(user==null){
			user=userService.partnerRegister(partnerId,partnerAccountId,partneraAccountName,partnerMobile,email,qqidKey);
			if(user==null){
				logger.warn("partner register failed.,partnerIdStr:"+partnerIdStr+",partnerAccountIdStr:"+partnerAccountIdStr);
				AjaxResponseUtil.returnData(response,  "{\"success\":false, \"msg\":\"register failed!\"}");
				return;
			}
		}else{
			boolean isUpdateUser=false;
			if(qqidKey!=null&&(!qqidKey.equals(user.getQqidKey()))){
				user.setQqidKey(qqidKey);
				isUpdateUser=true;
				try {
					List<Store> list = storeService.getListByUserId(user.getId());
					for (Store store : list) {
			        	cacheService.delete(CacheObject.STOREINFO, store.getId());
					}
				} catch (Exception e) {
					logger.warn("delete store cache failed:"+user.getId());
					// ignore
				}
				
			}
			if(!StringUtil.isEmpty(partnerMobile)&&(!partnerMobile.equals(user.getPartnerMobile()))){
				user.setPartnerMobile(partnerMobile);
				isUpdateUser=true;
			}
			
			if(isUpdateUser){
				userService.update(user);
			}
		}
       	int userId = user.getId();
       	updateUserPackageStat(user);
       	
       	PartnerLoginInfo info = new PartnerLoginInfo();
       	info.setUserId(userId);
       	info.setPackageId(user.getPackageId());
       	if(User.PARTNER_ADMIN==partnerId){
       		session.setAttribute(Constants.SESSION_ADMIN_USER, user);
       	}else{
       		session.setAttribute(Constants.SESSION_USER, user);
       		session.setAttribute(Constants.SESSION_MEMBERID, memberId);
       		session.setAttribute(Constants.SESSION_SUBUSERID, subUserId);
       	}
		response.addHeader("P3P","CP=CAO PSA OUR");
		model.clear();	//清除参数，防止参数传递
		
		logger.info("finishloginsessionId:"+session.getId()+",userID:"+userId);
		if(ispage!=null&&ispage.equals("1")){
			AjaxResponseUtil.returnData(response, "");
			return;
		}
		AjaxResponseUtil.returnData(response, "{\"success\":true,\"msg\":" + JSONObject.toJSONString(info) + "}");
		
	}

	private void updateUserPackageStat(User user) {
		int userId = user.getId();
		if(user.getPackageId()>0){
       		PackageStat packageStat = packageStatService.getByUserId(userId);
       		logger.debug("loginuser packageStat:"+packageStat);
           	Date packageEndTime = packageStat.getEndTime();
           	Date now = new Date();
           	if(packageEndTime==null||packageEndTime.before(now)){
           		user.setPackageId(0);
           		userService.update(user);
           	}
       	}
	}
	
	
	@RequestMapping(value="/partner/logout", method = {RequestMethod.GET,RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public void loginOut(HttpServletRequest request, HttpServletResponse response){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		int partnerId = reqInfo.getIntAttribute("partner_id");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(Constants.SESSION_USER);
		User adminUser = (User)session.getAttribute(Constants.SESSION_ADMIN_USER);
		response.setHeader("P3P","CP=CAO PSA OUR");
		logger.info("----logout"+",sessionId:"+session.getId()+",user:"+user+",adminUser:"+adminUser);
		if(User.PARTNER_ADMIN==partnerId){
			session.setAttribute(Constants.SESSION_ADMIN_USER, null);
		}else{
			session.setAttribute(Constants.SESSION_USER, null);
		}
		
	}
	
	
	@RequestMapping(value="/partner/heartbeat", method = {RequestMethod.GET,RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public void heartbeat(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(Constants.SESSION_USER);
		User adminUser = (User)session.getAttribute(Constants.SESSION_ADMIN_USER);
		logger.info("----heartbeat sessionId:"+session.getId()+",user:"+user+",adminUser:"+adminUser);
		response.setHeader("P3P","CP=CAO PSA OUR");
		AjaxResponseUtil.returnData(response, "");

	}
	
	@RequestMapping(value="/partner/setsession")
	public String setSession(HttpServletRequest request, HttpServletResponse response,ModelMap model){
		String partnerIndexUrl = request.getParameter("partnerIndexUrl");
		model.addAttribute("partnerIndexUrl", partnerIndexUrl);
		logger.info("--setSession  partnerIndexUrl :"+partnerIndexUrl);
		return "package/setsession";
		
	}
	
	@RequestMapping("/aztsay-h5")
	public String aztsayh5(HttpServletRequest request, HttpServletResponse response, 
			ModelMap model) {
			String userAgent = request.getHeader("user-agent");
			logger.info("---userAgent:"+userAgent);
			//判断当前session没有过期
			return "aztsay-h5/index";
    }

}
