/**
 * 
 */
package com.aepan.sysmgr.model.sms;

import java.io.Serializable;
import java.util.Date;

import com.aepan.sysmgr.web.api.pay.MD5Util;

/**
 * 短信发送日志
 * @author lanker
 * 2015年9月25日下午3:01:03
 */
public class SmsSendLog implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int TIME_发送频率 = 60;//单位秒
	public static final int TIME_验证码有效期 = 1800;//单位秒
	public static final int TYPE_短信验证码 = 1;
	public int id;
	public int type;
	public String phoneNo;
	public String content;
	public Date sendTime;
	public static void main(String[] args) {
		String phone  = "18679795883";
		Date now = new Date();
		String time = now.getTime()+"";
		String content = "123456";
		String sendurl = "http://localhost:8080/videoMgr/sms/sendcode?phone="+phone+"&time="+time+
						"&key="+MD5Util.MD5Encode(phone+time+"azt123qet","UTF-8");
		String checkurl = "http://localhost:8080/videoMgr/sms/checkcode?phone="+phone+"&content="+content+"&time="+time+
				"&key="+MD5Util.MD5Encode(phone+time+"azt123qet","UTF-8");
		System.out.println(sendurl);
		System.out.println(checkurl);
	}
}
