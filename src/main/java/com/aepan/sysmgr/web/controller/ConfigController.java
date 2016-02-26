/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.model.config.*;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.JSONUtil;

/**
 * @author lanekr
 * 2015年8月10日上午11:41:29
 */
@Controller
public class ConfigController extends DataTableController {
	@Autowired
	private ConfigService configService;
	@RequestMapping("/config/init")
	public String init(HttpServletRequest req,HttpServletResponse res,ModelMap model) throws Exception{
		ConfigManager.getInstance().getFileConfig(configService);
		return null;
	}
	@RequestMapping("/config/initby")
	public String initById(HttpServletRequest req,HttpServletResponse res,ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		int id = reqInfo.getIntParameter("id",0);
		if(id>0){
			configService.init(id);
		}
		return null;
	}
	@RequestMapping("/config/page")
	public String page(HttpServletRequest req,HttpServletResponse res,ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		String key = reqInfo.getParameter("key","");
		Config config = null;
		if(key.trim().length()>0){
			 config = configService.query(Config.ID_配置维护key);
		}
		if(config!=null&&key.equals(config.config)){
			ConfigInfo info = configService.getAllConfig();
			model.addAttribute("conf",info);
			return "/conf/edit";
		}else{
			model.addAttribute("msg","have no privilege");
		}
		AjaxResponseUtil.returnData(res, JSONUtil.toJson(model));
		return null;
	}
	@RequestMapping("/config/update")
	public String update(HttpServletRequest req,HttpServletResponse res,ModelMap model) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException{
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		int id = reqInfo.getIntParameter("confid",0);
		String confname = reqInfo.getParameter("confname","");
		String attrName = reqInfo.getParameter("attrname","");
		String value = reqInfo.getParameter("attrvalue","");
		if(id>0&&confname.trim().length()>0&&attrName.trim().length()>0&&value.trim().length()>0){
			Config config = configService.query(id);
			if(id==Config.ID_配置维护key){
				config.config = value;
				configService.update(config);
			}else{
				Object subconfig = JSONUtil.fromJson(config.config, Class.forName("com.aepan.sysmgr.model.config."+confname));
				Field ff = subconfig.getClass().getDeclaredField(attrName);
				ff.setAccessible(true);
				if(ff.getType()==int.class){
					ff.set(subconfig, Integer.parseInt(value));
				}else if(ff.getType()==float.class){
					ff.set(subconfig, Float.parseFloat(value));
				}else if(ff.getType()==double.class){
					ff.set(subconfig, Double.parseDouble(value));
				}else if(ff.getType()==boolean.class){
					ff.set(subconfig, value.endsWith("true")?true:false);
				}else{
					ff.set(subconfig, value);
				}
				config.config = JSONUtil.toJson(subconfig);
				configService.update(config);
				ConfigManager.getInstance().updateCache(id, subconfig);
			}
			model.addAttribute("success", 0);
		}else{
			model.addAttribute("success", 1);
		}
		AjaxResponseUtil.returnData(res, JSONUtil.toJson(model));
		return null;
	}
}
