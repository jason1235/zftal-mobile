package com.oa.service;

public class DownLoadBean {

	/**
	 *文件名称
	 */
	private String fileName;
	/**
	 *附件ID
	 */
	private String adjunctId;
	/**
	 *下载地址
	 */
	private String downLoadURL;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getAdjunctId() {
		return adjunctId;
	}
	public void setAdjunctId(String adjunctId) {
		this.adjunctId = adjunctId;
	}
	public String getDownLoadURL() {
		return downLoadURL;
	}
	public void setDownLoadURL(String downLoadURL) {
		this.downLoadURL = downLoadURL;
	}
}
