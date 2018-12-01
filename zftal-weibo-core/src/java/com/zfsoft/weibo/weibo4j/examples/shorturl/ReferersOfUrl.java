package com.zfsoft.weibo.weibo4j.examples.shorturl;

import com.zfsoft.weibo.weibo4j.ShortUrl;
import com.zfsoft.weibo.weibo4j.model.WeiboException;
import com.zfsoft.weibo.weibo4j.org.json.JSONObject;


public class ReferersOfUrl {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token =args[0];
		String url =args[1];
		ShortUrl su = new ShortUrl();
		su.client.setToken(access_token);
		try {
			JSONObject jo = su.referersOfUrl(url);
			System.out.println(jo.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
