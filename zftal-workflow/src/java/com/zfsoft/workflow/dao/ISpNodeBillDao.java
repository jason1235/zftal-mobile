package com.zfsoft.workflow.dao; 
  
 import java.util.List;

import com.zfsoft.workflow.exception.DaoException;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpNodeBill;
  
 /**  
  *  
  * 类描述：节点表单管理接口类
  *
  * @version: 1.0
  * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
  * @since: 2013-4-12 上午10:49:08
  */
 public interface ISpNodeBillDao { 
 	/* @interface model: 添加一条SpNodeBill记录 */ 
 	void insert(SpNodeBill spNodeBill) throws DaoException; 
  
 	/* @interface model: 删除一条SpNodeBill记录 */ 
 	void delete(String nodeId,String billId) throws DaoException; 
 	
 	/* @interface model: 删除节点下所有SpNodeBill记录 */ 
 	void deleteByNodeId(String nodeId) throws DaoException; 
 	
 	/* @interface model: 根据节点ID查询SpNodeBill结果集,返回SpNodeBill对象的集合 */ 
 	List <SpNodeBill> findNodeBillListByNodeId(String nodeId) throws DaoException;
 	
 	List <SpNodeBill> findCommitNodeBillListByNodeId(String nodeId) throws DaoException;
 	
 	List <SpNodeBill> findApproveNodeBillListByNodeId(String nodeId) throws DaoException;
 	
	int getCountByNodeIdAndClassId(SpNodeBill spNodeBill) throws WorkFlowException;
 } 
 