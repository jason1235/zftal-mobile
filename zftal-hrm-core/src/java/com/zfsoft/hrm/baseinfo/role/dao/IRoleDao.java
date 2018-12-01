package com.zfsoft.hrm.baseinfo.role.dao;

import java.util.List;

import com.zfsoft.hrm.baseinfo.role.entity.Role;
import com.zfsoft.hrm.baseinfo.role.query.RoleQuery;

/** 
 * 角色信息
 * @author jinjj
 * @date 2012-10-8 上午11:55:26 
 *  
 */
public interface IRoleDao {

	public List<Role> getList(RoleQuery query);
}
