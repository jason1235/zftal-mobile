package com.zfsoft.hrm.manoeuvre.configInfo.query;

import java.util.Date;
import java.util.List;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.entities.ManoeuvreInfo;
import com.zfsoft.orcus.lang.TimeUtil;

/**
 * 审核状态信息query
 * @author yongjun.fang
 *
 */
public class AuditStatusQuery extends BaseQuery {

	private static final long serialVersionUID = 4615178861786838085L;

	private String sid;//状态信息编号
	
	private ManoeuvreInfo manoeuvreInfo;//人员调动信息
	
	private String taskNodeName;//所属审核环节名称
	
	private String result;//审核结果
	
	private List<String> resultList;//审核结果集
	
	private String opinion;//审核意见
	
	private Date auditTime;//审核时间
	
	private Date auditTimeMin;//审核时间下限
	
	private Date auditTimeMax;//审核时间上限
	
	private String assessor;//审核人
	
	private String remark;//备注
	
	private String sortCol;//排序条件

	/**
	 * 返回
	 * @return 
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * 设置
	 * @param sid 
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}

	

	/**
	 * 返回
	 * @return 
	 */
	public String getResult() {
		return result;
	}

	/**
	 * 设置
	 * @param result 
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * 返回
	 * @return 
	 */
	public List<String> getResultList() {
		return resultList;
	}

	/**
	 * 设置
	 * @param resultList 
	 */
	public void setResultList(List<String> resultList) {
		this.resultList = resultList;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getOpinion() {
		return opinion;
	}

	/**
	 * 设置
	 * @param opinion 
	 */
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	/**
	 * 返回
	 * @return 
	 */
	public Date getAuditTime() {
		return auditTime;
	}

	/**
	 * 设置
	 * @param auditTime 
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	/**
	 * 返回
	 * @return 
	 */
	public Date getAuditTimeMin() {
		return auditTimeMin;
	}

	/**
	 * 设置
	 * @param auditTimeMin 
	 */
	public void setAuditTimeMin(Date auditTimeMin) {
		this.auditTimeMin = auditTimeMin;
	}

	/**
	 * 返回
	 * @return 
	 */
	public Date getAuditTimeMax() {
		return auditTimeMax;
	}

	/**
	 * 设置
	 * @param auditTimeMax 
	 */
	public void setAuditTimeMax(Date auditTimeMax) {
		this.auditTimeMax = auditTimeMax;
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
	
	public String getAuditTimeText() {
		return TimeUtil.format(auditTime, "yyyy-MM-dd HH:mm:ss");
	}
	
	public String getAuditTimeMinText() {
		return TimeUtil.format(auditTimeMin, "yyyy-MM-dd HH:mm:ss");
	}
	
	public String getAuditTimeMaxText() {
		return TimeUtil.format(auditTimeMax, "yyyy-MM-dd HH:mm:ss");
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
	public ManoeuvreInfo getManoeuvreInfo() {
		return manoeuvreInfo;
	}

	/**
	 * 设置
	 * @param manoeuvreInfo 
	 */
	public void setManoeuvreInfo(ManoeuvreInfo manoeuvreInfo) {
		this.manoeuvreInfo = manoeuvreInfo;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getTaskNodeName() {
		return taskNodeName;
	}

	/**
	 * 设置
	 * @param taskNodeName 
	 */
	public void setTaskNodeName(String taskNodeName) {
		this.taskNodeName = taskNodeName;
	}

}
