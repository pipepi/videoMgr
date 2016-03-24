package com.aepan.sysmgr.web.api;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aepan.sysmgr.azure.UploadFileInput;
import com.aepan.sysmgr.azure.UploadFileRps;
import com.aepan.sysmgr.model.ProductOrder;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.model.config.AlipayConfig;
import com.aepan.sysmgr.model.config.FileConfig;
import com.aepan.sysmgr.model.config.WechatpayConfig;
import com.aepan.sysmgr.model.log.OperationLog;
import com.aepan.sysmgr.model.packageinfo.PackageFlow;
import com.aepan.sysmgr.model.packageinfo.PackageInfo;
import com.aepan.sysmgr.model.packageinfo.PackageStat;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.OrderService;
import com.aepan.sysmgr.service.PackageService;
import com.aepan.sysmgr.service.PackageStatService;
import com.aepan.sysmgr.service.PartnerDataService;
import com.aepan.sysmgr.service.StorageService;
import com.aepan.sysmgr.service.UserService;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.StringUtil;
import com.aepan.sysmgr.util.UuidUtil;
import com.aepan.sysmgr.web.api.pay.AlipayCore;
import com.aepan.sysmgr.web.api.pay.AlipayNotify;
import com.aepan.sysmgr.web.api.pay.ClientResponseHandler;
import com.aepan.sysmgr.web.api.pay.ErrorInfo;
import com.aepan.sysmgr.web.api.pay.MD5Util;
import com.aepan.sysmgr.web.api.pay.PayConstants;
import com.aepan.sysmgr.web.api.pay.ResponseHandler;
import com.aepan.sysmgr.web.api.pay.TenPayRequestHandler;
import com.aepan.sysmgr.web.api.pay.TenpayHttpClient;
import com.aepan.sysmgr.web.api.pay.XMLUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 
 * @author hairry.zhou
 *
 * 2015年3月30日
 */

@Controller
public class PayRest {
	
	private static final Logger logger = LoggerFactory.getLogger(PayRest.class);
	@Autowired
	private StorageService storageService;
	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PackageService packageService;
	
	@Autowired
	PackageStatService packageStatService;
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	private PartnerDataService partnerDataService;
	
