package com.zfsoft.dao.entities;

import com.zfsoft.common.query.ModelBase;

/**
 * 
* 
* 类名称：XsmmModel 
* 类描述：基础数据MODEL
* 创建人：xucy 
* 创建时间：2012-4-17 下午01:46:43 
* @version 
*
 */
public class XsmmModel extends ModelBase{
	private static final long serialVersionUID = 5663475400106803986L;
	private String xh;//学号
	private String mm;//密码
	private String jmmm;//加密之后密码
	private String xm;//姓名
	private String nj;//年纪
	private String xy;//学院
	private String zy;//专业
	private String bjdm_id;//班级
	private String pkValue;
	private String gzlx;
	private String sfzh;
	
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public String getMm() {
		return mm;
	}
	public void setMm(String mm) {
		this.mm = mm;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getNj() {
		return nj;
	}
	public void setNj(String nj) {
		this.nj = nj;
	}
	public String getXy() {
		return xy;
	}
	public void setXy(String xy) {
		this.xy = xy;
	}
	public String getZy() {
		return zy;
	}
	public void setZy(String zy) {
		this.zy = zy;
	}
	public String getBjdm_id() {
		return bjdm_id;
	}
	public void setBjdm_id(String bjdmId) {
		bjdm_id = bjdmId;
	}
	public String getPkValue() {
		return pkValue;
	}
	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}
	public String getGzlx() {
		return gzlx;
	}
	public void setGzlx(String gzlx) {
		this.gzlx = gzlx;
	}
	public String getSfzh() {
		return sfzh;
	}
	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	public String getJmmm() {
		return jmmm;
	}
	public void setJmmm(String jmmm) {
		this.jmmm = jmmm;
	}
}
