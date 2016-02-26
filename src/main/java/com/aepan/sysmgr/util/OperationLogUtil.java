/**
 * 
 */
package com.aepan.sysmgr.util;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aepan.sysmgr.model.config.PartnerConfig;
import com.aepan.sysmgr.model.log.OperationLog;
import com.aepan.sysmgr.service.ConfigService;

/**
 * @author lanker
 * 2015年11月16日上午10:27:09
 */
public class OperationLogUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);
	public static void addLog(ConfigService cs,OperationLog oplog){
		PartnerConfig partnerConfig = ConfigManager.getInstance().getPartnerConfig(cs);
		String url = partnerConfig.ROOT_PATH_KAIMAI8+"/api/operationlog/";
		if(oplog.getOpType()==OperationLog.TYPE_视频){
			url+="video";
		}else if(oplog.getOpType()==OperationLog.TYPE_套餐){
			url+="package";
		}else if(OperationLog.TYPE_播放器==oplog.getOpType()){
			url+="player";
		}
		HttpUtil.postJsonParam(url, oplog.toParams());
	}
	public static void main(String[] args) {
		OperationLog ol = OperationLog.instance();
		String url = "http://192.168.1.183:8080/api/operationlog/video";
		logger.info("send url = "+url);
		logger.info("send param = "+ol.toParams());
		HttpUtil.postJsonParam(url, ol.toParams());
	}
}
