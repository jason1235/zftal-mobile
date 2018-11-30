package com.zfsoft.hrm.dybillgrade.entity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author ChenMinming
 * @date 2014-8-6
 * @version V1.0.0
 */
@XmlRootElement
public class SpBillGradeLevel {

	private String id;
	//分数线
	private Integer scorePoint;
	private String desc;
	@XmlAttribute
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	@XmlAttribute
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@XmlAttribute
	public Integer getScorePoint() {
		return scorePoint;
	}

	public void setScorePoint(Integer scorePoint) {
		this.scorePoint = scorePoint;
	}

	
}
