package com.zfsoft.weibo.weibo4j.examples.friendships;

import com.zfsoft.weibo.weibo4j.Friendships;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class GetFriendsIdsByName {

	public static void main(String[] args) {
		String access_token =args[0];
		String screenName = args[1];
		Friendships fm = new Friendships();
		fm.client.setToken(access_token);
		try {
			String[] ids = fm.getFriendsIdsByName(screenName);
			for(String s : ids){
				Log.logInfo(s);
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
