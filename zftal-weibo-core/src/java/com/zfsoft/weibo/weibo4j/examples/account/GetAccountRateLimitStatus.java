package com.zfsoft.weibo.weibo4j.examples.account;

import com.zfsoft.weibo.weibo4j.Account;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.RateLimitStatus;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class GetAccountRateLimitStatus {

	public static void main(String[] args) {
		String access_token =args[0];
		Account am = new Account();
		am.client.setToken(access_token);
		try {
            RateLimitStatus json = am.getAccountRateLimitStatus();
			Log.logInfo(json.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
