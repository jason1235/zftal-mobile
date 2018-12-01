package com.zfsoft.hrm.dybill.entity;

import java.util.Date;
import java.util.List;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;
import com.zfsoft.hrm.dybill.enums.BillInstanceStatus;
import com.zfsoft.hrm.dybill.xml.XmlValueClasses;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.jaxb.JaxbUtil;
/**
 * 审批表单实例
 * @author Patrick Shen
 */
@Table("sp_bill_instance")
public class SpBillInstance extends MyBatisBean{
	/**
	 * 表单编号
	 */
	@SQLField(key=true)
	private String id;
	/**
	 * 表单配置编号
	 */
	@SQLField("bill_config_id")
	private String billConfigId;
	@SQLField
	private Integer version;
	/**
	 * 表单状态
	 */
	@SQLField
	private BillInstanceStatus status=BillInstanceStatus.INITIALIZE;
	/**
	 * 表单填写值
	 */
	@SQLField
	private String content;
	/**
	 * 创建日期
	 */
	@SQLField("create_date")
	private Date createDate;
	/**
	 * 提交日期
	 */
	@SQLField("commit_date")
	private Date commitDate;
	
	private List<String> ids;//查询用
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getBillConfigId() {
		return billConfigId;
	}
	
	public void setBillConfigId(String billConfigId) {
		this.billConfigId = billConfigId;
	}
	public BillInstanceStatus getStatus() {
		return status;
	}
	public void setStatus(BillInstanceStatus status) {
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
	public Date getCommitDate() {
		return commitDate;
	}
	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}
	public XmlValueClasses getXmlValueClasses(){
		return JaxbUtil.getObjectFromXml(this.getContent(),
				XmlValueClasses.class);
	}
	
	public void setXmlValueClasses(XmlValueClasses xmlValueClasses){
		this.setContent(JaxbUtil.getXmlFromObject(xmlValueClasses));
	}
	
	private String switchTableName;
	
	@Override
	public String getTableName(){
		if(StringUtil.isEmpty(switchTableName)){
			return super.getTableName();
		}
		return "bill_"+switchTableName;
	}
	
	public void setTableName(String switchTableName){
		this.switchTableName=switchTableName;
	}
}