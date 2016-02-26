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
import com.aepan.sysmgr.model.Operation;
import com.aepan.sysmgr.service.OperationService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.DataTableReturnObject;
import com.alibaba.fastjson.JSONObject;

/**
 * @author rakika
 * 2015年7月6日下午5:20:17
 */
@Controller
public class OperationController extends DataTableController {

	private static final Logger logger = LoggerFactory.getLogger(OperationController.class);
	
	@Autowired
	OperationService operationService;
	
	/**
	 * 获取list
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/operation/list")
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		List<Operation> list = operationService.getList(model, 1, 50);
		model.put("list", list);
		return "/operation/list";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/operation/search")
	public String search(HttpServletRequest request, HttpServletResponse response, ModelMap model){
        String [] idKey = new String[]{"", "id", "shortcut", "name", "create_time"};
    	logger.info("method: operation search");
		setPageSortModel(request, model, idKey);
    	PageList<Operation> list = operationService.getList(model, Integer.parseInt(model.get("pageNo").toString()), 
    			                             Integer.parseInt(model.get("pageSize").toString()));
    	DataTableReturnObject data = new DataTableReturnObject();
    	data.setResult(list, model.get("sEcho").toString());
    	AjaxResponseUtil.returnData(response, JSONObject.toJSONString(data));
		return null;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/operation/create")
	public String create(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		PageList<Operation> list = operationService.getList(model, 1, 50);
    	model.put("list", list);
    	Operation operation = new Operation();
    	model.addAttribute("sysmgrOperation", operation);
		return "/operation/form";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/operation/save")
	public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		Operation operation = new Operation();
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		operation.setShortcut(reqInfo.getIntParameter("shortcut"));
		operation.setName(reqInfo.getParameter("name"));
		operationService.create(operation);
    	return "redirect:/operation/list.do";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/operation/show")
	public String show(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("eqId", -1);
        if(id > 0){
        	Operation operation = operationService.getById(id);
            if(operation == null){
                throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
            }
        	model.addAttribute("sysmgrOperation", operation);
        }
		return "/operation/form";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/operation/update")
	public String update(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		Integer id = reqInfo.getIntParameter("eqId", -1);
		if(id > 0){
			Operation operation = operationService.getById(id);
			if(operation != null){
				operation.setShortcut(reqInfo.getIntParameter("shortcut"));
				operation.setName(reqInfo.getParameter("name"));
				operationService.update(operation);
			}
		}
    	return "redirect:/operation/list.do";
	}
}
