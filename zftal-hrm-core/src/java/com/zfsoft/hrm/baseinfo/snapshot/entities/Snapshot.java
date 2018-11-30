package com.zfsoft.hrm.baseinfo.snapshot.entities;

import java.io.Serializable;
import java.util.Date;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;

/** 
 * 快照实体
 * @ClassName: Snapshot 
 * @author jinjj
 * @date 2012-7-17 上午11:18:52 
 *  
 */
public class Snapshot extends DynaBean implements Serializable{

	private static final long serialVersionUID = 339221221126681717L;

	private Date snapTime;


	public Snapshot(InfoClass clazz){
		super(clazz);
	}

	/**
	 * Get时间戳
	 * @return 
	 */
	public Date getSnapTime() {
		return snapTime;
	}

	/**
	 * Set时间戳
	 * @param timestamp 
	 */
	public void setSnapTime(Date snapTime) {
		this.snapTime = snapTime;
	}
	
}
