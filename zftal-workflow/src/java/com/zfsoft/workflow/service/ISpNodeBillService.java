package com.zfsoft.workflow.service;

import java.util.List;

import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpNodeBill;

/**
 * 
 * 类描述：节点动态表单关联接口
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-4-22 上午11:18:31
 */
	
public interface ISpNodeBillService extends BaseInterface {
	/* @interface model: 添加一条SpNodeBill记录 */
	void insert(SpNodeBill spNodeBill) throws WorkFlowException;

	/* @interface model: 删除一条SpNodeBill记录 */
	void delete(String nodeId,String billId) throws WorkFlowException;
	
	/* @interface model: 根据节点ID删除节点连接的所有关联表单 */
	void deleteByNodeId(String nodeId) throws WorkFlowException;

	/* @interface model: 根据上节点ID查询所有SpNodeBill结果集,返回SpNodeBill对象的集合 */
	List<SpNodeBill> findNodeBillListByNodeId(String nodeId) throws WorkFlowException;
}
