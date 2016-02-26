/**
 * 
 */
package com.aepan.sysmgr.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lanker
 * 2015年11月16日上午10:23:56
 */
public class HttpUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	public static String post(String url,String param){
		 PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            // 设置通用的请求属性
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(conn.getOutputStream());
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	        	logger.error("发送 POST 请求出现异常！"+e);
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	            	logger.error(ex.getMessage(), ex);
	            }
	        }
	        return result;
	}
	public static void postJsonParam(String url,String jsonParam){
		PostMethod method = new PostMethod(url);
		method.setRequestBody(jsonParam);
		method.setRequestHeader("Content-Type", "application/json");
        
		HttpClient client = new HttpClient();

		String ret = "";
		try {
			client.executeMethod(method);
			ret = method.getResponseBodyAsString();
			logger.info(ret);
		}catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	}
}
