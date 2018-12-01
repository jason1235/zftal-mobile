package com.zfsoft.hrm.manoeuvre.manoeuvreInfo.entities;

import java.io.Serializable;
import java.util.Date;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditStatus;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.TaskNode;
import com.zfsoft.orcus.lang.TimeUtil;


public class ManoeuvreInfo implements Serializable {

	private static final long serialVersionUID = -3890585282127038945L;

	private String guid;			//信息编号
	
	private TaskNode currentNode;	//当前环节
	
	private String currentNodeId;	//当前环节代码
	
	private String staffid;			//申请人 
	
	private Date applyTime;			//申请时间
	
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
	
	private Date excuteTime;		//调配执行时间
	
	private String executeStatus;	//执行状态
	
	private String remark; 			//备注
	
	private Date lastModifyTime;	//最后修改时间
	
	private String manoeuvreType;	//调配类别
	
	private String oldFormationType; //前编制类型
	
	private String formationType; //编制类别
	
	private Date changeTime;    //变更 时间
	
	public static final String EXCUTESTATUS_WAITING = "0";//执行状态——待执行
	
	public static final String EXCUTESTATUS_EXCUTING = "1";//执行状态——执行中
	
	public static final String EXCUTESTATUS_FINISHED = "2";//执行状态——已执行
	
	//调动类型
	private String ddlx;
	
	
//------------------------------------------------------------------------------------
	
	public String getOldFormationType() {
		return oldFormationType;
	}

	public String getDdlx() {
		return ddlx;
	}

	public void setDdlx(String ddlx) {
		this.ddlx = ddlx;
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
	public String getApplyTimeText(){
		return TimeUtil.format(applyTime, "yyyy-MM-dd");
	}
	
	// 20140506 add start
	public String getChangeTimeText(){
		return TimeUtil.format(changeTime, "yyyy-MM-dd");
	}
	// 20140506 add end
	
	public String getExcuteTimeText(){
		return TimeUtil.format(excuteTime, "yyyy-MM-dd");
	}
	
	public String getLastModifyTimeText(){
		return TimeUtil.format(lastModifyTime, "yyyy-MM-dd HH:mm:ss");
	}
	
	public DynaBean getPersonInfo(){
		DynaBean result = DynaBeanUtil.getPerson(staffid);
		
		if( result == null ) {
			result = new DynaBean( InfoClassCache.getOverallInfoClass() );
		}
		
		return result;
	}
	
	public String getAuditResultText(){
		if(AuditStatus.FINALRESULT_PASS.equals(auditResult)){
			return "通过";
		}
		if(AuditStatus.FINALRESULT_UNPASS.equals(auditResult)){
			return "未通过";
		}
		return "";
	} 
	
	public String getOrgText(){
		return CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, getPersonInfo().getViewHtml().get("dwm"));
	}
	
	public String getPostText(){
		return CodeUtil.getItemValue(ICodeConstants.DM_DEF_WORKPOST, getPersonInfo().getViewHtml().get("rzgwm"));
	}
	
	public String getPostTypeText(){
		return CodeUtil.getItemValue(ICodeConstants.DM_DEF_WORKPOST, getPersonInfo().getViewHtml().get("gwlb"));
	}
	
	public String getCurrentOrgText(){
		return CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, currentOrg);
	}
	
	public String getPlanOrgText(){
		return CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, planOrg);
	}
	
	public String getCurrentPostText(){
		return CodeUtil.getItemValue(ICodeConstants.DM_POSTINFO, currentPost);
	}
	
	public String getPlanPostText(){
		return CodeUtil.getItemValue(ICodeConstants.DM_POSTINFO, planPost);
	}
	
	public String getCurrentPostTypeText(){
		return CodeUtil.getItemValue(ICodeConstants.DM_DEF_WORKPOST, currentPostType);
	}
	
	public String getPlanPostTypeText(){
		return CodeUtil.getItemValue(ICodeConstants.DM_DEF_WORKPOST, planPostType);
	}
	
	public String getManoeuvreTypeText(){
		return CodeUtil.getItemValue(ICodeConstants.DM_XB_RYDPLB, manoeuvreType);
	}
	public String getDdlxText(){
		return CodeUtil.getItemValue(ICodeConstants.DM_DEF_DDLX, ddlx);
	}
	
	
	public String getExecuStatusText(){
		if(EXCUTESTATUS_WAITING.equals(executeStatus)){
			return "待执行";
		}
		if(EXCUTESTATUS_EXCUTING.equals(executeStatus)){
			return "执行中";
		}
		if(EXCUTESTATUS_FINISHED.equals(executeStatus)){
			return "已执行";
		}
		return "审核完毕"; 
	}
	
    public String getOldFormationTypeText() {
		return CodeUtil.getItemValue(ICodeConstants.AUTH_TYPE, oldFormationType);
	}
    
    public String getFormationTypeText() {
		return CodeUtil.getItemValue(ICodeConstants.AUTH_TYPE, formationType);
	}
//------------------------------------------------------------------------------------
	
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

	/**
	 * 返回
	 * @return 
	 */
	public String getCurrentNodeId() {
		return currentNodeId;
	}

	/**
	 * 设置
	 * @param currentNodeId 
	 */
	public void setCurrentNodeId(String currentNodeId) {
		this.currentNodeId = currentNodeId;
	}
	
	
}
