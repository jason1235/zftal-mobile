package com.zfsoft.mobile.servlet.entity;

import java.util.List;

public class MyPortalNewEntity {
	public String headPicturePath;//我的头像路径
	public String source;
	public String isTodaySign;
	List<MyPortalEntityItemEntity> list;//我的门户选项集合
	
	public MyPortalNewEntity(){
		
	}
	
	public MyPortalNewEntity(String headPicturePath, List<MyPortalEntityItemEntity> list){
		this.headPicturePath = headPicturePath;
		this.list = list;
	}

	public MyPortalNewEntity(String headPicturePath, String source,
			String isTodaySign, List<MyPortalEntityItemEntity> list) {
		this.headPicturePath = headPicturePath;
		this.source = source;
		this.isTodaySign = isTodaySign;
		this.list = list;
	}

	@Override
	public String toString() {
		return "MyPortalEntity [headPicturePath=" + headPicturePath + ",source="+source+",isTodaySign="+isTodaySign+",list="
				+ list + "]";
	}
	
}
