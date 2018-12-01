package com.zfsoft.sjzx.jhpz.wspz.model;
/**
 * <p>WebService 交换参数设置</p>
 * @author wangjian
 *
 */
public class WsjhParamsConfig {

	private Integer jhpxh;//WebService交换参数编号
	private String jhmc;//WebService交换配置名称
	private String cszd;//参数字段
	private String csz;// 参数值
	private String cszdt;//参数类型
	private Integer jhlx;//交换类型
	

	public Integer getJhlx() {
		return jhlx;
	}
	public void setJhlx(Integer jhlx) {
		this.jhlx = jhlx;
	}
	public String getCszdt() {
		return cszdt;
	}
	public void setCszdt(String cszdt) {
		this.cszdt = cszdt;
	}
	public Integer getJhpxh() {
		return jhpxh;
	}
	public void setJhpxh(Integer jhpxh) {
		this.jhpxh = jhpxh;
	}
	public String getJhmc() {
		return jhmc;
	}
	public void setJhmc(String jhmc) {
		this.jhmc = jhmc;
	}
	public String getCszd() {
		return cszd;
	}
	public void setCszd(String cszd) {
		this.cszd = cszd;
	}
	public String getCsz() {
		return csz;
	}
	public void setCsz(String csz) {
		this.csz = csz;
	}
	
}
