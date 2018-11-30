package com.zfsoft.hrm.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;

/** 
 * @author jinjj
 * @date 2012-12-5 上午11:47:32 
 *  
 */
public class CookieUtil {

	//default cookie expire time is 1 year
	private final static int MAX_AGE = 60*60*24*365;
	
	public static String getCookie(HttpServletRequest req,String name){
		Assert.notNull(req, "Request can't be null");
		Cookie[] cookies = req.getCookies();
		for(Cookie cookie : cookies){
			if(cookie.getName().equals(name)){
				try {
					return URLDecoder.decode(cookie.getValue(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	public static void setCookie(HttpServletResponse res,String name,String value){
		Assert.notNull(res, "Response can't be null");
		String v;
		try {
			v = URLEncoder.encode(value,"utf-8");
		} catch (UnsupportedEncodingException e) {
			v = value;
		}
		Cookie cookie = new Cookie(name,v);
		cookie.setMaxAge(MAX_AGE);
		res.addCookie(cookie);
	}
	
	public static void removeCookie(HttpServletResponse res,String name){
		Assert.notNull(res, "Response can't be null");
		Cookie cookie = new Cookie(name,null);
		cookie.setMaxAge(0);
		res.addCookie(cookie);
	}
}
