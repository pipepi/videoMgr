/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.GenerateCheckCode;
import com.aepan.sysmgr.util.JSONUtil;

/**
 * @author lanker
 * 2015年10月13日上午10:55:16
 */
@Controller
public class CheckCodeController extends DataTableController {
	private static final Logger logger = LoggerFactory.getLogger(CheckCodeController.class);

	private static final String STATIC_MOD_SINCE="Fri, 02 Sep 2011 16:00:38 GMT";
	@RequestMapping("/checkcode/check_code")
	public String checkCode(HttpServletRequest req,HttpServletResponse rsp,ModelMap model){
		Object[] codes = GenerateCheckCode.getCheckCode(66,27,4);
		req.getSession().setAttribute(Constants.SESSION_CHECK_CODE, codes[1].toString());
		byte[] buffer = null;
		ByteArrayInputStream inputStream = (ByteArrayInputStream)codes[0];
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int count = -1;
        while((count = inputStream.read(data,0,1024)) != -1)
            outStream.write(data, 0, count);
        data = null;
        buffer = outStream.toByteArray();
        try {
        	inputStream.close();
			outStream.close();
		} catch (IOException e1) {
			logger.error(e1.getMessage(), e1);
		}
		if(buffer!=null){
			rsp.setHeader("Content-Type", "image/jpg");
			rsp.setHeader("Cache-Control","max-age=604800,public");
			rsp.setHeader("Last-Modified",STATIC_MOD_SINCE);
			rsp.setContentLength(buffer.length);
			try {  
				rsp.getOutputStream().write(buffer);
				if(rsp.getOutputStream()!=null){
					rsp.getOutputStream().close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return null;
	}
	@RequestMapping("/checkcode/vali_code")
	public String valiCheckCode(HttpServletRequest req,HttpServletResponse rsp,ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		String code = reqInfo.getParameter("code","");
		String rightCode = (String)req.getSession().getAttribute(Constants.SESSION_CHECK_CODE);
		rightCode = rightCode==null?"":rightCode;
		if(!code.isEmpty()&&!rightCode.isEmpty()){
			if(code.equals(rightCode)){
				model.addAttribute("status", 0);
				AjaxResponseUtil.returnData(rsp, JSONUtil.toJson(model));
				return null;
			}
		}
		model.addAttribute("status", 1);
		model.addAttribute("errMSG", "验证码错误..");
		AjaxResponseUtil.returnData(rsp, JSONUtil.toJson(model));
		return null;
	}
}
