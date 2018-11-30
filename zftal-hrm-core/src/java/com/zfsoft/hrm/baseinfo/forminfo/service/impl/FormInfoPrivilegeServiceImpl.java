package com.zfsoft.hrm.baseinfo.forminfo.service.impl;

import java.util.List;

import com.zfsoft.hrm.baseinfo.forminfo.dao.daointerface.IFormInfoPrivilegeDao;
import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoPrivilege;
import com.zfsoft.hrm.baseinfo.forminfo.service.svcinterface.IFormInfoPrivilegeService;

public class FormInfoPrivilegeServiceImpl implements IFormInfoPrivilegeService {
	
	private IFormInfoPrivilegeDao formInfoPrivilegeDao;

	@Override
	public void add(FormInfoPrivilege model) {
		formInfoPrivilegeDao.insert(model);
	}


	@Override
	public void remove(FormInfoPrivilege model) {
		formInfoPrivilegeDao.delete(model);
	}

	@Override
	public List<FormInfoPrivilege> getListByRole(String roleId) {
		return formInfoPrivilegeDao.findListByRole(roleId);
	}

	@Override
	public FormInfoPrivilege getEntity(String roleId, String classId,
			String opType) {
		return formInfoPrivilegeDao.findEntity(roleId,classId,opType);
	}
	
	public void setFormInfoPrivilegeDao(IFormInfoPrivilegeDao formInfoPrivilegeDao) {
		this.formInfoPrivilegeDao = formInfoPrivilegeDao;
	}
	
}
