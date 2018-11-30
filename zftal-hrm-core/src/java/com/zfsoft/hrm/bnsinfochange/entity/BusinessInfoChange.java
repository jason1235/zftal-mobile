package com.zfsoft.hrm.bnsinfochange.entity;

import java.util.Date;
import java.util.Map;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.orcus.lang.TimeUtil;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-17
 * @version V1.0.0
 */
@Table("HRM_BUSINESSINFO_CHANGE")
public class BusinessInfoChange extends MyBatisBean{
	@SQLField(key=true)
	private String id;
	@SQLField("user_id")
	private String userId;
	private String userName;
	private String deptId;
	@SQLField("op_type")
	private String opType="modify";
	@SQLField("bsnsclass_id")
	private String classId;
	@SQLField("create_date")
	private Date createDate;
	@SQLField("audit_date")
	private Date auditDate;
	@SQLField("commit_date")
	private Date commitDate;
	@SQLField
	private WorkNodeStatusEnum status=WorkNodeStatusEnum.INITAIL;
	@SQLField("bill_instance_id")
	private String billInstanceId;
	@SQLField("bill_config_id")
	private String billConfigId;
	
	private Map<String,String> instance;
	
	
	public String getDeptName() {
		return CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, deptId);
	}

	public String getDeptId() {
		return deptId;
	}


	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClassName(){
		return InfoClassCache.getInfoClass(classId).getName();
	}
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	/**
	 * @return the instance
	 */
	public Map<String, String> getInstance() {
		return instance;
	}

	/**
	 * @param instance the instance to set
	 */
	public void setInstance(Map<String, String> instance) {
		this.instance = instance;
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
	
	public String getCreateDateStr() {
		if(createDate==null){
			return "";
		}
		return TimeUtil.format(createDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	 * @return the auditDate
	 */
	public Date getAuditDate() {
		return auditDate;
	}
	
	public String getAuditDateStr() {
		if(auditDate==null){
			return "";
		}
		return TimeUtil.format(auditDate, "yyyy-MM-dd HH:mm:ss");
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
	
	public String getCommitDateStr() {
		if(commitDate==null){
			return "";
		}
		return TimeUtil.format(commitDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @param commitDate the commitDate to set
	 */
	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
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

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getBillConfigId() {
		return billConfigId;
	}

	public void setBillConfigId(String billConfigId) {
		this.billConfigId = billConfigId;
	}
}
