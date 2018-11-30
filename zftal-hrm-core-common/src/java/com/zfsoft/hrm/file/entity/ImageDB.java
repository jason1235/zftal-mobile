package com.zfsoft.hrm.file.entity;

import java.util.Date;

public class ImageDB{
	
	private String	guid;		//文件存储表编号
	private String 	fileName;	//文件标题
	private String 	path;		//文件路径
	private byte[] 	fileContent;//文件内容
	private Date 	uploadTime;	//上传日期
	private Long 	fileSize;	//文件大小
	private String 	suffixs;	//后缀名
	private String 	opUser;		//操作人
	
	public String getSuffixs() {
		return suffixs;
	}
	public void setSuffixs(String suffixs) {
		this.suffixs = suffixs;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public byte[] getFileContent() {
		return fileContent;
	}
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getOpUser() {
		return opUser;
	}
	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}
	
}
