package com.zfsoft.hrm.authpost.post.entities;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;

/** 
 * 部门岗位
 * @author jinjj
 * @date 2012-7-24 上午11:30:12 
 *  
 */
public class DeptPost {

	private String guid;
	
	private String postId;
	
	private String deptId;
	
	private String superiorId;
	
	private String level;
	
	private int planNumber;
	
	private String duty;
	
	private String postType;
	
	/**
	 * 岗位编号
	 * @return
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * 岗位编号
	 * @param guid
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 岗位名称
	 * @return
	 */
	public String getPostId() {
		return postId;
	}

	/**
	 * 岗位名称
	 * @param name
	 */
	public void setPostId(String name) {
		this.postId = name;
	}

	/**
	 * 所属部门
	 * @return
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * 所属部门
	 * @param deptId
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * 上级岗位
	 * @return
	 */
	public String getSuperiorId() {
		return superiorId;
	}

	/**
	 * 上级岗位
	 * @param superiorId
	 */
	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}

	/**
	 * 岗位等级
	 * @return
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * 岗位等级
	 * @param level
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * 计划岗位数
	 * @return
	 */
	public int getPlanNumber() {
		return planNumber;
	}

	/**
	 * 计划岗位数
	 * @param planNumber
	 */
	public void setPlanNumber(int planNumber) {
		this.planNumber = planNumber;
	}

	/**
	 * 岗位职责
	 * @return
	 */
	public String getDuty() {
		return duty;
	}

	/**
	 * 岗位职责
	 * @param duty
	 */
	public void setDuty(String duty) {
		this.duty = duty;
	}
	
	public String getDeptValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, getDeptId());
		if(StringUtils.isEmpty(str)){
			return getDeptId();
		}
		return str;
	}
	
	public String getPostValue(){
		//String str = null;
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
