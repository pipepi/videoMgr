/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.model.PartnerMgrUser;
import com.aepan.sysmgr.model.PartnerUser;
import com.aepan.sysmgr.model.packageinfo.PackageInfo;
import com.aepan.sysmgr.service.PackageService;
import com.aepan.sysmgr.service.PartnerUserService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.DataTableReturnObject;
import com.alibaba.fastjson.JSONObject;


/**
 * 视频电商管理员Controller
 * @author rakika
 * 2015年9月4日下午5:39:30
 */
@Controller
public class ManageController extends DataTableController {

	private static final Logger logger = LoggerFactory.getLogger(ManageController.class);
	
	@Autowired
	private PartnerUserService partnerUserService;
	
	
	@Autowired
	private PackageService packageService;
	
	
	/**
	 * 卖家list
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/manage/sellerlist")
    public String sellerlist(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		List<PartnerMgrUser> rlist = new ArrayList<PartnerMgrUser>();
		PageList<PartnerUser> list = partnerUserService.getList(model, 1, Integer.MAX_VALUE);
		List<PackageInfo> packageList = packageService.getList(PackageInfo.PACKAGE_TYPE_PACKAGE, 1, 4);
		for(int i = 0; i < list.size(); i++ ){
			PartnerMgrUser pmu = new PartnerMgrUser();
			PartnerUser pu = list.get(i);
			pmu.setId(pu.getId());
			pmu.setSellerName(pu.getSellerName());
			pmu.setPartnerName(pu.getPartnerName());
			pmu.setPlayerNum(pu.getPlayerNum() + "/" + pu.getTotalPlayerNum());
			pmu.setProductNum(pu.getProductNum() + "/" + pu.getTotalProductNum());
			pmu.setVideoNum(pu.getVideoNum() + "/" + pu.getTotalVideoNum());
			pmu.setDuration(pu.getDuration());
			pmu.setCreateTime(pu.getEndTime());
			pmu.setPackageName(pu.getPackageName());
			
			rlist.add(pmu);
		}

		model.addAttribute("splist", rlist);
		model.addAttribute("packageList",packageList);
        return "/manage/list";
    }
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/manage/search")
    public String search(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String [] idKey = new String[]{"", "name"};
    	logger.info("method: role search");
		setPageSortModel(request, model, idKey);
		//根据ID拥有者，查询自己的店铺
    	PageList<PartnerUser> list = partnerUserService.getList(model, Integer.parseInt(model.get("pageNo").toString()), 
    			                             Integer.parseInt(model.get("pageSize").toString()));
    	DataTableReturnObject data = new DataTableReturnObject();
    	data.setResult(list, model.get("sEcho").toString());
    	AjaxResponseUtil.returnData(response, JSONObject.toJSONString(data));
    	return null;
    }
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/manage/testRest")
	public String testRest(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		return "/manage/test";
	}
	
}
