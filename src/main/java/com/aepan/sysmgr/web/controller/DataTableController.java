/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.model._enum.ResponseType;
import com.aepan.sysmgr.model._enum.Validate;
import com.aepan.sysmgr.model.config.PartnerConfig;
import com.aepan.sysmgr.model.hm.Privilege;
import com.aepan.sysmgr.model.hm.VideoAuth;
import com.aepan.sysmgr.model.packageinfo.PackageStat;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.PackageService;
import com.aepan.sysmgr.service.PackageStatService;
import com.aepan.sysmgr.service.ProductService;
import com.aepan.sysmgr.service.ProductTypeService;
import com.aepan.sysmgr.service.StoreService;
import com.aepan.sysmgr.service.VideoService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.Constants;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

/**
 * 数据table基类
 * @author rakika
 * 2015年6月21日下午7:42:35
 */
public class DataTableController {
	
	protected Logger log = LoggerFactory.getLogger(DataTableController.class);
	
	@Autowired
	protected ConfigService configService;
	
	@Autowired
	protected PackageStatService packageStatService;
	
	
	@Autowired
	StoreService storeService;
	
	@Autowired
	ProductTypeService productTypeService;
	
	@Autowired
	ProductService productService;
	@Autowired
	PackageService packageService;
	@Autowired
	VideoService videoService;
	
	
	public boolean checkPackageStat(HttpServletRequest req){
		User user = getUser(req);
		int userId = user.getId();
		PackageStat packageStat = packageStatService.getByUserId(userId);
		int  totalFlowNum=Math.round(packageStat.getFlowNum()/1024);
		int usedFlowNum = Math.round(packageStat.getUsedFlowNum()/1024);
		boolean flowEnough=true;
		if(usedFlowNum>=totalFlowNum){
			flowEnough=false;
		}
		Date now = new Date();
		Date endTime = packageStat.getEndTime();
		boolean isOutDate=now.after(endTime);
		
		if((!flowEnough)||isOutDate){
			return false;
		}
		return true;
	}
	
	public boolean hasAdminAuth(HttpServletRequest req,int privilegeCode){
		
		PartnerConfig config = ConfigManager.getInstance().getPartnerConfig(configService);
		String url = config.GET_VIDEOAUTH_URL;
		
		User user = getAdminUser(req);
		
		int partnerAccountId = user.getPartnerAccountId();
		
		PostMethod method = new PostMethod(url);
		method.setRequestBody("{id:"+partnerAccountId+"}");
		method.setRequestHeader("Content-Type", "application/json");
	    
		HttpClient client = new HttpClient();
		try {
			client.executeMethod(method);
			String ret = method.getResponseBodyAsString();
			Gson gson = new Gson();
			VideoAuth videoAuth = gson.fromJson(ret, VideoAuth.class);
			log.debug("id="+partnerAccountId+"  \n"+ videoAuth+"-------------"+ret);
			List<Privilege> authList = videoAuth.getVideoAuthList();
			if(authList!=null&&!authList.isEmpty()){
				for (Privilege privilege : authList) {
					if(privilege.getPrivilegeCode()==privilegeCode&&privilege.isAuth()){
						return true;
					}
				}
			}
		}catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		return false;
	}
	
	public boolean hasSellerAuth(HttpServletRequest req,int privilegeCode){
		HttpSession session = req.getSession();
		String subUserId=(String) session.getAttribute(Constants.SESSION_SUBUSERID);
		
		PartnerConfig config = ConfigManager.getInstance().getPartnerConfig(configService);
		String url = config.GET_SELLERVIDEOAUTH_URL;
		
		
		PostMethod method = new PostMethod(url);
		method.setRequestBody("{id:"+subUserId+"}");
		method.setRequestHeader("Content-Type", "application/json");
	    
		HttpClient client = new HttpClient();
		try {
			client.executeMethod(method);
			String ret = method.getResponseBodyAsString();
			Gson gson = new Gson();
			VideoAuth videoAuth = gson.fromJson(ret, VideoAuth.class);
			log.debug(videoAuth+"-------------"+ret);
			List<Privilege> authList = videoAuth.getVideoAuthList();
			if(authList!=null){
				for (Privilege privilege : authList) {
					if(privilege.getPrivilegeCode()==privilegeCode&&privilege.isAuth()){
						return true;
					}
				}
			}
		}catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		return false;
		
	}
	
	
	
	
	
	
	
	
	protected boolean isLogin(HttpServletRequest req){
		HttpSession session = req.getSession();
		Object user = session.getAttribute(Constants.SESSION_USER);
		log.info("isLoginsessionId:"+session.getId()+",user:"+user);
		if(user!=null){
			return true;
		}
		return false;
	}
	
	
	/**是否是管理员登录*/
	protected boolean isAdminLogin(HttpServletRequest req){
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute(Constants.SESSION_ADMIN_USER);
		log.info("isLoginsessionId:"+session.getId()+",user:"+user);
		if(user!=null){
			if(user.getPartnerId()==2){
				return true;
			}else{
				log.info("isAdminLogin false , partnerId ="+user.getPartnerId()+",user:"+user);
				return false;
			}
		}
		return false;
	}
	
