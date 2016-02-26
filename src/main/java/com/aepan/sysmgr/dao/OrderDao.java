/**
 * 
 */
package com.aepan.sysmgr.dao;

import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.ProductOrder;

/**
 * 订单
 * @author rakika
 * 2015年8月1日下午5:51:18
 */
public interface OrderDao {

	public PageList<ProductOrder> getList(Map<String, Object> params, int pageNo, int pageSize);
	
	public PageList<ProductOrder> getMobileList(Map<String, Object> params, int pageNo, int pageSize);
	
	//修改 订单，method = 1、修改订单金额 ；2、修改订单状态；3、修改送货状态；4、取消订单；
	public boolean update(ProductOrder productOrder, int method);
	
	public ProductOrder getById(int id);
	
	public boolean create(ProductOrder productOrder);
	
	//根据channelID和orderID查询订单号
	public ProductOrder getByNo(String channelId, String orderId);
	
	public boolean getByChannelId(String channelId);
	
	public ProductOrder getByOrderId(String orderId);
	
	//更新物流单
	public boolean addLogitics(ProductOrder productOrder);
	
	//判断是否存在该手机号
	public boolean isMobile(String mobile);

	/**
	 * 买家逻辑删除
	 */
	void multiBuyersDelete(String ids);

}
