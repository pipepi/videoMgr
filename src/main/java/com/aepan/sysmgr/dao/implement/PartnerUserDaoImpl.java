/**
 * 
 */
package com.aepan.sysmgr.dao.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com._21cn.framework.util.PageList;
import com._21cn.framework.util.PageTurn;
import com.aepan.sysmgr.dao.PartnerUserDao;
import com.aepan.sysmgr.dao.rowmapper.PartnerUserRowMapper;
import com.aepan.sysmgr.dao.rowmapper.UserRowMapper;
import com.aepan.sysmgr.model.PartnerUser;
import com.aepan.sysmgr.model.User;

/**
 * @author rakika
 * 2015年9月4日下午10:04:29
 */
@Repository
public class PartnerUserDaoImpl implements PartnerUserDao {
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
	@Override
	public PartnerUser getByPartnerUserName(String PartnerUserName) {
		StringBuffer sb = new StringBuffer("select u.id, u.username as seller_name, ");
		sb.append(" u.partner_account_name, u.email, u.package_id, p.name, ");
		sb.append(" s.player_num, s.video_num, s.product_num, p.price, s.duration, u.create_time ");
        sb.append(" from t_sysmgr_user u, t_sysmgr_package_stat s, t_sysmgr_package p ");
        sb.append(" where s.user_id = u.id and p.id = u.package_id and u.himall_name = ? ");
        
        return jdbcTemplate.queryForObject(sb.toString(), new Object[]{ PartnerUserName},
    				new PartnerUserRowMapper());
	}
	
	
	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.HimallUserDao#getList(java.util.Map, int, int)
	 */
	@Override
	public PageList<PartnerUser> getList(Map<String, Object> params, int pageNo,
			int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select u.id, u.username as seller_name, u.partner_account_name, u.email, u.package_id, ");
		sb.append(" p.name as package_name, s.player_num, s.video_num, s.product_num,"
				+ "p.player_num as total_player_num,p.player_num*p.product_num as total_product_num,p.player_num*p.video_num as total_video_num ,p.price, s.duration,s.end_time, u.create_time ");
		sb.append(" from t_sysmgr_user u, t_sysmgr_package_stat s, t_sysmgr_package p where s.user_id = u.id and p.id = u.package_id ");

        StringBuffer paramBf = new StringBuffer(" ");
        ArrayList<Object> paramList = new ArrayList<Object>();
        //params userName
        String packageId = (String)params.get("packageId");
        if(packageId != null&&packageId != "null"&&!packageId.isEmpty()){
    		paramBf.append(" and u.package_id = ? ");
    		paramList.add(packageId);
        }
        
        String sellerName = (String)params.get("sellerName");
        if( sellerName!= null&&!sellerName.isEmpty()){
        	
        	
        	paramBf.append(" and u.partner_account_name like concat('%',?,'%') ");
        	paramList.add(sellerName);
        }
		
		
		PageList<PartnerUser> pageList = new PageList<PartnerUser>();
		
		
		int startIndex = (pageNo-1)*pageSize;
        sb.append(paramBf.toString()).append(" limit "+startIndex+","+pageSize);
		List<PartnerUser> list = jdbcTemplate.query(sb.toString(),paramList.toArray(),new PartnerUserRowMapper());
		
		String countSql = "select count(0) from t_sysmgr_user u where u.package_id>0 "+paramBf.toString();
		int count = (Integer)jdbcTemplate.queryForObject(countSql,paramList.toArray(),Integer.class);
		PageTurn pageTurn = new PageTurn(pageNo, pageSize,count);
		for(int i = 0; i < list.size(); i++){
		    pageList.add(list.get(i));	
		}
	
		pageList.setPageTurn(pageTurn);
		return pageList;
	}


	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.HimallUserDao#getByHimallUser(java.lang.String)
	 */
	@Override
	public User getByPartnerUser(String himallUserName) {
		StringBuffer sb = new StringBuffer(" select t.id, t.username, t.partner_account_name, t.password, ");
		sb.append(" t.partner_mobile,t.email, t.role_id, t.status, r.name as rolename, t.package_id, ");
		sb.append(" p.name as packagename, t.create_time from t_sysmgr_user t, ");
		sb.append(" t_sysmgr_role r, t_sysmgr_package p where t.role_id = r.id ");
		sb.append(" and p.id = t.package_id and t.partner_account_name = ? ");
        
		return jdbcTemplate.queryForObject(sb.toString(), new Object[]{ himallUserName},
				new UserRowMapper());
	}

	

}
