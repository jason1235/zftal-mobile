package com.zfsoft.weibo.weibo4j.examples.friendships;

import com.zfsoft.weibo.weibo4j.Friendships;
import com.zfsoft.weibo.weibo4j.model.User;
import com.zfsoft.weibo.weibo4j.model.UserWapper;
import com.zfsoft.weibo.weibo4j.model.WeiboException;


public class GetFriendsByID {

	public static void main(String[] args) {
		String access_token = args[0];
		String id = args[1];
		Friendships fm = new Friendships();
		fm.client.setToken(access_token);
		try {
			UserWapper users = fm.getFriendsByID(id);
			for(User u : users.getUsers()){
				System.out.println(u.toString());
			}
			System.out.println(users.getNextCursor());
			System.out.println(users.getPreviousCursor());
			System.out.println(users.getTotalNumber());
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
