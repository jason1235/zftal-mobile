package com.zfsoft.weibo.weibo4j.examples.favorites;

import java.util.List;

import com.zfsoft.weibo.weibo4j.Favorite;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.FavoritesTag;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class GetFavoritesTags {

	public static void main(String[] args) {
		String access_token = args[0];
		Favorite fm = new Favorite();
		fm.client.setToken(access_token);
		try {
			List<FavoritesTag> favors = fm.getFavoritesTags();
			for(FavoritesTag s : favors){
				Log.logInfo(s.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}