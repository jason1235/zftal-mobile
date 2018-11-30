package com.zfsoft.hrm.summary.roster.entity;

import java.io.Serializable;
import java.util.Date;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;

/** 
 * 花名册条件实体
 * @author jinjj
 * @date 2012-8-30 上午10:49:22 
 *  
 */
public class RosterConfig implements Serializable {

	private static final long serialVersionUID = -8153234561984787580L;

	private String guid;
	
	private String classid;
	
	private String queryType;
	
	private Date createtime;
	
	private InfoProperty infoProperty;
	
	private boolean selected = false;

	/**
	 * ID,InfoProperty的guid
	 * @return
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * ID
	 * @param guid
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 查询类别 0精确 1模糊
	 * @return
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * 查询类别
	 * @param queryType
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public InfoProperty getInfoProperty() {
		return infoProperty;
	}

	public void setInfoProperty(InfoProperty infoProperty) {
		this.infoProperty = infoProperty;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
