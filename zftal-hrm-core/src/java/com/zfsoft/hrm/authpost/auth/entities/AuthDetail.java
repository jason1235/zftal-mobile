package com.zfsoft.hrm.authpost.auth.entities;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;

/** 
 * 编制详情
 * @author jinjj
 * @date 2012-7-27 上午10:33:06 
 *  
 */
public class AuthDetail {

	private String accountId;
	
	private String name;
	
	private String deptId;
	
	private String postType;
	
	private String level;
	
	private String postId;
	
	private String postStatus;
	
	private String authStatus;
	
	private String authType;

	/**
	 * 职工号
	 * @return
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * 职工号
	 * @param accountId
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * 职工姓名
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 职工姓名
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 部门ID（员工，非岗位所属）
	 * @return
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * 部门ID（员工，非岗位所属）
	 * @param deptId
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * 岗位类别
	 * @return
	 */
	public String getPostType() {
		return postType;
	}

	/**
	 * 岗位类别
	 * @param postType
	 */
	public void setPostType(String postType) {
		this.postType = postType;
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
	 * 部门岗位ID
	 * @return
	 */
	public String getPostId() {
		return postId;
	}

	/**
	 * 部门岗位ID
	 * @param postId
	 */
	public void setPostId(String postId) {
		this.postId = postId;
	}

	/**
	 * 岗位状态
	 * @return
	 */
	public String getPostStatus() {
		return postStatus;
	}

	/**
	 * 岗位状态
	 * @param postStatus
	 */
	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}

	/**
	 * 编制状态
	 * @return
	 */
	public String getAuthStatus() {
		return authStatus;
	}

	/**
	 * 编制状态
	 * @param authStatus
	 */
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}
	
	/**
	 * 编制类别
	 * @return
	 */
	public String getAuthType() {
		return authType;
	}

	/**
	 * 编制类别
	 * @param authType
	 */
	public void setAuthType(String authType) {
		this.authType = authType;
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
	
	public String getLevelValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_POST_LEVEL, getLevel());
		if(StringUtils.isEmpty(str)){
			return getLevel();
		}
		return str;
	}
	
	public String getPostTypeValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_WORKPOST, getPostType());
		if(StringUtils.isEmpty(str)){
			return getPostType();
		}
		return str;
	}
	
	public String getAuthTypeValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.AUTH_TYPE, getAuthType());
		if(StringUtils.isEmpty(str)){
			return getAuthType();
		}
		return str;
	}
	
	
}
