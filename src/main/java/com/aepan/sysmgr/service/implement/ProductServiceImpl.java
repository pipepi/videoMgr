/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.dao.PackageDao;
import com.aepan.sysmgr.dao.ProductDao;
import com.aepan.sysmgr.model.ProductInfo;
import com.aepan.sysmgr.model.StoreProduct;
import com.aepan.sysmgr.model.packageinfo.PackageInfo;
import com.aepan.sysmgr.model.tempinfo.LinkProductInfo;
import com.aepan.sysmgr.service.ProductService;

/**
 * @author rakika
 * 2015年7月14日下午3:52:18
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private PackageDao packageDao;
	
	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ProductService#getList(java.util.Map, int, int)
	 */
	@Override
	public PageList<ProductInfo> getList(Map<String, Object> params,
			int pageNo, int pageSize) {
		return productDao.getList(params, pageNo, pageSize);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ProductService#create(com._aepan.sysmgr.model.ProductInfo)
	 */
	@Override
	public boolean create(ProductInfo productInfo) {
		return productDao.create(productInfo);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ProductService#update(com._aepan.sysmgr.model.ProductInfo)
	 */
	@Override
	public boolean update(ProductInfo productInfo) {
		return productDao.update(productInfo);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ProductService#getById(java.lang.Integer)
	 */
	@Override
	public ProductInfo getById(Integer id) {
		// TODO Auto-generated method stub
		return productDao.getById(id);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ProductService#save(com._aepan.sysmgr.model.ProductInfo)
	 */
	@Override
	public boolean save(ProductInfo productInfo) {
		// TODO Auto-generated method stub
		return productDao.save(productInfo);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ProductService#getByUserList(int)
	 */
	@Override
	public List<ProductInfo> getByUserList(int userId) {
		// TODO Auto-generated method stub
		return productDao.getByUserList(userId);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ProductService#getByUserIdAndStoreIdList(int, int)
	 */
	@Override
	public List<ProductInfo> getByUserIdAndStoreIdList(int userId, int StoreId) {
		// TODO Auto-generated method stub
		return productDao.getByUserIdAndStoreIdList(userId, StoreId);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ProductService#batchInsert(int, java.util.List, java.lang.Integer)
	 */
	@Override
	public void batchInsert(int batchSize, List<StoreProduct> batchList,
			Integer userId) {
		productDao.batchInsert(batchSize, batchList, userId);
		
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ProductService#deleteByUserIdANDStoreId(int, int)
	 */
	@Override
	public boolean deleteByUserIdANDStoreId(int userId, int storeId) {
		// TODO Auto-generated method stub
		return productDao.deleteByUserIdANDStoreId(userId, storeId);
	}
	/**校验在套餐中是否有足够的数量来关联当前选中的商品*/
	@Override
	public LinkProductInfo canLink(int userId,int packgeId,int storeId,List<StoreProduct> batchList){
		LinkProductInfo lpi = new LinkProductInfo();
		PackageInfo p = packageDao.getById(packgeId);
		if(p.getProductNum()<batchList.size()){
			lpi.onceStoreProductNum=batchList.size();
			lpi.onceStoreCanLinkProductNum=p.getProductNum();
			
			return lpi;
		}
		
		//记录用户完成本次关联后的已关联商品linkedProductNum     套餐中拥有的数量canlinkProductNum
		List<StoreProduct> linkedList  = productDao.getStoreProductListByUserId(userId);
		List<StoreProduct> exceptCurrList = new ArrayList<StoreProduct>();
		if(linkedList!=null&&!linkedList.isEmpty()){
			for (StoreProduct storeVideo : linkedList) {
				//获得除去当前要关联播放器相关StoreVideo后的List<StoreVideo>
				if(storeVideo.storeId != storeId){
					exceptCurrList.add(storeVideo);
				}
			}
		}
		//完成本次关联后的List<StoreVideo>
		exceptCurrList.addAll(batchList);

		int linkedProductNum = exceptCurrList.size();
		//PackageStat pstate = packageStatDao.getByUserId(userId);
		int canlinkProductNum = p==null?0:p.getProductNum()*p.getPlayerNum();
		lpi.linkedProductNum = linkedProductNum;
		lpi.canlinkProductNum = canlinkProductNum;
		if(linkedProductNum>canlinkProductNum){
			lpi.can = false;
		}else{
			lpi.can = true;
		}
		return lpi;
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.ProductService#getStoreProductIdList(int)
	 */
	@Override
	public List<Integer> getStoreProductIdList(int storeId) {
		return productDao.getStoreProductIdList(storeId);
	}
	
	
	@Override
	public int getStoreProductCountByUserId(int userId){
		return productDao.getStoreProductCountByUserId(userId);
	}
	@Override
	public void deleteLinkRelationByProductId(int productId){
		productDao.deleteLinkRelationByProductId(productId);
	}
}
