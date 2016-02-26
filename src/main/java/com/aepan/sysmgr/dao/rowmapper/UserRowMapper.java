/**
 * 
 */
package com.aepan.sysmgr.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import com.aepan.sysmgr.model.User;

/**
 * UserMapper
 * @author rakika
 * 2015年6月9日下午6:32:07
 * 
 */
public class UserRowMapper implements RowMapper<User>{
	
	public User mapRow(ResultSet rs, int index) throws SQLException { 
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUserName(rs.getString("username"));
        user.setPartnerAccountName(rs.getString("partner_account_name"));
        user.setPartnerId(rs.getInt("partner_id"));
        user.setPartnerAccountId(rs.getInt("partner_account_id"));
        user.setPassWord(rs.getString("password"));
        user.setPartnerMobile(rs.getString("partner_mobile"));
        user.setEmail(rs.getString("email"));
        user.setRoleId(rs.getInt("role_id"));
        user.setQqidKey(rs.getString("qqid_key"));
        try {
        	 if(!StringUtils.isBlank(rs.getString("rolename"))){
             	user.setRoleName(rs.getString("rolename"));
             }
             if(!StringUtils.isBlank(rs.getString("packagename"))){
             	user.setPackageName(rs.getString("packagename"));
             }
		} catch (Exception e) {
			//ignore
		}
       
        user.setPackageId(rs.getInt("package_id"));
        user.setStatus(rs.getInt("status"));
        user.setCreateTime(rs.getTimestamp("create_time"));
        return user; 
    } 
}
