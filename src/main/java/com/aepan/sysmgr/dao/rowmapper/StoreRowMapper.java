/**
 * 
 */
package com.aepan.sysmgr.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aepan.sysmgr.model.Store;
/**
 * 角色rowmapper
 * @author rakika
 * 2015年6月21日下午10:31:57
 */
public class StoreRowMapper implements RowMapper<Store>{

	/* 
	 * Role实现类
	 * (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public Store mapRow(ResultSet rs, int index) throws SQLException {
		Store store = new Store();
        store.setId(rs.getInt("id"));
        store.setName(rs.getString("name"));
        store.setDescription(rs.getString("description"));
        if(rs.getString("share_content") != null){
        	store.setShareContent(rs.getString("share_content"));
        }
        if(rs.getString("inner_code") != null){
        	store.setInnerCode(rs.getString("inner_code"));
        }
        if(rs.getString("type") != null){
        	store.setType(rs.getString("type"));
        }
        if(rs.getString("private_dns") != null){
        	store.setPrivateDns(rs.getString("private_dns"));
        }
        if(rs.getString("com_address") != null){
        	store.setComAddress(rs.getString("com_address"));
        }
        if(rs.getString("com_tele") != null){
        	store.setComTele(rs.getString("com_tele"));
        }
        store.setUserId(rs.getInt("user_id"));
        store.setLogoUrl(rs.getString("logo_url"));
        store.setLogoUrl_301(rs.getString("logo_url_301"));
        store.setMaxLogoUrl(rs.getString("max_logo_url"));
        store.setMaxLogoUrl_414(rs.getString("max_logo_url_414"));
        store.setStatus(rs.getInt("status"));
        store.setCreateTime(rs.getTimestamp("create_time"));
		return store;
	}

}
