package com.aepan.sysmgr.web.api.pay;


/**
 * 
 * @author Doris.zhou
 * 2015年8月17日下午3:44:43
 */

public class PayConstants {
	
	
	
	public static final   int TYPE_ALIPAY=1;//支付宝
	public static final   int TYPE_ALIPAY_WAP=2;//支付宝手机
	public static final   int TYPE_WECHAT=3;//微信支付
	public static final   int TYPE_WECHATQRCODEWAP=4;//微信支付手机长按
	public static final   int TYPE_TENPAY=5;//财付通
	public static final   int TYPE_WECHATWAP=6;//微信支付手机
	

	
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
/*	public static String partner = "2088811963111575";
	// 收款支付宝账号
	public static String seller_email = "lucas.zhao@azt-china.com";
	// 商户的私钥
	public static String key = "7hi3wgo2kpxod9sm2slzminpd42fdld0";*/
	
	
//	public static String partner = "2088021209033309";
//	// 收款支付宝账号
//	public static String seller_email = "9cooo@9cooo.com";
//	// 商户的私钥
//	public static String key = "gxy9rgscf4vev5ibkvxjz1cbnlpjbe9w";
	 

	public static String ALIPAY_RETURN_URL="/pay/alipay_return";
	
	public static String ALIPAY_NOTIFY_URL="/pay/alipay_notify";

	
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "MD5";
	
	public static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
	
	
	
	public static String tenpayPartner = "1228960001";
	//密钥
	public static String tenpayKey = "c5e4983dd17ea099813114038185b3f0";

	//交易完成后跳转的URL
	public static String tenpayReturnUrl = "/pay/tenpay_return";
	//接收财付通通知的URL
	public static String tenpayNotifyUrl = "/pay/tenpay_notify";
	

	
	
//	public static String appId="wxa00a9edb0231b4bf";
//	
//	public static String mch_id="1235434802";
//	
//	public static String appSecret ="7ad5c3b62f3c22d0a23383e551464444"; 
//	
//	public static String apiKey="aeZ65aWe88daFb6bb98T503d0a51d168";
	
	
//	public static String appId="wxdea538ccfee2cb94";
//	
//	public static String mch_id="1252847601";
//	
//	public static String appSecret ="40517d3595e80ab191bc1729481a8b6e"; 
//	
//	public static String apiKey="9coooQWRETU20181020PLMIJNVGYUIWQ";
	
	public static final String WINXINPAY_GATEWAY_NEW = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	public static final String WECHAT_NOTIFY_URL="/pay/wechatpay_notify";
	
	public static final int BLACK = 0xff000000;
	
	public static final int WHITE = 0xFFFFFFFF;
	
	
}
