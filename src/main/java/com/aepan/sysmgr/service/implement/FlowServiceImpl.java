/**
 * 
 */
package com.aepan.sysmgr.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aepan.sysmgr.dao.FlowDao;
import com.aepan.sysmgr.model.Flow;
import com.aepan.sysmgr.service.FlowService;

/**
 * @author lanker
 * 2015年12月23日下午3:00:03
 */
@Service
public class FlowServiceImpl implements FlowService {
	@Autowired
	private FlowDao flowDao;
	@Override
	public void add(Flow f) {
		flowDao.add(f);
	}

}
