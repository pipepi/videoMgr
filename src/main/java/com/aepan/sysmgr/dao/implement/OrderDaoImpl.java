/**
 * 
 */
package com.aepan.sysmgr.dao.implement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com._21cn.framework.util.PageList;
import com._21cn.framework.util.PageTurn;
import com.aepan.sysmgr.dao.OrderDao;
import com.aepan.sysmgr.dao.rowmapper.OrderRowMapper;
import com.aepan.sysmgr.model.ProductOrder;
import com.aepan.sysmgr.util.Constants;

/**
 * 
 * @author rakika
 * 2015年8月1日下午5:52:30
 */
@Repository
public class OrderDaoImpl implements OrderDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	/* 
	 * 获取订单列表
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.OrderDao#getList(java.util.Map, int, int)
	 */
	@Override
	public PageList<ProductOrder> getList(Map<String, Object> params,
			int pageNo, int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select o.id, o.channel_id, o.order_id, o.out_order_id,o.buyers_id, ");
        sb.append(" o.buyers_name,o.buyers_delete, o.seller_id, o.seller_name, o.product_id, o.product_name, o.amount,o.price, ");
        sb.append(" o.to_address, o.to_mobile, o.order_status, o.to_mail, o.to_zip_code, o.back_mobile,o.back_mail,o.back_address, o.attr,o.deliver_status, o.pay_type, o.pay_for, o.to_province,o.to_city,o.to_area,");
        sb.append(" o.logistics_num, o.logistics_company, o.create_time, o.cannel_time ,o.wx_prepay_id, o.wxcode_img_url,o.wxcode_update_time from t_sysmgr_product_order o ");
		
        sb.append(" where o.pay_for = 1 ");
        
        //owner ssss
        if(params.get("userName") != null){
            sb.append(" and ( o.buyers_name = '");
        	sb.append(params.get("userName") + "' or ");
        	sb.append(" o.seller_name = '");
        	sb.append(params.get("userName") + "' ) ");
        }
        
        //params orderId
        if(params.get("orderId") != null){
        	sb.append(" and ( o.order_id = '");
        	sb.append(params.get("orderId") + "' or ");
        	sb.append(" o.channel_id = '");
        	sb.append(params.get("orderId") + "' ) ");
        }
        
        //params buyer
        if(params.get("buyer") != null){
        	sb.append(" and o.buyers_name like '%");
        	sb.append(params.get("buyer"));
        	sb.append("%' ");
        }
        
        //params seller
        if(params.get("seller") != null){
        	sb.append(" and o.seller_name like '%");
        	sb.append(params.get("seller"));
        	sb.append("%' ");
        }
        
        //params type
        if(params.get("stype") != null){
			/*
			 * 0/order_status = 0 
			 * 1/deliver_status = 0 
			 * 2/deliver_status = 1
			 * 3/order_status = 2 
			 * 4/order_status = 3 
			 * 5/deliver_status = 3
			 */
        	        
        	if(params.get("stype").equals("0")){
        		sb.append(" and o.order_status = 0 ");
        	}else if(params.get("stype").equals("1")){
        		sb.append(" and o.deliver_status = 0 ");
        	}else if(params.get("stype").equals("2")){
        		sb.append(" and o.deliver_status = 1 ");
        	}else if(params.get("stype").equals("3")){
        		sb.append(" and o.order_status = 2 ");
        	}else if(params.get("stype").equals("4")){
        		sb.append(" and o.order_status = 3 ");
        	}else if(params.get("stype").equals("5")){
        		sb.append(" and o.deliver_status = 3 ");
        	}
        }
        	
		//params sortName
		if(params.get("sortName") != null){
			sb.append(" order by " + params.get("sortName"));
			sb.append(" " + params.get("sortOrder"));
		}
		List<ProductOrder> list = jdbcTemplate.query(sb.toString(), new OrderRowMapper());
		PageList<ProductOrder> pageList = new PageList<ProductOrder>();
		PageTurn pageTurn = new PageTurn(pageNo, pageSize,
				params.get("iDisplayStart") == null ? 1 :
				Integer.parseInt(params.get("iDisplayStart").toString()));
		for(int i = 0; i < list.size(); i++){
		    pageList.add(list.get(i));	
		}
		pageList.setPageTurn(pageTurn);
		return pageList;
	}

	/* 
	 * 更新订单
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.OrderDao#edit(com._aepan.sysmgr.model.ProductOrder)
	 */
	@Override
	public boolean update(ProductOrder productOrder, int method) {
		if(method == Constants.UPDATE_PRICE){//更新价格
			StringBuffer sb = new StringBuffer();
			sb.append(" update t_sysmgr_product_order set price = ? ");
			sb.append(" where id = ? ");
			return jdbcTemplate.update(sb.toString(), new Object[]{productOrder.getPrice(), 
				productOrder.getId()}) > 0;
		}else if(method == Constants.UPDATE_ORDER_STATUS){//更新订单状态
			StringBuffer sb = new StringBuffer();
			sb.append(" update t_sysmgr_product_order set order_status = ? ");
			sb.append(" where id = ? ");
			return jdbcTemplate.update(sb.toString(), new Object[]{productOrder.getOrderStatus(), 
				productOrder.getId()}) > 0;
		}else if(method == Constants.UPDATE_ORDER_STATUS_AND_OUT_ORDER_ID){
			String sql ="update t_sysmgr_product_order set order_status = ?,out_order_id=? where id = ?  ";
			return jdbcTemplate.update(sql, new Object[]{productOrder.getOrderStatus(),productOrder.getOutOrderId(),
				productOrder.getId()}) > 0;
			
		}else if(method == Constants.UPDATE_GOOD_STATUS){//更新到货状态
			StringBuffer sb = new StringBuffer();
			sb.append(" update t_sysmgr_product_order set deliver_status = ? ");
			sb.append(" where id = ? ");
			return jdbcTemplate.update(sb.toString(), new Object[]{productOrder.getDeliverStatus(), 
				productOrder.getId()}) > 0;
		}else if(method == Constants.UPDATE_ORDER_CANCEL){//取消订单
			String sql=" update t_sysmgr_product_order set order_status = ?, deliver_status = ?, cannel_time = sysdate()  where id = ? ";
			return jdbcTemplate.update(sql, new Object[]{3, 3, productOrder.getId()}) > 0;
		}else if(method==Constants.UPDATE_WXCODE_IMG_URL){
			String sql=" update t_sysmgr_product_order set wx_prepay_id=?, wxcode_img_url = ?,wxcode_update_time =?,order_id=?  where id = ? ";
			
			return jdbcTemplate.update(sql, new Object[]{
					productOrder.getWxPrepayId(),
					productOrder.getWxCodeImgUrl(),
					productOrder.getWxCodeUpdateTime(), 
					productOrder.getOrderId(), 
					productOrder.getId()}) > 0;
		}else if(method == Constants.UPDATE_ORDER_BACK){//更新退换货信息
			//productOrder = productOrder.escapeHtml();
			String sql = "update t_sysmgr_product_order set deliver_status = ?,back_mobile = ?,back_mail=?,back_address=? where id = ? ";
			return jdbcTemplate.update(sql, 
					productOrder.getDeliverStatus(),
					productOrder.getBackMobile(),
					productOrder.getBackMail(),
					productOrder.getBackAddress(),
					productOrder.getId()
					)>0;
		}
		return false;
	}

	/* 
	 * 
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.OrderDao#getById(int)
	 */
	@Override
	public ProductOrder getById(int id) {
		return jdbcTemplate.queryForObject(" select o.id, o.channel_id, o.order_id,o.out_order_id, o.buyers_id, "
				+ " o.buyers_name,o.buyers_delete, o.seller_id, o.seller_name, o.product_id, o.product_name, o.amount,o.price, o.to_province,o.to_city,o.to_area,o.to_address,o.back_mobile,o.back_mail,o.back_address,  o.attr,"
				+ " o.to_mobile, o.to_mail, o.to_zip_code, o.order_status, o.deliver_status, o.pay_type,o.pay_for,o.logistics_num, o.logistics_company,"
				+ " o.create_time, o.cannel_time ,o.wx_prepay_id,o.wxcode_img_url ,o.wxcode_update_time from t_sysmgr_product_order o where o.id = ? ", 
				new Object[]{id}, new OrderRowMapper());
	}
	
	
	
	@Override
	public ProductOrder getByOrderId(String orderId) {
		return jdbcTemplate.queryForObject(" select o.id, o.channel_id, o.order_id, o.out_order_id,o.buyers_id, "
				+ " o.buyers_name,o.buyers_delete, o.seller_id, o.seller_name, o.product_id, o.product_name, o.amount, o.price,o.to_province,o.to_city,o.to_area, o.to_address,o.back_mobile,o.back_mail,o.back_address,  o.attr,"
				+ " o.to_mobile, o.to_mail, o.to_zip_code, o.order_status, o.deliver_status, o.pay_type,o.pay_for, o.logistics_num, o.logistics_company, "
				+ " o.create_time, o.cannel_time,o.wx_prepay_id ,o.wxcode_img_url,o.wxcode_update_time from t_sysmgr_product_order o where o.order_id = ? ", 
				new Object[]{orderId}, new OrderRowMapper());
	}


	/* d
	 * 
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.OrderDao#create(com._aepan.sysmgr.model.ProductOrder)
	 */
	@Override
	public boolean create(ProductOrder productOrder) {
		StringBuffer sb = new StringBuffer("insert into t_sysmgr_product_order(channel_id, ");
		sb.append(" order_id, out_order_id,buyers_id, buyers_name, seller_id, seller_name, ");
		sb.append(" product_id, product_name, quantitys, amount, price, to_province,to_city,to_area,to_address, to_mail, to_zip_code, to_mobile, pay_for, attr,create_time) ");
	    sb.append(" values(?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?) ");
		
	    StringBuffer setProductId = new StringBuffer();
	    String[] params = productOrder.getProductId();
	    for(int i = 0; i < params.length; i++ ){
	    	setProductId.append(params[i]);
	    	if(i + 1 < params.length){
	    		setProductId.append(",");
	    	}
	    }
	    
	    StringBuffer setQuantitys = new StringBuffer();
	    String[] paramsP = productOrder.getQuantity();
	    for(int i = 0; i < paramsP.length; i++ ){
	    	setQuantitys.append(paramsP[i]);
	    	if(i + 1 < paramsP.length){
	    		setQuantitys.append(",");
	    	}
	    }
	    Date now = new Date();
		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			productOrder.getChannelId(),
			productOrder.getOrderId(),
			productOrder.getOutOrderId(),
			productOrder.getBuyersId(),
			productOrder.getBuyersName(),
			productOrder.getSellerId(),
			productOrder.getSellerName(),
			setProductId.toString(),
			productOrder.getProductNames(),
			setQuantitys.toString(),
			productOrder.getAmount(),
			productOrder.getPrice(),
			productOrder.getToProvince(),
			productOrder.getToCity(),
			productOrder.getToArea(),
			productOrder.getToAddress(),
			productOrder.getToMail(),
			productOrder.getToZipCode(),
			productOrder.getToMobile(),
			productOrder.getPayfor(),
			productOrder.getAttr(),
			now
		});
		return ret > 0;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.OrderDao#getByNo(java.lang.String, java.lang.String)
	 */
	@Override
	public ProductOrder getByNo(String channelId, String orderId) {
		return jdbcTemplate.queryForObject(" select o.id, o.channel_id, o.order_id,o.out_order_id, o.buyers_id, "
				+ " o.buyers_name,o.buyers_delete, o.seller_id, o.seller_name, o.product_id, o.product_name, o.amount,o.price, o.to_province,o.to_city,o.to_area,o.to_address, o.back_mobile,o.back_mail,o.back_address,  o.attr,"
				+ " o.to_mobile, o.order_status, o.deliver_status, o.to_mail, o.to_zip_code, o.pay_type, o.pay_for, o.logistics_num, "
				+ " o.logistics_company, o.create_time, o.cannel_time,o.wx_prepay_id,o.wxcode_img_url,o.wxcode_update_time from t_sysmgr_product_order o where o.channel_id = ? and o.order_id = ? ", 
				new Object[]{channelId, orderId}, new OrderRowMapper());
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.OrderDao#getByChannelId(java.lang.String)
	 */
	@Override
	public boolean getByChannelId(String channelId) {
		return jdbcTemplate.query(" select o.id, o.channel_id, o.order_id, o.out_order_id,o.buyers_id, "
				+ " o.buyers_name,o.buyers_delete, o.seller_id, o.seller_name, o.product_id, o.product_name, o.amount,o.price,o.to_province,o.to_city,o.to_area, o.to_address,o.back_mobile,o.back_mail,o.back_address, o.attr,"
				+ " o.to_mobile, o.order_status, o.deliver_status,  o.to_mail, o.to_zip_code, o.pay_for, o.logistics_num, o.logistics_company, "
				+ " o.create_time, o.cannel_time,o.wx_prepay_id,o.wxcode_img_url,o.wxcode_update_time from t_sysmgr_product_order o where o.channel_id = ? ", 
					new Object[] {channelId}, new OrderRowMapper()).size() > 0;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.OrderDao#addLogitics(com._aepan.sysmgr.model.ProductOrder)
	 */
	@Override
	public boolean addLogitics(ProductOrder productOrder) {
		StringBuffer sb = new StringBuffer();
		sb.append(" update t_sysmgr_product_order set logistics_company = ?, logistics_num = ?, deliver_status = ? ");
		sb.append(" where id = ? ");
		return jdbcTemplate.update(sb.toString(), new Object[]{productOrder.getLogisticsCompany(),
			productOrder.getLogisticsNum(), 
			ProductOrder.DELIVER_STATUS_已发货, productOrder.getId()}) > 0;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.OrderDao#getMobileList(java.util.Map, int, int)
	 */
	@Override
	public PageList<ProductOrder> getMobileList(Map<String, Object> params,
			int pageNo, int pageSize) {
		//System.out.println("sortName:" + params.get("sortName"));
		StringBuffer sql = new StringBuffer();
		sql.append(" select o.id, o.channel_id, o.order_id,o.out_order_id, o.buyers_id, ");
        sql.append(" o.buyers_name,o.buyers_delete, o.seller_id, o.seller_name, o.product_id, o.product_name, o.amount,o.price, ");
        sql.append(" o.to_address, o.to_mobile, o.order_status, o.to_mail, o.to_zip_code, o.deliver_status, o.pay_type, o.pay_for, o.to_province,o.to_city,o.to_area,o.back_mobile,o.back_mail,o.back_address, o.attr,");
        sql.append(" o.logistics_num, o.logistics_company, o.create_time, o.cannel_time,o.wx_prepay_id, o.wxcode_img_url,o.wxcode_update_time from t_sysmgr_product_order o where 1=1  ");
        List<Object> args = new ArrayList<Object>();
        condition(sql, params, args);
		if(params.get("sortName") != null){
			String sortName = (String)params.get("sortName");
			String sortOrder = (String)params.get("sortOrder");
			if(sortName.equals("create_time")){
				sql.append(" order by o.create_time ");
				if(sortOrder.equals("desc")){
					sql.append(" desc ");
				}
			}
		}
        
        int startIndex = (pageNo-1)*pageSize;
        sql.append(" limit ?,? ");
		args.add(startIndex);
		args.add(pageSize);
		
		PageList<ProductOrder> list = toPageList(jdbcTemplate.query(sql.toString(), args.toArray(),new OrderRowMapper()));
		list = list==null?new PageList<ProductOrder>():list;
		list.setPageTurn(new PageTurn(pageNo, pageSize,pageCount(params)));
		return list;
	}
	public int pageCount(Map<String, Object> params){
		StringBuffer sql = new StringBuffer("select count(0) from t_sysmgr_product_order o  where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		condition(sql, params, args);
		int count = (Integer)jdbcTemplate.queryForObject(sql.toString(),args.toArray(),Integer.class);
		return count;
	}
	private PageList<ProductOrder> toPageList(List<ProductOrder> list){
		if(list!=null&&!list.isEmpty()){
			PageList<ProductOrder> rs = new PageList<ProductOrder>();
			for (ProductOrder t : list) {
				t.escapeHtml();
				rs.add(t);
			}
			return rs;
		}else{
			return null;
		}
	}
	private void condition(StringBuffer sql,Map<String, Object> params,List<Object> args){
        params.put("payFor", 2);//只查询购买套餐生成的订单

		if(params.get("payFor")!=null){
			sql.append(" and o.pay_for = ? ");
			args.add(params.get("payFor"));
		}
		if(params.get("toMobile") != null){
			sql.append(" and o.to_mobile = ? ");
			args.add(params.get("toMobile"));
		}
		if(params.get("orderId") != null){
			sql.append(" and ( o.order_id = ?  or o.channel_id = ? )");
			args.add(params.get("orderId"));
			args.add(params.get("orderId"));
		}
		if(params.get("buyer") != null){
			sql.append(" and o.buyers_name like concat('%',?,'%') ");
			args.add(params.get("buyer"));
		}
		if(params.get("buyerId") != null){
			sql.append(" and o.buyers_id =? ");
			args.add(params.get("buyerId"));
		}
		if(params.get("sellerId")!=null){
			sql.append(" and o.seller_id = ? ");
			args.add(params.get("sellerId"));
		}
		if(params.get("seller") != null){
			sql.append(" and o.seller_name like concat('%',?,'%') ");
			args.add(params.get("seller"));
		}
		if(params.get("stype") != null){
			String t = (String)params.get("stype");
			int orderStatus = t.equals("2")?1:0;
			sql.append(" and o.order_status = ? ");
			args.add(orderStatus);
		}
		if(params.get("buyersDelete")!=null){
			sql.append(" and o.buyers_delete = ? ");
			args.add(params.get("buyersDelete"));
		}	
	}
	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.OrderDao#isMobile(java.lang.String)
	 */
	@Override
	public boolean isMobile(String mobile) {
		int ret = jdbcTemplate.query(" select o.id, o.channel_id, o.order_id, o.out_order_id,o.buyers_id, "
				+ " o.buyers_name,o.buyers_delete, o.seller_id, o.seller_name, o.product_id, o.product_name, o.amount,o.price,o.to_province,o.to_city,o.to_area, o.to_address, "
				+ " o.to_mobile, o.order_status, o.deliver_status,  o.to_mail, o.to_zip_code, o.pay_type, o.back_mobile,o.back_mail,o.back_address, o.attr,o.pay_for, o.logistics_num, o.logistics_company, "
				+ " o.create_time, o.cannel_time,o.wx_prepay_id ,o.wxcode_img_url,o.wxcode_update_time from t_sysmgr_product_order o where o.to_mobile = ? ", 
					new Object[] {mobile}, new OrderRowMapper()).size();
		return ret > 0;
	}
	/**
	 * 买家逻辑删除
	 * @param ids
	 */
	@Override
	public void multiBuyersDelete(String ids){
		String sql = "update t_sysmgr_product_order set buyers_delete = 1 where id in ("+ids+")";
		jdbcTemplate.update(sql);
	}
	
	
}
