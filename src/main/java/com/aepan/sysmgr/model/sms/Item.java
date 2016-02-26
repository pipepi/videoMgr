/**
 * 
 */
package com.aepan.sysmgr.model.sms;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * 短信xml结果item封装类
 * @author lanker
 * 2015年9月24日下午3:31:22
 */
public class Item {
	/**
	 * 
	 */
	public Item() {
		super();		
	}
	/**发送短信的企业编号*/
	@XmlAttribute
	public String cid;
	/**发送短信的员工编号*/
	@XmlAttribute
	public String sid;
	/**每次发送的消息编号*/
	@XmlAttribute
	public String msgid;
	/**任务需要的短信条数*/
	@XmlAttribute
	public int total;
	/**任务中每条短信的价格*/
	@XmlAttribute
	public float fee;
	/**本次发送后企业的帐户余额*/
	@XmlAttribute
	public float remain;
	
}
