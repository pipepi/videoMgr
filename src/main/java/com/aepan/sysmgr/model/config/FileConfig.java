package com.aepan.sysmgr.model.config;

import java.io.Serializable;

/**
 * @author lanker
 * 2015年8月10日上午11:36:55
 */
public class FileConfig implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public String FILE_LOCAL_DIR;//图片本地目录
	public String IMG_AZURE_DIR;//图片微软云目录
	public String IMG_AZURE_PAY_DIR;//支付图片微软云目录
	public String IMG_AZURE_PRE;//图片微软云地址前缀
	public String IM4JAVA_TOOLPATH;//图片裁剪插件地址
	public String PAY_NOTIFY_URL;
	public int MAX_VIDEO_SIZE;
	public int MAX_IMGS_SIZE;
	public int MAX_IMG_WIDTH;
	public int MIN_IMG_WIDTH;
	public String getFILE_LOCAL_DIR() {
		return FILE_LOCAL_DIR;
	}
	public void setFILE_LOCAL_DIR(String fILE_LOCAL_DIR) {
		FILE_LOCAL_DIR = fILE_LOCAL_DIR;
	}
	public String getIMG_AZURE_DIR() {
		return IMG_AZURE_DIR;
	}
	public void setIMG_AZURE_DIR(String iMG_AZURE_DIR) {
		IMG_AZURE_DIR = iMG_AZURE_DIR;
	}
	public String getIMG_AZURE_PAY_DIR() {
		return IMG_AZURE_PAY_DIR;
	}
	public void setIMG_AZURE_PAY_DIR(String iMG_AZURE_PAY_DIR) {
		IMG_AZURE_PAY_DIR = iMG_AZURE_PAY_DIR;
	}
	public String getIMG_AZURE_PRE() {
		return IMG_AZURE_PRE;
	}
	public void setIMG_AZURE_PRE(String iMG_AZURE_PRE) {
		IMG_AZURE_PRE = iMG_AZURE_PRE;
	}
	public String getIM4JAVA_TOOLPATH() {
		return IM4JAVA_TOOLPATH;
	}
	public void setIM4JAVA_TOOLPATH(String iM4JAVA_TOOLPATH) {
		IM4JAVA_TOOLPATH = iM4JAVA_TOOLPATH;
	}
	public String getPAY_NOTIFY_URL() {
		return PAY_NOTIFY_URL;
	}
	public void setPAY_NOTIFY_URL(String pAY_NOTIFY_URL) {
		PAY_NOTIFY_URL = pAY_NOTIFY_URL;
	}
	public int getMAX_VIDEO_SIZE() {
		return MAX_VIDEO_SIZE;
	}
	public void setMAX_VIDEO_SIZE(int mAX_VIDEO_SIZE) {
		MAX_VIDEO_SIZE = mAX_VIDEO_SIZE;
	}
	public int getMAX_IMGS_SIZE() {
		return MAX_IMGS_SIZE;
	}
	public void setMAX_IMGS_SIZE(int mAX_IMGS_SIZE) {
		MAX_IMGS_SIZE = mAX_IMGS_SIZE;
	}
	public int getMAX_IMG_WIDTH() {
		return MAX_IMG_WIDTH;
	}
	public void setMAX_IMG_WIDTH(int mAX_IMG_WIDTH) {
		MAX_IMG_WIDTH = mAX_IMG_WIDTH;
	}
	public int getMIN_IMG_WIDTH() {
		return MIN_IMG_WIDTH;
	}
	public void setMIN_IMG_WIDTH(int mIN_IMG_WIDTH) {
		MIN_IMG_WIDTH = mIN_IMG_WIDTH;
	}
	
}
