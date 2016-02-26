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
import com._21cn.framework.util.StringUtil;
import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.model.Resource;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.service.ResourceService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.DataTableReturnObject;
import com.alibaba.fastjson.JSONObject;

/**
 * 资源controller
 * @author rakika
 * 2015年6月21日上午10:43:29
 */
@Controller
public class ResourceController extends DataTableController{
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
	
	@Autowired
	ResourceService resourceService;
	
	/**
	 * 获取list
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/resource/list")
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		//判断当前session没有过期
		if(request.getSession().getAttribute(Constants.SESSION_USER) == null){
			logger.info("check : session null");
			return "redirect:/login";
		}
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER);
		List<Resource> list = resourceService.selectResourceByName(user.getUserName());
		model.put("list", list);
		return "/resource/list";
	}
	
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/resource/search")
	public String search(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		String [] idKey = new String[]{"", "s.id", "s.p_id", "s.url", "s.name", "s.status", "s.create_time"};
		setPageSortModel(request, model, idKey);
    	PageList<Resource> list = resourceService.getList(model, Integer.parseInt(model.get("pageNo").toString()), 
    			                             Integer.parseInt(model.get("pageSize").toString()));
    	DataTableReturnObject data = new DataTableReturnObject();
    	data.setResult(list, model.get("sEcho").toString());
    	logger.info(JSONObject.toJSONString(data));
    	AjaxResponseUtil.returnData(response, JSONObject.toJSONString(data));
		return null;
	}
	
	/**
	 * 新建
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/resource/create")
	public String create(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		PageList<Resource> list = resourceService.getList(model, 1, 50);
    	model.put("list", list);
    	Resource resource = new Resource();
    	model.addAttribute("sysResource", resource);
		return "/resource/form";
	}
	
	/**
	 * 新增
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/resource/save")
	public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model){
    	Resource resource = new Resource();
    	resource.setPid(Integer.parseInt(request.getParameter("pId")));
    	resource.setName(request.getParameter("name"));
    	resource.setUrl(request.getParameter("url"));
    	resource.setStatus(Integer.parseInt(request.getParameter("status")));
    	//过滤权限
//    	if(bind(request, resource, model, validator, resourceValidator).hasErrors()){
//    		return formView;
//    	}
    	resourceService.insert(resource);
		return "redirect:/resource/list.do";
	}
	
	/**
	 * 展示修改
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/resource/show")
    public String show(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("eqId", -1);
        if(id > 0){
            Resource resource = resourceService.getById( id );
            if(resource == null){
                throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
            }
            model.addAttribute("sysResource", resource);
            PageList<Resource> list = resourceService.getList(model, 1, 50);
        	model.put("list", list);
        }
        return "/resource/form";
    }
	
	/**
	 * 更新
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/resource/update")
	public String update(HttpServletRequest request, HttpServletResponse response, ModelMap model){
    	HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("id", -1);
        logger.info("update id:" + id);
        if(id > 0){
            Resource resource = resourceService.getById(id);
            if(resource == null){
                throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
            }
            resource.setId(id);
            resource.setPid(reqInfo.getIntParameter("pId", 0));
            resource.setUrl(reqInfo.getParameter("url"));
            resource.setName(reqInfo.getParameter("name"));
            resource.setStatus(reqInfo.getIntParameter("status"));

            //过滤权限
//            if(bind(request, sysmgrResource, model, validator, sysmgrResourceValidator).hasErrors()){
//                return formView;
//            }
            resourceService.update(resource); 
        }
        return "redirect:/resource/list.do";
    }
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/resource/delete")
    public String delete(HttpServletRequest request, HttpServletResponse response, ModelMap model){
    	HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        setRequestModelMap(request, model);
        //遍历objectIds
        String objectIds = getObjectIds(reqInfo, model);
        logger.info("#### eqId ####:" + objectIds);
        if(objectIds.length() > 0){
            resourceService.deleteResourceByMap( model ); 
        }
        return "redirect:/resource/list.do";
    }
	
	/**
	 * 单个删除 or 批量删除
	 * @param reqInfo
	 * @param model
	 * @return
	 */
	private String getObjectIds(HttpRequestInfo reqInfo, ModelMap model) {
        boolean batchParam = false;
        StringBuilder objectIds = new StringBuilder();
        
        if(model.containsKey("inIdList")){
            objectIds.append(model.get("inIdList"));
            logger.info("#### inIdList ####:" + model.get("inIdList"));
            batchParam = true;
        }else if(!StringUtil.isEmpty(reqInfo.getParameter("inIds"))){
            objectIds.append(model.get("inIds"));
            logger.info("#### inIds ####:" + model.get("inIds"));
            batchParam = true;
        }
        
        if( batchParam ){
            model.remove("eqId");
        }else if(!StringUtil.isEmpty(reqInfo.getParameter("eqId"))){
            objectIds.append(model.get("eqId"));
        }
        
        return objectIds.toString();
    }
}
