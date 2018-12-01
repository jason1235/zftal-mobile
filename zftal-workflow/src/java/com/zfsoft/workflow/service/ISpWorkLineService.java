package com.zfsoft.workflow.service;

import java.util.List;

import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpWorkLine;

/**
 * 工作审核节点连线管理service接口类
 * 
 * @version 3.2.0
 */
public interface ISpWorkLineService extends BaseInterface {

	/* @interface model: 添加一条SpWorkLine记录 */
	void addSpWorkLine(SpWorkLine spWorkLine) throws WorkFlowException;

	/* @interface model: 查询SpWorkLine结果集,返回SpWorkLine对象的集合 */
	List<SpWorkLine> findWorkLineList(SpWorkLine spWorkLine) throws WorkFlowException;
}
