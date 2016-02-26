/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com._21cn.framework.exception.BusinessRuntimeException;
import com._21cn.framework.util.PageList;
import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.model.Role;
import com.aepan.sysmgr.service.RoleService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.DataTableReturnObject;
import com.alibaba.fastjson.JSONObject;

/**
 * 角色controller
 * @author rakika
 * 2015年6月21日下午7:57:02
 */
@Controller
public class RoleController extends DataTableController {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	RoleService roleService;
	
	/**
	 * 获取角色list
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/role/list")
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		//判断当前session没有过期
		if(request.getSession().getAttribute(Constants.SESSION_USER) == null){
			logger.info("check : session null");
			return "redirect:/login";
		}
		List<Role> list = roleService.getList(model, 1, 50);
		model.put("list", list);
		return "/role/list";
	}
	
	/**
	 * 搜索角色
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/role/search")
	public String search(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		String [] idKey = new String[]{"", "id", "name", "type", "createTime"};
    	logger.info("method: role search");
		setPageSortModel(request, model, idKey);
    	PageList<Role> list = roleService.getList(model, Integer.parseInt(model.get("pageNo").toString()), 
    			                             Integer.parseInt(model.get("pageSize").toString()));
    	DataTableReturnObject data = new DataTableReturnObject();
    	data.setResult(list, model.get("sEcho").toString());
    	AjaxResponseUtil.returnData(response, JSONObject.toJSONString(data));
		return null;
	}
	
	/**
	 * 新建角色
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/role/create")
	public String create(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		PageList<Role> list = roleService.getList(model, 1, Integer.MAX_VALUE);
    	model.put("list", list);
    	Role role = new Role();
    	model.addAttribute("sysmgrResource", role);
		return "/role/form";
	}
	
	/**
	 * 编辑
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/role/show")
	public String show(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("eqId", -1);
        if(id > 0){
        	Role role = roleService.getById(id);
            if(role == null){
                throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
            }
        	model.addAttribute("sysmgrRole", role);
        }
		return "/role/form";
	}
	
	/**
	 * 保存角色
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/role/save")
	public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		Role role = new Role();
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		role.setName(reqInfo.getParameter("name"));
		role.setType(reqInfo.getIntParameter("type"));
    	roleService.create(role);
    	return "redirect:/role/list.do";
	}
	
	/**
	 * 更新角色
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/role/update")
	public String update(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		Integer id = reqInfo.getIntParameter("eqId", -1);
		if(id > 0){
			Role role = roleService.getById(id);
			if(role != null){
				role.setName(reqInfo.getParameter("name"));
				role.setType(reqInfo.getIntParameter("type"));
				roleService.update(role);
			}
		}
    	return "redirect:/role/list.do";
	}
}
