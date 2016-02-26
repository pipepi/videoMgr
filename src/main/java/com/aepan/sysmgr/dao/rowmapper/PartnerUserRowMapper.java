/**
 * 
 */
package com.aepan.sysmgr.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aepan.sysmgr.model.PartnerUser;

/**
 *  user
 * @author rakika
 * 2015年8月1日下午5:54:40
 */
public class PartnerUserRowMapper implements RowMapper<PartnerUser>{

	@Override
	public PartnerUser mapRow(ResultSet rs, int index) throws SQLException {
		PartnerUser partnerUser = new PartnerUser();
		partnerUser.setId(rs.getInt("id"));
		partnerUser.setSellerName(rs.getString("seller_name"));
		partnerUser.setPartnerName(rs.getString("partner_account_name"));
		partnerUser.setEmail(rs.getString("email"));
		partnerUser.setPackageId(rs.getInt("package_id"));
		partnerUser.setPackageName(rs.getString("package_name"));
		partnerUser.setPlayerNum(rs.getInt("player_num"));
		partnerUser.setVideoNum(rs.getInt("video_num"));
		partnerUser.setProductNum(rs.getInt("product_num"));
		partnerUser.setTotalPlayerNum(rs.getInt("total_player_num"));
		partnerUser.setTotalVideoNum(rs.getInt("total_video_num"));
		partnerUser.setTotalProductNum(rs.getInt("total_product_num"));
		partnerUser.setPrice(rs.getFloat("price"));
		partnerUser.setDuration(rs.getInt("duration"));
		partnerUser.setEndTime(rs.getDate("end_time"));
		partnerUser.setCreateTime(rs.getTimestamp("create_time"));
		return partnerUser;
	}

}
