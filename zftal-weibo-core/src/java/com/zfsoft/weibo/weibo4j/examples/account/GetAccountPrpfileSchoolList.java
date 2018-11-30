package com.zfsoft.weibo.weibo4j.examples.account;

import java.util.List;

import com.zfsoft.weibo.weibo4j.Account;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.School;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class GetAccountPrpfileSchoolList {

	public static void main(String[] args) {
		String access_token = args[0];// 输入授权后的AccessToken
		Account am = new Account();
		am.client.setToken(access_token);
		try {
			List<School> schools = am.getAccountPrpfileSchoolList();
			for (School school : schools) {
				Log.logInfo(school.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
