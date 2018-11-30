package com.zfsoft.hrm.baseinfo.role.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.role.entity.Role;
import com.zfsoft.hrm.baseinfo.role.query.RoleQuery;
import com.zfsoft.hrm.baseinfo.role.service.IRoleService;
import com.zfsoft.hrm.common.HrmAction;

/** 
 * @author jinjj
 * @date 2012-10-8 下午01:35:49 
 *  
 */
public class RoleAction extends HrmAction {

	private static final long serialVersionUID = 199267773754716272L;

	private IRoleService roleService;
	private RoleQuery query = new RoleQuery();
	
	private List<Role> roleList;
	
	public String list() throws Exception{
		roleList = roleService.getList(query);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("result", roleList);
		getValueStack().set(DATA, map);
		return DATA;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	
}
