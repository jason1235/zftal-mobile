package com.zfsoft.weibo.weibo4j.examples.shorturl;

import com.zfsoft.weibo.weibo4j.model.WeiboException;
import com.zfsoft.weibo.weibo4j.org.json.JSONObject;


public class ShortToLongUrl {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];
		String url = args[1];
		com.zfsoft.weibo.weibo4j.ShortUrl su = new com.zfsoft.weibo.weibo4j.ShortUrl();
		su.client.setToken(access_token);
		try {
			JSONObject jo = su.shortToLongUrl(url);
			System.out.println(jo.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}

