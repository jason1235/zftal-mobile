package com.zfsoft.weibo.weibo4j.examples.comment;

import com.zfsoft.weibo.weibo4j.Comments;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.WeiboException;
import com.zfsoft.weibo.weibo4j.org.json.JSONArray;


public class DestroyCommentBatcn {

	public static void main(String[] args) {
		String access_token = args[0];
		String cids = args[1];
		Comments cm = new Comments(); 
		cm.client.setToken(access_token);
		try {
			JSONArray com = cm.destoryCommentBatch(cids);
			Log.logInfo(com.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