	protected User getUser(HttpServletRequest req){
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute(Constants.SESSION_USER);
		
		return user;
	}
	
	protected User getAdminUser(HttpServletRequest req){
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute(Constants.SESSION_ADMIN_USER);
		
		return user;
	}
	
	/**
	 * 设置分页排序信息排序信息
	 * @param request
	 * @param model
	 * @param cols
	 */
	public void setPageSortModel(HttpServletRequest request, ModelMap model, String[] cols){
		setRequestModelMap(request, model);
		
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		//分页信息
		int iDisplayLength = reqInfo.getIntParameter("iDisplayLength", Constants.PAGE_SIZE);
    	int iDisplayStart = reqInfo.getIntParameter("iDisplayStart", 0);
    	int pageNo = iDisplayStart / iDisplayLength + 1;
    	model.addAttribute("pageNo", pageNo);
    	model.addAttribute("pageSize", iDisplayLength);
    	model.addAttribute("iDisplayStart", iDisplayStart);
    	model.addAttribute("iDisplayLength", iDisplayLength);
    	
    	
    	//排序信息
		int sortCol = reqInfo.getIntParameter("iSortCol_0", 0);
		String sortOrder = reqInfo.getParameter("sSortDir_0", "desc");
		model.addAttribute("sortOrder", sortOrder);
		model.addAttribute("sortName", cols[sortCol]);
	}
	
	/**
	 * 设置请求头MAP
	 * @param request
	 * @param model
	 * @param query
	 * @param jsonEncoding
	 */
	public static void setRequestModelMap(HttpServletRequest request, ModelMap model)
	{
		Map<String, String[]> map = request.getParameterMap();  
        Set<Entry<String, String[]>> set = map.entrySet();  
        Iterator<Entry<String, String[]>> it = set.iterator();  
        while (it.hasNext()) {  
            Entry<String, String[]> entry = it.next();
//            System.out.println("KEY:" + entry.getKey());
            for (String i : entry.getValue()) {
            	if(entry.getKey().equals("sEcho") || entry.getKey().equals("inIdList")
            			|| entry.getKey().equals("inIds") || entry.getKey().equals("eqId")){
                    model.addAttribute(entry.getKey(), i);
            	}
            	if(entry.getKey().equals("eqUsername") || entry.getKey().equals("eqEmail")){
            		model.addAttribute(entry.getKey(), i);
            	}
            	if(entry.getKey().equals("orderId") || entry.getKey().equals("buyer")
                        || entry.getKey().equals("seller") || entry.getKey().equals("stype")){
            		model.addAttribute(entry.getKey(), i);
            	}
//                System.out.println("VALUE:" + i);  
            }  
        }  
	}
	protected String validateResponse(Validate v,ResponseType resType,ModelMap model,HttpServletResponse res){
		if(resType.equals(ResponseType.HTML)){
			String rs = "";
			switch (v) {
			case ALL_NO_LOGIN:rs = "redirect:/login";break;
			case ADMIN_NO_PRIVILEGE:model.put("error_desc", "admin not have privilege.");rs = "error";break;
			case SELLER_NO_PRIVILEGE:model.put("error_desc", "seller Do not have privilege.");rs = "error";break;
			default:break;
			}
			return rs;
		}else if(resType.equals(ResponseType.JSON)){
			switch (v) {
			case ALL_NO_LOGIN:model.put("error_desc", "no login.");break;
			case ADMIN_NO_PRIVILEGE:model.put("error_desc", "admin not have privilege.");break;
			case SELLER_NO_PRIVILEGE:model.put("error_desc", "seller Do not have privilege.");break;
			default:break;
			}
			model.addAttribute("success", false);
			AjaxResponseUtil.returnData(res, JSONObject.toJSONString(model));
			return null;
		}else{
			return null;
		}
	}
	protected Validate validate(HttpServletRequest req){
		Validate rs = Validate.OK;
		if(!isLogin(req)){
			if(!isAdminLogin(req)){
				rs =  Validate.ALL_NO_LOGIN;
			}else{
				if(!hasAdminAuth(req, Constants.PRIVILEGECODE_AUDIT)){
					rs = Validate.ADMIN_NO_PRIVILEGE;
				}
			}
		}else{
			if(!hasSellerAuth(req, Constants.PRIVILEGECODE_SELLERVIDEO)){
				rs = Validate.SELLER_NO_PRIVILEGE;
			}
		}
		return rs;
	}
	protected User getLoginUser(HttpServletRequest req){
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute(Constants.SESSION_USER);
		return user==null?(User)session.getAttribute(Constants.SESSION_ADMIN_USER):user;
	}
}
