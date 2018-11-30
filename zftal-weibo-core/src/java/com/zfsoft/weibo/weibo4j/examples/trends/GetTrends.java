package com.zfsoft.weibo.weibo4j.examples.trends;

import java.util.List;

import com.zfsoft.weibo.weibo4j.Trend;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.UserTrend;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class GetTrends {

	public static void main(String[] args) {
		String access_token = args[0];
		String uid = args[1];
		Trend tm = new Trend();
		tm.client.setToken(access_token);
		List<UserTrend> trends = null;
		try {
			trends = tm.getTrends(uid);
			for(UserTrend t : trends){
				Log.logInfo(t.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
