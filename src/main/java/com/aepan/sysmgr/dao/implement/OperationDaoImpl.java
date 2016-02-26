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
import com.aepan.sysmgr.dao.OperationDao;
import com.aepan.sysmgr.dao.rowmapper.OperationRowMapper;
import com.aepan.sysmgr.model.Operation;

/**
 * @author rakika
 * 2015年7月11日上午9:22:27
 */
@Repository
public class OperationDaoImpl implements OperationDao {

	
//	private static final Logger logger = LoggerFactory.getLogger(OperationDaoImpl.class);

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.OperationDao#selectMapByMap(java.util.Map, int, int)
	 */
	@Override
	public PageList<Operation> selectMapByMap(Map<String, Object> params,
			int pageNo, int pageSize) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id, shortcut, name, create_time from t_sysmgr_operation ");
		
		//params sortName
		if(params.get("sortName") != null){
			sb.append(" order by " + params.get("sortName"));
			sb.append(" " + params.get("sortOrder"));
		}
		List<Operation> list = jdbcTemplate.query(sb.toString(), new OperationRowMapper());
		PageList<Operation> pageList = new PageList<Operation>();
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
	 * @see com._aepan.sysmgr.dao.OperationDao#selectOperationByRoleId(java.lang.Integer)
	 */
	@Override
	public List<String> selectOperationByRoleId(Integer roleId) {
		List<Operation> list = jdbcTemplate.query(" select o.id, o.shortcut, o.name, o.create_time " +
               " from t_sysmgr_operation o, t_sysmgr_privilege p where p.role_id = ? "
               + " and p.operation_id = o.id group by o.id ", 
				new Object[]{roleId}, new OperationRowMapper());
		List<String> rlist = new ArrayList<String>();
		for(int i = 0; i < list.size(); i++){
			Operation rs = list.get(i);
			rlist.add(rs.getId() + "");
		}
		return rlist;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.OperationDao#create(com._aepan.sysmgr.model.Operation)
	 */
	@Override
	public boolean create(Operation operation) {
		StringBuffer sb = new StringBuffer(" insert into t_sysmgr_operation( shortcut, name )");
		sb.append(" values(?, ?) ");

		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			operation.getShortcut(),
			operation.getName()
		});
		return ret > 0;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.OperationDao#getById(int)
	 */
	@Override
	public Operation getById(int id) {
		return jdbcTemplate.queryForObject(" select id, shortcut, name, create_time " +
                " from t_sysmgr_operation where id = ? ", 
				new Object[] {id}, new OperationRowMapper());
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.OperationDao#update(com._aepan.sysmgr.model.Operation)
	 */
	@Override
	public boolean update(Operation operation) {
		StringBuffer sb = new StringBuffer(" update t_sysmgr_operation set shortcut = ?, ");
		sb.append(" name = ? where id = ? ");
		
		int ret = jdbcTemplate.update(sb.toString(), new Object[]{
			operation.getShortcut(),
			operation.getName(),
			operation.getId()
		});
		return ret > 0;
	}

}
