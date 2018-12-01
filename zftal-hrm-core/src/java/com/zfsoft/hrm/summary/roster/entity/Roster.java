package com.zfsoft.hrm.summary.roster.entity;

import java.io.Serializable;
import java.util.Date;

/** 
 * 花名册实体
 * @author jinjj
 * @date 2012-8-30 上午10:43:59 
 *  
 */
public class Roster implements Serializable{

	private static final long serialVersionUID = -5298737634161040852L;

	private String guid;
	
	private String name;
	
	/**
	 * 花名册增加类型
	 * 个人：PERSONAL；公共：PUBLIC
	 * 
	 * 2013-9-5 added by 1021
	 */
	private String rosterType;
	
	private String description;
	
	private Date createtime;
	
	private String creator;

	/**
	 * ID
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
	 * 名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
     * 类型
     * @return
     */
    public String getRosterType() {
        return rosterType;
    }

    /**
     * 类型
     * @param rosterType
     */
    public void setRosterType(String rosterType) {
        this.rosterType = rosterType;
    }

    /**
	 * 描述
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 描述
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
}
