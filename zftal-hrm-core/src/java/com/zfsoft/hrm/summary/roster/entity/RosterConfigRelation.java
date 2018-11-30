package com.zfsoft.hrm.summary.roster.entity;

import java.io.Serializable;
import java.util.Date;

/** 
 * 花名册条件关系实体
 * @author jinjj
 * @date 2012-8-30 上午10:53:58 
 *  
 */
public class RosterConfigRelation implements Serializable {

	private static final long serialVersionUID = -1718497453629519792L;

	private String rosterId;
	
	private String configId;
	
	private Date createtime;
	
	private String classId;

	/**
	 * 名册ID
	 * @return
	 */
	public String getRosterId() {
		return rosterId;
	}

	public void setRosterId(String rosterId) {
		this.rosterId = rosterId;
	}

	/**
	 * 条件字段ID
	 * @return
	 */
	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	/**
	 * 创建时间
	 * @return
	 */
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * 信息类id(冗余)
	 * @return
	 */
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
}
