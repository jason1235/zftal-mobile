package com.zfsoft.hrm.expertvote.vote.entity;

import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-20
 * @version V1.0.0
 */
public class ExpertTaskDetail {
	//任务ID （与工作流任务id保持统一）
	private String id;
	//任务名称
	private String name;
	//业务信息类id
	private String businessClassId;
	//通过人数
	private int passNumber=0;
	//任务人数
	private int waitNumber=0; 
	//未到达人数
	private int beforeNumber=0;
	/**
	 * 是否已经发起评审（查询条件）
	 *  Send 发起  NotSend 未发起
	 */
	private String send;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPassNumber() {
		return passNumber;
	}
	public void setPassNumber(int passNumber) {
		this.passNumber = passNumber;
	}
	public int getWaitNumber() {
		return waitNumber;
	}
	public void setWaitNumber(int waitNumber) {
		this.waitNumber = waitNumber;
	}
	public int getBeforeNumber() {
		return beforeNumber;
	}
	public void setBeforeNumber(int beforeNumber) {
		this.beforeNumber = beforeNumber;
	}
	public String getSend() {
		return send;
	}
	public void setSend(String send) {
		this.send = send;
	}
	public String getBusinessClassId() {
		return businessClassId;
	}
	public void setBusinessClassId(String businessClassId) {
		this.businessClassId = businessClassId;
	}
	public String getBusinessClassName() {
		if(StringUtil.isEmpty(businessClassId)) return "";
		InfoClass c = InfoClassCache.getInfoClass(businessClassId);
		if(c==null||StringUtil.isEmpty(c.getName())) return "";
		return c.getName();
	}
	
}
