/**
 * 
 */
package com.aepan.sysmgr.dao.implement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.aepan.sysmgr.dao.FlowDao;
import com.aepan.sysmgr.model.Flow;

/**
 * @author lanker
 * 2015年12月23日下午2:35:03
 */
@Repository
public class FlowDaoImpl implements FlowDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Flow> HANDLE = new RowMapper<Flow>() {
		@Override
		public Flow mapRow(ResultSet rs, int arg1) throws SQLException {
			Flow f = new Flow();
			f.id = rs.getInt("id");
			f.userId = rs.getInt("user_id");
			f.storeId = rs.getInt("store_id");
			f.videoId = rs.getInt("video_id");
			f.flow = rs.getFloat("flow");
			f.createTime = rs.getDate("create_time");
			return f;
		}
	}; 
	@Override
	public void add(Flow f) {
		String sql = "insert into t_sysmgr_flow_log(user_id,store_id,video_id,flow,create_time)"
				+ " values(?,?,?,?,?)";
		Date now = new Date();
		jdbcTemplate.update(sql, f.userId,f.storeId,f.videoId,f.flow,now);
	}
}
