/**
 * 
 */
package com.aepan.sysmgr.model.config;

import java.io.Serializable;

/**
 * @author lanker
 * 2015年10月9日下午4:24:02
 */
public class PartnerConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	public String ROOT_PATH_KAIMAI8;//开卖8根路径，用于关联产品图片展示
	public String PARTNER_INDEX_URL;//
	public String GET_PARTNER_PRODUCT_PAGE_URL; 
	public String GET_PARTNER_PRODUCT_DETAIL_URL;
	public String GET_PARTNER_PRODUCT_SKUSINFO;
	public String UPDATE_PRODUCT_STOKE;
	
	public String getROOT_PATH_KAIMAI8() {
		return ROOT_PATH_KAIMAI8;
	}

	public void setROOT_PATH_KAIMAI8(String rOOT_PATH_KAIMAI8) {
		ROOT_PATH_KAIMAI8 = rOOT_PATH_KAIMAI8;
	}

	public String getPARTNER_INDEX_URL() {
		return PARTNER_INDEX_URL;
	}

	public void setPARTNER_INDEX_URL(String pARTNER_INDEX_URL) {
		PARTNER_INDEX_URL = pARTNER_INDEX_URL;
	}

	public String getGET_PARTNER_PRODUCT_PAGE_URL() {
		return GET_PARTNER_PRODUCT_PAGE_URL;
	}

	public void setGET_PARTNER_PRODUCT_PAGE_URL(String gET_PARTNER_PRODUCT_PAGE_URL) {
		GET_PARTNER_PRODUCT_PAGE_URL = gET_PARTNER_PRODUCT_PAGE_URL;
	}

	public String getGET_PARTNER_PRODUCT_DETAIL_URL() {
		return GET_PARTNER_PRODUCT_DETAIL_URL;
	}

	public void setGET_PARTNER_PRODUCT_DETAIL_URL(
			String gET_PARTNER_PRODUCT_DETAIL_URL) {
		GET_PARTNER_PRODUCT_DETAIL_URL = gET_PARTNER_PRODUCT_DETAIL_URL;
	}

	public String getGET_PARTNER_PRODUCT_SKUSINFO() {
		return GET_PARTNER_PRODUCT_SKUSINFO;
	}

	public void setGET_PARTNER_PRODUCT_SKUSINFO(String gET_PARTNER_PRODUCT_SKUSINFO) {
		GET_PARTNER_PRODUCT_SKUSINFO = gET_PARTNER_PRODUCT_SKUSINFO;
	}

	public String getUPDATE_PRODUCT_STOKE() {
		return UPDATE_PRODUCT_STOKE;
	}

	public void setUPDATE_PRODUCT_STOKE(String uPDATE_PRODUCT_STOKE) {
		UPDATE_PRODUCT_STOKE = uPDATE_PRODUCT_STOKE;
	}

	public String getGET_VIDEOAUTH_URL() {
		return GET_VIDEOAUTH_URL;
	}

	public void setGET_VIDEOAUTH_URL(String gET_VIDEOAUTH_URL) {
		GET_VIDEOAUTH_URL = gET_VIDEOAUTH_URL;
	}

	public String getGET_SELLERVIDEOAUTH_URL() {
		return GET_SELLERVIDEOAUTH_URL;
	}

	public void setGET_SELLERVIDEOAUTH_URL(String gET_SELLERVIDEOAUTH_URL) {
		GET_SELLERVIDEOAUTH_URL = gET_SELLERVIDEOAUTH_URL;
	}

	public String GET_VIDEOAUTH_URL;//admin 权限接口
	
	public String GET_SELLERVIDEOAUTH_URL;//SellerAdmin权限接口
	
	
	
	
}
