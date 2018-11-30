package com.zfsoft.hrm.dybillgrade.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.zfsoft.hrm.dybill.xml.XmlValueEntity;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-12
 * @version V1.0.0
 */
@XmlRootElement
public class SpBillGradeResultCondition {
	private int score;
	private Long billClassId;
	private String conditionId;
	private String conditionText;
	private List<XmlValueEntity> xmlValueEntities;
	/**
	 * 返回
	 */
	@XmlAttribute
	public int getScore() {
		return score;
	}
	@XmlTransient
	public int getSumScore() {
		return score*xmlValueEntities.size();
	}
	/**
	 * 设置
	 * @param score 
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * 返回
	 */
	@XmlAttribute
	public Long getBillClassId() {
		return billClassId;
	}
	/**
	 * 设置
	 * @param billClassId 
	 */
	public void setBillClassId(Long billClassId) {
		this.billClassId = billClassId;
	}
	/**
	 * 返回
	 */
	@XmlAttribute
	public String getConditionText() {
		return conditionText;
	}
	/**
	 * 设置
	 * @param conditionText 
	 */
	public void setConditionText(String conditionText) {
		this.conditionText = conditionText;
	}
	/**
	 * 返回
	 */
	@XmlElement
	public List<XmlValueEntity> getXmlValueEntities() {
		return xmlValueEntities;
	}
	/**
	 * 设置
	 * @param xmlValueEntities 
	 */
	public void setXmlValueEntities(List<XmlValueEntity> xmlValueEntities) {
		this.xmlValueEntities = xmlValueEntities;
	}

	/**
	 * 返回
	 */
	@XmlAttribute
	public String getConditionId() {
		return conditionId;
	}

	/**
	 * 设置
	 * @param conditionId 
	 */
	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}
	
}
