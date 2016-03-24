/**
 * 
 */
package com.aepan.sysmgr.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author lanker
 * 2016年3月23日上午9:39:07
 */
public class HttpsUtil implements Serializable {
	public static Logger logger = LoggerFactory.getLogger(HttpsUtil.class);
	private static final long serialVersionUID = 1L;
	private static final String METHOD_POST = "POST";
	private static final String METHOD_GET = "GET";
	private static final String DEFAULT_CHARSET = "utf-8";
	private static final int DEFAULT_TIMEOUT = 30000; 
	public static String doPost(String url,String params){
		logger.debug(url+"\n params= "+params);
		String rs = "";
		try {
			rs = doPost(url, params, DEFAULT_CHARSET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT,METHOD_POST);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		logger.debug(rs);
		return rs;
	}
	public static String doPost(String url){
		logger.debug(url);
		String rs = "";
		try {
			rs = doPost(url, null, DEFAULT_CHARSET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT,METHOD_POST);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		logger.debug(rs);
		return rs;
	}
	public static String doGet(String url,String params){
		logger.debug(url+"\n params= "+params);
		String rs = "";
		try {
			rs = doPost(url, params, DEFAULT_CHARSET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT,METHOD_GET);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		logger.debug(rs);
		return rs;
	}
	public static String doGet(String url){
		logger.debug(url);
		String rs = "";
		try {
			rs = doPost(url, null, DEFAULT_CHARSET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT,METHOD_GET);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		logger.debug(rs);
		return rs;
	}
	private static String doPost(String url, String params, String charset, int connectTimeout, int readTimeout,String method) throws Exception {
		String ctype = "application/json;charset=" + charset;
		byte[] content = {};
		if(params != null){
			content = params.getBytes(charset);
		}
		
		return doPost(url, ctype, content, connectTimeout, readTimeout,method);
	}
	private static String doPost(String url, String ctype, byte[] content,int connectTimeout,int readTimeout,String method) throws Exception {
		HttpsURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			try{
				SSLContext ctx = SSLContext.getInstance("TLS");
		        ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
		        SSLContext.setDefault(ctx);

				conn = getConnection(new URL(url), method, ctype);	
				//conn.setSSLSocketFactory(getSSLSocketFactory());
				conn.setHostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
				conn.setConnectTimeout(connectTimeout);
				conn.setReadTimeout(readTimeout);
			}catch(Exception e){
				logger.error("GET_CONNECTOIN_ERROR, URL = " + url, e);
				throw e;
			}
			try{
				if(method==METHOD_POST){
					out = conn.getOutputStream();
					out.write(content);
				}
				rsp = getResponseAsString(conn);
			}catch(IOException e){
				logger.error("REQUEST_RESPONSE_ERROR, URL = " + url, e);
				throw e;
			}
			
		}finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		
		return rsp;
	}

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }
	
	private static HttpsURLConnection getConnection(URL url, String method, String ctype)
			throws IOException {
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
		conn.setRequestProperty("User-Agent", "stargate");
		conn.setRequestProperty("Content-Type", ctype);
		return conn;
	}

	protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
		String charset = getResponseCharset(conn.getContentType());
		InputStream es = conn.getErrorStream();
		if (es == null) {
			return getStreamAsString(conn.getInputStream(), charset);
		} else {
			String msg = getStreamAsString(es, charset);
			if (StringUtils.isEmpty(msg)) {
				throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
			} else {
				throw new IOException(msg);
			}
		}
	}

	private static String getStreamAsString(InputStream stream, String charset) throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
			StringWriter writer = new StringWriter();

			char[] chars = new char[256];
			int count = 0;
			while ((count = reader.read(chars)) > 0) {
				writer.write(chars, 0, count);
			}

			return writer.toString();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	private static String getResponseCharset(String ctype) {
		String charset = DEFAULT_CHARSET;

		if (!StringUtils.isEmpty(ctype)) {
			String[] params = ctype.split(";");
			for (String param : params) {
				param = param.trim();
				if (param.startsWith("charset")) {
					String[] pair = param.split("=", 2);
					if (pair.length == 2) {
						if (!StringUtils.isEmpty(pair[1])) {
							charset = pair[1].trim();
						}
					}
					break;
				}
			}
		}

		return charset;
	}
}
