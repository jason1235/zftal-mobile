package com.zfsoft.hrm.baseinfo.infoclass.service.impl;

import java.util.List;

import com.zfsoft.hrm.baseinfo.infoclass.dao.daointerface.IInfoGroupConditionDao;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoGroupCondition;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoGroupConditionService;

/**
 * 信息类组合条件服务
 * 
 * @author 沈鲁威
 * @since 2012-7-14
 * @version V1.0.0
 */
public class InfoGroupConditionServiceImpl implements
		IInfoGroupConditionService {
	private IInfoGroupConditionDao infoGroupConditionDao;

	public void setInfoGroupConditionDao(
			IInfoGroupConditionDao infoGroupConditionDao) {
		this.infoGroupConditionDao = infoGroupConditionDao;
	}

	@Override
	public List<InfoGroupCondition> getAllInfoGroupCondition() {
		return infoGroupConditionDao.findList();
	}

	@Override
	public void removeInfoGroupCondition(String guid) {
		infoGroupConditionDao.delete(guid);
	}

	@Override
	public void addInfoGroupCondition(InfoGroupCondition infoGroupCondition) {
		infoGroupConditionDao.insert(infoGroupCondition);
	}

	@Override
	public void modifyInfoGroupCondition(InfoGroupCondition infoGroupCondition) {
		infoGroupConditionDao.update(infoGroupCondition);
	}

	@Override
	public InfoGroupCondition getInfoGroupConditionById(String guid) {
		return infoGroupConditionDao.findByGuid(guid);
	}
}
