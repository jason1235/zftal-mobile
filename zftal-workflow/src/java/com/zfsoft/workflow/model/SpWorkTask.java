package com.zfsoft.workflow.model;

import java.io.Serializable;
import java.util.Date;

import com.zfsoft.workflow.enumobject.WorkNodeEStatusEnum;

/**
 * 
 * 类描述：工作审核任务对象
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-5-3 下午02:17:45
 */
public class SpWorkTask extends SpTask implements Serializable {

	/* serialVersionUID: serialVersionUID */

	private static final long serialVersionUID = 1L;
	/* @property:ID */
	private String id;
	/* @property:工作ID */
	private String wid;
	/* @property:节点ID */
	private String nodeId;
	/* @property:执行状态 */
	private String estatus;
	/* @property:操作人 */
	private String operator;
	/* @property:操作时间 */
	private Date opreateTime;
	/* @property:操作结果 */
	private String result;
	/* @property:表单实例ID */
	private String instanceId;

	/* Default constructor - creates a new instance with no values set. */
	public SpWorkTask() {
	}

	/**
	 * @return id : return the property id.
	 */

	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            : set the property id.
	 */

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return wId : return the property wId.
	 */

	public String getWid() {
		return wid;
	}

	/**
	 * @param wid
	 *            : set the property wId.
	 */

	public void setWid(String wid) {
		this.wid = wid;
	}

	/**
	 * @return nodeId : return the property nodeId.
	 */

	public String getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId
	 *            : set the property nodeId.
	 */

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return eStatus : return the property eStatus.
	 */

	public String getEstatus() {
		return estatus;
	}

	/**
	 * @param eStatus
	 *            : set the property eStatus.
	 */

	public void setEstatus(String eStatus) {
		this.estatus = eStatus;
	}

	/**
	 * @return operator : return the property operator.
	 */

	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator
	 *            : set the property operator.
	 */

	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return opreateTime : return the property opreateTime.
	 */

	public Date getOpreateTime() {
		return opreateTime;
	}

	/**
	 * @param opreateTime
	 *            : set the property opreateTime.
	 */

	public void setOpreateTime(Date opreateTime) {
		this.opreateTime = opreateTime;
	}

	/**
	 * @return result : return the property result.
	 */

	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            : set the property result.
	 */

	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return instanceId : return the property instanceId.
	 */
	
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * @param instanceId : set the property instanceId.
	 */
	
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	/**
	 * 将任务对象填充到工作审核任务对象中
	 * 
	 * @param spTask
	 * @return
	 */
	public static SpWorkTask putTaskObject(SpTask spTask) {
		SpWorkTask spWorkTask = new SpWorkTask();
		if (spTask != null) {
			spWorkTask.setTaskId(spTask.getTaskId());
			spWorkTask.setTaskName(spTask.getTaskName());
			spWorkTask.setTaskDesc(spTask.getTaskDesc());
			spWorkTask.setTaskType(spTask.getTaskType());
			spWorkTask.setTaskCode(spTask.getTaskCode());
			spWorkTask.setEstatus(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey());
			spWorkTask.setIsMust(spTask.getIsMust());
			spWorkTask.setIsAuto(spTask.getIsAuto());
			spWorkTask.setBillId(spTask.getBillId());
			spWorkTask.setClassId(spTask.getClassId());
			spWorkTask.setClassModeType(spTask.getClassModeType());
			spWorkTask.setClassPrivilege(spTask.getClassPrivilege());
			spWorkTask.setExeCondition(spTask.getExeCondition());
		}
		return spWorkTask;
	}
}
