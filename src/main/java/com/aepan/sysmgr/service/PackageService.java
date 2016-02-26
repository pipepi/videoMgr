/**
 * 
 */
package com.aepan.sysmgr.service;

import java.util.List;

import com.aepan.sysmgr.model.packageinfo.PackageInfo;

/**
 * @author rakika
 * 2015年8月12日下午3:40:52
 */
public interface PackageService {

	public List<PackageInfo> getList(int packageType, int pageNo, int pageSize);
	
	public boolean save(PackageInfo rpackage);
	
	public boolean update(PackageInfo rpackage);
	
	public PackageInfo getById(int id);
}
