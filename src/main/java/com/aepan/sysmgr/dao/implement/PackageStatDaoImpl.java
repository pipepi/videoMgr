/**
 * 
 */
package com.aepan.sysmgr.dao.implement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.aepan.sysmgr.dao.PackageStatDao;
import com.aepan.sysmgr.dao.rowmapper.PackageStatRowMapper;
import com.aepan.sysmgr.model.Video;
import com.aepan.sysmgr.model.packageinfo.PackageFlow;
import com.aepan.sysmgr.model.packageinfo.PackageStat;

/**
 * 
 * @author Doris
 * 2015年9月8日下午3:43:34
 */
@Repository
public class PackageStatDaoImpl implements PackageStatDao {
	private final Logger logger = LoggerFactory.getLogger(PackageStatDaoImpl.class);
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public boolean save(PackageStat packageStat) {
		StringBuffer sb = new StringBuffer(" insert into t_sysmgr_package_stat(user_id,"
				+ "package_id,player_num,video_num,product_num,flow_num,used_flow_num,duration,send_notify,start_time,end_time,create_time,update_time) values( ?,?,?,?,?,?,?,?,?,?,?,now(),now())");

		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			packageStat.getUserId(),
			packageStat.getPackageId(),
			packageStat.getPlayerNum(),
			packageStat.getVideoNum(),
			packageStat.getProductNum(),
			packageStat.getFlowNum(),
			packageStat.getUsedFlowNum(),
			packageStat.getDuration(),
			packageStat.getSendNotify(),
			packageStat.getStartime(),
			packageStat.getEndTime()
		});
		return ret > 0;
	}
	

	@Override
	public boolean updateUsedNum(PackageStat packageStat) {
		StringBuffer sb = new StringBuffer(" update t_sysmgr_package_stat set "
				+ " player_num=?,video_num=?,product_num=?,update_time=now() where user_id = ? ");

		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			
			packageStat.getPlayerNum(),
			packageStat.getVideoNum(),
			packageStat.getProductNum(),
			packageStat.getUserId()
		});
		return ret > 0;
	}
	
	
	
	
	@Override
	public boolean updatePackageStat(PackageStat packageStat) {
		
		
		StringBuffer sb = new StringBuffer(" update t_sysmgr_package_stat set package_id=?,duration=?,flow_num=?,send_notify=0,start_time=?,end_time=?,update_time=now() where user_id = ? ");

		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			packageStat.getPackageId(),
			packageStat.getDuration(),
			packageStat.getFlowNum(),
			packageStat.getStartime(),
			packageStat.getEndTime(),
			packageStat.getUserId()
		});
		return ret > 0;
	}
	
	
	
	@Override
	public boolean addUsedFlowNum(Video video,boolean isH5){
		
		String sql="update t_sysmgr_package_stat  set used_flow_num=used_flow_num+? where user_id=?";
		ArrayList<Object> param = new ArrayList<Object>();
		if(isH5){
			param.add(video.h5VideoSize);
		}else{
			param.add(video.videoSize);
		}
		
		param.add(video.getUserId());
		int ret=jdbcTemplate.update(sql, param.toArray());
		
		return ret>0;
	}
	
	

	
	@Override
	public PackageStat getByUserId(int userId) {
		
		String sql = "select * from t_sysmgr_package_stat where user_id=?";
		
		
		List<PackageStat> packageStatList = jdbcTemplate.query(sql, new Object[] {userId},new PackageStatRowMapper());
		 
		 if(packageStatList.size()>=1){
			 return packageStatList.get(0);
		 }
		 return null;
		
	}
	
	
	@Override
	public List<PackageStat> queryLessThan30PackageStat(){
		String sql = "SELECT * FROM t_sysmgr_package_stat  where (send_notify&1)=0 and date_add(end_time,interval -30 day)<now() limit 2000";
		return jdbcTemplate.query(sql, new PackageStatRowMapper());
	}
	
	
	@Override
	public List<PackageStat> queryLessThan200M(){
		String sql = "SELECT * FROM t_sysmgr_package_stat  where (send_notify&2)=0 and (flow_num-used_flow_num)<200 limit 2000";
		return jdbcTemplate.query(sql,new PackageStatRowMapper());
	}
	
	
	@Override
	public void addPackageStatSendNotify(int userId,int property){
		String sql = "update t_sysmgr_package_stat set send_notify=send_notify|? where user_id=?";
		jdbcTemplate.update(sql, new Object[]{property,userId});
	}
	
	
	
	
	@Override
	public boolean addPackageFlow(PackageFlow packageFlow) {
		
		float flowNumM=packageFlow.getFlowNum()*1024*1024;
		StringBuffer sb = new StringBuffer(" insert into t_sysmgr_flow(user_id,"
				+ "flow_num,start_time,end_time,create_time,update_time) values( ?,?,?,?,now(),now())");

		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			packageFlow.getUserId(),
			flowNumM,
			packageFlow.getStartTime(),
			packageFlow.getEndTime(),
		});
		return ret > 0;
	}
	
	@Override
	public float getFlowNumByUserId(int userId) {
		
		String sql = "select sum(flow_num) from t_sysmgr_flow where user_id=? ";
		
		Float flowNum = jdbcTemplate.queryForObject(sql, new Object[] {userId},Float.class);
		if(flowNum==null){
			flowNum=0f;
		}
		return  flowNum;
		
	}
	@Override
	public void countUsedFlow(){
		Date now = new Date();
		StringBuffer sql= new StringBuffer();
				sql.append("update t_sysmgr_package_stat  set used_flow_num = used_flow_num+")
					.append(" ifnull((")
					.append(" select SUM(flow)")
					.append(" from t_sysmgr_flow_log") 
					.append(" where t_sysmgr_flow_log.user_id = t_sysmgr_package_stat.user_id")
					.append("	and t_sysmgr_flow_log.create_time>=t_sysmgr_package_stat.used_flow_count_time")
					.append(" group by t_sysmgr_flow_log.user_id")
					.append(" ),0),used_flow_count_time = ? ");
	    int updateRows=jdbcTemplate.update(sql.toString(), now);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    logger.debug("countUsedFlow updateRows="+updateRows+" at "+sdf.format(now).toString());
	}
	 
}