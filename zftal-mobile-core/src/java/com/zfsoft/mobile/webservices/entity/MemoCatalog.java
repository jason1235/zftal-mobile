package com.zfsoft.mobile.webservices.entity;

public class MemoCatalog {
	private String	memoCatalogId;		//备忘录类别id
	private String 	memoCatalogName;	//备忘录类别名称
	private String 	userName;		    //上传用户
	private String 	catalogColor;		//类别颜色
	private int  sortNumber;
	public String getMemoCatalogId() {
		return memoCatalogId;
	}
	public void setMemoCatalogId(String memoCatalogId) {
		this.memoCatalogId = memoCatalogId;
	}
	public String getMemoCatalogName() {
		return memoCatalogName;
	}
	public void setMemoCatalogName(String memoCatalogName) {
		this.memoCatalogName = memoCatalogName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCatalogColor() {
		return catalogColor;
	}
	public void setCatalogColor(String catalogColor) {
		this.catalogColor = catalogColor;
	}
	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}
	public int getSortNumber() {
		return sortNumber;
	}
	
	
}
