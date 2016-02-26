/**
 * 
 */
package com.aepan.sysmgr.service;

import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.ProductOrder;

/**
 * @author rakika
 * 2015年8月1日下午5:43:23
 */
public interface OrderService {

	public PageList<ProductOrder> getList(Map<String, Object> params, int pageNo, int pageSize);
	
	public PageList<ProductOrder> getMobileList(Map<String, Object> params, int pageNo, int pageSize);
	
	public boolean update(ProductOrder productOrder, int method);
	
	public ProductOrder getById(int id);
	
	public boolean create(ProductOrder productOrder);
	
	public ProductOrder getByNo(String channelId, String orderId);
	
	public boolean getByChannelId(String channelId);

	ProductOrder getByOrderId(String orderId);
	
	public boolean addLogistics(ProductOrder productOrder);
	
	public boolean isMobile(String mobile);

	/**
	 * 买家逻辑删除
	 */
	void multiBuyersDelete(String ids);

	/**
	 * 更新退换货信息
	 */
	void updateOrderBackInfo(ProductOrder o);
	
}
