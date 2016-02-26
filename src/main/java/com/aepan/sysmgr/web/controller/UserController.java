/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com._21cn.framework.exception.BusinessRuntimeException;
import com._21cn.framework.util.PageList;
import com._21cn.framework.web.HttpRequestInfo;
import com._21cn.open.common.cipher.StringUtil;
import com.aepan.sysmgr.model.PackageUser;
import com.aepan.sysmgr.model.Role;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.model.packageinfo.PackageInfo;
import com.aepan.sysmgr.service.PackageService;
import com.aepan.sysmgr.service.PackageStatService;
import com.aepan.sysmgr.service.RoleService;
import com.aepan.sysmgr.service.UserService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.DataTableReturnObject;
import com.aepan.sysmgr.util.EncryptUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * @author rakika
 * 2015年6月9日下午6:43:49
 * 
 */
@Controller
public class UserController extends DataTableController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PackageService packageService;
	
	@Autowired
	private PackageStatService packageUserService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("userIndex")
	public String userIndex(HttpServletRequest request,
			HttpServletResponse response, Model model){
		logger.info("get all user info ---->/n"); 
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(Constants.SESSION_USER);
//        User user = userService.get(1);
        logger.debug(user.toString()); 
        logger.info("user:" + user.getUserName());
        model.addAttribute("user", user); 
         
        return "/user/user"; 
	}
	
	/**
	 * 获取用户list
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/list")
    public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
    	PageList<User> list = userService.getList(model, 1, Integer.MAX_VALUE);
    	model.put("list", list);
        return "/user/list";
    }
	
	/**
	 * 搜索用户
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/search")
	public String search(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		String [] idKey = new String[]{"", "id", "username", "email", "rolename", "status", "packagename", "create_time"};
    	logger.info("method: user search");
		setPageSortModel(request, model, idKey);
    	PageList<User> list = userService.getList(model, Integer.parseInt(model.get("pageNo").toString()), 
    			                             Integer.parseInt(model.get("pageSize").toString()));
    	DataTableReturnObject data = new DataTableReturnObject();
    	data.setResult(list, model.get("sEcho").toString());
    	AjaxResponseUtil.returnData(response, JSONObject.toJSONString(data));
		return null;
	}
	
	/**
	 * 新增用户
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/create")
	public String create(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		User sysUser = new User();
		model.addAttribute("sysmgrUser", sysUser);
    	PageList<Role> sysmgrRoles = roleService.getList(model, 1, Integer.MAX_VALUE);
    	List<PackageInfo> sysmgrPackages = packageService.getList(PackageInfo.PACKAGE_TYPE_ALL, 1, Integer.MAX_VALUE);
    	model.addAttribute("sysmgrRoles", sysmgrRoles);
    	model.addAttribute("sysmgrPackages", sysmgrPackages);
    	model.addAttribute("psread", true);
    	model.addAttribute("read", false);
		return "/user/form";
	}
	
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/save")
	public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		User sysUser = new User();
	    sysUser.setUserName(reqInfo.getParameter("username"));
	    try {
			sysUser.setPassWord(EncryptUtil.getMd5Str(reqInfo.getParameter("password")));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	    sysUser.setEmail(reqInfo.getParameter("email"));
	    sysUser.setRoleId(reqInfo.getIntParameter("roleId"));
	    sysUser.setStatus(reqInfo.getIntParameter("status"));
	    sysUser.setPackageId(reqInfo.getIntParameter("packageId"));
		if(userService.create(sysUser)){
			 List<User> list = userService.findUserByNameAndStatus(sysUser.getUserName(), Constants.USER_ENABLE);
			 if(list.size() == 1){
				 User user = list.get(0);
				 updateUserPackage(user.getId(), reqInfo.getIntParameter("packageId"));
			 }
		}
		return "redirect:/user/list.do";
	}
	
	/**
	 * 编辑
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/show")
	public String show(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("eqId", -1);
        if(id > 0){
        	User sysUser = userService.get(id);
            if(sysUser == null){
                throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
            }
            model.addAttribute("sysmgrUser", sysUser);
            PageList<Role> sysmgrRoles = roleService.getList(model, 1, Integer.MAX_VALUE);
        	List<PackageInfo> sysmgrPackages = packageService.getList(PackageInfo.PACKAGE_TYPE_ALL, 1, Integer.MAX_VALUE);
        	model.addAttribute("sysmgrRoles", sysmgrRoles);
        	model.addAttribute("sysmgrPackages", sysmgrPackages);
        	model.addAttribute("psread", false);
        	model.addAttribute("read", false);
        }
		return "/user/form";
	}
	
	/**
	 * 审核
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/audit")
	public String audit(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("eqId", -1);
        if(id > 0){
        	User sysUser = userService.get(id);
            if(sysUser == null){
                throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
            }
            model.addAttribute("sysmgrUser", sysUser);
            PageList<Role> sysmgrRoles = roleService.getList(model, 1, Integer.MAX_VALUE);
        	model.addAttribute("sysmgrRoles", sysmgrRoles);
        	model.addAttribute("psread", false);
        	model.addAttribute("read", true);
        }
		return "/user/form";
	}
	
	/**
	 * 更新
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/update")
	public String update(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		Integer id = reqInfo.getIntParameter("eqId", -1);
		logger.info("update id:" + id);
		if(id > 0){
        	User sysUser = userService.get(id);
        	if(sysUser != null){
        		 sysUser.setUserName(reqInfo.getParameter("username"));
			     sysUser.setEmail(reqInfo.getParameter("email"));
			     sysUser.setRoleId(reqInfo.getIntParameter("roleId"));
			     sysUser.setStatus(reqInfo.getIntParameter("status"));
			     sysUser.setPackageId(reqInfo.getIntParameter("packageId"));
			     logger.info("status:" + reqInfo.getIntParameter("status"));
			     sysUser.setId(id);
			     if(userService.update(sysUser)){
			    	 updateUserPackage(id, reqInfo.getIntParameter("packageId"));
			     }
        	}
		}
		return "redirect:/user/list.do";
	}
	
	/**
	 * 用户接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/testRest")
	public String testRest(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		return "/user/testform";
	}
	
	/**
	 * 用户系统间登陆
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/user/syslogin")
	public String usersyslogin(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		//判断当前session没有过期
		if(request.getSession().getAttribute(Constants.SESSION_USER) != null){
			return "redirect:/user/index";
		}
		//当前session过期
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String userName = reqInfo.getParameter("userName", "").trim();
		String mac = reqInfo.getParameter("mac", "").trim();
		model.put("userName", userName);
		//参数不为空
        if(!StringUtil.isEmpty(userName)){
        	List<User> list = userService.findUserByNameAndStatus(userName, Constants.USER_ENABLE);
        	if(list.size() == 0){
        		model.put("error", userName+"用户不存在或尚未审核");
        		return "login";
        	}
        	if(list.size() > 1){
        		model.put("error", userName+"用户名冲突");
        		return "login";
        	}
        	User user = list.get(Constants.USER_AUDIT);
        	
        	String md5mac = EncryptUtil.getMd5Str(userName + "azt");
        	if(!mac.equals(md5mac)){
        		model.put("error", "验证码不正确");
				return "login";
        	}
        	//把当前用户放入session
			HttpSession session = request.getSession();
			session.setAttribute(Constants.SESSION_USER, user);
			model.clear();	//清除参数，防止参数传递
			
			return "redirect:/user/index";
        }
		return "login";
	}
	
	private void updateUserPackage(int userId, int packageId){
//		List<PackageRule> plist = packageRuleService.getByPackageIdList(packageId);
        List<PackageUser> ulist = new ArrayList<PackageUser>();
        
//        for(int i = 0; i < plist.size()  ; i++){
//     	    PackageRule packageRule = plist.get(i);
//            PackageUser packageUser = new PackageUser();
//            packageUser.setUserId(userId);
//            packageUser.setName(packageRule.getName());
//            packageUser.setPackageId(packageRule.getPackageId());
//            packageUser.setPackageRuleId(packageRule.getId());
//            packageUser.setType(packageRule.getType());
//            packageUser.setTime(packageRule.getTime());
//            ulist.add(packageUser);
//        }
        
//        packageUserService.deleteByUserId(userId, packageId);
//        packageUserService.batchInsert(ulist);
	}
}
