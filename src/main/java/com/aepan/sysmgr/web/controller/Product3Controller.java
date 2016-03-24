/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com._21cn.framework.util.PageList;
import com._21cn.framework.util.PageTurn;
import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.azure.AzureMediaSDKException;
import com.aepan.sysmgr.model.Store;
import com.aepan.sysmgr.model.StoreProduct;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.model.config.PartnerConfig;
import com.aepan.sysmgr.model.hm.PartnerProduct;
import com.aepan.sysmgr.model.hm.PartnerProductPage;
import com.aepan.sysmgr.model.hm.Product;
import com.aepan.sysmgr.model.packageinfo.PackageStat;
import com.aepan.sysmgr.model.tempinfo.LinkProductInfo;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.PackageStatService;
import com.aepan.sysmgr.service.PartnerDataService;
import com.aepan.sysmgr.service.ProductService;
import com.aepan.sysmgr.service.StoreService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.web.api.pay.MD5Util;
import com.alibaba.fastjson.JSONObject;

/**
 * 第三方系统产品数据
 * @author lanker
 * 2015年9月28日下午4:14:13
 */
@Controller 
public class Product3Controller extends DataTableController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	private ProductService productService;
	@Autowired
	private PackageStatService packageStatService;
	@Autowired
	ConfigService configService;
	@Autowired
	StoreService storeService;
	@Autowired
	private PartnerDataService partnerDataService;
	/**
	 * 播放器页面关联产品
	 */
	@RequestMapping("/product3/list4player")
	public String list4player(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws AzureMediaSDKException{
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String pageNo = reqInfo.getParameter("pageNo");
		String pageSize = reqInfo.getParameter("pageSize");
		String orderBy = reqInfo.getParameter("orderBy","time");
		String orderType = reqInfo.getParameter("orderType","desc");
		int pn = pageNo==null?1:Integer.parseInt(pageNo);
		int ps = pageSize==null?6:Integer.parseInt(pageSize);
		if(!isLogin(request)){
			//initConfig(userName, request);
			return "redirect:/login";
		}
		PartnerProductPage ppi = new PartnerProductPage();
		//从第三方系统获取商品信息 todo
		//String retStr = "{\"count\":7,\"products\":[{\"addedDate\":\"1443429246996\",\"id\":123,\"imagePath\":\"/Storage/Shop/210/Products/234/1.png\",\"marketPrice\":100000,\"productName\":\"商品发布了\",\"quantity\":10,\"shopId\":210},{\"addedDate\":\"1443429246996\",\"id\":124,\"imagePath\":\"/Storage/Shop/210/Products/234/1.png\",\"marketPrice\":12000,\"productName\":\"限时购的商品\",\"quantity\":10,\"shopId\":210},{\"addedDate\":\"1443429246996\",\"id\":125,\"imagePath\":\"/Storage/Shop/210/Products/234/1.png\",\"marketPrice\":12000,\"productName\":\"限时购的商品\",\"quantity\":10,\"shopId\":210},{\"addedDate\":\"1443429246996\",\"id\":126,\"imagePath\":\"/Storage/Shop/210/Products/234/1.png\",\"marketPrice\":12000,\"productName\":\"限时购的商品\",\"quantity\":10,\"shopId\":210},{\"addedDate\":\"1443429246996\",\"id\":127,\"imagePath\":\"/Storage/Shop/210/Products/234/1.png\",\"marketPrice\":12000,\"productName\":\"限时购的商品\",\"quantity\":10,\"shopId\":210},{\"addedDate\":\"1443429246996\",\"id\":128,\"imagePath\":\"/Storage/Shop/210/Products/234/1.png\",\"marketPrice\":12000,\"productName\":\"限时购的商品\",\"quantity\":10,\"shopId\":210}]}";
		//ppi = JSONUtil.fromJson(retStr, ProductPageInfo.class);
		ppi = getUserPageProductList(request,pn,ps,orderBy,orderType);
				
		List<Product> productList = new ArrayList<Product>();
		if(ppi!=null){
			productList = toP(ppi.getRows());
		}
		PageList<Product> list = toPageList(productList);
		
		PageTurn pageTurn = new PageTurn(pn, ps,ppi==null?0:ppi.getTotal());
		if(list==null){
			list = new PageList<Product>();
		}
		list.setPageTurn(pageTurn);
		model.clear();
		model.addAttribute("list", list);
		model.addAttribute("turn", list.getPageTurn());
		PartnerConfig config = ConfigManager.getInstance().getPartnerConfig(configService);
		model.addAttribute("imgpre", config.rootPath);
		model.addAttribute("orderby", orderBy.equals("time")?0:1);
		model.addAttribute("ordertype", orderType.equals("desc")?0:1);
		return "/store/productlistsub";
	}
	private List<Product> toP(List<PartnerProduct> list){
		List<Product> rs = new ArrayList<Product>();
		if(list!=null&&!list.isEmpty()){
			for (PartnerProduct pp : list) {
				Product p = new Product();
				p.setId(pp.getId());
				p.setProductName(pp.getName());
				p.setProductDesc(pp.getShotDescription());
				p.setImagePath(pp.getImage());
				p.setAddedDate(pp.getPublishTime());
				p.setMarketPrice(pp.getPrice());
				p.setQuantity(pp.getStock());
				rs.add(p);
			}
		}
		return rs;
		
	}
	private PartnerProductPage getUserPageProductList(HttpServletRequest request,int page,int pagesize,String orderBy,String orderType){
		
		HttpSession session = request.getSession();
		
		User user = (User)session.getAttribute(Constants.SESSION_USER);
		PartnerProductPage hp = partnerDataService.getProducts(user.getPartnerAccountId(), page, pagesize, orderBy, orderType);
		if(hp!=null){
			add2Session(request,hp);
        	return hp;
		}
		return null;
	} 
	/**
	 * 将第三方返回的产品数据存入session，用于关联播放器获取产品名称、描述、类型等搜索关键词
	 */
	private void add2Session(HttpServletRequest request,PartnerProductPage hp){
		if(hp!=null&&request!=null&&hp.getRows()!=null&&!hp.getRows().isEmpty()){
			Map<String,PartnerProduct> oldMap = (Map<String,PartnerProduct>) request.getSession().getAttribute("partnerProducts");
			if(oldMap==null){
				oldMap = new HashMap<String,PartnerProduct>();
			}
			for (PartnerProduct p : hp.getRows()) {
				oldMap.put(""+p.getId(), p);
			}
			if(!oldMap.isEmpty()){
				request.getSession().setAttribute("partnerProducts", oldMap);
			}
		}
	}
	private PartnerProduct getFromSession(HttpServletRequest request,int id){
		Map<String,PartnerProduct> oldMap = (Map<String,PartnerProduct>) request.getSession().getAttribute("partnerProducts");
		return oldMap==null?null:oldMap.get(""+id);
	}
	private PageList<Product> toPageList(List<Product> list){
		if(list!=null&&!list.isEmpty()){
			PageList<Product> rs = new PageList<Product>();
			for (Product t : list) {
				rs.add(t);
			}
			return rs;
		}else{
			return null;
		}
	}
	@RequestMapping("/product3/linkPlayer")
	@Transactional(rollbackFor=Exception.class)
	public String linkPlayer(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws AzureMediaSDKException{
		if(!isLogin(request)){
			//initConfig("", request);
			return "redirect:/login";
		}
		if(!hasSellerAuth(request, Constants.PRIVILEGECODE_SELLERPLAYER)){
			model.put("error_desc", "Do not have privilege.");
			model.addAttribute("success", false);
			AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
        	return null;
		}
		
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer storeId = reqInfo.getIntParameter("storeId", -1);
        if(storeId > 0 ){
        	User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
        	String productIds = reqInfo.getParameter("productIds", "");
        	LinkProductInfo lpi  = new LinkProductInfo();
        	if(productIds.trim().length()>0){
        		String[] pidArr = productIds.split(",");
        		List<StoreProduct> batchList = new ArrayList<StoreProduct>();
        		for (String pid : pidArr) {
        			StoreProduct sv = new StoreProduct(user.getId(),storeId,Integer.parseInt(pid));
        			//设置播放器关联产品的名称、描述、类型，用于搜索播放器
        			PartnerProduct p = getFromSession(request, Integer.parseInt(pid));
        			if(p!=null){
        				sv.productName = p.getName();
        				sv.productDesc = p.getShotDescription();
        				sv.productPrice = p.getPrice();
        				sv.productAttrs = p.toProductAttr();
        				//sv.productType = p.get
        			}
					batchList.add(sv);
				}
        		lpi = productService.canLink(user.getId(),user.getPackageId(), storeId, batchList);
        		logger.debug(lpi.toString());
        		if(lpi.can){
        			productService.deleteByUserIdANDStoreId(user.getId(), storeId);
        			productService.batchInsert(batchList.size(), batchList, user.getId());
        			//更新套餐,已使用视频数量
        			PackageStat pstate = packageStatService.getByUserId(user.getId());
        			pstate.setProductNum(lpi.linkedProductNum);
        			packageStatService.updateUsedNum(pstate);
        			
        			Store store = storeService.getById(storeId);
        			if(store!=null){
        				//更新播放器状态
        				if(store.getStatus()==Store.STATUS_离线){
        					if(storeService.getLinkedVideoNum(user.getId(), storeId)>0){
        						store.setStatus(Store.STATUS_在线);
        					}
        				}
        				storeService.update(configService, store);
        			}
        			
        		}else{
        			model.addAttribute("success", false);
        			
        			model.addAttribute("ol", lpi.onceStoreProductNum);
        			model.addAttribute("oc", lpi.onceStoreCanLinkProductNum);
        			
        			model.addAttribute("c", lpi.canlinkProductNum);
        			model.addAttribute("l", lpi.linkedProductNum);
                	AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
                	return null;
        		}
        		 
        	}else if(productIds.trim().length()==0){
        		//获取取消关联的商品id列表
        		lpi = productService.canLink(user.getId(),user.getPackageId(), storeId, new ArrayList<StoreProduct>());
        		productService.deleteByUserIdANDStoreId(user.getId(), storeId);
        		//更新被关联播放器logo 状态
        		Store store = storeService.getById(storeId);
        		if(store!=null){
        			if(store.getStatus()==Store.STATUS_在线){
        				store.setStatus(Store.STATUS_离线);
        			}
        			storeService.update(configService, store);
        		}
        	}
        	model.addAttribute("success", true);
        	packageStatService.countLindedProductNum(user.getId());
        	partnerDataService.sendMsgOfProductsLinked(lpi.linkPids, lpi.unLinkPids);
        	AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
        	return null;
        }
        model.addAttribute("success", false);
        AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
		return null;
	}
	@RequestMapping("/product3/delete_link")
	public String deleteLinkByProductId(HttpServletRequest req,HttpServletResponse res,ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		String productId = reqInfo.getParameter("product_id","");
		String sendTime = reqInfo.getParameter("time","");
		String key = reqInfo.getParameter("key","");//key=md5(phone+time+azt123qet)
		if(!productId.trim().isEmpty()&&
				!sendTime.trim().isEmpty()&&
				!key.trim().isEmpty()){
				Calendar c = Calendar.getInstance(Locale.getDefault());
				c.setTimeInMillis(Long.parseLong(sendTime));
				if(key.equals(MD5Util.MD5Encode(productId+sendTime+"azt123qet","UTF-8"))){
					productService.deleteLinkRelationByProductId(Integer.parseInt(productId));
					String rs = "{\"code\":0}";
					AjaxResponseUtil.returnData(res, rs);
					return null;
				}
			}
			AjaxResponseUtil.returnData(res, "{\"code\":2,\"errormsg\":\"param error"
					+ " productId="+productId+""
					+ " time="+sendTime
					+ " key="+key
					+ "\"}");
		return null;
	}
}
