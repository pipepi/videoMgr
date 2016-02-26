/**
 * 
 */
package com.aepan.sysmgr.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aepan.sysmgr.service.PackageStatService;

/**
 * @author lanker
 * 2016年2月24日下午3:34:22
 */
@Component("FlowCountTask")
public class FlowCountTask {
	private static final Logger logger = LoggerFactory.getLogger(FlowCountTask.class);
	@Autowired
	private PackageStatService packageStatService;
	@Scheduled(fixedRateString="300000",initialDelayString="300000")
	public void run(){
		logger.info("\n\n\nbegin FlowCountTask\n\n\n");
		packageStatService.countUsedFlow();
		logger.info("\n\n\nend FlowCountTask\n\n\n");
	}
}
