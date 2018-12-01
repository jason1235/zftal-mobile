package com.zfsoft.mobile.pushmsg.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.util.base.StringUtil;

public class PushMsg {
	private String tsid;//主键
	private Date tssj;//推送时间
	private String tsbt;//推送标题
	private String tsnr;//推送内容
	private String tsry;//推送人员
	private String tsfs;//推送方式 0.消息 1.通知
	private String tsdx;//推送对象 
	private String[] tsdxArray;//推送对象数组
	private String tspt;//推送平台   iOS 开发环境     iOS 生产环境    Android    WinPhone
	private String tsdxlx;//推送对象类型   设备标签(Tag)  设备别名(Alias)  Registration ID
	private String tsjg;//推送结果
	private Long timeToLive;//离线保留时长
	private String appType;//app类型
	private String appDetail;//app类型
	private String tswds;//推送未读书总和
	private String tssjStr;//推送时间字符
	private Map<String, String> extras;
	private String extrasStr;
	
	
	
	public String getTssjStr() {
		return tssjStr;
	}
	public void setTssjStr(String tssjStr) {
		this.tssjStr = tssjStr;
	}
	public String getTsid() {
		return tsid;
	}
	public void setTsid(String tsid) {
		this.tsid = tsid;
	}

	public Date getTssj() {
		return tssj;
	}
	public void setTssj(Date tssj) {
		if(tssj != null){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			this.tssjStr = formatter.format(tssj);
		}
		this.tssj = tssj;
	}
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
	public String getTsdx() {
		return tsdx;
	}
	public void setTsdx(String tsdx) {
		tsdxArray = null;
		this.tsdx = tsdx;
	}
	public void setTsdxArray(String[] tsdxArray) {
		this.tsdxArray = tsdxArray;
	}
	public String[] getTsdxArray() {
		if(tsdxArray!=null) return tsdxArray;
		if(tsdx==null) 
			tsdxArray =  new String[0];
		else 
			tsdxArray = tsdx.split(",");
		return tsdxArray;
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
	public String getTsjg() {
		return tsjg;
	}
	public void setTsjg(String tsjg) {
		this.tsjg = tsjg;
	}
	
	public boolean isNotice(){
		return "1".equals(tsfs);
	}
	/**
	 * 返回
	 */
	public long getTimeToLive() {
		if(timeToLive==null)
			try{
				timeToLive=Long.valueOf(SubSystemHolder.getPropertiesValue("time_to_live"));
			}catch (Exception e) {
				timeToLive=86400L;
			}
		return timeToLive;
	}
	/**
	 * 设置
	 * @param timeToLive 
	 */
	public void setTimeToLive(long timeToLive) {
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
	 * 设置
	 * @param timeToLive 
	 */
	public void setTimeToLive(Long timeToLive) {
		this.timeToLive = timeToLive;
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
	/**
	 * 返回
	 */
	public String getAppDetail() {
		return appDetail;
	}
	/**
	 * 设置
	 * @param appDetail 
	 */
	public void setAppDetail(String appDetail) {
		this.appDetail = appDetail;
	}
	public void setTswds(String tswds) {
		this.tswds = tswds;
	}
	public String getTswds() {
		return tswds;
	}
	public void setExtrasStr(String extrasStr) {
		this.extrasStr = extrasStr;
	}
	public String getExtrasStr() {
		return extrasStr;
	}
}
