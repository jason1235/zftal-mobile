package com.zfsoft.dao.entities;

import com.zfsoft.common.query.ModelBase;

/**
 * 类描述：部门代码(组织机构) 
 * 创建人：caozf 
 * 创建时间：2012-6-11 
 * @version
 */
public class BmdmModel extends ModelBase {

	private static final long serialVersionUID = -6130116296218495498L;
	private String bmdm_id; 	  // 部门代码
	private String bmmc; 		  // 部门名称
	private String bmdm_id_ls;    // 隶属部门(自关联部门代码ID)
	private String ywmc; 		  // 英文名称
	private String bmjc; 		  // 部门简称
	private String cydm_id_bmlb;  // 部门类型
	private String cydm_id_bmmc;  // 部门类型名称
	private String sfyx;          // 是否有效（1 有效 0 无效）
	private String sfst;          // 是否实体（1是 0否）
	private String bmfzr;         // 部门负责人工号
	private String kkxy;          // 开课学院或学生学院（1，开课学院 2，学生学院）

	public String getBmdm_id() {
		return bmdm_id;
	}

	public void setBmdm_id(String bmdmId) {
		bmdm_id = bmdmId;
	}

	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getBmdm_id_ls() {
		return bmdm_id_ls;
	}

	public void setBmdm_id_ls(String bmdmIdLs) {
		bmdm_id_ls = bmdmIdLs;
	}

	public String getYwmc() {
		return ywmc;
	}

	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}

	public String getBmjc() {
		return bmjc;
	}

	public void setBmjc(String bmjc) {
		this.bmjc = bmjc;
	}

	public String getCydm_id_bmlb() {
		return cydm_id_bmlb;
	}

	public void setCydm_id_bmlb(String cydmIdBmlb) {
		cydm_id_bmlb = cydmIdBmlb;
	}

	public String getSfyx() {
		return sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

	public String getSfst() {
		return sfst;
	}

	public void setSfst(String sfst) {
		this.sfst = sfst;
	}

	public String getBmfzr() {
		return bmfzr;
	}

	public void setBmfzr(String bmfzr) {
		this.bmfzr = bmfzr;
	}

	public String getKkxy() {
		return kkxy;
	}

	public void setKkxy(String kkxy) {
		this.kkxy = kkxy;
	}

	public String getCydm_id_bmmc() {
		return cydm_id_bmmc;
	}

	public void setCydm_id_bmmc(String cydmIdBmmc) {
		cydm_id_bmmc = cydmIdBmmc;
	}
	
}
