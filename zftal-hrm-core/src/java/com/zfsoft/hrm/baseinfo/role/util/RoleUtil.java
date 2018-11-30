package com.zfsoft.hrm.baseinfo.role.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zfsoft.hrm.baseinfo.role.entity.Role;
import com.zfsoft.hrm.baseinfo.role.query.RoleQuery;
import com.zfsoft.hrm.baseinfo.role.service.IRoleService;
import com.zfsoft.common.spring.SpringHolder;

/** 
 * 角色工具类，缓存角色信息
 * @author jinjj
 * @date 2012-10-8 下午02:47:30 
 *  
 */
public class RoleUtil {

	private static Log log = LogFactory.getLog(RoleUtil.class);
	
	private static Map<String,Object> map = new HashMap<String,Object>();
	private static IRoleService roleService = (IRoleService)SpringHolder.getBean("baseRoleService");
	private static Date date;
	private static final long interal = 5*60*1000;
	
	public static Role getRole(String roleId){
		initData();
		Role role = (Role)map.get(roleId);
		return role;
	}
	
	private synchronized static void initData(){
		long now = new Date().getTime();
		if(date == null || now-date.getTime()>interal){
			map.clear();
			List<Role> list = roleService.getList(new RoleQuery());
			for(Role role : list){
				map.put(role.getGuid(), role);
			}
			
			Role system = new Role();
			system.setGuid("system");
			system.setName("系统");
			map.put(system.getGuid(), system);//内置角色
			log.debug("role info cache refresh, "+list.size()+" object loaded");
			date = new Date();
		}
	}
	
	/**
	 * 清理缓存
	 */
	public static void clear(){
		date = null;
	}
}