	@RequestMapping("/pay/platformPay")
    public ModelAndView platformPay(
    		@RequestParam("orderId") String orderId, 
    		@RequestParam("payType") Integer payType,   
    		HttpServletRequest request,
    		HttpServletResponse response) throws UnsupportedEncodingException{
		
		ModelAndView mav = new ModelAndView();
		ProductOrder order = null;
		try {
			 order  = orderService.getByOrderId(orderId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			payErrorPage(mav,
					ErrorInfo.CODE_CAN_NOT_FOUND_ORDER,
					ErrorInfo.DESC_CAN_NOT_FOUND_ORDER);
			return  mav;
		}
		logger.info("order:"+order);
		
		if(order.getOrderStatus()!=0){
			payErrorPage(mav,
					ErrorInfo.CODE_ORDER_STATUS_ERROR,
					ErrorInfo.DESC_ORDER_STATUS_ERROR);
		}
		
		
		if(order.getPrice()<=0){
			payErrorPage(mav,
					ErrorInfo.CODE_ORDER_PRICE_ERROR,
					ErrorInfo.DESC_ORDER_PRICE_ERROR);
		}
		
		if(payType==PayConstants.TYPE_ALIPAY){
				
			alipay(request, mav, order);
			
		}else if(payType==PayConstants.TYPE_TENPAY){
			
			tenpay(request, response, mav, order);
			
		}else if(payType==PayConstants.TYPE_WECHAT||payType==PayConstants.TYPE_WECHATQRCODEWAP){
			
			wechatPay(request, mav, order,payType);
			
		}else if(payType==PayConstants.TYPE_WECHATWAP){
			wechatwapPay(request, mav, order,payType);
		}else if(payType==PayConstants.TYPE_ALIPAY_WAP
				){
			alipaywap(mav, order);
		}else{
			payErrorPage(mav,
					ErrorInfo.CODE_CAN_NOT_FOUND_PAYTYPE,
					ErrorInfo.DESC_CAN_NOT_FOUND_PAYTYPE);
		}
		
		return mav;
	}


	private void payErrorPage(ModelAndView mav,String code,String desc) {
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("code", code);
		sParaTemp.put("message", desc);
		mav.addObject("params", sParaTemp );
		mav.setViewName("paypage/error");
	}



	private void alipaywap(ModelAndView mav, ProductOrder order) {
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alipay.wap.create.direct.pay.by.user");
		AlipayConfig alipayConfig = ConfigManager.getInstance().getAlipayConfig(configService);

		sParaTemp.put("partner", alipayConfig.partner);
		sParaTemp.put("seller_id", alipayConfig.sellerEmail);
		sParaTemp.put("_input_charset", PayConstants.input_charset);
		sParaTemp.put("payment_type", "1");
		String showUrl="rrr";
		FileConfig fileConfig = ConfigManager.getInstance().getFileConfig(configService);
		sParaTemp.put("notify_url", fileConfig.PAY_NOTIFY_URL+PayConstants.ALIPAY_NOTIFY_URL);
		sParaTemp.put("return_url", fileConfig.PAY_NOTIFY_URL+PayConstants.ALIPAY_RETURN_URL);
		String outTradeNo = order.getOrderId();
		sParaTemp.put("out_trade_no", outTradeNo);
		sParaTemp.put("subject", order.getProductNames());
		sParaTemp.put("total_fee", order.getPrice()+"");
		sParaTemp.put("show_url", showUrl);
//			sParaTemp.put("body", order.getProductId()+"dd");
		sParaTemp.put("it_b_pay", "");
		sParaTemp.put("extern_token", "");
		
		sParaTemp=buildRequestPara(sParaTemp,alipayConfig.key);
		logger.debug(sParaTemp.get("sign"));
		sParaTemp.put("paygateway", PayConstants.ALIPAY_GATEWAY_NEW);
		mav.setViewName("/paypage/request/alipaywap");
		mav.addObject("params", sParaTemp);
	}
	
	
	
	
	


	private void wechatPay(HttpServletRequest request, ModelAndView mav,
			ProductOrder order,int payType) throws UnsupportedEncodingException {
		FileConfig fileConfig = ConfigManager.getInstance().getFileConfig(configService);
		WechatpayConfig wechatConfig = ConfigManager.getInstance().getWechatpayConfig(configService);

		String codeImgUrl = order.getWxCodeImgUrl();
		
		if(codeImgUrl!=null&&!codeImgUrl.isEmpty()){
			
			Date createTime = order.getWxCodeUpdateTime();
			Calendar nowCal = Calendar.getInstance();
			nowCal.add(Calendar.MINUTE, -115);
			if(nowCal.getTime().before(createTime)){
				Map<String, String> rParaTemp = new HashMap<String, String>();
				int storage = ConfigManager.getInstance().getStorageConfig().getStoragePlatform();
				String imgPre = ConfigManager.imgPre(storage);
				rParaTemp.put("imgUrl", imgPre+codeImgUrl); 
				rParaTemp.put("totalFee", order.getPrice()+"");
				if(payType==PayConstants.TYPE_WECHAT){
					mav.setViewName("/paypage/request/wechat");
				}else if(payType==PayConstants.TYPE_WECHATQRCODEWAP){
					mav.setViewName("/paypage/request/wechatqrcodewap");
				}
				
				mav.addObject("params", rParaTemp);
				return;
			}else{
				order.setOrderId(StringUtil.getOrderNo());
			}
		}
		
		String remoteAddr = request.getRemoteAddr();
		SortedMap<String, String> sParaTemp = new TreeMap<String, String>();
		String outTradeNo = order.getOrderId();
		String nonceStr = UuidUtil.get32UUID();
		
		sParaTemp.put("appid", wechatConfig.appId);
		sParaTemp.put("mch_id", wechatConfig.mchId);
		sParaTemp.put("out_trade_no", outTradeNo);
		sParaTemp.put("nonce_str", nonceStr);
		
		long totalFee = (long) (order.getPrice()*100);
		
		sParaTemp.put("total_fee", totalFee+"");
		sParaTemp.put("notify_url", fileConfig.PAY_NOTIFY_URL+PayConstants.WECHAT_NOTIFY_URL);
		sParaTemp.put("trade_type", "NATIVE");
		sParaTemp.put("product_id", order.getProductId()+"");
		sParaTemp.put("device_info", "WEB");
		sParaTemp.put("spbill_create_ip", remoteAddr);
		String productName = order.getProductNames();
		if(productName!=null){
			productName=StringUtil.specialCharFilter(productName);
			if(productName.length()>32){
				productName=productName.substring(0, 31);
			}
		}
		sParaTemp.put("body", productName);//TODO 产品简单描述

		String sign = createSign(sParaTemp,wechatConfig.apiKey);

		sParaTemp.put("sign", sign);
		
		String xml = arrayToXml(sParaTemp);
		
		logger.debug(xml);
		
		StringEntity myEntity = new StringEntity(xml, "utf-8"); 
		
		try {
			
		    HttpPost httpPost = new HttpPost(PayConstants.WINXINPAY_GATEWAY_NEW);
		    httpPost.addHeader("Content-Type", "text/xml"); 

		    httpPost.setEntity(myEntity);
		    HttpClient httpclient = new DefaultHttpClient();  
		    HttpResponse httpResponse = httpclient.execute(httpPost);
			
		    HttpEntity entity = httpResponse.getEntity();    
		    if (entity != null) {
		    	
		        InputStream inputStream = entity.getContent();   
		        SAXReader reader = new SAXReader();
		        Document document= reader.read(inputStream);
		        Element root = document.getRootElement();
		        String return_msg=root.element("return_msg").getText();
		        logger.debug("return_msg:"+return_msg);
		        String codeUrl = root.element("code_url").getText();
		        String prepayId = root.element("prepay_id").getText();
		        
		        logger.debug("codeUrl:"+codeUrl+",prepayId"+prepayId);
		        logger.debug(root.element("return_code").getText());
		        logger.debug(root.element("return_msg").getText());
		        int x=500,y=500;
		        Hashtable hints = new Hashtable();
		        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		        BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE, x, y,hints);
		        
		        BufferedImage image = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
				for (int width = 0; width < x; width++) {
					for (int height = 0; height < y;height++) {
						image.setRGB(width, height, bitMatrix.get(width, height) == true ? PayConstants.BLACK : PayConstants.WHITE);
					}
				}
				
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
				ImageIO.write(image, "gif", outStream);  
				byte[] byteArray = outStream.toByteArray();
				InputStream is = new ByteArrayInputStream(byteArray);  
				String payImageDir =fileConfig.IMG_AZURE_PAY_DIR;
				
//				UploadFileRps rps = AzureSDK.upImg(payImageDir, codeUrl+".gif", is, byteArray.length);
				
				UploadFileRps rps = storageService.uploadImg(new UploadFileInput(codeUrl+".gif", byteArray, false));

				
				order.setWxPrepayId(prepayId);
				order.setWxCodeImgUrl(rps.imgUrl);
				order.setWxCodeUpdateTime(new Date());
				orderService.update(order, Constants.UPDATE_WXCODE_IMG_URL);
				int storage = ConfigManager.getInstance().getStorageConfig().getStoragePlatform();
				String imgPre = ConfigManager.imgPre(storage);
				Map<String, String> rParaTemp = new HashMap<String, String>();
				rParaTemp.put("imgUrl", imgPre+rps.imgUrl); 
				rParaTemp.put("totalFee", order.getPrice()+"");
				if(payType==PayConstants.TYPE_WECHAT){
					mav.setViewName("/paypage/request/wechat");
				}else if(payType==PayConstants.TYPE_WECHATQRCODEWAP){
					mav.setViewName("/paypage/request/wechatqrcodewap");
				}
				
				mav.addObject("params", rParaTemp);
			
		    }
		
		} catch (Exception e) {
			logger.error("edd", e);
		}
	}
	
	
	
