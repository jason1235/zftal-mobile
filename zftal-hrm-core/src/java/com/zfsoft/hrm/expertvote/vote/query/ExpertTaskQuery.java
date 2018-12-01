package com.zfsoft.hrm.expertvote.vote.query;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-14
 * @version V1.0.0
 */
public class ExpertTaskQuery extends BaseQuery{

	private static final long serialVersionUID = 201403141523946L;
	//任务ID （与工作流任务id保持统一）
	private String id;
	//任务名称
	private String name;
	//审核级别（与专家组审核级别对应）
	private String level;
	//通过比率控制
	private String passPoint;
	//比率计算方式（暂时只有  0:进一）
	private String pointType="0";
	//专家组id
	private String groupId;
	//专家工号
	private String expertGh;
	
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
	
	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getPassPoint() {
		return passPoint;
	}
	
	public void setPassPoint(String passPoint) {
		this.passPoint = passPoint;
	}
	
	public String getPointType() {
		return pointType;
	}
	
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getExpertGh() {
		return expertGh;
	}
	public void setExpertGh(String expertGh) {
		this.expertGh = expertGh;
	}
	

}
