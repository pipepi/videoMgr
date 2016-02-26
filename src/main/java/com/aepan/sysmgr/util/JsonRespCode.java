package com.aepan.sysmgr.util;


/**
 * 返回JSON的代码表
 * 
 * @author linxw
 * 
 */
public class JsonRespCode {

	public static final int SUCCESS = 0; //成功
	public static final int SYSTEM_ERROR = -1; //系统错误
	public static final int PARAM_ERROR = -2; //参数错误
	public static final int TIME_OUT = -3; //接口请求超时
	public static final int NOT_OPENED = 1; //未开通
	public static final int ALREADEY_OPENED = 2; //已开通
	public static final int ALREADEY_CLOSED = 3; //已退订
	public static final int OTHER_FAILD = 100; //其他错误

}
