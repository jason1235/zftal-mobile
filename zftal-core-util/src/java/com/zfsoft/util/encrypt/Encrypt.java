package com.zfsoft.util.encrypt;

import java.security.MessageDigest;


/**
 * 加密用到的工具类,MD5加密,目前无法进行解密
 * @author lt
 * @time 2011-3-14
 *
 */
public class Encrypt {
	
	private String key;

	public Encrypt() {
		this.key = "Encrypt01";
	}

	public static String encrypt(String PlainStr) {
		return "{MD5}" + testHA2(PlainStr, "md5");
	}
	
	public static String testHA2(String data, String ha) {
		byte[] buffer = null;
		MessageDigest messageDigest = null;
		String s = "";
		try {
			buffer = data.getBytes("UTF-8");
			messageDigest = MessageDigest.getInstance(ha);
			messageDigest.update(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		buffer = messageDigest.digest();
		s = new sun.misc.BASE64Encoder().encode(buffer);
		return s;
	}

	public static void main(String[] args) {
		System.out.println(Encrypt.encrypt("123456"));
	}
	
}