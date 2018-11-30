package com.zfsoft.weibo.weibo4j.examples.account;

import com.zfsoft.weibo.weibo4j.Account;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.WeiboException;
import com.zfsoft.weibo.weibo4j.org.json.JSONObject;


public class GetUid {

	public static void main(String[] args) {
		String access_token ="2.00_Vwu1GzHpwXC44c6a22dbc8HFrWD";
		Account am = new Account();
		am.client.setToken(access_token);
		try {
			JSONObject uid = am.getUid();
			Log.logInfo(uid.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
