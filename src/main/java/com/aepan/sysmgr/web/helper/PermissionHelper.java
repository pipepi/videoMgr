/**
 * 
 */
package com.aepan.sysmgr.web.helper;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com._21cn.framework.util.StringUtil;
import com.aepan.sysmgr.model.MgrPrivilege;
import com.aepan.sysmgr.model.Resource;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.model.ZtreeNode;
import com.aepan.sysmgr.service.PrivilegeService;
import com.aepan.sysmgr.service.ResourceService;
import com.aepan.sysmgr.service.UserService;
import com.aepan.sysmgr.util.Constants;
import com.google.gson.Gson;

/**
 * 权限组建
 * @author rakika
 * 2015年6月15日下午10:19:52
 */
@Component("permissionHelper")
public class PermissionHelper {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private PrivilegeService privilegeService;
	
	/**
	 * 查询用户菜单数据
	 * @param username
	 * @return
	 */
	public String getZtreeNodesJson(String userName,String pre){
		List<Resource> list = resourceService.selectResourceByName(userName);
		if(list != null && !list.isEmpty()){
			List<ZtreeNode> ztreeNodes = new ArrayList<ZtreeNode>();
			for(Resource obj:list){
				ZtreeNode ztreeNode = new ZtreeNode();
				ztreeNode.setId(obj.getId());
				ztreeNode.setpId(obj.getPid());
				ztreeNode.setName(obj.getName());
				ztreeNode.setUrl(pre+obj.getUrl());
				ztreeNodes.add(ztreeNode);
			}
			return new Gson().toJson(ztreeNodes);
		}
		return null;
	}
	
	/**
	 * 更新session资源权限
	 * @param session
	 */
	public void updateSessionPermission(HttpSession session){
		User user = (User)session.getAttribute(Constants.SESSION_USER);
		List<MgrPrivilege> privileges = privilegeService.selectPermissionByUsername(user.getUserName());
		if(privileges != null && !privileges.isEmpty()){
			List<String> permissions = new ArrayList<String>();
			for(MgrPrivilege obj:privileges){
				String url = obj.getUrl();
				String shortcut = obj.getShortcut();
				if(!StringUtil.isEmpty(url) && !StringUtil.isEmpty(shortcut)){
					String permission = url.substring(0, url.lastIndexOf("/"))+"/"+shortcut;
					permissions.add(permission);
				}
			}
			//放入session中
			session.setAttribute(Constants.SESSION_PERMISSION, permissions);
		}
	}
}
