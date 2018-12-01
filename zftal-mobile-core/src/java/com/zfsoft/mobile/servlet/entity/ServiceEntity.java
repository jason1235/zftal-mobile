package com.zfsoft.mobile.servlet.entity;



public class ServiceEntity {

	public String id;//id，唯一性标识
	public String type;//服务类型
	public String name;//名称
	public String icon;//图标
	public String url;//url
	public String androidUrl;//安卓url
	public String iosUrl;//苹果url
	public String wechatUrl;//微信url
	public String serviceCode;//服务编码
	public String apkdownUrl;//apk下载地址
	public String apkFileName;//apk文件名
	public String apkPackage;//apk包名
	public String urlScheme;//ios参数
	public String urliTunes;//ios参数
	public String procode;//系统procode编码，业务系统需要
	public String otherFlag;//第三方标识
	public String bak;//备用字段
	public String moduletype;//
	public String isCommon;//是否通用
	public String isSignal;//是否单点
	public String recommendFlag;//是否推荐位
	public String signalUrl;//单点相对地址
	public String signalXtbm;
	@Override
	public String toString() {
		return "ServiceEntity [id=" + id + ", type=" + type + ", name=" + name
				+ ", icon=" + icon + ", url=" + url + ", androidUrl="
				+ androidUrl + ", iosUrl=" + iosUrl + ", wechatUrl="
				+ wechatUrl + ", serviceCode=" + serviceCode + ", apkdownUrl="
				+ apkdownUrl + ", apkFileName=" + apkFileName + ", apkPackage="
				+ apkPackage + ", urlScheme=" + urlScheme + ", urliTunes="
				+ urliTunes + ", procode=" + procode + ", otherFlag="
				+ otherFlag + ", bak=" + bak + ", moduletype=" + moduletype
				+ ", isCommon=" + isCommon + ", isSignal=" + isSignal
				+ ", recommendFlag=" + recommendFlag + "]";
	}
	
	
}
