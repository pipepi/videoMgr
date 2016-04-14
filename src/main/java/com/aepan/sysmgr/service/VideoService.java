/**
 * 
 */
package com.aepan.sysmgr.service;

import java.util.List;
import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.StoreVideo;
import com.aepan.sysmgr.model.Video;
import com.aepan.sysmgr.model.tempinfo.LinkVideoInfo;

/**
 * @author lanker
 * 2015年8月3日下午2:52:40
 */
public interface VideoService {
	public int insert(Video v) throws Exception;
	public Video get(int id);
	public void update(Video v);
	public void delete(int id,int userId);
	/**激活视频*/
	public boolean active(int videoId,boolean state);
	/**审核视频*/
	public boolean check(int videoId,int state,String msg);
	
	public PageList<Video> getList(Map<String, Object> params, int pageNo, int pageSize);
	/**清楚播放器绑定的视频*/
	boolean deleteByUserIdANDStoreId(int userId, int storeId);
	/**批量插入播放器-视频 绑定关系*/
	void batchInsert(int batchSize, List<StoreVideo> batchList, Integer userId);
	/**
	 * 通过播放器Id， 查询已关联到次播放器、已激活并审核通过的视频列表
	 * @param storeId
	 * @return
	 */
	List<Video> getListByStoreId(int storeId, int userId);
	/**
	 * 查询有第三方帐号名称信息的视频列表
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageList<Video> getListWithPatnerAccountName(Map<String, Object> params,
			int pageNo, int pageSize);
	/**
	 * 校验在套餐中是否有足够的数量来关联当前选中的视频
	 * @param batchList
	 * @return
	 */
	LinkVideoInfo canLink(int userId,int packgeId,int storeId,List<StoreVideo> batchList);
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
	 * 微软云视频上传和编码
	 */
	void azureUploadAndEncode();
	/**
	 * 获取关联播放器的logo
	 * @param videoIds
	 * @return
	 */
	Video getStoreLogoVideo(String videoIds);
	/**
	 * @param videoId
	 * @return
	 */
	List<StoreVideo> getStoreVideoListByVideoId(int videoId);
	/**
	 * 播放器关联视频，视频列表
	 * 排序： 已关联的视频>已发布的视频；然后发布时间，或播放次数排序（默认时间倒序）；
	 * @param storeId
	 * @param userId
	 * @param sortBy
	 * @param sortType
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageList<Video> videos4Link(int storeId, int userId, String sortBy,
			String sortType, int pageNo, int pageSize);
	/**
	 * 按视频id删除关联关系
	 * @param userId
	 * @param videoId
	 */
	void deleteLinkRelationByVideoId(int userId, int videoId);
}
