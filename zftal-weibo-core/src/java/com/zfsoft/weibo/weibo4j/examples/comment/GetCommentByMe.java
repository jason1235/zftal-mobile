package com.zfsoft.weibo.weibo4j.examples.comment;

import com.zfsoft.weibo.weibo4j.Comments;
import com.zfsoft.weibo.weibo4j.examples.oauth2.Log;
import com.zfsoft.weibo.weibo4j.model.Comment;
import com.zfsoft.weibo.weibo4j.model.CommentWapper;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class GetCommentByMe {

	public static void main(String[] args) {
		String access_token = args[0];
		Comments cm = new Comments();
		cm.client.setToken(access_token);
		try {
			CommentWapper comment = cm.getCommentByMe();
			for(Comment c : comment.getComments()){
				Log.logInfo(c.toString());
			}
			System.out.println(comment.getNextCursor());
			System.out.println(comment.getPreviousCursor());
			System.out.println(comment.getTotalNumber());
			System.out.println(comment.getHasvisible());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
