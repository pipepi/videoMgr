/**
 * 
 */
package com.aepan.sysmgr.model.config;

import java.io.Serializable;

/**
 * @author Administrator
 * 2015年9月24日下午4:55:40
 */
public class SmsConfig implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final int TYPE_验证码 = 0;
	public static final int TYPE_非验证码 = 1;
	
	
	public static final String SHOP_FLOW_LESS_THAN_200 ="根据套餐合约的内容，您的账户（%s）流量已少于200M。在此通知您尽快 续费或者升级您的套餐，我们会为您提供充足的缴费时间，期间会保留您所有的商店信息。联系电话：%s";
	public static final String SHOP_VIDEO_PACKAGE_LESS_THAN_30="根据套餐合约的内容，您的账户（%s）将会在30天后暂停服务。在此通知您尽快 续费或者升级您的套餐，我们会为您提供充足的缴费时间，期间会保留您所有的商店信息。联系电话：%s";
	public static final String AUTH_CODE="尊敬的顾客，您本次操作的验证码是%s,请在页面填写验证码完成验证，如非本人操作请忽略该信息或联系客服：%s";
	public String url;
	public String ac;
	public String authkey;
	public String cgid;
	public String csid;
	
	public String url_2;
	public String ac_2;
	public String authkey_2;
	public String cgid_2;
	public String csid_2;
	
	public String oth_url;
	public String oth_ac;
	public String oth_authkey;
	public String oth_cgid;
	public String oth_csid;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAc() {
		return ac;
	}
	public void setAc(String ac) {
		this.ac = ac;
	}
	public String getAuthkey() {
		return authkey;
	}
	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}
	public String getCgid() {
		return cgid;
	}
	public void setCgid(String cgid) {
		this.cgid = cgid;
	}
	public String getCsid() {
		return csid;
	}
	public void setCsid(String csid) {
		this.csid = csid;
	}
	
	public String getUrl_2() {
		return url_2;
	}
	public void setUrl_2(String url_2) {
		this.url_2 = url_2;
	}
	public String getAc_2() {
		return ac_2;
	}
	public void setAc_2(String ac_2) {
		this.ac_2 = ac_2;
	}
	public String getAuthkey_2() {
		return authkey_2;
	}
	public void setAuthkey_2(String authkey_2) {
		this.authkey_2 = authkey_2;
	}
	public String getCgid_2() {
		return cgid_2;
	}
	public void setCgid_2(String cgid_2) {
		this.cgid_2 = cgid_2;
	}
	public String getCsid_2() {
		return csid_2;
	}
	public void setCsid_2(String csid_2) {
		this.csid_2 = csid_2;
	}
	public String getOth_url() {
		return oth_url;
	}
	public void setOth_url(String oth_url) {
		this.oth_url = oth_url;
	}
	public String getOth_ac() {
		return oth_ac;
	}
	public void setOth_ac(String oth_ac) {
		this.oth_ac = oth_ac;
	}
	public String getOth_authkey() {
		return oth_authkey;
	}
	public void setOth_authkey(String oth_authkey) {
		this.oth_authkey = oth_authkey;
	}
	public String getOth_cgid() {
		return oth_cgid;
	}
	public void setOth_cgid(String oth_cgid) {
		this.oth_cgid = oth_cgid;
	}
	public String getOth_csid() {
		return oth_csid;
	}
	public void setOth_csid(String oth_csid) {
		this.oth_csid = oth_csid;
	}

	
	
}
