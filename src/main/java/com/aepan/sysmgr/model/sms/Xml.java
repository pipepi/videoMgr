/**
 * 
 */
package com.aepan.sysmgr.model.sms;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 短信结果根类
 * @author lanker
 * 2015年9月24日下午3:36:15
 */
@XmlRootElement
public class Xml {
	/**
	 * 
	 */
	public Xml() {
		super();
	}
	@XmlAttribute
	public String name;
	@XmlAttribute
	public int result;
	@XmlElement
	public Item Item;	
	@Override
	public String toString(){
		String rsmsg = "";
		switch (result) {
		case 1:rsmsg = "短信发送成功";break;
		case 0:rsmsg = "帐户格式不正确(正确的格式为:员工编号@企业编号)";break;
		case -1:rsmsg = "服务器拒绝(速度过快、限时或绑定IP不对等)如遇速度过快可延时再发";break;
		case -2:rsmsg = "密钥不正确";break;
		case -3:rsmsg = "密钥已锁定";break;
		case -4:rsmsg = "参数不正确(内容和号码不能为空，手机号码数过多，发送时间错误等)";break;
		case -5:rsmsg = "无此帐户";break;
		case -6:rsmsg = "帐户已锁定或已过期";break;
		case -7:rsmsg = "帐户未开启接口发送";break;
		case -8:rsmsg = "不可使用该通道组";break;
		case -9:rsmsg = "帐户余额不足";break;
		case -10:rsmsg = "内部错误";break;
		case -11:rsmsg = "扣费失败";break;
		default:
			break;
		}
		return rsmsg;
	}
}
