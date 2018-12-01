package com.zfsoft.hrm.baseinfo.org.entities;

import java.util.List;
import java.util.Properties;

/**
 * 组织机构查询包装类
 * @author yongjun.fang
 *
 */
public class OrgSearch{

	private static final long serialVersionUID = -6180718113764585143L;

	private OrgInfo orgInfo;						//组织机构信息
	
	private List<OrgSearch> childrenSearch;			//子列表,包含orgInfo子部门的OrgSerach包装类对象
	
	private Properties result = new Properties();	//用于存储部门任职人数
	
	private boolean clickable;						//是否可点击
	
	private boolean viewable;						//是否可见
	
	private long totalCount;						//部门及其子部门人数总计
	
	/**
	 * 返回
	 * @return 
	 */
	public boolean isViewable() {
		return viewable;
	}

	/**
	 * 设置
	 * @param viewable 
	 */
	public void setViewable(boolean viewable) {
		this.viewable = viewable;
	}

	/**
	 * 返回
	 * @return 
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置
	 * @param totalCount 
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 返回
	 * @return 
	 */
	public boolean isClickable() {
		return clickable;
	}

	/**
	 * 设置
	 * @param clickable 
	 */
	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}

	/**
	 * 返回
	 * @return 
	 */
	public OrgInfo getOrgInfo() {
		return orgInfo;
	}

	/**
	 * 设置
	 * @param orgInfo 
	 */
	public void setOrgInfo(OrgInfo orgInfo) {
		this.orgInfo = orgInfo;
	}

	/**
	 * 返回
	 * @return 
	 */
	public Properties getResult() {
		return result;
	}

	/**
	 * 返回
	 * @return 
	 */
	public List<OrgSearch> getChildrenSearch() {
		return childrenSearch;
	}

	/**
	 * 设置
	 * @param childrenSearch 
	 */
	public void setChildrenSearch(List<OrgSearch> childrenSearch) {
		this.childrenSearch = childrenSearch;
	}

	/**
	 * 设置
	 * @param result 
	 */
	public void setResult(Properties result) {
		this.result = result;
	}
	
	
}
