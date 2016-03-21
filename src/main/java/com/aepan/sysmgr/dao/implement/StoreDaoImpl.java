/**
 * 
 */
package com.aepan.sysmgr.dao.implement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com._21cn.framework.util.PageList;
import com._21cn.framework.util.PageTurn;
import com.aepan.sysmgr.dao.StoreDao;
import com.aepan.sysmgr.dao.rowmapper.StoreRowMapper;
import com.aepan.sysmgr.model.Store;
import com.aepan.sysmgr.model.hm.StoreSubInfo;
import com.mysql.fabric.xmlrpc.base.Array;

/**
 * @author rakika
 * 2015年8月10日下午4:47:53
 */
@Repository
public class StoreDaoImpl implements StoreDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.StoreDao#getList(java.util.Map, int, int)
	 */
	@Override
	public PageList<Store> getList(Map<String, Object> params, int pageNo,
			int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id, name, description, share_content, inner_code, type, ");
		sb.append(" private_dns, com_address, com_tele, user_id, logo_url,logo_url_301, max_logo_url,max_logo_url_414, status, create_time,update_time ");
		sb.append(" from t_sysmgr_store where 1=1  ");
		List<Object> paramss = new ArrayList<Object>();
		if(params.get("userId")!=null){
			sb.append(" and user_id = ? ");
			paramss.add(params.get("userId"));
		}
		if(params.get("status")!=null){
			sb.append(" and status = ? ");
			paramss.add(params.get("status"));
		}
		
		//params sortName
		if(params.get("sortName") != null){
			sb.append(" order by " + params.get("sortName"));
			sb.append(" " + params.get("sortOrder"));
		}
		List<Store> list = jdbcTemplate.query(sb.toString(), paramss.toArray(),
				new StoreRowMapper());
		PageList<Store> pageList = new PageList<Store>();
		PageTurn pageTurn = new PageTurn(pageNo, pageSize,
				params.get("iDisplayStart") == null ? 1 :
				Integer.parseInt(params.get("iDisplayStart").toString()));
		for(int i = 0; i < list.size(); i++){
		    pageList.add(list.get(i));	
		}
		pageList.setPageTurn(pageTurn);
		return pageList;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.StoreDao#getById(int)
	 */
	@Override
	public Store getById(int id) {
		
		String sql = "  select id, name, description, share_content, inner_code, "
				+ " type, private_dns, com_address, com_tele, user_id, logo_url, logo_url_301,max_logo_url,max_logo_url_414,"
				+ " status, create_time,update_time from t_sysmgr_store where id = ? ";
		return jdbcTemplate.queryForObject(sql, new Object[] {id}, new StoreRowMapper());
		
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.StoreDao#updateStatus(int)
	 */
	@Override
	public boolean updateStatus(int id, int status) {
		StringBuffer sb = new StringBuffer();
		sb.append(" update t_sysmgr_store set status = ? where id = ? ");
		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			status,
			id
		});
		return ret > 0;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.StoreDao#getOnlineListByUserId(int)
	 */
	@Override
	public List<Store> getOnlineListByUserId(int userId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id, name, description, share_content, inner_code, type, ");
		sb.append(" private_dns, com_address, com_tele, user_id, logo_url,logo_url_301, max_logo_url,max_logo_url_414, status, create_time,update_time ");
		sb.append(" from t_sysmgr_store where user_id = ? and status = 1 order by update_time desc ");
		
		List<Store> list = jdbcTemplate.query(sb.toString(), new Object[]{userId},
				new StoreRowMapper());
		return list;
	}
	private static RowMapper<StoreSubInfo> STORESUBINFO_OTHER_ROWMAPPER = new RowMapper<StoreSubInfo>() {
		
		@Override
		public StoreSubInfo mapRow(ResultSet row, int i) throws SQLException {
			StoreSubInfo s = new StoreSubInfo();
			s.setId(row.getInt("id"));
			s.setUserId(row.getInt("user_id"));
			s.setImg(row.getString("logo_url"));
			s.setImg301(row.getString("logo_url_301"));
			s.setName(row.getString("name"));
			s.setImgMax(row.getString("max_logo_url"));
			s.setImgMax414(row.getString("max_logo_url_414"));
			s.setPartnerUserId(row.getInt("partner_account_id"));
			return s;
		}
	};
	
	
	
	@Override
	public List<StoreSubInfo> getStoreSubInfoByIds(List<Integer> ids){
		StringBuffer sb = new StringBuffer();
		sb.append("select s.id,s.user_id,s.name,s.logo_url,s.logo_url_301,s.max_logo_url,s.max_logo_url_414,u.partner_account_id from t_sysmgr_store s,t_sysmgr_user u where s.user_id=u.id  and s.id in (");
		for (int i = 0; i < ids.size(); i++) {
			if(i!=0) sb.append(",");
			sb.append("?");
		}
		sb.append(")");
		List<StoreSubInfo>  list = jdbcTemplate.query(sb.toString(),ids.toArray(),STORESUBINFO_OTHER_ROWMAPPER);
		return list;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.StoreDao#getListByUserId(int)
	 */
	@Override
	public List<Store> getListByUserId(int userId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id, name, description, share_content, inner_code, type, ");
		sb.append(" private_dns, com_address, com_tele, user_id, logo_url,logo_url_301,  max_logo_url,max_logo_url_414, status, create_time,update_time ");
		sb.append(" from t_sysmgr_store where user_id = ? order by update_time desc ");
		
		List<Store> list = jdbcTemplate.query(sb.toString(), new Object[]{userId},
				new StoreRowMapper());
		return list;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.StoreDao#save(com._aepan.sysmgr.model.Store)
	 */
	@Override
	public boolean save(Store store) {
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into t_sysmgr_store(name, description, share_content, inner_code, ");
        sb.append(" type, private_dns, com_address, com_tele, user_id, logo_url, max_logo_url,create_time ) ");
        sb.append(" value(?, ? ,?, ?, ?, ?, ?, ?, ?, ?,?,?) ");
        Date now = new Date();
		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			store.getName(),
			store.getDescription(),
			store.getShareContent(),
			store.getInnerCode(),
			store.getType(),
			store.getPrivateDns(),
			store.getComAddress(),
			store.getComTele(),
			store.getUserId(),
			store.getLogoUrl(),
			store.getMaxLogoUrl(),
			now
		});
		return ret > 0;
	}
	@Override
	public void update(Store store){
		Date now = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append(" update t_sysmgr_store set name=?, description=?, share_content=?, inner_code=?, ");
        sb.append(" type=?, private_dns=?, com_address=?, com_tele=?,logo_url=?,logo_url_301=?, max_logo_url=?,max_logo_url_414=?,status=?, update_time = ? where id = ? ");
		jdbcTemplate.update(sb.toString(), new Object[]{
			store.getName(),
			store.getDescription(),
			store.getShareContent(),
			store.getInnerCode(),
			store.getType(),
			store.getPrivateDns(),
			store.getComAddress(),
			store.getComTele(),
			store.getLogoUrl(),
			store.getLogoUrl_301(),
			store.getMaxLogoUrl(),
			store.getMaxLogoUrl_414(),
			store.getStatus(),
			now,
			store.getId()
		});
	}
	@Override
	public void delete(int storeId){
		String sql = "delete from t_sysmgr_store where id = ? ";
		jdbcTemplate.update(sql, storeId);
	}
	/**
	 * 获取商家其它播放器
	 * @param userId
	 * @param storeId
	 * @return
	 */
	@Override
	public List<StoreSubInfo> getSellerOtherStores(int userId,int storeId){
		StringBuffer sb = new StringBuffer();
		sb.append("select s.id,s.user_id,s.name,s.status,s.logo_url,s.logo_url_301,s.max_logo_url,s.max_logo_url_414,u.partner_account_id from t_sysmgr_store s,t_sysmgr_user u where  s.user_id = ? and s.user_id=u.id and s.status=")
		.append(Store.STATUS_在线)
		.append("  and s.id <> ? order by s.id desc limit 0,5");
		return jdbcTemplate.query(sb.toString(), new Object[]{userId,storeId},STORESUBINFO_OTHER_ROWMAPPER);
	}
	/**
	 * 获取同类型非本商家的播放器
	 * @param userId
	 * @param storeId
	 * @param type
	 * @return
	 */
	@Override
	public List<StoreSubInfo> getCategoryOtherStores(int userId,String type,int num){
		StringBuffer sb = new StringBuffer();
		sb.append("select s.id,s.user_id,s.name,s.status,s.logo_url,s.logo_url_301,s.max_logo_url,s.max_logo_url_414,u.partner_account_id from t_sysmgr_store s,t_sysmgr_user u where  s.user_id=u.id and s.type like concat('%',?,'%') and s.status=")
		.append(Store.STATUS_在线)
		.append(" and s.user_id <> ? order by s.id desc limit 0,? ");
		return jdbcTemplate.query(sb.toString(), new Object[]{type,userId,num},STORESUBINFO_OTHER_ROWMAPPER);
	}
	@Override
	public int getMostHotStoreId(int productId){
		String sql = "select a.store_id from t_sysmgr_store_video a,t_sysmgr_video b,t_sysmgr_store_product c "+
				"where a.store_id = c.store_id and a.video_id = b.id "+
				"and c.product_id = ? group by a.store_id order by sum(b.h5_video_cnum+b.video_cnum) desc limit 0,1";
		int rs = 0;
		try {
			rs = jdbcTemplate.queryForObject(sql, new Object[]{productId}, Integer.class);
		} catch (Exception e) {
		}
		return rs;
	}
	@Override
	public int getOnlineAmount(){
		String sql = "select count(0) from t_sysmgr_store where status = 1 ";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
