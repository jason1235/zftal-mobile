package com.zfsoft.workflow.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.dao.ISpWorkNodeDao;
import com.zfsoft.workflow.dao.ISpWorkTaskDao;
import com.zfsoft.workflow.enumobject.WorkNodeEStatusEnum;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpWorkNode;
import com.zfsoft.workflow.model.SpWorkTask;
import com.zfsoft.workflow.service.ISpWorkNodeService;

/**
 * 工作审核节点管理接口实现类
 * 
 * @version 3.2.0
 */
public class SpWorkNodeServiceImpl extends SpAuditingLogServiceImpl implements ISpWorkNodeService {
	/* @model: 注入SpNode */
	public ISpWorkNodeDao spWorkNodeDao;
	public ISpWorkTaskDao spWorkTaskDao;

	public void setSpWorkNodeDao(ISpWorkNodeDao spWorkNodeDao) {
		this.spWorkNodeDao = spWorkNodeDao;
	}

	/**
	 * @param spWorkTaskDao
	 *            : set the property spWorkTaskDao.
	 */

	public void setSpWorkTaskDao(ISpWorkTaskDao spWorkTaskDao) {
		this.spWorkTaskDao = spWorkTaskDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zfsoft.workflow.service.ISpWorkNodeService#editSpWorkNode(com.zfsoft
	 * .workflow.model.SpWorkNode)
	 */
	@Override
	public void editSpWorkNode(SpWorkNode spWorkNode) throws WorkFlowException {
		try {
			if (spWorkNode == null) {
				throw new WorkFlowException("异常：工作审核节点对象为空！");
			}
			spWorkNodeDao.editSpWorkNode(spWorkNode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zfsoft.workflow.service.ISpWorkNodeService#editSpWorkNodeAndTask(
	 * com.zfsoft.workflow.model.SpWorkNode)
	 */

	@Override
	public void editSpWorkNodeAndTask(SpWorkNode spWorkNode) throws WorkFlowException {
		try {
			// 更新节点对象
			this.editSpWorkNode(spWorkNode);
			// 修改任务信息
			List<SpWorkTask> spWorkTaskList = spWorkNode.getSpWorkTaskList();
			SpWorkTask spWorkTask = null;
			if (spWorkTaskList != null && spWorkTaskList.size() > 0) {
				for (Iterator<SpWorkTask> its = spWorkTaskList.iterator(); its.hasNext();) {
					spWorkTask = (SpWorkTask) its.next();
					spWorkTask.setEstatus(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey());// 执行状态
					spWorkTaskDao.editSpWorkTask(spWorkTask);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void addSpWorkNode(SpWorkNode spWorkNode) throws DataAccessException {
		try {
			if (spWorkNode == null) {
				throw new WorkFlowException("异常：工作审核节点对象为空！");
			}
			spWorkNodeDao.addSpWorkNode(spWorkNode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public List<SpWorkNode> findWorkNodeList(SpWorkNode spWorkNode) throws DataAccessException {
		try {
			if (spWorkNode != null) {
				return spWorkNodeDao.findWorkNodeList(spWorkNode);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zfsoft.workflow.service.ISpWorkNodeService#findWorkNodeListByCondition
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */

	@Override
	public List<SpWorkNode> findWorkNodeListByCondition(String bType, String bCode, String status,
			String[] roleIdArray, String auditor) throws WorkFlowException {
		String eStatus = "";
		try {
			if (roleIdArray == null || roleIdArray.length == 0) {
				throw new WorkFlowException("异常：角色ID为空！");
			}
			if (StringUtil.isEmpty(status)) {
				throw new WorkFlowException("异常：状态为空！");
			}
			if (StringUtil.isEmpty(bType)) {
				throw new WorkFlowException("异常：业务类型不能为空！");
			}
			if (StringUtil.isEmpty(auditor)) {

			}
			if (status.equals(WorkNodeStatusEnum.WAIT_AUDITING.getKey())) {
				eStatus = WorkNodeEStatusEnum.WAIT_EXECUTE.getKey();
				auditor = "";
			} else if (status.equals(WorkNodeStatusEnum.IN_AUDITING.getKey())) {
				eStatus = WorkNodeEStatusEnum.WAIT_EXECUTE.getKey();
			} else if (status.equals(WorkNodeStatusEnum.PASS_AUDITING.getKey())) {
				eStatus = "";
			} else {
				eStatus = WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey();
			}
			SpWorkNode spWorkNode = new SpWorkNode();
			spWorkNode.setBtype(bType);
			spWorkNode.setBcode(bCode);
			spWorkNode.setStatus(status);
			spWorkNode.setEstatus(eStatus);
			spWorkNode.setRoleIdArray(roleIdArray);
			spWorkNode.setAuditorId(auditor);// 操作人
			return spWorkNodeDao.findWorkNodeListByCondition(spWorkNode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public SpWorkNode findWorkNodeByWidAndNodeId(SpWorkNode spWorkNode) throws DataAccessException {
		try {
			if (spWorkNode != null) {
				return spWorkNodeDao.findWorkNodeByWidAndNodeId(spWorkNode);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

}
