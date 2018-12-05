package com.zfsoft.sjzx.jhpz.wspz.model;

import java.io.Serializable;

/**
 * <p>WebService 交换定时器配置表</p>
 * @author wangjian
 *
 */
public class WsjhDsqConfig implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -246811103529818776L;
	private Integer dsqxh; //定时器序号
	private String  dsqmc;//定时器名称
	private String  cjsj;
	private String  xgsj;
	private String  jhpzmc;
	private String  ddsjd;
	private Integer  jgsj;
	private String  jdqssj;
	private String  jdjssj;
	private Integer czdxbh;
	private Integer dsqlx;
	private Integer sjdwbh;
	private String  dsqlxmc;
	private String  dsqzt;
	private String  jdzxjh;
	private Integer jhlx;//交换类型
	private String sjdwmc;



	public String getSjdwmc() {
		return sjdwmc;
	}
	public void setSjdwmc(String sjdwmc) {
		this.sjdwmc = sjdwmc;
	}
	public Integer getJhlx() {
		return jhlx;
	}
	public void setJhlx(Integer jhlx) {
		this.jhlx = jhlx;
	}
	public String getDsqlxmc() {
		return dsqlxmc;
	}
	public void setDsqlxmc(String dsqlxmc) {
		this.dsqlxmc = dsqlxmc;
	}
	public Integer getDsqxh() {
		return dsqxh;
	}
	public void setDsqxh(Integer dsqxh) {
		this.dsqxh = dsqxh;
	}
	public String getDsqmc() {
		return dsqmc;
	}
	public void setDsqmc(String dsqmc) {
		this.dsqmc = dsqmc;
	}
	public String getCjsj() {
		return cjsj;
	}
	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}
	public String getXgsj() {
		return xgsj;
	}
	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}
	public String getJhpzmc() {
		return jhpzmc;
	}
	public void setJhpzmc(String jhpzmc) {
		this.jhpzmc = jhpzmc;
	}
	public String getDdsjd() {
		return ddsjd;
	}
	public void setDdsjd(String ddsjd) {
		this.ddsjd = ddsjd;
	}

	public Integer getJgsj() {
		return jgsj;
	}
	public void setJgsj(Integer jgsj) {
		this.jgsj = jgsj;
	}
	public String getJdqssj() {
		return jdqssj;
	}
	public void setJdqssj(String jdqssj) {
		this.jdqssj = jdqssj;
	}
	public String getJdjssj() {
		return jdjssj;
	}
	public void setJdjssj(String jdjssj) {
		this.jdjssj = jdjssj;
	}
	public Integer getCzdxbh() {
		return czdxbh;
	}
	public void setCzdxbh(Integer czdxbh) {
		this.czdxbh = czdxbh;
	}
	public Integer getDsqlx() {
		return dsqlx;
	}
	public void setDsqlx(Integer dsqlx) {
		this.dsqlx = dsqlx;
	}
	public Integer getSjdwbh() {
		return sjdwbh;
	}
	public void setSjdwbh(Integer sjdwbh) {
		this.sjdwbh = sjdwbh;
	}
	public String getDsqzt() {
		return dsqzt;
	}
	public void setDsqzt(String dsqzt) {
		this.dsqzt = dsqzt;
	}
	public String getJdzxjh() {
		return jdzxjh;
	}
	public void setJdzxjh(String jdzxjh) {
		this.jdzxjh = jdzxjh;
	}
}
