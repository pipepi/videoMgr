/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.dao.OrderDao;
import com.aepan.sysmgr.model.ProductOrder;
import com.aepan.sysmgr.service.OrderService;
import com.aepan.sysmgr.util.Constants;

/**
 * 订购service
 * @author rakika
 * 2015年8月1日下午5:50:27
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDao orderDao;
	
	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.OrderService#getList(java.util.Map, int, int)
	 */
	@Override
	public PageList<ProductOrder> getList(Map<String, Object> params,
			int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return orderDao.getList(params, pageNo, pageSize);
	}

	/* 
	 * 更新订单状态，修改 订单，method = 1、修改订单金额 ；2、修改订单状态；3、修改送货状态；4、取消订单；
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.service.OrderService#edit(com._aepan.sysmgr.model.ProductOrder)
	 */
	@Override
	public boolean update(ProductOrder productOrder, int method) {
		// TODO Auto-generated method stub
		return orderDao.update(productOrder, method);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.OrderService#getById(int)
	 */
	@Override
	public ProductOrder getById(int id) {
		// TODO Auto-generated method stub
		return orderDao.getById(id);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.OrderService#create(com._aepan.sysmgr.model.ProductOrder)
	 */
	@Override
	public boolean create(ProductOrder productOrder) {
		// TODO Auto-generated method stub
		return orderDao.create(productOrder);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.OrderService#getByNo(java.lang.String, java.lang.String)
	 */
	@Override
	public ProductOrder getByNo(String channelId, String orderId) {
		// TODO Auto-generated method stub
		return orderDao.getByNo(channelId, orderId);
	}
	
	@Override
	public boolean getByChannelId(String channelId){
		return orderDao.getByChannelId(channelId);
	}
	
	@Override
	public ProductOrder getByOrderId(String orderId){
		return orderDao.getByOrderId(orderId);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.OrderService#addLogistics(com._aepan.sysmgr.model.ProductOrder)
	 */
	@Override
	public boolean addLogistics(ProductOrder productOrder) {
		return orderDao.addLogitics(productOrder);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.OrderService#getMobileList(java.util.Map, int, int)
	 */
	@Override
	public PageList<ProductOrder> getMobileList(Map<String, Object> params,
			int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return orderDao.getMobileList(params, pageNo, pageSize);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.OrderService#isMobile(java.lang.String)
	 */
	@Override
	public boolean isMobile(String mobile) {
		// TODO Auto-generated method stub
		return orderDao.isMobile(mobile);
	}
	@Override
	public void multiBuyersDelete(String ids){
		orderDao.multiBuyersDelete(ids);
	}

	/**更新退换货信息*/
	@Override
	public void updateOrderBackInfo(ProductOrder o){
		orderDao.update(o, Constants.UPDATE_ORDER_BACK);
	}
}
