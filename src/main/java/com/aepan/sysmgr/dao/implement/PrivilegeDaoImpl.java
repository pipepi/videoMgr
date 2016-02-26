/**
 * 
 */
package com.aepan.sysmgr.dao.implement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com._21cn.framework.util.PageList;
import com._21cn.framework.util.PageTurn;
import com.aepan.sysmgr.dao.PrivilegeDao;
import com.aepan.sysmgr.dao.rowmapper.PrivilegeRowMapper;
import com.aepan.sysmgr.model.MgrPrivilege;


/**
 * 权限Dao实现类
 * @author rakika
 * 2015年6月20日上午11:49:19
 */
@Repository
public class PrivilegeDaoImpl implements PrivilegeDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;

	/* 
	 * 根据用户名选择
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.PrivilegeDao#selectPermissionByUsername(java.lang.String)
	 */
	@Override
	public List<MgrPrivilege> selectPermissionByUsername(String userName) {
		return jdbcTemplate.query(" select p.id, p.resource_id,s.name as sname, p.operation_id, o.name as oname, "
				+ " p.role_id, r.name as rname, p.create_time,s.url, o.shortcut from t_sysmgr_user u, t_sysmgr_role r, "
				+ " t_sysmgr_privilege p, t_sysmgr_resource s, t_sysmgr_operation o where u.username = ? "
				+ " and u.status = 1 and u.role_id = r.id and p.role_id = r.id and s.id = p.resource_id "
				+ " and p.operation_id = o.id ", 
				new Object[]{userName}, new PrivilegeRowMapper());
	}

	/* 
	 * 获取权限
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.PrivilegeDao#getList(java.util.Map, int, int)
	 */
	@Override
	public PageList<MgrPrivilege> getList(Map<String, Object> params,
			int pageNo, int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select p.id, p.resource_id,s.name as sname, p.operation_id, o.name as oname, ");
		sb.append(" p.role_id, r.name as rname, p.create_time,s.url, o.shortcut from t_sysmgr_user u, t_sysmgr_role r, ");
		sb.append(" t_sysmgr_privilege p, t_sysmgr_resource s, t_sysmgr_operation o where u.username = ? ");
		sb.append(" and u.status = 1 and u.role_id = r.id and p.role_id = r.id and s.id = p.resource_id ");
        sb.append(" and p.operation_id = o.id ");
        
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
		
		List<MgrPrivilege> list = jdbcTemplate.query(sb.toString(), 
				     new Object[]{params.get("userName")}, new PrivilegeRowMapper());
		PageList<MgrPrivilege> pageList = new PageList<MgrPrivilege>();
		PageTurn pageTurn = new PageTurn(pageNo, pageSize,
				params.get("iDisplayStart") == null ? 1 :
				Integer.parseInt(params.get("iDisplayStart").toString()));
		for(int i = 0; i < list.size(); i++){
		    pageList.add(list.get(i));	
		}
		pageList.setPageTurn(pageTurn);
		return pageList;
	}

	/* 
	 * 
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.PrivilegeDao#batchUpdatePrivilege(int, java.util.List, java.lang.Integer)
	 */
	@Override
	public void batchUpdatePrivilege(int batchSize,
			List<MgrPrivilege> batchList, Integer roleId) {
		
		final List<MgrPrivilege> rlist = batchList;
		jdbcTemplate.batchUpdate(" insert t_sysmgr_privilege(resource_id, operation_id, role_id) "
                    + " values(?, ?, ?)", new BatchPreparedStatementSetter(){

						/* (non-Javadoc)
						 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#getBatchSize()
						 */
						@Override
						public int getBatchSize() {
							return rlist.size();
						}

						/* (non-Javadoc)
						 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(java.sql.PreparedStatement, int)
						 */
						@Override
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							MgrPrivilege mp = (MgrPrivilege)rlist.get(i);
							ps.setInt(1, mp.getResourceId());
							ps.setInt(2, mp.getOperationId());
							ps.setInt(3, mp.getRoleId());
						}
                    });
		
	}
	
	/* 
	 * 删除该权限列表
	 * (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.PrivilegeDao#deleteByRoleId(java.lang.Integer)
	 */
	@Override
	public void deleteByRoleId(Integer roleId) {
		jdbcTemplate.update(" delete from t_sysmgr_privilege where role_id = ? ", 
				new Object[]{roleId});
	}

}
