package com.zfsoft.hrm.manoeuvre.manoeuvreInfo.query;

import java.util.Date;
import java.util.List;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfiguration;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.TaskNode;
import com.zfsoft.orcus.lang.TimeUtil;

public class ManoeuvreQuery extends BaseQuery {

	private static final long serialVersionUID = -1580655215866307488L;
	
	private String guid;			//信息编号
	
	private TaskNode currentNode;	//当前环节
	
	private String staffid;			//申请人 
	
	private boolean wideStaffid;	//是否将申请人条件作为模糊查询
	
	private String name;			//申请人姓名
	
	private Date applyTime;			//申请时间
	
	private Date applyTimeMin;		//申请时间下限
	
	private Date applyTimeMax;		//申请时间上限
	
	private String currentOrg;		//所在部门
	
	private String planOrg;			//拟调入部门
	
	private String currentPost;		//当前岗位
	
	private String planPost;		//拟调入岗位
	
	private String currentPostType;		//当前岗位
	
	private String planPostType;		//拟调入岗位
	
	private String reason;			//调配原因
	
	private boolean createdByHR;	//是否由人事处创建
	
	private boolean beenDeclared;	//是否已提交
	
	private boolean finishAudit; 	//是否完成审核
	
	private String auditResult;		//最终审核结果
	
	private List<String> auditResultList;//审核结果集
	
	private Date excuteTime;		//调配执行时间
	
	private Date excuteTimeMin;		//调配执行时间下限
	
	private Date excuteTimeMax;		//调配执行时间上限
	
	private String executeStatus;		//执行状态
	
	private List<String> executeStatusList;//执行状态集合
	
	private String remark; 			//备注
	
	private Date lastModifyTime;	//最后修改时间
	
	private Date lastModifyTimeMin;	//最后修改时间下限
	
	private Date lastModifyTimeMax;	//最后修改时间上限
	
	private String sortCol;			//排序字段
	
	private String selfOrg;			//自己的工号
	
	private boolean useCreatedByHR;	//使用"是否由人事处创建"
	
	private boolean useBeenDeclared;//使用"是否已提交"
	
	private boolean useFinishAudit; //使用"是否完成审核"
	
	//private boolean manoeuvreIn;	//查询时是否使用调入部门匹配当前部门
	
	//private boolean useManoeuvreIn;	//是否使用manoeuvreIn
	
	private String manoeuvreType;	//调配类别
	
    private String oldFormationType; //前编制类型
	
	private String formationType; //编制类别
	
	private Date changeTime;    //变更 时间
	
	private Date changeDateStart;//变更时间上限
	
	private Date changeDateEnd; //变更时间下限
	
	private List<AuditConfiguration> auditConfigurationList; //审核设置集
	
	private String express;	
	
	public ManoeuvreQuery() {
		setPerPageSize(20);
	}
	
	public String getApplyTimeText(){
		return TimeUtil.format(applyTime, "yyyy-MM-dd");
	}
	
	public String getApplyTimeMinText(){
		return TimeUtil.format(applyTimeMin, "yyyy-MM-dd");
	}
	
	public String getApplyTimeMaxText(){
		return TimeUtil.format(applyTimeMax, "yyyy-MM-dd");
	}
	
	public String getExcuteTimeText(){
		return TimeUtil.format(excuteTime, "yyyy-MM-dd");
	}
	
	public String getExcuteTimeMinText(){
		return TimeUtil.format(excuteTimeMin, "yyyy-MM-dd");
	}
	
	public String getExcuteTimeMaxText(){
		return TimeUtil.format(excuteTimeMax, "yyyy-MM-dd");
	}
	
	public String getLastModifyTimeText(){
		return TimeUtil.format(lastModifyTime, "yyyy-MM-dd HH:mm:ss");
	}
	
	public String getLastModifyTimeMinText(){
		return TimeUtil.format(lastModifyTimeMin, "yyyy-MM-dd HH:mm:ss");
	}
	
	public String getLastModifyTimeMaxText(){
		return TimeUtil.format(lastModifyTimeMax, "yyyy-MM-dd HH:mm:ss");
	}



	/**
	 * 返回
	 * @return 
	 */
	public String getCurrentPostType() {
		return currentPostType;
	}

	/**
	 * 设置
	 * @param currentPostType 
	 */
	public void setCurrentPostType(String currentPostType) {
		this.currentPostType = currentPostType;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getPlanPostType() {
		return planPostType;
	}

	/**
	 * 设置
	 * @param planPostType 
	 */
	public void setPlanPostType(String planPostType) {
		this.planPostType = planPostType;
	}
	
	/**
	 * 返回
	 * @return 
	 */
	public String getGuid() {
		return guid;
	}



	/**
	 * 设置
	 * @param guid 
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}



	/**
	 * 返回
	 * @return 
	 */
	public TaskNode getCurrentNode() {
		return currentNode;
	}



	/**
	 * 设置
	 * @param currentNode 
	 */
	public void setCurrentNode(TaskNode currentNode) {
		this.currentNode = currentNode;
	}



	/**
	 * 返回
	 * @return 
	 */
	public String getStaffid() {
		return staffid;
	}



	/**
	 * 设置
	 * @param staffid 
	 */
	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}



