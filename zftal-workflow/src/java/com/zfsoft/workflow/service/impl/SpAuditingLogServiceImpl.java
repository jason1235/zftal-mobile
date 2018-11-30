package com.zfsoft.workflow.service.impl;

import java.util.List;

import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.dao.ISpAuditingLogDao;
import com.zfsoft.workflow.enumobject.LogTypeEnum;
import com.zfsoft.workflow.enumobject.NodeTypeEnum;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpAuditingLog;
import com.zfsoft.workflow.model.SpWorkNode;
import com.zfsoft.workflow.model.SpWorkTask;
import com.zfsoft.workflow.service.ISpAuditingLogService;
import com.zfsoft.workflow.service.ISpWorkNodeService;
import com.zfsoft.workflow.service.ISpWorkTaskService;

/**
 * 
 * 类描述：审核日志接口实现类
 * 
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-3-23 下午02:24:29
 */
public class SpAuditingLogServiceImpl extends BaseInterfaceServiceImpl implements ISpAuditingLogService {
	/* @model: 注入SpAuditingLog */
	public ISpAuditingLogDao spAuditingLogDao;
	public ISpWorkNodeService spWorkNodeService;
	public ISpWorkTaskService spWorkTaskService;

	public void setSpAuditingLogDao(ISpAuditingLogDao spAuditingLogDao) {
		this.spAuditingLogDao = spAuditingLogDao;
	}

	public void setSpWorkTaskService(ISpWorkTaskService spWorkTaskService) {
		this.spWorkTaskService = spWorkTaskService;
	}

	public void setSpWorkNodeService(ISpWorkNodeService spWorkNodeService) {
		this.spWorkNodeService = spWorkNodeService;
	}

	SpAuditingLog spAuditingLog;

	@Override
	public void insertNodeLog(SpWorkNode spWorkNode) throws WorkFlowException {
		try {
			SpWorkNode workNode = null;
			if (spWorkNode != null && StringUtil.isNotEmpty(spWorkNode.getStatus()) 
					&& !WorkNodeStatusEnum.WAIT_AUDITING.getKey().equals(spWorkNode.getStatus())) {
				workNode = new SpWorkNode();
				workNode.setWid(spWorkNode.getWid());
				workNode.setNodeId(spWorkNode.getNodeId());
				SpWorkNode workNode2 = spWorkNodeService.findWorkNodeByWidAndNodeId(workNode);

				spAuditingLog = new SpAuditingLog();
				String content = "[审核环节]：" + workNode2.getNodeName() + "；";
				
				// 如果是申报型的节点不需要记录状态和意见信息
				if(!workNode2.getNodeType().equals(NodeTypeEnum.COMMIT_NODE.getKey())){
					if(StringUtil.isNotEmpty(spWorkNode.getStatus())){
						content += "[审核状态]：" + WorkNodeStatusEnum.valueOf(spWorkNode.getStatus()).getText() + "；";
					}
					if(StringUtil.isNotEmpty(spWorkNode.getSuggestion())){
						content += "[审核意见]：" + spWorkNode.getSuggestion() + "；";
					}
					if (StringUtil.isNotEmpty(spWorkNode.getAuditorId())) {
						String name = "";
						if (DynaBeanUtil.getPerson(spWorkNode.getAuditorId()) != null) {
							name = (String) DynaBeanUtil.getPerson(spWorkNode.getAuditorId()).getValue("xm");
						}
						content += "[审核人]：" + name + "；";
					}
				}			

				spAuditingLog.setLogType(LogTypeEnum.NODE_LOG.getKey());
				spAuditingLog.setWid(spWorkNode.getWid());
				spAuditingLog.setBtype(spWorkNode.getBtype());
				spAuditingLog.setOtype(workNode2.getNodeName());
				spAuditingLog.setOstatus(spWorkNode.getStatus());
				spAuditingLog.setOresult(spWorkNode.getAuditResult());
				spAuditingLog.setOcontent(content);
				spAuditingLog.setOrole(workNode2.getRoleId());// 记录节点的角色ID
				spAuditingLog.setOperator(spWorkNode.getAuditorId());
				spAuditingLog.setOsuggestion(spWorkNode.getSuggestion());
				spAuditingLogDao.insert(spAuditingLog);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage());
		}
	}

