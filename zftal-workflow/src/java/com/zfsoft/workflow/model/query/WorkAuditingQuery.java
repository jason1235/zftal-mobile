package com.zfsoft.workflow.model.query;

import java.io.Serializable;

/**
 * 
 * 类描述：工作审核信息查询对象
 * 
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-3-16 下午03:28:18
 */
public class WorkAuditingQuery extends BaseQuery implements Serializable {

	/* serialVersionUID: serialVersionUID */

	private static final long serialVersionUID = 3188162105103760323L;

	private String businessCode;// 业务编码（如果是招聘人员审核则传入的是编织类别代码）（选填），如果有多个，格式如：code1','code2','code3','code4
	private String businessType;// 业务类型（例如：招聘人员审核、编辑计划审核等）（必填）
	private String status;// 状态（待审核；通过；不通过）（必填）
	private String[] roleIdArray;// 角色ID数组（必填）
	private String auditor;// 审核人

	/**
	 * @return businessCode : return the property businessCode.
	 */
	public String getBusinessCode() {
		return businessCode;
	}

	/**
	 * @param businessCode
	 *            : set the property businessCode.
	 */

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	/**
	 * @return businessType : return the property businessType.
	 */

	public String getBusinessType() {
		return businessType;
	}

	/**
	 * @param businessType
	 *            : set the property businessType.
	 */

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * @return status : return the property status.
	 */

	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            : set the property status.
	 */

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return roleIdArray : return the property roleIdArray.
	 */
	
	public String[] getRoleIdArray() {
		return roleIdArray;
	}

	/**
	 * @param roleIdArray : set the property roleIdArray.
	 */
	
	public void setRoleIdArray(String[] roleIdArray) {
		this.roleIdArray = roleIdArray;
	}

	
	/**
	 * @param auditor : set the property auditor.
	 */
	
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	
	/**
	 * @return auditor : return the property auditor.
	 */
	
	public String getAuditor() {
		return auditor;
	}

}
