/**
 * 
 */
package com.aepan.sysmgr.dao;

import java.util.List;
import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.ProductInfo;
import com.aepan.sysmgr.model.ProductType;
import com.aepan.sysmgr.model.StoreProduct;

/**
 * @author rakika
 * 2015年7月14日下午3:51:45
 */
public interface ProductDao {

	public PageList<ProductInfo> getList(Map<String, Object> params, int pageNo, int pageSize);

	public PageList<ProductType> getTypeList(Map<String, Object> params, int pageNo, int pageSize);
	
	public List<ProductInfo> getByUserList(int userId);
	//根据用户ID和店铺ID获取对应产品列表
	public List<ProductInfo> getByUserIdAndStoreIdList(int userId, int StoreId);
	//创建产品
	public boolean create(ProductInfo productInfo);
	//更新产品信息
	public boolean update(ProductInfo productInfo);
	
	public ProductInfo getById(Integer id);
	
	public boolean save(ProductInfo productInfo);
	
	public boolean saveType(ProductType productType);
	
	public boolean updateType(ProductType productType);
	
	public ProductType getById(int id);
	
	public void batchInsert(int batchSize, List<StoreProduct> batchList, Integer userId);
	
	public boolean deleteByUserIdANDStoreId(int userId, int storeId);

	/**
	 * 播放器关联商品，查询已关联关系
	 * @param userId
	 * @param storeId
	 * @return
	 */
	List<StoreProduct> getStoreProductList(int userId, int storeId);

	/**
	 * 查询用户已关联产品关系
	 * @param userId
	 * @return
	 */
	List<StoreProduct> getStoreProductListByUserId(int userId);
	List<StoreProduct> getStoreProductListByProductId(int pId);

	/**
	 * @param storeId
	 * @return
	 */
	List<Integer> getStoreProductIdList(int storeId);

	/**
	 * @param userId
	 * @return
	 */
	int getStoreProductCountByUserId(int userId);

	/**
	 * 删除产品和播放器的关联关系
	 * @param userId
	 * @param productId
	 */
	void deleteLinkRelationByProductId(int productId);

	/**
	 * 重新初始化类目
	 * @param list
	 */
	void reInit(List<ProductType> list);

	/**
	 * @param storeId
	 * @param pId
	 * @return
	 */
	StoreProduct getStoreProduct(int storeId, int pId);

	/**
	 * @param storeProduct
	 */
	void update(StoreProduct storeProduct);

	/**
	 * 获取每个播放器关联视频数量 =》用于购买套餐时弹窗
	 * @param userId
	 * @return
	 */
	List<Integer> getLinkedProductNumPerStore(int userId);

}