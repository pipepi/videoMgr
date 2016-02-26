/**
 * 
 */
package com.aepan.sysmgr.service;

/**
 * @author Administrator
 * 2015年9月25日下午3:24:25
 */
public interface SmsService {

	/**
	 * 发送短信验证码
	 * @param phoneNo
	 * @param content
	 * @param cs
	 * @return
	 */
	String sendSms4Check(String phoneNo, String content, ConfigService cs);

	/**
	 * 校验短信验证码
	 * @param phoneNo
	 * @param content
	 * @return
	 */
	String checkSms(String phoneNo, String content);

}
