package com.aepan.sysmgr.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;


/**
 * 配置文件读取工具类
 * 
 * @author linxw
 * @date 2014-04-12
 * 
 */
public class ConfigUtil {
	private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
	private static ConfigUtil instance = null;
	private Properties props = new Properties();

	public ConfigUtil() {
		try {
			Resource resource = new ClassPathResource("/application.properties");
			this.setProps(PropertiesLoaderUtils.loadProperties(resource));
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	} 
	
	public static ConfigUtil getInstance() {
		if(instance==null) {
			return new ConfigUtil();
		} 
		return instance;
	} 

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}
	
	
}
