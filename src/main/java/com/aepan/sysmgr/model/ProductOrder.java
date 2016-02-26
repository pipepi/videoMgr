/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * 订单model
 * author rakika
 * 2015年8月1日下午5:07:59
 */
public class ProductOrder implements Serializable {
	
	
	public static final int PAY_FOR_PRODUCT=1;
	public static final int PAY_FOR_PACKAGE=2;
	
	
	public static final int ORDER_STATUS_INI=0;
	public static final int ORDER_STATUS_PAY_SUCCESS=1;
	
	public static final int DELIVER_STATUS_未发货 = 0;
	public static final int DELIVER_STATUS_已发货 = 1;
	public static final int DELIVER_STATUS_已收货 = 2;   //              --》买家【退换货】
	public static final int DELIVER_STATUS_申请退换货 = 3;//待确认 | 申请退换货    --》卖家【同意退换货】
	public static final int DELIVER_STATUS_退换货中 = 4;//退换货中 | 退换货中        --》买家【完成退换货】
	public static final int DELIVER_STATUS_已取货 = 5;//退换货完成 | 退换货中         --》卖家【完成退换货】
	public static final int DELIVER_STATUS_已回货 = 6;//退换货完成 | 退货货完成
	//public static final int DELIVER_STATUS_退货 = 3;
	//public static final int DELIVER_STATUS_延期 = 4;
	public final ProductOrder escapeHtml(){
		this.backMobile = StringEscapeUtils.escapeHtml(this.backMobile);
		this.backMail = StringEscapeUtils.escapeHtml(this.backMail);
		this.backAddress = StringEscapeUtils.escapeHtml(this.backAddress);
		return this;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 9197702340664833812L;

	private int id;
	
	private String channelId;
	/**
	 * 本系统订单号
	 */
	private String orderId;
	/**
	 * 支付方订单号
	 */
	private String outOrderId;
	
	private int buyersId;
	
	private String buyersName;
	
	private int sellerId;
	
	private String sellerName;
	/**买家逻辑删除*/
	private boolean buyersDelete;
	
	private String[] productId;
	
	private String productNames;
	
	private String[] quantity;
	
	private int amount;
	
	private float price;
	
	private String toProvince;
	
	private String toCity;
	
	private String toArea;
	
	private String toAddress;
	
	private String toMobile;
	
	private String toMail;
	
	private String toZipCode;
	//退货手机号
	private String backMobile;
	//退货邮箱
	private String backMail;
	//退货地址
	private String backAddress;
	
	private int orderStatus;
	
	private int deliverStatus;

	private int payType;
	
	private int payfor;
	
	//物流公司
	private String logisticsCompany;
	//物流单号
	private String logisticsNum;
	
	private String attr;
	
	private Date createTime;
	
	private Date cannelTime;
	
	private String wxCodeImgUrl;
	
	private Date wxCodeUpdateTime;
	
	private String wxPrepayId;
	
	
	
	/**
	 * @return the logisticsCompany
	 */
	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	/**
	 * @param logisticsCompany the logisticsCompany to set
	 */
	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	/**
	 * @return the logisticsNum
	 */
	public String getLogisticsNum() {
		return logisticsNum;
	}

	/**
	 * @param logisticsNum the logisticsNum to set
	 */
	public void setLogisticsNum(String logisticsNum) {
		this.logisticsNum = logisticsNum;
	}

	/**
	 * @return the productNames
	 */
	public String getProductNames() {
		return productNames;
	}

	/**
	 * @param productNames the productNames to set
	 */
	public void setProductNames(String productNames) {
		this.productNames = productNames;
	}

	/**
	 * @return the attr
	 */
	public String getAttr() {
		return attr;
	}

	/**
	 * @param attr the attr to set
	 */
	public void setAttr(String attr) {
		this.attr = attr;
	}
	
	
	public String getBackMobile() {
		return backMobile;
	}

	public void setBackMobile(String backMobile) {
		this.backMobile = backMobile;
	}

	public String getBackMail() {
		return backMail;
	}

	public void setBackMail(String backMail) {
		this.backMail = backMail;
	}

	public String getBackAddress() {
		return backAddress;
	}

	public void setBackAddress(String backAddress) {
		this.backAddress = backAddress;
	}

	/**
	 * @return the toZipCode
	 */
	public String getToZipCode() {
		return toZipCode;
	}

	/**
	 * @param toZipCode the toZipCode to set
	 */
	public void setToZipCode(String toZipCode) {
		this.toZipCode = toZipCode;
	}

	/**
	 * @return the toMail
	 */
	public String getToMail() {
		return toMail;
	}

	/**
	 * @param toMail the toMail to set
	 */
	public void setToMail(String toMail) {
		this.toMail = toMail;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the orderid
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderid the orderid to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the outOrderId
	 */
	public String getOutOrderId() {
		return outOrderId;
	}

	/**
	 * @param outOrderId the outOrderId to set
	 */
	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}

	/**
	 * @return the buyersId
	 */
	public int getBuyersId() {
		return buyersId;
	}

	/**
	 * @param buyersId the buyersId to set
	 */
	public void setBuyersId(int buyersId) {
		this.buyersId = buyersId;
	}

	/**
	 * @return the buyersName
	 */
	public String getBuyersName() {
		return buyersName;
	}

	/**
	 * @param buyersName the buyersName to set
	 */
	public void setBuyersName(String buyersName) {
		this.buyersName = buyersName;
	}

	/**
	 * @return the sellerId
	 */
	public int getSellerId() {
		return sellerId;
	}

	/**
	 * @param sellerId the sellerId to set
	 */
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * @return the sellerName
	 */
	public String getSellerName() {
		return sellerName;
	}

	/**
	 * @param sellerName the sellerName to set
	 */
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	
	public boolean getBuyersDelete() {
		return buyersDelete;
	}

	public int getPayType() {
		return payType;
	}

	public String getWxPrepayId() {
		return wxPrepayId;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public void setWxPrepayId(String wxPrepayId) {
		this.wxPrepayId = wxPrepayId;
	}

	public void setBuyersDelete(boolean buyersDelete) {
		this.buyersDelete = buyersDelete;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	public String getToProvince() {
		return toProvince;
	}

	public String getToCity() {
		return toCity;
	}

	public String getToArea() {
		return toArea;
	}

	public void setToProvince(String toProvince) {
		this.toProvince = toProvince;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public void setToArea(String toArea) {
		this.toArea = toArea;
	}

	/**
	 * @return the toAddress
	 */
	public String getToAddress() {
		return toAddress;
	}

	/**
	 * @param toAddress the toAddress to set
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	
	/**
	 * @return the toMobile
	 */
	public String getToMobile() {
		return toMobile;
	}

	/**
	 * @param toMobile the toMobile to set
	 */
	public void setToMobile(String toMobile) {
		this.toMobile = toMobile;
	}

	/**
	 * @return the orderStatus
	 */
	public int getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return the deliverStatus
	 */
	public int getDeliverStatus() {
		return deliverStatus;
	}

	/**
	 * @param deliverStatus the deliverStatus to set
	 */
	public void setDeliverStatus(int deliverStatus) {
		this.deliverStatus = deliverStatus;
	}

	/**
	 * @return the payfor
	 */
	public int getPayfor() {
		return payfor;
	}

	/**
	 * @param payfor the payfor to set
	 */
	public void setPayfor(int payfor) {
		this.payfor = payfor;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the cannelTime
	 */
	public Date getCannelTime() {
		return cannelTime;
	}

	/**
	 * @param cannelTime the cannelTime to set
	 */
	public void setCannelTime(Date cannelTime) {
		this.cannelTime = cannelTime;
	}
	
	/**
	 * @return the productId
	 */
	public String[] getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String[] productId) {
		this.productId = productId;
	}

	/**
	 * @return the quantity
	 */
	public String[] getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String[] quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the wxCodeImgUrl
	 */
	public String getWxCodeImgUrl() {
		return wxCodeImgUrl;
	}

	/**
	 * @param wxCodeImgUrl the wxCodeImgUrl to set
	 */
	public void setWxCodeImgUrl(String wxCodeImgUrl) {
		this.wxCodeImgUrl = wxCodeImgUrl;
	}

	/**
	 * @return the wxCodeUpdateTime
	 */
	public Date getWxCodeUpdateTime() {
		return wxCodeUpdateTime;
	}

	/**
	 * @param wxCodeUpdateTime the wxCodeUpdateTime to set
	 */
	public void setWxCodeUpdateTime(Date wxCodeUpdateTime) {
		this.wxCodeUpdateTime = wxCodeUpdateTime;
	}

	@Override
	public String toString() {
		return "ProductOrder [id=" + id + ", channelId=" + channelId
				+ ", orderId=" + orderId + ", outOrderId=" + outOrderId
				+ ", buyersId=" + buyersId + ", buyersName=" + buyersName
				+ ", sellerId=" + sellerId + ", sellerName=" + sellerName
				+ ", buyersDelete=" + buyersDelete + ", productId="
				+ Arrays.toString(productId) + ", productNames=" + productNames
				+ ", quantity=" + Arrays.toString(quantity) + ", amount="
				+ amount + ", price=" + price + ", toProvince=" + toProvince
				+ ", toCity=" + toCity + ", toArea=" + toArea + ", toAddress="
				+ toAddress + ", toMobile=" + toMobile + ", toMail=" + toMail
				+ ", toZipCode=" + toZipCode + ", orderStatus=" + orderStatus
				+ ", deliverStatus=" + deliverStatus + ", payType=" + payType
				+ ", payfor=" + payfor + ", logisticsCompany="
				+ logisticsCompany + ", logisticsNum=" + logisticsNum
				+ ", attr=" + attr + ", createTime=" + createTime
				+ ", cannelTime=" + cannelTime + ", wxCodeImgUrl="
				+ wxCodeImgUrl + ", wxCodeUpdateTime=" + wxCodeUpdateTime
				+ ", wxPrepayId=" + wxPrepayId + "]";
	}
	
}
