/**
 * 
 */
package com.aepan.sysmgr.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aepan.sysmgr.service.VideoService;

/**
 * 视频异步编码（微软云）任务
 * @author lanker
 * 2015年12月8日下午2:05:07
 */
@Component("AzureTask")
public class AzureTask {
	private static final Logger logger = LoggerFactory.getLogger(AzureTask.class);
	@Autowired
	VideoService videoService;
	//最后修改时间在一个小时前，状态仍然是编码中的视频，执行再次上传微软云
	@Scheduled(fixedRateString="3600000",initialDelayString="10000")
	public void run(){
		logger.info("\n\n\nbegin upload video to azure\n\n\n");
		videoService.azureUploadAndEncode();
		logger.info("\n\n\n upload video to azure end\n\n\n");
	}
}
