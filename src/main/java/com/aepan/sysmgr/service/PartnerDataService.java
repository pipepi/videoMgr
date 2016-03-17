/**
 * 
 */
package com.aepan.sysmgr.service;

import com.aepan.sysmgr.model.hm.PartnerProductPage;

/**
 * 调用合作方数据接口，后续可扩展为根据配置调用不同合作方的数据接口
 * @author lanker
 * 2016年1月18日下午5:13:53
 */
public interface PartnerDataService {
	public PartnerProductPage getProducts(int partnerUserId,int page,int pagesize,String orderBy,String orderType);
	public PartnerProductPage getProducts(String productIds);
	/**开通视频电商 通知*/
	void sendMsgOfOpenVideoStore(int partnerUserId, int packageId);
	/**商品被关联或取消关联 通知*/
	void sendMsgOfProductsLinked(String linkedPids,String unlinkedPids);
}
