package com.zfsoft.workflow.service.impl; 
  
 import java.util.List;

import com.zfsoft.workflow.dao.ISpWorkNodeBillDao;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpWorkNodeBill;
import com.zfsoft.workflow.service.ISpWorkNodeBillService;
  
 /**  
  * 
  * 类描述：审核工作节点动态表单关联接口实现类
  *
  * @version: 1.0
  * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
  * @since: 2013-4-22 上午11:39:45
  */
 public class SpWorkNodeBillServiceImpl extends BaseInterfaceServiceImpl implements ISpWorkNodeBillService { 
 	/* @model: 注入SpWorkNodeBill */ 
 	public ISpWorkNodeBillDao spWorkNodeBillDao;

 	public void setSpWorkNodeBillDao(ISpWorkNodeBillDao spWorkNodeBillDao) { 
 		this.spWorkNodeBillDao = spWorkNodeBillDao; 
 	}
 	
	@Override
	public void insert(SpWorkNodeBill spWorkNodeBill) throws WorkFlowException {
		try{
//			spWorkNodeBillDao.delete(spWorkNodeBill.getNodeId(), spWorkNodeBill.getClassId(), spWorkNodeBill.getWid());
			spWorkNodeBillDao.insert(spWorkNodeBill);
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(String nodeId,String classId, String wid) throws WorkFlowException {
		try{
			spWorkNodeBillDao.delete(nodeId,classId,wid);//删除节点
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteByNodeId(String nodeId) throws WorkFlowException {
		try{
			spWorkNodeBillDao.deleteByNodeId(nodeId);//根据节点ID删除所有节点节点表单关联关系
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.zfsoft.workflow.service.ISpWorkNodeBillService#findWorkNodeBillListByNodeId(java.lang.String)
	 */
	
	@Override
	public List<SpWorkNodeBill> findWorkNodeBillListByNodeId(String unodeId) throws WorkFlowException {
		try{
			if(unodeId != null){
				return spWorkNodeBillDao.findWorkNodeBillListByNodeId(unodeId);
			}else{
				throw new WorkFlowException("异常：节点表单关联关系对象为空！");
			}
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

 } 
 