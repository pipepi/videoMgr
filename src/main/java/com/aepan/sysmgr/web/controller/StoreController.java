/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com._21cn.framework.exception.BusinessRuntimeException;
import com._21cn.framework.util.PageList;
import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.common.cache.ParamInfoCache;
import com.aepan.sysmgr.model.ProductType;
import com.aepan.sysmgr.model.Store;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.model.Video;
import com.aepan.sysmgr.model.config.FileConfig;
import com.aepan.sysmgr.model.hm.PartnerProductPage;
import com.aepan.sysmgr.model.hm.StoreSubInfo;
import com.aepan.sysmgr.model.log.OperationLog;
import com.aepan.sysmgr.model.lucene.ProductAttribute;
import com.aepan.sysmgr.model.lucene.SearchParams;
import com.aepan.sysmgr.model.lucene.StoreArray;
import com.aepan.sysmgr.model.lucene.StoreSub;
import com.aepan.sysmgr.model.packageinfo.PackageInfo;
import com.aepan.sysmgr.model.packageinfo.PackageStat;
import com.aepan.sysmgr.service.PartnerDataService;
import com.aepan.sysmgr.service.SearchService;
import com.aepan.sysmgr.service.UserService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.DataTableReturnObject;
import com.aepan.sysmgr.util.JSONUtil;
import com.aepan.sysmgr.util.lucene.SearchHelper;
import com.alibaba.fastjson.JSONObject;

/**
 * 商铺controller
 * @author rakika
 * 2015年8月10日下午4:38:16
 */
@Controller
public class StoreController extends DataTableController {

	private static final Logger logger = LoggerFactory.getLogger(StoreController.class);
	@Autowired
	private SearchService searchService;
	@Autowired
	private PartnerDataService partnerDataService;
	@Autowired
	private UserService userService;
	/**
	 * 获取店铺list
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/store/list")
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		//判断当前session没有过期
		if(request.getSession().getAttribute(Constants.SESSION_USER) == null){
			logger.info("check : session null");
			return "redirect:/login";
		}
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER);
		model.addAttribute("userId", user.getId());
		List<Store> list = storeService.getList(model, 1, 50);
		model.put("list", list);
		return "/store/list";
	}
	
	/**
	 * 搜索店铺
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/store/search")
	public String search(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		String [] idKey = new String[]{"", "id", "name", "description", "create_time"};
    	logger.info("method: role search");
		setPageSortModel(request, model, idKey);
		//根据ID拥有者，查询自己的店铺
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER);
		model.addAttribute("userId", user.getId());
    	PageList<Store> list = storeService.getList(model, Integer.parseInt(model.get("pageNo").toString()), 
    			                             Integer.parseInt(model.get("pageSize").toString()));
    	DataTableReturnObject data = new DataTableReturnObject();
    	data.setResult(list, model.get("sEcho").toString());
    	AjaxResponseUtil.returnData(response, JSONObject.toJSONString(data));
		return null;
	}
	
	/**
	 * 创建店铺
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/store/create")
	public String create(HttpServletRequest request, HttpServletResponse response, ModelMap model){
//		PageList<Store> list = storeService.getList(model, 1, Integer.MAX_VALUE);
//    	model.put("list", list);
    	Store store = new Store();
    	model.addAttribute("sysmgrStore", store);
		return "/store/form";
	}
	
	/**
	 * 保存店铺信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/store/save")
	public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		return "redirect:/store/list.do";
	}
	
	/**
	 * 店铺上线
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/store/online")
	public String online(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("eqId", -1);
        logger.info("id" + id);
        if(id != -1){
        	Store store = storeService.getById(id);
        	if(store == null){
        		 throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
        	}else{
        		if(store.getStatus() == 0){
        			storeService.updateStatus(store.getId(), 1);
        		}
        	}
        }
		return "redirect:/store/list.do";
	}
	
	/**
	 * 店铺下线
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/store/offline")
	public String offline(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("eqId", -1);
        if(id > 0){
        	Store store = storeService.getById(id);
        	if(store == null){
        		 throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
        	}else{
        		if(store.getStatus() == 1){
        			storeService.updateStatus(store.getId(), 0);
        		}
        	}
        }
		return "redirect:/store/list.do";
	}

	
	/**
	 * 添加播放器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/store/addbfq")
	public String addbfq(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		if(!checkPackageStat(request)){
			model.addAttribute("error_desc", "package is outdate or flow not enough.");
			return "error";
		}
		List<ProductType> productTypeList = productTypeService.getTypeList(model, 1, Integer.MAX_VALUE);
		model.addAttribute("ProductType", productTypeList);
		return "store/addbfq";
	}
	@RequestMapping("/store/editStore")
	public String editStore(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		int storeId  = reqInfo.getIntParameter("storeId");
		if(storeId>0){
			Store s = storeService.getById(storeId);
			if(s!=null){
				s.escapeHtml();
				model.addAttribute("store", s);
				List<Integer> storeTypes = new ArrayList<Integer>();
				String type = s.getType();
				if(type!=null&&type.length()>0){
					String[] types = type.split(",");
					for (String t : types) {
						storeTypes.add(new Integer(t));
					}
				}
				model.addAttribute("types",storeTypes);
			}
		}
		List<ProductType> productTypeList = productTypeService.getTypeList(model, 1, Integer.MAX_VALUE);
		model.addAttribute("ProductType", productTypeList);
		return "store/addbfq";
	}

	/**
	 * 播放器列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/store/videolist")
	public String videolist(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        logger.info("videoSessionId:"+session.getId()+",videolistuser:"+user);

		if(!isLogin(request)){
			return "redirect:/login";
		}
		
		/*if(!hasSellerAuth(request, Constants.PRIVILEGECODE_SELLERPLAYER)){
			model.put("error_desc", "Do not have privilege.");
			return "error";
		}*/
		int userId = user.getId();
		//查询包含关联商品、视频信息的播放器列表
		List<Store> slist = storeService.getListHasLinkedIds(userId);
		model.addAttribute("storelist", slist);
		int storeVideoCount=0;
		int storeProductCount=0;
		for (Store store : slist) {
			storeVideoCount=storeVideoCount+store.getLinkedVideoNum();
			storeProductCount= storeProductCount+store.getLinkedProductNum();
		}
		
		
		FileConfig config = ConfigManager.getInstance().getFileConfig(configService);
		model.addAttribute("imgpre", config.IMG_AZURE_PRE);
		//购买新套餐后，更新session中的套餐
		user = userService.getByUserId(userId);
		session.setAttribute(Constants.SESSION_USER, user);
		
