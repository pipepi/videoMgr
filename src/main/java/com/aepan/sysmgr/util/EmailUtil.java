/**
 * 
 */
package com.aepan.sysmgr.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aepan.sysmgr.model.config.EmailConfig;
import com.aepan.sysmgr.service.ConfigService;

/**
 * 邮件发送
 * @author lanker
 * 2015年9月26日下午1:19:12
 */
public class EmailUtil {
	private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);
	public static final int TYPE_触发=1;
	public static final int TYPE_批量=2;
	/**
	 * 给指定的人发送邮件  sendMail("65479854@qq.com", "来自SendCloud的第一封邮件！", "你太棒了！你已成功的从SendCloud发送了一封测试邮件，接下来快登录前台去完善账户信息吧！", emailConfig);
	 * @param to 收件人
	 * @param subject 标题
	 * @param html 内容
	 * @param cs 取配置的service对象
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void sendMail(String to,String subject,String html,ConfigService cs) throws ClientProtocolException, IOException{
		EmailConfig conf = ConfigManager.getInstance().getEmailConfig(cs);
		sendMail(to, subject, html, conf);
	}
	/**
	 * 模版发送    sendMailTemplate("{\"to\": [\"65479854@qq.com\"],\"sub\":{\"%phone%\": [\"18612347894\"],\"%orderid%\":[\"23164564\"]}}", "test_template", "使用模版发送", emailConfig);
	 * @param var
	 * @param template
	 * @param subject
	 * @param cs
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void sendMailTemplate(int type,String var,String template,String subject,ConfigService cs) throws ClientProtocolException, IOException{
		EmailConfig conf = ConfigManager.getInstance().getEmailConfig(cs);
		sendMailTemplate(type,var, template, subject, conf);
	}
	private static void sendMail(String to,String subject,String html,EmailConfig conf) throws ClientProtocolException, IOException{
		 HttpPost httpost = new HttpPost(conf.url+"mail.send.json");
		  @SuppressWarnings({ "resource", "deprecation" })
		 HttpClient httpclient = new DefaultHttpClient();

		    List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("api_user", conf.apiUser));
		    params.add(new BasicNameValuePair("api_key", conf.apiKey));
		    params.add(new BasicNameValuePair("to", to));
		    params.add(new BasicNameValuePair("from", conf.from));
		    params.add(new BasicNameValuePair("fromname", conf.fromname));
		    params.add(new BasicNameValuePair("subject", subject));
		    params.add(new BasicNameValuePair("html", html));
		    params.add(new BasicNameValuePair("resp_email_id", "true"));

		    httpost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		    HttpResponse response = httpclient.execute(httpost);

		    // response
		    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		    	logger.info(EntityUtils.toString(response.getEntity()));
		    } else {
		    	logger.info("send mail error");
		    }
		    httpost.releaseConnection();
	}
	private static void sendMailTemplate(int type,String var,String template,String subject,EmailConfig conf) throws ClientProtocolException, IOException{
		HttpPost httpost = new HttpPost(conf.url+"mail.send_template.json");
		@SuppressWarnings({ "resource", "deprecation" })
		HttpClient httpclient = new DefaultHttpClient();
		logger.debug("var:"+var+",template:"+template+",subject:"+subject+",conf:"+conf);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("api_user",type==TYPE_批量?conf.apiBatchUser:conf.apiUser));
		params.add(new BasicNameValuePair("api_key", conf.apiKey));
		params.add(new BasicNameValuePair("substitution_vars", var));
		params.add(new BasicNameValuePair("template_invoke_name", template));
		params.add(new BasicNameValuePair("from", conf.from));
		params.add(new BasicNameValuePair("fromname", conf.fromname));
		params.add(new BasicNameValuePair("subject", subject));
		params.add(new BasicNameValuePair("resp_email_id", "true"));
		
		httpost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpResponse response = httpclient.execute(httpost);
		
		// response
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	    	logger.info(EntityUtils.toString(response.getEntity()));
	    } else {
	    	logger.info("send mail error");
	    }
		httpost.releaseConnection();
	}
	public static void main(String[] args) throws ClientProtocolException, IOException {
		EmailConfig emailConfig =new EmailConfig();
		emailConfig.url = "http://sendcloud.sohu.com/webapi/";
		emailConfig.apiKey = "YJLNcPHzXPCwryJ1";
		emailConfig.apiUser = "9cooo_api_user";
		emailConfig.apiBatchUser = "9cooo_api_user_batch";
		emailConfig.from = "9cooo@mail.9cooo.com";
		emailConfig.fromname = "9库网";
		//sendMail("65479854@qq.com", "来自SendCloud的第一封邮件！", "你太棒了！你已成功的从SendCloud发送了一封测试邮件，接下来快登录前台去完善账户信息吧！", emailConfig);
//		sendMailTemplate("{\"to\": [\"hairry.come@qq.com\"],\"sub\":{\"%orderId%\":[\"201510151104209879\"],\"%productName%\": [\"Ceshi\"],\"%buyerName%\": [\"HHHH\"],\"%price%\": [\"1000  支付宝支付\"],\"%createTime%\": [\"20158445 234:34:2\"]}}", "user_finish_pay_notify_html", "9库网-支付成功", emailConfig);
		//sendMailTemplate("{\"to\": [\"hairry.come@qq.com\"],\"sub\":{\"%partnerAccountName%\":[\"lsjdlfjwer\"]}}", "shop_flow_less_than_200", "9库网-流量少于200M", emailConfig);
		
		sendMailTemplate(TYPE_触发,"{\"to\": [\"hairry.zhou@azt-china.com\"],\"sub\":{\"%partnerAccountName%\":[\"lsjdlfjwer\"],\"%currentPackageName%\":[\"套餐1\"],\"%currentPackagePrice%\":[\"100.377\"]}}", "shop_video_package_less_than_30", "9库网-套餐少于30天", emailConfig);
	}
}