	@Override
	public void insertTaskLog(SpWorkTask spWorkTask, String warnInfo) throws WorkFlowException {
		try {
			if (spWorkTask != null) {
				SpWorkTask workTask = new SpWorkTask();
				workTask.setWid(spWorkTask.getWid());
				workTask.setTaskId(spWorkTask.getTaskId());
				workTask.setTaskCode(spWorkTask.getTaskCode());
				SpWorkTask spWorkTas2 = null;
				if (StringUtil.isNotEmpty(spWorkTask.getTaskCode()) && StringUtil.isNotEmpty(spWorkTask.getWid())) {
					spWorkTas2 = spWorkTaskService.findWorkNodeByTaskCodeAndTaskId(spWorkTask);
				}
				if (StringUtil.isNotEmpty(spWorkTask.getTaskId()) && StringUtil.isNotEmpty(spWorkTask.getWid())) {
					spWorkTas2 = spWorkTaskService.findWorkNodeByWidAndTaskId(spWorkTask);
				}
				spAuditingLog = new SpAuditingLog();
				String content = "[操作]：执行" + spWorkTas2.getTaskName() + "任务；";
				if (StringUtil.isNotEmpty(spWorkTask.getResult())) {
					content = content.concat("[内容]：" + spWorkTask.getResult() + "；");
				}
				if(StringUtil.isNotEmpty(warnInfo)){
					content += "[提醒]：（" + spWorkTask.getDecCondition() + "）" + warnInfo + "；";
				}
				spAuditingLog.setLogType(LogTypeEnum.TASK_LOG.getKey());
				spAuditingLog.setWid(spWorkTask.getWid());
				spAuditingLog.setBtype("");
				spAuditingLog.setOtype(spWorkTas2.getTaskName());
				spAuditingLog.setOstatus("");
				spAuditingLog.setOresult(spWorkTask.getResult());
				spAuditingLog.setOcontent(content);
				spAuditingLog.setOrole("");
				spAuditingLog.setOperator(spWorkTask.getOperator());
				spAuditingLog.setOsuggestion("");
				spAuditingLogDao.insert(spAuditingLog);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public List<SpAuditingLog> findAuditingLogByWid(String wId) throws WorkFlowException {
		try {
			if (wId != null && wId != "") {
				return spAuditingLogDao.findAuditingLogByWid(wId);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public void saveLog(String logType, String wId, String bType, String oType, String oStatus, String oResult,
			String oContent, String oRole, String operator) throws WorkFlowException {
		try {
			SpAuditingLog spAuditingLog = new SpAuditingLog();
			if (StringUtil.isNotEmpty(logType))
				spAuditingLog.setLogType(logType);
			if (StringUtil.isNotEmpty(wId))
				spAuditingLog.setWid(wId);
			if (StringUtil.isNotEmpty(bType))
				spAuditingLog.setBtype(bType);
			if (StringUtil.isNotEmpty(oType))
				spAuditingLog.setOtype(oType);
			if (StringUtil.isNotEmpty(oStatus))
				spAuditingLog.setOstatus(oStatus);
			if (StringUtil.isNotEmpty(oResult))
				spAuditingLog.setOresult(oResult);
			if (StringUtil.isNotEmpty(oContent))
				spAuditingLog.setOcontent(oContent);
			if (StringUtil.isNotEmpty())
				spAuditingLog.setOrole(oRole);
			if (StringUtil.isNotEmpty(operator))
				spAuditingLog.setOperator(operator);

			spAuditingLogDao.insert(spAuditingLog);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new WorkFlowException(e.getMessage(), e);
		}
	}

	@Override
	public List<SpAuditingLog> findAuditingLog(SpAuditingLog spAuditingLog) throws WorkFlowException {
		return spAuditingLogDao.findAuditingLog(spAuditingLog);
	}

}
