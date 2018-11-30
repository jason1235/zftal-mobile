package com.zfsoft.hrm.baseinfo.table.service.svcinterface;

import com.zfsoft.hrm.baseinfo.table.entities.Table;
import com.zfsoft.hrm.baseinfo.table.exception.TableException;

/**
 * 数据表操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-6
 * @version V1.0.0
 */
public interface ITableService {
	
	/**
	 * 创建表结构
	 * @param table 表结构信息
	 * @throws TableException 如果操作出现异常
	 */
	public void create(Table table) throws TableException;
	
	/**
	 * 注视表
	 * @param table 表结构信息
	 * @throws TableException 如果操作出现异常
	 */
	public void comment(Table table) throws TableException;
	
	/**
	 * 删除表结构
	 * @param table 表实体
	 * @throws TableException 如果操作出现异常
	 */
	public void drop(Table table) throws TableException;

	/**
	 * 是否存在指定的表
	 * @param name 表名
	 * @return
	 * @throws TableException 如果操作出现异常
	 */
	public boolean isExist(String name) throws TableException;
}