/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com._21cn.framework.exception.BusinessRuntimeException;
import com._21cn.framework.util.PageList;
import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.model.Logistics;
import com.aepan.sysmgr.model.ProductOrder;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.service.OrderService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.DataTableReturnObject;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

/**
 * 订单控制器
 * @author rakika
 * 2015年7月31日上午11:05:25
 */
@Controller
public class OrderController extends DataTableController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	OrderService orderService;
	
	/**
	 * 获取list
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/list")
	public String list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		if(!isLogin(request)){
			return "redirect:/login";
		}
		
		//查询所有的资源
    	PageList<ProductOrder> productOrder = orderService.getList(model, 1, Integer.MAX_VALUE);
    	model.addAttribute("productOrder", productOrder);
    	logger.debug("productOrder:" + productOrder.size());
    	return "/order/list";
	}
	
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/search")
    public String search(HttpServletRequest request, HttpServletResponse response, ModelMap model){
    	
		setPageSortModel(request, model, new String[]{"", "id", "channel_id", "order_id", 
				"buyers_id", "buyers_name", "seller_id", "seller_name", "product_id",
				"price", "to_address", "to_mobile", "order_status", "deliver_status",
				"create_time"});
		User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
		model.put("userName", user.getUserName());
		logger.info("userName:" + user.getUserName());
    	PageList<ProductOrder> list = orderService.getList(model, 
    			Integer.parseInt(model.get("pageNo").toString()), Integer.parseInt(model.get("pageSize").toString()));
    	logger.info("ProductOrder:" + list.size());
    	logger.debug("ProductOrder:" + list.size());
    	DataTableReturnObject data = new DataTableReturnObject();
    	data.setResult(list, model.get("sEcho").toString());
    	AjaxResponseUtil.returnData(response, JSONObject.toJSONString(data));
    	return null;
    }
	
	/**
	 * 编辑订单
	 * （只允许修改订单金额）
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("eqId", -1);
        if(id > 0){
        	ProductOrder productOrder = orderService.getById(id);
            if(productOrder == null){
                throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
            }
            model.addAttribute("productOrder", productOrder);
            model.addAttribute("read", false);
        }
		return "/order/form";
	}
	
	/**
	 * 新增物流
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/addlogistics")
	public String addlogistics(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("eqId", -1);
        if(id > 0){
        	ProductOrder productOrder = orderService.getById(id);
            if(productOrder == null){
                throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
            }
            model.addAttribute("productOrder", productOrder);
            String[] ds = new String[]{"未发货", "已发货未送达", "已送达", "退货", "延期"};
            model.addAttribute("deliverStatus", ds);
        }
		return "/order/logistics";
	}
	
	/**
	 * 保存物流
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/savelog")
	public String savelog(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("id", -1);
        if(id > 0){
        	ProductOrder productOrder = new ProductOrder();
    		productOrder.setId(id);
    		productOrder.setLogisticsCompany(reqInfo.getParameter("logisticsCompany"));
    		productOrder.setLogisticsNum(reqInfo.getParameter("logisticsNum"));
    		orderService.addLogistics(productOrder);
        }
		return "redirect:/order/list.do";
	}
	
	/**
	 * 显示物流
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/showlogistics")
	public String showLogistics(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("id", -1);
        if(id > 0){
        	ProductOrder productOrder = orderService.getById(id);
        	Logistics ls = getLogitics(productOrder.getLogisticsCompany(),
        			productOrder.getLogisticsNum());
    		request.setAttribute("logistics", ls);
    		return "/order/showlogistics";
        }else{
        	return "redirect:/order/list.do";
        }
	}
	
	/**
	 * 保存修改价格
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/update")
	public String update(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("id", -1);
        Float price = reqInfo.getFloatParameter("price", 0);
        if(id > 0 && price >= 0){
        	ProductOrder productOrder = new ProductOrder();
    		productOrder.setId(id);
    		productOrder.setPrice(price);
    		orderService.update(productOrder, Constants.UPDATE_PRICE);
        }
		return "redirect:/order/list.do";
	}
	
	@RequestMapping("/order/testOrderStatus")
	public String testOrderStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("id", -1);
        if(id > 0){
        	ProductOrder productOrder = orderService.getById(id);
        	model.addAttribute("productOrder", productOrder);
        	model.addAttribute("method", Constants.UPDATE_ORDER_STATUS);
            String[] ds = new String[]{"未发货", "已发货未送达", "已送达", "退货", "延期"};
            model.addAttribute("deliverStatus", ds);
        }
		return "/order/testform";
	}
	
	@RequestMapping("/order/testUpdate")
	public String testUpdate(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("id", -1);
        if(id > 0){
        	ProductOrder productOrder = orderService.getById(id);
    		if(productOrder != null && 
    				reqInfo.getIntParameter("method") == Constants.UPDATE_ORDER_STATUS){
    			productOrder.setChannelId(reqInfo.getParameter("channel_id"));
    			productOrder.setOrderId(reqInfo.getParameter("order_id"));
    			productOrder.setOrderStatus(reqInfo.getIntParameter("order_status"));
    			logger.info("更新订单状态");
    			orderService.update(productOrder, Constants.UPDATE_ORDER_STATUS);
    		}
    		if(productOrder != null && 
    				reqInfo.getIntParameter("method") == Constants.UPDATE_GOOD_STATUS){
    			productOrder.setChannelId(reqInfo.getParameter("channel_id"));
    			productOrder.setOrderId(reqInfo.getParameter("order_id"));
    			productOrder.setDeliverStatus(reqInfo.getIntParameter("deliver_status"));
    			logger.info("更新送货状态");
    			orderService.update(productOrder, Constants.UPDATE_ORDER_STATUS);
    		}
    		
        }
		return "redirect:/order/list.do";
	}
	
	@RequestMapping("/order/testGoodStatus")
	public String testGoodStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("id", -1);
        if(id > 0){
        	ProductOrder productOrder = orderService.getById(id);
        	
        	model.addAttribute("productOrder", productOrder);
        	model.addAttribute("method", Constants.UPDATE_GOOD_STATUS);
            String[] ds = new String[]{"未发货", "已发货未送达", "已送达", "退货", "延期"};
            model.addAttribute("deliverStatus", ds);
        }
		return "/order/testform";
	}
	
	@RequestMapping("/order/testOrderst")
	public String testOrderst(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		return "/order/testorderst";
	}
	
	/**
	 * 保存修改订单状态
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/updateStatus")
	public String updateStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("id", -1);
        int orderStatus = reqInfo.getIntParameter("status", -1);
        if(id > 0 && orderStatus > 0 && orderStatus < 3){
        	ProductOrder productOrder = orderService.getById(id);
        	if(productOrder.getOrderStatus() < orderStatus){
        		productOrder.setId(id);
        		productOrder.setOrderStatus(orderStatus);
        		orderService.update(productOrder, Constants.UPDATE_ORDER_STATUS);
        	}
        }else{
        	logger.debug("params error");
        }
		return "redirect:/order/list.do";
	}
	
	/**
	 * 关闭订单
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/close")
	public String close(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		Integer id = reqInfo.getIntParameter("eqId", -1);
		if(id > 0){
			ProductOrder productOrder = orderService.getById(id);
			if(productOrder != null && productOrder.getOrderStatus() != 3){
				orderService.update(productOrder, Constants.UPDATE_ORDER_CANCEL);
			}
		}
		return "redirect:/order/list.do";
	}
	
	/**
	 * 查看订单（不能修改）
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/show")
	public String show(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer id = reqInfo.getIntParameter("eqId", -1);
        if(id > 0){
        	ProductOrder productOrder = orderService.getById(id);
            if(productOrder == null){
                throw new BusinessRuntimeException( "error.param.wrong", "无效参数" );
            }
            model.addAttribute("productOrder", productOrder);
            model.addAttribute("read", true);
            String[] ds = new String[]{"未发货", "已发货未送达", "已送达", "退货", "延期"};
            model.addAttribute("deliverStatus", ds);
        }
		return "/order/form";
	}
	
	/**
	 * 退货
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/refund")
	public String refund(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		Integer id = reqInfo.getIntParameter("eqId", -1);
		if(id > 0){
			ProductOrder productOrder = orderService.getById(id);
			if(productOrder != null && productOrder.getOrderStatus() == 1 &&
					productOrder.getOrderStatus() == 2){
				productOrder.setDeliverStatus(Constants.DELIVER_STATUS_REFUND);
				orderService.update(productOrder, Constants.UPDATE_GOOD_STATUS);
			}
		}
		return "redirect:/order/list.do";
	}
	
	/**
	 * 手机list
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/mobilelist")
	public String mobilelist(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
/*		if(!isLogin(request)){
			return "redirect:/login";
		}
*/
    	return "/order/mobilelist";
	}
	
	/**
	 * 手机查找
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/mobilesearch")
    public String mobilesearch(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		if(!isLogin(request)){
			return null;
		}
		
		setPageSortModel(request, model, new String[]{"", "id", "channel_id", "order_id", 
				"buyers_id", "buyers_name", "seller_id", "seller_name", "product_id",
				"price", "to_address", "to_mobile", "order_status", "deliver_status",
				"create_time"});
		String mobile = (String)request.getSession().getAttribute(Constants.SESSION_MOBILE);
		model.put("toMobile", mobile);
		logger.info("toMobile:" + mobile);
    	PageList<ProductOrder> list = orderService.getMobileList(model, 
    			Integer.parseInt(model.get("pageNo").toString()), Integer.parseInt(model.get("pageSize").toString()));
    	logger.debug("ProductOrder:" + list.size());
    	DataTableReturnObject data = new DataTableReturnObject();
    	data.setResult(list, model.get("sEcho").toString());
    	AjaxResponseUtil.returnData(response, JSONObject.toJSONString(data));
    	return null;
    }
	
	/**
	 * 手机号登陆
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/mobilelogin")
	public String mobilelogin(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String mobile = reqInfo.getParameter("mobile","");
		logger.info("mobile:" + mobile);
		if(orderService.isMobile(mobile)){
			request.getSession().setAttribute(Constants.SESSION_MOBILE, mobile);
			AjaxResponseUtil.returnData(response, "{\"code\": 0}");
		}else{
			AjaxResponseUtil.returnData(response, "{\"code\": -1}");
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param logisticsCompany
	 * @param logisticsNum
	 * @return
	 */
	private Logistics getLogitics(String logisticsCompany, String logisticsNum){
		String url = "http://www.kuaidi100.com/query?type=" + logisticsCompany 
				+ "&postid=" + logisticsNum;
		
        GetMethod method = new GetMethod(url);
        
		HttpClient client = new HttpClient();

		logger.info(method.toString());
		Logistics ls = new Logistics();
		String ret = "";
		try {
			client.executeMethod(method);
			ret = method.getResponseBodyAsString();
			logger.info("-------------"+ret);
			
	        if(method.getStatusCode() == 200 ){
	        	Gson gson = new Gson();
	        	ls = gson.fromJson(ret, Logistics.class);	        	
	        }
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return ls;
	}
	
}