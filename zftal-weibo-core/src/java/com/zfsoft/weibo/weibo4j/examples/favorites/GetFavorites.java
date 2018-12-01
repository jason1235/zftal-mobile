package com.zfsoft.weibo.weibo4j.examples.favorites;

import java.util.List;

import com.zfsoft.weibo.weibo4j.Favorite;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.Favorites;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class GetFavorites {

	public static void main(String[] args) {
		String access_token = args[0];
		Favorite fm = new Favorite();
		fm.client.setToken(access_token);
		try {
			List<Favorites> favors = fm.getFavorites();
			for(Favorites s : favors){
				Log.logInfo(s.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
