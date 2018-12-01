package com.zfsoft.mobile.servlet.entity;

import java.util.List;
import java.util.Map;

public class MyPortalNewEntityWithFwMap {
	public String headPicturePath;//我的头像路径
	public String source;
	public String sourceLevel; //积分等级
	public String levelLimit;  //上下限制
	public String isTodaySign;
	Map<String,Object> map; //我的门户选项集合map
	
	public MyPortalNewEntityWithFwMap(){
		
	}
	
	public MyPortalNewEntityWithFwMap(String headPicturePath, Map<String,Object> map){
		this.headPicturePath = headPicturePath;
		this.map = map;
	}

	public MyPortalNewEntityWithFwMap(String headPicturePath, String source,
			String isTodaySign, Map<String,Object> map) {
		this.headPicturePath = headPicturePath;
		this.source = source;
		this.isTodaySign = isTodaySign;
		this.map = map;
	}
	
	public MyPortalNewEntityWithFwMap(String headPicturePath, String source,String sourceLevel,String levelLimit,
			String isTodaySign, Map<String,Object> map) {
		this.headPicturePath = headPicturePath;
		this.source = source;
		this.sourceLevel = sourceLevel;
		this.levelLimit = levelLimit;
		this.isTodaySign = isTodaySign;
		this.map = map;
	}

	@Override
	public String toString() {
		return "MyPortalEntity [headPicturePath=" + headPicturePath + ",source="+source+",isTodaySign="+isTodaySign+",list="
				+ map + "]";
	}
	
}
