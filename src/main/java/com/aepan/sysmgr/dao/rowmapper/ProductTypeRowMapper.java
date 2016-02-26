/**
 * 
 */
package com.aepan.sysmgr.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aepan.sysmgr.model.ProductType;


/**
 * @author rakika
 * 2015年7月31日上午10:42:07
 */
public class ProductTypeRowMapper implements RowMapper<ProductType>{

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public ProductType mapRow(ResultSet rs, int index) throws SQLException {
		ProductType productType = new ProductType();
		productType.setId(rs.getInt("id"));
		productType.setName(rs.getString("name"));
		productType.setCreateTime(rs.getTimestamp("create_time"));
		return productType;
	}

}
