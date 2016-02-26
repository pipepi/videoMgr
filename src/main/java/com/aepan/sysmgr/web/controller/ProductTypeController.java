/**
 * 
 */
package com.aepan.sysmgr.web.controller;

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
import com.aepan.sysmgr.model.ProductType;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.service.ProductTypeService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.DataTableReturnObject;
import com.alibaba.fastjson.JSONObject;

/**
 * 商品类目管理
 * @author rakika
 * 2015年8月13日下午5:53:36
 */
@Controller
public class ProductTypeController extends DataTableController {

	private static final Logger logger = LoggerFactory.getLogger(ProductTypeController.class);
	
	@Autowired
	ProductTypeService productTypeService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/productType/list")
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		//查询所有的资源
    	PageList<ProductType> productType = productTypeService.getTypeList(model, 1, Integer.MAX_VALUE);
    	model.addAttribute("productType", productType);
    	logger.debug("productType/list");
    	return "/productType/list";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/productType/search")
    public String search(HttpServletRequest request, HttpServletResponse response, ModelMap model){
    	
		setPageSortModel(request, model, new String[]{"", "id", "name"});
		User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
		model.put("userName", user.getUserName());
		logger.info("userName:" + user.getUserName());
    	PageList<ProductType> list = productTypeService.getTypeList(model, 
    			Integer.parseInt(model.get("pageNo").toString()), Integer.parseInt(model.get("pageSize").toString()));
    	logger.info("ProductType:" + list.size());
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
	@RequestMapping("/productType/create")
	public String create(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		return "/productType/form";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/productType/show")
	public String show(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		ProductType productType = productTypeService.getById(reqInfo.getIntParameter("eqId"));
		model.addAttribute("product", productType);
		return "/productType/form";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/productType/save")
	public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
    	ProductType productType = new ProductType();
    	productType.setName(reqInfo.getParameter("name"));
    	productTypeService.save(productType);
		return "redirect:/productType/list.do";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/productType/update")
	public String update(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
    	Integer id = reqInfo.getIntParameter("eqId", -1);
    	if(id > 0){
    		ProductType productType = productTypeService.getById(id);
    		if(productType == null){
                throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
            }
    		productType.setId(id);
    		productType.setName(reqInfo.getParameter("name"));
    		productTypeService.update(productType);
    	}
		return "redirect:/productType/list.do";
	}
}
