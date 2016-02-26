/**
 * 
 */
package com.aepan.sysmgr.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aepan.sysmgr.model.CpFile;
/**
 * 文件rowmapper
 * @author rakika
 * 2015年6月21日下午10:31:57
 */
public class CpFileRowMapper implements RowMapper<CpFile>{

	/* 
	 * Role实现类
	 * (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public CpFile mapRow(ResultSet rs, int index) throws SQLException {
		CpFile cpFile = new CpFile();
		cpFile.setId(rs.getInt("id"));
		cpFile.setFileName(rs.getString("file_name"));
		cpFile.setOrgFileName(rs.getString("org_file_name"));
		cpFile.setType(rs.getInt("type"));
		cpFile.setCreateTime(rs.getTimestamp("create_time"));
		return cpFile;
	}
}
