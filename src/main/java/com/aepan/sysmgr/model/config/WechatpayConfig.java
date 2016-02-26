/**
 * 
 */
package com.aepan.sysmgr.model.config;

import java.io.Serializable;

/**
 * @author doris.zhou
 * 2015年11月24日下午3:23:35
 */
public class WechatpayConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public  String appId="wxdea538ccfee2cb94";
	
	public  String mchId="1252847601";
	
	public  String appSecret ="40517d3595e80ab191bc1729481a8b6e"; 
	
	public  String apiKey="9coooQWRETU20181020PLMIJNVGYUIWQ";

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	

}
