package com.zfsoft.hrm.contract.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.query.QueryModel;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;

/**
 * 合同
 * @author
 * 2014-2-28
 */
public class Contract extends QueryModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String workNum;//职工号
	private String fullName;//姓名
	private String sex;//性别
	private String deptId;//所在部门
	private Date birthDay;//出生日期
	private Date recruitDate;//进校时间
	private String number;//合同编号
	private String type;//合同种类编码
	private int term;//合同期限
	private Date startDate;//合同起始日期
	private Date endDate; //合同终止日期
	private Date actualEndDate;  //实际终止日期
	private String overResult;//合同终止原因
	private Date probationDate;//试用期截止日期
	private Date actualProbationDate;//试用期实际结束日期
	private String regularResult;//试用期转正说明
	private Date deferDate;//延期结束日期
	private String deferResult;//延期原因
	private Date ravelDate;//解除日期
	private String ravelResult;//解除原因
	private String status;//合同状态
	private boolean disuse;//是否作废
	private boolean sign;//是否签定过
	private String remark;//备注
	private String creator;//创建人
	private String mender;
	private Date createDate;//创建日期
	private Date updateDate;
	private String condition;
	
	private CategoryConfig categoryConfig;
	
	public CategoryConfig getCategoryConfig() {
		return categoryConfig;
	}
	public void setCategoryConfig(CategoryConfig categoryConfig) {
		this.categoryConfig = categoryConfig;
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
	 * @return the workNum
	 */
	public String getWorkNum() {
		return workNum;
	}
	/**
	 * @param workNum the workNum to set
	 */
	public void setWorkNum(String workNum) {
		this.workNum = workNum;
	}
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the term
	 */
	public int getTerm() {
		return term;
	}
	/**
	 * @param term the term to set
	 */
	public void setTerm(int term) {
		this.term = term;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
//	public void setStartDate(String startDate) {
//		this.startDate = DateTimeUtil.getFormatDate(startDate);
//	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the actualEndDate
	 */
	public Date getActualEndDate() {
		return actualEndDate;
	}
	/**
	 * @param actualEndDate the actualEndDate to set
	 */
	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}
	/**
	 * @return the probationDate
	 */
	public Date getProbationDate() {
		return probationDate;
	}
	/**
	 * @param probationDate the probationDate to set
	 */
	public void setProbationDate(Date probationDate) {
		this.probationDate = probationDate;
	}
	/**
	 * @return the actualProbationDate
	 */
	public Date getActualProbationDate() {
		return actualProbationDate;
	}
	/**
	 * @param actualProbationDate the actualProbationDate to set
	 */
	public void setActualProbationDate(Date actualProbationDate) {
		this.actualProbationDate = actualProbationDate;
	}
	/**
	 * @return the deferDate
	 */
	public Date getDeferDate() {
		return deferDate;
	}
	/**
	 * @param deferDate the deferDate to set
	 */
	public void setDeferDate(Date deferDate) {
		this.deferDate = deferDate;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the disuse
	 */
	public boolean isDisuse() {
		return disuse;
	}
	/**
	 * @param disuse the disuse to set
	 */
	public void setDisuse(boolean disuse) {
		this.disuse = disuse;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * @return the mender
	 */
	public String getMender() {
		return mender;
	}
	/**
	 * @param mender the mender to set
	 */
	public void setMender(String mender) {
		this.mender = mender;
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
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSexValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.SEX, getSex());
		if(StringUtils.isEmpty(str)){
			return getSex();
		}
		return str;
	}
	/**
	 * @return the deptId
	 */
	public String getDeptId() {
		return deptId;
	}
	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, getDeptId());
		if(StringUtils.isEmpty(str)){
			return getDeptId();
		}
		return str;
	}
	/**
	 * @return the birthDay
	 */
	public Date getBirthDay() {
		return birthDay;
	}
	/**
	 * @param birthDay the birthDay to set
	 */
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	/**
	 * @return the recruitDate
	 */
	public Date getRecruitDate() {
		return recruitDate;
	}
	/**
	 * @param recruitDate the recruitDate to set
	 */
	public void setRecruitDate(Date recruitDate) {
		this.recruitDate = recruitDate;
	}
	/**
	 * @return the sign
	 */
	public boolean isSign() {
		return sign;
	}
	/**
	 * @param sign the sign to set
	 */
	public void setSign(boolean sign) {
		this.sign = sign;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}
	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}
	/**
	 * @return the overResult
	 */
	public String getOverResult() {
		return overResult;
	}
	/**
	 * @param overResult the overResult to set
	 */
	public void setOverResult(String overResult) {
		this.overResult = overResult;
	}
	/**
	 * @return the regularResult
	 */
	public String getRegularResult() {
		return regularResult;
	}
	/**
	 * @param regularResult the regularResult to set
	 */
	public void setRegularResult(String regularResult) {
		this.regularResult = regularResult;
	}
	/**
	 * @return the deferResult
	 */
	public String getDeferResult() {
		return deferResult;
	}
	/**
	 * @param deferResult the deferResult to set
	 */
	public void setDeferResult(String deferResult) {
		this.deferResult = deferResult;
	}
	/**
	 * @return the ravelDate
	 */
	public Date getRavelDate() {
		return ravelDate;
	}
	/**
	 * @param ravelDate the ravelDate to set
	 */
	public void setRavelDate(Date ravelDate) {
		this.ravelDate = ravelDate;
	}
	/**
	 * @return the ravelResult
	 */
	public String getRavelResult() {
		return ravelResult;
	}
	/**
	 * @param ravelResult the ravelResult to set
	 */
	public void setRavelResult(String ravelResult) {
		this.ravelResult = ravelResult;
	}
}
