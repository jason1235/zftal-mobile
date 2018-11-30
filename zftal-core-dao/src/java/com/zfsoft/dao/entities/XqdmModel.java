package com.zfsoft.dao.entities;

import com.zfsoft.common.query.ModelBase;

/**
 * 类描述：校区代码
 * 创建人：caozf
 * 创建时间：2012-6-11
 * @version 
 */
public class XqdmModel extends ModelBase {

	private static final long serialVersionUID = 3386656514060517042L;
	private String xqdm_id; //校区代码   
	private String xqdm;    //校区代码                 
	private String xqmc;    //校区名称                 
	private String xqdz;    //校区地址                 

	public String getXqdm_id() {
		return xqdm_id;
	}

	public void setXqdm_id(String xqdmId) {
		xqdm_id = xqdmId;
	}

	public String getXqdm() {
		return xqdm;
	}

	public void setXqdm(String xqdm) {
		this.xqdm = xqdm;
	}

	public String getXqmc() {
		return xqmc;
	}

	public void setXqmc(String xqmc) {
		this.xqmc = xqmc;
	}

	public String getXqdz() {
		return xqdz;
	}

	public void setXqdz(String xqdz) {
		this.xqdz = xqdz;
	}

}
