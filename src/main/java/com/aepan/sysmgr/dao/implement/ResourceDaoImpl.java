/**
 * 
 */
package com.aepan.sysmgr.dao.implement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com._21cn.framework.util.PageList;
import com._21cn.framework.util.PageTurn;
import com.aepan.sysmgr.dao.ResourceDao;
import com.aepan.sysmgr.dao.rowmapper.ResourceRowMapper;
import com.aepan.sysmgr.model.Resource;

/**
 * 资源Dao实现类
 * @author rakika
 * 2015年6月20日上午9:50:47
 */
@Repository
public class ResourceDaoImpl implements ResourceDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/* 
	 * 根据用户名选出可用的资源信息
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ResourceDao#selectResourceByName(java.lang.String)
	 */
	@Override
	public List<Resource> selectResourceByName(String userName) {
		return jdbcTemplate.query(" select s.id, s.p_id, s.url, s.name, s.status, s.create_time " +
               " from t_sysmgr_user u, t_sysmgr_resource s, t_sysmgr_role r, t_sysmgr_privilege p " +
               " where u.username = ? and u.status = 1 and u.role_id = r.id and p.role_id = r.id " +
               " and s.id = p.resource_id and s.status = 1 ", 
				new Object[]{userName}, new ResourceRowMapper());
	}

	/* 
	 * 根据用户选择的关键字搜索资源
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ResourceDao#selectMapByMap(java.util.Map, int, int, boolean)
	 */
	@Override
	public PageList<Resource> selectMapByMap(
			Map<String, Object> params, int pageNo, int pageSize,
			boolean doCount) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select s.id, s.p_id, s.url, s.name, s.status, s.create_time ");
        sb.append(" from t_sysmgr_resource s ");
		
		//params sortName
		if(params.get("sortName") != null){
			sb.append(" order by " + params.get("sortName"));
			sb.append(" " + params.get("sortOrder") + ", ");
		}
		
		/**
		 * 分页
		 */
		sb.append(" LIMIT ");
		sb.append(params.get("iDisplayStart") + " ");
		sb.append(params.get("pageSize"));
		
		List<Resource> list = jdbcTemplate.query(sb.toString(), new ResourceRowMapper());
		
		PageList<Resource> pageList = new PageList<Resource>();
		PageTurn pageTurn = new PageTurn(Integer.parseInt(params.get("pageNo").toString()),
				Integer.parseInt(params.get("pageSize").toString()),
				Integer.parseInt(params.get("iDisplayStart").toString()));
		for(int i = 0; i < list.size(); i++){
		    pageList.add(list.get(i));	
		}
		pageList.setPageTurn(pageTurn);
		return pageList;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ResourceDao#selectMapByMap(java.util.Map, int, int)
	 */
	@Override
	public PageList<Resource> selectMapByMap(Map<String, Object> params,
			int pageNo, int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select s.id, s.p_id, s.url, s.name, s.status, s.create_time ");
        sb.append(" from t_sysmgr_resource s ");
		
		//params sortName
		if(params.get("sortName") != null){
			sb.append(" order by " + params.get("sortName"));
			sb.append(" " + params.get("sortOrder"));
		}
		
		/**
		 * 分页
		 */
		if(params.get("iDisplayStart") != null &&
				params.get("pageSize") != null){
			sb.append(" LIMIT ");
			sb.append(params.get("iDisplayStart") + ", ");
			sb.append(params.get("pageSize"));
		}
		
		
		List<Resource> list = jdbcTemplate.query(sb.toString(), new ResourceRowMapper());
		PageList<Resource> pageList = new PageList<Resource>();
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
	 * @see com._aepan.sysmgr.dao.ResourceDao#insert(com._aepan.sysmgr.model.Resource)
	 */
	@Override
	public void insert(Resource resource) {
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into t_sysmgr_resource (p_id, url, name, status) ");
		sb.append(" values(? ,? ,?, ?) ");
		jdbcTemplate.update(sb.toString(), new Object[]{resource.getPid(), resource.getUrl(),
			resource.getName(), resource.getStatus()});
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ResourceDao#getById(int)
	 */
	@Override
	public Resource getById(int id) {
		return jdbcTemplate.queryForObject(" select s.id, s.p_id, s.url, s.name, s.status, s.create_time " +
		        " from t_sysmgr_resource s where s.id = ? ", 
				new Object[]{id}, new ResourceRowMapper());
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ResourceDao#update(com._aepan.sysmgr.model.Resource)
	 */
	@Override
	public void update(Resource resource) {
		StringBuffer sb = new StringBuffer();
		sb.append(" update t_sysmgr_resource set p_id = ?, url = ?, name = ?, status = ? ");
		sb.append(" where id = ? ");
		jdbcTemplate.update(sb.toString(), new Object[]{resource.getPid(), resource.getUrl(),
			resource.getName(), resource.getStatus(), resource.getId()});
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ResourceDao#deleteResourceByMap(java.util.Map)
	 */
	@Override
	public void deleteResourceByMap(Map<String, Object> params) {
    	Integer eqResourceId = Integer.parseInt(params.get("eqId").toString());
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("eqResourceId", eqResourceId);
    	
//    	//删除权限表相关记录
//    	sysmgrPrivilegeDao.deleteByMap(map);
    	
//    	deleteByMap(params);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ResourceDao#selectResourceNotExist(int)
	 */
	@Override
	public List<Resource> selectResourceNotExist(int roleId) {
		return jdbcTemplate.query(" select id, p_id, url, name, status, create_time " +
                    " from t_sysmgr_resource where id not in( select s.id from " +
                    " t_sysmgr_user u, t_sysmgr_resource s, t_sysmgr_privilege p " + 
                    " where u.role_id = ? and u.role_id = p.role_id and p.resource_id = s.id) ", 
					new Object[]{roleId}, new ResourceRowMapper());
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.ResourceDao#selectResourceByRoleId(java.lang.Integer)
	 */
	@Override
	public List<Integer> selectResourceByRoleId(Integer roleId) {
		List<Resource> list = jdbcTemplate.query(" select  s.id, s.p_id, s.url, s.name, "
				+ " s.status, s.create_time from t_sysmgr_resource s, t_sysmgr_privilege p " + 
                " where p.role_id = ? and p.resource_id = s.id group by s.id ", 
				new Object[]{roleId}, new ResourceRowMapper());
		List<Integer> rlist = new ArrayList<Integer>();
		for(int i = 0; i < list.size(); i++){
			Resource rs = list.get(i);
			rlist.add(rs.getId());
		}
		return rlist;
	}

}
