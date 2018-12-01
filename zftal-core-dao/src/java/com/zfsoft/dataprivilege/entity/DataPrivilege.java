package com.zfsoft.dataprivilege.entity;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;

@Table("ZFTAL_XTGL_SJFWB")
public class DataPrivilege extends MyBatisBean{
	@SQLField("JSBH")
	private String roleId;
	@SQLField("GLZBH")
	private String filterId;
	@SQLField("YHBH")
	private String userId;
	@SQLField("TCZ")
	private String xmlValue;
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String getFilterId() {
		return filterId;
	}
	public void setFilterId(String contextId) {
		this.filterId = contextId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getXmlValue() {
		return xmlValue;
	}
	public void setXmlValue(String xmlValue) {
		this.xmlValue = xmlValue;
	} 
	
	
}
