/**
 * 
 */
package com.aepan.sysmgr.web.api.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.model.config.EmailConfig;
import com.aepan.sysmgr.model.config.PartnerConfig;
import com.aepan.sysmgr.model.config.SmsConfig;
import com.aepan.sysmgr.model.packageinfo.PackageInfo;
import com.aepan.sysmgr.model.packageinfo.PackageStat;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.PackageService;
import com.aepan.sysmgr.service.PackageStatService;
import com.aepan.sysmgr.service.ProductTypeService;
import com.aepan.sysmgr.service.UserService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.EmailUtil;
import com.aepan.sysmgr.util.SmsUtil;

/**
 * 分布式  单点运行task
 * @author lanker
 * 2016年3月17日下午2:18:10
 */
@Controller
@RequestMapping("/rest/task")
public class TaskRest {
	private static final Logger logger = LoggerFactory.getLogger(TaskRest.class);
	
	@Autowired
	private PackageStatService packageStatService;
	@Autowired
	private PackageService packageService;
	@Autowired
	private UserService userService;
	@Autowired
	ConfigService configService;
	@Autowired
	private ProductTypeService pts;
	
	@RequestMapping(value="/countUsedFlow", produces = MediaType.APPLICATION_JSON_VALUE)
	public void countUsedFlow(HttpServletRequest request, HttpServletResponse response){
		packageStatService.countUsedFlow();
		AjaxResponseUtil.returnData(response, "ok");
	}
	@RequestMapping(value="/sendPackageDueTips", produces = MediaType.APPLICATION_JSON_VALUE)
	public void sendPackageDueTips(HttpServletRequest request, HttpServletResponse response){
		sendPackageDueSmsAndEmail();
		AjaxResponseUtil.returnData(response, "ok");
	}
	@RequestMapping(value="/reloadCategory", produces = MediaType.APPLICATION_JSON_VALUE)
	public void reloadCategory(HttpServletRequest request, HttpServletResponse response){
		pts.synchronous();
		AjaxResponseUtil.returnData(response, "ok");
	}
	private void sendPackageDueSmsAndEmail(){
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
						+packageInfo.getName()+"\"],\"%currentPackagePrice%\":[\""+packageInfo.getTotalPrice()+"\"],\"%companyLogo%\":[\""
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
