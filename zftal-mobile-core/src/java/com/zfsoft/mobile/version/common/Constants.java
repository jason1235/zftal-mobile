package com.zfsoft.mobile.version.common;

import com.common.Config;



/**
 * 系统常量文件Constants.java
 * <p>
 * Description:系统有关产量的定义要求全部定义到本文件里来。 如：
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: zfsoft.com
 * </p>
 * 
 * @since 2011-10-23下午01:36:18
 * @author:xxh
 * @version 1.0
 */
public class Constants {
	// 文件上传的路径
	public static final String IMG_UPLOAD_PATH = "upload/";
	//服务器地址
	public static final String SERVICE_ADDRESS = Config.getString("suploadPath", "http://10.71.32.205:8888/zftale-mobile/");
	
	// 缩略图大小 100
	public static final int PHOTO_SIZE = 100;
	
	// 缩略图高 600
	public static final int PHOTO_SIZE_H = 600;
	
	// 缩略宽大小 800
	public static final int PHOTO_SIZE_W = 800;

	// 缩略图高 200
	public static final int PHOTO_SIZE_SH = 200;
	
	// 缩略宽大小 300
	public static final int PHOTO_SIZE_SW = 300;
	//安卓
	public static final String ANDROID = "android";
	//苹果手机
	public static final String IPHONE = "iphone";
	//苹果平板电脑
	public static final String IPAD = "ipad";
	/**
	 *版本更新状态--强制更新1
	 */
	public static String VERSION_STATE1 = "1";
	/**
	 *版本更新状态--非强制更新2
	 */
	public static String VERSION_STATE2 = "2";
	/**
	 *版本更新状态--不需要更新3
	 */
	public static String VERSION_STATE3 = "3";
}