/**
 * 
 */
package com.aepan.sysmgr.service;

import com.aepan.sysmgr.model.hm.PartnerProductPage;
import com.aepan.sysmgr.model.log.OperationLog;

/**
 * 调用合作方数据接口，后续可扩展为根据配置调用不同合作方的数据接口
 * @author lanker
 * 2016年1月18日下午5:13:53
 */
public interface PartnerDataService {
	/**获取商品详情sku，播放器数据接口，透传商品数据*/
	String getProductsSKU(String productIds, String videoIds, int page,int pagesize);
	/** 获取用户可关联商品列表  用于播放器关联商品*/
	public PartnerProductPage getProducts(int partnerUserId,int page,int pagesize,String orderBy,String orderType);
	/**根据ids获取商品信息列表，用于更新搜索索引*/
	public PartnerProductPage getProducts(String productIds);
	/**开通视频电商 通知*/
	void sendMsgOfOpenVideoStore(int partnerUserId, int packageId);
	/**商品被关联或取消关联 通知*/
	void sendMsgOfProductsLinked(String linkedPids,String unlinkedPids);
	/***获取商品分类*/
	String getCategory();
	/**卖家权限*/
	String sellerAuth(int sellerUserId);
	/**管理员权限*/
	String adminAuth(int adminUserId);
	/**操作日志*/
	void addLog(OperationLog oplog);
	/**
	 * 包含已关联且已下架商品    关联商品
	 * @param partnerUserId
	 * @param productIds
	 * @param searchKey
	 * @param pageNo
	 * @param pageSize
	 * @param orderBy
	 * @param orderType
	 * @return
	 */
	PartnerProductPage getProducts4Link(int partnerUserId, String productIds,
			String searchKey, int pageNo, int pageSize, String orderBy,
			String orderType);
}
