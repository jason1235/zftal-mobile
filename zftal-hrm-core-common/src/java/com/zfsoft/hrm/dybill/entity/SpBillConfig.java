package com.zfsoft.hrm.dybill.entity;

import java.util.Date;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;
import com.zfsoft.hrm.dybill.enums.BillConfigStatus;
import com.zfsoft.hrm.dybill.enums.BillType;
import com.zfsoft.hrm.dybill.xml.XmlBillClasses;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.jaxb.JaxbUtil;
/**
 * 审批表单配置
 * @author Patrick Shen
 */
@Table("sp_bill_config")
public class SpBillConfig extends MyBatisBean{
	/**
	 * 表单编号
	 */
	@SQLField(key=true)
	private String id;
	/**
	 * 表单名称
	 */
	@SQLField
	private String name;
	/**
	 * 数据表名
	 */
	@SQLField("id_name")
	private String idName;
	/**
	 * 表单状态
	 */
	@SQLField
	private BillConfigStatus status=BillConfigStatus.INITIALIZE;
	/**
	 * 表单配置内容
	 */
	@SQLField
	private String content;
	/**
	 * 表单类型
	 */
	@SQLField("bill_type")
	private BillType billType=BillType.COMMIT;
	/**
	 * 创建日期
	 */
	@SQLField("create_date")
	private Date createDate;
	
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
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public BillType[] getBillTypes() {
		return BillType.values();
	}
	public BillType getBillType() {
		return billType;
	}
	public void setBillType(BillType billType) {
		this.billType = billType;
	}
	public BillConfigStatus getStatus() {
		return status;
	}
	public void setStatus(BillConfigStatus status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public XmlBillClasses getXmlBillClasses() {
		if (StringUtil.isNotEmpty(this.getContent())) {
			return JaxbUtil.getObjectFromXml(this.getContent(), XmlBillClasses.class);
		} else {
			return null;
		}
	}
	
	public void setXmlBillClasses(XmlBillClasses xmlBillClasses){
		this.setContent(JaxbUtil.getXmlFromObject(xmlBillClasses));
	}
	
}