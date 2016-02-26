/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.EmailUtil;
import com.aepan.sysmgr.web.api.pay.MD5Util;

/**
 * @author lanker
 * 2015年11月26日下午2:50:55
 */
@Controller
public class EmailController extends DataTableController {
	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
	@Autowired
	private ConfigService cs;
	@RequestMapping("/email/send")
	public String sendEmailTriggle(HttpServletRequest req,HttpServletResponse res,ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		int type = reqInfo.getIntParameter("type",0);//邮件类型 1:触发  2:批量
		String var = reqInfo.getParameter("var","");//参数json穿"{\"to\": [\"65479854@qq.com\"],\"sub\":{\"%phone%\": [\"18612347894\"],\"%orderid%\":[\"23164564\"]}}"
		String template = reqInfo.getParameter("template","");//模版名称
		String subject = reqInfo.getParameter("subject","");//标题
		String sendTime = reqInfo.getParameter("time","");
		String key = reqInfo.getParameter("key","");//key=md5(template+time+azt123qet)
		if(type>0&&
			!var.trim().isEmpty()&&
			!template.trim().isEmpty()&&
			!subject.trim().isEmpty()&&
			!sendTime.trim().isEmpty()&&
			!key.trim().isEmpty()){
				Calendar c = Calendar.getInstance(Locale.getDefault());
				c.setTimeInMillis(Long.parseLong(sendTime));
				if(key.equals(MD5Util.MD5Encode(template+sendTime+"azt123qet","UTF-8"))){
					try {
						EmailUtil.sendMailTemplate(type, var, template, subject, cs);
					} catch (ClientProtocolException e) {
						logger.error(e.getMessage(),e);
						AjaxResponseUtil.returnData(res, "{\"code\":2}");//发送异常ClientProtocolException
						return null;
					} catch (IOException e) {
						logger.error(e.getMessage(),e);
						AjaxResponseUtil.returnData(res, "{\"code\":3}");//发送异常IOException
						return null;
					}
					AjaxResponseUtil.returnData(res, "{\"code\":0}");//发送成功
					return null;
				}else{
					AjaxResponseUtil.returnData(res, "{\"code\":4}");//校验码异常
					return null;
				}
			}
		AjaxResponseUtil.returnData(res, "{\"code\":1}");//参数异常
		return null;
	}
}
