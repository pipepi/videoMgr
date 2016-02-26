package com.aepan.sysmgr.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com._21cn.open.pay.paychannel.wapalipay.security.MD5Signature;

/**
 * 字符串处理的工具类 
 * @author stone.zhangjl
 * @version $Id: StringUtil.java, v 0.1 2008-8-21 上午10:47:41 stone.zhangjl Exp $
 */
public class StringUtil {
	protected static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static String uncapitalize(String str){
        return changeFirstCharacterCase(str, false);
    }
    
    private static String changeFirstCharacterCase(String str, boolean capitalize)
    {
        if(str == null || str.length() == 0)
            return str;
        StringBuilder sb = new StringBuilder(str.length());
        if(capitalize)
            sb.append(Character.toUpperCase(str.charAt(0)));
        else
            sb.append(Character.toLowerCase(str.charAt(0)));
        sb.append(str.substring(1));
        return sb.toString();
    }    
    /** 空字符串。 */
    public static final String EMPTY_STRING = "";
	private final static char[] digits = {
		'0' , '1' , '2' , '3' , '4' , '5' ,	'6' , '7' ,
		'8' , '9' , 'a' , 'b' , 'c' , 'd' , 'e' , 'f' 
	};
    /**
     * 比较两个字符串（大小写敏感）。
     * <pre>
     * StringUtil.equals(null, null)   = true
     * StringUtil.equals(null, "abc")  = false
     * StringUtil.equals("abc", null)  = false
     * StringUtil.equals("abc", "abc") = true
     * StringUtil.equals("abc", "ABC") = false
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     *
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equals(str2);
    }

    /**
     * 比较两个字符串（大小写不敏感）。
     * <pre>
     * StringUtil.equalsIgnoreCase(null, null)   = true
     * StringUtil.equalsIgnoreCase(null, "abc")  = false
     * StringUtil.equalsIgnoreCase("abc", null)  = false
     * StringUtil.equalsIgnoreCase("abc", "abc") = true
     * StringUtil.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     *
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equalsIgnoreCase(str2);
    }

    /**
     * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str 要检查的字符串
     *
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查字符串是否不是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * <pre>
     * StringUtil.isBlank(null)      = false
     * StringUtil.isBlank("")        = false
     * StringUtil.isBlank(" ")       = false
     * StringUtil.isBlank("bob")     = true
     * StringUtil.isBlank("  bob  ") = true
     * </pre>
     *
     * @param str 要检查的字符串
     *
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isNotBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    /**
     * 检查字符串是否为<code>null</code>或空字符串<code>""</code>。
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("bob")     = false
     * StringUtil.isEmpty("  bob  ") = false
     * </pre>
     * 
     * 更改:先清空2边空格
     *
     * @param str 要检查的字符串
     *
     * @return 如果为空, 则返回<code>true</code>
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 检查字符串是否不是<code>null</code>和空字符串<code>""</code>。
     * <pre>
     * StringUtil.isEmpty(null)      = false
     * StringUtil.isEmpty("")        = false
     * StringUtil.isEmpty(" ")       = true
     * StringUtil.isEmpty("bob")     = true
     * StringUtil.isEmpty("  bob  ") = true
     * </pre>
     *
     * @param str 要检查的字符串
     *
     * @return 如果不为空, 则返回<code>true</code>
     */
    public static boolean isNotEmpty(String str) {
        return ((str != null) && (str.length() > 0));
    }

    /**
     * 在字符串中查找指定字符串，并返回第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>。
     * <pre>
     * StringUtil.indexOf(null, *)          = -1
     * StringUtil.indexOf(*, null)          = -1
     * StringUtil.indexOf("", "")           = 0
     * StringUtil.indexOf("aabaabaa", "a")  = 0
     * StringUtil.indexOf("aabaabaa", "b")  = 2
     * StringUtil.indexOf("aabaabaa", "ab") = 1
     * StringUtil.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param str 要扫描的字符串
     * @param searchStr 要查找的字符串
     *
     * @return 第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>
     */
    public static int indexOf(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        return str.indexOf(searchStr);
    }

