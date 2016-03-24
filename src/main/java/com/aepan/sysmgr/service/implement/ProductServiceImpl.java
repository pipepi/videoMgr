/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.aepan.sysmgr.web.controller.VideoController;

/**
 * @author rakika
 * 2015年7月14日下午3:52:18
 */
@Service
public class ProductServiceImpl implements ProductService {
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
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
		if(p.getProductNum()<batchList.size()){//限定每个播放器可关联商品数量
			lpi.onceStoreProductNum=batchList.size();
			lpi.onceStoreCanLinkProductNum=p.getProductNum();
			return lpi;
		}
		
		//记录用户完成本次关联后的已关联商品linkedProductNum     套餐中拥有的数量canlinkProductNum
		List<StoreProduct> linkedList  = productDao.getStoreProductListByUserId(userId);
		List<StoreProduct> exceptCurrList = new ArrayList<StoreProduct>();
		if(linkedList!=null&&!linkedList.isEmpty()){
			for (StoreProduct StoreProduct : linkedList) {
				//获得除去当前要关联播放器相关StoreProduct后的List<StoreProduct>
				if(StoreProduct.storeId != storeId){
					exceptCurrList.add(StoreProduct);
				}
			}
		}
		//完成本次关联后的List<StoreProduct>
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
			setLinkAndUnlinkProductIds(lpi,linkedList,exceptCurrList);
		}
		return lpi;
	}
	/**
	 * 设置 本次关联商品 要关联的商品id列表和要取消关联的商品id列表
	 * @param linkPInfo 结果封装
	 * @param linkedList 已关联商品
	 * @param newlist 要关联商品
	 */
	private static void setLinkAndUnlinkProductIds(LinkProductInfo linkPInfo,List<StoreProduct> linkedList,List<StoreProduct> newlist){
		logger.debug("\nlinkedList.size()="+linkedList.size()+"  newlist.size()="+newlist.size()+"\n");
		String linkPids = "";
		String unLinkPids = "";
		if(linkedList==null||linkedList.isEmpty()){
			if(newlist==null||newlist.isEmpty()){
				//nothing to od
			}else{
				linkPids = getIdsStr(newlist);
			}
		}else{
			if(newlist==null||newlist.isEmpty()){
				unLinkPids = getIdsStr(linkedList);
			}else{
				
				for(int i = 0 ;i<linkedList.size();i++){
					for(int j=0;j<newlist.size();j++){
						StoreProduct a = linkedList.get(i);
						StoreProduct b = newlist.get(j);
						if(!containsByProductId(newlist,a)&&!containsByStr(unLinkPids,a.getProductId())){
							unLinkPids += a.getProductId()+",";
						}
						if(!containsByProductId(linkedList,b)&&!containsByStr(linkPids,b.getProductId())){
								linkPids += b.getProductId()+",";
						}
					}
				}
				if(linkPids.length()>0){
					linkPids = linkPids.substring(0,linkPids.length()-1);
				}
				if(unLinkPids.length()>0){
					unLinkPids = unLinkPids.substring(0,unLinkPids.length()-1);
				}
			}
		}
		linkPInfo.unLinkPids = unLinkPids;
		linkPInfo.linkPids = linkPids;
		logger.debug("\nlinkPInfo.unLinkPids="+linkPInfo.unLinkPids+"  linkPInfo.linkPids="+linkPInfo.linkPids+"\n");
	}
	private static boolean containsByStr(String linkPids,int pId){
		if(linkPids!=null&&linkPids.length()>0){
			String[] pids = linkPids.split(",");
			for (String id : pids) {
				if(Integer.parseInt(id)==pId){
					return true;
				}
			}
		}
		return false;
	}
	private static boolean containsByProductId(List<StoreProduct> list,StoreProduct sp){
		for (StoreProduct storeProduct : list) {
			if(storeProduct.productId==sp.productId){
				return true;
			}
		}
		return false;
	}
	public static void main(String[] args) {
		List<StoreProduct> linkedList = new ArrayList<StoreProduct>();
		List<StoreProduct> batchList = new ArrayList<StoreProduct>();
		StoreProduct sp = new StoreProduct(1, 1, 1);
		StoreProduct sp1 = new StoreProduct(1, 1, 11);
		StoreProduct sp2 = new StoreProduct(1, 1, 12);
		StoreProduct sp3 = new StoreProduct(1, 1, 13);
		linkedList.add(sp);
		linkedList.add(sp1);
		batchList.add(sp);
		batchList.add(sp2);
		batchList.add(sp3);
		LinkProductInfo linkPInfo = new LinkProductInfo();
		
		setLinkAndUnlinkProductIds(linkPInfo, linkedList, batchList);
		System.out.println(linkPInfo.linkPids+" ---"+linkPInfo.unLinkPids);
		System.out.println(containsByStr("", 13));
	}
	private static String getIdsStr(List<StoreProduct> batchList){
		StringBuffer sb = new StringBuffer();
		for (StoreProduct sp : batchList) {
			sb.append(sp.getProductId()).append(",");
		}
		String rs = sb.toString();
		return rs.substring(0,rs.length()-1);
	}
	
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
