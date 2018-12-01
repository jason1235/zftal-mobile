package com.zfsoft.dao.entities;

import com.zfsoft.dao.query.BaseQuery;

public class NewJsglModel extends BaseQuery{

	private String jsdm;//角色代码
	
	private String jsmc;//角色名称
	
	private String jscjrxm;//角色创建人
	
	private String yhnum;//用户数

	public String getJsdm() {
		return jsdm;
	}

	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}

	public String getJsmc() {
		return jsmc;
	}

	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}

	public String getJscjrxm() {
		return jscjrxm;
	}

	public void setJscjrxm(String jscjrxm) {
		this.jscjrxm = jscjrxm;
	}

	public String getYhnum() {
		return yhnum;
	}

	public void setYhnum(String yhnum) {
		this.yhnum = yhnum;
	}
	
	
}
