package com.zfsoft.hrm.file.dto;

public class FileProp {
	private String fileId;
	private String fileType="file";
	private String key;
	private String currentName;
	private long maxSize;
	private int width;
	private int height;

	public String getCurrentName() {
		return currentName;
	}

	public void setCurrentName(String currentName) {
		this.currentName = currentName;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getMaxSize() {
		return maxSize;
	}

	public String getLimitDes(){
//		if
//		maxSize%1024
		return "";
	}
	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}

