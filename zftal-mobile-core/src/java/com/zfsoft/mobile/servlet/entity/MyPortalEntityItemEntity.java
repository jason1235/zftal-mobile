package com.zfsoft.mobile.servlet.entity;

public class MyPortalEntityItemEntity {
	public String id;//id唯一性标识
	public String serviceType;//服务类型
	public String name;//选项名称
	public String itemName;//选项行提示语
	public String itemValue;//选项行提示值
	public String itemUnit;//选项行提示值的单位
	public String icon;//图标
	public String url;//url链接
	//public String androidurl;
	//public String iosurl;
	//public String wxurl;
	public String serviceCode;//服务编码
	public String bak;//备用字段
	public String tsgn;//特色功能
	public String isfx;//是否我的发现服务
	
	@Override
	public String toString() {
		return "MyPortalEntityItemEntity [id=" + id + ", serviceType=" + serviceType
				+ ", name=" + name + ", itemName=" + itemName + ", itemValue="
				+ itemValue + ", itemUnit=" + itemUnit + ", icon=" + icon
				+ ", url=" + url + ", serviceCode=" + serviceCode + ", bak="
				+ bak + "]";
	}
	
	
}
