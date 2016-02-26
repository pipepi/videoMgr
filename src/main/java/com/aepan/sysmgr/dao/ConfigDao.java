/**
 * 
 */
package com.aepan.sysmgr.dao;

import java.util.List;

import com.aepan.sysmgr.model.config.Config;

/**
 * @author lanker
 * 2015年8月10日上午10:53:34
 */
public interface ConfigDao {
	void add(Config c) ;
	Config query(int id);
	void update(Config c);
	/**
	 * @return
	 */
	List<Config> queryAll();

}
