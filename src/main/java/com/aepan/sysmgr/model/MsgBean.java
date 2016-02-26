package com.aepan.sysmgr.model;

import java.io.Serializable;

/**
 * @desc  全局信息
 * @since JDK 1.6
 * @version 1.0
 * @author pet
 * @date 2013/10/30
 */
public class MsgBean implements Serializable{

	private static final long serialVersionUID = 1112450813425084624L;
	private Long saveId;
	
	public Long getSaveId() {
		return saveId;
	}

	public void setSaveId(Long saveId) {
		this.saveId = saveId;
	}

	/**
	 * 消息体ID
	 */
	private String msgId = "-";
	
	/**
	 * 重发次数
	 */
	private Integer reSend = 0;
			
	/**
	 * 应答编码：1 - 成功，0 - 失败。
	 */
	private int reply = 1;
		
	/**
	 * 为了按时间清楚global队列了的msgBean
	 */
	private String msgTime = "-";
	
	/**
	 * 消息体
	 */
	private Object msgObject = null;

	public int getReply() {
		return reply;
	}

	public void setReply(int reply) {
		this.reply = reply;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public Integer getReSend() {
		return reSend;
	}

	public void setReSend(Integer reSend) {
		this.reSend = reSend;
	}
	
	public String getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}
	
	public Object getMsgObject() {
		return msgObject;
	}

	public void setMsgObject(Object msgObject) {
		this.msgObject = msgObject;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}




