/**
 * 
 */
package com.aepan.sysmgr.model.lucene;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.aepan.sysmgr.util.JSONUtil;

/**
 * 商品属性 用于搜索过滤
 * @author lanker
 * 2015年11月17日上午10:34:13
 */
public class ProductAttribute implements Serializable {

	private static final long serialVersionUID = 1L;
	public Integer id;
	public List<Integer> values;
	public ProductAttribute() {
	}
	public ProductAttribute(Integer id,Integer... values) {
		this.id = id;
		if(values!=null&&values.length>0){
			this.values = new ArrayList<Integer>();
			for (Integer i : values) {
				this.values.add(i);
			}
		}
	}
	public static List<ProductAttribute> init(){
		List<ProductAttribute> pattrs = new ArrayList<ProductAttribute>();
		ProductAttribute pa1 = new ProductAttribute(1, 1,2);
		ProductAttribute pa2 = new ProductAttribute(2, 1);
		ProductAttribute pa3 = new ProductAttribute(3, 1,2,3);
		pattrs.add(pa1);
		pattrs.add(pa2);
		pattrs.add(pa3);
		return pattrs;
	}
	public static void main(String[] args) {
		
		System.out.println(JSONUtil.toJson(init()));
	}
}
