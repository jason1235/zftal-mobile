package com.zfsoft.hrm.staffturn.leaveschool.entities;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.query.QueryModel;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.util.date.DateTimeUtil;

/** 
 * 离校管理实体
 * @author jinjj
 * @date 2012-8-2 上午06:25:57 
 *  
 */
public class LeaveFlowInfo extends QueryModel implements Serializable {

	private static final long serialVersionUID = -8654836067828149854L;

	private String accountId;
	
	private String type;
	
	private Date leaveDate;
	
	private String salaryStatus;
	
	private String leaveStatus;
	
	private String processDept;
	
	private String comment;
	
	private DynaBean dynaBean;
	
	private boolean processStatus = false;
	
	private String remark;
	
	// 20140422 add start
	private String bzzkCd; // 编制状况代码
	// 20140422 add end

	private String lxqx;   //离校取向
	
	public String getLeaveStatusStr(){
		if(leaveStatus == "1"){
			return "已处理";
		}else
			return "未处理";
	}
	/**
	 * 员工号
	 * @return
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * 员工号
	 * @param accountId
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * 离校类型
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 离校类型
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 离校时间
	 * @return
	 */
	public Date getLeaveDate() {
		return leaveDate;
	}

	public String getLeaveDateString(){
		return DateTimeUtil.getFormatDate(leaveDate, "yyyy-MM-dd");
	}
	
	/**
	 * 离校时间
	 * @param leaveDate
	 */
	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	/**
	 * 薪水结清状态
	 * @return
	 */
	public String getSalaryStatus() {
		return salaryStatus;
	}

	/**
	 * 薪水结清状态
	 * @param salaryStatus
	 */
	public void setSalaryStatus(String salaryStatus) {
		this.salaryStatus = salaryStatus;
	}

	/**
	 * 离校状态
	 * @return
	 */
	public String getLeaveStatus() {
		return leaveStatus;
	}

	/**
	 * 离校状态
	 * @param leaveStatus
	 */
	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	/**
	 * 处理结果
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 处理结果
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * 处理部门
	 * @return
	 */
	public String getProcessDept() {
		return processDept;
	}

	/**
	 * 处理部门
	 * @param processDept
	 */
	public void setProcessDept(String processDept) {
		this.processDept = processDept;
	}

	/**
	 * OVERALL人员信息
	 * @return
	 */
	public DynaBean getDynaBean() {
		return dynaBean;
	}

	/**
	 * OVERALL人员信息
	 * @param dynaBean
	 */
	public void setDynaBean(DynaBean dynaBean) {
		this.dynaBean = dynaBean;
	}
	
	public boolean isProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(boolean processStatus) {
		this.processStatus = processStatus;
	}
	
	/**
	 * 备注
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTypeValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_RETIRD_REASON, getType());
		if(StringUtils.isEmpty(str)){
			return getType();
		}
		return str;
	}
	
	public String getProcessDeptValue(){
		if(StringUtils.isEmpty(getProcessDept())){
			return getProcessDept();
		}
		String[] depts = getProcessDept().split(",");
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<depts.length;i++){
			String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, depts[i].trim());
			if(sb.length()>0){
				sb.append(",");
			}
			if(!StringUtils.isEmpty(str)){
				sb.append(str);
			}else{
				sb.append(depts[i]);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 职务（包含管理，技术，工勤）废弃
	 * 职务（现任行政职务）
	 * @return
	 */
	public String getDuty(){
		StringBuilder sb = new StringBuilder();
		//appendViewParse("gbzwmcdm", sb);
		//appendViewParse("przyjszw", sb);
		//appendViewParse("gjzyzgdm", sb);
		appendViewParse("xrzw", sb);
		return sb.toString();
	}
	
	private void appendViewParse(String column,StringBuilder sb){
		String value = dynaBean.getViewHtml().get(column);
		if(StringUtils.isEmpty(value)){
			return;
		}
		if(sb.length()>0){
			sb.append(",");
		}
		sb.append(value);
	}

	// 20140422 add start
	/**
	 * @return the bzzkCd
	 */
	public String getBzzkCd() {
		return bzzkCd;
	}

	/**
	 * @param bzzkCd the bzzkCd to set
	 */
	public void setBzzkCd(String bzzkCd) {
		this.bzzkCd = bzzkCd;
	}
	// 20140422 add end
	public String getLxqx() {
		return lxqx;
	}
	public void setLxqx(String lxqx) {
		this.lxqx = lxqx;
	}
}