    /**
     * 在字符串中查找指定字符串，并返回第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>。
     * <pre>
     * StringUtil.indexOf(null, *, *)          = -1
     * StringUtil.indexOf(*, null, *)          = -1
     * StringUtil.indexOf("", "", 0)           = 0
     * StringUtil.indexOf("aabaabaa", "a", 0)  = 0
     * StringUtil.indexOf("aabaabaa", "b", 0)  = 2
     * StringUtil.indexOf("aabaabaa", "ab", 0) = 1
     * StringUtil.indexOf("aabaabaa", "b", 3)  = 5
     * StringUtil.indexOf("aabaabaa", "b", 9)  = -1
     * StringUtil.indexOf("aabaabaa", "b", -1) = 2
     * StringUtil.indexOf("aabaabaa", "", 2)   = 2
     * StringUtil.indexOf("abc", "", 9)        = 3
     * </pre>
     *
     * @param str 要扫描的字符串
     * @param searchStr 要查找的字符串
     * @param startPos 开始搜索的索引值，如果小于0，则看作0
     *
     * @return 第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>
     */
    public static int indexOf(String str, String searchStr, int startPos) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        // JDK1.3及以下版本的bug：不能正确处理下面的情况
        if ((searchStr.length() == 0) && (startPos >= str.length())) {
            return str.length();
        }

