package com.zfsoft.mobile.skin.entity;

import java.util.List;

/**
 * 皮肤实体
 * @author liucb
 */
public class SkinEntity {
    private String id;
    private String name;
    private String description;
    private String apkPath;
    private String createTime;
    private List<SkinPreviewPicsEntity> previewPics;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getApkPath() {
		return apkPath;
	}
	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public List<SkinPreviewPicsEntity> getPreviewPics() {
		return previewPics;
	}
	public void setPreviewPics(List<SkinPreviewPicsEntity> previewPics) {
		this.previewPics = previewPics;
	}
}
