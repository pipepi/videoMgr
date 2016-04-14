/**
 * 
 */
package com.aepan.sysmgr.web.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.model.Flow;
import com.aepan.sysmgr.model.config.FlowConfig;
import com.aepan.sysmgr.service.FlowService;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.web.api.pay.MD5Util;

/**
 * 流量统计接口
 * @author lanker
 * 2015年12月23日上午11:33:26
 */
@Controller
@RequestMapping( value = "/rest/flow" )
public class FlowRest {
	private static final Logger logger = LoggerFactory.getLogger(FlowRest.class);	
	@Autowired
	private FlowService flowService;
	@RequestMapping(value="/add" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void add(HttpServletRequest request, HttpServletResponse response){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		int userId = reqInfo.getIntParameter("userId", 0);
		int storeId = reqInfo.getIntParameter("storeId", 0);
		int videoId = reqInfo.getIntParameter("videoId", 0);
		float flow = reqInfo.getFloatParameter("flow",0.0f);
		String key = reqInfo.getParameter("key","");//md5(userId+storeId+videoId+flow+flowConfig.authKey, "utf-8")
		if(valiParams(new Integer(userId),
					  new Integer(storeId),
					  new Integer(videoId),
					  new Float(flow),
					  key)){
			if(checkKey(userId, storeId, videoId, flow, key)){
				flowService.add(new Flow(userId, storeId, videoId, flow));
			}
		}else{
			logger.debug("/rest/flow/add error;the params error");
		}
	}
	private boolean checkKey(int userId,int storeId,int videoId,float flow,String key){
		FlowConfig flowConfig = ConfigManager.getInstance().getFlowConfig();
		if(flowConfig==null||!flowConfig.open) {
			logger.debug("/rest/flow/add error;the flowConfig is null or flowConfig door is close. flowConfig="+(flowConfig==null?"null":""+flowConfig.open));
			return false;
		}
		String newkey =MD5Util.MD5Encode(""+userId+storeId+videoId+flow+flowConfig.authKey, "utf-8"); 
		if(!key.equals(newkey)){
			logger.debug("/rest/flow/add error;the key err;input key="+key+",right key="+newkey);
			return false;
		}  
		return true;
	}
	public static void main(String[] args) {
		//System.out.println(MD5Util.MD5Encode(""+1+1+1+1.1+"abdc3936fa6b4c16be4940b3068a8206", "utf-8"));
		System.out.println(MD5Util.MD5Encode("2902016-03-28 16:55:19aztsysmgr_md5", "utf-8"));
	}
	private boolean valiParams(Object... params){
		if(params!=null&&params.length>0){
			for(Object i : params){
				if(i instanceof Integer){
					if(((Integer) i).intValue()<=0) return false;
				}
				if(i instanceof Float){
					if(((Float) i).floatValue()<=0.0f) return false;
				}
				if(i instanceof String){
					if(((String) i).trim().length()<=0) return false;
				}
			}
		}
		return true;
	}
}
