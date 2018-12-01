package com.zfsoft.workflow.model.query;

import java.io.Serializable;

/**
 * 
 * 类描述：工作审核任务信息查询对象
 * 
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-3-16 下午03:28:18
 */
public class WorkTaskQuery extends BaseQuery implements Serializable {

	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = 6811844529192340357L;
	
	private String taskType;// 任务类型（必填）
	private String status;// 执行状态（待执行；已执行）（必填）
	/**
	 * @return taskType : return the property taskType.
	 */
	
	public String getTaskType() {
		return taskType;
	}
	/**
	 * @param taskType : set the property taskType.
	 */
	
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	/**
	 * @return status : return the property status.
	 */
	
	public String getStatus() {
		return status;
	}
	/**
	 * @param status : set the property status.
	 */
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}
