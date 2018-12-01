package com.zfsoft.hrm.baseinfo.role.service.impl;

import java.util.List;

import com.zfsoft.hrm.baseinfo.role.business.IRoleBusiness;
import com.zfsoft.hrm.baseinfo.role.entity.Role;
import com.zfsoft.hrm.baseinfo.role.query.RoleQuery;
import com.zfsoft.hrm.baseinfo.role.service.IRoleService;

/** 
 * @author jinjj
 * @date 2012-10-8 下午01:33:16 
 *  
 */
public class RoleServiceImpl implements IRoleService {

	private IRoleBusiness roleBusiness;
	
	@Override
	public List<Role> getList(RoleQuery query) {
		return roleBusiness.getList(query);
	}

	public void setRoleBusiness(IRoleBusiness roleBusiness) {
		this.roleBusiness = roleBusiness;
	}

}
