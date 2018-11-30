package com.zfsoft.dao.entities;

import com.zfsoft.common.query.ModelBase;

/**
 * 类描述：常用代码(基础代码)
 * 创建人：caozf
 * 创建时间：2012-6-11
 * @version 
 */
public class CydmModel extends ModelBase {

	private static final long serialVersionUID = -7608724800053873404L;
	private String cydm_id; //代码ID
	private String dmlx;    //代码类型      
	private String dmmc;    //代码名称      
	private String dmbh;    //代码编号      
	private String sfyx;    //是否有效（1有效，0无效） 
	private String bz;      //备注 

	public String getCydm_id() {
		return cydm_id;
	}

	public void setCydm_id(String cydmId) {
		cydm_id = cydmId;
	}

	public String getDmlx() {
		return dmlx;
	}

	public void setDmlx(String dmlx) {
		this.dmlx = dmlx;
	}

	public String getDmmc() {
		return dmmc;
	}

	public void setDmmc(String dmmc) {
		this.dmmc = dmmc;
	}

	public String getDmbh() {
		return dmbh;
	}

	public void setDmbh(String dmbh) {
		this.dmbh = dmbh;
	}

	public String getSfyx() {
		return sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}
