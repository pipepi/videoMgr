/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aepan.sysmgr.dao.PackageStatDao;
import com.aepan.sysmgr.model.Video;
import com.aepan.sysmgr.model.packageinfo.PackageFlow;
import com.aepan.sysmgr.model.packageinfo.PackageStat;
import com.aepan.sysmgr.service.PackageStatService;

/**
 * @author Doris.zhou
 * 2015年9月8日下午3:36:19
 */
@Service
public class PackageStatServiceImpl implements PackageStatService{

	
	@Autowired
    PackageStatDao packageStatDao;
	/**
	 * @param packageStat
	 * @return
	 */
	public boolean save(PackageStat packageStat){
		return packageStatDao.save(packageStat);
	}

	/**
	 * @param packageStat
	 * @return
	 */
	public boolean updateUsedNum(PackageStat packageStat){
		return packageStatDao.updateUsedNum(packageStat);
	}
	
	
	
	@Override
	public boolean updatePackageStat(PackageStat packageStat){
		return packageStatDao.updatePackageStat(packageStat);
	}

	/**
	 * @param userId
	 * @return
	 */
	public PackageStat getByUserId(int userId){
		PackageStat stat = packageStatDao.getByUserId(userId);
		if(stat==null){
			PackageStat packageStat = new PackageStat();
			packageStat.setUserId(userId);
			packageStat.setUsedFlowCountTime(new Date());
			packageStatDao.save(packageStat );
			stat = packageStatDao.getByUserId(userId);
		}
		return stat;
	}
	
	
	@Override
	public boolean addPackageFlow(PackageFlow packageFlow) {
		return packageStatDao.addPackageFlow(packageFlow);
	}
	
		
	
	@Override
	public float getFlowNumByUserId(int userId) {
		return packageStatDao.getFlowNumByUserId(userId);
	}
	
	@Override
	public boolean addUsedFlowNum(Video video, boolean isH5){
		return packageStatDao.addUsedFlowNum( video, isH5);
	}

	@Override
	public void addPackageStatSendNotify(int userId, int property) {
		packageStatDao.addPackageStatSendNotify(userId, property);
	}

	@Override
	public List<PackageStat> queryLessThan200M() {
		return packageStatDao.queryLessThan200M();
	}
	
	@Override
	public List<PackageStat> queryLessThan30PackageStat() {
		return packageStatDao.queryLessThan30PackageStat();
	}
	@Override
	public void countUsedFlow(){
		packageStatDao.countUsedFlow();
	}
	@Override
	public void countLindedVideoNum(int userId){
		packageStatDao.countLindedVideoNum(userId);
	}
	@Override
	public void countLindedProductNum(int userId){
		packageStatDao.countLindedProductNum(userId);
	}
	@Override
	public void countStoreNum(int userId){
		packageStatDao.countStoreNum(userId);
	}
}
