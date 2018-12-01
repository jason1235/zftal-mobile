package com.zfsoft.wechat.common.utils;

/**
 * QQ公众号接口地址模板
 * @author wuying
 *
 */
public class QQConstants {
	// 获取access_token地址
	public static final String ACCESS_TOKEN_URL = "https://api.mp.qq.com/cgi-bin/token?appid=APPID&secret=APPSECRET";
	
	// 用户同意授权，获取code URL
	public static final String AUTHORIZATION_CODE_URL = "https://open.mp.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#qq_redirect";
	
	// 获取网页授权凭证URL http请求方式GET
    public final static String WEB_AUTH_ACCESS_TOKEN_URL = "https://api.mp.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    // 刷新网页授权凭证URL http请求方式GET
    public final static String WEB_REFRESH_ACCESS_TOKEN_URL = "https://api.mp.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    // 通过网页授权获取用户信息URL http请求方式GET
    public final static String WEB_USERINFO_URL = "https://api.mp.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=LANG";
    // 检验授权凭证（access_token）是否有效URL http请求方式GET
    public final static String CHECK_ACCESS_TOKEN_URL = "https://api.mp.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
    // 获取用户信息URL http请求方式GET
    public final static String USERINFO_URL = "https://api.mp.qq.com/cgibin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=LANG";
    // 消息发送URL http请求方式POST
    public final static String MESSAGE_SEND_URL = "https://api.mp.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    
    /** 菜单配置接口*/
    // 创建菜单URL http请求方式POST
    public final static String MENU_CREATE_URL = "https://api.mp.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    // 取得菜单URL http请求方式GET
    public final static String MENU_GET_URL = "https://api.mp.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    // 删除菜单URL http请求方式GET
    public final static String MENU_DELETE_URL = "https://api.mp.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	// 获取自定义菜单配置URL http请求方式GET
	//public final static String CURRENT_MENU_GET_URL = "https://api.mp.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN";
    
    //上传图文消息
    //public final static String UPLOAD_NEWS_URL = "https://api.mp.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
    //群发消息
    public final static String SEND_NEWS_URL = "https://api.mp.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
    //public final static String SEND_NEWS_ALL_URL = "https://api.mp.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
    //public final static String SEND_NEWS_PREVIEW_URL = "https://api.mp.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
    //素材管理
    public final static String MATERIAL_ADD_URL = "https://api.mp.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";
    public final static String MATERIAL_DELETE_URL = "https://api.mp.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";
    
    // 弹出授权页面，直接跳转，只能获取用户openid
    public static final String OAUTH_SCOPE_BASE = "snsapi_base";
    // 弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息
    public static final String OAUTH_SCOPE_USERINFO = "snsapi_userinfo";
    // 返回语言-简体
    public static final String LANGUAGE_ZH_CN = "zh_CN";
    // 返回语言-繁体
    public static final String LANGUAGE_ZH_TW  = "zh_TW";
    // 返回语言-英语
    public static final String LANGUAGE_ENGLISH = "en";
}