	/**
	 * 返回
	 * @return 
	 */
	public Date getApplyTime() {
		return applyTime;
	}



	/**
	 * 设置
	 * @param applyTime 
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}



	/**
	 * 返回
	 * @return 
	 */
	public String getCurrentOrg() {
		return currentOrg;
	}



	/**
	 * 设置
	 * @param currentOrg 
	 */
	public void setCurrentOrg(String currentOrg) {
		this.currentOrg = currentOrg;
	}



	/**
	 * 返回
	 * @return 
	 */
	public String getPlanOrg() {
		return planOrg;
	}



	/**
	 * 设置
	 * @param planOrg 
	 */
	public void setPlanOrg(String planOrg) {
		this.planOrg = planOrg;
	}



	/**
	 * 返回
	 * @return 
	 */
	public String getCurrentPost() {
		return currentPost;
	}



	/**
	 * 设置
	 * @param currentPost 
	 */
	public void setCurrentPost(String currentPost) {
		this.currentPost = currentPost;
	}



	/**
	 * 返回
	 * @return 
	 */
	public String getPlanPost() {
		return planPost;
	}



	/**
	 * 设置
	 * @param planPost 
	 */
	public void setPlanPost(String planPost) {
		this.planPost = planPost;
	}



	/**
	 * 返回
	 * @return 
	 */
	public String getReason() {
		return reason;
	}



	/**
	 * 设置
	 * @param reason 
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}



	/**
	 * 返回
	 * @return 
	 */
	public boolean isCreatedByHR() {
		return createdByHR;
	}



	/**
	 * 设置
	 * @param createdByHR 
	 */
	public void setCreatedByHR(boolean createdByHR) {
		this.createdByHR = createdByHR;
	}



	/**
	 * 返回
	 * @return 
	 */
	public boolean isBeenDeclared() {
		return beenDeclared;
	}



	/**
	 * 设置
	 * @param beenDeclared 
	 */
	public void setBeenDeclared(boolean beenDeclared) {
		this.beenDeclared = beenDeclared;
	}



	/**
	 * 返回
	 * @return 
	 */
	public boolean isFinishAudit() {
		return finishAudit;
	}



	/**
	 * 设置
	 * @param finishAudit 
	 */
	public void setFinishAudit(boolean finishAudit) {
		this.finishAudit = finishAudit;
	}



	/**
	 * 返回
	 * @return 
	 */
	public String getAuditResult() {
		return auditResult;
	}



