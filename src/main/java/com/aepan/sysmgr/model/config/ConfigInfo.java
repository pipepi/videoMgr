/**
 * 
 */
package com.aepan.sysmgr.model.config;

import java.io.Serializable;

/**
 * @author lanker
 * 2015年12月14日下午4:32:12
 */
public class ConfigInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	//视频图片文件
	public AzureConfig azureConfig;
	public Ks3Config ks3Config;
	public FileConfig fileConfig;
	//传统电商
	public PartnerConfig partnerConfig;
	//邮件短信
	public EmailConfig emailConfig;
	public SmsConfig smsConfig;
	//搜索引擎
	public LuceneConfig luceneConfig;
	//支付配置
	public AlipayConfig alipayConfig;
	public WechatpayConfig wechatpayConfig;
	public String configMgrKey;
	public FlowConfig flowConfig;
	public StorageConfig storageConfig;
	
	
	public Ks3Config getKs3Config() {
		return ks3Config;
	}
	public void setKs3Config(Ks3Config ks3Config) {
		this.ks3Config = ks3Config;
	}
	public StorageConfig getStorageConfig() {
		return storageConfig;
	}
	public void setStorageConfig(StorageConfig storageConfig) {
		this.storageConfig = storageConfig;
	}
	public FlowConfig getFlowConfig() {
		return flowConfig;
	}
	public void setFlowConfig(FlowConfig flowConfig) {
		this.flowConfig = flowConfig;
	}
	public String getConfigMgrKey() {
		return configMgrKey;
	}
	public void setConfigMgrKey(String configMgrKey) {
		this.configMgrKey = configMgrKey;
	}
	public AzureConfig getAzureConfig() {
		return azureConfig;
	}
	public void setAzureConfig(AzureConfig azureConfig) {
		this.azureConfig = azureConfig;
	}
	public FileConfig getFileConfig() {
		return fileConfig;
	}
	public void setFileConfig(FileConfig fileConfig) {
		this.fileConfig = fileConfig;
	}
	public PartnerConfig getPartnerConfig() {
		return partnerConfig;
	}
	public void setPartnerConfig(PartnerConfig partnerConfig) {
		this.partnerConfig = partnerConfig;
	}
	public EmailConfig getEmailConfig() {
		return emailConfig;
	}
	public void setEmailConfig(EmailConfig emailConfig) {
		this.emailConfig = emailConfig;
	}
	public SmsConfig getSmsConfig() {
		return smsConfig;
	}
	public void setSmsConfig(SmsConfig smsConfig) {
		this.smsConfig = smsConfig;
	}
	public LuceneConfig getLuceneConfig() {
		return luceneConfig;
	}
	public void setLuceneConfig(LuceneConfig luceneConfig) {
		this.luceneConfig = luceneConfig;
	}
	public AlipayConfig getAlipayConfig() {
		return alipayConfig;
	}
	public void setAlipayConfig(AlipayConfig alipayConfig) {
		this.alipayConfig = alipayConfig;
	}
	public WechatpayConfig getWechatpayConfig() {
		return wechatpayConfig;
	}
	public void setWechatpayConfig(WechatpayConfig wechatpayConfig) {
		this.wechatpayConfig = wechatpayConfig;
	}
	
}
