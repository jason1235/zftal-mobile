package com.zfsoft.workflow.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpWorkProcedure;

/**
 * 工作审核流程管理service接口类
 * 
 * @version 3.2.0
 */
public interface ISpWorkProcedureService extends BaseInterface {

	/* @interface model: 添加一条SpWorkProcedure记录 */
	void addSpWorkProcedure(SpWorkProcedure spWorkProcedure) throws WorkFlowException;
	
	/* @interface model: 查询SpWorkProcedure结果,返回SpWorkProcedure对象 */
	SpWorkProcedure findWorkProcedureByWId(String wId) throws WorkFlowException;
	
	/* @interface model: 删除SpWorkProcedure记录 */
	boolean removeSpWorkProcedureByWid(String wId) throws DataAccessException;

	/* @interface model: 查询SpWorkProcedure结果集,返回SpWorkProcedure对象的集合 */
	List<SpWorkProcedure> findWorkProcedureList(SpWorkProcedure spWorkProcedure) throws WorkFlowException;
}
