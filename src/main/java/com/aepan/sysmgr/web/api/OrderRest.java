/**
 * 
 */
package com.aepan.sysmgr.web.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.common.resp.JsonResp;
import com.aepan.sysmgr.model.ProductOrder;
import com.aepan.sysmgr.model.SkusArray;
import com.aepan.sysmgr.model.SkusInfo;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.model.config.PartnerConfig;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.OrderService;
import com.aepan.sysmgr.service.UserService;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.JsonRespCode;
import com.aepan.sysmgr.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 订单Rest
 * @author rakika
 * 2015年8月2日下午9:19:57
 */
@Controller
@RequestMapping( value = "/rest/order" )
public class OrderRest {

	private static final Logger logger = LoggerFactory.getLogger(OrderRest.class);	
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ConfigService configService;
	
	
	
	
	//收到order的订单生成通知
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public String create( HttpServletRequest request, HttpServletResponse response ) throws IOException {
//			channel_id : 外部交易流水号  任意唯一
//			order_id : 订单ID YYYYMMDDXXXXX
//			buyers_id : 用户ID ，账号系统提供
//			buyers_name : 买家名
//			seller_id : 卖家， 账号系统提供
//			seller_name : 卖家名
//			price : 订单金额（单位：元）
//			to_address : 收货地址
//			to_mobile : 收货人手机号
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        String channelId = reqInfo.getParameter("channel_id");
        
        Integer buyersId = reqInfo.getIntParameter("buyers_id", -1);
        String buyersName = reqInfo.getParameter("buyers_name");
        Integer sellerId = reqInfo.getIntParameter("seller_id", -1);
//        String sellerName = reqInfo.getParameter("seller_name");
        String[] productId = request.getParameter("product_id").split(",");
//        String productNames = reqInfo.getParameter("product_name");
//        String[] quantitys = request.getParameterValues("quantitys");
        String[] quantitys = request.getParameter("quantitys").split(",");
        
        String toProvince = reqInfo.getParameter("to_province");
        String toCity = reqInfo.getParameter("to_city");
        String toArea = reqInfo.getParameter("to_area");
        
        String toAddress = reqInfo.getParameter("to_address");
        String toMobile = reqInfo.getParameter("to_mobile");
        String toMail = reqInfo.getParameter("to_mail");
        String toZipCode = reqInfo.getParameter("to_zip_code");
        String attr = reqInfo.getParameter("attr");
        
        if(StringUtils.isBlank(channelId)){
        	return responseLog("channelId", channelId, JsonRespCode.SYSTEM_ERROR, "channelId.empty"); 
        }
        if(productId.length == 0){
        	return responseLog("productId", productId + "", JsonRespCode.SYSTEM_ERROR, "productId.empty"); 
        }
        if(quantitys.length == 0){
        	return responseLog("quantitys", quantitys + "", JsonRespCode.SYSTEM_ERROR, "quantitys.empty"); 
        }
        if(productId.length != quantitys.length){
        	return responseLog("quantitys", quantitys + "", JsonRespCode.SYSTEM_ERROR, "productId.quantitys.not.equal"); 
        }
        if(StringUtils.isBlank(toAddress)){
        	return responseLog("toAddress", toAddress, JsonRespCode.SYSTEM_ERROR, "toAddress.empty"); 
        }
        if(StringUtils.isBlank(toMobile)){
        	return responseLog("toMobile", toMobile, JsonRespCode.SYSTEM_ERROR, "toMobile.empty"); 
        }
        
//        if(orderService.getByChannelId(channelId)){//判断流水号重复
//        	return responseLog("channelId", channelId, JsonRespCode.SYSTEM_ERROR, "channelId.readyExist");
//        }
        
        //判断是否存在手机号  直接在订单表中可查询到数据。
//        if(userService.isExistMobile(toMobile)){//存在，不注册手机号
//        	if(!userService.isExistAddress(toMobile, toAddress)){//判断地址是否存在过
//        		userService.buyerResgister(toMobile, toAddress);
//        	}
//        }else{//不存在，注册手机号
//            userService.buyerResgister(toMobile, toAddress);
//        }
        
        String listname = "";
        //获取skus
        List<SkusInfo> list = getProductJson(productId);
        for(int i = 0; i < list.size(); i++){
        	SkusInfo si = list.get(i);
        	logger.debug("##############:" + quantitys[i]);
        	if(Integer.parseInt(si.getStock()) < Integer.parseInt(quantitys[i])){
        		return responseLog("stock", si.getStock(), JsonRespCode.SYSTEM_ERROR, "stock.notEnough");
        	}
        	if(i + 1 < list.size()){
        		listname = listname + si.getProductName() + ",";
        	}else{
        		listname = listname + si.getProductName();
        	}
        	
        }
        //减少库存
        updateProductStock(productId, quantitys);        
        
        ProductOrder productOrder = new ProductOrder();
        productOrder.setChannelId(channelId);
        //生成订单号
        String orderId = StringUtil.getOrderNo();
        productOrder.setOrderId(orderId);
        productOrder.setBuyersId(buyersId);
        productOrder.setBuyersName(buyersName);
        productOrder.setSellerId(sellerId);
        
        User user = userService.getByUserId(sellerId);
        if(user!=null){
        	productOrder.setSellerName(user.getPartnerAccountName());
        }
        
        productOrder.setToMail(toMail);
        productOrder.setToZipCode(toZipCode);
        productOrder.setAttr(attr);
        
        productOrder.setSellerId(sellerId);
       
        productOrder.setProductId(productId);
        productOrder.setProductNames(listname);
        productOrder.setQuantity(quantitys);
        
        productOrder.setToProvince(toProvince);
        productOrder.setToCity(toCity);
        productOrder.setToArea(toArea);
        
        Float fprice = (float) 0.00;
        //计算价格
        for(int i = 0; i < list.size(); i++ ){
        	SkusInfo si = list.get(i);
        	fprice = fprice + si.getSalePrice()*Integer.parseInt(quantitys[i]);
        }
        
        productOrder.setPrice(fprice);
        productOrder.setToAddress(toAddress);
        productOrder.setToMobile(toMobile);
        productOrder.setPayfor(ProductOrder.PAY_FOR_PRODUCT);
        if(orderService.create(productOrder)){
        	return responseLog(buyersId + "", orderId, JsonRespCode.SUCCESS, orderId); 
        }else{
        	return responseLog(buyersId + "", orderId, JsonRespCode.OTHER_FAILD, "create fail"); 
        }
	} 
	
	
	@RequestMapping(value = "/testpost", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public String testPost( HttpServletRequest request, HttpServletResponse response ) throws IOException {
		return "You are success!";
	}
	
	//更新order的相关状态
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public String update( HttpServletRequest request, HttpServletResponse response ) throws IOException {
//			channel_id : 交易流水号  任意唯一
//			order_id : 订单ID YYYYMMDDXXXXX
//			buyers_id : 用户ID ，账号系统提供
//			buyers_name : 买家名
//			seller_id : 卖家， 账号系统提供
//			seller_name : 卖家名
//			price : 订单金额（单位：元）
//			to_address : 收货地址
//			to_mobile : 收货人手机号
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        String channelId = reqInfo.getParameter("channel_id");
        String orderId = reqInfo.getParameter("order_id");
        Float price = reqInfo.getFloatParameter("price");
        int method = reqInfo.getIntParameter("method", 0);
        int orderStatus = reqInfo.getIntParameter("order_status", -1);
        int deliverStatus = reqInfo.getIntParameter("deliver_status", -1);
        
        
        if(StringUtils.isBlank(channelId)){
        	return responseLog("channelId", channelId, JsonRespCode.SYSTEM_ERROR, "channelId.empty"); 
        }
        if(StringUtils.isBlank(orderId)){
        	return responseLog("orderId", orderId, JsonRespCode.SYSTEM_ERROR, "orderId.empty"); 
        }
        if(method != Constants.UPDATE_PRICE && method != Constants.UPDATE_ORDER_STATUS
        		&& method != Constants.UPDATE_GOOD_STATUS && method != Constants.UPDATE_ORDER_CANCEL){
        	return responseLog("method", method + "", JsonRespCode.SYSTEM_ERROR, "method.error"); 
        }
        if(method == Constants.UPDATE_PRICE && price < 0){
        	return responseLog("price", price + "", JsonRespCode.SYSTEM_ERROR, "price.error"); 
        }
        if(method == Constants.UPDATE_ORDER_STATUS && orderStatus == -1){
        	return responseLog("orderStatus", orderStatus + "", JsonRespCode.SYSTEM_ERROR, "orderStatus.error");
        }
        if(method == Constants.UPDATE_GOOD_STATUS && deliverStatus == -1){
        	return responseLog("deliverStatus", deliverStatus + "", JsonRespCode.SYSTEM_ERROR, "deliverStatus.error");
        }
        
        //更新订单价格
		if(method == Constants.UPDATE_PRICE){
			 ProductOrder productOrder = orderService.getByNo(channelId, orderId);
	         if(productOrder == null || productOrder.getOrderStatus() == 3){
	        	 return responseLog("productOrder", orderId + "", JsonRespCode.SYSTEM_ERROR, "orderNo.not.Exist");
	         }
	         productOrder.setPrice(price);
	           
	         if(orderService.update(productOrder, Constants.UPDATE_PRICE)){
	        	 return responseLog(orderId, orderId, JsonRespCode.SUCCESS, "success"); 
	         }else{
	        	 return responseLog(orderId, orderId, JsonRespCode.OTHER_FAILD, "update price fail"); 
	         }
		}
		//更新订单状态
        if(method == Constants.UPDATE_ORDER_STATUS){
        	 ProductOrder productOrder = orderService.getByNo(channelId, orderId);
	         if(productOrder == null || productOrder.getOrderStatus() == 3){
	        	 return responseLog("productOrder", orderId + "", JsonRespCode.SYSTEM_ERROR, "orderNo.not.Exist");
	         }
	         
	         /**
	          * 订单状态不可逆
	          */
	         if(orderStatus > productOrder.getOrderStatus()){
	        	 productOrder.setOrderStatus(orderStatus);
	         }else{
	        	 return responseLog("productStatus", orderStatus + "", JsonRespCode.SYSTEM_ERROR, "orderStatus.error");
	         }
	           
	         if(orderService.update(productOrder, Constants.UPDATE_ORDER_STATUS)){
	        	 return responseLog(orderId, orderId, JsonRespCode.SUCCESS, "success"); 
	         }else{
	        	 return responseLog(orderId, orderId, JsonRespCode.OTHER_FAILD, "update order status fail"); 
	         }
        }
		//更新到货状态
        if(method == Constants.UPDATE_GOOD_STATUS){
	         //到货状态为未发货,状态错误
	         if(deliverStatus == 0){
	        	 return responseLog("deliverStatus", orderId + "", JsonRespCode.SYSTEM_ERROR, "deliverStatus.error");
	         }
        	 ProductOrder productOrder = orderService.getByNo(channelId, orderId);
	         if(productOrder == null || productOrder.getOrderStatus() == 3){
	        	 return responseLog("deliverStatus", orderId + "", JsonRespCode.SYSTEM_ERROR, "orderNo.not.Exist");
	         }
	         
	         /**
	          * 到货状态判断
	          */
             if(deliverStatus > 0 && deliverStatus < 4){
            	 productOrder.setDeliverStatus(deliverStatus);
             }else{
            	 return responseLog("deliverStatus", orderId + "", JsonRespCode.SYSTEM_ERROR, "deliverStatus.error");
             }
	           
	         if(orderService.update(productOrder, Constants.UPDATE_GOOD_STATUS)){
	        	 return responseLog(orderId, orderId, JsonRespCode.SUCCESS, "success"); 
	         }else{
	        	 return responseLog(orderId, orderId, JsonRespCode.OTHER_FAILD, "update good status fail"); 
	         }
        }
        //取消订单
        if(method == Constants.UPDATE_ORDER_CANCEL){
       	 ProductOrder productOrder = orderService.getByNo(channelId, orderId);
	         if(productOrder == null || productOrder.getOrderStatus() == 3){
	        	 return responseLog("productOrder", orderId + "", JsonRespCode.SYSTEM_ERROR, "orderNo.not.Exist");
	         }
	           
	         if(orderService.update(productOrder, Constants.UPDATE_ORDER_CANCEL)){
	        	 return responseLog(orderId, orderId, JsonRespCode.SUCCESS, "success"); 
	         }else{
	        	 return responseLog(orderId, orderId, JsonRespCode.OTHER_FAILD, "cancel order fail"); 
	         }
       }
        
        return responseLog(orderId, orderId, JsonRespCode.OTHER_FAILD, "param error"); 
	}
	
	/**
	 * 响应日志
	 * @param buyersId
	 * @param orderId
	 * @param code
	 * @param msgName
	 * @return
	 */
	private String responseLog(String buyersId, String orderId, int code, String msgName) {
		logger.info("responseLog code:" + code);
	    Gson gson = new GsonBuilder().setDateFormat( "yyyy-MM-dd HH:mm:ss" ).create();
	    JsonResp resp = new JsonResp();
	    
	    resp.setCode(code);
	    resp.setMsg(msgName);
        
        String json = gson.toJson(resp);
        logger.info("buyersId: " + buyersId + ", orderId: " + orderId + ", " + json);
	    return json;
	}
	
	/**
	 * 根据SKUS查询库存情况
	 * @param skus
	 * @return
	 */
	private List<SkusInfo> getProductJson(String[] skus){		
		
		/*PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(configService);
		String  productSkusInfoUrl= partnerConfig.GET_PARTNER_PRODUCT_SKUSINFO;
		
	   StringBuffer sb = new StringBuffer(productSkusInfoUrl);
	   for(int i = 0; i < skus.length; i++){
		   if(i == 0){
			   sb.append("skus=");
		   }
		   sb.append(skus[i]);
		   if(i + 1 < skus.length){
				sb.append(",");
		   }
	   }
	   
	   List<SkusInfo> rlist = new ArrayList<SkusInfo>();
	    
       GetMethod method = new GetMethod(sb.toString());

	   HttpClient client = new HttpClient();
	   logger.info(method.toString()+",url:"+sb.toString());
	   
	   String ret = "";
	   try {
			client.executeMethod(method);
			ret = method.getResponseBodyAsString();
			logger.info("ret:::::" + ret);
			
	        if(method.getStatusCode() == 200 ){
	        	Gson gson = new Gson();
	        	SkusArray sa = gson.fromJson(ret, SkusArray.class);
	        	rlist = sa.getSkus();
	        }
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return rlist;*/
		return null;
	}
	
	/**
	 * 更新库存
	 * @param stock
	 */
	private void updateProductStock(String[] skus, String[] stock){
		
		/*PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(configService);
		String  updateStokeUrl= partnerConfig.UPDATE_PRODUCT_STOKE;
		
	   StringBuffer sb = new StringBuffer(updateStokeUrl);
	   for(int i = 0; i < skus.length; i++){
		   sb.append(skus[i]);
		   if(i + 1 < skus.length){
				sb.append(",");
		   }
	   }
	   
	   for(int j = 0; j < stock.length; j++){
		   if(j == 0){
			   sb.append("&quantitys=");
		   }
		   sb.append("-" + stock[j]);
		   if(j + 1 < stock.length){
			   sb.append(",");
		   }
	   }
	    
       GetMethod method = new GetMethod(sb.toString());
	   HttpClient client = new HttpClient();
	   logger.info("updateProductStock:"+sb.toString());
	   
	   String ret = "";
	   try {
			client.executeMethod(method);
			ret = method.getResponseBodyAsString();
			logger.info("updateProductStock ret:::::" + ret);

		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}*/
	}
}