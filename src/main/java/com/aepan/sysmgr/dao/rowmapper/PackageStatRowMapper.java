/**
 * 
 */
package com.aepan.sysmgr.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aepan.sysmgr.model.packageinfo.PackageStat;

/**
 * @author AZT
 * 2015年9月8日下午3:56:14
 */
public class PackageStatRowMapper implements RowMapper<PackageStat> {

	@Override
	public PackageStat mapRow(ResultSet rs, int index) throws SQLException {
		PackageStat rpackage = new PackageStat();
		rpackage.setId(rs.getInt("id"));
		rpackage.setUserId(rs.getInt("user_id"));
		rpackage.setPackageId(rs.getInt("package_id"));
		rpackage.setPlayerNum(rs.getInt("player_num"));
		rpackage.setVideoNum(rs.getInt("video_num"));
		rpackage.setProductNum(rs.getInt("product_num"));
		rpackage.setFlowNum(rs.getFloat("flow_num"));
		rpackage.setUsedFlowNum(rs.getFloat("used_flow_num"));
		rpackage.setDuration(rs.getInt("duration"));
		rpackage.setStartime(rs.getTimestamp("start_time"));
		rpackage.setEndTime(rs.getTimestamp("end_time"));
		rpackage.setCreateTime(rs.getTimestamp("create_time"));
		rpackage.setCreateTime(rs.getTimestamp("update_time"));
		return rpackage;
	}


}
