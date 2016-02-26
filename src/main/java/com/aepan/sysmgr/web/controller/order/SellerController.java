/**
 * 买家  查询订单信息
 */
package com.aepan.sysmgr.web.controller.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com._21cn.framework.exception.BusinessRuntimeException;
import com._21cn.framework.util.PageList;
import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.model.ProductOrder;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.service.OrderService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.JSONUtil;
import com.aepan.sysmgr.web.controller.DataTableController;
import com.aepan.sysmgr.web.controller.VideoController;

/**
 * @author Lanker
 * 2015年10月8日上午11:46:36
 */
@Controller
public class SellerController extends DataTableController{
	private static final Logger logger = LoggerFactory.getLogger(VideoController.class);
	@Autowired
	OrderService orderService;
	@RequestMapping("/seller/orderpage")
	public String searchOrderPage(HttpServletRequest request,HttpServletResponse res,ModelMap model){
		if(!isLogin(request)){
			return "redirect:/login";
		}
		return "/order/seller/list";
	}
	@RequestMapping("/seller/ordersearch")
	public String searchOrder(HttpServletRequest request,HttpServletResponse res,ModelMap model){
		if(!isLogin(request)){
			return "redirect:/login";
		}
		User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String pageNo = reqInfo.getParameter("pn");
		String pageSize = reqInfo.getParameter("ps");
		String id = reqInfo.getParameter("id","");
		String buyersName = reqInfo.getParameter("bn","");
		String orderState = reqInfo.getParameter("os","");
		String toPhone = reqInfo.getParameter("to_mobile","");
		
		int pn = pageNo==null?1:Integer.parseInt(pageNo);
		int ps = pageSize==null?4:Integer.parseInt(pageSize);
			model.put("sellerId", user.getId());
			if(!toPhone.trim().isEmpty()){
				model.put("toMobile", toPhone);
			}
			if(!id.trim().isEmpty()){
				model.put("orderId", id);
			}
			if(!buyersName.trim().isEmpty()){
				model.put("buyer", buyersName);
			}
			if(!orderState.trim().isEmpty()){
				model.put("stype", orderState);
			}
			model.put("sortName", "create_time");
			model.put("sortOrder", "desc");
			PageList<ProductOrder> list = orderService.getMobileList(model,pn, ps);
			model.clear();
			model.put("list", list);
			model.put("turn", list.getPageTurn());
		return "/order/seller/listsub";
	}
	
	@RequestMapping("/seller/addlogistics")
	public String addlogistics(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("eqId", -1);
        if(id > 0){
        	ProductOrder productOrder = orderService.getById(id);
            if(productOrder == null){
                throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
            }
            model.addAttribute("productOrder", productOrder);
            String[] ds = new String[]{"未发货", "已发货", "已收货"};
            model.addAttribute("deliverStatus", ds);
        }
		return "/order/seller/logistics";
	}
	
	
	
	@RequestMapping("/seller/logistics")
	public String savelog(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("id", -1);
        if(id > 0){
        	ProductOrder productOrder = new ProductOrder();
    		productOrder.setId(id);
    		productOrder.setLogisticsCompany(reqInfo.getParameter("logisticsCompany"));
    		productOrder.setLogisticsNum(reqInfo.getParameter("logisticsNum"));
    		orderService.addLogistics(productOrder);
    		model.addAttribute("code", 0);
        }else{
        	model.addAttribute("code", 1);
        }
        AjaxResponseUtil.returnData(response, JSONUtil.toJson(model));
		return null;
	}
	
	
	
	
	
	
}
