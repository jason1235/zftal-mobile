package com.zfsoft.wechat.login.service;

import com.zfsoft.wechat.login.entity.WeChatBinding;
import com.zfsoft.common.log.User;

/**
 * 
 * @author Administrator
 *
 */
public interface IWeChatLoginService {
	public String findByOpenId(String openId);
	public void bindOpenId(WeChatBinding weChatBinding);
}
