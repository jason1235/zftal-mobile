package com.zfsoft.hrm.file.dto;

import org.apache.commons.fileupload.ProgressListener;

public class Progress implements ProgressListener{
	// 文件总长度
	private long length = 0;
	// 已上传的文件长度
	private long currentLength = 0;
	// 上传是否完成
	private boolean isComplete = false;

	private String fileName = "";
	
	private String fileGuid="";
	
	private boolean exceptionMaxSize=false;

	private String errorMessage;
	
	public boolean isExceptionMaxSize() {
		return exceptionMaxSize;
	}

	public void setExceptionMaxSize(boolean exceptionMaxSize) {
		this.exceptionMaxSize = exceptionMaxSize;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getFileGuid() {
		return fileGuid;
	}

	public void setFileGuid(String fileGuid) {
		this.fileGuid = fileGuid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.fileupload.ProgressListener#update(long,
	 * long, int)
	 */
	public void update(long bytesRead, long contentLength, int items) {
		this.currentLength = bytesRead;
	}

	/**
	 * the getter method of length
	 * 
	 * @return the length
	 */
	public long getLength() {
		return length;
	}

	/**
	 * the getter method of currentLength
	 * 
	 * @return the currentLength
	 */
	public long getCurrentLength() {
		return currentLength;
	}

	/**
	 * the getter method of isComplete
	 * 
	 * @return the isComplete
	 */
	public boolean isComplete() {
		return isComplete;
	}

	/**
	 * the setter method of the length
	 * 
	 * @param length
	 *            the length to set
	 */
	public void setLength(long length) {
		this.length = length;
	}

	/**
	 * the setter method of the currentLength
	 * 
	 * @param currentLength
	 *            the currentLength to set
	 */
	public void setCurrentLength(long currentLength) {
		this.currentLength = currentLength;
	}

	/**
	 * the setter method of the isComplete
	 * 
	 * @param isComplete
	 *            the isComplete to set
	 */
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
}
