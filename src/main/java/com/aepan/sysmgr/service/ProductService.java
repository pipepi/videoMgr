/**
 * 
 */
package com.aepan.sysmgr.service;

import java.util.List;
import java.util.Map;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.ProductInfo;
import com.aepan.sysmgr.model.StoreProduct;
import com.aepan.sysmgr.model.tempinfo.LinkProductInfo;

/**
 * @author rakika
 * 2015年7月14日下午3:50:33
 */
public interface ProductService {

	public PageList<ProductInfo> getList(Map<String, Object> params, int pageNo, int pageSize);
	//根据用户ID和店铺ID获取对应产品列表
	public List<ProductInfo> getByUserIdAndStoreIdList(int userId, int StoreId);
	//创建产品
	public boolean create(ProductInfo productInfo);
	//更新产品信息
	public boolean update(ProductInfo productInfo);
	
	public ProductInfo getById(Integer id);
	
	public boolean save(ProductInfo productInfo);
	
	public List<ProductInfo> getByUserList(int userId);

    public void batchInsert(int batchSize, List<StoreProduct> batchList, Integer userId);
	
	public boolean deleteByUserIdANDStoreId(int userId, int storeId);
	/**
	 * 校验在套餐中是否有足够的数量来关联当前选中的商品
	 * @param userId
	 * @param packgeId
	 * @param storeId
	 * @param batchList
	 * @return
	 */
	LinkProductInfo canLink(int userId, int packgeId, int storeId,
			List<StoreProduct> batchList);
	
	List<Integer> getStoreProductIdList(int storeId);
	/**
	 * @param userId
	 * @return
	 */
	int getStoreProductCountByUserId(int userId);
	/**
	 * 删除产品和播放器的关联关联
	 * @param userId
	 * @param productId
	 */
	void deleteLinkRelationByProductId(int productId);
}
