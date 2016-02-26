/**
 * 
 */
package com.aepan.sysmgr.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aepan.sysmgr.model.ProductInfo;

/**
 * @author rakika
 * 2015年7月18日上午10:26:02
 */
public class ProductRowMapper implements RowMapper<ProductInfo>{

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public ProductInfo mapRow(ResultSet rs, int index) throws SQLException {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setId(rs.getInt("id"));
		productInfo.setName(rs.getString("name"));
		productInfo.setUserId(rs.getInt("user_id"));
		productInfo.setPhotoUrl1(rs.getString("photo_url1"));
		productInfo.setPhotoUrl2(rs.getString("photo_url2"));
		productInfo.setPhotoUrl3(rs.getString("photo_url3"));
		productInfo.setPhotoUrl3(rs.getString("photo_url4"));
		productInfo.setPhotoUrl3(rs.getString("photo_url5"));
		productInfo.setPrice(rs.getInt("price"));
		productInfo.setReserve(rs.getInt("reserve"));
		productInfo.setStatus(rs.getInt("status"));
		productInfo.setType(rs.getInt("type"));
		productInfo.setExt(rs.getString("ext"));
		productInfo.setWeight(rs.getInt("weight"));
		productInfo.setCreateTime(rs.getTimestamp("create_time"));
		return productInfo;
	}

}
