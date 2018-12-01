package com.zfsoft.hrm.staffturn.leaveschool.entities;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.config.IConstants;

/** 
 * 离校步骤
 * @author jinjj
 * @date 2012-8-1 上午12:37:59 
 *  
 */
public class LeaveStep {

	private String guid;
	
	private String deptId;
	
	private String handler;

	/**
	 * 步骤ID
	 * @return
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * 步骤ID
	 * @param guid
	 */
	public void setGuid(String guid) {
		this.guid = guid;
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
	 * 处理人ID(多人使用','分隔)
	 * @return
	 */
	public String getHandler() {
		return handler;
	}
	/**
	 * 处理人ID
	 * @return
	 */
	public String[] getHandlerList() {
		if(handler == null) return null;
		return handler.split(IConstants.SPLIT_STR);
	}
	/**
	 * 处理人ID(多人使用','分隔)
	 * @return
	 */
	public void setHandler(String handler) {
		this.handler = handler;
	}
	
	public String getDeptValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, getDeptId());
		if(StringUtils.isEmpty(str)){
			return getDeptId();
		}
		return str;
	}
}
