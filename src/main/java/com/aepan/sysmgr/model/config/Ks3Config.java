/**
 * 
 */
package com.aepan.sysmgr.model.config;

import java.io.Serializable;
/**
 * 
 * @author AZT
 * 2015年12月30日上午10:54:57
 */
public class Ks3Config implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String accesskeyid="hcd8nl286b7RNCoJygZ/";
	
	public String accesskeysecret="UxP8J1arrNTzFM2pPx/ws6awAErCwPnEAP0HJHnp";
	
	public String notifyUrl="http://aepanzeurus.vicp.cc:80/videoMgr/ksnotify";
	
	public String videoBucketName="videomall";
	
	public String picBucketName="picmall";
	
	private String picUrl="http://picmall.kssws.ks-cdn.com/";

	public String getAccesskeyid() {
		return accesskeyid;
	}

	public void setAccesskeyid(String accesskeyid) {
		this.accesskeyid = accesskeyid;
	}

	public String getAccesskeysecret() {
		return accesskeysecret;
	}

	public void setAccesskeysecret(String accesskeysecret) {
		this.accesskeysecret = accesskeysecret;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getVideoBucketName() {
		return videoBucketName;
	}

	public void setVideoBucketName(String videoBucketName) {
		this.videoBucketName = videoBucketName;
	}

	public String getPicBucketName() {
		return picBucketName;
	}

	public void setPicBucketName(String picBucketName) {
		this.picBucketName = picBucketName;
	}

	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * @param picUrl the picUrl to set
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	
	
	
	
}
