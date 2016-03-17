/**
 * 
 */
package com.aepan.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.Store;
import com.aepan.sysmgr.model.hm.StoreSubInfo;

/**
 * @author rakika
 * 2015年8月10日下午4:46:04
 */
public interface StoreDao {
	
	public PageList<Store> getList(Map<String, Object> params, int pageNo, int pageSize);
	//获取该用户在线商铺的的列表
	public List<Store> getOnlineListByUserId(int userId);
	//获取该用户所有商铺的列表
	public List<Store> getListByUserId(int userId);
	
	public Store getById(int id);
	//更新店铺状态
	public boolean updateStatus(int id, int status);
	//保存
	public boolean save(Store store);
	/**修改*/
	void update(Store store);
	/**删除*/
	void delete(int storeId);
	/**
	 * 通过ids获取播放器列表名称
	 * @param ids
	 * @return
	 */
	List<StoreSubInfo> getStoreSubInfoByIds(List<Integer> ids);
	/**
	 * 获取同类型非本商家的播放器
	 * @param userId
	 * @param type
	 * @param num
	 * @return
	 */
	List<StoreSubInfo> getCategoryOtherStores(int userId, String type, int num);
	/**
	 *  获取商家其它播放器
	 * @param userId
	 * @param storeId
	 * @return
	 */
	List<StoreSubInfo> getSellerOtherStores(int userId, int storeId);
	/**
	 * 获取商品最热的播放器id
	 * @param productId
	 * @return
	 */
	int getMostHotStoreId(int productId);
}