		int packageId = user.getPackageId();
		PackageInfo packageInfo = packageService.getById(packageId);
		PackageStat packageStatInfo = packageStatService.getByUserId(userId);
		
		
		int totalVideoNum = packageInfo.getPlayerNum()*packageInfo.getVideoNum();
		int totalProductNum = packageInfo.getPlayerNum()*packageInfo.getProductNum();

//		int storeVideoCount = videoService.getStoreVideoCountByUserId(userId);
		
//		float totalFlowNum = packageStatService.getFlowNumByUserId(userId);
		
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 Date endTime = packageStatInfo.getEndTime();
		 String packageEndTimeFormat = sdf.format(endTime);  
		
		int playerCount = slist.size();
		int packagePlayerNum = packageInfo.getPlayerNum();
		int canAddPlayerNum = packagePlayerNum-playerCount;
		model.addAttribute("canAddPlayerNum",canAddPlayerNum);
		model.addAttribute("packagePlayerNum",packagePlayerNum);
		model.addAttribute("playerCount",playerCount);
		model.addAttribute("packageInfo",packageInfo);
		model.addAttribute("packageStatInfo", packageStatInfo);
		model.addAttribute("storeVideoCount", storeVideoCount);
		model.addAttribute("totalVideoNum", totalVideoNum);
		model.addAttribute("storeProductCount", storeProductCount);
		model.addAttribute("totalProductNum", totalProductNum);
		
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setGroupingUsed(false);
		nf.setMaximumFractionDigits(3);
		float  totalFlowNum=packageStatInfo.getFlowNum()/1024;
		model.addAttribute("totalFlowNum", nf.format(totalFlowNum));
		float usedFlowNum=packageStatInfo.getUsedFlowNum()/1024;
		float remainFlowNum = totalFlowNum - usedFlowNum<0?0:totalFlowNum - usedFlowNum;
		model.addAttribute("remainFlowNum", nf.format(remainFlowNum));
		Date now = new Date();
		boolean isOutDate=now.after(endTime);
		model.addAttribute("isOutDate",isOutDate);
		double flowUseRate=0;
		if(totalFlowNum!=0){
			flowUseRate = usedFlowNum/totalFlowNum;
		}
		double flowUseRateInt=flowUseRate*100;
		if(flowUseRateInt>100){
			flowUseRateInt=100;
		}
		model.addAttribute("flowUseRate",100-Float.parseFloat(nf.format(flowUseRateInt)));
		model.addAttribute("packageEndTime",packageEndTimeFormat);
		
