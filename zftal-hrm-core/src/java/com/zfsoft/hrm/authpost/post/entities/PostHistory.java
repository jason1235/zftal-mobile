package com.zfsoft.hrm.authpost.post.entities;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;

/** 
 * @author jinjj
 * @date 2012-7-25 下午02:31:59 
 *  
 */
public class PostHistory {

	private String guid;
	
	private String postId;
	
	private String deptId;
	
	private String superiorId;
	
	private String level;
	
	private int planNumber;
	
	private String duty;
	
	private Date snapTime;
	
	private String postType;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getSuperiorId() {
		return superiorId;
	}

	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getPlanNumber() {
		return planNumber;
	}

	public void setPlanNumber(int planNumber) {
		this.planNumber = planNumber;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public Date getSnapTime() {
		return snapTime;
	}

	public void setSnapTime(Date snapTime) {
		this.snapTime = snapTime;
	}
	
	public String getDeptValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, getDeptId());
		if(StringUtils.isEmpty(str)){
			return getDeptId();
		}
		return str;
	}
	
	public String getPostValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_POSTINFO, getPostId());
		if(StringUtils.isEmpty(str)){
			return getPostId();
		}
		return getDeptValue()+str;
	}
	
	public String getSuperiorValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_DEPT_POST, getSuperiorId());
		if(StringUtils.isEmpty(str)){
			return getSuperiorId();
		}
		return str;
	}
	
	public String getLevelValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_POST_LEVEL, getLevel());
		if(StringUtils.isEmpty(str)){
			return getLevel();
		}
		return str;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}
	
	public String getPostTypeValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_WORKPOST, getPostType());
		if(StringUtils.isEmpty(str)){
			return getPostType();
		}
		return str;
	}
}