	/**
	 * 设置
	 * @param auditResult 
	 */
	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}



	/**
	 * 返回
	 * @return 
	 */
	public Date getExcuteTime() {
		return excuteTime;
	}



	/**
	 * 设置
	 * @param excuteTime 
	 */
	public void setExcuteTime(Date excuteTime) {
		this.excuteTime = excuteTime;
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
	public Date getLastModifyTime() {
		return lastModifyTime;
	}



	/**
	 * 设置
	 * @param lastModifyTime 
	 */
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
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
	public String getSelfOrg() {
		return selfOrg;
	}



	/**
	 * 设置
	 * @param selfOrg 
	 */
	public void setSelfOrg(String selfOrg) {
		this.selfOrg = selfOrg;
	}



	/**
	 * 返回
	 * @return 
	 */
	public boolean isUseCreatedByHR() {
		return useCreatedByHR;
	}



	/**
	 * 设置
	 * @param useCreatedByHR 
	 */
	public void setUseCreatedByHR(boolean useCreatedByHR) {
		this.useCreatedByHR = useCreatedByHR;
	}



	/**
	 * 返回
	 * @return 
	 */
	public boolean isUseBeenDeclared() {
		return useBeenDeclared;
	}



	/**
	 * 设置
	 * @param useBeenDeclared 
	 */
	public void setUseBeenDeclared(boolean useBeenDeclared) {
		this.useBeenDeclared = useBeenDeclared;
	}



	/**
	 * 返回
	 * @return 
	 */
	public boolean isUseFinishAudit() {
		return useFinishAudit;
	}



	/**
	 * 设置
	 * @param useFinishAudit 
	 */
	public void setUseFinishAudit(boolean useFinishAudit) {
		this.useFinishAudit = useFinishAudit;
	}





	/**
	 * 返回
	 * @return 
	 */
	public Date getApplyTimeMin() {
		return applyTimeMin;
	}





	/**
	 * 设置
	 * @param applyTimeMin 
	 */
	public void setApplyTimeMin(Date applyTimeMin) {
		this.applyTimeMin = applyTimeMin;
	}





	/**
	 * 返回
	 * @return 
	 */
	public Date getApplyTimeMax() {
		return applyTimeMax;
	}





	/**
	 * 设置
	 * @param applyTimeMax 
	 */
	public void setApplyTimeMax(Date applyTimeMax) {
		this.applyTimeMax = applyTimeMax;
	}





	/**
	 * 返回
	 * @return 
	 */
	public Date getExcuteTimeMin() {
		return excuteTimeMin;
	}





	/**
	 * 设置
	 * @param excuteTimeMin 
	 */
	public void setExcuteTimeMin(Date excuteTimeMin) {
		this.excuteTimeMin = excuteTimeMin;
	}





	/**
	 * 返回
	 * @return 
	 */
	public Date getExcuteTimeMax() {
		return excuteTimeMax;
	}





	/**
	 * 设置
	 * @param excuteTimeMax 
	 */
	public void setExcuteTimeMax(Date excuteTimeMax) {
		this.excuteTimeMax = excuteTimeMax;
	}





	/**
	 * 返回
	 * @return 
	 */
	public Date getLastModifyTimeMin() {
		return lastModifyTimeMin;
	}





	/**
	 * 设置
	 * @param lastModifyTimeMin 
	 */
	public void setLastModifyTimeMin(Date lastModifyTimeMin) {
		this.lastModifyTimeMin = lastModifyTimeMin;
	}





	/**
	 * 返回
	 * @return 
	 */
	public Date getLastModifyTimeMax() {
		return lastModifyTimeMax;
	}





	/**
	 * 设置
	 * @param lastModifyTimeMax 
	 */
	public void setLastModifyTimeMax(Date lastModifyTimeMax) {
		this.lastModifyTimeMax = lastModifyTimeMax;
	}

	/**
	 * 返回
	 * @return 
	 */
	public List<String> getAuditResultList() {
		return auditResultList;
	}

	/**
	 * 设置
	 * @param auditResultList 
	 */
	public void setAuditResultList(List<String> auditResultList) {
		this.auditResultList = auditResultList;
	}

	/**
	 * 返回
	 * @return 
	 */
	public List<AuditConfiguration> getAuditConfigurationList() {
		return auditConfigurationList;
	}

	/**
	 * 设置
	 * @param auditConfigurationList 
	 */
	public void setAuditConfigurationList(
			List<AuditConfiguration> auditConfigurationList) {
		this.auditConfigurationList = auditConfigurationList;
	}

	/**
	 * 返回
	 * @return 
	 */
	public boolean isWideStaffid() {
		return wideStaffid;
	}

	/**
	 * 设置
	 * @param wideStaffid 
	 */
	public void setWideStaffid(boolean wideStaffid) {
		this.wideStaffid = wideStaffid;
	}

	/**
	 * 返回
	 * @return 
	 *//*
	public boolean isManoeuvreIn() {
		return manoeuvreIn;
	}

	*//**
	 * 设置
	 * @param manoeuvreIn 
	 *//*
	public void setManoeuvreIn(boolean manoeuvreIn) {
		this.manoeuvreIn = manoeuvreIn;
	}

	*//**
	 * 返回
	 * @return 
	 *//*
	public boolean isUseManoeuvreIn() {
		return useManoeuvreIn;
	}

	*//**
	 * 设置
	 * @param useManoeuvreIn 
	 *//*
	public void setUseManoeuvreIn(boolean useManoeuvreIn) {
		this.useManoeuvreIn = useManoeuvreIn;
	}*/

	/**
	 * 返回
	 * @return 
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getExecuteStatus() {
		return executeStatus;
	}

	/**
	 * 设置
	 * @param executeStatus 
	 */
	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}

	/**
	 * 返回
	 * @return 
	 */
	public List<String> getExecuteStatusList() {
		return executeStatusList;
	}

	/**
	 * 设置
	 * @param executeStatusList 
	 */
	public void setExecuteStatusList(List<String> executeStatusList) {
		this.executeStatusList = executeStatusList;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getManoeuvreType() {
		return manoeuvreType;
	}

	/**
	 * 设置
	 * @param manoeuvreType 
	 */
	public void setManoeuvreType(String manoeuvreType) {
		this.manoeuvreType = manoeuvreType;
	}

	public String getOldFormationType() {
		return oldFormationType;
	}

	public void setOldFormationType(String oldFormationType) {
		this.oldFormationType = oldFormationType;
	}

	public String getFormationType() {
		return formationType;
	}

	public void setFormationType(String formationType) {
		this.formationType = formationType;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public Date getChangeDateStart() {
		return changeDateStart;
	}

	public void setChangeDateStart(Date changeDateStart) {
		this.changeDateStart = changeDateStart;
	}

	public Date getChangeDateEnd() {
		return changeDateEnd;
	}

	public void setChangeDateEnd(Date changeDateEnd) {
		this.changeDateEnd = changeDateEnd;
	}

	/**
	 * @return the express
	 */
	public String getExpress() {
		return express;
	}

	/**
	 * @param express the express to set
	 */
	public void setExpress(String express) {
		this.express = express;
	}
	
	

}
