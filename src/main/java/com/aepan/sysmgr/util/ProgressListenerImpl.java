package com.aepan.sysmgr.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.apache.commons.fileupload.ProgressListener;

public class ProgressListenerImpl implements ProgressListener {
	private HttpSession session;

	public ProgressListenerImpl(HttpServletRequest req) {
		session=req.getSession();
		FileUploadStatus status = new FileUploadStatus();
		session.setAttribute("status", status);
	}

	/* pBytesRead  到目前为止读取文件的比特数
	 * pContentLength 文件总大小
	 * pItems 目前正在读取第几个文件
	 */
	public void update(long pBytesRead, long pContentLength, int pItems) {
		// TODO Auto-generated method stub
		FileUploadStatus status = (FileUploadStatus) session.getAttribute("status");
		status.setPBytesRead(pBytesRead);
		status.setPContentLength(pContentLength);
		status.setPItems(pItems);
	}

}
