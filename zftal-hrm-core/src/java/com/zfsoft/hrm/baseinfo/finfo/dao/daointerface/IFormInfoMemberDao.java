package com.zfsoft.hrm.baseinfo.finfo.dao.daointerface;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMember;
import com.zfsoft.hrm.baseinfo.finfo.query.FormInfoMemberQuery;

/**
 * 信息维护组成成员数据操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-22
 * @version V1.0.0
 */
public interface IFormInfoMemberDao {
	
	/**
	 * 查询符合条件的组成成员列表
	 * @param query 查询条件
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public FormInfoMember[] findList( FormInfoMemberQuery query ) throws DataAccessException;
	
	/**
	 * 统计查询符合条件的组成成员数量
	 * @param query 查询条件
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public long findCount( FormInfoMemberQuery query ) throws DataAccessException;
	
	/**
	 * 插入信息维护组成成员的描述信息
	 * @param bean 信息维护组成成员的描述信息
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void insert( FormInfoMember bean ) throws DataAccessException;
	
	/**
	 * 更新信息维护组成成员的描述信息
	 * @param bean 信息维护组成成员的描述信息
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void update( FormInfoMember bean ) throws DataAccessException;
	
	/**
	 * 更新组成成员的开发状态
	 * @param member 组成成员信息 
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void updateOpen( FormInfoMember member ) throws DataAccessException;
	
	/**
	 * 更新组成成员的排序索引
	 * @param member 组成成员的描述信息
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void updateIndex( FormInfoMember member ) throws DataAccessException;
	
	/**
	 * 向上移动组成成员后面所有成员的序号（向上移动一位）
	 * @param member 组成成员
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void upIndex( FormInfoMember member ) throws DataAccessException;
	
	/**
	 * 删除组成成员
	 * @param member 删除的组成成员
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void delete( FormInfoMember member ) throws DataAccessException;
}
