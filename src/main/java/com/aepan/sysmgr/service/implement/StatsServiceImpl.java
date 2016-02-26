/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aepan.sysmgr.dao.StatDao;
import com.aepan.sysmgr.service.StatsService;

/**
 * @author rakika
 * 2015年9月12日下午2:51:01
 */
@Service
public class StatsServiceImpl implements StatsService {
	
	@Autowired
    StatDao statsdao;
	
	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.StatsService#statsVideo(java.lang.String, int, java.lang.String)
	 */
	@Override
	public boolean statsVideo(String videoId, int type, String sharePlat) {
		
		return statsdao.statsVideo(videoId, type, sharePlat);
	}

	/* (non-Javadoc)
	 * @see com._aepan.sysmgr.service.StatsService#statsProduct(java.lang.String, int, java.lang.String)
	 */
	@Override
	public boolean statsProduct(String productId, int type, String sharePlat) {
		
		return statsdao.statsProduct(productId, type, sharePlat);
	}

}
