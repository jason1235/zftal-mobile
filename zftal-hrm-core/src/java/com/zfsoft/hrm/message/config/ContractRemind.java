package com.zfsoft.hrm.message.config;

import java.util.Date;

/** 
 * @author jinjj
 * @date 2012-9-25 上午09:45:47 
 *  
 */
public class ContractRemind implements IMessageJobDefine {

	private static final long serialVersionUID = -2150152712284054827L;

	private String serviceName = "contractRemindService";
	
	private String jobName = "CONTRACT_REMIND";
	
	private String jobDescription = "合同提醒";
	
	private int status = 0;
	
	private String receiver;
	
	private String sender = "system";
	
	private int remindType = 1;
	
	private Date lastTime;
	
	private String period = "1d";
	
	private int aheadDay = 15;
	
	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String getSender() {
		return sender;
	}

	@Override
	public String getServiceName() {
		return serviceName;
	}

	@Override
	public String getJobName() {
		return jobName;
	}

	@Override
	public String getJobDescription() {
		return jobDescription;
	}

	@Override
	public String getReceiver() {
		return receiver;
	}

	@Override
	public int getRemindType() {
		return remindType;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getPeriod() {
		return period;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	public String getOptionText(){
		StringBuilder sb = new StringBuilder();
		sb.append("<option value='1d' selected>每一天</option>");
		return sb.toString();
	}

	public int getAheadDay() {
		return aheadDay;
	}
	
}
