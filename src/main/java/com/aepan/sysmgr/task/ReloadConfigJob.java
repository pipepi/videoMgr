/**
 * 
 */
package com.aepan.sysmgr.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aepan.sysmgr.azure.AzureMediaSDKException;
import com.aepan.sysmgr.azure.KS3YunSDK;
import com.aepan.sysmgr.azure.MediaServiceSDK;
import com.aepan.sysmgr.azure.StorageServiceSDK;
import com.aepan.sysmgr.model.config.AzureConfig;
import com.aepan.sysmgr.model.config.Ks3Config;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.util.ConfigManager;

/**
 * 每1小时重新加载配置
 * @author lanker
 * 2015年9月12日下午2:00:48
 */
@Component("ReloadConfigJob")
public class ReloadConfigJob {
	private static final Logger logger = LoggerFactory.getLogger(ReloadConfigJob.class);
	
	@Autowired
	private ConfigService configService;
	

	
	@Scheduled(fixedRateString="3600000",initialDelayString="8000")
	public void run() {
		
		
		logger.debug("------------ReloadConfigJob");
		
		try {
			
			ConfigManager.getInstance().loadCache(configService);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		try {
			
			Ks3Config ks3Config = ConfigManager.getInstance().getKs3Config(configService);
			KS3YunSDK.init(ks3Config);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		
		AzureConfig config=ConfigManager.getInstance().getAzureConfig(configService);
		try {
			MediaServiceSDK.init(config);
			StorageServiceSDK.init(config);
		} catch (AzureMediaSDKException e) {
			logger.error(e.getMessage(),e);
		}
		/*ConfigManager.getInstance().getAlipayConfig(configService);
		ConfigManager.getInstance().getWechatpayConfig(configService);
		ConfigManager.getInstance().getSmsConfig(configService);
		ConfigManager.getInstance().getEmailConfig(configService);
		ConfigManager.getInstance().getFileConfig(configService);
		ConfigManager.getInstance().getPartnerConfig(configService);*/
		
	}
	
}
