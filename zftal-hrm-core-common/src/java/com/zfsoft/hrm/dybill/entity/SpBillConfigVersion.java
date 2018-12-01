package com.zfsoft.hrm.dybill.entity;

import java.util.Date;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;
/**
 * 审批表单版本
 * @author Patrick Shen
 */
@Table("sp_bill_config_version")
public class SpBillConfigVersion extends MyBatisBean{
	/**
	 * 表单编号
	 */
	@SQLField(key=true)
	private String id;
	/**
	 * 表单名称
	 */
	@SQLField("bill_config_id")
	private String billConfigId;
	/**
	 * 版本
	 */
	@SQLField
	private Integer version=0;
	/**
	 * 表单配置内容
	 */
	@SQLField
	private String content;
	/**
	 * 创建日期
	 */
	@SQLField("create_date")
	private Date createDate;
	/**
	 * 数据表名（先冗余记录， 暂时不用）
	 */
	@SQLField("id_name")
	private String idName;
	
	
	public String getBillConfigId() {
		return billConfigId;
	}
	public void setBillConfigId(String billConfigId) {
		this.billConfigId = billConfigId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	
}