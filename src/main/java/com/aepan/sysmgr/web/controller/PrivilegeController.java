/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com._21cn.framework.util.PageList;
import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.model.MgrPrivilege;
import com.aepan.sysmgr.model.Operation;
import com.aepan.sysmgr.model.Privilege;
import com.aepan.sysmgr.model.Privileges;
import com.aepan.sysmgr.model.Resource;
import com.aepan.sysmgr.model.Role;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.service.OperationService;
import com.aepan.sysmgr.service.PrivilegeService;
import com.aepan.sysmgr.service.ResourceService;
import com.aepan.sysmgr.service.RoleService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.DataTableReturnObject;
import com.alibaba.fastjson.JSONObject;

/**
 * @author rakika
 * 2015年7月11日上午11:09:57
 */
@Controller
public class PrivilegeController extends DataTableController{

	private static final Logger logger = LoggerFactory.getLogger(PrivilegeController.class);
	
	@Autowired
	ResourceService resourceService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	PrivilegeService privilegeService;
	
	@Autowired
	OperationService operationService;
	
	/**
	 * 获取权限list
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/privilege/list")
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		//查询所有的资源
    	PageList<Resource> sysmgrResources = resourceService.getList(model, 1, Integer.MAX_VALUE);
    	model.addAttribute("sysmgrResources", sysmgrResources);
    	logger.info("sysmgrResources:" + sysmgrResources.size());
    	//查询角色
    	PageList<Role> sysmgrRoles = roleService.getList(model, 1, Integer.MAX_VALUE);
    	model.addAttribute("sysmgrRoles", sysmgrRoles);
    	logger.info("sysmgrRoles:" + sysmgrRoles.size());
    	return "/privilege/list";
	}
	
	/**
	 * 权限搜索
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/privilege/search")
    public String search(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		setPageSortModel(request, model, new String[]{"", "id", "roleName", "resourceName", "operationName"});
		User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
		model.put("userName", user.getUserName());
    	PageList<MgrPrivilege> list = privilegeService.getList(model, 
    			Integer.parseInt(model.get("pageNo").toString()), Integer.parseInt(model.get("pageSize").toString()));
    	DataTableReturnObject data = new DataTableReturnObject();
    	data.setResult(list, model.get("sEcho").toString());
    	AjaxResponseUtil.returnData(response, JSONObject.toJSONString(data));
    	return null;
    }
	
	/**
	 * 配置权限
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/privilege/create")
    public String create(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
    	defaultCreateConfig(request, response, model);
    	return "/privilege/form";
    }
	
	/**
	 * 添加权限
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/privilege/add")
    public String add(HttpServletRequest request, HttpServletResponse response, ModelMap model){
    	defaultAddConfig(request, response, model);
    	return "/privilege/form";
    }
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param privileges
	 * @param result
	 * @return
	 */
	@RequestMapping("/privilege/save")
    public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model, 
    		@ModelAttribute(value="privileges") Privileges privileges, BindingResult result){
		
    	HttpRequestInfo reqInfo = new HttpRequestInfo(request);
    	String operateType = reqInfo.getParameter("operateType", "");
    	   	
    	List<MgrPrivilege> batchList = new ArrayList<MgrPrivilege>();
    	List<Privilege> list = privileges.getPrivilegeList();
    	for(Privilege obj:list){
    		if(obj != null){
    			List<Integer> operationIds = obj.getOperationIds();
    			if(operationIds!=null && ! operationIds.isEmpty()){
    				for(Integer operationId:operationIds){
    					if(operationId!=null && operationId!=0){
    						MgrPrivilege sysmgrPrivilege = new MgrPrivilege();
    						sysmgrPrivilege.setResourceId(obj.getResourceId());
    						sysmgrPrivilege.setOperationId(operationId);
    						sysmgrPrivilege.setRoleId(privileges.getRoleId());
    						batchList.add(sysmgrPrivilege);
    					}
    				}
    			}
    		}
    	}
    	
    	if("create".equals(operateType)){
    		privilegeService.batchUpdatePrivilege(20, batchList, privileges.getRoleId());
    	}else if("add".equals(operateType)){
    		privilegeService.batchInsert(20, batchList, privileges.getRoleId());
    	}
    	return "redirect:/privilege/list.do";
    }
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
    private void defaultCreateConfig(HttpServletRequest request, HttpServletResponse response, ModelMap model){
    	HttpRequestInfo reqInfo = new HttpRequestInfo(request);
    	//查询所有的资源
    	List<Resource> sysmgrResources = resourceService.getList(model, 1, Integer.MAX_VALUE);
    	model.addAttribute("sysmgrResources", sysmgrResources);
    	//查询所有的操作
    	List<Operation> sysmgrOperations = operationService.getList(model, 1, Integer.MAX_VALUE);
    	model.addAttribute("sysmgrOperations", sysmgrOperations);
    	//根据角色查询资源id
    	int roleId = reqInfo.getIntParameter("eqRoleId", 0);
    	String roleName = reqInfo.getParameter("eqRoleName", "");
    	model.put("roleId", roleId);
    	model.put("roleName", roleName);
    	List<Integer> resourceIds = resourceService.selectResourceByRoleId(roleId);
    	model.put("resourceIds", resourceIds);
    	//根据角色查询资源操作
    	List<String> operations = operationService.selectOperationByRoleId(roleId);
    	model.put("operations", operations);
    	//绑定对象
    	Privileges privileges = new Privileges();
    	model.addAttribute("privileges", privileges);
    	model.addAttribute("operateType", "create");
    }
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	private void defaultAddConfig(HttpServletRequest request, HttpServletResponse response, ModelMap model){
    	HttpRequestInfo reqInfo = new HttpRequestInfo(request);
    	int roleId = reqInfo.getIntParameter("eqRoleId", 0);
    	String roleName = reqInfo.getParameter("eqRoleName", "");
    	model.put("roleId", roleId);
    	model.put("roleName", roleName);
    	
    	//查询未增加的资源
    	List<Resource> sysmgrResources = resourceService.selectResourceNotExist(roleId);
    	model.addAttribute("sysmgrResources", sysmgrResources);
    	//查询所有的操作
    	List<Operation> sysmgrOperations = operationService.getList(model, 1, Integer.MAX_VALUE);
    	model.addAttribute("sysmgrOperations", sysmgrOperations);
    	//绑定对象
    	Privileges privileges = new Privileges();
    	model.addAttribute("privileges", privileges);
    	model.addAttribute("operateType", "add");
	}
}
