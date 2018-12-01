package com.zfsoft.weibo.weibo4j.examples.suggestions;

import com.zfsoft.weibo.weibo4j.Suggestion;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class SuggestionsUsersNot_interested {

	public static void main(String[] args) {
		String access_token=args[0];
		Suggestion suggestion = new Suggestion();
		suggestion.client.setToken(access_token);
		try {
			suggestion.suggestionsUsersNot_interested(args[1]);
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
