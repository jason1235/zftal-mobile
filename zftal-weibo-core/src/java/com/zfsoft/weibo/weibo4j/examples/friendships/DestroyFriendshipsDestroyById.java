package com.zfsoft.weibo.weibo4j.examples.friendships;

import com.zfsoft.weibo.weibo4j.Friendships;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.User;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class DestroyFriendshipsDestroyById {

	public static void main(String[] args) {
		String access_token = args[0];
		String uid = args[1];
		Friendships fm = new Friendships();
		fm.client.setToken(access_token);
		try {
			User fv =fm.destroyFriendshipsDestroyById(uid);
			Log.logInfo(fv.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
