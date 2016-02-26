/**
 * 
 */
package com.aepan.sysmgr.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aepan.sysmgr.model.ProductOrder;

/**
 * @author rakika
 * 2015年8月1日下午5:54:40
 */
public class OrderRowMapper implements RowMapper<ProductOrder>{

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public ProductOrder mapRow(ResultSet rs, int index) throws SQLException {
		ProductOrder productOrder = new ProductOrder();
		productOrder.setId(rs.getInt("id"));
		productOrder.setChannelId(rs.getString("channel_id"));
		productOrder.setOrderId(rs.getString("order_id"));
		productOrder.setOutOrderId(rs.getString("out_order_id"));
		productOrder.setBuyersId(rs.getInt("buyers_id"));
		productOrder.setBuyersName(rs.getString("buyers_name"));
		productOrder.setBuyersDelete(rs.getBoolean("buyers_delete"));
		productOrder.setSellerId(rs.getInt("seller_id"));
		productOrder.setSellerName(rs.getString("seller_name"));
		productOrder.setProductId(rs.getString("product_id").split(","));
		productOrder.setProductNames(rs.getString("product_name"));
		productOrder.setPrice(rs.getFloat("price"));
		productOrder.setToProvince(rs.getString("to_province"));
		productOrder.setToCity(rs.getString("to_city"));
		productOrder.setToArea(rs.getString("to_area"));
		productOrder.setToAddress(rs.getString("to_address"));
		productOrder.setToMobile(rs.getString("to_mobile"));
		productOrder.setToMail(rs.getString("to_mail"));
		productOrder.setToZipCode(rs.getString("to_zip_code"));
		productOrder.setBackMobile(rs.getString("back_mobile"));
		productOrder.setBackMail(rs.getString("back_mail"));
		productOrder.setBackAddress(rs.getString("back_address"));
		productOrder.setOrderStatus(rs.getInt("order_status"));
		productOrder.setDeliverStatus(rs.getInt("deliver_status"));
		productOrder.setPayfor(rs.getInt("pay_for"));
		productOrder.setAmount(rs.getInt("amount"));
		productOrder.setLogisticsNum(rs.getString("logistics_num"));
		productOrder.setLogisticsCompany(rs.getString("logistics_company"));
		productOrder.setCreateTime(rs.getTimestamp("create_time"));
		productOrder.setCannelTime(rs.getTimestamp("cannel_time"));
		productOrder.setWxCodeImgUrl(rs.getString("wxcode_img_url"));
		productOrder.setWxCodeUpdateTime(rs.getTimestamp("wxcode_update_time"));
		productOrder.setWxPrepayId(rs.getString("wx_prepay_id"));
		productOrder.setPayType(rs.getInt("pay_type"));
		productOrder.setAttr(rs.getString("attr"));
		return productOrder;
	}

}
