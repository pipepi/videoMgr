/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com._21cn.framework.exception.BusinessRuntimeException;
import com._21cn.framework.util.PageList;
import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.model.ProductInfo;
import com.aepan.sysmgr.model.ProductType;
import com.aepan.sysmgr.model.Store;
import com.aepan.sysmgr.model.StoreProduct;
import com.aepan.sysmgr.model.StoreProducts;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.model.config.PartnerConfig;
import com.aepan.sysmgr.model.hm.PartnerProductPage;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.ProductService;
import com.aepan.sysmgr.service.ProductTypeService;
import com.aepan.sysmgr.service.StoreService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.DataTableReturnObject;
import com.aepan.sysmgr.util.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

/**
 * 商品controller
 * @author rakika
 * 2015年7月14日下午3:49:05
 */
@Controller
public class ProductController extends DataTableController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductTypeService productTypeService;
	
	@Autowired
	StoreService storeService;
	
	@Autowired
	ConfigService configService;
	
	/**
	 * 获取list
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/product/list")
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		//查询所有的资源
    	PageList<ProductInfo> productInfo = productService.getList(model, 1, Integer.MAX_VALUE);
    	model.addAttribute("productInfo", productInfo);
    	logger.debug("prodcut/list");
    	return "/product/list";
	}
	
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/product/search")
    public String search(HttpServletRequest request, HttpServletResponse response, ModelMap model){
    	
		setPageSortModel(request, model, new String[]{"", "id", "name", "type"});
		User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
		model.put("userName", user.getUserName());
		logger.info("userName:" + user.getUserName());
    	PageList<ProductInfo> list = productService.getList(model, 
    			Integer.parseInt(model.get("pageNo").toString()), Integer.parseInt(model.get("pageSize").toString()));
    	logger.info("MgrPrivilege:" + list.size());
    	DataTableReturnObject data = new DataTableReturnObject();
    	data.setResult(list, model.get("sEcho").toString());
    	AjaxResponseUtil.returnData(response, JSONObject.toJSONString(data));
    	return null;
    }
	
	/**
	 * 查询商铺下的产品
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/product/getProductStoreId")
	public String getProductStoreId(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer storeId = reqInfo.getIntParameter("storeId", -1);
        logger.info("storeId:" + storeId);
		List<ProductInfo> slist = productService.getByUserIdAndStoreIdList(user.getId(), storeId);
		List<Integer> idList = new ArrayList<Integer>();
		for(int j = 0; j < slist.size(); j++){
			idList.add(slist.get(j).getId());
		}
		
		List<ProductInfo> ulist = productService.getByUserList(user.getId());
		logger.info("list size:" + ulist.size());        
        int selectNum = 0;

		StringBuffer sb = new StringBuffer(); 
		sb.append("<input type=\"hidden\" name=\"storeId\" value=\"");
		sb.append(storeId);
		sb.append("\" />");
		sb.append("<ul style=\"overflow:hidden;height:473px;overflow:auto;\">");
		for(int i = 0; i < ulist.size(); i++ ){
			ProductInfo productInfo = ulist.get(i);
			//判断是否换行
			if(( i + 1 ) % 3 == 0){
				sb.append("<li style=\"margin-right:0;\">");
			}else{
				sb.append("<li>");
			}
	    	sb.append("<img src=\"");
	    	//输出图片
	    	sb.append(productInfo.getPhotoUrl1());
	    	sb.append("\" ");
	    	sb.append("width=\"210px\" ");
	    	sb.append("height=\"126px\" />");
	    	
	    	sb.append("<span>");
	    	//商品名
	    	sb.append(productInfo.getName());
	    	sb.append("</span>");
	    	sb.append("<div><input value=\"");
	    	sb.append(productInfo.getId());
	    	sb.append("\" ");
	    	if(idList.indexOf(productInfo.getId()) != -1){
	    		sb.append(" checked ");
	    		selectNum++;
	    	}
	    	sb.append("name=\"checkbox\" type=\"checkbox\" class=\"checkbox\"/>");
	    	sb.append("添加商品到此播放器</div>");
	    	sb.append("</li>");
	    }
		sb.append("</ul>");
		sb.append("<div style=\"text-align:center;margin-top:20px;\">已选择添加<em>");
		sb.append(selectNum);
		sb.append("</em>个商品</div>");
		sb.append("<div style=\"width:216px; margin:0 auto;margin-top:20px; \">");
		sb.append("<input type=\"submit\" class=\"video-btnsInfor\" value=\"保存\" />");
		sb.append("<input type=\"button\" class=\"video-btnsInfors\" value=\"重选\" />");
		sb.append("<div class=\"clear\"></div>");
		sb.append("</div>");
		//分页
		sb.append("<div class=\"list-page\" style=\"margin-top:20px;\">");
		sb.append("<i>1/16页</i><a href=\"\" class=\"pre-page\">上一页</a>");
		sb.append("<a href=\"\" class=\"current\">1</a><a href=\"\">2</a>");
		sb.append("<a href=\"\" class=\"next-page\">下一页</a></div>");
              		  
		AjaxResponseUtil.returnData(response, sb.toString());
		return null;
	}
	
	/**
	 * 新建产品
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/product/create")
	public String create(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		PageList<ProductType> productTypeList = productTypeService.getTypeList(model, 1, Integer.MAX_VALUE);
		model.put("productTypeList", productTypeList);
		return "/product/form";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/product/show")
	public String show(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("eqId", -1);
        if(id > 0){
        	ProductInfo productInfo = productService.getById(id);
            if(productInfo == null){
                throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
            }
        	model.addAttribute("product", productInfo);
        	PageList<ProductType> productTypeList = 
        			       productTypeService.getTypeList(model, 1, Integer.MAX_VALUE);
    		model.put("productTypeList", productTypeList);
        }
		return "/product/form";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/product/save")
	public String save(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
    	ProductInfo productInfo = new ProductInfo();
    	productInfo.setName(reqInfo.getParameter("name"));
    	User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
    	productInfo.setUserId(user.getId());
    	productInfo.setPhotoUrl1(reqInfo.getParameter("photoUrl1"));
    	productInfo.setPhotoUrl2(reqInfo.getParameter("photoUrl2"));
    	productInfo.setPhotoUrl3(reqInfo.getParameter("photoUrl3"));
    	productInfo.setPhotoUrl4(reqInfo.getParameter("photoUrl4"));
    	productInfo.setPhotoUrl5(reqInfo.getParameter("photoUrl5"));
    	productInfo.setType(reqInfo.getIntParameter("type"));
    	productInfo.setPrice(reqInfo.getIntParameter("price"));
    	productInfo.setWeight(reqInfo.getIntParameter("weight"));
    	productInfo.setReserve(reqInfo.getIntParameter("reserve"));
    	productInfo.setStatus(reqInfo.getIntParameter("status"));
    	productService.save(productInfo);
		return "redirect:/product/list.do";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/product/update")
	public String update(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("eqId", -1);
        if(id > 0){
        	ProductInfo productInfo = productService.getById(id);
            if(productInfo == null){
                throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
            }
            productInfo.setName(reqInfo.getParameter("name"));
            if(reqInfo.getParameter("photoUrl1") != null){
              productInfo.setPhotoUrl1(reqInfo.getParameter("photoUrl1"));
            }
            if(reqInfo.getParameter("photoUrl2") != null){
           	  productInfo.setPhotoUrl2(reqInfo.getParameter("photoUrl2"));
            }
            if(reqInfo.getParameter("photoUrl3") != null){
           	  productInfo.setPhotoUrl3(reqInfo.getParameter("photoUrl3"));
            }
            if(reqInfo.getParameter("photoUrl4") != null){
           	  productInfo.setPhotoUrl4(reqInfo.getParameter("photoUrl4"));
            }
            if(reqInfo.getParameter("photoUrl5") != null){
           	  productInfo.setPhotoUrl5(reqInfo.getParameter("photoUrl5"));
            }
            productInfo.setPrice(reqInfo.getIntParameter("price"));
            productInfo.setReserve(reqInfo.getIntParameter("reserve"));
            productInfo.setStatus(reqInfo.getIntParameter("status"));
            productInfo.setType(reqInfo.getIntParameter("type"));
            productInfo.setExt(reqInfo.getParameter("ext"));
            productService.update(productInfo);
        }
		return "redirect:/product/list.do";
	}
	
	
	@RequestMapping("/product/getUserPageProductList")
	public String getUserPageProductList(HttpServletRequest request, HttpServletResponse response){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);

		if(isLogin(request)){
			return "redirect:/login";
		}
		
		int page = reqInfo.getIntAttribute("page",1);
		int pagesize = reqInfo.getIntAttribute("pagesize",6);
		
		
		PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(configService);
		String productPageUrl = partnerConfig.GET_PARTNER_PRODUCT_PAGE_URL;
		
		StringBuffer param= new StringBuffer("?saleStatus=1&auditStatus=2&page=").append(""+page).append("&rows="+pagesize);
		
		GetMethod method = new GetMethod(productPageUrl+param.toString());
		

		HttpClient client = new HttpClient();

		logger.info(method.toString());
		String ret = "";
		try {
			client.executeMethod(method);
			ret = method.getResponseBodyAsString();
			logger.debug("ret:"+ret);
			
	        if(method.getStatusCode() == 200 ){
	        	Gson gson = new Gson();
	        	PartnerProductPage hp = gson.fromJson(ret, PartnerProductPage.class);
	        	AjaxResponseUtil.returnData(response, JSONUtil.toJson(hp));
	        }
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/product/unionStore")
	public String unionStore(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer storeId = reqInfo.getIntParameter("eqId", -1);
        logger.info("storeId:" + storeId);
        if(storeId > 0){
        	Store store = storeService.getById(storeId);
            if(store == null){
                throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
            }
            model.addAttribute("store", store);
            User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
            List<ProductInfo> list = productService.getByUserList(user.getId());
            List<ProductInfo> plist = productService.getByUserIdAndStoreIdList(user.getId(), store.getId());
            List<Integer> productIds = new ArrayList<Integer>();
            for(int i = 0; i < plist.size(); i++){
            	productIds.add(plist.get(i).getId());
            }
            model.addAttribute("productIds", productIds);
            model.addAttribute("Products", list);
        }
		return "/product/selectform";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/product/psave")
	public String psave(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			@ModelAttribute(value="StoreProducts") StoreProducts storeProducts, BindingResult result){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer storeId = reqInfo.getIntParameter("storeId", -1);
        if(storeId > 0 ){
        	Store store = storeService.getById(storeId);
        	if(store == null){
       		   throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
       	    }
        	List<StoreProduct> batchList = new ArrayList<StoreProduct>();

        	List<ProductInfo> uList = storeProducts.getProductInfoList();
        	logger.info("ProductInfo:" + uList.size());
        	User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
        	for(ProductInfo obj : uList){
        		if(obj != null){
        			if(obj.getId() > 0 ){
        				StoreProduct storeProduct = new StoreProduct();
        				storeProduct.setProductId(obj.getId());
        				storeProduct.setStoreId(storeId);
        				storeProduct.setUserId(user.getId());
        				batchList.add(storeProduct);
        			}
        		}
        	}
        	productService.deleteByUserIdANDStoreId(user.getId(), storeId);
        	productService.batchInsert(20, batchList, user.getId());
        }
		return "redirect:/store/list.do";
	}
}
