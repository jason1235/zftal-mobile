package com.zfsoft.weibo.weibo4j.examples.tags;

import java.util.List;

import com.zfsoft.weibo.weibo4j.Tags;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.Tag;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class GetTagsSuggestions {

	public static void main(String[] args) {
		String access_token =args[0];
		Tags tm = new Tags();
		tm.client.setToken(access_token);
		List<Tag> tags = null;
		try {
			tags = tm.getTagsSuggestions();
			for(Tag tag : tags){				
				Log.logInfo(tag.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
