package com.zfsoft.hrm.message.config;

import java.io.Serializable;
import java.util.Date;

/** 
 * @author jinjj
 * @date 2012-9-25 上午09:54:47 
 *  
 */
public interface IMessageJobDefine extends Serializable{

	public int getStatus();

	public void setStatus(int status);

	public String getSender();

	public void setReceiver(String receiver);

	public String getServiceName();

	public String getJobName();

	public String getJobDescription();

	public String getReceiver();

	public int getRemindType();
	
	public Date getLastTime();
	
	public void setLastTime(Date lastTime);
	
	public String getPeriod();
	
	public String getOptionText();
}