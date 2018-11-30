package com.zfsoft.weibo.weibo4j.examples.comment;

import com.zfsoft.weibo.weibo4j.Comments;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.Comment;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class ReplyComment {

	public static void main(String[] args) {
		String access_token = args[0];
		String comments =args[1];
		String id = args[2];
		String cid =args[3];
		Comments cm = new Comments();
		cm.client.setToken(access_token);
		try {
			Comment com = cm.replyComment(cid, id, comments);
			Log.logInfo(com.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
