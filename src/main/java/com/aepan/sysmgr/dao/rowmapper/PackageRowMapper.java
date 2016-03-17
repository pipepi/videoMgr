/**
 * 
 */
package com.aepan.sysmgr.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aepan.sysmgr.model.packageinfo.PackageInfo;

/**
 * 套餐rowmapper
 * @author rakika
 * 2015年6月21日下午10:31:57
 */
public class PackageRowMapper implements RowMapper<PackageInfo>{

	@Override
	public PackageInfo mapRow(ResultSet rs, int index) throws SQLException {
		PackageInfo rpackage = new PackageInfo();
		rpackage.setId(rs.getInt("id"));
		rpackage.setName(rs.getString("name"));
		rpackage.setPackageType(rs.getInt("package_type"));
		rpackage.setPlayerNum(rs.getInt("player_num"));
		rpackage.setVideoNum(rs.getInt("video_num"));
		rpackage.setProductNum(rs.getInt("product_num"));
		rpackage.setTotalPrice(rs.getFloat("price"));
		rpackage.setDuration(rs.getInt("duration"));
		rpackage.setFlowNum(rs.getFloat("flow_num"));
		//rpackage.setMonthFlowPrice(rs.getFloat("month_flow_price"));
		/*float price = rpackage.getPrice();
		float monthFlowPrice = rpackage.getMonthFlowPrice();
		int duration = rpackage.getDuration();
		float totalPrice = (price+monthFlowPrice)*duration;
		totalPrice = (float)Math.round(totalPrice*100)/100;//保留两位小数点
		rpackage.setTotalPrice(totalPrice);*/	
		rpackage.setCreateTime(rs.getTimestamp("create_time"));
		rpackage.setCreateTime(rs.getTimestamp("update_time"));
		return rpackage;
	}

}
