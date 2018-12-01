package com.zfsoft.mobile.servlet.entity;

import java.util.List;

public class MyPortalEntity {
	public String headPicturePath;//我的头像路径
	List<MyPortalEntityItemEntity> list;//我的门户选项集合
	
	public MyPortalEntity(){
		
	}
	
	public MyPortalEntity(String headPicturePath, List<MyPortalEntityItemEntity> list){
		this.headPicturePath = headPicturePath;
		this.list = list;
	}

	@Override
	public String toString() {
		return "MyPortalEntity [headPicturePath=" + headPicturePath + ", list="
				+ list + "]";
	}
	
}
