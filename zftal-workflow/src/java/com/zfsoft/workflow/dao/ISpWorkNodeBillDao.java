package com.zfsoft.workflow.dao; 
  
 import java.util.List;

import com.zfsoft.workflow.exception.DaoException;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpWorkNodeBill;
  
 /**  
  *  
  * 类描述：节点表单管理接口类
  *
  * @version: 1.0
  * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
  * @since: 2013-4-12 上午10:49:08
  */
 public interface ISpWorkNodeBillDao { 
 	/* @interface model: 添加一条SpWorkNodeBill记录 */ 
 	void insert(SpWorkNodeBill spWorkNodeBill) throws DaoException; 
  
 	/* @interface model: 删除一条SpWorkNodeBill记录 */ 
 	void delete(String nodeId,String classId, String wid) throws DaoException; 
 	
 	/* @interface model: 删除节点下所有SpWorkNodeBill记录 */ 
 	void deleteByNodeId(String nodeId) throws DaoException; 
 	
 	/* @interface model: 根据节点ID查询SpWorkNodeBill结果集,返回SpWorkNodeBill对象的集合 */ 
 	List <SpWorkNodeBill> findWorkNodeBillListByNodeId(String nodeId) throws DaoException;
 	
 	List <SpWorkNodeBill> findWorkNodeBillListByNodeIdAndWid(String nodeId, String wid) throws DaoException;
 	
 	List <SpWorkNodeBill> findCommitWorkNodeBillListByNodeIdAndWid(String nodeId, String wid) throws DaoException;
 	
 	List <SpWorkNodeBill> findApproveWorkNodeBillListByNodeIdAndWid(String nodeId, String wid) throws DaoException;
 	
	int getCountByNodeIdAndClassId(SpWorkNodeBill spWorkNodeBill) throws WorkFlowException;
 } 
 