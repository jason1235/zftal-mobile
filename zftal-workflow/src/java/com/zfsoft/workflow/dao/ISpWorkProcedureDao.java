package com.zfsoft.workflow.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.workflow.model.SpWorkProcedure;

/**
 * 
 * 类描述：工作审核流程接口类
 * 
 * @author: <a href="mailto:fanyingjie@126.com">范英杰</a>
 * @since: 2013-3-12 下午05:39:33
 * @version: 1.0
 */
public interface ISpWorkProcedureDao {
	/* @interface model: 添加一条SpWorkProcedure记录 */
	void addSpWorkProcedure(SpWorkProcedure spWorkProcedure)
			throws DataAccessException;

	/* @interface model: 删除一条SpWorkProcedure记录 */
	void removeSpWorkProcedureById(String id) throws DataAccessException;

	/* @interface model: 删除SpWorkProcedure记录 */
	void removeSpWorkProcedureByWid(String wid) throws DataAccessException;

	/* @interface model: 查询SpWorkProcedure结果集,返回SpWorkProcedure对象的集合 */
	List<SpWorkProcedure> findWorkProcedureList(SpWorkProcedure spWorkProcedure)
			throws DataAccessException;
}
