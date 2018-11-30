package com.zfsoft.wechat.menu.action;

import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.wechat.common.entity.token.AccessToken;
import com.zfsoft.wechat.common.utils.QQMenuUtil;
import com.zfsoft.wechat.common.utils.QQTokenUtil;
import com.zfsoft.wechat.common.utils.WeChatMenuUtil;
import com.zfsoft.wechat.common.utils.WeChatSystemHolder;
import com.zfsoft.wechat.common.utils.WeChatTokenUtil;

/**
 * 
 * @author ChenMinming
 * @date 2015-7-30
 * @version V1.0.0
 */
public class QQMenuAction extends HrmAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8700949978481721688L;
	private String menuInfo;
	

	public String list(){
//		menuInfo="{\"menu\":{\"button\":[{\"type\":\"click\",\"name\":\"今日歌曲\",\"key\":\"V1001_TODAY_MUSIC\",\"sub_button\":[]},{\"type\":\"click\",\"name\":\"歌手简介\",\"key\":\"V1001_TODAY_SINGER\",\"sub_button\":[]},{\"name\":\"菜单\",\"sub_button\":[{\"type\":\"view\",\"name\":\"搜索\",\"url\":\"http://www.soso.com/\",\"sub_button\":[]},{\"type\":\"view\",\"name\":\"视频\",\"url\":\"http://v.qq.com/\",\"sub_button\":[]},{\"type\":\"click\",\"name\":\"赞一下我们\",\"key\":\"V1001_GOOD\",\"sub_button\":[]}]}]}}";
		// 第三方用户唯一凭证֤
		String appId = WeChatSystemHolder.getPropertiesValue("qq_appId");
		// 第三方用户唯一凭证密钥
		String appSecret = WeChatSystemHolder.getPropertiesValue("qq_appSecret");
		AccessToken t =QQTokenUtil.getAccessToken(appId, appSecret,false);
		menuInfo=QQMenuUtil.getMenu(t.getAccessToken());
		if(StringUtil.isEmpty(menuInfo))
			menuInfo = "{\"menu\":{\"button\":[]}}";
		getValueStack().set("appid", appId);
		return "list";
	}
	
	public String save(){
		// 第三方用户唯一凭证֤
		String appId = WeChatSystemHolder.getPropertiesValue("qq_appId");
		// 第三方用户唯一凭证密钥
		String appSecret = WeChatSystemHolder.getPropertiesValue("qq_appSecret");
		AccessToken t =QQTokenUtil.getAccessToken(appId, appSecret,false);
		getValueStack().set(DATA, getMessage());
		if(!QQMenuUtil.createMenu(menuInfo, t.getAccessToken()))
			setErrorMessage("接口调用失败！");
		return DATA;
	}
	/**
	 * 返回
	 */
	public String getMenuInfo() {
		return menuInfo;
	}
	/**
	 * 设置
	 * @param menuInfo 
	 */
	public void setMenuInfo(String menuInfo) {
		this.menuInfo = menuInfo;
	}
}
