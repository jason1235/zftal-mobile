package com.zfsoft.hrm.expertvote.vote.query;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-11
 * @version V1.0.0
 */
public class ExpertGroupQuery extends BaseQuery{

	private static final long serialVersionUID = 201403111125946L;

	//id
	private String id;
	//组名
	private String name;
	//专家类型
	private String type;
	//审核级别
	private String level;
	
	private String memberId;

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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
}
