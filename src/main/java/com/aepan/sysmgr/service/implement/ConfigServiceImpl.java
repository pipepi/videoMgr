/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aepan.sysmgr.dao.ConfigDao;
import com.aepan.sysmgr.model.config.AlipayConfig;
import com.aepan.sysmgr.model.config.AzureConfig;
import com.aepan.sysmgr.model.config.Config;
import com.aepan.sysmgr.model.config.ConfigInfo;
import com.aepan.sysmgr.model.config.EmailConfig;
import com.aepan.sysmgr.model.config.FileConfig;
import com.aepan.sysmgr.model.config.FlowConfig;
import com.aepan.sysmgr.model.config.Ks3Config;
import com.aepan.sysmgr.model.config.LuceneConfig;
import com.aepan.sysmgr.model.config.PartnerConfig;
import com.aepan.sysmgr.model.config.SmsConfig;
import com.aepan.sysmgr.model.config.StorageConfig;
import com.aepan.sysmgr.model.config.VersionConfig;
import com.aepan.sysmgr.model.config.WechatpayConfig;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.JSONUtil;
import com.aepan.sysmgr.util.UuidUtil;

/**
 * @author lanker
 * 2015年8月10日上午11:22:52
 */
@Service
public class ConfigServiceImpl implements ConfigService {
	@Autowired
	private ConfigDao configDao;
	@Override
	public void add(Config c) {
		configDao.add(c);
	}
	@Override
	public void update(Config c){
		configDao.update(c);
	}
	@Override
	public Config query(int id){
		Config config = configDao.query(id);
		if(config==null&&id==Config.ID_配置维护key){
			config = new Config();
			config.id  = id;
			config.name = "配置维护key";
			config.config = UuidUtil.get32UUID();
			add(config);
		}
		return config;
	}
	@Override
	public void init(int id){
		Config config = configDao.query(id);
		if(config==null&&id==Config.ID_流量统计){
			config =new Config();
			config.id = id;
			config.name = "流量统计";
			FlowConfig flowConfig = new FlowConfig();
			flowConfig.open = true;
			flowConfig.authKey = UuidUtil.get32UUID();
			config.config = JSONUtil.toJson(flowConfig);
			add(config);
			ConfigManager.getInstance().updateCache(id, flowConfig);
		}
		if(config==null&&id == Config.ID_存储使用平台){
			config =new Config();
			config.id = id;
			config.name = "存储使用平台";
			StorageConfig storageConfig = new StorageConfig();
			storageConfig.storagePlatform = StorageConfig.PLATFORM_AZURE;
			config.config = JSONUtil.toJson(storageConfig);
			add(config);
			ConfigManager.getInstance().updateCache(id, storageConfig);
		}
		if(config==null&&id == Config.ID_版本){
			config = new Config();
			config.id = id;
			config.name = "版本";
			VersionConfig vconfig  = new VersionConfig();
			vconfig.version = "1.0.0";
			config.config = JSONUtil.toJson(vconfig);
			add(config);
			ConfigManager.getInstance().updateCache(id, vconfig);
		}
	}
	@Override
	public ConfigInfo getAllConfig(){
		List<Config> list= configDao.queryAll();
		ConfigInfo c = new ConfigInfo();
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				switch (list.get(i).id) {
				case Config.ID_文件上传配置:c.fileConfig = JSONUtil.fromJson(list.get(i).config, FileConfig.class);break;
				case Config.ID_微软配置:c.azureConfig = JSONUtil.fromJson(list.get(i).config,AzureConfig.class);break;
				case Config.ID_金山云配置:c.ks3Config = JSONUtil.fromJson(list.get(i).config,Ks3Config.class);break;
				case Config.ID_微信配置:c.wechatpayConfig = JSONUtil.fromJson(list.get(i).config, WechatpayConfig.class);break;
				case Config.ID_支付宝配置:c.alipayConfig = JSONUtil.fromJson(list.get(i).config, AlipayConfig.class);break;
				case Config.ID_搜索引擎:c.luceneConfig = JSONUtil.fromJson(list.get(i).config, LuceneConfig.class);break;
				case Config.ID_短信配置:c.smsConfig = JSONUtil.fromJson(list.get(i).config, SmsConfig.class);break;
				case Config.ID_邮件配置:c.emailConfig = JSONUtil.fromJson(list.get(i).config, EmailConfig.class);break;
				case Config.ID_合作方地址配置:c.partnerConfig = JSONUtil.fromJson(list.get(i).config, PartnerConfig.class);break;
				case Config.ID_配置维护key:c.configMgrKey = list.get(i).config;break;
				case Config.ID_流量统计:c.flowConfig = JSONUtil.fromJson(list.get(i).config, FlowConfig.class);break;
				case Config.ID_存储使用平台:c.storageConfig = JSONUtil.fromJson(list.get(i).config, StorageConfig.class);break;
				case Config.ID_版本:c.versionConfig = JSONUtil.fromJson(list.get(i).config, VersionConfig.class);break;
				default:
					break;
				}
			}
		}
		return c;
	}
}
