/**
 * 
 */
package com.aepan.sysmgr.string;

import java.net.URLEncoder;
import java.util.Date;

import com._21cn.open.pay.paychannel.tenpay.util.MD5Util;

/**
 * @author Administrator
 * 2016年2月16日上午11:38:17
 */
public class TestSendMsg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String phoneNo = "19679795883";
		String msg=URLEncoder.encode("测试发送消息");
		String time = "";
		Date now = new Date();
		time += now.getTime();
		String key = MD5Util.MD5Encode(phoneNo+"测试发送消息"+time+"azt123qet","UTF-8");
		String url = "/sms/sendmsg?phone="+phoneNo+"&msg="+msg+"&time="+time+"&key="+key;
		System.out.println(url);
		String origin = "15361040981【开卖吧】尊敬的用户:maple_chen，您的订单(2016021796400449)已成功创建.1455679518azt123qet";
		System.out.println(MD5Util.MD5Encode(origin,"UTF-8"));
	}

}
