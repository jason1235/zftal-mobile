package com.zfsoft.wechat.login.dao.daointerface;

import java.util.HashMap;

import com.zfsoft.wechat.login.entity.WeChatBinding;

/**
 * 
 * @author Administrator
 *
 */
public interface IWeChatLoginDao {
	
	public String findUserByOpenId(String openId);
	public int insert(WeChatBinding weChatBinding);
	public int remove(WeChatBinding weChatBinding);
	
}
