package com.zfsoft.workflow.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.dybill.dao.ISpBillConfigDao;
import com.zfsoft.hrm.dybill.enums.BillType;
import com.zfsoft.hrm.dybill.enums.ModeType;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.dao.ISpNodeBillDao;
import com.zfsoft.workflow.dao.ISpNodeDao;
import com.zfsoft.workflow.dao.ISpNodeTaskDao;
import com.zfsoft.workflow.dao.ISpProcedureDao;
import com.zfsoft.workflow.exception.DaoException;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpNode;
import com.zfsoft.workflow.model.SpNodeBill;
import com.zfsoft.workflow.model.SpNodeTask;
import com.zfsoft.workflow.model.SpTask;
import com.zfsoft.workflow.service.ISpNodeService;

/**
 * 节点管理接口实现类
 * 
 * @version 3.2.0
 */
public class SpNodeServiceImpl extends SpAuditingLogServiceImpl implements ISpNodeService {
	/* @model: 注入SpNode */
	public ISpNodeDao spNodeDao;
	public ISpNodeTaskDao spNodeTaskDao;
	public ISpNodeBillDao spNodeBillDao;
	public ISpProcedureDao spProcedureDao;
	public ISpBillConfigDao spBillConfigDao;

	@Override
	public void insert(SpNode spNode, Map<String, String[]> map) {
		if (map != null && map.size() > 0) {
			String[] taskIds = map.get("taskIds");
			String[] commitBillClassIds = map.get("commitBillClassIds");
			String[] approveBillClassIds = map.get("approveBillClassIds");
			String[] commitClassPrivilege = map.get("commitClassPrivilege");
			String[] approveClassPrivilege = map.get("approveClassPrivilege");
			String[] musts = map.get("musts");
			String[] autos = map.get("autos");
			String[] pushTaskIds = map.get("pushTaskIds");
			spNodeDao.insert(spNode);
			this.addTask(spNode, taskIds, musts, autos, pushTaskIds);
			this.addBill(spNode, commitBillClassIds, commitClassPrivilege, approveBillClassIds, approveClassPrivilege);
		}
	}

