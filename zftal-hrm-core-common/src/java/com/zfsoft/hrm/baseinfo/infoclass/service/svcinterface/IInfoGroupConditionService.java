package com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoGroupCondition;

/**
 * 信息类组合条件服务接口
 * 
 * @author 沈鲁威
 * @since 2012-7-14
 * @version V1.0.0
 */
public interface IInfoGroupConditionService {
	/**
	 * 获取所有组合条件配置信息
	 * 
	 * @return
	 */
	public List<InfoGroupCondition> getAllInfoGroupCondition();

	/**
	 * 通过组合条件Id获取组合条件项目
	 * 
	 * @param guid
	 * @return
	 */
	public InfoGroupCondition getInfoGroupConditionById(String guid);

	/**
	 * 移除对应ID的组合条件
	 * 
	 * @param guid
	 */
	public void removeInfoGroupCondition(String guid);

	/**
	 * 添加组合条件
	 * 
	 * @param infoGroupCondition
	 */
	public void addInfoGroupCondition(InfoGroupCondition infoGroupCondition);

	/**
	 * 修改组合条件
	 * 
	 * @param infoGroupCondition
	 */
	public void modifyInfoGroupCondition(InfoGroupCondition infoGroupCondition);
}
