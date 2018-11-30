package com.zfsoft.hrm.baseinfo.role.service;

import java.util.List;

import com.zfsoft.hrm.baseinfo.role.entity.Role;
import com.zfsoft.hrm.baseinfo.role.query.RoleQuery;

/** 
 * @author jinjj
 * @date 2012-10-8 下午01:31:21 
 *  
 */
public interface IRoleService {

	/**
	 * 获取所有角色信息(除admin)
	 * @return
	 */
	public List<Role> getList(RoleQuery query);
	
}
