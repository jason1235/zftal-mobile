package com.zfsoft.hrm.dybill.entity;

import java.util.Date;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;
import com.zfsoft.hrm.dybill.xml.XmlValueClasses;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.jaxb.JaxbUtil;

/**
 * 
 * @author ChenMinming
 * @date 2013-8-27
 * @version V1.0.0
 */
@Table("sp_bill_instance_log")
public class SpBillInstanceLog extends MyBatisBean{
	/**
	 * 日志编号
	 */
	@SQLField(key=true)
	private String id;
	/**
	 * 表单配置编号
	 */
	@SQLField("instance_id")
	private String billInstanceId;
	/**
	 * 更新表单值
	 */
	@SQLField
	private String content;
	/**
	 * 修改日期
	 */
	@SQLField("modify_date")
	private Date modifyDate;
	/**
	 * 修改日期
	 */
	@SQLField("operator_")
	private String operator;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getBillInstanceId() {
		return billInstanceId;
	}
	
	public void setBillInstanceId(String billInstanceId) {
		this.billInstanceId = billInstanceId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public XmlValueClasses getXmlValueClasses(){
		return JaxbUtil.getObjectFromXml(this.getContent(),
				XmlValueClasses.class);
	}
	
	public void setXmlValueClasses(XmlValueClasses xmlValueClasses){
		this.setContent(JaxbUtil.getXmlFromObject(xmlValueClasses));
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	private String switchTableName;
	
	@Override
	public String getTableName(){
		if(StringUtil.isEmpty(switchTableName)){
			return super.getTableName();
		}
		return "bill_"+switchTableName+"_log";
	}
	
	public void setTableName(String switchTableName){
		this.switchTableName=switchTableName;
	}
}
