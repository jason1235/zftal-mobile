package com.zfsoft.hrm.report.xentity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author ChenMinming
 * @date 2015-1-16
 * @version V1.0.0
 */
@XmlRootElement
public class ReportFieldConfig {
	private List<ReportViewField> fields = new ArrayList<ReportViewField>();

	/**
	 * 返回
	 */
	@XmlElement(name="fields")
	public List<ReportViewField> getFields() {
		return fields;
	}

	/**
	 * 设置
	 * @param fields 
	 */
	public void setFields(List<ReportViewField> fields) {
		this.fields = fields;
	}
}
