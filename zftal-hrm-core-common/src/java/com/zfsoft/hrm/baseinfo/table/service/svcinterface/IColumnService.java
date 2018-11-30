package com.zfsoft.hrm.baseinfo.table.service.svcinterface;

import com.zfsoft.hrm.baseinfo.table.entities.Column;
import com.zfsoft.hrm.baseinfo.table.exception.TableException;

/**
 * 数据库表字段操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-6
 * @version V1.0.0
 */
public interface IColumnService {

	/**
	 * 增加字段信息
	 * @param column 字段信息
	 * @throws TableException 如果操作出现异常
	 */
	public void add(Column column) throws TableException;
	/**
	 * 增加字段信息
	 * @param column 字段信息
	 * @param createIndex 是否同步创建索引
	 * @throws TableException 如果操作出现异常
	 */
	public void add(Column column,boolean createIndex) throws TableException;
	
	/**
	 * 重命名字段名字
	 * @param column 字段信息
	 * @throws TableException 如果操作出现异常
	 */
	public void rename(Column column) throws TableException;
	
	/**
	 * 修改字段
	 * <p>
	 * 修改字段包含：字段类型，默认值
	 * </p>
	 * @param column 修改的字段信息
	 * @throws TableException 修改字段信息
	 */
	public void modify(Column column) throws TableException;
	
	/**
	 * 删除字段
	 * @param column 删除的字段信息
	 * @throws TableException 如果操作出现异常
	 */
	public void drop(Column column) throws TableException;
	
	/**
	 * 注释字段信息
	 * @param column 字段信息
	 * @throws TableException 如果操作出现异常
	 */
	public void comment(Column column) throws TableException;
	
	/**
	 * 判断表字段是否存在
	 * @param column 表字段信息
	 * @return
	 * @throws TableException 如果操作出现异常
	 */
	public boolean isExist(Column column) throws TableException;
	
}
