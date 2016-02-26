/**
 * 重新同步类目
 */
package com.aepan.sysmgr.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aepan.sysmgr.model.config.PartnerConfig;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.ProductTypeService;
import com.aepan.sysmgr.util.ConfigManager;

/**
 * @author lanker
 * 2015年10月23日下午5:12:09
 */
@Component("ReloadCategory")
public class ReloadCategory {
	private static final Logger logger = LoggerFactory.getLogger(ReloadCategory.class);

	@Autowired
	private ProductTypeService pts;
	@Autowired
	private ConfigService configService;
	@Scheduled(fixedRateString="1800000",initialDelayString="10000")
	public void synchronous(){
		logger.info("\n\n\n\n--------------------------------------\n\n\n\n");
		logger.info("ProductTypeService--------------"+pts);
		PartnerConfig config = ConfigManager.getInstance().getPartnerConfig(configService);
		String url = config.ROOT_PATH_KAIMAI8+"/api/category";
		pts.synchronous(url);
		logger.info("\n\n\n\n--------------------------------------\n\n\n\n");
	}
}
