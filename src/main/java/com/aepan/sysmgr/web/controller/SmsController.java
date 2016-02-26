/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;




import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.model.config.SmsConfig;
import com.aepan.sysmgr.model.sms.Xml;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.SmsService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.SmsUtil;
import com.aepan.sysmgr.web.api.pay.MD5Util;

/**
 * 短信接口
 * @author lanker
 * 2015年9月25日下午4:11:48
 */
@Controller
public class SmsController {
	@Autowired
	private SmsService smsService;
	@Autowired
	private ConfigService configService;
	private static final Logger logger = LoggerFactory.getLogger(SmsController.class);
	@RequestMapping("/sms/sendcode")
	public String sendSms4Check(HttpServletRequest req,HttpServletResponse res,ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		String phoneNo = reqInfo.getParameter("phone","");
		String sendTime = reqInfo.getParameter("time","");
		String key = reqInfo.getParameter("key","");//key=md5(phone+time+azt123qet)
		if(!phoneNo.trim().isEmpty()&&
			!sendTime.trim().isEmpty()&&
			!key.trim().isEmpty()){
			Calendar c = Calendar.getInstance(Locale.getDefault());
			c.setTimeInMillis(Long.parseLong(sendTime));
			/*if(DateUtil.diff(new Date(), c.getTime())>SmsSendLog.TIME_发送频率){
				AjaxResponseUtil.returnData(res, "{\"code\":1,\"errormsg\":\"the request had out of time \"}");
				return null;
			}*/
			if(key.equals(MD5Util.MD5Encode(phoneNo+sendTime+"azt123qet","UTF-8"))){
				String content = (new Random().nextInt(900000)+100000)+"";
				String rs = smsService.sendSms4Check(phoneNo, content, configService);
				AjaxResponseUtil.returnData(res, rs);
				return null;
			}
		}
		AjaxResponseUtil.returnData(res, "{\"code\":2,\"errormsg\":\"param error"
				+ " phone="+phoneNo+""
				+ " time="+sendTime
				+ " key="+key
				+ "\"}");
		return null;
	}
	@RequestMapping("/sms/sendmsg")
	public String sendmsg(HttpServletRequest req,HttpServletResponse res,ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		String phoneNo = reqInfo.getParameter("phone","");
		String msg = reqInfo.getParameter("msg","");
		String sendTime = reqInfo.getParameter("time","");
		String key = reqInfo.getParameter("key","");//key=md5(phone+msg+time+azt123qet)
		if(!phoneNo.trim().isEmpty()&&
				!msg.trim().isEmpty()&&
				!sendTime.trim().isEmpty()&&
				!key.trim().isEmpty()){
			if(key.equals(MD5Util.MD5Encode(phoneNo+msg+sendTime+"azt123qet","UTF-8"))){
				String rs = "";
				Xml rsxml = SmsUtil.send(msg, phoneNo, configService,SmsConfig.TYPE_非验证码);
				if(rsxml.result == 1){
					rs = "{\"code\":0}";
				}else{
					rs = "{\"code\":2,\"errormsg\":\""+rsxml.toString()+"\"}";
				}
				AjaxResponseUtil.returnData(res, rs);
			}else{
				logger.debug("input key = "+key +" right key= "+MD5Util.MD5Encode(phoneNo+msg+sendTime+"azt123qet","UTF-8"));
				AjaxResponseUtil.returnData(res, "{\"code\":3,\"errormsg\":\"key error"
						+ " input key="+key
						+ "\"}");
			}
			return null;
			
		}
		AjaxResponseUtil.returnData(res, "{\"code\":1,\"errormsg\":\"param error"
				+ " phone="+phoneNo+""
				+ " msg="+msg
				+ " time="+sendTime
				+ " key="+key
				+ "\"}");
		return null;
		
	}
	@RequestMapping("/sms/checkcode")
	public String checkSms(HttpServletRequest req,HttpServletResponse res,ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		String phoneNo = reqInfo.getParameter("phone","");
		String content = reqInfo.getParameter("content","");
		String checkTime = reqInfo.getParameter("time","");
		String key = reqInfo.getParameter("key","");//key=md5(phone+time+azt123qet)
		if(!phoneNo.trim().isEmpty()&&
			!content.trim().isEmpty()&&
			!checkTime.trim().isEmpty()&&
			!key.trim().isEmpty()){
			Calendar c = Calendar.getInstance(Locale.getDefault());
			c.setTimeInMillis(Long.parseLong(checkTime));
			/*if(DateUtil.diff(new Date(), c.getTime())>SmsSendLog.TIME_发送频率){
				AjaxResponseUtil.returnData(res, "{\"code\":1,\"errormsg\":\"the request had out of time \"}");
				return null;
			}*/
			if(key.equals(MD5Util.MD5Encode(phoneNo+checkTime+"azt123qet","UTF-8"))){
				String rs = smsService.checkSms(phoneNo, content);
				//将校验通过的phone压人当前session
				req.getSession().setAttribute(Constants.SESSION_MOBILE, phoneNo);
				AjaxResponseUtil.returnData(res, rs);
				return null;
			}
		}
		AjaxResponseUtil.returnData(res, "{\"code\":2,\"errormsg\":\"param error"
				+ " phone="+phoneNo+""
				+ " content="+content+""
				+ " time="+checkTime
				+ " key="+key
				+ "\"}");
		return null;
	}
}
