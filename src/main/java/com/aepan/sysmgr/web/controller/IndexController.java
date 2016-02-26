package com.aepan.sysmgr.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com._21cn.framework.web.HttpRequestInfo;
import com._21cn.open.common.cipher.StringUtil;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.UserService;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.EncryptUtil;
import com.aepan.sysmgr.web.helper.PermissionHelper;

/**
 * 登陆
 * @author rakika
 * 2015年6月14日下午5:03:13
 */
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
@Controller
public class IndexController {
	//登陆
	private String loginView = "login";
	//首页
	private String indexView = "index";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PermissionHelper permissionHelper;
	@Autowired
	private ConfigService configService;

	/**
	 * 登陆
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, 
			ModelMap model) throws Exception{
		//判断当前session没有过期
		if(request.getSession().getAttribute(Constants.SESSION_USER) != null){
			return "redirect:/user/index";
		}
		//当前session过期
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String userName = reqInfo.getParameter("userName", "").trim();
		String password = reqInfo.getParameter("password", "").trim();
		model.put("userName", userName);
		//参数不为空
        if(!StringUtil.isEmpty(userName) && !StringUtil.isEmpty(password)){
        	List<User> list = userService.findUserByNameAndStatus(userName, Constants.USER_ENABLE);
        	if(list.size() == 0){
        		model.put("error", userName+"用户不存在或尚未审核");
        		return loginView;
        	}
        	if(list.size() > 1){
        		model.put("error", userName+"用户名冲突");
        		return loginView;
        	}
        	User user = list.get(Constants.USER_AUDIT);
        	
        	String pwd = user.getPassWord();
        	String md5pwd = EncryptUtil.getMd5Str(password);
        	if(!pwd.equals(md5pwd)){
        		model.put("error", "密码不正确");
				return loginView;
        	}
        	//把当前用户放入session
			HttpSession session = request.getSession();
			session.setAttribute(Constants.SESSION_USER, user);
			model.clear();	//清除参数，防止参数传递
			
			/*MediaServiceSDK.init("aztsaymediaservice", "ftjscy5977rr0dRopAfUWC5nAd+loZ6MgE+AcSIesTk=");
			StorageServiceSDK.init("picazt",
	 				"xLiYUD1/HyBTsnLNu1qNUgLiJmId/c/nYnIVQQw5i2iGF06Asr0pKLqCVlI42f6hrqdMNo4VCW052kSlU0rOCQ==");
			ConfigManager.getInstance().getFileConfig(configService);*/
			return "redirect:/user/index";
        }
		return loginView;
	}
	
	/**
	 * 后台首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/index")
	public String getZtree(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(Constants.SESSION_USER);
		//查询ztreeNodes数据
		model.put("ztreeNodes", permissionHelper.getZtreeNodesJson(user.getUserName(),request.getContextPath()));
		//查询资源操作权限
		permissionHelper.updateSessionPermission(session);
		return indexView;
	}
	
	/**
	 * 退出
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpSession session = request.getSession();
		session.removeAttribute(Constants.SESSION_USER);
		session.removeAttribute(Constants.SESSION_PERMISSION);
		session.removeAttribute(Constants.SESSION_ZTREENODES);
		return "redirect:/login";
	}
	
}
