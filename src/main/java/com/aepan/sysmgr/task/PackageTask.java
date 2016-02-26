package com.aepan.sysmgr.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.model.config.EmailConfig;
import com.aepan.sysmgr.model.config.SmsConfig;
import com.aepan.sysmgr.model.packageinfo.PackageInfo;
import com.aepan.sysmgr.model.packageinfo.PackageStat;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.PackageService;
import com.aepan.sysmgr.service.PackageStatService;
import com.aepan.sysmgr.service.UserService;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.EmailUtil;
import com.aepan.sysmgr.util.SmsUtil;

/**
 * 
 * @author Doris
 * 2015年11月2日下午3:18:10
 */
@Component("packageTask")
public class PackageTask {
	private static final Logger logger = LoggerFactory.getLogger(PackageTask.class);

	@Autowired
	private PackageStatService packageStatService;
	
	@Autowired
	private PackageService packageService;
	
	@Autowired
	private UserService userService;
	@Autowired
	ConfigService configService;

	@Scheduled(fixedRateString="7200000",initialDelayString="10000")
	public void run(){
		
		logger.info("\n\n\nPackageTask----------------------------------\n\n\n");
		//暂时
		/*List<PackageStat> packageStatList = packageStatService.queryLessThan200M();
		
		for (PackageStat packageStat : packageStatList) {
			try {
				
				int userId=packageStat.getUserId();
				User user = userService.getByUserId(userId);
				String email=user.getEmail();
				String partnerAccountName=user.getPartnerAccountName();
				String partnerMobile = user.getPartnerMobile();
				try {
					SmsConfig conf = ConfigManager.getInstance().getSmsConfig(configService);
					String message = String.format(SmsConfig.SHOP_FLOW_LESS_THAN_200, partnerAccountName,conf.serviceTel);
					SmsUtil.send(message, partnerMobile, configService, SmsConfig.TYPE_非验证码);
				} catch (Exception e) {
					logger.error("sendMsgFailed  shop_flow_less_than_200 :"+userId,e);
				}
				try {
					EmailUtil.sendMailTemplate(EmailUtil.TYPE_批量,"{\"to\": [\""+email+"\"],\"sub\":{\"%partnerAccountName%\":[\""+partnerAccountName+"\"]}}", "shop_flow_less_than_200", "9库网-视频电商流量不足", configService);
				} catch (Exception e) {
					logger.error("sendEmalFailed  shop_flow_less_than_200 :"+userId,e);
				}
				packageStatService.addPackageStatSendNotify(userId, PackageStat.SEND_NOTIFY_PROPERTY_FLOW_LESS_THAN_200M);
			} catch (Exception e) {
				logger.error("sendMsgFailed:"+packageStat,e);
			}
			
		}*/
		
		
		List<PackageStat> lessThan30dPackageStatList = packageStatService.queryLessThan30PackageStat();
		if(lessThan30dPackageStatList!=null&&lessThan30dPackageStatList.size()>0){
			Map<Integer,PackageInfo> packageInfoMap = new HashMap<Integer, PackageInfo>(); 
			List<PackageInfo> packageList = packageService.getList(PackageInfo.PACKAGE_TYPE_PACKAGE, 1, 3);
			for (PackageInfo packageInfo : packageList) {
				packageInfoMap.put(packageInfo.getId(), packageInfo);
			}
			
			for (PackageStat packageStat : lessThan30dPackageStatList) {
				try {
					int userId=packageStat.getUserId();
					User user = userService.getByUserId(userId);
					String email=user.getEmail();
					String partnerAccountName=user.getPartnerAccountName();
					int packageId=user.getPackageId();
					PackageInfo packageInfo = packageInfoMap.get(packageId);
					String partnerMobile = user.getPartnerMobile();
					EmailConfig conf = ConfigManager.getInstance().getEmailConfig(configService);
					try {
						String message = String.format(SmsConfig.SHOP_VIDEO_PACKAGE_LESS_THAN_30, partnerAccountName,conf.tel);
						SmsUtil.send(message, partnerMobile, configService, SmsConfig.TYPE_非验证码);
					} catch (Exception e) {
						logger.error("sendMsgFailed  shop_flow_less_than_200 :"+userId,e);
					}
					
					try {
						
						String emailContent = "{\"to\": [\""+email+"\"],\"sub\":{\"%partnerAccountName%\":[\""+partnerAccountName+"\"],\"%currentPackageName%\":[\""
						+packageInfo.getName()+"\"],\"%currentPackagePrice%\":[\""+packageInfo.getPrice()+"\"],\"%companyLogo%\":[\""
								+conf.logo+"\"],\"%companyName%\":[\""+conf.name+"\"],\"%companyAdress%\":[\""+conf.address+"\"],\"%companyEmail%\":[\""
						+conf.email+"\"],\"%companyTel%\":[\""+conf.tel+"\"],\"%companyWebSite%\":[\""+conf.webSite+"\"]}}";
						
						EmailUtil.sendMailTemplate(EmailUtil.TYPE_批量,emailContent, "shop_video_package_less_than_30", conf.fromname+"-视频电商套餐续费提醒", configService);
					} catch (Exception e) {
						logger.error("sendEmalFailed  shop_video_package_less_than_30 :"+userId,e);
					}
					
					packageStatService.addPackageStatSendNotify(userId, PackageStat.SEND_NOTIFY_PROPERTY_STAT_LESS_THAN_30D);
				} catch (Exception e) {
					logger.error("sendMsgFailed:"+packageStat,e);
				}
				
				
			}
			
		}
		
		
	}
}
