package com.zfsoft.hrm.authpost.auth.entities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;

/**
 * 编制统计条目
 * @author 沈鲁威 Patrick Shen
 * @since 2012-7-24
 * @version V1.0.0
 */
public class AuthStatistics {
	private String name;
	private String deptName;
	private String typeCode;
	private String postTypeName;
	private String postId;
	private String deptPostName;
	private String deptId;
	private String level;
	private String levelName;
	private int planAuthNum=0;
	private int currentAuthNum=0;
	private int overNum=0;
	private int shortNum=0;
	private List<AuthStatistics> children=new ArrayList<AuthStatistics>();
	
	public List<AuthStatistics> getChildren() {
		return children;
	}
	public void setChildren(List<AuthStatistics> children) {
		this.children = children;
	}
	
	public String getDeptPostName() {
		return deptPostName;
	}
	public void setDeptPostName(String deptPostName) {
		this.deptPostName = deptPostName;
	}
	public int getPlanAuthNum() {
		return planAuthNum;
	}
	public void setPlanAuthNum(int planAuthNum) {
		this.planAuthNum = planAuthNum;
	}
	public int getCurrentAuthNum() {
		return currentAuthNum;
	}
	public void setCurrentAuthNum(int currentAuthNum) {
		this.currentAuthNum = currentAuthNum;
	}
	public int getOverNum() {
		overNum=currentAuthNum-planAuthNum;
		if(overNum<0)
			overNum=0;
		return overNum;
	}
	public void setOverNum(int overNum) {
		this.overNum = overNum;
	}
	public int getShortNum() {
		shortNum=planAuthNum-currentAuthNum;
		return shortNum;
	}
	public void setShortNum(int shortNum) {
		this.shortNum = shortNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeptName() {
		deptName = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, deptId);
		if(StringUtils.isEmpty(deptName)){
			return deptId;
		}
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public int getChildrenNum(){
		return children.size();
	}
	public String getPostTypeName() {
		return postTypeName;
	}
	public void setPostTypeName(String postTypeName) {
		this.postTypeName = postTypeName;
	}
	
}
