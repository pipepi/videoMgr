/**
 * 
 */
package com.aepan.sysmgr.model.config;

import java.io.Serializable;

/**
 * @author doris.zhou
 * 2015年11月24日下午3:17:54
 */
public class AlipayConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public  String partner = "2088021209033309";
	// 收款支付宝账号
	public  String sellerEmail = "9cooo@9cooo.com";
	// 商户的私钥
	public  String key = "gxy9rgscf4vev5ibkvxjz1cbnlpjbe9w";
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getSellerEmail() {
		return sellerEmail;
	}
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
}
