package com.zfsoft.hrm.core.file;

import java.io.File;
import java.io.Serializable;

/**
 * 文件上传请求（适应于多个文件同时上传）
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-10-18
 * @version V1.0.0
 */
public class FileRequest implements Serializable {

	private static final long serialVersionUID = 6838187210345080744L;

	private File[] files;					// 文件
	
	private String[] filesContentType;		// 文件类型
	
	private String[] filesFileName;			// 文件名称

	/**
	 * 返回文件集合
	 */
	public File[] getFiles() {
		return files;
	}

	/**
	 * 设置文件集合
	 * @param files 文件集合 
	 */
	public void setFiles(File[] files) {
		this.files = files;
	}

	/**
	 * 返回文件类型集合
	 */
	public String[] getFilesContentType() {
		return filesContentType;
	}

	/**
	 * 设置文件类型集合
	 * @param filesContentType 文件类型集合
	 */
	public void setFilesContentType(String[] filesContentType) {
		this.filesContentType = filesContentType;
	}

	/**
	 * 返回文件名称集合
	 */
	public String[] getFilesFileName() {
		return filesFileName;
	}

	/**
	 * 设置文件名称集合
	 * @param filesFileName 文件名称集合
	 */
	public void setFilesFileName(String[] filesFileName) {
		this.filesFileName = filesFileName;
	}
}
