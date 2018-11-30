package com.zfsoft.util.base;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CharsetUtil {

	public static String UnicodeToGBK(String original) {
		if (original != null) {
			try {
				String str = new String(original.getBytes("ISO8859_1"), "GBK");
				return str;
			} catch (Exception e) {
				// e.printStackTrace();

				return "";
			}
		} else {
			return "";
		}
	}

	public static String GBKToUnicode(String original) {
		if (original != null) {
			try {
				return new String(original.getBytes("GBK"), "ISO8859_1");
			} catch (Exception e) {
				// e.printStackTrace();

				return "";
			}
		} else {
			return "";
		}
	}

	public static String Gb2312ToUnicode(String original) {
		if (original != null) {
			try {
				return new String(original.getBytes("gb2312"), "ISO8859_1");
			} catch (Exception e) {
				// e.printStackTrace();

				return "";
			}
		} else {
			return "";
		}
	}

	public static String UnicodeToGb2312(String original) {
		if (original != null) {
			try {
				return new String(original.getBytes("ISO8859_1"), "gb2312");
			} catch (Exception e) {
				// e.printStackTrace();

				return "";
			}
		} else {
			return "";
		}
	}

	public static String GBK2UTF8(String text) {
		String result = "";
		try {
			result = new String(text.getBytes("GBK"), "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			// ex.printStackTrace();
		}
		return result;
	}

	public static String UTF82GBK(String text) {
		String result = "";
		try {
			result = new String(text.getBytes("GBK"), "GBK");
		} catch (UnsupportedEncodingException ex) {
			// ex.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			System.out
					.println(URLDecoder.decode("%E6%B5%8B%E8%AF%95", "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
