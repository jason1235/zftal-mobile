package com.zfsoft.mobile.classCommunity.entity;

/**
 * 班级信息实体
 * @author H110MF
 *
 */
public class ClassEntity {
	private String id;           //唯一主键
	private String name;         //班级名称
	private String instituteId;  //学院id
	private String instituteName; //学院名称
	
	private String memberCount; //圈子成员数
	private String dynamicCount; //帖子数
	private String type;         //0想加入，1已加入
	
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
	public String getInstituteId() {
		return instituteId;
	}
	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(String memberCount) {
		this.memberCount = memberCount;
	}
	public String getDynamicCount() {
		return dynamicCount;
	}
	public void setDynamicCount(String dynamicCount) {
		this.dynamicCount = dynamicCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
