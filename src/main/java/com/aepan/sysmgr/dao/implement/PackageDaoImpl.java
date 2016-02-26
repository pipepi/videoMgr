/**
 * 
 */
package com.aepan.sysmgr.dao.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.aepan.sysmgr.dao.PackageDao;
import com.aepan.sysmgr.dao.rowmapper.PackageRowMapper;
import com.aepan.sysmgr.model.packageinfo.PackageInfo;

/**
 * 套餐Dao
 * @author rakika
 * 2015年8月12日下午3:35:17
 */
@Repository
public class PackageDaoImpl implements PackageDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.PackageDao#getList(java.util.Map, int, int)
	 */
	@Override
	public List<PackageInfo> getList(int packageType, int pageNo,
			int pageSize) {
		ArrayList<Object> paramList = new ArrayList<Object>();
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from t_sysmgr_package where 1=1" );
		
		if(packageType!=0){
			sb.append(" and package_type=?");
			paramList.add(packageType);
		}
		
		paramList.add(pageSize);
		sb.append(" limit ?");
		
		List<PackageInfo> pageList = jdbcTemplate.query(sb.toString(),paramList.toArray(), new PackageRowMapper());
		
		return pageList;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.PackageDao#save(com._aepan.sysmgr.model.Package)
	 */
	@Override
	public boolean save(PackageInfo rpackage) {
		StringBuffer sb = new StringBuffer(" insert into t_sysmgr_package(name,package_type,player_num,video_num,product_num,price,duration,flow_num,month_flow_price,create_time,update_time) values( ?,?,?,?,?,?,?,?,?,now(),now())");

		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			rpackage.getName(),
			rpackage.getPackageType(),
			rpackage.getPlayerNum(),
			rpackage.getVideoNum(),
			rpackage.getProductNum(),
			rpackage.getPrice(),
			rpackage.getDuration(),
			rpackage.getFlowNum(),
			rpackage.getMonthFlowPrice(),
		});
		return ret > 0;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.PackageDao#update(com._aepan.sysmgr.model.Package)
	 */
	@Override
	public boolean update(PackageInfo rpackage) {
		StringBuffer sb = new StringBuffer(" update t_sysmgr_package set name  = ?, package_type=?, player_num=?,video_num=?,product_num=?,price=?,duration=?,flow_num=?,month_flow_price=?,update_time=now() where id = ? ");

		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			rpackage.getName(),
			rpackage.getPackageType(),
			rpackage.getPlayerNum(),
			rpackage.getVideoNum(),
			rpackage.getProductNum(),
			rpackage.getPrice(),
			rpackage.getDuration(),
			rpackage.getFlowNum(),
			rpackage.getMonthFlowPrice(),
			rpackage.getId()
		});
		return ret > 0;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.PackageDao#getById(int)
	 */
	@Override
	public PackageInfo getById(int id) {
		StringBuffer sb = new StringBuffer(" select * from t_sysmgr_package ");
		                  sb.append(" where id = ? ");
		return jdbcTemplate.queryForObject(sb.toString(), new Object[]{id}, new PackageRowMapper());
	}
	
}
