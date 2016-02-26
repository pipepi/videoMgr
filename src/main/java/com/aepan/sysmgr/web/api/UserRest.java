/**
 * 
 */
package com.aepan.sysmgr.web.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.common.resp.JsonResp;
import com.aepan.sysmgr.model.PackageUser;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.service.PackageService;
import com.aepan.sysmgr.service.PackageStatService;
import com.aepan.sysmgr.service.UserService;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.EncryptUtil;
import com.aepan.sysmgr.util.JsonRespCode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 用户Rest
 * @author rakika
 * 2015年8月5日下午3:59:36
 */
@Controller
@RequestMapping( value = "/rest/user" )
public class UserRest {
	
	private static Logger log = LoggerFactory.getLogger(UserRest.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	PackageService packageService;
	
	@Autowired
	PackageStatService packageUserService;
	
	//创建卖家通知
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public String create( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		
        String userName = reqInfo.getParameter("user_name");
        
        String passWord = reqInfo.getParameter("pass_word");
        
        String email = reqInfo.getParameter("email");
        
        if(StringUtils.isBlank(userName)){
        	return responseLog("userName", userName, JsonRespCode.SYSTEM_ERROR, "userName.empty"); 
        }
        if(StringUtils.isBlank(passWord)){
        	return responseLog("passWord", passWord, JsonRespCode.SYSTEM_ERROR, "passWord.empty"); 
        }
        if(StringUtils.isBlank(email)){
        	return responseLog("email", email, JsonRespCode.SYSTEM_ERROR, "email.empty"); 
        }
        if(!email.contains("@")){
        	return responseLog("email", email, JsonRespCode.SYSTEM_ERROR, "email.error"); 
        }        
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        //查询用户和邮箱是否已存在
        ;
        
        if(!userService.isExist(user)){
        	return responseLog("userName&email", email, JsonRespCode.SYSTEM_ERROR, "userName or email already exist"); 
        }
        try {
			user.setPassWord(EncryptUtil.getMd5Str(passWord));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //创建用户
        if(userService.create(user)){
        	return responseLog("User", user.getUserName(), JsonRespCode.SUCCESS, "success!! user id:" + user.getId()); 
        }else{
        	return responseLog("User", user.getUserName(), JsonRespCode.OTHER_FAILD, "create fail"); 
        }
	}
	
	//更新卖家邮箱通知
	@RequestMapping(value = "/updateEmail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public String updateEmail( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		
        String userName = reqInfo.getParameter("user_name");
        
        String passWord = reqInfo.getParameter("pass_word");
        
        String email = reqInfo.getParameter("email");
        
        if(StringUtils.isBlank(userName)){
        	return responseLog("userName", userName, JsonRespCode.SYSTEM_ERROR, "userName.empty"); 
        }
        if(StringUtils.isBlank(passWord)){
        	return responseLog("passWord", passWord, JsonRespCode.SYSTEM_ERROR, "passWord.empty"); 
        }
        if(StringUtils.isBlank(email)){
        	return responseLog("email", email, JsonRespCode.SYSTEM_ERROR, "email.empty"); 
        }
        if(!email.contains("@")){
        	return responseLog("email", email, JsonRespCode.SYSTEM_ERROR, "email.error"); 
        }

        User user = userService.findUserByNameAndPassWord(userName, passWord);
        if(user == null){
        	return responseLog("user", user.getUserName(), JsonRespCode.SYSTEM_ERROR, "user.error"); 
        }
        
        List<User> list = userService.findUserByNameAndStatus(userName, Constants.USER_ENABLE);
        if(list.size() > 0){
           User user1 = list.get(0);
           user1.setEmail(email);
           if(userService.update(user1)){
           	 return responseLog("User", user1.getUserName(), JsonRespCode.SUCCESS, "success"); 
           }
             return responseLog("User", userName, JsonRespCode.OTHER_FAILD, "update fail"); 
        }else{
        	return responseLog("User", userName, JsonRespCode.OTHER_FAILD, "fail! user not exist"); 
        }
	}
	
	//更新卖家套餐状态通知
	@RequestMapping(value = "/updatePackage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public String updatePackage( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		
        String userName = reqInfo.getParameter("user_name");
        
        String email = reqInfo.getParameter("email");
        
        int packageId = reqInfo.getIntParameter("package_id", -1);
        
        if(StringUtils.isBlank(userName)){
        	return responseLog("userName", userName, JsonRespCode.SYSTEM_ERROR, "userName.empty"); 
        }
        if(StringUtils.isBlank(email)){
        	return responseLog("email", email, JsonRespCode.SYSTEM_ERROR, "email.empty"); 
        }
        if(!email.contains("@")){
        	return responseLog("email", email, JsonRespCode.SYSTEM_ERROR, "email.error"); 
        }
        if(packageId == -1){
        	return responseLog("packageId", packageId + "", JsonRespCode.SYSTEM_ERROR, "packageId.empty"); 
        }
        
        List<User> list = userService.findUserByNameAndStatus(userName, Constants.USER_ENABLE);
        if(list.size() > 0){
           User user = list.get(0);
           user.setPackageId(packageId);
//           List<PackageRule> plist = packageRuleService.getByPackageIdList(packageId);
           List<PackageUser> ulist = new ArrayList<PackageUser>();
           
//           for(int i = 0; i < plist.size()  ; i++){
//        	   PackageRule packageRule = plist.get(i);
//               PackageUser packageUser = new PackageUser();
//               packageUser.setUserId(user.getId());
//               packageUser.setName(packageRule.getName());
//               packageUser.setPackageId(packageRule.getPackageId());
//               packageUser.setPackageRuleId(packageRule.getId());
//               packageUser.setType(packageRule.getType());
//               packageUser.setTime(packageRule.getTime());
//               ulist.add(packageUser);
//           }
           
           if(userService.update(user)){
//        	 packageUserService.deleteByUserId(user.getId(), packageId);
//             packageUserService.batchInsert(ulist);
           	 return responseLog("User", user.getUserName(), JsonRespCode.SUCCESS, "packageId update success"); 
           }
             return responseLog("User", userName, JsonRespCode.OTHER_FAILD, "update fail"); 
        }else{
        	return responseLog("User", userName, JsonRespCode.OTHER_FAILD, "fail! user not exist"); 
        }
	}
	
	//查询卖家套餐情况
	/*@RequestMapping(value = "/showUserPackage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public String showUserPackage( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		
        String userName = reqInfo.getParameter("user_name");
        
        String email = reqInfo.getParameter("email");
        
        if(StringUtils.isBlank(userName)){
        	return responseLog("userName", userName, JsonRespCode.SYSTEM_ERROR, "userName.empty"); 
        }
        if(StringUtils.isBlank(email)){
        	return responseLog("email", email, JsonRespCode.SYSTEM_ERROR, "email.empty"); 
        }
        if(!email.contains("@")){
        	return responseLog("email", email, JsonRespCode.SYSTEM_ERROR, "email.error"); 
        }
        
        List<User> list = userService.findUserByNameAndStatus(userName, Constants.USER_ENABLE);
        System.out.println(list.size());
        if(list.size() > 0){
           User user = list.get(0);
           System.out.println("package ID:" + user.getPackageId());
           PackageInfo rpackage = packageService.getById(user.getPackageId());
           System.out.println(rpackage.getId());
           List<PackageUser> plist = packageUserService
        		                     .getByUserIdAndpackageId(user.getId(), rpackage.getId());
           
           if(plist.size() > 0){
           	 return responseLog("User", user.getUserName(), JsonRespCode.SUCCESS,
           			 JSONObject.toJSONString(plist)); 
           }else{
        	   return responseLog("User", userName, JsonRespCode.OTHER_FAILD, "update fail"); 
           }

        }else{
        	return responseLog("User", userName, JsonRespCode.OTHER_FAILD, "fail! user not exist"); 
        }
	}*/
	
	//使用卖家套餐
	@RequestMapping(value = "/useUserPackage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public String useUserPackage( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		
        String userName = reqInfo.getParameter("user_name");
        
        String email = reqInfo.getParameter("email");
        
        int packageRuleId = reqInfo.getIntParameter("package_rule_id", -1);
        
        if(StringUtils.isBlank(userName)){
        	return responseLog("userName", userName, JsonRespCode.SYSTEM_ERROR, "userName.empty"); 
        }
        if(StringUtils.isBlank(email)){
        	return responseLog("email", email, JsonRespCode.SYSTEM_ERROR, "email.empty"); 
        }
        if(!email.contains("@")){
        	return responseLog("email", email, JsonRespCode.SYSTEM_ERROR, "email.error"); 
        }
        if(packageRuleId == -1){
        	return responseLog("packageId", packageRuleId + "", JsonRespCode.SYSTEM_ERROR, "packageRuleId.empty"); 
        }
        
        List<User> list = userService.findUserByNameAndStatus(userName, Constants.USER_ENABLE);
        if(list.size() > 0){
           User user = list.get(0);
//           if(packageUserService.useTimes(user.getId(), packageRuleId)){
//           	 return responseLog("User", user.getUserName(), JsonRespCode.SUCCESS, "packageRuleId use success"); 
//           }
             return responseLog("User", userName, JsonRespCode.OTHER_FAILD, "packageRuleId use fail"); 
        }else{
        	return responseLog("User", userName, JsonRespCode.OTHER_FAILD, "fail! user not exist"); 
        }
	}

	/**
	 * 响应日志
	 * @param buyersId
	 * @param orderId
	 * @param code
	 * @param msgName
	 * @return
	 */
	private String responseLog(String buyersId, String orderId, int code, String msgName) {
		System.out.println("responseLog code:" + code);
	    Gson gson = new GsonBuilder().setDateFormat( "yyyy-MM-dd HH:mm:ss" ).create();
	    JsonResp resp = new JsonResp();
	    
	    resp.setCode(code);
	    resp.setMsg(msgName);
        
        String json = gson.toJson(resp);
	    log.info("buyersId: " + buyersId + ", orderId: " + orderId + ", " + json);
	    return json;
	}
}
