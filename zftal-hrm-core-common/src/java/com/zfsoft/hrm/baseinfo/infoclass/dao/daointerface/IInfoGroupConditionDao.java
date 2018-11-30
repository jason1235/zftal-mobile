package com.zfsoft.hrm.baseinfo.infoclass.dao.daointerface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoGroupCondition;

/**
 * 信息类组合条件操作接口
 * @author 沈鲁威
 * @since 2012-7-14
 * @version V1.0.0
 */
public interface IInfoGroupConditionDao {
	/**
	 * 查询所有的信息类组合条件
	 * @return
	 */
	public List<InfoGroupCondition> findList();
	/**
	 * 通过ID查询信息类组合条件
	 * @param guid
	 * @return
	 */
	public InfoGroupCondition findByGuid(String guid);
	/**
	 * 插入一条信息类组合条件
	 * @param infoGroupCondition
	 */
	public void insert(InfoGroupCondition infoGroupCondition);
	/**
	 * 更新一条信息类组合条件
	 * @param infoGroupCondition
	 */
	public void update(InfoGroupCondition infoGroupCondition);
	/**
	 * 通过Id删除一条信息类组合条件
	 * @param guid
	 */
	public void delete(String guid);
}
