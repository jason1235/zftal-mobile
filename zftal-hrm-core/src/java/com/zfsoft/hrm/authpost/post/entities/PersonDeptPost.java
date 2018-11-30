package com.zfsoft.hrm.authpost.post.entities;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
/**
 * 人员岗位关联实体
 * @author 沈鲁威 Patrick Shen
 * @since 2012-8-16
 * @version V1.0.0
 */
public class PersonDeptPost {
	private String userId;
	private String postState;
	private String authState;
	private DeptPost deptPost;
	
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

	public DeptPost getDeptPost() {
		return deptPost;
	}

	public void setDeptPost(DeptPost deptPost) {
		this.deptPost = deptPost;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
