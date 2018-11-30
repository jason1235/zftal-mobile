package com.zfsoft.service.impl;

import java.util.List;

import com.zfsoft.common.log.Subsystem;
import com.zfsoft.dao.daointerface.SubsystemDao;
import com.zfsoft.service.svcinterface.SubsystemService;
/**
 * 子系统操作service
 * 
 * @author gonghui
 * 2014-5-15
 */
public class SubsystemServiceImpl implements SubsystemService {

	private SubsystemDao subsystemDao;
	
	@Override
	public List<Subsystem> queryAllEnabledAndDefaultOrder() {
		Subsystem query=new Subsystem();
		query.setEnabled("1");
		query.setOrderStr("ORDER BY SEQ");
		return subsystemDao.findList(query);
	}
	
	
	public SubsystemDao getSubsystemDao() {
		return subsystemDao;
	}
	
	public void setSubsystemDao(SubsystemDao subsystemDao) {
		this.subsystemDao = subsystemDao;
	}

}
