package com.zfsoft.hrm.config;

import java.util.Map;

import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfo;
import com.zfsoft.hrm.baseinfo.role.entity.Role;
import com.zfsoft.hrm.baseinfo.role.util.RoleUtil;

/**
 * 信息维护描述信息工厂
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-22
 * @version V1.0.0
 */
public final class FormInfoFactory {
	
	private static Map<String, FormInfo> _infos;
	
	/**
	 * 设置信息维护描述信息
	 * @param infos 信息维护描述信息
	 */
	public void setInfos( Map<String, FormInfo> infos ) {
		_infos = infos;
	}
	
	/**
	 * 获取所有的信息维护描述信息
	 * @return
	 */
	public static FormInfo[] getInfos( ) {
		FormInfo[] result = new FormInfo[ _infos.size() ];
		int i = 0;
		
		for ( String key: _infos.keySet() ) {
			result[i++] = _infos.get( key );
		}
		
		return result;
	}
	
	/**
	 * 获取指定名字的信息维护描述信息
	 * @param name 信息维护描述信息的名字
	 * @return
	 */
	public static FormInfo getInfos( String name ) {
		return _infos.get( name );
	}
	
	/**
	 * 获取指定名字的信息维护描述信息
	 * @param name 信息维护描述信息的名字
	 * @return
	 */
	public static FormInfo getRoleInfos( String name ) {
		Role  r = RoleUtil.getRole(name);
		if(r == null) return null;
		FormInfo info = new FormInfo();
		info.setName(r.getGuid());
		info.setType("teacher");
		info.setComment(r.getName());
		return info;
	}
}
