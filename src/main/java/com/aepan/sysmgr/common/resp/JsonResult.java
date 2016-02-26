package com.aepan.sysmgr.common.resp;

/**
 * JSON返回对象
 * 
 * @author xieat
 * 
 */
public class JsonResult {

	private int result;

	private String msg;

	private String otid;

	private String tid;
	
	public int getResult() {
		return result;
	}

	public void setResult( int result ) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg( String msg ) {
		this.msg = msg;
	}

	public String getOtid() {
		return otid;
	}

	public void setOtid(String otid) {
		this.otid = otid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}


}
