/**
 * 
 */
package com.aepan.sysmgr.model.hm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aepan.sysmgr.util.JSONUtil;

/**
 * 第三方系统产品信息
 * @author lanker
 * 2015年9月28日下午3:21:31
 */
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;//商品ID
	private int shopId;//店铺ID
	private String productName;//商品名称
	private String productDesc;//产品描述
	private String imagePath;//存放图片的目录
	private float marketPrice;//市场价
	private int quantity;//数量
	private String addedDate;//添加日期
	/**
	 * 1:待审核   
	 * 2:销售中
	 * 3:未通过
	 * 4:违规下架
	 * 5:未审核
	 */
	private int auditState;
	public Product() {
	}
	public Product(int id,int shopId,String productName,String imagePath,float marketPrice,int quantity,String addedDate) {
		this.id = id;
		this.shopId = shopId;
		this.productName = productName;
		this.imagePath = imagePath;
		this.marketPrice = marketPrice;
		this.quantity = quantity;
		this.addedDate = addedDate;
	}
	public static void main(String[] args) {
		List<Product> ps = new ArrayList<Product>();
		ps.add(new Product(123,210,"商品发布了","/Storage/Shop/210/Products/123",100000.0f,10,new Date().getTime()+""));
		ps.add(new Product(124,210,"限时购的商品","/Storage/Shop/210/Products/124",12000.0f,10,new Date().getTime()+""));
		String json = JSONUtil.toJson(ps);
		System.out.println(json);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public float getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(float marketPrice) {
		this.marketPrice = marketPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}
	public int getAuditState() {
		return auditState;
	}
	public void setAuditState(int auditState) {
		this.auditState = auditState;
	}
	

}
