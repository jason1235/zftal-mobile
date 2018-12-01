package com.zfsoft.hrm.staffturn.retire.query;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.query.BeanQueryV2;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.util.date.DateTimeUtil;

public class RetireInfoQuery extends BeanQueryV2{
	
	private static final long serialVersionUID = 1L;
	private String userId;
	private int state;
	private String deptCode;
	private String userName;
	private Date startBothdayTime;
	private Date endBothdayTime;
	private Date startRetireTime;
	private Date endRetireTime;
	private String sex;
	private String opType="add";
	private String retirePost;			//退休职位  这些都是取代码表的
	private String identifier;
	
	private String isDoctorMentor;		//是否博士生导师
	
	private Boolean reEngage;//是否返聘
	
	private Boolean isEmploy;// 是否延聘
	
	public String getRetirePost() {
		if (retirePost != null && retirePost.trim().equals("")) {
			retirePost = null;
		}
		return retirePost;
	}

	public void setRetirePost(String retirePost) {
		this.retirePost = retirePost;
	}

	public String getUserId() {
		if (userId != null && userId.trim().equals("")) {
			userId = null;
		}
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getDeptCode() {
		if (deptCode != null && deptCode.trim().equals("")) {
			deptCode = null;
		}
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getUserName() {
		if (userName != null && userName.trim().equals("")) {
			userName = null;
		}
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getStartBothdayTime() {
		return startBothdayTime;
	}

	public void setStartBothdayTime(Date startBothdayTime) {
		this.startBothdayTime = startBothdayTime;
	}

	public Date getEndBothdayTime() {
		return endBothdayTime;
	}

	public void setEndBothdayTime(Date endBothdayTime) {
		this.endBothdayTime = endBothdayTime;
	}

	public Date getStartRetireTime() {
		return startRetireTime;
	}

	public void setStartRetireTime(Date startRetireTime) {
		this.startRetireTime = startRetireTime;
	}

	public Date getEndRetireTime() {
		return endRetireTime;
	}

	public void setEndRetireTime(Date endRetireTime) {
		this.endRetireTime = endRetireTime;
	}
	public String getSex() {
		if (sex != null && sex.trim().equals("")) {
			sex = null;
		}
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	// ++++++++++++++++??++++++++++++++//
	public String getStartBothdayTimeString() {
		return DateTimeUtil.getFormatDate(startBothdayTime, "yyyy-MM-dd");
	}

	public void setStartBothdayTimeString(String startBothdayTime) {
		this.startBothdayTime = DateTimeUtil.getFormatDateTime(
				startBothdayTime, "yyyy-MM-dd");
	}

	public String getEndBothdayTimeString() {
		return DateTimeUtil.getFormatDate(endBothdayTime, "yyyy-MM-dd");
	}

	public String getStartRetireTimeString() {
		return DateTimeUtil.getFormatDate(startRetireTime, "yyyy-MM-dd");
	}


	public String getEndRetireTimeString() {
		return DateTimeUtil.getFormatDate(endRetireTime, "yyyy-MM-dd");
	}

	// ++++++++++++++++??++++++++++++++//


	public String getDeptValue() {
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, deptCode);
		if (StringUtils.isEmpty(str)) {
			return deptCode;
		}
		return str;
	}

	public String getSexValue() {
		String str = CodeUtil.getItemValue(ICodeConstants.SEX, sex);
		if (StringUtils.isEmpty(str)) {
			return sex;
		}
		return str;
	}
	public String getRetirePostValue() {
		String str = CodeUtil.getItemValue(ICodeConstants.ADMIN_DUTY_LEVEL,
				this.getRetirePost());
		if (StringUtils.isEmpty(str)) {
			return this.getRetirePost();
		}
		return str;
	}

	/**
	 * 返回
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * 设置
	 * @param identifier 
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getIsDoctorMentor() {
		return isDoctorMentor;
	}

	public void setIsDoctorMentor(String isDoctorMentor) {
		this.isDoctorMentor = isDoctorMentor;
	}

	public Boolean getReEngage() {
		return reEngage;
	}
	
	public String getReEngageStr() {
		if(reEngage==null){
			return "";
		}
		return reEngage.toString();
	}
	
	public String getIsEmployStr() {
		if(isEmploy==null){
			return "";
		}
		return isEmploy.toString();
	}
	
	public void setIsEmploy(String isEmploy) {
		if(StringUtils.isEmpty(isEmploy)){
			this.isEmploy=null;
		}else{
			this.isEmploy = Boolean.valueOf(isEmploy);
		}
	}

	public void setReEngage(String reEngage) {
		if(StringUtils.isEmpty(reEngage)){
			this.reEngage=null;
		}else{
			this.reEngage = Boolean.valueOf(reEngage);
		}
	}

	public Boolean getIsEmploy() {
		return isEmploy;
	}
}
