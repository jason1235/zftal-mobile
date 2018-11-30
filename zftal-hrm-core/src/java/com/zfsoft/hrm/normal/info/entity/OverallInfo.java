package com.zfsoft.hrm.normal.info.entity;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.util.date.DateTimeUtil;


public class OverallInfo {
	private String userId;
	private String userName;
	private String departmentId;
	private String departmentName;
	private String status;
	private String statusName;
	private String term;
	private String sex;         		  //性别
	
	private Date birthday;         		  //出生年月
	
	private String idCardNum;             //身份证号码
	private String expression;
	
	private String postId;				//岗位类别
	private String formationType;		//编制类别
	private String dutyLevel;			//行政职务级别
	private String postTitle;			//职称(专业技术职务)
	private Date entryDate;				//入职日期(进校日期)
	private Date startDate;				//进校开始时间
	private Date endDate;				//进校结束时间
	
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
	 * @return the departmentId
	 */
	public String getDepartmentId() {
		return departmentId;
	}
	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}
	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * @return the term
	 */
	public String getTerm() {
		return term;
	}
	/**
	 * @param term the term to set
	 */
	public void setTerm(String term) {
		this.term = term;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return userName+"("+userId+")"+departmentName+"("+departmentId+")"+statusName+"("+status+")";
	}
	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}
	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
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
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the idCardNum
	 */
	public String getIdCardNum() {
		return idCardNum;
	}
	/**
	 * @param idCardNum the idCardNum to set
	 */
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getFormationType() {
		return formationType;
	}
	public void setFormationType(String formationType) {
		this.formationType = formationType;
	}
	public String getDutyLevel() {
		return dutyLevel;
	}
	public void setDutyLevel(String dutyLevel) {
		this.dutyLevel = dutyLevel;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public String getEndDateString(){
		return DateTimeUtil.getFormatDate(endDate,"yyyy-MM-dd");
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public String getStartDateString(){
		return DateTimeUtil.getFormatDate(startDate,"yyyy-MM-dd");
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
