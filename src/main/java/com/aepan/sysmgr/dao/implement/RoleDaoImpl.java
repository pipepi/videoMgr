/**
 * 
 */
package com.aepan.sysmgr.dao.implement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com._21cn.framework.util.PageList;
import com._21cn.framework.util.PageTurn;
import com.aepan.sysmgr.dao.RoleDao;
import com.aepan.sysmgr.dao.rowmapper.RoleRowMapper;
import com.aepan.sysmgr.model.Role;

/**
 * 角色Dao实现类
 * @author rakika
 * 2015年6月21日下午10:26:02
 */
@Repository
public class RoleDaoImpl implements RoleDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.RoleDao#getList(java.util.Map, int, int)
	 */
	@Override
	public PageList<Role> getList(Map<String, Object> params, int pageNo,
			int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id, name, type, create_time from t_sysmgr_role ");
		
		//params sortName
		if(params.get("sortName") != null){
			sb.append(" order by " + params.get("sortName"));
			sb.append(" " + params.get("sortOrder"));
		}
		List<Role> list = jdbcTemplate.query(sb.toString(), new RoleRowMapper());
		PageList<Role> pageList = new PageList<Role>();
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
	 * @see com._aepan.sysmgr.dao.RoleDao#getById(int)
	 */
	@Override
	public Role getById(int id) {
		return jdbcTemplate.queryForObject(" select o.id, o.name, o.type, o.create_time " +
                    " from t_sysmgr_role o where o.id = ? ", 
					new Object[] {id}, new RoleRowMapper());
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.RoleDao#create(com._aepan.sysmgr.model.Role)
	 */
	@Override
	public boolean create(Role role) {
		StringBuffer sb = new StringBuffer(" insert into t_sysmgr_role( name, type )");
		sb.append(" values(?, ?) ");

		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			role.getName(),
			role.getType()
		});
		return ret > 0;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.RoleDao#update(com._aepan.sysmgr.model.Role)
	 */
	@Override
	public boolean update(Role role) {
		StringBuffer sb = new StringBuffer();
		sb.append(" update t_sysmgr_role set type = ?, name = ? where id = ? ");

		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			role.getType(),
			role.getName(),
			role.getId()
		});
		return ret > 0;
	}
	

}
