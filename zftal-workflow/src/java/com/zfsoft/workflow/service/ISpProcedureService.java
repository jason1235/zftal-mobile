package com.zfsoft.workflow.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.workflow.exception.DaoException;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpProcedure;
import com.zfsoft.workflow.model.query.SpProcedureQuery;

/**
 * 流程管理service接口类
 * 
 * @version 3.2.0
 */
public interface ISpProcedureService extends BaseInterface {
	/* @interface model: 添加一条SpProcedure记录 */
	void insert(SpProcedure spProcedure, String[] commitBillIds, String[] approveBillIds) throws WorkFlowException;

	/* @interface model: 更新一条SpProcedure记录 */
	void update(SpProcedure spProcedure, String[] commitBillIds, String[] approveBillIds) throws WorkFlowException;
	
	/* @interface model: 更新一条SpProcedure记录 */
	void update(SpProcedure spProcedure) throws WorkFlowException;

	/* @interface model: 删除一条SpProcedure记录 */
	void delete(String pId) throws WorkFlowException;

	/* @interface model: 根据业务编码查询流程对象（一个业务对应一个流程） */
	SpProcedure findSpProcedureByBCode(String bCode) throws DaoException;

	/* @interface model: 根据流程编码查询流程对象（一个业务对应一个流程） */
	SpProcedure findSpProcedureByPid(String pid, boolean sequence) throws DaoException;

	/* @interface model: 查询所有SpProcedure结果集,返回SpProcedure对象的集合 */
	List<SpProcedure> findSpProcedureList(SpProcedure spProcedure) throws WorkFlowException;
	
	/* @interface model: 分页查询所有SpProcedure结果集,返回SpProcedure对象的集合 */
	PageList<SpProcedure> getPagedSpProcedureList(SpProcedureQuery query) throws WorkFlowException;
}
