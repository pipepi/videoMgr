/**
 * 
 */
package com.aepan.sysmgr.util;

import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

/**
 * @author rakika
 * 2015年6月21日上午9:48:09
 */
public class EncryptUtil {

	public static final String MD5_SALT = "aztsysmgr_md5";
	
	/**
	 * 采用md5方式加密
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String getMd5Str(String str) throws Exception{
		MessageDigest md = MessageDigest.getInstance("md5");
		md.update((str + MD5_SALT).getBytes());
		byte[] ary = md.digest();
		BASE64Encoder base64 = new BASE64Encoder();	//将byte[]转成字符串
		return base64.encode(ary);
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String password = "123456";
//		String md5pwd = CryptTool.md5Digest(password);
		String md5pwd = getMd5Str(password);
		
		System.out.println(md5pwd);

	}

}
