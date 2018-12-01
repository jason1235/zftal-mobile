package com.zfsoft.hrm.baseinfo.role.business;

import java.util.List;

import com.zfsoft.hrm.baseinfo.role.entity.Role;
import com.zfsoft.hrm.baseinfo.role.query.RoleQuery;

/** 
 * 角色信息
 * @author jinjj
 * @date 2012-10-8 上午11:57:00 
 *  
 */
public interface IRoleBusiness {

	/**
	 * 获取所有角色信息(除admin)
	 * @return
	 */
	public List<Role> getList(RoleQuery query);
}
