package com.zfsoft.weibo.weibo4j.examples.comment;

import com.zfsoft.weibo.weibo4j.Comments;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.Comment;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class CreateComment {

	public static void main(String[] args) {
		String access_token = args[0];
		String comments =args[1];
		String id = args[2];
		Comments cm = new Comments();
		cm.client.setToken(access_token);
		try {
			Comment comment = cm.createComment(comments, id);
			Log.logInfo(comment.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
