package com.zfsoft.mobile.classCommunity.entity;

/**
 * 帖子图片实体
 * @author H110MF
 *
 */
public class DynamicFileEntity {
    private String id;
    private String fileName;     //文件名称
    private String filePath;     //文件路径
    private String createTime;   //创建时间
    private String dynamicId;    //帖子id
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getDynamicId() {
		return dynamicId;
	}
	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}
}
