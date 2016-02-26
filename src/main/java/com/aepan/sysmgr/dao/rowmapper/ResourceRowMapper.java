/**
 * 
 */
package com.aepan.sysmgr.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aepan.sysmgr.model.Resource;

/**
 * ResourceMapper
 * @author rakika
 * 2015年6月20日下午3:57:09
 */
public class ResourceRowMapper implements RowMapper<Resource>{

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public Resource mapRow(ResultSet rs, int index) throws SQLException {
		Resource resource = new Resource();
		resource.setId(rs.getInt("id"));
		resource.setPid(rs.getInt("p_id"));
		resource.setUrl(rs.getString("url"));
		resource.setName(rs.getString("name"));
		resource.setStatus(rs.getInt("status"));
		resource.setCreateTime(rs.getTimestamp("create_time"));
		return resource;
	}

}
