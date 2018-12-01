package com.zfsoft.hrm.dybillgrade.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;
import com.zfsoft.util.jaxb.JaxbUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-12
 * @version V1.0.0
 */
@Table("sp_bill_grade_result")
public class SpBillGradeResult extends MyBatisBean{
	@SQLField(key=true)
	private String id;
	@SQLField("sum_score")
	private int sumScore;//总分
	@SQLField("bill_instance_id")
	private String billInstanceId;//对应表单实例id
	@SQLField("bill_config_id")
	private String billConfigId;//对应表单配置id
	@SQLField("config_id")
	private String configId; //对应评分事件id
	private List<SpBillGradeResultCondition> spBillGradeResultConditions;//具体项目
	
	private Map<Long, List<SpBillGradeResultCondition>> resultConditionMap;
	private Map<Long, Integer> scoreMap;
	
	private SpBillGradeLevel spBillGradeLevel;
	@SQLField
	private String condition_detail;
	/**
	 * 返回
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 返回
	 */
	public int getSumScore() {
		return sumScore;
	}
	/**
	 * 设置
	 * @param sumScore 
	 */
	public void setSumScore(int sumScore) {
		this.sumScore = sumScore;
	}
	/**
	 * 返回
	 */
	public String getBillInstanceId() {
		return billInstanceId;
	}
	/**
	 * 设置
	 * @param billInstanceId 
	 */
	public void setBillInstanceId(String billInstanceId) {
		this.billInstanceId = billInstanceId;
	}
	/**
	 * 返回
	 */
	public String getBillConfigId() {
		return billConfigId;
	}
	/**
	 * 设置
	 * @param billConfigId 
	 */
	public void setBillConfigId(String billConfigId) {
		this.billConfigId = billConfigId;
	}
	/**
	 * 返回
	 */
	public String getConfigId() {
		return configId;
	}
	/**
	 * 设置
	 * @param configId 
	 */
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	/**
	 * 返回
	 */
	public List<SpBillGradeResultCondition> getSpBillGradeResultConditions() {
		if(spBillGradeResultConditions == null){
			spBillGradeResultConditions = new ArrayList<SpBillGradeResultCondition>();
		}
		return spBillGradeResultConditions;
	}
	/**
	 * 设置
	 * @param spBillGradeResultConditions 
	 */
	public void setSpBillGradeResultConditions(
			List<SpBillGradeResultCondition> spBillGradeResultConditions) {
		this.spBillGradeResultConditions = spBillGradeResultConditions;
		resultConditionMap = null;
		scoreMap=null;
	}
	
	
	public void addSpBillGradeResultCondition(SpBillGradeResultCondition spBillGradeResultCondition){
		getSpBillGradeResultConditions().add(spBillGradeResultCondition);
		if(resultConditionMap==null){
			resultConditionMap = new HashMap<Long, List<SpBillGradeResultCondition>>();
		}
		List<SpBillGradeResultCondition> list=resultConditionMap.get(spBillGradeResultCondition.getBillClassId());
		if(list==null){
			list = new ArrayList<SpBillGradeResultCondition>();
			resultConditionMap.put(spBillGradeResultCondition.getBillClassId(), list);
		}
		list.add(spBillGradeResultCondition);
		scoreMap=null;
	}
	/**
	 * 返回
	 */
	public Map<Long,List<SpBillGradeResultCondition>> getResultConditionMap() {
		if(resultConditionMap==null){
			resultConditionMap = new HashMap<Long, List<SpBillGradeResultCondition>>();
			for (SpBillGradeResultCondition c : getSpBillGradeResultConditions()) {
				List<SpBillGradeResultCondition> list=resultConditionMap.get(c.getBillClassId());
				if(list==null){
					list = new ArrayList<SpBillGradeResultCondition>();
					resultConditionMap.put(c.getBillClassId(), list);
				}
				list.add(c);
			}
		}
		return resultConditionMap;
	}
	/**
	 * 返回
	 */
	public Map<Long, Integer> getScoreMap() {
		if(scoreMap==null){
			scoreMap = new HashMap<Long, Integer>();
			for (Long key : getResultConditionMap().keySet()) {
				List<SpBillGradeResultCondition> list=resultConditionMap.get(key);
				if(list!=null){
					int score = 0;
					for (SpBillGradeResultCondition c : list) {
						score+=c.getSumScore();
					}
					scoreMap.put(key, score);
				}
			}
		}
		return scoreMap;
	}
	
	/**
	 * 返回
	 */
	public String getCondition_detail() {
		condition_detail = JaxbUtil.getXmlFromObject(new SpBillGradeResultConditionList(spBillGradeResultConditions));
		return condition_detail;
	}
	/**
	 * 设置
	 * @param conditionDetail 
	 */
	public void setCondition_detail(String conditionDetail) {
		spBillGradeResultConditions = JaxbUtil.getObjectFromXml(conditionDetail, SpBillGradeResultConditionList.class)
		.getSpBillGradeResultConditions();
		condition_detail = conditionDetail;
	}
	
	@XmlRootElement
	static class SpBillGradeResultConditionList{
		
		private List<SpBillGradeResultCondition> spBillGradeResultConditions;
		
		SpBillGradeResultConditionList(){}
		
		SpBillGradeResultConditionList(
				List<SpBillGradeResultCondition> spBillGradeResultConditions) {
			this.spBillGradeResultConditions = spBillGradeResultConditions;
		}
		@XmlElement
		public List<SpBillGradeResultCondition> getSpBillGradeResultConditions() {
			return spBillGradeResultConditions;
		}

		public void setSpBillGradeResultConditions(
				List<SpBillGradeResultCondition> spBillGradeResultConditions) {
			this.spBillGradeResultConditions = spBillGradeResultConditions;
		}
	}

	/**
	 * 返回
	 */
	public SpBillGradeLevel getSpBillGradeLevel() {
		return spBillGradeLevel;
	}
	/**
	 * 设置
	 * @param spBillGradeLevel 
	 */
	public void setSpBillGradeLevel(SpBillGradeLevel spBillGradeLevel) {
		this.spBillGradeLevel = spBillGradeLevel;
	}
}
