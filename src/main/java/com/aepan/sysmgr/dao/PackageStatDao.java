/**
 * 
 */
package com.aepan.sysmgr.dao;

import java.util.List;

import com.aepan.sysmgr.model.Video;
import com.aepan.sysmgr.model.packageinfo.PackageFlow;
import com.aepan.sysmgr.model.packageinfo.PackageStat;

/**
 * @author Doris.zhou
 * 2015年9月8日下午3:36:19
 */
public interface PackageStatDao {

	/**
	 * @param packageStat
	 * @return
	 */
	boolean save(PackageStat packageStat);

	/**
	 * @param packageStat
	 * @return
	 */
	boolean updateUsedNum(PackageStat packageStat);

	/**
	 * @param userId
	 * @return
	 */
	PackageStat getByUserId(int userId);

	/**
	 * @param packageFlow
	 * @return
	 */
	boolean addPackageFlow(PackageFlow packageFlow);

	/**
	 * @param userId
	 * @return
	 */
	float getFlowNumByUserId(int userId);

	/**
	 * @param userId
	 * @param videoId
	 * @param isH5
	 * @return
	 */
	boolean addUsedFlowNum(Video video, boolean isH5);

	/**
	 * @param packageStat
	 * @return
	 */
	boolean updatePackageStat(PackageStat packageStat);

	/**
	 * @param userId
	 * @param property
	 */
	void addPackageStatSendNotify(int userId, int property);

	/**
	 * @return
	 */
	List<PackageStat> queryLessThan200M();

	/**
	 * @return
	 */
	List<PackageStat> queryLessThan30PackageStat();

	/**
	 * 统计流量 task
	 */
	void countUsedFlow();

	/**
	 * @param userId
	 */
	void countLindedVideoNum(int userId);

	/**
	 * @param userId
	 */
	void countLindedProductNum(int userId);

	/**
	 * @param userId
	 */
	void countStoreNum(int userId);

}
