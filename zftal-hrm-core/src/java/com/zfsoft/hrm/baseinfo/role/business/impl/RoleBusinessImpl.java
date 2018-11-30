package com.zfsoft.hrm.baseinfo.role.business.impl;

import java.util.List;

import com.zfsoft.hrm.baseinfo.role.business.IRoleBusiness;
import com.zfsoft.hrm.baseinfo.role.dao.IRoleDao;
import com.zfsoft.hrm.baseinfo.role.entity.Role;
import com.zfsoft.hrm.baseinfo.role.query.RoleQuery;

/** 
 * @author jinjj
 * @date 2012-10-8 下午12:01:18 
 *  
 */
public class RoleBusinessImpl implements IRoleBusiness {

	private IRoleDao roleDao;
	
	@Override
	public List<Role> getList(RoleQuery query) {
		return roleDao.getList(query);
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

}
