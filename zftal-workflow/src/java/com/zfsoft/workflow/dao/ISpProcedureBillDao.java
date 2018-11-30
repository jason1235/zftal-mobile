package com.zfsoft.workflow.dao; 
  
 import java.util.List;

import com.zfsoft.workflow.exception.DaoException;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpProcedureBill;
  
 /**  
  *  
  * 类描述：节点表单管理接口类
  *
  * @version: 1.0
  * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
  * @since: 2013-4-12 上午10:49:08
  */
 public interface ISpProcedureBillDao { 
 	/* @interface model: 添加一条SpProcedureBill记录 */ 
 	void insert(SpProcedureBill spProcedureBill) throws DaoException; 
 	
 	/* @interface model: 修改一条SpProcedureBill记录 */ 
 	void update(SpProcedureBill spProcedureBill) throws DaoException;
  
 	/* @interface model: 删除一条SpProcedureBill记录 */ 
 	void delete(String pid,String billId) throws DaoException; 
 	
 	/* @interface model: 删除节点下所有SpProcedureBill记录 */ 
 	void deleteByPId(String pid) throws DaoException; 
 	
 	/* @interface model: 根据节点ID查询SpProcedureBill结果集,返回SpProcedureBill对象的集合 */ 
 	List <SpProcedureBill> findProcedureBillListByPId(String pid) throws DaoException;
 	
 	/**
 	 * 
 	 * 方法描述：根据流程ID得到提交申报类型的表单集合
 	 *
 	 * @param: 
 	 * @return: 
 	 * @version: 1.0
 	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 	 * @since: 2013-4-25 上午10:32:51
 	 */
 	List <SpProcedureBill> findCommitBillListByPIdAndBillType(String pid) throws DaoException;
 	
 	/**
 	 * 
 	 * 方法描述：根据流程ID得到审批类型的表单集合
 	 *
 	 * @param: 
 	 * @return: 
 	 * @version: 1.0
 	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 	 * @since: 2013-4-25 上午10:32:28
 	 */
 	List <SpProcedureBill> findApproveBillListByPIdAndBillType(String pid) throws DaoException;
 	
 	/**
 	 * 
 	 * 方法描述：根据流程ID和表单类ID统计记录数量
 	 *
 	 * @param: 
 	 * @return: 
 	 * @version: 1.0
 	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 	 * @since: 2013-4-25 上午10:28:26
 	 */
	int getCountByPIdAndClassId(SpProcedureBill spProcedureBill) throws WorkFlowException;
 } 
 