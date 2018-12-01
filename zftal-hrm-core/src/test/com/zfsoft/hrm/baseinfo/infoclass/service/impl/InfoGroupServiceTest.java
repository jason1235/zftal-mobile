package com.zfsoft.hrm.baseinfo.infoclass.service.impl;

import base.BaseTxTestCase;

import com.zfsoft.hrm.baseinfo.infoclass.dao.daointerface.IInfoGroupConditionDao;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoGroupCondition;

public class InfoGroupServiceTest extends BaseTxTestCase{
	
	private IInfoGroupConditionDao infoGroupDao;
	
	public void getAllInfoGroup(){
		infoGroupDao.findList();
	}
	
	public void removeInfoGroup(String guid){
		infoGroupDao.delete(guid);
	}
	
	public void addInfoGroup(InfoGroupCondition infoGroupCondition){
		infoGroupDao.insert(infoGroupCondition);
	}
	
	public void modifyInfoGroup(InfoGroupCondition infoGroupCondition){
		infoGroupDao.update(infoGroupCondition);
	}

	public void getInfoGroupById(String guid) {
		infoGroupDao.findByGuid(guid);
	}
}
