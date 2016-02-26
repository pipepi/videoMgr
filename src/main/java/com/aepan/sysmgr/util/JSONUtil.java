/**
 * *****************************************************************************
 * 							Copyright (c) 2014 yama.
 * This is not a free software,all rights reserved by yama(guooscar@gmail.com).
 * ANY use of this software MUST be subject to the consent of yama.
 *
 * *****************************************************************************
 */
package com.aepan.sysmgr.util;

import java.util.List;

import com.aepan.sysmgr.model.json.Root;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author yama
 * @date Jun 5, 2014
 */
public class JSONUtil {
	public static interface JSONPropertyFilter{
		boolean apply(Object object, String name, Object arg2);
	}
	/**
	 *convert object to json string 
	 */
	public static String toJson(Object obj,
			JSONPropertyFilter propertyFilter,
			boolean prettyFormat){
		if(prettyFormat){
			return JSON.toJSONString(obj,new FastJsonPropertyFilter(propertyFilter),
					SerializerFeature.PrettyFormat);
		}else{
			return JSON.toJSONString(obj,new FastJsonPropertyFilter(propertyFilter));	
		}
	}
	//
	private static class FastJsonPropertyFilter implements PropertyFilter{
		JSONPropertyFilter propertyFilter;
		public FastJsonPropertyFilter(JSONPropertyFilter filter) {
			this.propertyFilter=filter;
		}
		@Override
		public boolean apply(Object arg0, String arg1, Object arg2) {
			return propertyFilter.apply(arg0, arg1, arg2);
		}
	} 
	/**
	 *convert object to json string 
	 */
	public static String toJson(Object obj){
		return JSON.toJSONString(obj);
	}
	/**
	 * convert json string to class
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String str,Class<?>t){
		return (T) JSON.parseObject(str, t);
	}
	/**
	 *convert json to class list 
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> fromJsonList(String str,Class<?>t){
		return  (List<T>) JSON.parseArray(str, t);	
	}
	public static void main(String[] args) {
		String jsonStr = "{\"data\":{\"json\":[{\"Name\":\"Color\",\"SpecId\":1,\"Values\":[{\"Id\":\"618\",\"isPlatform\":true,\"Name\":\"白色\",\"Selected\":false},{\"Id\":\"619\",\"isPlatform\":true,\"Name\":\"黑色\",\"Selected\":false},{\"Id\":\"620\",\"isPlatform\":true,\"Name\":\"红色\",\"Selected\":false}]},{\"Name\":\"Size\",\"SpecId\":2,\"Values\":[{\"Id\":\"73\",\"isPlatform\":true,\"Name\":\"10.1寸\",\"Selected\":false},{\"Id\":\"74\",\"isPlatform\":true,\"Name\":\"12寸\",\"Selected\":false},{\"Id\":\"75\",\"isPlatform\":true,\"Name\":\"14寸\",\"Selected\":false},{\"Id\":\"610\",\"isPlatform\":true,\"Name\":\"15寸\",\"Selected\":false},{\"Id\":\"611\",\"isPlatform\":true,\"Name\":\"17寸\",\"Selected\":false},{\"Id\":\"612\",\"isPlatform\":true,\"Name\":\"18寸\",\"Selected\":false},{\"Id\":\"613\",\"isPlatform\":true,\"Name\":\"19寸\",\"Selected\":false},{\"Id\":\"614\",\"isPlatform\":true,\"Name\":\"哒哒哒大的寸\",\"Selected\":false}]},{\"Name\":\"Version\",\"SpecId\":3,\"Values\":[{\"Id\":\"76\",\"isPlatform\":true,\"Name\":\"i3 CPU\",\"Selected\":false},{\"Id\":\"77\",\"isPlatform\":true,\"Name\":\"i5 CPU/独显\",\"Selected\":false},{\"Id\":\"78\",\"isPlatform\":true,\"Name\":\"i7 CPU/独显\",\"Selected\":false}]}],\"tableData\":{\"cost\":[],\"mallPrice\":[],\"productId\":127,\"sku\":[],\"stock\":[]}}}";
		Root root = fromJson(jsonStr, Root.class);
		System.out.println(root);
	}
}
