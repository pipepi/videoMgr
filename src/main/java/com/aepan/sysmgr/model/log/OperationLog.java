/**
 * 
 */
package com.aepan.sysmgr.model.log;

import java.io.Serializable;
import java.net.URLEncoder;

import com.aepan.sysmgr.util.Constants;

/**
 * @author lanker
 * 2015年11月16日上午10:34:52
 */
public class OperationLog implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int TYPE_视频 = 1;
	public static final int TYPE_套餐 = 2;
	public static final int TYPE_播放器 = 3;
	private int opType;
	private int shopId;
	private String userName;
	private String pageUrl;
	private String description;
	private String ipAddress;
	public OperationLog() {
	}
	public OperationLog(int opType,int shopId,String userName,String pageUrl,String description,String ipAddress) {
		this.opType = opType;
		this.shopId = shopId;
		this.userName = userName;
		this.pageUrl = pageUrl;
		this.description = description;
		this.ipAddress = ipAddress;
	}
	public int getOpType() {
		return opType;
	}
	public void setOpType(int opType) {
		this.opType = opType;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public String toParams(){
		StringBuffer sb = new StringBuffer();
		try {
		sb.append("{\"Description\":\"").append(URLEncoder.encode(this.description,Constants.URL_ENCODE)).append("\",");
		sb.append("\"IPAddress\":\"").append(this.ipAddress).append("\",");
		sb.append("\"PageUrl\":\"").append(this.pageUrl).append("\",");
		sb.append("\"SellerManagerId\":").append(this.shopId).append(",");
		sb.append("\"UserName\":\"").append(URLEncoder.encode(this.userName,Constants.URL_ENCODE)).append("\"");
		sb.append("}");
		} catch (Exception e) {
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		OperationLog ol = instance();
		System.out.println(ol.toParams());
	}
	public static OperationLog instance(){
		OperationLog ol = new OperationLog();
		ol.opType = 1;
		ol.shopId = 1;
		ol.userName = "张三";
		ol.description = "添加视频";
		ol.ipAddress = "192.168.1.6";
		ol.pageUrl = "/video/videolist";
		return ol;
	}
}
