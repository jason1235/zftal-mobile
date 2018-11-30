package com.zfsoft.dao.entities;

import com.zfsoft.common.query.QueryModel;

/**
 * 
 * 类名称：SjfwdxbModel 
 * 类描述：数据范围对象 
 * 创建人：caozf 
 * 创建时间：2012-7-10
 */
public class SjfwdxModel {

	private static final long serialVersionUID = 2159801764630979101L;
	private String sjfwdx_id; // 数据范围对象ID
	private String bm; 		   // 表名
	private String zddm;       // 字段代码
	private String zdmc;       // 字段名称
	private String zwmc;       // 中文名称
	private String xssx;       // 显示顺序
	private String sfqy; 	   // 是否启用
	
	//private Integer resultType; //myPagination分页插件特殊使用，用于一个页面多种分页的情况
	
	/**以下页面查询用**/
	private String ls_bmdm;    //所属学院
	private String ls_zydm;	   //所属专业
	
	public QueryModel queryModel = new QueryModel();
	
	public String getSjfwdx_id() {
		return sjfwdx_id;
	}

	public void setSjfwdx_id(String sjfwdxId) {
		sjfwdx_id = sjfwdxId;
	}

	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	public String getZddm() {
		return zddm;
	}

	public void setZddm(String zddm) {
		this.zddm = zddm;
	}

	public String getZdmc() {
		return zdmc;
	}

	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}

	public String getZwmc() {
		return zwmc;
	}

	public void setZwmc(String zwmc) {
		this.zwmc = zwmc;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public String getSfqy() {
		return sfqy;
	}

	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}

	public QueryModel getQueryModel() {
		return queryModel;
	}

	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}

	public String getLs_bmdm() {
		return ls_bmdm;
	}

	public void setLs_bmdm(String lsBmdm) {
		ls_bmdm = lsBmdm;
	}

	public String getLs_zydm() {
		return ls_zydm;
	}

	public void setLs_zydm(String lsZydm) {
		ls_zydm = lsZydm;
	}

}
