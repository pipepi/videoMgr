/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aepan.sysmgr.dao.ProductDao;
import com.aepan.sysmgr.dao.StoreDao;
import com.aepan.sysmgr.dao.VideoDao;
import com.aepan.sysmgr.model.Store;
import com.aepan.sysmgr.model.StoreProduct;
import com.aepan.sysmgr.model.StoreVideo;
import com.aepan.sysmgr.model.hm.PartnerProduct;
import com.aepan.sysmgr.model.hm.PartnerProductPage;
import com.aepan.sysmgr.service.PartnerDataService;
import com.aepan.sysmgr.service.StoreStatusService;

/**
 * @author lanker
 * 2016年4月14日上午10:38:08
 */
@Service
public class StoreStatusServiceImpl implements StoreStatusService {
	Logger logger = LoggerFactory.getLogger(StoreStatusServiceImpl.class);
	@Autowired
	private VideoDao videoDao;
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private PartnerDataService partnerDataService;
	@Override
	public void linkVideo(Store store, boolean haveV) {
		switch (store.getStatus()) {
		case Store.STATUS_V_NO_P_NO:store.setStatus(haveV?Store.STATUS_V_OK_P_NO:Store.STATUS_V_NO_P_NO);break;
		case Store.STATUS_V_NO_P_OFF:store.setStatus(haveV?Store.STATUS_V_OK_P_OFF:Store.STATUS_V_NO_P_OFF);break;
		case Store.STATUS_V_NO_P_OK:store.setStatus(haveV?Store.STATUS_V_OK_P_OK:Store.STATUS_V_NO_P_OK);break;
		case Store.STATUS_V_OFF_P_NO:store.setStatus(haveV?Store.STATUS_V_OK_P_NO:Store.STATUS_V_NO_P_NO);break;
		case Store.STATUS_V_OFF_P_OFF:store.setStatus(haveV?Store.STATUS_V_OK_P_OFF:Store.STATUS_V_NO_P_OFF);break;
		case Store.STATUS_V_OFF_P_OK:store.setStatus(haveV?Store.STATUS_V_OK_P_OK:Store.STATUS_V_NO_P_OK);break;
		case Store.STATUS_V_OK_P_NO:store.setStatus(haveV?Store.STATUS_V_OK_P_NO:Store.STATUS_V_NO_P_NO);break;
		case Store.STATUS_V_OK_P_OFF:store.setStatus(haveV?Store.STATUS_V_OK_P_OFF:Store.STATUS_V_NO_P_OFF);break;
		case Store.STATUS_V_OK_P_OK:store.setStatus(haveV?Store.STATUS_V_OK_P_OK:Store.STATUS_V_NO_P_OK);break;
		default:
			break;
		}
		Store dbStore = storeDao.getById(store.getId());
		if(dbStore.getStatus()!=store.getStatus()){
			logger.debug("\nstore[storeId="+store.getId()+"] status changed by link video from "+dbStore.getStatus()+" to "+store.getStatus());
			dbStore.setStatus(store.getStatus());
			storeDao.update(dbStore);
		}
	}
	@Override
	public void unActiveVideo(int videoId) {
		List<StoreVideo> storeVideoList = videoDao.getStoreVideoListByVideoId(videoId); 
		if(storeVideoList!=null&&!storeVideoList.isEmpty()){
			for (StoreVideo storeVideo : storeVideoList) {
				Store store = storeDao.getById(storeVideo.storeId); 
				if(store!=null){
					int statusOld = store.getStatus();
					switch (statusOld) {
					case Store.STATUS_V_OFF_P_NO:store.setStatus(Store.STATUS_V_NO_P_NO);break;
					case Store.STATUS_V_OFF_P_OFF:store.setStatus(Store.STATUS_V_NO_P_OFF);break;
					case Store.STATUS_V_OFF_P_OK:store.setStatus(Store.STATUS_V_NO_P_OK);break;
					case Store.STATUS_V_OK_P_NO:store.setStatus(Store.STATUS_V_NO_P_NO);break;
					case Store.STATUS_V_OK_P_OFF:store.setStatus(Store.STATUS_V_NO_P_OFF);break;
					case Store.STATUS_V_OK_P_OK:store.setStatus(Store.STATUS_V_NO_P_OK);break;
					default:
						break;
					}
					if(statusOld!=store.getStatus()){
						logger.debug("\nstore[storeId="+store.getId()+"] status changed by unactive video from "+statusOld+" to "+store.getStatus());
						storeDao.update(store);
					}
				}
			}
		}
	}

