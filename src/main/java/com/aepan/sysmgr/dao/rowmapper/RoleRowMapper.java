/**
 * 
 */
package com.aepan.sysmgr.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aepan.sysmgr.model.Role;
/**
 * 角色rowmapper
 * @author rakika
 * 2015年6月21日下午10:31:57
 */
public class RoleRowMapper implements RowMapper<Role>{

	/* 
	 * Role实现类
	 * (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public Role mapRow(ResultSet rs, int index) throws SQLException {
		Role role = new Role();
		role.setId(rs.getInt("id"));
		role.setName(rs.getString("name"));
		role.setType(rs.getInt("type"));
		role.setCreateTime(rs.getTimestamp("create_time"));
		return role;
	}

	
}
