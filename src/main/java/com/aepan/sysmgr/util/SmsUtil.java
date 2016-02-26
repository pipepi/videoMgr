/**
 * 
 */
package com.aepan.sysmgr.util;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aepan.sysmgr.model.config.SmsConfig;
import com.aepan.sysmgr.model.sms.Xml;
import com.aepan.sysmgr.service.ConfigService;

/**
 * @author lanker
 * 2015年9月24日下午2:46:59
 */
public class SmsUtil {
	private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);
	/**
	 * 发送一条短信
	 * @param c 发送内容(总长度应在1000个汉字以内)
	 * @param m 手机号码，如多个以英文逗号分隔 13688998866,15899668855
	 * @throws UnsupportedEncodingException 
	 */
	public static Xml send(String c,String m,ConfigService cs,int type){
		SmsConfig conf = ConfigManager.getInstance().getSmsConfig(cs);
		return send(c, m, conf,type);
	}
	public static void main(String[] args) {
		int countless50 = 0;
		int countmore50 = 0;
		for (int i = 0; i < 20; i++) {
			int r = RandomUtils.nextInt(100);
			if(r<50) countless50++;
			if(r>=50) countmore50++;
			System.out.println(r);
		}
		System.out.println(countless50+"  more 50 count = "+countmore50);
	}
	private static Xml send(String c,String m,SmsConfig conf,int type){
		if(c==null||c.trim().isEmpty()||c.trim().length()>1000||m==null||m.trim().isEmpty()){
			logger.info("send param error  c= "+c+"  m= "+m);
			return null;
		}
		try {
			c = URLEncoder.encode(c, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.info("send content encode error  c= "+c+"  m= "+m);
			return null;
		}
		StringBuffer parambuffer = new StringBuffer();
		parambuffer.append("action=sendOnce&");
		String url = "";
		if(type==SmsConfig.TYPE_验证码){
			int r = RandomUtils.nextInt(100);
			if(r>50){
				parambuffer.append("ac="+conf.ac+"&");
				parambuffer.append("authkey="+conf.authkey+"&");
				parambuffer.append("cgid="+conf.cgid+"&");
				parambuffer.append("csid="+conf.csid+"&");
				url = conf.url;				
			}else{
				parambuffer.append("ac="+conf.ac_2+"&");
				parambuffer.append("authkey="+conf.authkey_2+"&");
				parambuffer.append("cgid="+conf.cgid_2+"&");
				parambuffer.append("csid="+conf.csid_2+"&");
				url = conf.url_2;				
			}
		}else{
			parambuffer.append("ac="+conf.oth_ac+"&");
			parambuffer.append("authkey="+conf.oth_authkey+"&");
			parambuffer.append("cgid="+conf.oth_cgid+"&");
			parambuffer.append("csid="+conf.oth_csid+"&");
			url = conf.oth_url;
		}
		parambuffer.append("c=").append(c).append("&");
		parambuffer.append("m=").append(m);
		String param = parambuffer.toString();
		logger.info("send url = "+url);
		logger.info("send param = "+param);
		String xmlrs = HttpUtil.post(url,param);
		
		logger.info("send xmlrs = "+xmlrs);
		return doRs(xmlrs);
		
	}
	/*public static void main(String[] args) throws UnsupportedEncodingException {
		
		SmsConfig smsConfig = new SmsConfig();
		smsConfig.url = "http://smsapi.c123.cn/OpenPlatform/OpenApi";
		smsConfig.ac = "1001@501176370003";
		smsConfig.authkey = "DF007ADB8A1C5B02A9BF27C368FB945A";
		smsConfig.cgid = "5278";
		smsConfig.csid = "5550";
		
		
		smsConfig.oth_url =  "http://smsapi.c123.cn/OpenPlatform/OpenApi";
		smsConfig.oth_ac = "1001@501176370004";
		smsConfig.oth_authkey = "E723ED871F6BFFDABB16F78D8AA1782C";
		smsConfig.oth_cgid = "5279";
		smsConfig.oth_csid = "5553";
		
		//Xml xml = send("根据套餐合约的内容，您的账户（dsdwer）流量已少于200M。在此通知您尽快 续费或者升级您的套餐，我们会为您提供充足的缴费时间，期间会保留您所有的商店信息。联系电话 400-664-9000", "15875589605", smsConfig,SmsConfig.TYPE_非验证码);
		//System.out.println(xml.toString());
		
		send("6666","18679795883",smsConfig,SmsConfig.TYPE_验证码);
		//String xmlrs = "<xml name=\"sendOnce\" result=\"1\"><Item cid=\"501176370003\" sid=\"1001\" msgid=\"52261791705342400\" total=\"1\" price=\"0.1\" remain=\"20.400\" /></xml>";
		//doRs(xmlrs);
	}*/
	private static Xml doRs(String xmlStr){
		try {  
            JAXBContext context = JAXBContext.newInstance(Xml.class);  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            Xml rs = (Xml)unmarshaller.unmarshal(new StringReader(xmlStr));  
            logger.info("send rs =  "+rs.toString());
            return rs;
        } catch (JAXBException e) {  
            logger.error(e.getMessage(),e);
        }  
		return null;
	}
	
}
