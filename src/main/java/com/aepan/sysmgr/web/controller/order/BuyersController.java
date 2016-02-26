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

import com._21cn.framework.util.PageList;
import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.model.ProductOrder;
import com.aepan.sysmgr.service.OrderService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.web.controller.DataTableController;
import com.aepan.sysmgr.web.controller.VideoController;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Lanker
 * 2015年10月8日上午11:46:36
 */
@Controller
public class BuyersController extends DataTableController{
	private static final Logger logger = LoggerFactory.getLogger(VideoController.class);
	@Autowired
	OrderService orderService;
	@RequestMapping("/buyers/orderpage")
	public String searchOrderPage(HttpServletRequest request,HttpServletResponse res,ModelMap model){
		String toPhone = (String)request.getSession().getAttribute(Constants.SESSION_MOBILE);//买家电话
		//toPhone = "13422896119";
		if(toPhone!=null){
			model.put("login", 0);
		}
		return "/order/buyers/list";
	}
	@RequestMapping("/buyers/ordersearch")
	public String searchOrder(HttpServletRequest request,HttpServletResponse res,ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String pageNo = reqInfo.getParameter("pn");
		String pageSize = reqInfo.getParameter("ps");
		String id = reqInfo.getParameter("id","");
		String sellerName = reqInfo.getParameter("sn","");
		String orderState = reqInfo.getParameter("os","");
		
		int pn = pageNo==null?1:Integer.parseInt(pageNo);
		int ps = pageSize==null?4:Integer.parseInt(pageSize);
		String toPhone = (String)request.getSession().getAttribute(Constants.SESSION_MOBILE);//买家电话
		//toPhone = "13422896119";
		//request.getSession().setAttribute(Constants.SESSION_MOBILE, toPhone);
		if(toPhone!=null){
			model.put("toMobile", toPhone);
			model.put("buyersDelete", "0");
			if(!id.trim().isEmpty()){
				model.put("orderId", id);
			}
			if(!sellerName.trim().isEmpty()){
				model.put("seller", sellerName);
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
		}
		return "/order/buyers/listsub";
	}
	/**买家逻辑删除*/
	@RequestMapping("/buyers/multidelete")
	public String multiLogicDelete(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		String toPhone = (String)request.getSession().getAttribute(Constants.SESSION_MOBILE);//买家电话
		if(toPhone==null){
			model.addAttribute("success", false);
			AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
			return null;
		}
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String ids = reqInfo.getParameter("ids");
		if(ids!=null&&ids.trim().length()>0){
			orderService.multiBuyersDelete(ids);
			model.addAttribute("success", true);
			AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
		}
		return null;
	}
	/**
	 * 保存修改订单状态
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/buyers/deliver")
	public String updateStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		String toPhone = (String)request.getSession().getAttribute(Constants.SESSION_MOBILE);//买家电话
		if(toPhone==null){
			model.addAttribute("success", false);
			AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
			return null;
		}
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("id", -1);
        Integer status = reqInfo.getIntParameter("status", -1);
        if(id>0&&status>0){
        	ProductOrder productOrder = orderService.getById(id);
        	if(productOrder!=null){
        		if(status==ProductOrder.DELIVER_STATUS_已收货||
        		   status==ProductOrder.DELIVER_STATUS_退换货中||
        		   status==ProductOrder.DELIVER_STATUS_已取货||
        		   status==ProductOrder.DELIVER_STATUS_已回货
        		   ){
        			if(productOrder.getDeliverStatus() < status){
        				productOrder.setId(id);
        				productOrder.setDeliverStatus(status);//送货状态：已收货
        				orderService.update(productOrder, Constants.UPDATE_GOOD_STATUS);
        				model.addAttribute("success", true);
        				AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
        				return null;
        			}
        		}
        	}
        }
    	logger.debug("params error");
    	model.addAttribute("success", false);
		AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
		return null;
	}
	@RequestMapping("/buyers/back")
	public String updateOrderBackInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		String toPhone = (String)request.getSession().getAttribute(Constants.SESSION_MOBILE);//买家电话
		if(toPhone==null){
			model.addAttribute("success", false);
			AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
			return null;
		}
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("id", -1);
        String phone = reqInfo.getParameter("phone", "");
        String mail = reqInfo.getParameter("mail", "");
        String address = reqInfo.getParameter("address", "");
        if(id>0&&!phone.isEmpty()&&!mail.isEmpty()&&!address.isEmpty()){
        	ProductOrder o = orderService.getById(id);
        	if(o!=null){
        		o.setDeliverStatus(ProductOrder.DELIVER_STATUS_申请退换货);
        		o.setBackMobile(phone);
        		o.setBackMail(mail);
        		o.setBackAddress(address);
        		orderService.updateOrderBackInfo(o);
        		model.addAttribute("success", true);
        	    AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
        	    return null;
        	}
        }        
        model.addAttribute("success", false);
        AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
		return null;
	}
}
