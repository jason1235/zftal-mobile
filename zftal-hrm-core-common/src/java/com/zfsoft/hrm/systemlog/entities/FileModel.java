package com.zfsoft.hrm.systemlog.entities;

import java.io.File;
import java.util.Date;

import com.zfsoft.util.date.TimeUtil;

/**
 * 
 * @author ChenMinming
 * @date 2013-11-4
 * @version V1.0.0
 */
public class FileModel {
	
	private String fileName;
	
	private long fileSize;
	
	private long modifyDate;

	public FileModel(){}
	
	public FileModel(File f) {
		fileName = f.getName();
		modifyDate = f.lastModified();
		fileSize = f.length();
	}

	/**
	 * 返回
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置
	 * @param fileName 
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 返回
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * 设置
	 * @param fileSize 
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * 返回
	 */
	public long getModifyDate() {
		return modifyDate;
	}

	/**
	 * 返回
	 */
	public String getModifyDateView() {
		return TimeUtil.getDataTime(modifyDate, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 设置
	 * @param modifyDate 
	 */
	public void setModifyDate(long modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	
}
