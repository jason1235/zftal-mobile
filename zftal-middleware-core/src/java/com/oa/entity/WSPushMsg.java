package com.oa.entity;

import java.util.Date;
import java.util.Map;

/**
 * 
 * @author ChenMinming
 * @date 2015-4-23
 * @version V1.0.0
 */
public class WSPushMsg {
	private String tsbt;//推送标题
	private String tsnr;//推送内容
	private String tsry;//推送人员
	private String tsfs;//推送方式
	private String[] tsdx;//推送对象 
	private String tspt;//推送平台   iOS 开发环境     iOS 生产环境    Android    WinPhone
	private String tsdxlx;//推送对象类型   设备标签(Tag)  设备别名(Alias)  Registration ID
	private String timeToLive;//离线保留时长
	private String appType;
	private String tswds;//推送未读书总和
	private Map<String, String> extras;
	
	public String getTsbt() {
		return tsbt;
	}
	public void setTsbt(String tsbt) {
		this.tsbt = tsbt;
	}
	public String getTsnr() {
		return tsnr;
	}
	public void setTsnr(String tsnr) {
		this.tsnr = tsnr;
	}
	public String getTsry() {
		return tsry;
	}
	public void setTsry(String tsry) {
		this.tsry = tsry;
	}
	public String getTsfs() {
		return tsfs;
	}
	public void setTsfs(String tsfs) {
		this.tsfs = tsfs;
	}
	public String[] getTsdx() {
		return tsdx;
	}
	public void setTsdx(String[] tsdx) {
		this.tsdx = tsdx;
	}
	public String getTspt() {
		return tspt;
	}
	public void setTspt(String tspt) {
		this.tspt = tspt;
	}
	public String getTsdxlx() {
		return tsdxlx;
	}
	public void setTsdxlx(String tsdxlx) {
		this.tsdxlx = tsdxlx;
	}
	/**
	 * 返回
	 */
	public String getTimeToLive() {
		return timeToLive;
	}
	/**
	 * 设置
	 * @param timeToLive 
	 */
	public void setTimeToLive(String timeToLive) {
		this.timeToLive = timeToLive;
	}
	/**
	 * 返回
	 */
	public Map<String, String> getExtras() {
		return extras;
	}
	/**
	 * 设置
	 * @param extras 
	 */
	public void setExtras(Map<String, String> extras) {
		this.extras = extras;
	}
	/**
	 * 返回
	 */
	public String getAppType() {
		return appType;
	}
	/**
	 * 设置
	 * @param appType 
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public void setTswds(String tswds) {
		this.tswds = tswds;
	}
	public String getTswds() {
		return tswds;
	}
}
