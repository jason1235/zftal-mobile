package com.util;

import java.security.MessageDigest;

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

/*	public static void main(String[] args) {
		//System.out.println(e.encrypt("VZvHND"));
		System.out.println(Encrypt.encrypt("0"));
	}*/

}
