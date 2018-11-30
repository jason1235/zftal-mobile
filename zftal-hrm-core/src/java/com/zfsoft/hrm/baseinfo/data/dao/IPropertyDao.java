package com.zfsoft.hrm.baseinfo.data.dao;


import java.util.List;

import com.zfsoft.hrm.baseinfo.data.entity.Property;

public interface IPropertyDao {
	/**
	 * 方法描述：属性列表
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 */
	public List<Property> getPagedProperty(Property property);
	/**
	 * 删除模版属性
	 */
	public void delete(String id);
	/**
	 * 增加模版属性
	 */
	public void insert(Property property);
	/**
	 * 根据模版id获取该模版的所有字段的字段名称属性,返回值String
	 */
	public List<String> getZdmc(String mbid);
	/**
	 *  根据模版id获取该模版的所有字段信息
	 */
	public List<Property> getProperties(String mbid);
}
