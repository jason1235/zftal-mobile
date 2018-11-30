package com.zfsoft.hrm.baseinfo.forminfo.entities;

import java.io.Serializable;

/**
 * 登记类别Entity
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-8
 * @version V1.0.0
 */
public class FormInfoType implements Serializable {

	private static final long serialVersionUID = 4516843834354244994L;

	private String guid;				//全局ID
	
	private String name;				//名称
	
	private Integer seq;
	

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
}
