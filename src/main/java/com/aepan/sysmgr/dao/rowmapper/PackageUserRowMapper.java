/**
 * 
 */
package com.aepan.sysmgr.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aepan.sysmgr.model.PackageUser;

/**
 * 套餐规则rowmapper
 * @author rakika
 * 2015年6月21日下午10:31:57
 */
public class PackageUserRowMapper implements RowMapper<PackageUser>{

	/* 
	 * Role实现类
	 * (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public PackageUser mapRow(ResultSet rs, int index) throws SQLException {
		PackageUser packageUser = new PackageUser();
		packageUser.setId(rs.getInt("id"));
		packageUser.setName(rs.getString("name"));
		packageUser.setUserId(rs.getInt("user_id"));
		packageUser.setPackageId(rs.getInt("package_id"));
		packageUser.setPackageRuleId(rs.getInt("package_rule_id"));
		packageUser.setType(rs.getInt("type"));
		packageUser.setProductType(rs.getInt("product_type"));
		packageUser.setTime(rs.getInt("times"));
		packageUser.setCreateTime(rs.getTimestamp("create_time"));
		return packageUser;
	}

}
