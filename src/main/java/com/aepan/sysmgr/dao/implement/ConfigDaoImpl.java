/**
 * 
 */
package com.aepan.sysmgr.dao.implement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.aepan.sysmgr.dao.ConfigDao;
import com.aepan.sysmgr.model.config.Config;
import com.aepan.sysmgr.util.DaoUtil;

/**
 * @author lanker
 * 2015年8月10日上午10:54:52
 */
@Repository
public class ConfigDaoImpl implements ConfigDao {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	private static  RowMapper<Config> HANDDLE = new RowMapper<Config>(){
		@Override
		public Config mapRow(ResultSet rs, int index) throws SQLException {
			Config config = new Config();
			config.id = rs.getInt("id");
			config.name = rs.getString("name");
			config.config = rs.getString("config");
			return config;
		}
		
	};
	@Override
	public void add(Config c) {
		String sql = "insert into t_sysmgr_config(id,name,config) values(?,?,?)";
		jdbcTemplate.update(sql, c.id,c.name,c.config);
	}
	@Override
	public List<Config> queryAll(){
		String sql = "select * from t_sysmgr_config limit 0,100 ";
		return jdbcTemplate.query(sql, HANDDLE);
	}
	@Override
	public Config query(int id){
		String sql = "select * from t_sysmgr_config where id = ? ";
		return DaoUtil.queryForObject(jdbcTemplate, sql, HANDDLE, id);
	}
	@Override
	public void update(Config c){
		String sql = "update t_sysmgr_config set name = ? , config = ? where id = ? ";
		jdbcTemplate.update(sql, c.name,c.config,c.id);
	}
}
