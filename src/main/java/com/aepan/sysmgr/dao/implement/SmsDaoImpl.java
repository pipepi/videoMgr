/**
 * 
 */
package com.aepan.sysmgr.dao.implement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.aepan.sysmgr.dao.SmsDao;
import com.aepan.sysmgr.model.sms.SmsSendLog;

/**
 * @author lanker
 * 2015年9月25日下午3:05:18
 */
@Repository
public class SmsDaoImpl implements SmsDao {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	private static  RowMapper<SmsSendLog> HANDLE = new RowMapper<SmsSendLog>(){
		@Override
		public SmsSendLog mapRow(ResultSet row, int index) throws SQLException {
			SmsSendLog sendlog = new SmsSendLog();
			sendlog.id = row.getInt("id");
			sendlog.type = row.getInt("type");
			sendlog.phoneNo = row.getString("phone_no");
			sendlog.content = row.getString("content");
			sendlog.sendTime = row.getTimestamp("send_time");
			return sendlog;
		}
		
	};
	@Override
	public SmsSendLog getLastByPhoneNo(String phoneNo,int smsType) {
		String sql = "select * from t_sysmgr_sms where phone_no = ? and type = ? order by send_time desc limit 0,1 ";
		List<SmsSendLog> videoList = jdbcTemplate.query(sql, new Object[] {phoneNo,smsType},HANDLE);
		 if(videoList.size()>0){
			 return videoList.get(0);
		 }
		 return null;
	}
	@Override
	public void add(SmsSendLog log){
		String sql = "insert into t_sysmgr_sms(type,phone_no,content,send_time) values(?,?,?,?) ";
		jdbcTemplate.update(sql,log.type,log.phoneNo,log.content,new Date());
	}
	@Override
	public void delete(int logid){
		String sql = "delete from t_sysmgr_sms where id = ? ";
		jdbcTemplate.update(sql,logid);
	}
}
