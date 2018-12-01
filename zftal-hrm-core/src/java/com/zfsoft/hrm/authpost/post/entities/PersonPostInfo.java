package com.zfsoft.hrm.authpost.post.entities;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.config.ICodeConstants;
/**
 * 人员岗位关联实体
 * @author 沈鲁威 Patrick Shen
 * @since 2012-8-16
 * @version V1.0.0
 */
public class PersonPostInfo {
	private String userId;
	private String deptId;
	private String postState;
	private String authState;
	private String postLevel;
	private PostInfo postInfo;
	
	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	private DynaBean overall;
	
	public DynaBean getOverall() {
		return overall;
	}

	public void setOverall(DynaBean overall) {
		this.overall = overall;
	}
	public String getPostState() {
		return postState;
	}

	public void setPostState(String postState) {
		this.postState = postState;
	}

	public String getAuthState() {
		return authState;
	}

	public void setAuthState(String authState) {
		this.authState = authState;
	}

	public PostInfo getPostInfo() {
		return postInfo;
	}

	public void setPostInfo(PostInfo postInfo) {
		this.postInfo = postInfo;
	}

	public String getUserId() {
		return userId;
	}

	public String getPostLevel() {
		return postLevel;
	}

	public void setPostLevel(String postLevel) {
		this.postLevel = postLevel;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeptValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, deptId);
		if(StringUtils.isEmpty(str)){
			return deptId;
		}
		return str;
	}
	public String getLevelValue(){
		if(StringUtils.isEmpty(getPostLevel())){
			return "(无)";
		}
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_POST_LEVEL, getPostLevel());
		if(StringUtils.isEmpty(str)){
			return getPostLevel();
		}
		return str;
	}
}
