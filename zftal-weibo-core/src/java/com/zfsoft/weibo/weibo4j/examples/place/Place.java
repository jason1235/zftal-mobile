package com.zfsoft.weibo.weibo4j.examples.place;

import com.zfsoft.weibo.weibo4j.model.WeiboException;

public class Place {

	public static void main(String[] args) {
		com.zfsoft.weibo.weibo4j.Place wp = new com.zfsoft.weibo.weibo4j.Place();
		wp.client.setToken(args[1]);
		try {
			wp.poisUsersList(args[0]);
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
