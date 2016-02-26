/**
 * 
 */
package com.aepan.sysmgr.dao.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.aepan.sysmgr.dao.CpFileDao;
import com.aepan.sysmgr.dao.rowmapper.CpFileRowMapper;
import com.aepan.sysmgr.model.CpFile;

/**
 * @author rakika
 * 2015年8月10日下午10:13:11
 */
@Repository
public class CpFileDaoImpl implements CpFileDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.FileDao#save(com._aepan.sysmgr.model.Store)
	 */
	@Override
	public boolean save(CpFile file) {
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into t_sysmgr_file(file_name, org_file_name, type) ");
		sb.append(" values(? ,? ,?) ");
		return jdbcTemplate.update(sb.toString(), new Object[]{file.getFileName(), 
			file.getOrgFileName(), file.getType()}) > 0;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.dao.CpFileDao#getById(int)
	 */
	@Override
	public CpFile getById(int id) {
		 return jdbcTemplate.queryForObject(" select id, file_name, org_file_name, type, create_time " +
		            " from t_sysmgr_file where  id  = ? ", 
					new Object[]{id}, new CpFileRowMapper());
	}
}
