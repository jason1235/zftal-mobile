package com.zfsoft.sjzx.jhpz.wspz.model;
/**
 * <p>WebService 交换结果</p>
 * @author wangjian
 *
 */
public class WsjhResult {

	private String mbzd;//源字段
	private String value;//返回值
	private Integer sfzj;//是否主键
	private Integer mbzdsjlx;//数据类型
	
	public Integer getMbzdsjlx() {
		return mbzdsjlx;
	}
	public void setMbzdsjlx(Integer mbzdsjlx) {
		this.mbzdsjlx = mbzdsjlx;
	}
	public String getMbzd() {
		return mbzd;
	}
	public void setMbzd(String mbzd) {
		this.mbzd = mbzd;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getSfzj() {
		return sfzj;
	}
	public void setSfzj(Integer sfzj) {
		this.sfzj = sfzj;
	}
	
	
}
