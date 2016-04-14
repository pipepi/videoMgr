/**
 * 
 */
package com.aepan.sysmgr.model.hm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.aepan.sysmgr.model.lucene.ProductAttribute;
import com.aepan.sysmgr.model.lucene.ProductAttributeList;

/**
 * @author Doris.zhou
 * 2015年9月28日下午6:48:53
 */
public class PartnerProduct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * 产品id
	 */
	private int Id;
	
	/**
	 * 目录id
	 */
	private int CategoryId;
	/**
	 * 产品名
	 */
	private String Name;
	private String ShotDescription;
	/**
	 * 图片地址
	 */
	private String Image;
	//产品属性  ProductAttrs:[{1,[1,2]},{2,[1]},{3,[1,2,3]}]
	private List<ProductAttributeList> ProductAttributeList;
	/**
	 * 价格
	 */
	private float Price;
	/**
	 * 库存，目前数据不对
	 */
	private int Stock;
	
	/**
	 * 1:待审核   
	 * 2:销售中
	 * 3:未通过
	 * 4:违规下架
	 * 5:未审核
	 */
	private int AuditState;
	
	/**
	 * 发布时间
	 */
	private String PublishTime;
	/**
	 * 计量单位
	 */
	private String Unit;
	
	
	public List<ProductAttribute> toProductAttr(){
		if(this.ProductAttributeList==null||this.ProductAttributeList.size()==0){
			return null;
		}else{
			List<ProductAttribute> pas = new ArrayList<ProductAttribute>();
			for (ProductAttributeList pal : this.ProductAttributeList) {
				ProductAttribute pa = new ProductAttribute();
				pa.id = pal.getAttributeId();
				pa.values = pal.getAttributeValueList();
				pas.add(pa);
			}
			return pas;
		}
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return CategoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		CategoryId = categoryId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	public String getShotDescription() {
		return ShotDescription;
	}
	public void setShotDescription(String shotDescription) {
		ShotDescription = shotDescription;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	
	public List<ProductAttributeList> getProductAttributeList() {
		return ProductAttributeList;
	}
	public void setProductAttributeList(
			List<ProductAttributeList> productAttributeList) {
		ProductAttributeList = productAttributeList;
	}
	public float getPrice() {
		return Price;
	}
	public void setPrice(float price) {
		Price = price;
	}
	public int getStock() {
		return Stock;
	}
	public void setStock(int stock) {
		Stock = stock;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return Unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		Unit = unit;
	}
	
	/**
	 * @return the auditState
	 */
	public int getAuditState() {
		return AuditState;
	}
	/**
	 * @param auditState the auditState to set
	 */
	public void setAuditState(int auditState) {
		AuditState = auditState;
	}
	/**
	 * @return the publishTime
	 */
	public String getPublishTime() {
		return PublishTime;
	}
	/**
	 * @param publishTime the publishTime to set
	 */
	public void setPublishTime(String publishTime) {
		PublishTime = publishTime;
	}
	@Override
	public String toString() {
		return "PartnerProduct [Id=" + Id + ", Name=" + Name + ", Image="
				+ Image + ", Price=" + Price + ", Stock=" + Stock
				+ ", PublishTime=" + PublishTime + ", Unit=" + Unit + "]";
	}
	
	
}
