/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aepan.sysmgr.dao.PackageDao;
import com.aepan.sysmgr.model.packageinfo.PackageInfo;
import com.aepan.sysmgr.service.PackageService;

/**
 * @author rakika
 * 2015年8月12日下午3:41:20
 */
@Service
public class PackageServiceImpl implements PackageService {
	
	
	@Autowired
    PackageDao packageDao;
	
	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.PackageService#getList(java.util.Map, int, int)
	 */
	@Override
	public List<PackageInfo> getList(int packageType, int pageNo,
			int pageSize) {
		return packageDao.getList(packageType, pageNo, pageSize);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.PackageService#save(com._aepan.sysmgr.model.Package)
	 */
	@Override
	public boolean save(PackageInfo rpackage) {
		rpackage.harmSensitiveWord();
		return packageDao.save(rpackage);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.PackageService#update(com._aepan.sysmgr.model.Package)
	 */
	@Override
	public boolean update(PackageInfo rpackage) {
		rpackage.harmSensitiveWord();
		return packageDao.update(rpackage);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.PackageService#getById(int)
	 */
	@Override
	public PackageInfo getById(int id) {
		// TODO Auto-generated method stub
		return packageDao.getById(id);
	}
	
	
}
