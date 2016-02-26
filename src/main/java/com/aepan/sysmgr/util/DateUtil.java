/**
 * 
 */
package com.aepan.sysmgr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author lanker
 * 2015年9月25日下午3:37:47
 */
public class DateUtil {
	
	/**
	 * 日期one 减去 日期two 相差的时间间隔（秒）
	 * @param one
	 * @param two
	 * @return
	 */
	public static long diff(Date one,Date two){
		long diff = (one.getTime()-two.getTime())/1000;
		return diff;
	}
	
	
	public static Date getDate(String dateStr) throws ParseException{
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    Date date = sdf.parse(dateStr);  
	    return date;
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String getDateStr(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //可以方便地修改日期格式
		String dateStr = dateFormat.format(date);
		return dateStr;
	}
	
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date one = sdf.parse("2015-9-25 15:46:50");
		Date two = sdf.parse("2015-9-25 15:46:20");
		System.out.println(one);
		System.out.println(two);
		System.out.println(diff(one, two));
	}



}
