package com.zfsoft.hrm.summary.roster.entity;

/** 
 * 花名册参数实体
 * @author jinjj
 * @date 2012-11-20 下午04:25:03 
 *  
 */
public class RosterParam {

	private String guid;
	
	private byte[] data;

	/**
	 * 花名册主键
	 * @return
	 */
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 参数序列化数据
	 * @return
	 */
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	
}
