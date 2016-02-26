package com.aepan.sysmgr.azure;

import java.io.File;
import java.io.Serializable;

/**
 * 
 * @author Doris.zhou
 *
 * @2015年8月6日
 */
public class UploadFileRps implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 上传存储id，删除需要用
	 */
	private String uploadAssetId;
	/**
	 * 编码存储id，删除需要用
	 */
	private String encodeAssetId;
	/**
	 * 流url
	 */
	private String vedioUrl;
	
	private String h5VedioUrl;
	
	private long vedioFileSize;
	
	private long h5VedioFileSize;
	
	public String videoName;
	public String imgName;
	public String imgUrl;
	public boolean success;
	public File file;//视频存储到本地

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

	public String getVedioUrl() {
		return vedioUrl;
	}

	public void setVedioUrl(String vedioUrl) {
		this.vedioUrl = vedioUrl;
	}

	/**
	 * @return the h5VedioUrl
	 */
	public String getH5VedioUrl() {
		return h5VedioUrl;
	}

	/**
	 * @param h5VedioUrl the h5VedioUrl to set
	 */
	public void setH5VedioUrl(String h5VedioUrl) {
		this.h5VedioUrl = h5VedioUrl;
	}

	/**
	 * @return the vedioFileSize
	 */
	public long getVedioFileSize() {
		return vedioFileSize;
	}

	/**
	 * @param vedioFileSize the vedioFileSize to set
	 */
	public void setVedioFileSize(long vedioFileSize) {
		this.vedioFileSize = vedioFileSize;
	}

	/**
	 * @return the h5VedioFileSize
	 */
	public long getH5VedioFileSize() {
		return h5VedioFileSize;
	}

	/**
	 * @param h5VedioFileSize the h5VedioFileSize to set
	 */
	public void setH5VedioFileSize(long h5VedioFileSize) {
		this.h5VedioFileSize = h5VedioFileSize;
	}

	@Override
	public String toString() {
		return "UploadFileRps [uploadAssetId=" + uploadAssetId
				+ ", encodeAssetId=" + encodeAssetId + ", vedioUrl=" + vedioUrl
				+ ", h5VedioUrl=" + h5VedioUrl + ", vedioFileSize="
				+ vedioFileSize + ", h5VedioFileSize=" + h5VedioFileSize
				+ ", videoName=" + videoName + ", imgName=" + imgName
				+ ", imgUrl=" + imgUrl + ", success=" + success + "]";
	}

}
