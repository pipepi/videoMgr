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
import com.aepan.sysmgr.util.Constants;


/**
 * 视频电商管理员Controller
 * @author rakika
 * 2015年9月4日下午5:39:30
 */
@Controller
public class PlayerSellerController extends DataTableController {

	private static final Logger logger = LoggerFactory.getLogger(PlayerSellerController.class);
	
	@Autowired
	private PartnerUserService partnerUserService;
	
	
	@Autowired
	private PackageService packageService;
	
	
	
	@RequestMapping("/playerseller/sellerspage")
	public String searchSellersPage(HttpServletRequest request,HttpServletResponse res,ModelMap model){
		if(!isAdminLogin(request)){
			return "redirect:/login";
		}
		
		if(!hasAdminAuth(request, Constants.PRIVILEGECODE_SELLER)){
			model.put("error_desc", "Do not have privilege.");
			return "error";
		}
		List<PackageInfo> packageList = packageService.getList(PackageInfo.PACKAGE_TYPE_PACKAGE, 1, 3);
		model.addAttribute("packageList",packageList);
		return "/manage/list";
	}
	
	@RequestMapping("/playerseller/search" )
	public String searchOrder(HttpServletRequest request,HttpServletResponse res,ModelMap model){
		if(!isAdminLogin(request)){
			return "redirect:/login";
		}
		
		if(!hasAdminAuth(request, Constants.PRIVILEGECODE_SELLER)){
			model.put("error_desc", "Do not have privilege.");
			return "error";
		}
	
		
		int pageNo = Integer.valueOf(request.getParameter("pageNo"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String packageId = request.getParameter("packageId");
		String sellerName = request.getParameter("sellerName");
		model.put("packageId", packageId);
		model.put("sellerName", sellerName);
		
		logger.debug(model.toString());
		
		List<PartnerMgrUser> rlist = new ArrayList<PartnerMgrUser>();
		PageList<PartnerUser> list = partnerUserService.getList(model, pageNo, pageSize);
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

		model.clear();
		model.put("list", rlist);
		model.put("turn", list.getPageTurn());
		
		
		return "/manage/listsub";
	}
	
}
