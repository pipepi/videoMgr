/**
 * 
 */
package com.aepan.sysmgr.dao;

import com.aepan.sysmgr.model.sms.SmsSendLog;

/**
 * @author lanker
 * 2015年9月25日下午3:04:36
 */
public interface SmsDao {

	/**
	 * 查询最后发出的短信
	 * @param phoneNo
	 * @return
	 */
	SmsSendLog getLastByPhoneNo(String phoneNo,int smsType);

	/**
	 * 添加发送短信日志
	 * @param log
	 */
	void add(SmsSendLog log);

	/**
	 * 删除
	 * @param logid
	 */
	void delete(int logid);

}
