/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.aepan.sysmgr.model.hm.PartnerProduct;
import com.aepan.sysmgr.model.lucene.ProductAttribute;

/**
 * 店铺产品关系
 * @author rakika
 * 2015年8月14日上午1:06:22
 */
public class StoreProduct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7610653488618260030L;
	public static final int STATUS_违规下架 = 4;
	public static final int STATUS_销售中 = 2;
	public int id;
	
	public int userId;
	
	public int storeId;
	
	public int productId;
	
	public String productName;
	
	public String productDesc;
	
	public String productType;
	
	//商品属性
	public List<ProductAttribute> productAttrs; 
	//商品价格
	public float productPrice;
	
	public Date createTime;
	
	/**
	 * 1:待审核   
	 * 2:销售中
	 * 3:未通过
	 * 4:违规下架
	 * 5:未审核
	 */
	public int auditState;
	public static boolean hasProductOffline(List<StoreProduct> list){
		if(list!=null&&!list.isEmpty()){
			for (StoreProduct partnerProduct : list) {
				if(partnerProduct.getAuditState()==STATUS_违规下架){
					return true;
				}
			}
		}
		return false;
	}
	
	public StoreProduct() {
	}
	public StoreProduct(int userId,int storeId,int productId) {
		this.userId = userId;
		this.storeId = storeId;
		this.productId = productId;
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
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the storeId
	 */
	public int getStoreId() {
		return storeId;
	}

	/**
	 * @param storeId the storeId to set
	 */
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
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
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the productDesc
	 */
	public String getProductDesc() {
		return productDesc;
	}
	/**
	 * @param productDesc the productDesc to set
	 */
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}
	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}
	/**
	 * @return the auditState
	 */
	public int getAuditState() {
		return auditState;
	}
	/**
	 * @param auditState the auditState to set
	 */
	public void setAuditState(int auditState) {
		this.auditState = auditState;
	}
	
	
}
