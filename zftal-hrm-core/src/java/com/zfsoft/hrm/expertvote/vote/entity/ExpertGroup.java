package com.zfsoft.hrm.expertvote.vote.entity;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-11
 * @version V1.0.0
 */
public class ExpertGroup {
	//id
	private String id;
	//组名
	private String name;
	//专业领域
	private String type;
	//关联任务
	private String level;
	
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
	
	
	

}