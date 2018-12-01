package com.zfsoft.weibo.weibo4j.examples.favorites;

import com.zfsoft.weibo.weibo4j.Favorite;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.Favorites;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class DestroyFavorites {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String access_token = args[0];
		Favorite fm = new Favorite();
		fm.client.setToken(access_token);
		String id = args[1];
		try {
			Favorites favors =fm.destroyFavorites(id);
			Log.logInfo(favors.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
