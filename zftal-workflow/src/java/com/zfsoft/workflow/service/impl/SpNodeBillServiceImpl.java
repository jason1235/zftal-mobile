package com.zfsoft.workflow.service.impl; 
  
 import java.util.List;

import com.zfsoft.workflow.dao.ISpNodeBillDao;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpNodeBill;
import com.zfsoft.workflow.service.ISpNodeBillService;
  
 /**  
  * 
  * 类描述：节点动态表单关联接口实现类
  *
  * @version: 1.0
  * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
  * @since: 2013-4-22 上午11:39:45
  */
 public class SpNodeBillServiceImpl extends BaseInterfaceServiceImpl implements ISpNodeBillService { 
 	/* @model: 注入SpNodeBill */ 
 	public ISpNodeBillDao spNodeBillDao;

 	public void setSpNodeBillDao(ISpNodeBillDao spNodeBillDao) { 
 		this.spNodeBillDao = spNodeBillDao; 
 	}
 	
	@Override
	public void insert(SpNodeBill spNodeBill) throws WorkFlowException {
		try{
			spNodeBillDao.insert(spNodeBill);
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(String nodeId,String billId) throws WorkFlowException {
		try{
			spNodeBillDao.delete(nodeId, billId);//删除节点
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteByNodeId(String nodeId) throws WorkFlowException {
		try{
			spNodeBillDao.deleteByNodeId(nodeId);//根据节点ID删除所有节点节点表单关联关系
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.zfsoft.workflow.service.ISpNodeBillService#findNodeBillListByNodeId(java.lang.String)
	 */
	
	@Override
	public List<SpNodeBill> findNodeBillListByNodeId(String unodeId) throws WorkFlowException {
		try{
			if(unodeId != null){
				return spNodeBillDao.findNodeBillListByNodeId(unodeId);
			}else{
				throw new WorkFlowException("异常：节点表单关联关系对象为空！");
			}
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

 } 
 