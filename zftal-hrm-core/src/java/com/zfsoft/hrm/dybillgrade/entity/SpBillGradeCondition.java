package com.zfsoft.hrm.dybillgrade.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngineManager;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybillgrade.enums.GradePropertyLogicalEnum;
import com.zfsoft.hrm.dybillgrade.enums.GradePropertyOperatorEnum;
import com.zfsoft.util.jaxb.JaxbUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-5
 * @version V1.0.0
 */
@Table("sp_bill_grade_cindition")
public class SpBillGradeCondition extends MyBatisBean{
	@SQLField(key=true)
	private String id;
	private List<SpBillGradeConditionProperty> properties;
	@SQLField("config_id")
	private String configId;
	@SQLField("bill_class_id")
	private String billClassId;
	@SQLField
	private int num=1;
	@SQLField
	private int score;
	@SQLField
	private String rule_detail;

	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	public List<SpBillGradeConditionProperty> getProperties() {
		return properties;
	}
	public void setProperties(List<SpBillGradeConditionProperty> properties) {
		this.properties = properties;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getBillClassId() {
		return billClassId;
	}
	public void setBillClassId(String billClassId) {
		this.billClassId = billClassId;
	}
	public void setConfig_id(String configId) {
		this.configId = configId;
	}
	public void setBill_class_id(String billClassId) {
		this.billClassId = billClassId;
	}

	public boolean runCondition(boolean run,Map<Long, String> data){
		String result="";
		String logicalRel = "";
		for (SpBillGradeConditionProperty p : properties) {
			result+=logicalRel;
			result+=p.getParenthesisBefore();
			Boolean flag=true;
			if(run) flag = p.runConditionProperties(data.get(p.getPropertyId()));
			result += flag.toString();
			result+=p.getParenthesisAfter();
			logicalRel=GradePropertyLogicalEnum.getGradePropertyLogicalEnum(p.getLogicalRel()).getRel();
		}
		try {
			if("true".equals(new ScriptEngineManager().getEngineByName("js").eval(result).toString()))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	/**
	 * 返回
	 */
	public String getRule_detail() {
		rule_detail = JaxbUtil.getXmlFromObject(new SpBillGradeConditionPropertyList(properties));
		return rule_detail;
	}
	/**
	 * 设置
	 * @param ruleDetail 
	 */
	public void setRule_detail(String ruleDetail) {
		properties = JaxbUtil.getObjectFromXml(ruleDetail, SpBillGradeConditionPropertyList.class)
			.getSpBillGradeConditionProperties();
		rule_detail = ruleDetail;
	}
	
	public void fillProperties(Map<Long,XmlBillProperty> map){
		List<SpBillGradeConditionProperty> list = new ArrayList<SpBillGradeConditionProperty>();
		for (SpBillGradeConditionProperty p : properties) {
			XmlBillProperty bp = map.get(p.getPropertyId());
			if(bp!=null){
				p.setBillProperty(bp);
				list.add(p);
			}
		}
		properties = list;
	}
	
	public String getText(){
		StringBuffer result=new StringBuffer();
		String logicalRel = "";
		for (SpBillGradeConditionProperty p : properties) {
			if(null == p.getBillProperty()){
				continue;
			}
			result.append(logicalRel);
			result.append(p.getParenthesisBefore());
			result.append(" ");
			result.append(p.getBillProperty().getName());
			result.append(" ");
			result.append(GradePropertyOperatorEnum.getGradePropertyOperatorEnum(p.getOperator()).getText());
			result.append(" ");
			result.append("'"+p.getFieldValueString()+"'");
			result.append(" ");
			result.append(p.getParenthesisAfter());
			logicalRel=" "+GradePropertyLogicalEnum.getGradePropertyLogicalEnum(p.getLogicalRel()).getText()+" ";
		}
		return result.toString();
	}


	@XmlRootElement
	static class SpBillGradeConditionPropertyList{
		
		private List<SpBillGradeConditionProperty> spBillGradeConditionProperties;
		
		SpBillGradeConditionPropertyList(){}
		
		SpBillGradeConditionPropertyList(
				List<SpBillGradeConditionProperty> spBillGradeConditionProperties) {
			this.spBillGradeConditionProperties = spBillGradeConditionProperties;
		}
		@XmlElement
		public List<SpBillGradeConditionProperty> getSpBillGradeConditionProperties() {
			return spBillGradeConditionProperties;
		}

		public void setSpBillGradeConditionProperties(
				List<SpBillGradeConditionProperty> spBillGradeConditionProperties) {
			this.spBillGradeConditionProperties = spBillGradeConditionProperties;
		}
	}

}




