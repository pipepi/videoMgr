/**
 * 
 */
package com.aepan.sysmgr.dao;

import java.util.List;

import com.aepan.sysmgr.model.packageinfo.PackageInfo;

/**
 * 套餐dao
 * @author rakika
 * 2015年8月12日下午3:28:27
 */
public interface PackageDao {
	
	public List<PackageInfo> getList(int packageType, int pageNo, int pageSize);
	
	public boolean save(PackageInfo rpackage);
	
	public boolean update(PackageInfo rpackage);
	
	public PackageInfo getById(int id);
	
}
