package com.zfsoft.workflow.dao;

import java.util.List;

import com.zfsoft.workflow.exception.DaoException;
import com.zfsoft.workflow.model.SpProcedure;
import com.zfsoft.workflow.model.query.SpProcedureQuery;

/**
 * 流程管理dao接口类
 * 
 * @version 3.2.0
 */
public interface ISpProcedureDao {
	/* @interface model: 添加一条SpProcedure记录 */
	void insert(SpProcedure spProcedure) throws DaoException;

	/* @interface model: 更新一条SpProcedure记录 */
	void update(SpProcedure spProcedure) throws DaoException;

	/* @interface model: 删除一条SpProcedure记录 */
	void delete(String pId) throws DaoException;

	/* @interface model: 根据流程ID删除关联的节点和任务记录 */
	void deleteTaskByPid(String pId) throws DaoException;

	/* @interface model: 根据条件查询是否存在相同名称和业务类型的流程记录 */
	int getCountByProcedureNameAndPtype(SpProcedure spProcedure) throws DaoException;
	
	/* @interface model: 根据业务编码查询流程对象（一个业务对应一个流程） */
	SpProcedure findSpProcedureByBCode(String bCode) throws DaoException;
	/* @interface model: 根据业务编码查询流程对象（一个业务对应一个流程） */
	SpProcedure spProcedure(String pid) throws DaoException;
	
	/* @interface model: 查询所有SpProcedure结果集,返回SpProcedure对象的集合 */
	List<SpProcedure> findSpProcedureList(SpProcedure spProcedure) throws DaoException;

	SpProcedure findSpProcedureById(String pid);

	int getPagingCount(SpProcedureQuery query);
	/* @interface model: 分页查询所有SpProcedure结果集,返回SpProcedure对象的集合 */
	List<SpProcedure> getPagingList(SpProcedureQuery query);
	
}
