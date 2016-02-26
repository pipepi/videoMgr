package com.aepan.sysmgr.azure;

/**
 * 
 * @author Doris.zhou
 *
 * @2015��8��6��
 */
public class AzureMediaSDKException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int TRUSTMANAGER_ERROR=10000001;
	
	public static final int INIT_AZUER_MEDIA_SERVICE_ERROR=10000002;
	
	public static final int UPDATE_VEDIO_FAILD=10000003;
	
	public static final int DELETE_VIDEO_FAILD=10000004;

	public static final int INIT_STORAGE_SERVICE_ERROR = 10000005;

	public static final int UPLOAD_FILE_FAILED = 10000006;

	public static final int DELETE_IMAGE_FAILED = 10000007;

	public static final int GET_CONTAINER_FAILED = 10000008;
	
	public static final int FILE_AREADY_EXISTS = 10000009;
	
	

	protected int code;

	//
	public AzureMediaSDKException(int code) {
		super();
		this.code = code;
	}

	public AzureMediaSDKException(int code, String msg) {
		super(msg);
		this.code = code;
	}

	public AzureMediaSDKException(int code, String msg, Throwable e) {
		super(msg, e);
		this.code = code;
	}

	public AzureMediaSDKException(int code, Throwable e) {
		super(e);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return "[" + code + "] " + super.getMessage();
	}

}
