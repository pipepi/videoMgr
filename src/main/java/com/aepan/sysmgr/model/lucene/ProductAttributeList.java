/**
 * 
 */
package com.aepan.sysmgr.model.lucene;

import java.io.Serializable;
import java.util.List;

/**
 * @author lanker
 * 2015年11月19日上午10:07:34
 */
public class ProductAttributeList implements Serializable {

	private static final long serialVersionUID = 1L;
	private int ProductId;
	private int AttributeId;
	private List<Integer> AttributeValueList;
	public int getProductId() {
		return ProductId;
	}
	public void setProductId(int productId) {
		ProductId = productId;
	}
	public int getAttributeId() {
		return AttributeId;
	}
	public void setAttributeId(int attributeId) {
		AttributeId = attributeId;
	}
	public List<Integer> getAttributeValueList() {
		return AttributeValueList;
	}
	public void setAttributeValueList(List<Integer> attributeValueList) {
		AttributeValueList = attributeValueList;
	}
	
}
