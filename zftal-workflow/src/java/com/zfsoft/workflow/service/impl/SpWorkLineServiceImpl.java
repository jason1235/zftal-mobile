package com.zfsoft.workflow.service.impl; 
  
 import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.workflow.dao.ISpWorkLineDao;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpWorkLine;
import com.zfsoft.workflow.service.ISpWorkLineService;
  
 /**  
  * 工作审核节点连线管理接口实现类
  * @version 3.2.0  
  */  
 public class SpWorkLineServiceImpl extends BaseInterfaceServiceImpl implements ISpWorkLineService { 
 	/* @model: 注入SpLine */ 
	 public ISpWorkLineDao spWorkLineDao;

	 	public void setSpWorkLineDao(ISpWorkLineDao spWorkLineDao) { 
	 		this.spWorkLineDao = spWorkLineDao; 
	 	}

	@Override
	public void addSpWorkLine(SpWorkLine spWorkLine) throws DataAccessException {
		try{
			spWorkLineDao.addSpWorkLine(spWorkLine);
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public List<SpWorkLine> findWorkLineList(SpWorkLine spWorkLine) throws DataAccessException {
		try{
			if(spWorkLine != null){
				return spWorkLineDao.findWorkLineList(spWorkLine);
			}
		}catch (Exception e){
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

 } 
 