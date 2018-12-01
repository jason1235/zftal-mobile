package com.zfsoft.weibo.weibo4j.examples.tags;

import com.zfsoft.weibo.weibo4j.Tags;
import com.zfsoft.weibo.weibo4j.model.TagWapper;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class GetTagsBatch {

	public static void main(String[] args) {
		String access_token = args[0];
		Tags tm = new Tags();
		tm.client.setToken(access_token);
		TagWapper tags = null;
		String uids = args[1];
		try {;
			tags = tm.getTagsBatch(uids);
			System.out.println(tags.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
