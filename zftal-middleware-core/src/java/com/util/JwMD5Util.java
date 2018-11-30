package com.util;

import java.net.URLEncoder;

public class JwMD5Util {

	private static final String KEY = "DAFF8EA19E6BAC86E040007F01004EA";
	
	private static final String KEY2= "zfsoft";
	
	/**
	 * 添加密钥后md5加密
	 * @param str
	 * @return
	 */
	public static String md5Ecode(String str) {
		MD5Util md5 = new MD5Util();
		return md5.getMD5ofStr(URLEncoder.encode(str).toUpperCase() + KEY);
	}
	
	public static String md5Ecode2(String str){
		MD5Util md5 = new MD5Util();
		return md5.getMD5ofStr(URLEncoder.encode(str) + KEY2);
	}
	
	public static String md5Ecode3(String str){
		MD5Util md5 = new MD5Util();
		return md5.getMD5ofStr(KEY2 +URLEncoder.encode(str));
	}
	
	
}
