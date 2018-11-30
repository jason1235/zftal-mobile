package com.zfsoft.mobile.webservices.entity;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

public class MemoDB extends BaseQuery{

	//private String  memoId;				//备忘录id
	private String	memoCatalogId;		//备忘录类别id
	private String 	memoTitle;			//备忘录标题
	private String 	memoFileName;		//备忘录存储文件名称
	private byte[] 	memoContent;		//文件内容
	private String 	createTime;			//上传日期
	private String 	memoPath;			//备忘录存储文件路径
	private String 	userName;			//上传用户
	private String 	memoCatalogName; 	//备忘类别名称
	private String  contentFlag;		//图文标志位,1为文，2为图，3为图文
	public String getMemoCatalogId() {
		return memoCatalogId;
	}
	public void setMemoCatalogId(String memoCatalogId) {
		this.memoCatalogId = memoCatalogId;
	}
	public String getMemoTitle() {
		return memoTitle;
	}
	public void setMemoTitle(String memoTitle) {
		this.memoTitle = memoTitle;
	}
	public String getMemoFileName() {
		return memoFileName;
	}
	public void setMemoFileName(String memoFileName) {
		this.memoFileName = memoFileName;
	}
	public byte[] getMemoContent() {
		return memoContent;
	}
	public void setMemoContent(byte[] memoContent) {
		this.memoContent = memoContent;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMemoPath() {
		return memoPath;
	}
	public void setMemoPath(String memoPath) {
		this.memoPath = memoPath;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setMemoCatalogName(String memoCatalogName) {
		this.memoCatalogName = memoCatalogName;
	}
	public String getMemoCatalogName() {
		return memoCatalogName;
	}
	public void setContentFlag(String contentFlag) {
		this.contentFlag = contentFlag;
	}
	public String getContentFlag() {
		return contentFlag;
	}
	
	
}
