package com.zfsoft.hrm.report.xentity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 报表条件列
 * @author 沈鲁威 Patrick Shen
 * @since 2012-9-13
 * @version V1.0.0
 */
@XmlRootElement
public class Item extends XmlTitle{
	
	private String condition;
	
	@XmlAttribute
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
}
