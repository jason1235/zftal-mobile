package com.zfsoft.hrm.staffturn.leaveschool.query;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.util.date.DateTimeUtil;

/** 
 * 离校管理查询实体
 * @author jinjj
 * @date 2012-8-2 上午07:21:18 
 *  
 */
public class LeaveFlowInfoQuery extends BaseQuery {

	private static final long serialVersionUID = 3406416121620700112L;

	private String deptId;
	
	private String name;
	
	private String type;
	
	private String leaveStatus = "0";
	
	private Date leaveDateStart;	// 离校时间上限
	
	private Date leaveDateEnd;	// 离校时间下限
	
	// 20140422 add start
	private String bzzk; // 编制状态
	// 20140422 add end
	
	private String lxqx; //离校去向

	public LeaveFlowInfoQuery(){
		setPerPageSize(IConstants.COMMON_PAGE_SIZE);
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
	
	public String getDeptValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, getDeptId());
		if(StringUtils.isEmpty(str)){
			return getDeptId();
		}
		return str;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public Date getLeaveDateStart() {
		return leaveDateStart;
	}
	
	public String getLeaveDateStartString(){
		return DateTimeUtil.getFormatDate(leaveDateStart,"yyyy-MM-dd");
	}

	public void setLeaveDateStart(Date leaveDateStart) {
		this.leaveDateStart = leaveDateStart;
	}

	public Date getLeaveDateEnd() {
		return leaveDateEnd;
	}
	
	public String getLeaveDateEndString(){
		return DateTimeUtil.getFormatDate(leaveDateEnd, "yyyy-MM-dd");
	}

	public void setLeaveDateEnd(Date leaveDateEnd) {
		this.leaveDateEnd = leaveDateEnd;
	}

	/**
	 * 返回
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置
	 * @param type 
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	// 20140422 add start
	/**
	 * @return the bzzk
	 */
	public String getBzzk() {
		return bzzk;
	}

	/**
	 * @param bzzk the bzzk to set
	 */
	public void setBzzk(String bzzk) {
		this.bzzk = bzzk;
	}
	
	public String getBzzkValue() {
		String str = CodeUtil.getItemValue(ICodeConstants.DM_GB_BZZKDMB, bzzk);
		if (StringUtils.isEmpty(str)) {
			return bzzk;
		}
		return str;
	}
	// 20140422 add end

	public String getLxqx() {
		return lxqx;
	}

	public void setLxqx(String lxqx) {
		this.lxqx = lxqx;
	}

}
