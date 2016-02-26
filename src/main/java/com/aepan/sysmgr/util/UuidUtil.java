package com.aepan.sysmgr.util;

import java.util.UUID;
/**
 * 
 * @author Doris
 * 2015年8月10日下午5:22:03
 */
public class UuidUtil {

	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
	
	
	public static String getUUID(){
		return UUID.randomUUID().toString().trim();
	}
	
	public static void main(String[] args) {
		System.out.println(get32UUID());
		System.out.println(getUUID());
	}
}

