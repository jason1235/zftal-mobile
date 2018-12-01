package com.zfsoft.dao.entities;

import com.zfsoft.common.query.ModelBase;

/**
 * 类描述：专业代码
 * 创建人：caozf
 * 创建时间：2012-6-11
 * @version 
 */
public class ZydmModel extends ModelBase {

	private static final long serialVersionUID = 3021173623687196606L;
	private String zydm_id;           //专业代码                     
	private String zydm;              //专业代码                                   
	private String zymc;              //专业名称                                   
	private String zyjc;              //专业简称                                   
	private String zyywmc;            //专业英文名称                               
	private String bmdm_id_lsbm;      //所属部门（关联部门代码ID）                 
	private String jcdm_id_xkml;      //关联基础代码表 DMLB=XKML                   
	private String xz;                //删除，培养方案实现                         
	private String jcdm_id_cc;        //基础代码表 DMLB=CC                         
	private String jcdm_id_gbzydm;    //基础代码表dmlb=bgzydm                      
	private String bmdm_id_tgxy;      //托管学院                                   
	private String sfty;              //是否停用（1是 0否）                        
	private String bz;                //备注                                       

	public String getZydm_id() {
		return zydm_id;
	}

	public void setZydm_id(String zydmId) {
		zydm_id = zydmId;
	}

	public String getZydm() {
		return zydm;
	}

	public void setZydm(String zydm) {
		this.zydm = zydm;
	}

	public String getZymc() {
		return zymc;
	}

	public void setZymc(String zymc) {
		this.zymc = zymc;
	}

	public String getZyjc() {
		return zyjc;
	}

	public void setZyjc(String zyjc) {
		this.zyjc = zyjc;
	}

	public String getZyywmc() {
		return zyywmc;
	}

	public void setZyywmc(String zyywmc) {
		this.zyywmc = zyywmc;
	}

	public String getBmdm_id_lsbm() {
		return bmdm_id_lsbm;
	}

	public void setBmdm_id_lsbm(String bmdmIdLsbm) {
		bmdm_id_lsbm = bmdmIdLsbm;
	}

	public String getJcdm_id_xkml() {
		return jcdm_id_xkml;
	}

	public void setJcdm_id_xkml(String jcdmIdXkml) {
		jcdm_id_xkml = jcdmIdXkml;
	}

	public String getXz() {
		return xz;
	}

	public void setXz(String xz) {
		this.xz = xz;
	}

	public String getJcdm_id_cc() {
		return jcdm_id_cc;
	}

	public void setJcdm_id_cc(String jcdmIdCc) {
		jcdm_id_cc = jcdmIdCc;
	}

	public String getJcdm_id_gbzydm() {
		return jcdm_id_gbzydm;
	}

	public void setJcdm_id_gbzydm(String jcdmIdGbzydm) {
		jcdm_id_gbzydm = jcdmIdGbzydm;
	}

	public String getBmdm_id_tgxy() {
		return bmdm_id_tgxy;
	}

	public void setBmdm_id_tgxy(String bmdmIdTgxy) {
		bmdm_id_tgxy = bmdmIdTgxy;
	}

	public String getSfty() {
		return sfty;
	}

	public void setSfty(String sfty) {
		this.sfty = sfty;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	
}
