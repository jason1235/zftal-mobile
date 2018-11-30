package com.zfsoft.hrm.authpost.auth.query;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.search.constants.SearchConstants;
import com.zfsoft.hrm.config.ICodeConstants;

/** 
 * 编制详情查询实体
 * @author jinjj
 * @date 2012-7-27 上午10:35:18 
 *  
 */
public class AuthDetailQuery extends BaseQuery {

	private static final long serialVersionUID = -4478745840041569577L;

	private final static int pageSize = 20;
	
	private String postStatus = SearchConstants.STATE_IN_SERVICE_1;
	
	private String deptId;
	
	private String level;
	
	private String postType;
	
	private String authType;
	
	public AuthDetailQuery(){
		setPerPageSize(pageSize);
	}

	/**
	 * 岗位状态
	 * @return
	 */
	public String getPostStatus() {
		return postStatus;
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

	/**
	 * 部门ID
	 * @return
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * 部门ID
	 * @param deptId
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
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
	
	public String getDeptValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, getDeptId());
		if(StringUtils.isEmpty(str)){
			return getDeptId();
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