        return str.indexOf(searchStr, startPos);
    }

    /**
     * 取指定字符串的子串。
     * 
     * <p>
     * 负的索引代表从尾部开始计算。如果字符串为<code>null</code>，则返回<code>null</code>。
     * <pre>
     * StringUtil.substring(null, *, *)    = null
     * StringUtil.substring("", * ,  *)    = "";
     * StringUtil.substring("abc", 0, 2)   = "ab"
     * StringUtil.substring("abc", 2, 0)   = ""
     * StringUtil.substring("abc", 2, 4)   = "c"
     * StringUtil.substring("abc", 4, 6)   = ""
     * StringUtil.substring("abc", 2, 2)   = ""
     * StringUtil.substring("abc", -2, -1) = "b"
     * StringUtil.substring("abc", -4, 2)  = "ab"
     * </pre>
     * </p>
     *
     * @param str 字符串
     * @param start 起始索引，如果为负数，表示从尾部计算
     * @param end 结束索引（不含），如果为负数，表示从尾部计算
     *
     * @return 子串，如果原始串为<code>null</code>，则返回<code>null</code>
     */
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        if (end < 0) {
            end = str.length() + end;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return EMPTY_STRING;
        }

        if (start < 0) {
            start = 0;
        }

        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 检查字符串中是否包含指定的字符串。如果字符串为<code>null</code>，将返回<code>false</code>。
     * <pre>
     * StringUtil.contains(null, *)     = false
     * StringUtil.contains(*, null)     = false
     * StringUtil.contains("", "")      = true
     * StringUtil.contains("abc", "")   = true
     * StringUtil.contains("abc", "a")  = true
     * StringUtil.contains("abc", "z")  = false
     * </pre>
     *
     * @param str 要扫描的字符串
     * @param searchStr 要查找的字符串
     *
     * @return 如果找到，则返回<code>true</code>
     */
    public static boolean contains(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return false;
        }

        return str.indexOf(searchStr) >= 0;
    }

    /**
     * <p>Checks if the String contains only unicode digits.
     * A decimal point is not a unicode digit and returns false.</p>
     *
     * <p><code>null</code> will return <code>false</code>.
     * An empty String ("") will return <code>true</code>.</p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = true
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if only contains digits, and is non-null
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isPrice(String str){
        if (str == null) {
            return false;
        }
        try{
        	Double.parseDouble(str);
        	return true;
        }catch(Exception e){
        	logger.warn(e.getMessage());
        	return false;
        }
    }

	/**
	 * byte数组转化为16进制的String<p>
	 * @param byteData byte[] 字节数组
	 * @return String
	 * <p>把字节数组转换成可视字符串</p>
	 */
	public static  String toHex(byte byteData[]) {
		return toHex( byteData,0,byteData.length );
	}
	
	/**
	 * 将字符串data按照encode转化为byte数组，然后转化为16进制的String
	 * @param data 源字符串
	 * @param encode 字符编码
	 * @return 把字节数组转换成可视字符串
	 */
	public static  String toHex(String data, String encode) {
		try {
			return toHex( data.getBytes( encode ) );
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * byte转化为16进制的String
	 * @param b
	 * @return 16进制的String
	 */
	public static  String toHex(byte b) {
		byte[] buf = { b };
		return toHex( buf );
	}
	/**
	 * byte数组的部分字节转化为16进制的String
	 * @param byteData 待转换的byte数组
	 * @param offset 开始位置
	 * @param len 字节数
	 * @return 16进制的String
	 */
	public static  String toHex(byte byteData[], int offset, int len) {
		char buf[] = new char[len*2];
		int k=0;
		for (int i= offset; i < len; i++ ) {
		    buf[k++] = digits[((int) byteData[i] & 0xff)>>4 ];
		    buf[k++] = digits[((int) byteData[i] & 0xff)%16];
		}
		return new String(buf);
	}
	
	/**
	 * 将16进制的字符串转换为byte数组，是toHex的逆运算
	 * @param hex 16进制的字符串
	 * @return byte数组
	 */
	public static  byte[] hex2Bytes( String hex ) {
		if( isEmpty(hex) || hex.length() %2> 0 ) {
			return null;
		}
		int len = hex.length() / 2;
		byte[] ret = new byte[ len ];
		int k = 0;
		for (int i = 0; i < len; i++) {
			int c = hex.charAt(k++);
			if( c>='0'&& c<='9' )
				c = c-'0';
			else if( c>='a'&& c<='f' )
				c = c-'a'+ 10;
			else if( c>='A'&& c<='F' )
				c = c-'A'+ 10;
			else {
				return null;
			}
			ret[i]= (byte)(c<<4);
			c = hex.charAt(k++);
			if( c>='0'&& c<='9' )
				c = c-'0';
			else if( c>='a'&& c<='f' )
				c = c-'a'+ 10;
			else if( c>='A'&& c<='F' )
				c = c-'A'+ 10;
			else {
				return null;
			}
			ret[i]+= (byte)c;
		}
		return ret;
	}	
	
	private static Object initLock = new Object();
	private static Random randGen = null;
	private static char[] numbersAndLetters = null;
 
	public static String randomString(int length) {
		//初始化随机数字生成器
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					//初始化数字、字母数组
					numbersAndLetters = ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
				}
			}
		}
		
		//创建字符缓存数组装入字母和数字
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
		    randBuffer[i] = numbersAndLetters[randGen.nextInt(51)];
		}

		return new String(randBuffer);
	}
	
	/**
	 * 数组字典序排序
	 * @param array
	 * @return
	 */
	public static String arraysSort(String[] array){
		
		Arrays.sort(array);
		
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			stringBuilder.append(array[i]);
		}
		return stringBuilder.toString();
	}
	
	 public static boolean checkChs(String str) {
		  Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5（）()]+$");
		  Matcher matc = pattern.matcher(str);
		  while (matc.find()) {
			  return true;		   	 	
		  }
		  return false;
	 }
	 
	 /**
	  * 简单放XSS攻击
	  * @param s
	  * <,&lt;
	  * >,&gt;
	  * ',&acute;
	  * ",&quot;
	  * @return
	  */
	 public static String replaceSpecial(String s){
		 if(isEmpty(s)){
			 return s;
		 }
		 String str = s.replaceAll("<", "&lt;");
		 str = str.replaceAll(">", "&gt;");
		 
		 //str = s.replaceAll("u003c", "&lt;");
		// str = str.replaceAll("u003e", "&gt;");
		 str = str.replaceAll("/", "&#x2f;");
	
		 str = str.replaceAll("javascript:", "-");
		 str = str.replaceAll("jscript:", "-");
		 str = str.replaceAll("vbscript:", "-");

		 str = str.replaceAll("onerror=", "-");

		 str = str.replaceAll("'", "&acute;");
		 str = str.replaceAll("\"", "&quot;");
		 return str.trim();
	 }
	 
	 public static boolean isEmail(String email){
	     if(isEmpty(email))
	         return false;
	     else
	         return Pattern.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", email);
	 }
	 
	 public static boolean is21cnEmail(String email){
		 String arr[] = {"@189.","@corp.21cn.","@21cn."};
	     if(isEmpty(email))
	         return false;
	     else{
	    	 for(String s:arr){
	    		 if(email.indexOf(s)>0){
	    			 return true;
	    		 }
	    	 }
	         return false;
	     }
	 }
	 
	 public static boolean isEnglish(String english){
		  if(isEmpty(english))
		         return true;
		     else
		         return Pattern.matches("^[A-Za-z][A-Za-z\\s]*[A-Za-z]$", english);
	 }
	 
	 public static boolean isUrl(String s){
	     if(isEmpty(s))
	         return false;
	     
		 if(s.substring(s.length()-1,s.length()).equalsIgnoreCase("/")){
			 s = s.substring(0, s.length()-1);
		 }
	     
	     Pattern p = Pattern.compile("^(http|www|ftp|https)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$",Pattern.CASE_INSENSITIVE );   
	     Matcher m = p.matcher(s);
	     return m.find();
	     /*
	     boolean ret = Pattern.matches("^(https|http|ftp|rtsp|mms)?:\\/\\/[^\\s]*$", s);
	     if(!ret)
	         ret = Pattern.matches("^[\\.\\/\\?#a-zA-Z0-9-_=&;,%]*$", s);
	     return ret;
	     */
	 }	 
	 
	 public static boolean isTel(String tel){
		 boolean ret = Pattern.matches("^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$", tel);
		 if(!ret){
			 return Pattern.matches("^(?:13\\d|15\\d|18\\d)-?\\d{5}(\\d{3}|\\*{3})$", tel);
		 }else{
			 return true;
		 }
	 }
	 
	 public static boolean isNumOrEng(String s,boolean isEmpty){
	     if(isEmpty(s))
	         return isEmpty;
		 return Pattern.matches("^[A-Za-z0-9]*$", s);
	 }
	 
	 public static String encode(String str, String code){
	        if(str == null || str.equals(""))return "";
	        try {
	            str = java.net.URLEncoder.encode(str,code);
	        } catch (UnsupportedEncodingException e) {
	            logger.error(e.getMessage(),e);
	        }
	        return str;
	    }
	 
	 /*public static void main(String []args){
		 System.out.println(replaceSqlCode("aaaaaa是是是是*\\,"));
	 }*/
	 
 /**
     * 校验签名
     * @param request
     * @param secret
     * @return
 * @throws Exception 
     */
    public static  boolean checkSign(HttpServletRequest request, String secret) throws Exception {
        boolean result = false;
        Map<String, String[]> reqParams = request.getParameterMap();
        String remoteSign = request.getParameter("sign");
        if (isEmpty(remoteSign)) {
            return result;
        }
        List<String> paramList = new LinkedList<String>();
        for (Entry<String, String[]> entry : reqParams.entrySet()) {
            if (entry.getKey().equals("sign")) {
                continue;
            }
            StringBuilder valueSb = new StringBuilder();
            for (String val : entry.getValue()) {
                valueSb.append(val);
            }
            paramList.add(entry.getKey() + "=" + valueSb);
        }

        StringBuilder signSb = new StringBuilder(request.getMethod() + request.getRequestURL());

        Collections.sort(paramList);
        for (String paramStr : paramList) {
            signSb.append(paramStr);
        }

        signSb.append(secret);

        String localSign = null;
        try {
            //localSign = StringUtil.md5(URLEncoder.encode(signSb.toString(), "utf8"));
        	localSign = MD5Signature.sign(URLEncoder.encode(signSb.toString(), "utf8"), "");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(),e);
        }

        result = localSign.equals(remoteSign);
        return result;
    }
    
    /**
     * 生成签名
     * @param params
     * @param secret
     * @param method
     * @param url
     * @return
     * @throws Exception 
     */
    public static String generateSign(Map<String, Object> params, String secret, String method, String url) throws Exception {
        Set<String> paramSet = new TreeSet<String>();
        for(Entry<String, Object> entry : params.entrySet()){
            paramSet.add(entry.getKey()+"="+entry.getValue());
        }
        StringBuilder signSb = new StringBuilder(method+url);
        for(String paramStr:paramSet){
            signSb.append(paramStr);
        }
        signSb.append(secret);
        
        String localSign = null;
        try {
            //localSign = StringUtil.md5(URLEncoder.encode(signSb.toString(), "utf8"));
        	localSign = MD5Signature.sign(URLEncoder.encode(signSb.toString(), "utf8"), "");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(),e);
        }
        return localSign;
    }	 
    
    public static String replaceSqlCode(String str){
        if(isEmpty(str)){
            return "";
        }
        String reStr = str;
        String inj_str = "'|=|*|%|;|+|,|\\|/|\"|>|<";
        String[] inj_stra=inj_str.split("\\|");
        for (int i=0 ; i < inj_stra.length ; i++ ){
            if (str.indexOf(inj_stra[i])>=0){
                reStr = reStr.replace(inj_stra[i], "");
                System.out.println(reStr+"--------------"+inj_stra[i]);
            }
        }
        return reStr;
    }
    
    public static String replaceSpecialSql(String str){
        if(isEmpty(str)){
            return "";
        }
        String reStr = str;
        String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|+|,|=|\\|/|\"|>|<";
        String[] inj_stra=inj_str.split("\\|");
        for (int i=0 ; i < inj_stra.length ; i++ ){
            if (str.indexOf(inj_stra[i])>=0){
                reStr = reStr.replace(inj_stra[i], "");
                System.out.println(reStr+"--------------"+inj_stra[i]);
            }
        }
        return reStr;
    }
    
    /**
     * 生成唯一订单号
     * @return
     */
    public static String getOrderNo(){
    	Date now = new Date(); 
    	String timeout = getStrDate(now);
		return timeout+ new Random().nextInt(100000);
    }

	public static String getStrDate(Date now) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); //可以方便地修改日期格式
    	String timeout = dateFormat.format(now);
		return timeout;
	}
    
    
    public static String getBatchNo(){
    	Date now = new Date(); 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd"); //可以方便地修改日期格式
    	String timeout = dateFormat.format(now);
		return timeout+ new Random().nextInt(100000);
    }
	/**
     * 河蟹 敏感词
     * @param text
     * @return
     */
    public static String harmSensitiveWord(String text) {
		SensitiveWordFilterUtil.get().addKeywords(SensitiveWord.get().getSensitiveWord());
		Set<String> words = SensitiveWordFilterUtil.get().getSensitiveWord(text);
		for (String string : words) {
			text = text.replaceAll(string, " “河蟹” ");
		}
		return text;
    }
    public static void main(String[] args) {
    	System.out.println(harmSensitiveWord("习近平，你好牛逼啊"));
	}
    
	public static String specialCharFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
}
