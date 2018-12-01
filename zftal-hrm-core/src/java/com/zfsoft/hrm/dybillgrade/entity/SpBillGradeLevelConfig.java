package com.zfsoft.hrm.dybillgrade.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sun.xml.internal.txw2.annotation.XmlElement;

/**
 * 
 * @author ChenMinming
 * @date 2014-8-6
 * @version V1.0.0
 */
@XmlRootElement
public class SpBillGradeLevelConfig {
	
	private Integer maxScore;
	
	private List<SpBillGradeLevel> levelList;
	@XmlElement
	public List<SpBillGradeLevel> getLevelList() {
		return levelList;
	}
	
	public void setLevelList(List<SpBillGradeLevel> levelList) {
		this.levelList = levelList;
	}
	@XmlAttribute
	public Integer getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}
	
	@XmlTransient
	public List<SpBillGradeLevel> getInnerLevelList(){
		if(getLevelList() ==null || getLevelList().size()<2)
			return null;
		return getLevelList().subList(0, getLevelList().size()-1);
	}
	@XmlTransient
	public SpBillGradeLevel getLastLevel(){
		if(getLevelList() ==null || getLevelList().size()<2)
			return null;
		return getLevelList().get(getLevelList().size()-1);
	}
}
