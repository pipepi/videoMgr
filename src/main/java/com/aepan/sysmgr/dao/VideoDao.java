/**
 * 
 */
package com.aepan.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.StoreVideo;
import com.aepan.sysmgr.model.Video;

/**
 * @author lanker
 * 2015年8月3日上午11:27:26
 */
public interface VideoDao {
	public Video findVideoById(int id );
	public void delete(int id);
	public PageList<Video> pageList(Map<String, Object> params, int pageNo, int pageSize);
	public int addVideoWithGenKey(Video v) throws Exception;
	public void updateVideo(Video v);
	/**
	 * @param batchSize
	 * @param batchList
	 * @param userId
	 */
	void batchInsert(int batchSize, List<StoreVideo> batchList, Integer userId);
	/**
	 * @param userId
	 * @param storeId
	 * @return
	 */
	boolean deleteByUserIdANDStoreId(int userId, int storeId);
	/**
	 * 查询播放器管理视频
	 * @param userId
	 * @param storeId
	 * @return
	 */
	List<StoreVideo> getStoreVideoList(int userId, int storeId);
	/**
	 * 通过播放器Id， 查询已关联到次播放器、已激活并审核通过的视频列表
	 * @param storeId
	 * @return
	 */
	List<Video> getListByStoreId(int storeId, int userId);
	/**
	 * 查询用户已管理视频
	 * @param userId
	 * @return
	 */
	List<StoreVideo> getStoreVideoListByUserId(int userId);
	/**
	 * 按视频id删除关联关系
	 * @param userId
	 * @param videoId
	 */
	void deleteLinkRelationByVideoId(int userId, int videoId);
	/**
	 * @param userId
	 * @return
	 */
	int getStoreVideoCountByUserId(int userId);
	/**
	 * @param userId
	 * @return
	 */
	int getVideoCountByUserId(int userId);
	/**
	 * @param id
	 */
	void addVideoCNum(int id);
	/**
	 * @param id
	 */
	void addH5VideoCNum(int id);
	/**
	 * @param userId
	 * @return
	 */
	float getUsedFlowNumByUserId(int userId);
	/**
	 * 查询视频数量
	 * @param params
	 * @return
	 */
	int pageCount(Map<String, Object> params);
	/**
	 * @param videoId
	 * @return
	 */
	List<StoreVideo> getStoreVideoListByVideoId(int videoId);
	/**
	 * @param storeId
	 * @return
	 */
	int getStoreClickNum(int storeId);
}
