/**
 * 
 */
package com.aepan.sysmgr.model.config;

import java.io.Serializable;

/**
 * @author lanker
 * 2015年8月10日上午10:44:20
 */
public class Config implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int ID_文件上传配置 = 1;
	public static final int ID_短信配置 = 2;
	public static final int ID_邮件配置 = 3;
	public static final int ID_合作方地址配置 = 4;
	public static final int ID_搜索引擎 = 5;
	
	
	public static final int ID_支付宝配置 = 6;
	public static final int ID_微信配置 = 7;
	public static final int ID_微软配置 = 8;
	public static final int ID_配置维护key = 9;
	public static final int ID_流量统计 = 10;
	
	public static final int ID_金山云配置 = 11;
	
	public static final int ID_存储使用平台 = 12;
	
	public int id;
	public String name;
	public String config;

}
