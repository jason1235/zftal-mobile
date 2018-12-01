package com.zfsoft.hrm.manoeuvre.configInfo.entities;

import java.io.Serializable;

/**
 * 人员调动审核环节节点实体类
 * @author yongjun.fang
 * 编写时间: 2012-10-23 11:11
 *
 */
public class TaskNode implements Serializable {

	private static final long serialVersionUID = -4029190724397462187L;

	private String nid;			//环节节点编号
	
	private String nodeName;	//环节节点名称
	
	private String order;		//顺序码
	
	private String remark;		//备注
	

	/**
	 * 返回
	 * @return 
	 */
	public String getNid() {
		return nid;
	}

	/**
	 * 设置
	 * @param nid 
	 */
	public void setNid(String nid) {
		this.nid = nid;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * 设置
	 * @param nodeName 
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置
	 * @param remark 
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置
	 * @param order 
	 */
	public void setOrder(String order) {
		this.order = order;
	}

}