	private void wechatwapPay(HttpServletRequest request, ModelAndView mav,
			ProductOrder order,int payType) throws UnsupportedEncodingException {
		FileConfig fileConfig = ConfigManager.getInstance().getFileConfig(configService);
		WechatpayConfig wechatConfig = ConfigManager.getInstance().getWechatpayConfig(configService);

		String remoteAddr = request.getRemoteAddr();
		SortedMap<String, String> sParaTemp = new TreeMap<String, String>();
		String outTradeNo = order.getOrderId();
		String nonceStr = UuidUtil.get32UUID();
		
		sParaTemp.put("appid", wechatConfig.appId);
		sParaTemp.put("mch_id", wechatConfig.mchId);
		sParaTemp.put("out_trade_no", outTradeNo);
		sParaTemp.put("nonce_str", nonceStr);
		
		long totalFee = (long) (order.getPrice()*100);
		
		sParaTemp.put("total_fee", totalFee+"");
		sParaTemp.put("notify_url", fileConfig.PAY_NOTIFY_URL+PayConstants.WECHAT_NOTIFY_URL);
		sParaTemp.put("trade_type", "WAP");
		sParaTemp.put("product_id", order.getProductId()+"");
		sParaTemp.put("device_info", "WEB");
		sParaTemp.put("spbill_create_ip", remoteAddr);
		String productName = order.getProductNames();
		if(productName!=null){
			productName=StringUtil.specialCharFilter(productName);
			if(productName.length()>32){
				productName=productName.substring(0, 31);
			}
		}
		sParaTemp.put("body", productName);//TODO 产品简单描述

		String sign = createSign(sParaTemp,wechatConfig.apiKey);

		sParaTemp.put("sign", sign);
		
		String xml = arrayToXml(sParaTemp);
		
		logger.debug("xml:"+xml);
		
		StringEntity myEntity = new StringEntity(xml, "utf-8"); 
		
		
		
		try {
			
		    HttpPost httpPost = new HttpPost(PayConstants.WINXINPAY_GATEWAY_NEW);
		    httpPost.addHeader("Content-Type", "text/xml"); 

		    httpPost.setEntity(myEntity);
		    HttpClient httpclient = new DefaultHttpClient();  
		    HttpResponse httpResponse = httpclient.execute(httpPost);
			
		    HttpEntity entity = httpResponse.getEntity();    
		    if (entity != null) {
		    	
		        InputStream inputStream = entity.getContent();   
		        SAXReader reader = new SAXReader();
		        Document document= reader.read(inputStream);
		        Element root = document.getRootElement();
		        String return_msg=root.element("return_msg").getText();
		        logger.debug("return_msg:"+return_msg);
		        String codeUrl = root.element("code_url").getText();
		        String prepayId = root.element("prepay_id").getText();
		        
		        logger.debug("codeUrl:"+codeUrl+",prepayId"+prepayId);
		        logger.debug(root.element("return_code").getText());
		        logger.debug(root.element("return_msg").getText());
		       
				order.setWxPrepayId(prepayId);
				order.setWxCodeUpdateTime(new Date());
				orderService.update(order, Constants.UPDATE_WXCODE_IMG_URL);
				
				SortedMap<String, String> wapParam = new TreeMap<String, String>();
				
				wapParam.put("appid", wechatConfig.appId);
				wapParam.put("nonce_str", UuidUtil.get32UUID());
				wapParam.put("package", "WAP");
				wapParam.put("prepayid", prepayId);
				wapParam.put("timestamp", System.currentTimeMillis()/1000+"");
				
				String wapsign = createSign(wapParam,wechatConfig.apiKey);
				
				wapParam.put("sign", wapsign);
				
				StringBuffer string1Buf=new StringBuffer();
				
				Set<Entry<String, String>> wapParamSet = wapParam.entrySet();
				for (Entry<String, String> entry : wapParamSet) {
					string1Buf.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue())).append("&");
				}
				String string1 = string1Buf.substring(0, string1Buf.length()-1);
				
				logger.debug("String1:"+string1);
				String string2=URLEncoder.encode(string1);
				logger.debug("string2:"+string2);
				
				String deeplink = "weixin://wap/pay?"+string2;
				logger.debug("deeplink:"+deeplink);
				
				mav.setViewName("/paypage/request/wechatdeepwap");
				
				mav.addObject("deeplink", deeplink);
			
		    }
		
		} catch (Exception e) {
			logger.error("edd", e);
		}
	}
	


	
	private void alipay(HttpServletRequest request, ModelAndView mav,
			ProductOrder order) {
		AlipayConfig alipayConfig = ConfigManager.getInstance().getAlipayConfig(configService);

		String remoteAddr = request.getRemoteAddr();
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user"); 
		sParaTemp.put("partner", alipayConfig.partner);
		sParaTemp.put("seller_email", alipayConfig.sellerEmail);
		sParaTemp.put("_input_charset", PayConstants.input_charset);
		sParaTemp.put("payment_type", "1");
		FileConfig fileConfig = ConfigManager.getInstance().getFileConfig(configService);
		String serverURL = fileConfig.PAY_NOTIFY_URL;
		sParaTemp.put("return_url",serverURL+PayConstants.ALIPAY_RETURN_URL);
		sParaTemp.put("notify_url", serverURL+PayConstants.ALIPAY_NOTIFY_URL);
		String outTradeNo = order.getOrderId();
		sParaTemp.put("out_trade_no", outTradeNo+"");
		sParaTemp.put("subject", order.getProductNames());
		sParaTemp.put("total_fee", order.getPrice()+"");
		sParaTemp.put("body", order.getProductNames());
		sParaTemp.put("show_url", "http://www.cinsayyu.cc/");
		sParaTemp.put("exter_invoke_ip", remoteAddr);
		sParaTemp=buildRequestPara(sParaTemp,alipayConfig.key);
		sParaTemp.put("paygateway", PayConstants.ALIPAY_GATEWAY_NEW);
		mav.setViewName("/paypage/request/alipay");
		mav.addObject("params", sParaTemp);
	}


	private void tenpay(HttpServletRequest request,
			HttpServletResponse response, ModelAndView mav,
			ProductOrder order) throws UnsupportedEncodingException {

		//创建支付请求对象
		TenPayRequestHandler reqHandler = new TenPayRequestHandler(request, response);
		reqHandler.init();
		//设置密钥
		reqHandler.setKey(PayConstants.tenpayKey);
		reqHandler.setGateUrl("https://gw.tenpay.com/gateway/pay.htm");

		String outTradeNo = order.getOrderId();
		
		long totalFee = (long) (order.getPrice()*100);
		
		reqHandler.setParameter("partner", PayConstants.tenpayPartner);		        //商户号
		reqHandler.setParameter("out_trade_no", outTradeNo);		//商家订单号
		reqHandler.setParameter("total_fee", totalFee+"");			        //商品金额,以分为单位
		FileConfig fileConfig = ConfigManager.getInstance().getFileConfig(configService);
		reqHandler.setParameter("return_url", fileConfig.PAY_NOTIFY_URL+PayConstants.tenpayReturnUrl);		    //交易完成后跳转的URL
		reqHandler.setParameter("notify_url", fileConfig.PAY_NOTIFY_URL+PayConstants.tenpayNotifyUrl);		    //接收财付通通知的URL
		reqHandler.setParameter("body", order.getProductNames());	                    //商品描述
		reqHandler.setParameter("bank_type", "DEFAULT");		    //银行类型
		reqHandler.setParameter("spbill_create_ip",request.getRemoteAddr());   //用户的公网ip
		reqHandler.setParameter("fee_type", "1");

		//系统可选参数
		reqHandler.setParameter("sign_type", "MD5");
		reqHandler.setParameter("service_version", "1.0");
		reqHandler.setParameter("input_charset", "GBK");
		reqHandler.setParameter("sign_key_index", "1");

		
		//---------------生成订单号 开始------------------------
		//当前时间 yyyyMMddHHmmss
		String currTime = StringUtil.getStrDate(new Date());
		//业务可选参数
		reqHandler.setParameter("attach", "");
		reqHandler.setParameter("product_fee", totalFee+"");
		reqHandler.setParameter("transport_fee", "0");
		reqHandler.setParameter("time_start", currTime);
		reqHandler.setParameter("time_expire", "");

		reqHandler.setParameter("buyer_id", "");
		reqHandler.setParameter("goods_tag", "");
		//reqHandler.setParameter("agentid", "");
		//reqHandler.setParameter("agent_type", "");

		//请求的url
		String tenpayUrl = reqHandler.getRequestURL();

		//获取debug信息,建议把请求和debug信息写入日志，方便定位问题
		String debuginfo = reqHandler.getDebugInfo();

		logger.debug("requestUrl:" + tenpayUrl);
		logger.debug("debuginfo:" + debuginfo);
		
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("tenpayUrl", tenpayUrl); 
		mav.setViewName("/paypage/request/tenpay");
		mav.addObject("params", sParaTemp);
	}

	
	@RequestMapping(value = "/pay/alipay_notify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
    public @ResponseBody String alipayNotify(HttpServletRequest request) throws Exception{
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		String outTradeOrder = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		
		logger.info("alipayNotify outTradeOrder:"+outTradeOrder+",trade_no:"+tradeNo+",trade_status:"+tradeStatus);
		ProductOrder order = orderService.getByOrderId(outTradeOrder);
		logger.info("notify order:"+order);
		order.setOutOrderId(tradeNo);
		AlipayConfig alipayConfig = ConfigManager.getInstance().getAlipayConfig(configService);

		if(AlipayNotify.verify(params,alipayConfig.key,alipayConfig.partner)){//验证成功
			if(order==null){
				return "fail";
			}
			if(tradeStatus.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
					
				//注意：
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
			} else if (tradeStatus.equals("TRADE_SUCCESS")){
				 paySuccess(order);
			}
				
			return "success";
		}else{//验证失败
			return "fail";
		}
		
	}

	/**
	 * 更新用户套餐信息
	 * @param order
	 */
	private void updateUserPackage(ProductOrder order) {
		int userId = order.getBuyersId();
		int packageId = Integer.parseInt(order.getProductId()[0]);
		User user = userService.getByUserId(userId);
		
		PackageInfo packageInfo = packageService.getById(packageId);
		
		float flowNum = packageInfo.getFlowNum()*1024;
		PackageStat packageStat = packageStatService.getByUserId(userId);
		String logMsg = "";
		if(PackageInfo.PACKAGE_TYPE_PACKAGE==packageInfo.getPackageType()){
			//如果购买套餐和已有套餐不同，修改用户套餐
			/*if(user.getPackageId()==0||user.getPackageId()!=packageId){
				packageStat.setPackageId(packageId);
				int duration=order.getAmount();
				packageStat.setDuration(duration);//设置套餐有效期（月）
				Calendar cal = Calendar.getInstance();
				packageStat.setStartime(cal.getTime());
				cal.add(Calendar.MONTH, duration);
				packageStat.setEndTime(cal.getTime());//直接替换为新套餐有效期，不累加
				packageStat.setFlowNum(packageStat.getFlowNum()+flowNum);//累加流量
				packageStatService.updatePackageStat(packageStat);
				user.setPackageId(packageId);
				userService.update(user);
				logMsg = "开通或购买新套餐";
			}else{
				int duration=order.getAmount();
				packageStat.setDuration(duration);//设置套餐有效期（月）
				Calendar cal = Calendar.getInstance();
				packageStat.setStartime(cal.getTime());
				Date endTime = packageStat.getEndTime();
				if(endTime!=null&&endTime.after(cal.getTime())){
					cal.setTime(endTime);
				}
				cal.add(Calendar.MONTH, duration);
				packageStat.setEndTime(cal.getTime());//续约套餐，累加有效期
				packageStat.setFlowNum(packageStat.getFlowNum()+flowNum);//累加流量
				packageStatService.updatePackageStat(packageStat);
				logMsg = "续约套餐";
			}*/
			//一刀切，旧套餐废弃，新套餐立即生效；已使用流量归零，流量最后统计时间设为当前时间，拥有流量重置为当前套餐流量。
			packageStat.setPackageId(packageId);
			int duration=order.getAmount();
			Calendar cal = Calendar.getInstance();
			//设置流量
			packageStat.setUsedFlowNum(0);
			packageStat.setUsedFlowCountTime(cal.getTime());
			packageStat.setFlowNum(flowNum);
			//设置有效期
			packageStat.setDuration(duration);//设置套餐有效期（月）
			packageStat.setStartime(cal.getTime());
			cal.add(Calendar.MONTH, duration);
			packageStat.setEndTime(cal.getTime());
			packageStatService.updatePackageStat(packageStat);
			if(user.getPackageId()!=packageId){
				if(user.getPackageId()==0){
					//通知第三方 已开通视频套餐
					partnerDataService.sendMsgOfOpenVideoStore(user.getPartnerAccountId(), packageId);
				}
				user.setPackageId(packageId);
				userService.update(user);
			}
			logMsg = "续约套餐";
		}else if(PackageInfo.PACKAGE_TYPE_FLOW==packageInfo.getPackageType()){
			packageStat.setFlowNum(packageStat.getFlowNum()+flowNum);//累加流量
			packageStatService.updatePackageStat(packageStat);
			logMsg = "购买流量";
		}else{
			
		}
		
		if(packageInfo.getFlowNum()!=0){//流量日志
			
			Calendar nowCal = Calendar.getInstance();
			
			PackageFlow packageFlow  = new PackageFlow();
			packageFlow.setUserId(userId);
			packageFlow.setFlowNum(packageInfo.getFlowNum());
			packageFlow.setStartTime(nowCal.getTime());
			nowCal.add(Calendar.MONTH, 1);
			packageFlow.setEndTime(nowCal.getTime());
			
			packageStatService.addPackageFlow(packageFlow);
		}
		
		
		//记录操作日志
		partnerDataService.addLog(new OperationLog(OperationLog.TYPE_套餐, 
						user.getPartnerAccountId(),
						user.getPartnerAccountName(),
						"/package/updateuserpackage", 
						logMsg+":"+packageStat.toString(), 
						order.getOrderId()));
		
		
	}
	
	
	@RequestMapping("/pay/alipay_return")
    public ModelAndView alipayReturn(){
		
		 ModelAndView mav = new ModelAndView();
		 logger.info(" ---------------alipay_return");
		 mav.setViewName("paypage/return/alipay");
		 return mav;
	}
	
	
	
	
	@RequestMapping("/pay/tenpay_return")
    public ModelAndView tenpayReturn(){
		 ModelAndView mav = new ModelAndView();
		 logger.info(" ---------------tenpay_return");
		 mav.setViewName("paypage/return/tenpay");
		 return mav;
		
	}
	
	
	@RequestMapping(value = "/pay/wechatpay_notify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public String wechatPayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException{
		WechatpayConfig wechatConfig = ConfigManager.getInstance().getWechatpayConfig(configService);

		BufferedReader br = request.getReader();
		String line = null;
		StringBuffer xmlMsg = new StringBuffer();
		while ((line = br.readLine()) != null) {
			xmlMsg.append(line);
		}
		
		Map map = XMLUtil.doXMLParse(xmlMsg.toString());
		TreeMap<String, String> sortMap = new TreeMap<String, String>();
		sortMap.putAll(map);
		
		String wechatsign = (String) map.get("sign");
		String resultCode=(String) map.get("result_code");
		String orderId = (String) map.get("out_trade_no");
		String transactionId = (String) map.get("transaction_id");
		
		
		
		logger.debug("sortMap:"+sortMap);
		String sign = createSign(sortMap,wechatConfig.apiKey);
		logger.debug("wechat notify----sign:"+sign+",orderId:"+orderId+",transactionId:"+transactionId+",resultCode:"+resultCode+",wechatsign:"+wechatsign);
		
		if(sign.equals(wechatsign)&&"SUCCESS".equals(resultCode)){
			
			ProductOrder order = orderService.getByOrderId(orderId);
			order.setOutOrderId(transactionId);
			paySuccess(order);
		}
		//
		return "success";
		
	}

	
	
	

	private void paySuccess(ProductOrder order) {
		
		if(ProductOrder.ORDER_STATUS_INI!=order.getOrderStatus()){
			logger.info("notifyOrderStatus:"+order);
			return;
		}
		order.setOrderStatus(ProductOrder.ORDER_STATUS_PAY_SUCCESS);
		orderService.update(order, Constants.UPDATE_ORDER_STATUS_AND_OUT_ORDER_ID);
		
		if(ProductOrder.PAY_FOR_PACKAGE==order.getPayfor()){
			 updateUserPackage(order);
		}
	}
	
	

	
	@RequestMapping(value = "/pay/tenpay_notify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	public void tenpayNotify(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		ResponseHandler resHandler = new ResponseHandler(request, response);
		
		resHandler.setKey(PayConstants.tenpayKey);

		logger.debug("----------------tenpayNotify!");
		//判断签名
		if(resHandler.isTenpaySign()) {
			
			//通知id
			String notify_id = resHandler.getParameter("notify_id");
			
			//创建请求对象
			TenPayRequestHandler queryReq = new TenPayRequestHandler(request, response);
			//通信对象
			TenpayHttpClient httpClient = new TenpayHttpClient();
			//应答对象
			ClientResponseHandler queryRes = new ClientResponseHandler();
			logger.info("notify_id:"+notify_id);
			//通过通知ID查询，确保通知来至财付通
			queryReq.init();
			queryReq.setKey(PayConstants.tenpayKey);
			queryReq.setGateUrl("https://gw.tenpay.com/gateway/verifynotifyid.xml");
			queryReq.setParameter("partner", PayConstants.tenpayPartner);
			queryReq.setParameter("notify_id", notify_id);
			
			//通信对象
			httpClient.setTimeOut(5);
			//设置请求内容
			httpClient.setReqContent(queryReq.getRequestURL());
			logger.debug("queryReq:" + queryReq.getRequestURL());
			//后台调用
			if(httpClient.call()) {
				//设置结果参数
				queryRes.setContent(httpClient.getResContent());
				logger.debug("queryRes:" + httpClient.getResContent());
				queryRes.setKey(PayConstants.tenpayKey);
					
					
				//获取返回参数
				String retcode = queryRes.getParameter("retcode");
				String trade_state = queryRes.getParameter("trade_state");
			
				String trade_mode = queryRes.getParameter("trade_mode");
				
				boolean isTenpaySign = queryRes.isTenpaySign();
				logger.info("queryRes.isTenpaySign():"+isTenpaySign);
				//判断签名及结果
				if(isTenpaySign&& "0".equals(retcode) && "0".equals(trade_state) && "1".equals(trade_mode)) {
					logger.debug("订单查询成功");
					
					String orderId=queryRes.getParameter("out_trade_no");
					String transactionId = queryRes.getParameter("transaction_id");
					//取结果参数做业务处理				
					logger.debug("out_trade_no:" + queryRes.getParameter("out_trade_no")+
							" transaction_id:" + transactionId);
					logger.debug("trade_state:" + queryRes.getParameter("trade_state")+
							" total_fee:" + queryRes.getParameter("total_fee"));
					
				        //如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee
					logger.debug("discount:" + queryRes.getParameter("discount")+
							" time_end:" + queryRes.getParameter("time_end"));
					ProductOrder order = orderService.getByOrderId(orderId);
					order.setOutOrderId(transactionId);
					paySuccess(order);
					
			        logger.info("updateOrder:"+order);
					resHandler.sendToCFT("Success");
				}
				else{
						//错误时，返回结果未签名，记录retcode、retmsg看失败详情。
						logger.debug("查询验证签名失败或业务错误");
						logger.debug("retcode:" + queryRes.getParameter("retcode")+
								" retmsg:" + queryRes.getParameter("retmsg"));
				}
			
			} else {

				logger.debug("后台调用通信失败");
					
				logger.debug(httpClient.getResponseCode()+"");
				logger.debug(httpClient.getErrInfo());
				//有可能因为网络原因，请求已经处理，但未收到应答。
			}
		}
		else{
			logger.debug("通知签名验证失败");
		}

	}
	
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp,String key) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
       //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String prestr = AlipayCore.createLinkString(sPara); 
		logger.info("--prestr:"+prestr);
		String mysign = MD5Util.sign(prestr, key, PayConstants.input_charset);
		logger.info("mysign:"+mysign);
        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", PayConstants.sign_type);

        return sPara;
    }
    
    
    
	protected static String createSign(SortedMap<String, String> sParaTemp,String apiKey) {
		StringBuffer sb = new StringBuffer();
		Set es = sParaTemp.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + apiKey);
		logger.debug("stringSignTemp:"+sb.toString());
		
		String sign =    MD5Util.MD5Encode(sb.toString(), PayConstants.input_charset).toUpperCase();
		
		return sign;
		
	}
	
	
	
	   private String arrayToXml(SortedMap<String,String> map)
	    {
	        StringBuffer xml = new StringBuffer("<xml>");
	        Set<String> set = map.keySet();
	        Iterator<String> iter = set.iterator();
	        while (iter.hasNext()) {
	        	String key=iter.next();
	        	String value = map.get(key);
	        	xml.append("<").append(key).append(">").append(value).append("</").append(key).append(">");
			}
	        xml.append("</xml>");

	        return xml.toString();
	    }
		
}
