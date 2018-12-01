package com.zfsoft.weibo.weibo4j.examples.timeline;

import com.zfsoft.weibo.weibo4j.Timeline;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.WeiboException;
import com.zfsoft.weibo.weibo4j.org.json.JSONObject;


public class QueryId {

	public static void main(String[] args) {
		String access_token = args[0];
		String mid =  args[1];
		Timeline tm = new Timeline();
		tm.client.setToken(access_token);
		try {
			JSONObject id = tm.QueryId( mid, 1,1);
				Log.logInfo(String.valueOf(id));			
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