		String memberId = (String) session.getAttribute(Constants.SESSION_MEMBERID);
		//用于播放器预览、传人参数
		model.addAttribute("memberId", memberId);
		setVersion(model);
		setProductsNum(user.getPartnerAccountId(),model);
		setVideoNum(user.getId(),model);
		return "store/bfqlist";
	}
	private void setProductsNum(int userId,ModelMap model){
		PartnerProductPage hp = partnerDataService.getProducts4Link(userId,
				"", "", 1, 1, "time", "desc");
		model.addAttribute("productNum", hp==null?0:hp.getTotal());
	}
	private void setVideoNum(int userId,ModelMap model){
		PageList<Video> list = videoService.videos4Link(0, userId, "updatetime", "desc", 1, 1);
		model.addAttribute("videoNum",list==null?0:list.getPageTurn().getRowCount() );
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/store/videoOffLine")
	public String videoOffLine(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("eqId", -1);
        if(id > 0){
        	Store store = storeService.getById(id);
        	if(store == null){
        		 throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
        	}else{
        		if(store.getStatus() == 1){
        			storeService.updateStatus(store.getId(), 0);
        		}
        	}
        }
		return "redirect:/store/videolist.do";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/store/videoOnLine")
	public String videoOnLine(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("eqId", -1);
        logger.info("id" + id);
        if(id != -1){
        	Store store = storeService.getById(id);
        	if(store == null){
        		 throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
        	}else{
        		if(store.getStatus() == 0){
        			storeService.updateStatus(store.getId(), 1);
        		}
        	}
        }
		return "redirect:/store/videolist.do";
	}
	
	/**
	 * 保存播放器
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/store/savebfq")
	@Transactional(rollbackFor=Exception.class)
	public String savebfq(HttpServletRequest request, HttpServletResponse response, ModelMap model){
	
		if(!isLogin(request)){
			model.addAttribute("success", false);
			AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
			return null;
		}
		if(!hasSellerAuth(request, Constants.PRIVILEGECODE_SELLERPLAYER)){
			model.put("error_desc", "Do not have privilege.");
			return "error";
		}
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER);
		int packageId = user.getPackageId();
		int userId=user.getId();
		
		PackageInfo packageInfo = packageService.getById(packageId);
		
		ReentrantLock lock = ParamInfoCache.controllerLockMap.get(ParamInfoCache.LOCK_SAVE_PLAYER);
		lock.lock();
		try {
			
			List<Store> storeList = storeService.getListByUserId(userId);
			
			if(packageInfo.getPlayerNum()<=storeList.size()){
				model.addAttribute("success", false);
				model.addAttribute("message", "player num arrive max value.");
				AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
				return null;
			}
			
			HttpRequestInfo reqInfo = new HttpRequestInfo(request);
			Store store = new Store();
			store.setName(reqInfo.getParameter("name"));
			store.setDescription(reqInfo.getParameter("description"));
			store.setShareContent(reqInfo.getParameter("shareContent"));
			store.setInnerCode(reqInfo.getParameter("innerCode"));
			String type = reqInfo.getParameter("type");
			store.setType(type);
			store.setPrivateDns(reqInfo.getParameter("privateDns"));
			store.setComAddress(reqInfo.getParameter("comAddress"));
			store.setComTele(reqInfo.getParameter("comTele"));
			store.setUserId(user.getId());
			storeService.save(store);
			
			partnerDataService.addLog(new OperationLog(OperationLog.TYPE_播放器, 
							user.getPartnerAccountId(),
							user.getPartnerAccountName(),
							"/store/savebfq", 
							"添加播放器", 
							request.getRemoteAddr()));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			lock.unlock();
		}
		model.addAttribute("success", true);
		packageStatService.countStoreNum(user.getId());
		AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
		
		//记录操作日志
	
		return null;
	}
	
	@RequestMapping("/store/updatebfq")
	public String updateBfq(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		Store store = new Store();
		store.setId(Integer.parseInt(reqInfo.getParameter("id")));
		store = storeService.getById(store.getId());
		if(store==null){
			model.clear();
			model.addAttribute("success", false);
			AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
			return null;
		}
		store.setName(reqInfo.getParameter("name"));
		store.setDescription(reqInfo.getParameter("description"));
		store.setShareContent(reqInfo.getParameter("shareContent"));
		store.setInnerCode(reqInfo.getParameter("innerCode"));
		String type = reqInfo.getParameter("type");
		store.setType(type);
		store.setPrivateDns(reqInfo.getParameter("privateDns"));
		store.setComAddress(reqInfo.getParameter("comAddress"));
		store.setComTele(reqInfo.getParameter("comTele"));
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER);
		if(user != null){
			storeService.update(configService,store);
			model.addAttribute("success", true);
		}else{
			model.addAttribute("success", false);
		}
		AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
		
		partnerDataService.addLog(new OperationLog(OperationLog.TYPE_播放器, 
						user.getPartnerAccountId(),
						user.getPartnerAccountName(),
						"/store/updatebfq", 
						"修改播放器:"+store, 
						request.getRemoteAddr()));
		return null;
	}
	
	
	@RequestMapping("/store/deletestore")
	@Transactional(rollbackFor=Exception.class)
	public String delete(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		if(!isLogin(request)){
			return "redirect:/login";
		}
		if(!hasSellerAuth(request, Constants.PRIVILEGECODE_SELLERPLAYER)){
			model.put("error_desc", "Do not have privilege.");
			return "error";
		}
		User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		Integer storeId = reqInfo.getIntParameter("storeId", -1);
		if(storeId>0){
			storeService.delete(configService,storeId, user.getId());
		}
		packageStatService.countStoreNum(user.getId());
		partnerDataService.addLog(new OperationLog(OperationLog.TYPE_播放器, 
						user.getPartnerAccountId(),
						user.getPartnerAccountName(),
						"/store/updatebfq", 
						"删除播放器:"+storeId, 
						request.getRemoteAddr()));
		return "redirect:/store/videolist";
	}
	private void setQK(StringBuffer query ,String k){
		query.append("name:").append("*").append(k).append("*").append(" OR ")
	     .append("desc:").append("*").append(k).append("*").append(" OR ")
	     .append("v_name:").append("*").append(k).append("*").append(" OR ")
	     .append("v_desc:").append("*").append(k).append("*").append(" OR ")
	     .append("p_name:").append("*").append(k).append("*").append(" OR ")
	     .append("p_desc:").append("*").append(k).append("*");
	}
	private void setQ(StringBuffer query ,String k,String storeType){
		if(k==null&&storeType==null){
			query.append("*:*");
		}else if(k==null&&storeType!=null){
			query.append("type:").append(storeType);
		}else if(k!=null&&storeType==null){
			setQK(query,k);
		}else if(k!=null&&storeType!=null){
			query.append("(");
			setQK(query,k);
			query.append(") ");
		    query.append(" AND ").append("type:").append(storeType);
		}
	}
	private static void setFQ(StringBuffer fq,String productAttrs, String priceStr){
		if(productAttrs!=null&&!productAttrs.trim().isEmpty()){
			String[] attrs = productAttrs.split("@");
			if(attrs.length>0){
				for (int i=0;i<attrs.length;i++) {
					String[] aItems = attrs[i].split("_");
					if(aItems!=null&&aItems.length>=2){
						String attrId = aItems[0];
						fq.append("(");
						for(int j=1;j<aItems.length;j++){
							String attrValue = aItems[j];
							fq.append("(");
							fq.append("p_attrname:").append(attrId)
							.append(" AND ")
							.append("p_attrvalue:").append(attrValue);
							fq.append(")");
							if(j<aItems.length-1){
								fq.append(" OR ");
							}
						}
						fq.append(")");
						if(i<attrs.length-1){
							fq.append(" AND ");
						}
					}
					
				}
				
			}
		}
		if(priceStr!=null&&priceStr.trim().length()>0){
			String[] priceArry = priceStr.split("_");
			if(priceArry!=null&&priceArry.length>=2){
				float priceMin = Float.parseFloat(priceArry[0]);
				float priceMax = Float.parseFloat(priceArry[1]);
				if(fq.length()>0){
					fq.append(" AND ");
				}
				fq.append("(")
				.append("p_pricemin:").append("[").append(priceMin).append(" TO ").append(priceMax).append("]")
				.append(" OR ")
				.append("p_pricemax:").append("[").append(priceMin).append(" TO ").append(priceMax).append("]")
				.append(")");
				
			}
		}
	}
	public static void main(String[] args) {
		StringBuffer fq = new StringBuffer();
		String productAttrs = "1_2_3@2_4_6";
		setFQ(fq, productAttrs,"2_3");
		System.out.println(fq.toString());
		
	}
	private String SetToString(ArrayList<String> set){
		if(set!=null&&!set.isEmpty()){
			String rs = "";
			for (String s : set) {
				rs += s +",";
			}
			rs = rs.substring(0,rs.length()-1);
			return rs;
		}
		return "";
	}
	private List<StoreSub> toStoreSub(List<StoreArray> lstoreList){
		List<StoreSub> list = new ArrayList<StoreSub>();
		if(lstoreList!=null&&!lstoreList.isEmpty()){
			for (StoreArray s : lstoreList) {
				StoreSub sub = new StoreSub(
						Integer.parseInt(s.getId()), 
						s.getName(), 
						s.getDesc(), 
						s.getV_img(),
						s.getV_img_max(),
						SetToString(s.getType()),
						s.getP_ids(),
						s.getP_pricemax(),
						s.getP_pricemin(),
						s.getV_hot(),
						s.getUpdate_time(),
						s.getPartnerUserId());
				list.add(sub);
			}
			
		}
		return list;
	}
	@RequestMapping("/store/searchbylucene")
	public String searchBySolr(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String k = reqInfo.getParameter("searchWord");//搜索框输入词   
		String storeType = reqInfo.getParameter("typeId");//播放器类型
		String productAttrs = reqInfo.getParameter("attrId");//41_139_126@40_132
		String priceStr = reqInfo.getParameter("expKeywords");//价格区间 
		String sortStr = reqInfo.getParameter("orderType");//排序字段  例如1_1代表人气升序，1_2人气降序,2_1价格升序,2_2价格降序。
		String pageNo = reqInfo.getParameter("pageNo");//页面
		String pageSize = reqInfo.getParameter("pageSize");//每页记录数
		logger.info("searchWord="+k+
				"   typeId="+storeType+
				"   attrId="+productAttrs+
				"   expKeywords="+priceStr+
				"   orderType="+sortStr+
				"   pageNo="+pageNo+
				"   pageSize="+pageSize
				);
		String sortBy = "v_hot";//排序字段   p_pricemax  v_hot
		String sortType = "desc";//排序方式  desc  asc
		int pn = pageNo==null?1:Integer.parseInt(pageNo);
		int ps = pageSize==null?10:Integer.parseInt(pageSize);
		if(sortStr!=null&&sortStr.trim().length()>0){
			String[] sortArry = sortStr.split("_");
			if(sortArry!=null&&sortArry.length>=2){
				int sby = Integer.parseInt(sortArry[0]);
				sortBy = sby==1?"v_hot":(sby==2?"p_pricemax":"update_time");
				sortType = Integer.parseInt(sortArry[1])==1?"asc":"desc";
			}
		}
		StringBuffer query = new StringBuffer();
		setQ(query, k, storeType);
		StringBuffer fq = new StringBuffer();
		setFQ(fq, productAttrs,priceStr);
		
		
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(query.toString());
		solrQuery.setFilterQueries(fq.toString());
		solrQuery.setSort(sortBy, sortType.equals("desc")?ORDER.desc:ORDER.asc);
		solrQuery.setStart((pn-1)*ps);
		solrQuery.setRows(ps);
		try {
			List<StoreArray> lstoreList = searchService.select(solrQuery);
			List<StoreSub> list = toStoreSub(lstoreList);
			String json = JSONUtil.toJson(list);
			logger.debug("search by solr result json:\n\n"+json);
			AjaxResponseUtil.returnData(response, json);
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	//@RequestMapping("/store/searchbylucene")
	public String searchMulti(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String k = reqInfo.getParameter("searchWord");//搜索框输入词   
		String storeType = reqInfo.getParameter("typeId");//播放器类型
		String productAttrs = reqInfo.getParameter("attrId");//41_139_126@40_132
		String priceStr = reqInfo.getParameter("expKeywords");//价格区间 
		String sortStr = reqInfo.getParameter("orderType");//排序字段  例如1_1代表人气升序，1_2人气降序,2_1价格升序,2_2价格降序。
		String pageNo = reqInfo.getParameter("pageNo");//页面
		String pageSize = reqInfo.getParameter("pageSize");//每页记录数
		logger.info("searchWord="+k+
				"   typeId="+storeType+
				"   attrId="+productAttrs+
				"   expKeywords="+priceStr+
				"   orderType="+sortStr+
				"   pageNo="+pageNo+
				"   pageSize="+pageSize
				);
		String priceMax = "";//价格区间 
		String priceMin = "";//价格区间
		if(priceStr!=null&&priceStr.trim().length()>0){
			String[] priceArry = priceStr.split("_");
			if(priceArry!=null&&priceArry.length>=2){
				priceMin = priceArry[0];
				priceMax = priceArry[1];
			}
		}
		String sortBy = "v_hot";//排序字段   p_pricemax  v_hot
		String sortType = "desc";//排序方式  desc  asc
		if(sortStr!=null&&sortStr.trim().length()>0){
			String[] sortArry = sortStr.split("_");
			if(sortArry!=null&&sortArry.length>=2){
				sortBy = Integer.parseInt(sortArry[0])==1?"v_hot":"p_pricemax";
				sortType = Integer.parseInt(sortArry[1])==1?"asc":"desc";
			}
		}
		
		int pn = pageNo==null?1:Integer.parseInt(pageNo);
		int ps = pageSize==null?10:Integer.parseInt(pageSize);
		//List<StoreSub> list = Store.searchLucene(configService,k,"MultiField", pn, ps);
		SearchParams sparams = new SearchParams();
		if(k==null){
			if(storeType==null){
				sparams.key = "*";
				sparams.filed = "name";
				//AjaxResponseUtil.returnData(response, "[]");
				//return null;
			}else{
				sparams.key = storeType;
				sparams.filed = "type";
			}
		}else{
			sparams.key = k;
			sparams.filed = "name,desc,type,p_name,p_desc,p_type,v_name,v_desc";
			if(storeType!=null){
				sparams.storeType = Integer.parseInt(storeType);
			}
		}
		if(productAttrs!=null&&!productAttrs.trim().isEmpty()){
			String[] attrs = productAttrs.split("@");
			if(attrs.length>0){
				List<ProductAttribute> productAttrList = new ArrayList<ProductAttribute>();
				for (String attr : attrs) {
					ProductAttribute pattr = new ProductAttribute();
					String[] aItems = attr.split("_");
					if(aItems!=null&&aItems.length>=2){
						String attrId = aItems[0];
						pattr.id = Integer.parseInt(attrId);
						pattr.values = new ArrayList<Integer>();
						for(int i=1;i<aItems.length;i++){
							String attrValue = aItems[i];
							pattr.values.add(Integer.parseInt(attrValue));
						}
						productAttrList.add(pattr);
					}
					
				}
				sparams.productAttrs = productAttrList;
			}
		}
		if(priceMax==null||priceMax.trim().isEmpty()){
			if(priceMin!=null&&!priceMin.trim().isEmpty()){
				sparams.priceMax = Float.MAX_VALUE;
				sparams.priceMin = Float.parseFloat(priceMin);
			}
		}else{
			if(priceMin==null||priceMin.trim().isEmpty()){
				sparams.priceMax = Float.parseFloat(priceMax);
				sparams.priceMin = 0.0f;
			}else{
				sparams.priceMax = Float.parseFloat(priceMax);
				sparams.priceMin = Float.parseFloat(priceMin);
				if(sparams.priceMin>sparams.priceMax){
					sparams.priceMax = Float.parseFloat(priceMin);
					sparams.priceMin = Float.parseFloat(priceMax);
				}
			}
		}
		if(sortBy==null||sortBy.equals("v_hot")){
			sparams.sortBy = "v_hot";
		}else{
			sparams.sortBy = "p_pricemax";
		}
		if(sortType==null||sortType.equals("desc")){
			sparams.sortType = false;
		}else{
			sparams.sortType = true;
		}
		sparams.pn = pn;
		sparams.ps = ps;
		List<StoreSub> list = SearchHelper.search(configService, sparams);
		AjaxResponseUtil.returnData(response, JSONUtil.toJson(list));
		return null;
	}	
	@RequestMapping("/store/searchpage")
	public String searchByLucenePage(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		return "/store/search";
	}
	@RequestMapping("/store/other")
	public String queryOthersByStoreId(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		int storeId = reqInfo.getIntParameter("storeId", 0);
		if(storeId>0){
			List<StoreSubInfo> list = storeService.queryOthersByStoreId(storeId); 
			AjaxResponseUtil.returnData(response, JSONUtil.toJson(list));
		}else{
			AjaxResponseUtil.returnData(response, "[]");
		}
		return null;
	}
	@RequestMapping("/store/mosthot")
	public String getMostHotStoreId(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		int productId = reqInfo.getIntParameter("pid", 0);
		if(productId>0){
			AjaxResponseUtil.returnData(response, ""+storeService.getMostHotStoreId(productId));
		}else{
			AjaxResponseUtil.returnData(response, "0");
		}
		return null;
	}
}
