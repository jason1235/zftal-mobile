package com.zfsoft.weibo.weibo4j.examples.trends;

import java.util.List;

import com.zfsoft.weibo.weibo4j.Trend;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.Trends;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class GetTrendsDaily {
	public static void main(String[] args) {
		String access_token = args[0];
		Trend tm = new Trend();
		tm.client.setToken(access_token);
		List<Trends> trends = null;
		try {
			trends = tm.getTrendsDaily();
			for(Trends ts : trends){
				Log.logInfo(ts.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}


}


