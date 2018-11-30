package com.zfsoft.dataprivilege.service;

import java.util.List;

import com.zfsoft.dataprivilege.dto.AbstractFilter;
import com.zfsoft.dataprivilege.entity.DataPrivilege;

/**
 * 数据范围服务
 * @author Patrick Shen
 */
public interface IDataPrivilegeService {
	/**
	 * 获取自定义的过滤对象
	 * @param filter
	 * @return
	 */
	public AbstractFilter getValueObject(AbstractFilter filter);
	/**
	 * 保存过滤对象
	 * @param filter
	 */
	public void saveValue(AbstractFilter filter);
	/**
	 * 删除过滤对象
	 * @param filter
	 */
	public void deleteDataPrivilege(AbstractFilter filter);
	/**
	 * 获取数据范围的数据库存储对象
	 * @param query
	 * @return
	 */
	public DataPrivilege getDataPrivilegeById(DataPrivilege query); 
	/**
	 * 获取数据范围的数据库存储对象
	 * @param query
	 * @return
	 */
	public List<DataPrivilege> getDataPrivilege(DataPrivilege query); 
}
