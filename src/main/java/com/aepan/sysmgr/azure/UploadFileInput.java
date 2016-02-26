/**
 * 
 */
package com.aepan.sysmgr.azure;

import javax.servlet.http.HttpServletRequest;


/**
 * @author lanker
 * 2015年12月31日上午10:42:31
 */
public class UploadFileInput {
	public boolean isLocalVideo;//视频是否存储到/删除本地
	public HttpServletRequest req;//视频存储到本地
	public String uploadAssetId;//微软云上传资源id 或本地视频名称
	public String encodeAssetId;//微软云编码资源id 或本地视频完整路径
	public boolean isLocalImg;//图片是否存储到/删除本地
	public String imgMax;
	public String imgMin;
	public String imgName;//上传图片名称
	public byte[] imgContent;//上传图片二进制内容
	public UploadFileInput() {
	}
	public UploadFileInput(boolean isLocalVideo,String uploadAssetId,String encodeAssetId) {
		this.isLocalVideo = isLocalVideo;
		this.uploadAssetId = uploadAssetId;
		this.encodeAssetId = encodeAssetId;
	}
	public UploadFileInput(boolean isLocalVideo,HttpServletRequest req) {
		this.isLocalVideo = isLocalVideo;
		this.req = req;
	}
	public UploadFileInput(HttpServletRequest req,boolean isLocalImg) {
		this.isLocalImg = isLocalImg;
		this.req = req;
	}
	public UploadFileInput(String imgName,byte[] imgContent,boolean isLocalImg) {
		this.isLocalImg = isLocalImg;
		this.imgName =imgName;
		this.imgContent = imgContent;
	}
	public UploadFileInput(String imgMax,String imgMin,boolean isLocalImg) {
		this.isLocalImg = isLocalImg;
		this.imgMax =imgMax;
		this.imgMin = imgMin;
	}
	public UploadFileInput(boolean isLocalVideo,String uploadAssetId,String encodeAssetId,boolean isLocalImg,String imgMax,String imgMin) {
		this(isLocalVideo,uploadAssetId,encodeAssetId);
		this.isLocalImg = isLocalImg;
		this.imgMax =imgMax;
		this.imgMin = imgMin;
	}
}
