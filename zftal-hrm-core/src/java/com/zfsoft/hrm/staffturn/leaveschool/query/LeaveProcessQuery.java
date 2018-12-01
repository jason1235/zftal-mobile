package com.zfsoft.hrm.staffturn.leaveschool.query;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.config.IConstants;

/** 
 * 离校处理查询实体
 * @author jinjj
 * @date 2012-8-2 上午09:15:44 
 *  
 */
public class LeaveProcessQuery extends BaseQuery {

	private static final long serialVersionUID = 6732687158795339680L;
	
	private String deptId;
	
	private String name;
	
	private String status = "0";
	
	private String accountId;
	
	private String processDept;
	
	public LeaveProcessQuery(){
		setPerPageSize(IConstants.COMMON_PAGE_SIZE);
		//setPerPageSize(1);
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
	 * 员工姓名
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 员工姓名
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 处理状态
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 处理状态
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 工号
	 * @return
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * 工号
	 * @param accountId
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	/**
	 * 处理部门
	 * @return
	 */
	public String getProcessDept() {
		return processDept;
	}

	/**
	 * 处理部门
	 * @param processDept
	 */
	public void setProcessDept(String processDept) {
		this.processDept = processDept;
	}

	public String getDeptValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, getDeptId());
		if(StringUtils.isEmpty(str)){
			return getDeptId();
		}
		return str;
	}
}
