package com.zfsoft.mobile.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.encrypt.DBEncrypt;
import com.zfsoft.util.encrypt.Encrypt;

public class Authentication {
	
	public static String sign() {
		DBEncrypt dbe = new DBEncrypt();
		//String uuid = UUID.randomUUID().toString();
		String uuid = "123";
		//String encodeStr = dbe.eCode(uuid + "&&" + dbe.eCode(uuid + Encrypt.encrypt("zfsoft")));
		String encodeStr = dbe.eCode(uuid + "&&" + dbe.eCode(uuid));
		try {
			encodeStr = URLEncoder.encode(encodeStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return encodeStr;
		
	}
	
	public static String sign(String code) {
		DBEncrypt dbe = new DBEncrypt();
		//String uuid = UUID.randomUUID().toString();
		String uuid = "123";
		if (!StringUtil.isEmpty(code)) {
			uuid = code;
		}
		//String encodeStr = dbe.eCode(uuid + "&&" + dbe.eCode(uuid + Encrypt.encrypt("zfsoft")));
		String encodeStr = dbe.eCode(uuid + "&&" + dbe.eCode(uuid));
		try {
			encodeStr = URLEncoder.encode(encodeStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return encodeStr;
		
	}
	
	public static boolean authenticate(String sign) {
 		if (StringUtil.isEmpty(sign)) {
			return false;
		}
		DBEncrypt dbe = new DBEncrypt();
		try {
			sign = URLDecoder.decode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		String code = null;
		try {
			code = dbe.dCode(sign.getBytes());
		} catch (Exception e) {
		}
		String encodeConstant = Encrypt.encrypt("zfsoft");
		String uuid = null,encodeUUID = null;
		if (code != null) {
			String[] results = code.split("&&");
			if (results.length >1) {
				uuid = results[0];
				try {
					encodeUUID = dbe.dCode(results[1].getBytes());
				} catch (Exception e) {
				}
				if (encodeUUID != null) {
					if (uuid.equals(encodeUUID.replace(encodeConstant, ""))) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(sign());
		System.out.println(authenticate(sign()));
	}

}
