package com.zfsoft.dao.entities;

import com.zfsoft.common.query.ModelBase;

/**
 * 
* 
* 类名称：JcsjModel 
* 类描述：基础数据MODEL
* 创建人：xucy 
* 创建时间：2012-4-13 下午01:46:43 
* 修改人：xucy 
* 修改时间：2012-4-13 下午01:46:43 
* 修改备注： 
* @version 
*
 */
public class JcsjModel extends ModelBase{

	private String lx;//类型代码
	private String dm;//基础数据代码
	private String mc;//基础数据名称
	
	private String lxdm;
	private String lxmc;//类型名称
	private String pkValue;
	
	public String getDm() {
		return dm;
	}
	public void setDm(String dm) {
		this.dm = dm;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public String getPkValue() {
		return pkValue;
	}
	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}
	public String getLxmc() {
		return lxmc;
	}
	public void setLxmc(String lxmc) {
		this.lxmc = lxmc;
	}
	public String getLx() {
		return lx;
	}
	public void setLx(String lx) {
		this.lx = lx;
	}
	public String getLxdm() {
		return lxdm;
	}
	public void setLxdm(String lxdm) {
		this.lxdm = lxdm;
	}
	
}
