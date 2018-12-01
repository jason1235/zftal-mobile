package com.zfsoft.hrm.staffturn.dead.query;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.query.BeanQueryV1;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;
/**
 * 离世
 * @author 沈鲁威 Patrick Shen
 * @since 2012-8-7
 * @version V1.0.0
 */
public class DeadInfoQuery extends BeanQueryV1{
	private static final long serialVersionUID = 2431271039378249673L;
	private String userId;
	private String deptCode;
	private String opType="add";
	
	private String orderStr;
	
	// 20140422 add start
	private String ryztCode;
	// 20140422 add end

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeptCode() {
		if (deptCode != null && deptCode.trim().equals("")) {
			deptCode = null;
		}
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptValue() {
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, deptCode);
		if (StringUtils.isEmpty(str)) {
			return deptCode;
		}
		return str;
	}

	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	// 20140422 add start
	/**
	 * @return the ryztCode
	 */
	public String getRyztCode() {
		return ryztCode;
	}

	/**
	 * @param ryztCode the ryztCode to set
	 */
	public void setRyztCode(String ryztCode) {
		this.ryztCode = ryztCode;
	}
	
	public String getRyztValue() {
		String str = CodeUtil.getItemValue(ICodeConstants.DM_GB_LSRYZTDMB, ryztCode);
		if (StringUtils.isEmpty(str)) {
			return ryztCode;
		}
		return str;
	}
	// 20140422 add end
}
