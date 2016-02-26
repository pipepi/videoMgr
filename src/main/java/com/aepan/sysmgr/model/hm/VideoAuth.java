/**
 * 
 */
package com.aepan.sysmgr.model.hm;

import java.io.Serializable;
import java.util.List;

/**
 * @author Doris
 * 2015年11月17日下午5:48:53
 */
public class VideoAuth implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Privilege> VideoAuthList;

	public List<Privilege> getVideoAuthList() {
		return VideoAuthList;
	}

	public void setVideoAuthList(List<Privilege> videoAuthList) {
		VideoAuthList = videoAuthList;
	}

	@Override
	public String toString() {
		return "VideoAuth [VideoAuthList=" + VideoAuthList + "]";
	}
	
	
	

}
