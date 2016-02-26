/**
 * 
 */
package com.aepan.sysmgr.service;

import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.ProductType;

/**
 * 产品类型
 * @author rakika
 * 2015年7月31日上午10:49:24
 */
public interface ProductTypeService {

	public PageList<ProductType> getTypeList(Map<String, Object> params, int pageNo, int pageSize);
	
	public ProductType getById(int id);
	
	public boolean save(ProductType productType);
	
	public boolean update(ProductType productType);

	/**
	 * 同步类目
	 * @param url 接口地址
	 */
	void synchronous(String url);
}
