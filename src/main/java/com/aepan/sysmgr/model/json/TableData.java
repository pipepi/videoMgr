/**
 * 
 */
package com.aepan.sysmgr.model.json;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * 2015年9月28日上午11:45:41
 */
public class TableData implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<TableDataItem> cost;
	private List<TableDataItem> mallPrice;
	private int productId;
	private List<TableDataItem> sku;
	private List<TableDataItem> stock;
	public List<TableDataItem> getCost() {
		return cost;
	}
	public void setCost(List<TableDataItem> cost) {
		this.cost = cost;
	}
	public List<TableDataItem> getMallPrice() {
		return mallPrice;
	}
	public void setMallPrice(List<TableDataItem> mallPrice) {
		this.mallPrice = mallPrice;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public List<TableDataItem> getSku() {
		return sku;
	}
	public void setSku(List<TableDataItem> sku) {
		this.sku = sku;
	}
	public List<TableDataItem> getStock() {
		return stock;
	}
	public void setStock(List<TableDataItem> stock) {
		this.stock = stock;
	}
	
}
