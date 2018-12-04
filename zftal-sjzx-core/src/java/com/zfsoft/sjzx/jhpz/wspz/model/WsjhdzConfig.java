package com.zfsoft.sjzx.jhpz.wspz.model;
/**
 * <p>WebService 交换字段配置表</p>
 * @author wangjian
 *
 */
public class WsjhdzConfig {

	private Integer  zdpzxh;//字段配置编号
	private String   mbzd;   //目标字段
	private Integer  jhpzxh;
	private String   sfzj;//是否主键
	private Integer  mbzdsjlx;//目标字段数据类型
	private String   sfgx;//是否更新
	private String   jhmc;  //交换配置名称
	private String   fhbl;//返回变量
	private Integer jhlx;//交换类型


	public Integer getJhlx() {
		return jhlx;
	}
	public void setJhlx(Integer jhlx) {
		this.jhlx = jhlx;
	}
	public String getFhbl() {
		return fhbl;
	}
	public void setFhbl(String fhbl) {
		this.fhbl = fhbl;
	}
	public Integer getZdpzxh() {
		return zdpzxh;
	}
	public void setZdpzxh(Integer zdpzxh) {
		this.zdpzxh = zdpzxh;
	}

	public String getMbzd() {
		return mbzd;
	}
	public void setMbzd(String mbzd) {
		this.mbzd = mbzd;
	}
	public Integer getJhpzxh() {
		return jhpzxh;
	}
	public void setJhpzxh(Integer jhpzxh) {
		this.jhpzxh = jhpzxh;
	}
	public String getSfzj() {
		return sfzj;
	}
	public void setSfzj(String sfzj) {
		this.sfzj = sfzj;
	}

	public Integer getMbzdsjlx() {
		return mbzdsjlx;
	}
	public void setMbzdsjlx(Integer mbzdsjlx) {
		this.mbzdsjlx = mbzdsjlx;
	}
	public String getSfgx() {
		return sfgx;
	}
	public void setSfgx(String sfgx) {
		this.sfgx = sfgx;
	}
	public String getJhmc() {
		return jhmc;
	}
	public void setJhmc(String jhmc) {
		this.jhmc = jhmc;
	}


}
