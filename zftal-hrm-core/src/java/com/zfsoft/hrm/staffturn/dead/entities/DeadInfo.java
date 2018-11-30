package com.zfsoft.hrm.staffturn.dead.entities;

import java.util.Date;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.staffturn.retire.entities.RetireInfo;
import com.zfsoft.util.date.DateTimeUtil;


/**
 * 离世
 * @author 沈鲁威 Patrick Shen
 * @since 2012-7-30
 * @version V1.0.0
 */
@Table("RYYD_LSRYB")
public class DeadInfo extends MyBatisBean{
	@SQLField(value="gh",key=true)
	private String userId;//死亡人员工号
	@SQLField(value="swsj")
	private Date deadTime;//死亡时间
	@SQLField(value="swfxj")
	private double deadSubsidy;//死亡抚恤金
	@SQLField(value="qsr")
	private String receiver;//签收人
	@SQLField(value="qsrq")
	private Date receiveDate;//签收日期
	@SQLField(value="bz")
	private String remark;//备注
	// 20140422 add start
	@SQLField(value="ryztm")
	private String ryztm;// 人员状态码
	// 20140422 add end
	
	private String deadAge;     //离世年龄
	private RetireInfo retireInfo;//退休信息
	private DynaBean overall;
	
	public RetireInfo getRetireInfo() {
		return retireInfo;
	}
	public void setRetireInfo(RetireInfo retireInfo) {
		this.retireInfo = retireInfo;
	}
	public DynaBean getOverall() {
		return overall;
	}
	public void setOverall(DynaBean overall) {
		this.overall = overall;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeadTimeString(){
		return DateTimeUtil.getFormatDate(deadTime,"yyyy-MM-dd");
	}
	public Date getDeadTime() {
		return deadTime;
	}
	public void setDeadTime(Date deadTime) {
		this.deadTime = deadTime;
	}
	public double getDeadSubsidy() {
		return deadSubsidy;
	}
	public void setDeadSubsidy(double deadSubsidy) {
		this.deadSubsidy = deadSubsidy;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceiveDateString(){
		return DateTimeUtil.getFormatDate(receiveDate,"yyyy-MM-dd");
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	// 20140422 add start
	/**
	 * @return the ryztm
	 */
	public String getRyztm() {
		return ryztm;
	}
	/**
	 * @param ryztm the ryztm to set
	 */
	public void setRyztm(String ryztm) {
		this.ryztm = ryztm;
	}
	// 20140422 add end
	public String getDeadAge() {
		return deadAge;
	}
	public void setDeadAge(String deadAge) {
		this.deadAge = deadAge;
	}
}
