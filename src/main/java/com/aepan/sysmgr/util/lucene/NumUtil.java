/**
 * 
 */
package com.aepan.sysmgr.util.lucene;

/**
 * @author lanker
 * 2015年12月30日下午2:35:35
 */
public class NumUtil {
	public static final int NUM_MAX_LENGTH = 10;
	public static String toPriceStr(float f){
		String rs = "";
		rs = Float.toString(f*100);
		rs = rs.substring(0, rs.indexOf("."));
		int len = NUM_MAX_LENGTH - rs.length();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<len;i++){
			sb.append("0");
		}
		sb.append(rs);
		//System.out.println("f="+f+"   rs= "+sb.toString());
		return sb.toString();
	}
	public static float toPriceFloat(String s){
		float f = Float.parseFloat(s)/100.0f;
		//System.out.println("f="+f+"   rs= "+s);
		return f;
	}
	public static void main(String[] args) {
		toPriceFloat(toPriceStr(13f));
	}
}
