/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.aepan.sysmgr.model.hm.PartnerStoreProduct;
import com.aepan.sysmgr.model.lucene.StoreSub;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.util.StringUtil;
import com.aepan.sysmgr.util.lucene.SearchHelper;

/**
 * 商铺
 * @author rakika
 * 2015年8月10日下午4:33:10
 */
public class Store implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7262644731986089332L;
	public static final int STATUS_离线 = 0;
	public static final int STATUS_在线 = 1;
	
	/**
	 * html标签过滤
	 * @return
	 */
	public final Store escapeHtml(){
		this.name = StringEscapeUtils.escapeHtml(this.name);
		this.description = StringEscapeUtils.escapeHtml(this.description);
		this.shareContent = StringEscapeUtils.escapeHtml(this.shareContent);
		return this;
	}
	/**
	 * 敏感词过滤
	 * @return
	 */
	public final Store harmSensitiveWord(){
		this.name = StringUtil.harmSensitiveWord(this.name);
		this.description = StringUtil.harmSensitiveWord(this.description);
		this.shareContent = StringUtil.harmSensitiveWord(this.shareContent);
		return this;
	}
	public static List<StoreSub> searchLucene(ConfigService configService,String k,String filed,int pageNo,int hitsPerPage){
		return SearchHelper.search(configService,k, filed, pageNo, hitsPerPage);
	}
	private int id;
	
	private String name;
	
	private String description;
	
	private String shareContent;
	
	private String innerCode;
	
	private String type;
	
	private String privateDns;
	
	private String comAddress;
	
	private String comTele;
	
	private int userId;
	
	private String logoUrl;
	private String logoUrl_301;
	
	private String maxLogoUrl_414;
	private String maxLogoUrl;
	
	private int status;
	
	private Date createTime;
	//华哥旧商品   建议删除 todo
	private List<PartnerProduct> partnerProductList;
	//用于播放器关联产品功能，产品列表
	private List<ProductInfo> prodcutInfoList;
	//预览功能 使用 建议和ProductInfo合并 todo
	private List<PartnerStoreProduct> storeProductList;
	
	private List<Video> videoList;
	
	public int linkedVideoNum;//已关联视频数量
	public int linkedProductNum;//已关联商品数量
	
	public String linkedVideoIds;//已关联视频ids
	public String linkedProductIds;//已关联商品ids
	
	/**
	 * @return the PartnerProductList
	 */
	public List<PartnerProduct> getPartnerProductList() {
		return partnerProductList;
	}

	/**
	 * @param PartnerProductList the PartnerProductList to set
	 */
	public void setPartnerProductList(List<PartnerProduct> partnerProductList) {
		this.partnerProductList = partnerProductList;
	}

	/**
	 * @return the videoList
	 */
	public List<Video> getVideoList() {
		return videoList;
	}

	/**
	 * @param videoList the videoList to set
	 */
	public void setVideoList(List<Video> videoList) {
		this.videoList = videoList;
	}

	public int getLinkedVideoNum() {
		return linkedVideoNum;
	}

	public void setLinkedVideoNum(int linkedVideoNum) {
		this.linkedVideoNum = linkedVideoNum;
	}

	public String getLinkedVideoIds() {
		return linkedVideoIds;
	}

	public void setLinkedVideoIds(String linkedVideoIds) {
		this.linkedVideoIds = linkedVideoIds;
	}

	/**
	 * @return the prodcutInfoList
	 */
	public List<ProductInfo> getProdcutInfoList() {
		return prodcutInfoList;
	}

	/**
	 * @param prodcutInfoList the prodcutInfoList to set
	 */
	public void setProdcutInfoList(List<ProductInfo> prodcutInfoList) {
		this.prodcutInfoList = prodcutInfoList;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the logoUrl
	 */
	public String getLogoUrl() {
		return logoUrl;
	}

	/**
	 * @param logoUrl the logoUrl to set
	 */
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	
	
	
	public String getLogoUrl_301() {
		return logoUrl_301;
	}
	public void setLogoUrl_301(String logoUrl_301) {
		this.logoUrl_301 = logoUrl_301;
	}
	public String getMaxLogoUrl_414() {
		return maxLogoUrl_414;
	}
	public void setMaxLogoUrl_414(String maxLogoUrl_414) {
		this.maxLogoUrl_414 = maxLogoUrl_414;
	}
	/**
	 * @return the maxLogoUrl
	 */
	public String getMaxLogoUrl() {
		return maxLogoUrl;
	}
	/**
	 * @param maxLogoUrl the maxLogoUrl to set
	 */
	public void setMaxLogoUrl(String maxLogoUrl) {
		this.maxLogoUrl = maxLogoUrl;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getShareContent() {
		return shareContent;
	}

	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrivateDns() {
		return privateDns;
	}

	public void setPrivateDns(String privateDns) {
		this.privateDns = privateDns;
	}

	public String getComAddress() {
		return comAddress;
	}

	public void setComAddress(String comAddress) {
		this.comAddress = comAddress;
	}

	public String getComTele() {
		return comTele;
	}

	public void setComTele(String comTele) {
		this.comTele = comTele;
	}


	public int getLinkedProductNum() {
		return linkedProductNum;
	}

	public void setLinkedProductNum(int linkedProductNum) {
		this.linkedProductNum = linkedProductNum;
	}

	public String getLinkedProductIds() {
		return linkedProductIds;
	}

	public void setLinkedProductIds(String linkedProductIds) {
		this.linkedProductIds = linkedProductIds;
	}

	/**
	 * @return the storeProductList
	 */
	public List<PartnerStoreProduct> getStoreProductList() {
		return storeProductList;
	}

	/**
	 * @param storeProductList the storeProductList to set
	 */
	public void setStoreProductList(List<PartnerStoreProduct> storeProductList) {
		this.storeProductList = storeProductList;
	}
	@Override
	public String toString() {
		return "Store [id=" + id + ", name=" + name + ", description="
				+ description + ", shareContent=" + shareContent
				+ ", type=" + type
				+ ", privateDns=" + privateDns + ", comAddress=" + comAddress
				+ ", comTele=" + comTele + ", userId=" + userId + ", logoUrl="
				+ logoUrl + ", status=" + status + ", createTime=" + createTime
				+ ", partnerProductList=" + partnerProductList
				+ ", prodcutInfoList=" + prodcutInfoList
				+ ", storeProductList=" + storeProductList + ", videoList="
				+ videoList + ", linkedVideoNum=" + linkedVideoNum
				+ ", linkedProductNum=" + linkedProductNum
				+ ", linkedVideoIds=" + linkedVideoIds + ", linkedProductIds="
				+ linkedProductIds + "]";
	}
	
}
