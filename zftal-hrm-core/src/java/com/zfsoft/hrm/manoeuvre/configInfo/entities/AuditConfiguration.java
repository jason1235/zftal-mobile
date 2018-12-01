package com.zfsoft.hrm.manoeuvre.configInfo.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.orcus.lang.TimeUtil;

/**
 * 人员调动审核环节设置信息实体类
 * @author yongjun.fang
 * 编写时间: 2012-10-23 11:11
 *
 */
public class AuditConfiguration implements Serializable {

	private static final long serialVersionUID = 2027887468584244889L;

	private String aid;//审核设置编号
	
	private TaskNode taskNode;//所属审核节点
	
	private String extension;//审核数据范围
	
	private String extensionType;// 审核类型
	
	private String assessor;//审核人
	
	private String remark;//备注
	
	private List<AuditConfigOrgInfo> auditConfigOrgList;	//审核设置部门信息列表
	
	//private boolean deleted;//是否删除
	
	//private Date modifyTime;//修改时间
	
	private String role; // 角色
	
	private String userName; // 姓名
	
	private String audittype;
	
	public String getAuditConfigOrgListText(){
		if(getAuditConfigOrgList() == null || getAuditConfigOrgList().size() == 0){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		int idx = 0;
		for (AuditConfigOrgInfo aco : getAuditConfigOrgList()){
			idx++;
			sb.append(CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, aco.getOid()));
			sb.append(",");
			if(idx >= 3)break;
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" 等部门");
		return sb.toString();
	}

	/**
	 * 返回
	 * @return 
	 */
	public List<AuditConfigOrgInfo> getAuditConfigOrgList() {
		return auditConfigOrgList;
	}

	/**
	 * 设置
	 * @param auditConfigOrgList 
	 */
	public void setAuditConfigOrgList(List<AuditConfigOrgInfo> auditConfigOrgList) {
		this.auditConfigOrgList = auditConfigOrgList;
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
	 *//*
	public boolean isDeleted() {
		return deleted;
	}

	*//**
	 * 设置
	 * @param deleted 
	 *//*
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	*//**
	 * 返回
	 * @return 
	 *//*
	public Date getModifyTime() {
		return modifyTime;
	}

	*//**
	 * 设置
	 * @param modifyTime 
	 *//*
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}*/
	
	/**
	 * 获取审核人信息
	 * @return
	 */
	public DynaBean getAssessorInfo(){
		DynaBean result = DynaBeanUtil.getPerson(assessor);
		
		if( result == null ) {
			result = new DynaBean( InfoClassCache.getOverallInfoClass() );
		}
		
		return result;
	}
	
	public String getExtensionText(){
		if("0".equals(extension)){
			return "本部门";
		}
		return "全部";
	}
	
	/*public String getModifyTimeText(){
		return TimeUtil.format(modifyTime, "yyyy-MM-dd HH:mm:ss");
	}*/

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
	
	public String getExtensionTypeText(){
		if("0".equals(extensionType)){
			return "调入审核";
		}
		else if("1".equals(extensionType)){
			return "调出审核";
		}
		else if("2".equals(extensionType)){
			return "全部审核";
		}
		return "";
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the audittype
	 */
	public String getAudittype() {
		return audittype;
	}

	/**
	 * @param audittype the audittype to set
	 */
	public void setAudittype(String audittype) {
		this.audittype = audittype;
	}

}
