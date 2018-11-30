package com.zfsoft.orcus.beans.xdo;

/**
 * 实体, 该类是对存储空间中的实体的映射（如关系数据库中的表和视图）
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-27
 * @version V1.0.0
 */
public interface Entity {
	
	/**
	 * 返回所有属性的属性名（never null）
	 * 
	 * @throws XDOException 如果操作出现异常
	 */
	public String[] properties() throws XDOException;
	
	/**
	 * 返回所有可写属性的属性名（never null）
	 * 
	 * @throws XDOException 如果操作出现异常
	 */
	public String[] writeableProperties() throws XDOException;

	/**
     * 返回所有只读属性的属性名(never null)
     * 
     * @throws XDOException 如果操作失败
     */
    public String[] readonlyProperties() throws XDOException;
}
