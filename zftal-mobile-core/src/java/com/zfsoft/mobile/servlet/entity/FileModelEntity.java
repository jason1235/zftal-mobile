/**
 * 
 */
package com.zfsoft.mobile.servlet.entity;

/**
 * @author zhangxu
 * @description 
 * @date 2017-10-10 下午08:38:14
 */
public class FileModelEntity {

	private String fileName;//文件名称
	private String adjunctid;//附件id
	private String downloadUrl;//下载路径
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getAdjunctid() {
		return adjunctid;
	}
	public void setAdjunctid(String adjunctid) {
		this.adjunctid = adjunctid;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	@Override
	public String toString() {
		return "FileModelEntity [fileName=" + fileName + ", adjunctid="
				+ adjunctid + ", downloadUrl=" + downloadUrl + "]";
	}
	
	
}
