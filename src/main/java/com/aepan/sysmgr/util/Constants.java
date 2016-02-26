/**
 * 
 */
package com.aepan.sysmgr.util;

/**
 * 常量
 * @author rakika
 * 2015年6月14日下午5:46:38
 */
public interface Constants {

	public static final int USER_ENABLE = 1;	//用户可用
	
	public static final int USER_AUDIT = 0;	    //用户待审核
	
	public static final int USER_DISABLE = -1;	//用户停用
	
	public static final int PAGE_NO = 1;	//默认第几页
	
	public static final int PAGE_SIZE = 20;	//默认每页条数
	
	public static final String SESSION_USER = "currentUser";  //sessionUser
	public static final String SESSION_MEMBERID = "memberId";
	public static final String SESSION_SUBUSERID = "subUserId";  
	public static final String CURRENT_PAY_PACKAGE_ORDER_ID = "currentPayPackageOrderId";  //当前正在支付的套餐orderId
	
	public static final String SESSION_MOBILE = "currentMobile"; //sessionMobile
	
	public static final String SESSION_UPLOAD_VIDEO_RPS = "uploadVideoRps"; //上传视频，微软云地址.安全性要求不能返回到浏览器，固存到session中
	
	public static final String SESSION_ADMIN_USER = "currentAdminUser";  //sessionUser
	
	public static final String SESSION_PERMISSION = "permission";  //permission
	
	public static final String SESSION_ZTREENODES = "ztreeNodes";  //ztreeNodes
	
	public static final String SESSION_CHECK_CODE = "checkcode"; //校验码
	
	public static final int UPDATE_PRICE = 1;  //更新价格
	
	public static final int UPDATE_ORDER_STATUS = 2;  //更新订单状态
	
	
	public static final int UPDATE_GOOD_STATUS = 3;  //更新货物状态
	
	public static final int UPDATE_ORDER_CANCEL = 4;  //取消订单
	
	public static final int UPDATE_WXCODE_IMG_URL = 5;  //更新微信codeimgurl
	public static final int UPDATE_ORDER_BACK = 6;  //更新退换货信息
	
	public static final int UPDATE_ORDER_STATUS_AND_OUT_ORDER_ID = 7;  //更新订单状态和第三方订单id
	
	public static final int DELIVER_STATUS_REFUND = 3; //退货
	
	public static final String FILE_IMG_UPLOAD_PATH = "C://test/"; //上传路径
	
	public static final String TRADITIONAL_SYS = "traditionsys";//传统电商
	
	public static final String PARTNER_IMG_URL = "http://mall-azt.chinacloudapp.cn";//第三方图片地址
	
	public static final int PRIVILEGECODE_AUDIT=12001;
	
	public static final int PRIVILEGECODE_PACKAGE=12002;
	
	public static final int PRIVILEGECODE_SELLER=12003;
	
	
	public static final int PRIVILEGECODE_SELLERPLAYER=10001;
	
	public static final int PRIVILEGECODE_SELLERVIDEO=10002;
	
	public static final String URL_ENCODE="UTF-8";
	
	
	
}
