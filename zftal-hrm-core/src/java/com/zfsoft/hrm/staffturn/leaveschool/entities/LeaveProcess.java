package com.zfsoft.hrm.staffturn.leaveschool.entities;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.orcus.lang.TimeUtil;

/** 
 * 离校处理实体
 * @author jinjj
 * @date 2012-8-2 上午08:27:19 
 *  
 */
public class LeaveProcess {

	private String accountId;
	
	private String status;
	
	private String deptId;
	
	private String operator;
	
	private Date operateDate;
	
	private DynaBean dynaBean;
	
	private String guid;

	/**
	 * 员工ID
	 * @return
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * 员工ID
	 * @param accountId
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
	 * 处理人
	 * @return
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * 处理人
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 处理时间
	 * @return
	 */
	public Date getOperateDate() {
		return operateDate;
	}
	
	/**
	 * 处理时间String
	 * @return
	 */
	public String getOperateDateStr() {
		if(operateDate==null){
			return "";
		}
		return TimeUtil.format(operateDate, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	/**
	 * 处理时间
	 * @param operateDate
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	
	/**
	 * overall动态信息
	 * @return
	 */
	public DynaBean getDynaBean() {
		return dynaBean;
	}

	/**
	 * overall动态信息
	 * @param dynaBean
	 */
	public void setDynaBean(DynaBean dynaBean) {
		this.dynaBean = dynaBean;
	}

	public String getDeptValue(){
		String str = CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, getDeptId());
		if(StringUtils.isEmpty(str)){
			return getDeptId();
		}
		return str;
	}
	
	/**
	 * 职务（包含管理，技术，工勤）
	 * @return
	 */
	public String getDuty(){
		StringBuilder sb = new StringBuilder();
		//appendViewParse("gbzwmcdm", sb);
		//appendViewParse("przyjszw", sb);
		//appendViewParse("gjzyzgdm", sb);
		appendViewParse("xrzw", sb);
		return sb.toString();
	}
	
	private void appendViewParse(String column,StringBuilder sb){
		String value = dynaBean.getViewHtml().get(column);
		if(StringUtils.isEmpty(value)){
			return;
		}
		if(sb.length()>0){
			sb.append(",");
		}
		sb.append(value);
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
}