	@Override
	public void insert(SpNode spNode) throws WorkFlowException {
		try {
			int result = spNodeDao.getCountByNodeNameAndPid(spNode);
			if (result > 0) {
				throw new WorkFlowException("同一流程相同节点名称的记录已经存在，不能执行新增操作！");
			} else {
				spNodeDao.insert(spNode);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void update(SpNode spNode, Map<String, String[]> map) {
		try {
			if (map != null && map.size() > 0) {
				String[] taskIds = map.get("taskIds");
				String[] commitBillClassIds = map.get("commitBillClassIds");
				String[] approveBillClassIds = map.get("approveBillClassIds");
				String[] commitClassPrivilege = map.get("commitClassPrivilege");
				String[] approveClassPrivilege = map.get("approveClassPrivilege");
				String[] musts = map.get("taskIsMusts");
				String[] autos = map.get("taskIsAutos");
				String[] pushTaskIds = map.get("pushTaskIds");
				spNodeDao.update(spNode);
				this.addTask(spNode, taskIds, musts, autos, pushTaskIds);
				this.addBill(spNode, commitBillClassIds, commitClassPrivilege, approveBillClassIds,
						approveClassPrivilege);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void update(SpNode spNode) throws WorkFlowException {
		try {
			spNodeDao.update(spNode);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(String nodeId) throws WorkFlowException {
		try {
			spNodeDao.delete(nodeId);// 删除节点
			spNodeDao.deleteTaskByNodeId(nodeId);// 删除节点下所有任务关联
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public List<SpNode> findNodeList(SpNode spNode) throws WorkFlowException {
		try {
			if (spNode != null) {
				return spNodeDao.findNodeList(spNode);
			} else {
				throw new WorkFlowException("异常：节点对象为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage());
		}
	}

	@Override
	public List<SpNode> findNodeListByDnodeId(String dNodeId) throws DataAccessException {
		try {
			if (StringUtil.isNotEmpty(dNodeId)) {
				SpNode spNode = new SpNode();
				spNode.setNodeId(dNodeId);
				return spNodeDao.findNodeList(spNode);
			} else {
				throw new WorkFlowException("异常：下节点ID为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage());
		}
	}

	@Override
	public List<SpNode> findNodeByDnodeId(String dNodeId) throws DataAccessException {
		try {
			if (StringUtil.isNotEmpty(dNodeId)) {
				SpNode spNode = new SpNode();
				spNode.setNodeId(dNodeId);
				List<SpNode> nodeList = spNodeDao.findNodeList(spNode);
				if (nodeList != null) {
					return nodeList;
				}
			} else {
				throw new WorkFlowException("异常：下节点ID为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage());
		}
		return null;
	}

	@Override
	public List<SpNode> findNodeListByUnodeId(String uNodeId) throws DataAccessException {
		try {
			if (StringUtil.isNotEmpty(uNodeId)) {
				SpNode spNode = new SpNode();
				spNode.setNodeId(uNodeId);
				return spNodeDao.findNodeList(spNode);
			} else {
				throw new WorkFlowException("异常：上节点ID为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public List<SpNode> findNodeByUnodeId(String uNodeId) throws DataAccessException {
		try {
			if (StringUtil.isNotEmpty(uNodeId)) {
				SpNode spNode = new SpNode();
				spNode.setNodeId(uNodeId);
				List<SpNode> nodeList = spNodeDao.findNodeList(spNode);
				if (nodeList != null) {
					return nodeList;
				}
			} else {
				throw new WorkFlowException("异常：上节点ID为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public void deleteByPid(String pId) throws DaoException {
		try {
			if (StringUtil.isNotEmpty(pId)) {
				spNodeDao.deleteByPid(pId);
			} else {
				throw new WorkFlowException("异常：流程ID为空！");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	// --------------------------------------------------------------------------------

	private void addBill(SpNode spNode, String[] commitBillClassIds, String[] commitClassPrivilege,
			String[] approveBillClassIds, String[] approveClassPrivilege) {
		spNodeBillDao.deleteByNodeId(spNode.getNodeId());
		SpNodeBill spNodeBill;
		// 添加提交类型的表单信息
		if (commitBillClassIds != null && commitBillClassIds.length > 0) {
			for (int i = 0; i < commitBillClassIds.length; i++) {
				spNodeBill = new SpNodeBill();
				spNodeBill.setNodeId(spNode.getNodeId());
				spNodeBill.setClassId(commitBillClassIds[i]);
				// spNodeBill.setBillId(billId);
				spNodeBill.setBillType(BillType.COMMIT.toString());
				spNodeBill.setClassModeType(ModeType.NORMAL.toString());
				spNodeBill.setClassesPrivilege(commitClassPrivilege[i]);
				spNodeBillDao.insert(spNodeBill);
			}
		}
		// 添加审核类型的表单信息
		if (approveBillClassIds != null && approveBillClassIds.length > 0) {
			for (int i = 0; i < approveBillClassIds.length; i++) {
				spNodeBill = new SpNodeBill();
				spNodeBill.setNodeId(spNode.getNodeId());
				spNodeBill.setClassId(approveBillClassIds[i]);
				// spNodeBill.setBillId(billId);
				spNodeBill.setBillType(BillType.APPROVE.toString());
				spNodeBill.setClassModeType(ModeType.NORMAL.toString());
				spNodeBill.setClassesPrivilege(approveClassPrivilege[i]);
				spNodeBillDao.insert(spNodeBill);
			}
		}
	}

	private void addTask(SpNode spNode, String[] taskIds, String[] musts, String[] autos, String[] pushTaskIds) {
		spNodeTaskDao.deleteByNodeId(spNode.getNodeId());
		SpNodeTask spNodeTask;
		// 添加任务
		if (taskIds != null && taskIds.length > 0) {
			for (int i = 0; i < taskIds.length; i++) {
				spNodeTask = new SpNodeTask();
				spNodeTask.setNeed(musts[i]);
				spNodeTask.setAuto(autos[i]);
				spNodeTask.setSpNode(new SpNode());
				spNodeTask.getSpNode().setNodeId(spNode.getNodeId());
				spNodeTask.setSpTask(new SpTask());
				spNodeTask.getSpTask().setTaskId(taskIds[i]);
				spNodeTaskDao.insert(spNodeTask);
			}
		}
		// 添加任务
		if (pushTaskIds != null && pushTaskIds.length > 0) {
			for (int i = 0; i < pushTaskIds.length; i++) {
				spNodeTask = new SpNodeTask();
				spNodeTask.setNeed("Y");
				spNodeTask.setAuto("Y");
				spNodeTask.setSpNode(new SpNode());
				spNodeTask.getSpNode().setNodeId(spNode.getNodeId());
				spNodeTask.setSpTask(new SpTask());
				spNodeTask.getSpTask().setTaskId(pushTaskIds[i]);
				spNodeTaskDao.insert(spNodeTask);
			}
		}
	}

	@Override
	public SpNode findNodeById(String nodeId) {
		return spNodeDao.findNodeById(nodeId);
	}

	public void setSpNodeTaskDao(ISpNodeTaskDao spNodeTaskDao) {
		this.spNodeTaskDao = spNodeTaskDao;
	}

	public void setSpNodeDao(ISpNodeDao spNodeDao) {
		this.spNodeDao = spNodeDao;
	}

	public void setSpNodeBillDao(ISpNodeBillDao spNodeBillDao) {
		this.spNodeBillDao = spNodeBillDao;
	}

	/**
	 * @param spProcedureDao
	 *            : set the property spProcedureDao.
	 */

	public void setSpProcedureDao(ISpProcedureDao spProcedureDao) {
		this.spProcedureDao = spProcedureDao;
	}

	/**
	 * @param spBillConfigDao
	 *            : set the property spBillConfigDao.
	 */

	public void setSpBillConfigDao(ISpBillConfigDao spBillConfigDao) {
		this.spBillConfigDao = spBillConfigDao;
	}

}
