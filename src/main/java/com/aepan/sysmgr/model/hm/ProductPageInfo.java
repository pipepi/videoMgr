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
 * @author lanker
 * 2015年9月28日下午4:27:19
 */
public class ProductPageInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Product> products;
	private int count;
	public static void main(String[] args) {
		ProductPageInfo ppi=new ProductPageInfo();
		ppi.products = new ArrayList<Product>();
		ppi.products.add(new Product(123,210,"商品发布了","/Storage/Shop/210/Products/123",100000.00f,10,new Date().getTime()+""));
		ppi.products.add(new Product(124,210,"限时购的商品","/Storage/Shop/210/Products/124",12000.00f,10,new Date().getTime()+""));
		ppi.products.add(new Product(125,210,"限时购的商品","/Storage/Shop/210/Products/124",12000.00f,10,new Date().getTime()+""));
		ppi.products.add(new Product(126,210,"限时购的商品","/Storage/Shop/210/Products/124",12000.00f,10,new Date().getTime()+""));
		ppi.products.add(new Product(127,210,"限时购的商品","/Storage/Shop/210/Products/124",12000.00f,10,new Date().getTime()+""));
		ppi.products.add(new Product(128,210,"限时购的商品","/Storage/Shop/210/Products/124",12000.00f,10,new Date().getTime()+""));
		ppi.products.add(new Product(129,210,"限时购的商品","/Storage/Shop/210/Products/124",12000.00f,10,new Date().getTime()+""));
		ppi.count = 7;
		String json = JSONUtil.toJson(ppi);
		System.out.println(json);
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
