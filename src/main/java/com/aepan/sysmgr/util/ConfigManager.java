/**
 * 
 */
package com.aepan.sysmgr.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aepan.sysmgr.azure.AzureMediaSDKException;
import com.aepan.sysmgr.azure.KS3YunSDK;
import com.aepan.sysmgr.azure.MediaServiceSDK;
import com.aepan.sysmgr.azure.StorageServiceSDK;
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
import com.aepan.sysmgr.model.config.WechatpayConfig;
import com.aepan.sysmgr.service.ConfigService;


/**
 * @author lanker
 * 2015年8月10日上午11:28:37
 */
public class ConfigManager {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);

	private static ConfigManager instance;
	private ConfigManager(){
		
	}
	public static ConfigManager getInstance(){
		if(instance==null){
			instance = new ConfigManager();
		}
		return instance;
	}
	private FileConfig fileConfig;
	private SmsConfig smsConfig;
	private EmailConfig emailConfig;
	private PartnerConfig partnerConfig;
	private LuceneConfig luceneConfig;
	private AlipayConfig alipayConfig;
	private WechatpayConfig wechatpayConfig;
	private AzureConfig azureConfig;
	private Ks3Config ks3Config;
	private FlowConfig flowConfig;
	private StorageConfig storageConfig;
	public static String imgPre(int storage){
		String rs = "";
		if( storage==StorageConfig.PLATFORM_AZURE){
			rs =  getInstance().fileConfig.IMG_AZURE_PRE;
		}else if( storage==StorageConfig.PLATFORM_KS3){
			rs = getInstance().ks3Config.getPicUrl();
		}
		return rs;
	}
	public void loadCache(ConfigService cs){
		ConfigInfo c = cs.getAllConfig();
		this.fileConfig = c.fileConfig;
		this.smsConfig = c.smsConfig;
		this.emailConfig = c.emailConfig;
		this.partnerConfig = c.partnerConfig;
		this.luceneConfig = c.luceneConfig;
		this.alipayConfig = c.alipayConfig;
		this.wechatpayConfig = c.wechatpayConfig;
		this.azureConfig = c.azureConfig;
		this.ks3Config = c.ks3Config;
		this.flowConfig = c.flowConfig;
		this.storageConfig = c.storageConfig;
	}
	public void updateCache(int id,Object obj){
		switch (id) {
		case Config.ID_文件上传配置:fileConfig = (FileConfig) obj;break;
		case Config.ID_短信配置:smsConfig = (SmsConfig) obj;break;
		case Config.ID_邮件配置:emailConfig = (EmailConfig) obj;break;
		case Config.ID_合作方地址配置:partnerConfig = (PartnerConfig) obj;break;
		case Config.ID_搜索引擎:luceneConfig = (LuceneConfig) obj;break;
		case Config.ID_支付宝配置:alipayConfig = (AlipayConfig) obj;break;
		case Config.ID_微信配置:wechatpayConfig = (WechatpayConfig) obj;break;
		case Config.ID_微软配置:azureConfig = (AzureConfig) obj;reInitStorageConfig(azureConfig,null);break;
		case Config.ID_金山云配置:ks3Config = (Ks3Config) obj;reInitStorageConfig(null,ks3Config);break;
		case Config.ID_流量统计:flowConfig = (FlowConfig) obj;break;
		case Config.ID_存储使用平台:storageConfig = (StorageConfig)obj;break;
		default:
			break;
		}
	}
	private void reInitStorageConfig(AzureConfig azureConfig,Ks3Config ks3Config){
		try {
			if(azureConfig!=null){
				MediaServiceSDK.init(azureConfig);
				StorageServiceSDK.init(azureConfig);
			}
		} catch (AzureMediaSDKException e) {
		}
		if(ks3Config!=null){
			KS3YunSDK.init(ks3Config);
		}
	}
	public LuceneConfig getLuceneConfig(ConfigService configService){
		if(luceneConfig==null){
			Config config = configService.query(Config.ID_搜索引擎);
			if(config!=null){
				luceneConfig = JSONUtil.fromJson(config.config, LuceneConfig.class);
			}else{
				config = new Config();
				config.id = Config.ID_搜索引擎;
				config.name = "搜索引擎";
				luceneConfig =new LuceneConfig();
				//partnerConfig.ROOT_PATH_KAIMAI8 = "http://192.168.1.250:8080";
				luceneConfig.DIR_URL = "E:\\code\\video0.3.0\\videoMgr\\dir";
				luceneConfig.INDEX_URL = "E:\\code\\video0.3.0\\videoMgr\\lucene\\store";
				luceneConfig.SOLR_URL = "http://192.168.1.223:8983/solr/mycollection_shard2_replica1";
				config.config = JSONUtil.toJson(luceneConfig);
				configService.add(config);
			}
		}
		return luceneConfig;
	}
	
	public PartnerConfig getPartnerConfig(ConfigService configService){
		if(partnerConfig==null){
			Config config = configService.query(Config.ID_合作方地址配置);
			if(config!=null){
				partnerConfig = JSONUtil.fromJson(config.config, PartnerConfig.class);
			}else{
				config = new Config();
				config.id = Config.ID_合作方地址配置;
				config.name = "合作方地址配置";
				partnerConfig =new PartnerConfig();
				//partnerConfig.ROOT_PATH_KAIMAI8 = "http://192.168.1.250:8080";
				partnerConfig.ROOT_PATH_KAIMAI8 = "http://www.kaimai8.com";
				partnerConfig.PARTNER_INDEX_URL="";
				partnerConfig.GET_PARTNER_PRODUCT_PAGE_URL="http://192.168.1.250:8080/SellerAdmin/product/ListProduct";
				partnerConfig.GET_PARTNER_PRODUCT_DETAIL_URL="http://192.168.1.250:8080/Product/GetSKUByProductId";
				partnerConfig.GET_VIDEOAUTH_URL="http://192.168.1.250:8080/api/member/videoauth";
				partnerConfig.GET_SELLERVIDEOAUTH_URL="http://192.168.1.250:8080/api/member/sellervideoauth";
				config.config = JSONUtil.toJson(partnerConfig);
				configService.add(config);
			}
		}
		return partnerConfig;
	}
	
	public EmailConfig getEmailConfig(ConfigService configService){
		if(emailConfig==null){
			Config config = configService.query(Config.ID_邮件配置);
			if(config!=null){
				emailConfig = JSONUtil.fromJson(config.config, EmailConfig.class);
			}else{
				config = new Config();
				config.id = Config.ID_邮件配置;
				config.name = "邮件配置";
				emailConfig =new EmailConfig();
				emailConfig.url = "http://sendcloud.sohu.com/webapi/";//接口地址
				emailConfig.apiKey = "YJLNcPHzXPCwryJ1";//key
				emailConfig.apiUser = "9cooo_api_user";//触发邮件用户名
				emailConfig.apiBatchUser="9cooo_api_user_batch";//批量邮件用户名
				emailConfig.from = "9cooo@mail.9cooo.com";//发件人地址
				emailConfig.fromname = "9库网";//发件人名称
				config.config = JSONUtil.toJson(emailConfig);
				configService.add(config);
			}
		}
		return emailConfig;
	}
	public SmsConfig getSmsConfig(ConfigService configService){
		if(smsConfig==null){
			Config config = configService.query(Config.ID_短信配置);
			if(config!=null){
				smsConfig = JSONUtil.fromJson(config.config, SmsConfig.class);
			}else{
				config = new Config();
				config.id = Config.ID_短信配置;
				config.name = "短信配置";
				smsConfig = new SmsConfig();
				smsConfig.url = "http://smsapi.c123.cn/OpenPlatform/OpenApi";//短信接口地址（验证码）
				smsConfig.ac = "1001@501176370006";//企业账号
				smsConfig.authkey = "957C2C52FD0098F494C2F7CDE801B275";//密钥
				smsConfig.cgid = "52";//通道组
				smsConfig.csid = "5550";//签名编号
				
				smsConfig.url_2 = "http://smsapi.c123.cn/OpenPlatform/OpenApi";//短信接口地址（验证码）
				smsConfig.ac_2 = "1001@501176370007";//企业账号
				smsConfig.authkey_2 = "B2BDFFDB65298F452E400215F9ED2E83";//密钥
				smsConfig.cgid_2 = "52";//通道组
				smsConfig.csid_2 = "5550";//签名编号
				
				smsConfig.oth_url =  "http://smsapi.c123.cn/OpenPlatform/OpenApi";//其他短信
				smsConfig.oth_ac = "1001@501176370004";
				smsConfig.oth_authkey = "E723ED871F6BFFDABB16F78D8AA1782C";
				smsConfig.oth_cgid = "5279";
				smsConfig.oth_csid = "5553";
				
				config.config = JSONUtil.toJson(smsConfig);
				try {
					configService.add(config);
				} catch (Exception e) {
					logger.error("addFailed"+e.getMessage(),e);
				}
			}
		}
		return smsConfig;
	}
	
	public FileConfig getFileConfig(ConfigService configService){
		logger.info("getFileConfigconfigserivce:"+configService+",fileConfig"+fileConfig);
		if(fileConfig==null){
			Config config = configService.query(Config.ID_文件上传配置);
			if(config!=null){
				fileConfig = JSONUtil.fromJson(config.config, FileConfig.class);
			}else{
				config = new Config();
				config.id = Config.ID_文件上传配置;
				config.name = "文件上传配置";
				fileConfig = new FileConfig();
				fileConfig.FILE_LOCAL_DIR = "E:/eclipse/tmpfile/"; 
				fileConfig.IMG_AZURE_DIR = "videoimg";
				fileConfig.IMG_AZURE_PAY_DIR="payimg";
				fileConfig.IMG_AZURE_PRE = "https://picazt.blob.core.chinacloudapi.cn";
				fileConfig.IM4JAVA_TOOLPATH = "D:/Program Files/GraphicsMagick-1.3.21-Q16";
				fileConfig.PAY_NOTIFY_URL="http://aepanzeurus.vicp.cc:8081/videoMgr";
				fileConfig.MAX_VIDEO_SIZE = 1024*1024*200;
				fileConfig.MAX_IMGS_SIZE = 1024*1024*3;
				fileConfig.MAX_IMG_WIDTH = 1004;
				fileConfig.MIN_IMG_WIDTH = 200;
				config.config = JSONUtil.toJson(fileConfig);
				configService.add(config);
			}
		}
		return  fileConfig;
	}
	
	
	public AlipayConfig getAlipayConfig(ConfigService configService){
		if(alipayConfig==null){
			Config config = configService.query(Config.ID_支付宝配置);
			if(config!=null){
				alipayConfig = JSONUtil.fromJson(config.config, AlipayConfig.class);
			}else{
				config = new Config();
				config.id = Config.ID_支付宝配置;
				config.name = "支付宝";
				alipayConfig =new AlipayConfig();
				config.config = JSONUtil.toJson(alipayConfig);
				configService.add(config);
			}
		}
		return alipayConfig;
	}
	
	
	public WechatpayConfig getWechatpayConfig(ConfigService configService){
		if(wechatpayConfig==null){
			Config config = configService.query(Config.ID_微信配置);
			if(config!=null){
				wechatpayConfig = JSONUtil.fromJson(config.config, WechatpayConfig.class);
			}else{
				config = new Config();
				config.id = Config.ID_微信配置;
				config.name = "微信配置";
				wechatpayConfig =new WechatpayConfig();
				config.config = JSONUtil.toJson(wechatpayConfig);
				try {
					configService.add(config);
				} catch (Exception e) {
					logger.error("addFailed"+e.getMessage(),e);
				}
			}
		}
		return wechatpayConfig;
	}
	
	public AzureConfig getAzureConfig(ConfigService configService){
		if(azureConfig==null){
			Config config = configService.query(Config.ID_微软配置);
			if(config!=null){
				azureConfig = JSONUtil.fromJson(config.config, AzureConfig.class);
			}else{
				config = new Config();
				config.id = Config.ID_微软配置;
				config.name = "微软";
				azureConfig =new AzureConfig();
				config.config = JSONUtil.toJson(azureConfig);
				configService.add(config);
			}
		}
		return azureConfig;
	}
	
	
	
	public Ks3Config getKs3Config(ConfigService configService){
		if(ks3Config==null){
			Config config = configService.query(Config.ID_金山云配置);
			if(config!=null){
				ks3Config = JSONUtil.fromJson(config.config, Ks3Config.class);
			}else{
				config = new Config();
				config.id = Config.ID_金山云配置;
				config.name = "金山云";
				ks3Config =new Ks3Config();
				config.config = JSONUtil.toJson(ks3Config);
				configService.add(config);
			}
		}
		return ks3Config;
	}
	public FlowConfig getFlowConfig() {
		return flowConfig;
	}
	public void setFlowConfig(FlowConfig flowConfig) {
		this.flowConfig = flowConfig;
	}
	public StorageConfig getStorageConfig() {
		return storageConfig;
	}
	public void setStorageConfig(StorageConfig storageConfig) {
		this.storageConfig = storageConfig;
	}
	
}
