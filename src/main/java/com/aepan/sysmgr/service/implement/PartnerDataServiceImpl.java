/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aepan.sysmgr.model.config.PartnerConfig;
import com.aepan.sysmgr.model.hm.PartnerProductPage;
import com.aepan.sysmgr.model.log.OperationLog;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.PartnerDataService;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.HttpUtil;
import com.aepan.sysmgr.util.HttpsUtil;
import com.google.gson.Gson;

/**
 * @author lanker
 * 2016年1月18日下午5:20:03
 */
@Service
public class PartnerDataServiceImpl implements PartnerDataService {
	private static final Logger logger = LoggerFactory.getLogger(PartnerDataServiceImpl.class);
	@Autowired
	private ConfigService configService;
	
	/* 
	 * /**获取商品详情sku，播放器数据接口，透传商品数据
	 */
	@Override
	public String getProductsSKU(String productIds,String videoIds,int page,int pagesize){
		PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(configService);
		String productSKU = partnerConfig.rootPath+"/api/productsku/";
		StringBuffer param = new StringBuffer();
		param.append(productIds).append("/").append(videoIds).append("/1/2/").append(page).append("/").append(pagesize);
		String url = productSKU+param.toString();
		return HttpsUtil.doGet(url);
	}
	/* 
	 * 获取用户可关联商品列表  用于播放器关联商品
	 */
	@Override
	public PartnerProductPage getProducts(int partnerUserId,int page,int pagesize,String orderBy,String orderType) {
		PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(configService);
		String productPageUrl = partnerConfig.rootPath+"/api/product/";
		StringBuffer param = new StringBuffer();
		param.append(partnerUserId).append("/1/2/").append(page).append("/").append(pagesize).append("/"+orderBy+"/"+orderType);
		String url = productPageUrl+param.toString();
		String ret = HttpsUtil.doGet(url);
        if(ret!=null&&ret.length()>0){
        	Gson gson = new Gson();
        	PartnerProductPage hp = gson.fromJson(ret, PartnerProductPage.class);
        	return hp;
        }
		return null;
	}

	/* 
	 * 根据ids获取商品信息列表，用于更新搜索索引
	 */
	@Override
	public PartnerProductPage getProducts(String productIds) {
		PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(configService);
		String url = partnerConfig.rootPath+"/api/product/"+productIds;
		String ret = HttpsUtil.doGet(url);
        if(ret!=null&&ret.length()>0 ){
        	Gson gson = new Gson();
        	PartnerProductPage hp = gson.fromJson(ret, PartnerProductPage.class);
        	return hp;
        }
		return null;
	}
	/* 
	 * 通知 商家已开通视频电商
	 */
	@Override
	public void sendMsgOfOpenVideoStore(int partnerUserId,int packageId){
		PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(configService);
		String url = partnerConfig.rootPath+"/api/store/open/"+partnerUserId+"/"+packageId;
		HttpsUtil.doPost(url);
	}

	/* 
	 * 通知 商品已关联
	 */
	@Override
	public void sendMsgOfProductsLinked(String linkedPids, String unlinkedPids) {
		if(linkedPids.length()==0&&unlinkedPids.length()==0) return;
		if(linkedPids.length()==0){linkedPids = "0";}
		if(unlinkedPids.length()==0){unlinkedPids = "0";}
		PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(configService);
		String url = partnerConfig.rootPath+"/api/UpdateProductIsStore/"+linkedPids+"/"+unlinkedPids;
		HttpsUtil.doPost(url);
	}
	/* 
	 * 获取商品分类
	 */
	@Override
	public String getCategory(){
		PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(configService);
		String url = partnerConfig.rootPath+"/api/category";
		return HttpsUtil.doGet(url);
	}
	/* 
	 * 卖家权限
	 */
	@Override
	public String sellerAuth(int sellerUserId){
		PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(configService);
		String url = partnerConfig.rootPath+"/api/member/sellervideoauth";
		return HttpsUtil.doPost(url, "{id:"+sellerUserId+"}");
	}
	/* 
	 * 管理员权限
	 */
	@Override
	public String adminAuth(int adminUserId){
		PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(configService);
		String url = partnerConfig.rootPath+"/api/member/videoauth";
		return HttpsUtil.doPost(url, "{id:"+adminUserId+"}");
	}
	/* 
	 * 操作日志
	 */
	@Override
	public void addLog(OperationLog oplog){
		PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(configService);
		String url = partnerConfig.rootPath+"/api/operationlog/";
		if(oplog.getOpType()==OperationLog.TYPE_视频){
			url+="video";
		}else if(oplog.getOpType()==OperationLog.TYPE_套餐){
			url+="package";
		}else if(OperationLog.TYPE_播放器==oplog.getOpType()){
			url+="player";
		}
		HttpsUtil.doGet(url, oplog.toParams());
	}
}
