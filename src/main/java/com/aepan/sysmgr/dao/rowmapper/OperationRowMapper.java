/**
 * 
 */
package com.aepan.sysmgr.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aepan.sysmgr.model.Operation;

/**
 * @author rakika
 * 2015年7月11日上午9:24:29
 */
public class OperationRowMapper implements RowMapper<Operation>{

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public Operation mapRow(ResultSet rs, int index) throws SQLException {
		Operation operation = new Operation();
		operation.setId(rs.getInt("id"));
		operation.setShortcut(rs.getInt("shortcut"));
		operation.setName(rs.getString("name"));
		operation.setCreateTime(rs.getTimestamp("create_time"));
		return operation;
	}


}