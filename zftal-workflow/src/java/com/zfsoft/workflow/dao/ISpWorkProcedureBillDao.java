package com.zfsoft.workflow.dao; 
  
 import java.util.List;

import com.zfsoft.workflow.exception.DaoException;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpWorkProcedureBill;
  
 /**  
  *  
  * 类描述：流程表单管理接口类
  *
  * @version: 1.0
  * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
  * @since: 2013-4-12 上午10:49:08
  */
 public interface ISpWorkProcedureBillDao { 
 	/* @interface model: 添加一条SpWorkProcedureBill记录 */ 
 	void insert(SpWorkProcedureBill spWorkProcedureBill) throws DaoException; 
  
 	/* @interface model: 删除一条SpWorkProcedureBill记录 */ 
 	void delete(String pid,String billId) throws DaoException; 
 	
 	/* @interface model: 删除节点下所有SpWorkProcedureBill记录 */ 
 	void deleteByProcedureId(String pid) throws DaoException; 
 	
 	/* @interface model: 根据节点ID查询SpWorkProcedureBill结果集,返回SpWorkProcedureBill对象的集合 */ 
 	List <SpWorkProcedureBill> findWorkProcedureBillListByPId(String pid) throws DaoException;
 	
 	/**
 	 * 
 	 * 方法描述：根据流程ID和工作ID获取流程表单集合
 	 *
 	 * @param: 
 	 * @return: 
 	 * @version: 1.0
 	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 	 * @since: 2013-4-25 上午10:26:46
 	 */
 	List <SpWorkProcedureBill> findWorkProcedureBillListByPIdAndWid(String pid, String wid) throws DaoException;
 	
 	/**
 	 * 
 	 * 方法描述：根据流程ID获取提交类型的表单
 	 *
 	 * @param: 
 	 * @return: 
 	 * @version: 1.0
 	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 	 * @since: 2013-4-25 上午10:25:08
 	 */
 	List <SpWorkProcedureBill> findWorkCommitBillListByPIdAndBillType(String pid) throws DaoException;
 	
 	/**
 	 * 
 	 * 方法描述：根据流程ID获取审批类型的表单
 	 *
 	 * @param: 
 	 * @return: 
 	 * @version: 1.0
 	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 	 * @since: 2013-4-25 上午10:25:28
 	 */
 	List <SpWorkProcedureBill> findWorkApproveBillListByPIdAndBillType(String pid) throws DaoException;
 	
 	/**
 	 * 
 	 * 方法描述：根据流程ID和表单类ID统计记录数量
 	 *
 	 * @param: 
 	 * @return: 
 	 * @version: 1.0
 	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 	 * @since: 2013-4-25 上午10:26:01
 	 */
	int getCountByPIdAndClassId(SpWorkProcedureBill spWorkProcedureBill) throws WorkFlowException;
 } 
 