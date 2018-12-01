package com.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class base64Tobyte {

	public static byte[] base64Tobyte(String att){
		try {
			return Base64.decodeBase64(att.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
