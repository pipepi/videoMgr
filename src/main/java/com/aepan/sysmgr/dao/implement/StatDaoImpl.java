/**
 * 
 */
package com.aepan.sysmgr.dao.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.aepan.sysmgr.dao.StatDao;

/**
 * 统计记录
 * @author rakika
 * 2015年9月12日下午4:05:40
 */
@Repository
public class StatDaoImpl implements StatDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.StatDao#statsVideo(java.lang.String, int, java.lang.String)
	 */
	@Override
	public boolean statsVideo(String videoId, int type, String sharePlat) {
		String name = "";
		if(type == 1 || type == 4){
			name = "点击";
		}else if(type == 2 || type == 5){
			name = "分享";
		}else if(type == 3){
			name = "流量";
		}else{
			name = "未知";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(" insert t_sysmgr_stat into (name, type, param_id, content) ");
		sb.append(" values( ?, ?, ?, ? ) ");
		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			name,
			type,
			videoId,
			sharePlat
		});
		return ret > 0;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.StatDao#statsProduct(java.lang.String, int, java.lang.String)
	 */
	@Override
	public boolean statsProduct(String videoId, int type, String sharePlat) {
		String name = "";
		if(type == 1 || type == 4){
			name = "点击";
		}else if(type == 2 || type == 5){
			name = "分享";
		}else if(type == 3){
			name = "流量";
		}else{
			name = "未知";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(" insert t_sysmgr_stat into (name, type, param_id, content) ");
		sb.append(" values( ?, ?, ?, ? ) ");
		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			name,
			type,
			videoId,
			sharePlat
		});
		return ret > 0;
	}

}
