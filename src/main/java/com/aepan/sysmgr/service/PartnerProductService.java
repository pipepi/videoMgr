/**
 * 
 */
package com.aepan.sysmgr.service;

import java.util.List;

import com.aepan.sysmgr.model.PartnerProduct;

/**
 * 第三方 数据库产品表相关操作
 * @author rakika
 * 2015年9月14日上午12:14:37
 */
public interface PartnerProductService {

	//根据shopId获取第三方后台产品清单
	public List<PartnerProduct> getPartnerProduct(String shopId);
	
	//更新产品表和SKU
	public boolean update(List<PartnerProduct> listHaimllProduct);
}
