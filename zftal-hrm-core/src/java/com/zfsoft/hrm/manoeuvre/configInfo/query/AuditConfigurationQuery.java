package com.zfsoft.hrm.manoeuvre.configInfo.query;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.TaskNode;

/**
 * 环节审核设置信息查询类
 * @author yongjun.fang
 *
 */
public class AuditConfigurationQuery extends BaseQuery {

	private static final long serialVersionUID = 1954339985103836513L;

	private String aid;//审核设置编号
	
	private TaskNode taskNode;//所属审核节点
	
	private String extensionType;//审核类型
	
	private String extension;//审核数据范围
	
	private String assessor;//审核人
	
	private String assessorName;//审核人姓名
	
	private String remark;//备注
	
	private String sortCol;//排序条件
	
	public AuditConfigurationQuery() {
		setPerPageSize(IConstants.COMMON_PAGE_SIZE);
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getAid() {
		return aid;
	}

	/**
	 * 设置
	 * @param aid 
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

	

	/**
	 * 返回
	 * @return 
	 */
	public String getAssessor() {
		return assessor;
	}

	/**
	 * 设置
	 * @param assessor 
	 */
	public void setAssessor(String assessor) {
		this.assessor = assessor;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置
	 * @param remark 
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getSortCol() {
		return sortCol;
	}

	/**
	 * 设置
	 * @param sortCol 
	 */
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getAssessorName() {
		return assessorName;
	}

	/**
	 * 设置
	 * @param assessorName 
	 */
	public void setAssessorName(String assessorName) {
		this.assessorName = assessorName;
	}

	/**
	 * 返回
	 * @return 
	 */
	public TaskNode getTaskNode() {
		return taskNode;
	}

	/**
	 * 设置
	 * @param taskNode 
	 */
	public void setTaskNode(TaskNode taskNode) {
		this.taskNode = taskNode;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getExtensionType() {
		return extensionType;
	}

	/**
	 * 设置
	 * @param extensionType 
	 */
	public void setExtensionType(String extensionType) {
		this.extensionType = extensionType;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * 设置
	 * @param extension 
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	
	

}
