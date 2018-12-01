package com.zfsoft.hrm.baseinfo.finfo.dao.daointerface;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMemberProperty;
import com.zfsoft.hrm.baseinfo.finfo.query.FormInfoMemberPropertyQuery;

/**
 * {@link FormInfoMemberProperty}DAO层数据操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-22
 * @version V1.0.0
 */
public interface IFormInfoMemberPropertyDao {
	
	/**
	 * 查询符合条件的成员属性集合
	 * @param query 成员属性查询条件
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public FormInfoMemberProperty[] find( FormInfoMemberPropertyQuery query ) throws DataAccessException;
	
	/**
	 * 统计符合条件的成员属性数量
	 * @param query 成员属性统计条件
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public long count( FormInfoMemberPropertyQuery query ) throws DataAccessException;
	
	/**
	 * 增加成员属性信息
	 * @param property 增加的成员属性信息
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void insert( FormInfoMemberProperty property ) throws DataAccessException;
	
	/**
	 * 修改成员属性信息
	 * @param property 修改的成员属性信息
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void update( FormInfoMemberProperty property ) throws DataAccessException;
	
	/**
	 * 修改成员属性显示状态
	 * @param property 修改的成员属性
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void updateViewable( FormInfoMemberProperty property ) throws DataAccessException;
	
	/**
	 * 修改成员属性的显示序号
	 * @param property 修改的成员属性
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void updateIndex( FormInfoMemberProperty property ) throws DataAccessException;
	
	/**
	 * 删除符合条件的成员属性
	 * @param query 删除条件
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void delete( FormInfoMemberPropertyQuery query) throws DataAccessException;

	/**
	 * 提升显示索引
	 * <p>
	 * 对指定的成员属性所属组成成员下所有的属性，且显示索引大于指定成员属性显示索引的属性显示索引提升一位
	 * </p>
	 * @param property 成员属性
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void upIndex( FormInfoMemberProperty property ) throws DataAccessException;
}
