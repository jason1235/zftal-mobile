package com.zfsoft.hrm.file.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.file.entity.Attachement;
import com.zfsoft.hrm.file.query.AttachementQuery;

/**
 * {@link Attachement}数据操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-10-19
 * @version V1.0.0
 */
public interface IAttachementDao {

	/**
	 * 查询符合条件的记录数
	 * @param query 查询条件
	 * @return 符合条件的附件集合
	 * @throws DataAccessException 如果操作出现异常 
	 */
	public List<Attachement> findList( AttachementQuery query ) throws DataAccessException;
	
	/**
	 * 查询指定的主键ID的记录
	 * @param guid 主键ID
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public Attachement findById( String guid ) throws DataAccessException;
	
	/**
	 * 增加记录
	 * @param bean 增加的附件记录
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void insert( Attachement bean ) throws DataAccessException;
	/**
	 * 修改记录
	 * @param bean 增加的附件记录
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void update( Attachement bean ) throws DataAccessException;
	
	/**
	 * 根据主键ID进行删除
	 * @param guid 主键ID
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void deleteById( String guid ) throws DataAccessException;
	
	/**
	 * 根据表单Id进行删除
	 * @param formId 表单ID
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void deleteByFormId( String formId ) throws DataAccessException;
}
