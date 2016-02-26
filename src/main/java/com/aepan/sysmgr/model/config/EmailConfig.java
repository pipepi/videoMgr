/**
 * 
 */
package com.aepan.sysmgr.model.config;

import java.io.Serializable;

/**
 * @author lanker
 * 2015年9月26日上午11:54:30
 */
public class EmailConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String url;
	public String apiKey;
	public String apiUser;
	public String apiBatchUser;
	public String from;
	public String fromname;
	//公司邮件logo
	public String logo;
	//公司名称
	public String name;
	//公司电话
	public String tel;
	//公司地址
	public String address;
	//公司邮件
	public String email;
	//公司网址
	public String webSite;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getApiUser() {
		return apiUser;
	}
	public void setApiUser(String apiUser) {
		this.apiUser = apiUser;
	}
	public String getApiBatchUser() {
		return apiBatchUser;
	}
	public void setApiBatchUser(String apiBatchUser) {
		this.apiBatchUser = apiBatchUser;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFromname() {
		return fromname;
	}
	public void setFromname(String fromname) {
		this.fromname = fromname;
	}
	public String getLogo() {
		return logo;
	}
	public String getName() {
		return name;
	}
	public String getTel() {
		return tel;
	}
	public String getAddress() {
		return address;
	}
	public String getEmail() {
		return email;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	@Override
	public String toString() {
		return "EmailConfig [url=" + url + ", apiKey=" + apiKey + ", apiUser="
				+ apiUser + ", apiBatchUser=" + apiBatchUser + ", from=" + from
				+ ", fromname=" + fromname + ", logo=" + logo + ", name="
				+ name + ", tel=" + tel + ", address=" + address + ", email="
				+ email + ", webSite=" + webSite + "]";
	}
	
	
}
