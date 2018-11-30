package com.zfsoft.hrm.file.query;

import java.io.Serializable;

/**
 * 附件查询条件
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-10-19
 * @version V1.0.0
 */
public class AttachementQuery implements Serializable {
	
	private static final long serialVersionUID = 8319334141209528486L;
	
	private String formId;	//表单ID

	/**
	 * 返回表单ID
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * 设置表单ID
	 * @param formId 表单ID 
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	
	
}