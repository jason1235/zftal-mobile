package com.zfsoft.workflow.service.impl; 
  
 import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.dao.ISpLineDao;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpLine;
import com.zfsoft.workflow.service.ISpLineService;
  
 /**  
  * 节点管理接口实现类
  * @version 3.2.0  
  */  
 public class SpLineServiceImpl extends BaseInterfaceServiceImpl implements ISpLineService { 
 	/* @model: 注入SpLine */ 
 	public ISpLineDao spLineDao;

 	public void setSpLineDao(ISpLineDao spLineDao) { 
 		this.spLineDao = spLineDao; 
 	}
 	
	@Override
	public void insert(SpLine spLine) throws WorkFlowException {
		try{
			int result = spLineDao.getCountByUnodeIdAndDnodeId(spLine);
			if(result > 0){
				throw new WorkFlowException("异常：相同的上节点和下节点的连线记录已经存在，不能执行新增操作！");
			}else{
				spLineDao.insert(spLine);
			}
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(String spLineId) throws WorkFlowException {
		try{
			spLineDao.delete(spLineId);//删除节点连线
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteLineByNodeId(String nodeId) throws WorkFlowException {
		try{
			spLineDao.deleteLineByNodeId(nodeId);//根据节点ID删除所有节点连线
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void updateOrder(String unodeId, String dnodeId)
			throws WorkFlowException {
		try{
			if(StringUtil.isEmpty(dnodeId)){
				throw new WorkFlowException("异常：下节点ID为空！");
			}
			if(StringUtil.isEmpty(unodeId)){
				throw new WorkFlowException("异常：上节点ID为空！");
			}
			SpLine spLine = new SpLine();
			spLine.setDnodeId(dnodeId);
			spLine.setUnodeId(unodeId);
			spLineDao.updateOrder(spLine);//调整节点连线位置
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void updateExpression(String expression) throws WorkFlowException {
		try{
			spLineDao.updateExpression(expression);//修改连线表达式
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public int getCountByUNodeIdAndDNodeId(String uNodeId, String dNodeId)
			throws WorkFlowException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SpLine> findLineList(SpLine spLine) throws WorkFlowException {
		try{
			if(spLine != null){
				return spLineDao.findLineList(spLine);
			}else{
				throw new WorkFlowException("异常：连线对象为空！");
			}
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public List<SpLine> findLineListByUnodeId(String uNodeId)
			throws DataAccessException {
		try{
			if(StringUtil.isNotEmpty(uNodeId)){
				SpLine spLine = new SpLine();
				spLine.setUnodeId(uNodeId);
				return spLineDao.findLineList(spLine);
			}else{
				throw new WorkFlowException("异常：上节点ID为空！");
			}
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public SpLine findLineByUNodeId(String uNodeId) throws DataAccessException {
		try{
			if(StringUtil.isNotEmpty(uNodeId)){
				SpLine spLine = new SpLine();
				spLine.setUnodeId(uNodeId);
				List<SpLine> lineList = spLineDao.findLineList(spLine);
				if(lineList != null){
					return lineList.get(0);
				}
			}else{
				throw new WorkFlowException("异常：上节点ID为空！");
			}
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<SpLine> findLineListByDnodeId(String dNodeId)
			throws DataAccessException {
		try{
			if(StringUtil.isNotEmpty(dNodeId)){
				SpLine spLine = new SpLine();
				spLine.setDnodeId(dNodeId);
				return spLineDao.findLineList(spLine);
			}else{
				throw new WorkFlowException("异常：下节点ID为空！");
			}
		}catch (Exception e){
			
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public SpLine findLineByDNodeId(String dNodeId) throws DataAccessException {
		try{
			if(StringUtil.isNotEmpty(dNodeId)){
				SpLine spLine = new SpLine();
				spLine.setDnodeId(dNodeId);
				List<SpLine> lineList = spLineDao.findLineList(spLine);
				if(lineList != null){
					return lineList.get(0);
				}
			}else{
				throw new WorkFlowException("异常：下节点ID为空！");
			}
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<SpLine> findLineListByPid(String pId) throws WorkFlowException {
		try{
			if(StringUtil.isNotEmpty(pId)){
				SpLine spLine = new SpLine();
				spLine.setPid(pId);
				return spLineDao.findLineList(spLine);
			}else{
				throw new WorkFlowException("异常：下节点ID为空！");
			}
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public SpLine findLineListById(String lineId) {
		return spLineDao.findLineListById(lineId);
	}

	@Override
	public void update(SpLine spLine) {
		spLineDao.update(spLine);
	}

	@Override
	public void insertLineListAfresh(String pid, List<SpLine> spLineList)
			throws WorkFlowException {
		spLineDao.deleteLineByPId(pid);
		for (SpLine spLine : spLineList) {
			spLine.setPid(pid);
			spLineDao.insert(spLine);
		}
		
	}


  
	
 } 
 