	@Override
	public void offLineVideo(int videoId) {
		List<StoreVideo> storeVideoList = videoDao.getStoreVideoListByVideoId(videoId); 
		if(storeVideoList!=null&&!storeVideoList.isEmpty()){
			for (StoreVideo storeVideo : storeVideoList) {
				Store store = storeDao.getById(storeVideo.storeId); 
				if(store!=null){
					int statusOld = store.getStatus();
					switch (statusOld) {
					case Store.STATUS_V_OK_P_NO:store.setStatus(Store.STATUS_V_OFF_P_NO);break;
					case Store.STATUS_V_OK_P_OFF:store.setStatus(Store.STATUS_V_OFF_P_OFF);break;
					case Store.STATUS_V_OK_P_OK:store.setStatus(Store.STATUS_V_OFF_P_OK);break;
					default:
						break;
					}
					if(statusOld!=store.getStatus()){
						logger.debug("\nstore[storeId="+store.getId()+"] status changed by offline video from "+statusOld+" to "+store.getStatus());
						storeDao.update(store);
					}
				}
			}
		}
	}

	@Override
	public void linkProduct(Store store, boolean haveP,boolean havePOff) {
		switch (store.getStatus()) {
		case Store.STATUS_V_NO_P_NO:store.setStatus(haveP?Store.STATUS_V_NO_P_OK:Store.STATUS_V_NO_P_NO);break;
		case Store.STATUS_V_NO_P_OFF:store.setStatus(haveP?(havePOff?Store.STATUS_V_NO_P_OFF:Store.STATUS_V_NO_P_OK):Store.STATUS_V_NO_P_NO);break;
		case Store.STATUS_V_NO_P_OK:store.setStatus(haveP?Store.STATUS_V_NO_P_OK:Store.STATUS_V_NO_P_OK);break;
		case Store.STATUS_V_OFF_P_NO:store.setStatus(haveP?Store.STATUS_V_OFF_P_OK:Store.STATUS_V_OFF_P_NO);break;
		case Store.STATUS_V_OFF_P_OFF:store.setStatus(haveP?(havePOff?Store.STATUS_V_OFF_P_OFF:Store.STATUS_V_OFF_P_OK):Store.STATUS_V_OFF_P_NO);break;
		case Store.STATUS_V_OFF_P_OK:store.setStatus(haveP?Store.STATUS_V_OFF_P_OK:Store.STATUS_V_OFF_P_NO);break;
		case Store.STATUS_V_OK_P_NO:store.setStatus(haveP?Store.STATUS_V_OK_P_OK:Store.STATUS_V_OK_P_NO);break;
		case Store.STATUS_V_OK_P_OFF:store.setStatus(haveP?(havePOff?Store.STATUS_V_OK_P_OFF:Store.STATUS_V_OK_P_OK):Store.STATUS_V_OK_P_NO);break;
		case Store.STATUS_V_OK_P_OK:store.setStatus(haveP?Store.STATUS_V_OK_P_OK:Store.STATUS_V_OK_P_OK);break;
		default:
			break;
		}
		Store dbStore = storeDao.getById(store.getId());
		if(dbStore.getStatus()!=store.getStatus()){
			logger.debug("\nstore[storeId="+store.getId()+"] status changed by link product from "+dbStore.getStatus()+" to "+store.getStatus());
			dbStore.setStatus(store.getStatus());
			storeDao.update(dbStore);
		}
	}
	@Override
	public void resetStatusByProducts(int[] productIds) {
		if(productIds!=null&&productIds.length>0){
			for (int pId : productIds) {
				List<StoreProduct> storeProductList = productDao.getStoreProductListByProductId(pId);
				if(storeProductList!=null&&!storeProductList.isEmpty()){
					for (StoreProduct storeProduct : storeProductList) {
						Store store = storeDao.getById(storeProduct.getStoreId());
						if(store!=null){
							resetStoreStatus(store);
						}
					}
				}
			}
		}
	}

	private void resetStoreStatus(Store store){
		if(store!=null){
			List<StoreProduct> storeProductList = productDao.getStoreProductList(store.getUserId(), store.getId());
			if(storeProductList==null||storeProductList.isEmpty()){
				//no link products
				store.setStatusProductNo();
			}else{
				//has offLine product
				boolean havPOff = false;
				StringBuffer productIds = new StringBuffer();
				for (int i = 0;i<storeProductList.size();i++) {
					productIds.append(storeProductList.get(i).getProductId());
					if(i<storeProductList.size()-1){
						productIds.append(",");
					}
				}
				PartnerProductPage ppp = partnerDataService.getProducts(productIds.toString());
				if(ppp!=null&&ppp.getTotal()>0){
					for (PartnerProduct partnerProduct : ppp.getRows()) {
						if(partnerProduct.getAuditState()!=StoreProduct.STATUS_销售中){
							havPOff = true;
							break;
						}
					}
				}
				if(havPOff){
					store.setStatusProductOff();
				}else{
					store.setStatusProductOk();
				}
			}
			storeDao.update(store);
		}
	}
}
