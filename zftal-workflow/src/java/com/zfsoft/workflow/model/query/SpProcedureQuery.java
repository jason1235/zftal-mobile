package com.zfsoft.workflow.model.query;

import com.zfsoft.dao.query.BaseQuery;

public class SpProcedureQuery extends BaseQuery {
	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = 6285825924949352868L;
	private String plink;   //链接
	private String pbelongToSysName;   //业务模块
	private String pname;  //流程名称
	private String pstatus; //状态
	private String pdesc; //状态

	public String getPdesc() {
		return pdesc;
	}
	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}
	public String getPlink() {
		return plink;
	}
	public String getPname() {
		return pname;
	}
	public String getPstatus() {
		return pstatus;
	}
	public void setPlink(String plink) {
		this.plink = plink;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public void setPstatus(String pstatus) {
		this.pstatus = pstatus;
	}
	/**
	 * 返回
	 */
	public String getPbelongToSysName() {
		return pbelongToSysName;
	}
	/**
	 * 设置
	 * @param pbelongToSysName 
	 */
	public void setPbelongToSysName(String pbelongToSysName) {
		this.pbelongToSysName = pbelongToSysName;
	}
}
