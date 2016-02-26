/**
 * 
 */
package com.aepan.sysmgr.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aepan.sysmgr.model.MgrPrivilege;

/**
 * @author rakika
 * 2015年6月20日下午4:16:05
 */
public class PrivilegeRowMapper implements RowMapper<MgrPrivilege>{

	/* 
	 * 
	 * (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public MgrPrivilege mapRow(ResultSet rs, int index) throws SQLException {
		MgrPrivilege mgrPrivilege = new MgrPrivilege();
		mgrPrivilege.setId(rs.getInt("id"));
		mgrPrivilege.setResourceId(rs.getInt("resource_id"));
		mgrPrivilege.setResourceName(rs.getString("sname"));
		mgrPrivilege.setOperationId(rs.getInt("operation_id"));
		mgrPrivilege.setOperationName(rs.getString("oname"));
		mgrPrivilege.setRoleId(rs.getInt("role_id"));
		mgrPrivilege.setRoleName(rs.getString("rname"));
		mgrPrivilege.setCreateTime(rs.getTimestamp("create_time"));
		mgrPrivilege.setUrl(rs.getString("url"));
		mgrPrivilege.setShortcut(rs.getString("shortcut"));
		return mgrPrivilege;
	}

}
