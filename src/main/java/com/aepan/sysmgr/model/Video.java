/**
 * 
 */
package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com._21cn.framework.util.PageList;
import com.aepan.sysmgr.dao.UserDao;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.StringUtil;

/**
 * @author Administrator
 * 2015年8月3日上午10:57:12
 */
public class Video implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int REUPLOAD_AZURE_TIME = 3600;//重新上传微软云时间=1小时；
	
	public static final boolean active_激活 = true;
	public static final boolean active_未激活 = false;
	
	public static final int type_rm = 1;
	public static final int type_avi = 2;
	public static final int type_mp4 = 4;
	public static final int type_mpeg = 5;
	public static final int type_3gp = 6;
	/**
	 * html标签过滤
	 * @return
	 */
	public final Video escapeHtml(){
		this.name = StringEscapeUtils.escapeHtml(this.name);
		this.desc = StringEscapeUtils.escapeHtml(this.desc);
		if(checkMsgStr!=null&&!checkMsgStr.isEmpty()){
			this.checkMsgStr = StringEscapeUtils.escapeHtml(this.checkMsgStr);
		}
		return this;
	}
	/**
	 * 敏感词过滤
	 * @return
	 */
	public final Video harmSensitiveWord(){
		this.name = StringUtil.harmSensitiveWord(this.name);
		this.desc = StringUtil.harmSensitiveWord(this.desc);
		if(checkMsgStr!=null&&!checkMsgStr.isEmpty()){
			this.checkMsgStr = StringUtil.harmSensitiveWord(this.checkMsgStr);
		}
		return this;
	}
	/**
	 * 
	 */
	public Video() {
		this.checkState = VideoCheck.state_待审核;
	}
	//id
	public int id;
	public int userId;
	//32位唯一标识
	public String guid; 
	//名称
	public String name;
	//合作方帐号名称
	public String partnerAccountName;
	//简介
	public String desc;
	//视频地址url
	public String video;
	//h5视频地址
	public String h5Video;
	//文件大小
	public float videoSize;
	//h5文件大小
	public float h5VideoSize;
	//点击次数
	public long videoCnum;
	//h5点击次数
	public long h5VideoCnum;
	
	public String uploadAssetId;
	public String encodeAssetId;
	//大图
	public String imgMax;//1004*568
	public String imgMax414;//414*233
	public String imgMin301;//301*170
	//小图
	public String imgMin;//188*106
	public String imgPre;
	//存储平台  0：本地；1：微软云；2：金山云；
	public int storage;
	//类型
	public int type;
	//排序值
	public int sortOrder;
	//是否激活
	public boolean active;
	//审核状态
	public int checkState;
	//审核消息
	public List<VideoCheck> checkMsgs;
	public String checkMsgStr;
	//创建时间
	public Date createTime;
	//修改时间
	public Date modificationTime;
	
	public static void setParterAccountName(PageList<Video> list,UserDao userDao){
		if(list!=null&&!list.isEmpty()){
			Map<Integer,String> tmp = new HashMap<Integer, String>();
			for(int i = 0 ;i<list.size();i++){
				int userId = list.get(i).userId;
				if(tmp.get(userId)!=null){
					list.get(i).partnerAccountName = tmp.get(userId);
				}else{
					User user = userDao.getByUserId(userId);
					if(user!=null){
						list.get(i).partnerAccountName = user.getPartnerAccountName();
						tmp.put(userId, list.get(i).partnerAccountName);
					}
				}
			}
		}
	}
	
	public String getImgPre() {
		return imgPre;
	}
	public void setImgPre(String imgPre) {
		this.imgPre = imgPre;
	}
	public int getStorage() {
		return storage;
	}
	public void setStorage(int storage) {
		this.storage = storage;
		this.imgPre = ConfigManager.imgPre(storage);
		if(this.imgMax!=null&&this.imgMax.trim().length()>0&&!this.imgMax.startsWith(this.imgPre)){
			this.imgMax = this.imgPre+this.imgMax;
		}
		if(this.imgMax414!=null&&this.imgMax414.trim().length()>0&&!this.imgMax414.startsWith(this.imgPre)){
			this.imgMax414 = this.imgPre+this.imgMax414;
		}
		if(this.imgMin301!=null&&this.imgMin301.trim().length()>0&&!this.imgMin301.startsWith(this.imgPre)){
			this.imgMin301 = this.imgPre+this.imgMin301;
		}
		if(this.imgMin!=null&&this.imgMin.trim().length()>0&&!this.imgMin.startsWith(this.imgPre)){
			this.imgMin = this.imgPre+this.imgMin;
		}
	}
	public void deleteImgPre(){
		if(this.imgMax!=null&&this.imgMax.trim().length()>0&&this.imgPre!=null){
			this.imgMax = this.imgMax.replace(this.imgPre, "");
		}
		if(this.imgMax414!=null&&this.imgMax414.trim().length()>0&&this.imgPre!=null){
			this.imgMax414 = this.imgMax414.replace(this.imgPre, "");
		}
		if(this.imgMin301!=null&&this.imgMin301.trim().length()>0&&this.imgPre!=null){
			this.imgMin301 = this.imgMin301.replace(this.imgPre, "");
		}
		if(this.imgMin!=null&&this.imgMin.trim().length()>0&&this.imgPre!=null){
			this.imgMin = this.imgMin.replace(this.imgPre, "");
		}
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPartnerAccountName() {
		return partnerAccountName;
	}
	public void setPartnerAccountName(String partnerAccountName) {
		this.partnerAccountName = partnerAccountName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	/**
	 * @return the h5Video
	 */
	public String getH5Video() {
		return h5Video;
	}

	/**
	 * @param h5Video the h5Video to set
	 */
	public void setH5Video(String h5Video) {
		this.h5Video = h5Video;
	}

	public String getUploadAssetId() {
		return uploadAssetId;
	}
	public void setUploadAssetId(String uploadAssetId) {
		this.uploadAssetId = uploadAssetId;
	}
	public String getEncodeAssetId() {
		return encodeAssetId;
	}
	public void setEncodeAssetId(String encodeAssetId) {
		this.encodeAssetId = encodeAssetId;
	}
	public String getImgMax() {
		return imgMax;
	}
	public void setImgMax(String imgMax) {
		this.imgMax = imgMax;
	}
	
	public String getImgMax414() {
		return imgMax414;
	}
	public void setImgMax414(String imgMax414) {
		this.imgMax414 = imgMax414;
	}
	public String getImgMin301() {
		return imgMin301;
	}
	public void setImgMin301(String imgMin301) {
		this.imgMin301 = imgMin301;
	}
	public String getImgMin() {
		return imgMin;
	}
	public void setImgMin(String imgMin) {
		this.imgMin = imgMin;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public int getCheckState() {
		return checkState;
	}
	public void setCheckState(int checkState) {
		this.checkState = checkState;
	}
	public List<VideoCheck> getCheckMsgs() {
		return checkMsgs;
	}
	public void setCheckMsgs(List<VideoCheck> checkMsgs) {
		this.checkMsgs = checkMsgs;
	}
	
	public String getCheckMsgStr() {
		return checkMsgStr;
	}
	public void setCheckMsgStr(String checkMsgStr) {
		this.checkMsgStr = checkMsgStr;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModificationTime() {
		return modificationTime;
	}
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}
	
}
