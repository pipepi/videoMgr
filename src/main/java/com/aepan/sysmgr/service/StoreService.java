/**
 * 
 */
package com.aepan.sysmgr.service;

import java.util.List;
import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.Store;
import com.aepan.sysmgr.model.StoreProducts;
import com.aepan.sysmgr.model.StoreVideo;
import com.aepan.sysmgr.model.hm.StoreSubInfo;

/**
 * 店铺service
 * @author rakika
 * 2015年8月10日下午4:42:57
 */
public interface StoreService {

	public PageList<Store> getList(Map<String, Object> params, int pageNo, int pageSize);
	//获取该用户在线商铺的的列表
    public List<Store> getOnlineListByUserId(int userId);
    //获取该用户的所有商铺
    public List<Store> getListByUserId(int userId);
	
	public Store getById(int id);
	//更新店铺状态
	public boolean updateStatus(int id, int status);
	//获取店铺产品
	public StoreProducts getProductsByStoreId(int userId, int storeId);
	
	public boolean save(Store store);
	/**
	 * 查询带有关联商品、视频id和数量信息的播放器列表
	 * @param userId
	 * @return
	 */
	List<Store> getListHasLinkedIds(int userId);
	/**修改*/
	void update(ConfigService configService,Store store);
	/**
	 * 添加到搜索库
	 * @param store
	 */
	void addLucene(ConfigService configService,Store store);
	/**
	 * 添加到搜索库
	 * @param storeId
	 */
	void addLucene(ConfigService configService,int storeId);
	/**
	 * 通过播放器ids获取播放器列表名称和图片
	 * @param ids
	 * @return
	 */
	List<StoreSubInfo> getStoreSubInfoByIds(List<Integer> ids);
	/**
	 * 通过storeId找到商家前5个播放器，不足5个时，找同类别点击量最大的播放器
	 * @param storeId
	 * @return
	 */
	List<StoreSubInfo> queryOthersByStoreId(int storeId);
	/**删除*/
	void delete(ConfigService configService, int storeId, int userId);
	/**
	 * 查询关联产品数量
	 * @param storeId
	 * @return
	 */
	int getLinkedProductNum(int storeId);
	/**
	 * 查询关联视频数量
	 * @param userId
	 * @param storeId
	 * @return
	 */
	int getLinkedVideoNum(int userId, int storeId);
	void reflashSearchIndex(int videoId);
	/**
	 * @param configService
	 * @param storeId
	 * @param pId
	 */
	void addLucene(ConfigService configService, int storeId, int pId);
	/**
	 * @param userId
	 * @param storeId
	 * @return
	 */
	List<StoreVideo> getStoreVideoList(int userId, int storeId);
	/**
	 * @param configService
	 * @param store
	 */
	void addSolr(ConfigService configService, Store store);
	/**
	 * 获取每个播放器关联视频数量 =》用于购买套餐时弹窗
	 * @param userId
	 * @return
	 */
	List<Integer> getLinkedProductPerStore(int userId);
	/**
	 * 获取商品最热的播放器id
	 * @param productId
	 * @return
	 */
	int getMostHotStoreId(int productId);
}