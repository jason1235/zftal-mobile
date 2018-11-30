package com.zfsoft.hrm.infochange.query;

import java.util.Date;

import com.zfsoft.dao.query.BeanQueryV1;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.infochange.entity.InfoChange;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
/** 
 * @author Patrick Shen
 * @date 2013-6-9 下午02:22:08 
 */
public class InfoChangeQuery extends BeanQueryV1{
	private Boolean onwer=true;
	private String id;
	private String userId;
	private String username;
	private Date createDate;
	private Date auditDate;
	private Date commitDate;
	private String classId;
	private WorkNodeStatusEnum status;
	private String billInstanceId;
	private Date screateDate;
	private Date ecreateDate;
	private String express;
	private String express2;
	private String depId;
	
	private String orderStr;
	
	public Boolean getOnwer() {
		return onwer;
	}
	public void setOnwer(Boolean onwer) {
		this.onwer = onwer;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public InfoChangeQuery(){
		this.setQueryClass(InfoChange.class);
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the auditDate
	 */
	public Date getAuditDate() {
		return auditDate;
	}
	/**
	 * @param auditDate the auditDate to set
	 */
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	/**
	 * @return the commitDate
	 */
	public Date getCommitDate() {
		return commitDate;
	}
	/**
	 * @param commitDate the commitDate to set
	 */
	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}
	/**
	 * @return the status
	 */
	public WorkNodeStatusEnum getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(WorkNodeStatusEnum status) {
		this.status = status;
	}
	/**
	 * @return the billInstanceId
	 */
	public String getBillInstanceId() {
		return billInstanceId;
	}
	/**
	 * @param billInstanceId the billInstanceId to set
	 */
	public void setBillInstanceId(String billInstanceId) {
		this.billInstanceId = billInstanceId;
	}
	/**
	 * @return the screateDate
	 */
	public Date getScreateDate() {
		return screateDate;
	}
	/**
	 * @param screateDate the screateDate to set
	 */
	public void setScreateDate(Date screateDate) {
		this.screateDate = screateDate;
	}
	/**
	 * @return the ecreateDate
	 */
	public Date getEcreateDate() {
		return ecreateDate;
	}
	/**
	 * @param ecreateDate the ecreateDate to set
	 */
	public void setEcreateDate(Date ecreateDate) {
		this.ecreateDate = ecreateDate;
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
	public String getExpress2() {
		return express2;
	}
	public void setExpress2(String express2) {
		this.express2 = express2;
	}
	/**
	 * 返回
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置
	 * @param username 
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getDepartmentListString() {
		if(StringUtil.isEmpty(depId)){
			return "";
		}
		String departmentList= "'"+depId+"'";
		for (Item item:CodeUtil.getChildren(ICodeConstants.DM_DEF_ORG,depId))
		{
			departmentList += ",'"+item.getGuid()+"'";
		};
		return departmentList;
	}
	
	public String getDepId() {
		return depId;
	}
	
	public void setDepId(String depId) {
		this.depId = depId;
	}
	public String getOrderStr() {
		return orderStr;
	}
	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}
	
}
