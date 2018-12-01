package com.zfsoft.wechat.common.utils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.zfsoft.wechat.common.entity.token.AccessToken;
import com.zfsoft.wechat.common.entity.token.WeChatOauth2Token;

/**
 * 获取Token
 * @author Administrator
 *
 */
public class QQTokenUtil {
    private static Logger log = LoggerFactory.getLogger(QQTokenUtil.class);
    private static Map<String, AccessToken> tokenMap= new HashMap<String, AccessToken>();;
    /**
     * 获取access_token
     * 
     * @param appid 凭证
     * @param appsecret 密钥
     * @param flag 是否强制重新获取
     * @return AccessToken
     */
    public static AccessToken getAccessToken(String appid, String appsecret,boolean flag) {
        AccessToken accessToken = tokenMap.get(appid+"_"+appsecret);
        if(flag||accessToken==null||accessToken.isOut())
        	return getAccessToken(appid, appsecret);
        return accessToken;
        
    }
    
    /**
     * 获取access_token(强制刷新)
     * 
     * @param appid 凭证
     * @param appsecret 密钥
     * @return AccessToken
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        log.info("getAccessToken START");
        AccessToken accessToken = null;

        String requestUrl = QQConstants.ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
        log.info("请求URL: " + requestUrl);
        JSONObject jsonObject = WeChatCommonUtil.httpsRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setAccessToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expire"));
                tokenMap.put(appid+"_"+appsecret, accessToken);
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                log.error("getAccessToken FAIL errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        log.info("getAccessToken END");
        return accessToken;
    }
    
    /**
     * 用户同意授权，获取code
     * @param appid 公众号的唯一标识
     * @param redirect_uri 授权后重定向的回调链接地址，请使用urlencode对链接进行处理
     * @param scope 应用授权作用域nsapi_base (不弹出授权页面，直接跳转，只能获取用户openid)，snsapi_userinfo (弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息)
     * @param state 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节(非必须)
     */
    public static String getAuthorizationCode(String appId, String redirect_uri, String scope, String state) {
        String requestUrl = QQConstants.AUTHORIZATION_CODE_URL;
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("REDIRECT_URI", URLEncoder.encode(redirect_uri));
        requestUrl = requestUrl.replace("SCOPE", scope);
        if (StringUtils.isEmpty(state)) {
            requestUrl = requestUrl.replace("STATE", "");
        } else {
            requestUrl = requestUrl.replace("STATE", state);
        }
        return requestUrl;
    }
    
    /**
     * 获取网页授权凭证
     * 描述：通过code换取网页授权access_token
     * @param appId 公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @param code
     * @return WeChatOauth2Token
     */
    public static WeChatOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        log.info("getOauth2AccessToken START");
        WeChatOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = QQConstants.WEB_AUTH_ACCESS_TOKEN_URL;
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
        JSONObject jsonObject = WeChatCommonUtil.httpsRequest(requestUrl, "GET", null);
        
        if (null != jsonObject) {
            try {
                wat = new WeChatOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
                if (null!=jsonObject.get("unionid")) {
                    wat.setUnionid(jsonObject.getString("unionid"));
                }
            } catch (Exception e) {
            	e.printStackTrace();
                wat = null;
//                int errorCode = jsonObject.get("errcode");
//                String errorMsg = jsonObject.getString("errmsg");
                log.error("getOauth2AccessToken FAIL errcode:{} errmsg:{}", jsonObject.get("errcode"), jsonObject.get("errmsg"));
            }
        }
        log.info("getOauth2AccessToken END");
        return wat;
    }
    
    /**
     * 刷新网页授权凭证
     * 
     * @param appId 公众账号的唯一标识
     * @param refreshToken
     * @return WeChatOauth2Token
     */
    public static WeChatOauth2Token refreshOauth2AccessToken(String appId, String refreshToken) {
        log.info("refreshOauth2AccessToken START");
        WeChatOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = QQConstants.WEB_REFRESH_ACCESS_TOKEN_URL;

        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);
        // 刷新网页授权凭证
        JSONObject jsonObject = WeChatCommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wat = new WeChatOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("refreshOauth2AccessToken FAIL errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        log.info("refreshOauth2AccessToken END");
        return wat;
    }
    
    /**
     * 检验授权凭证（access_token）是否有效
     * 
     * @param accessToken 网页授权接口调用凭证
     * @param openId 用户的唯一标识
     * @return boolean
     */
    public static boolean checkAccessToken(String accessToken, String openId) {
        log.info("checkAccessToken START");
        boolean result = false;
        // 拼接请求地址
        String requestUrl = QQConstants.CHECK_ACCESS_TOKEN_URL;
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        requestUrl = requestUrl.replace("OPENID", openId);
        // 刷新网页授权凭证
        JSONObject jsonObject = WeChatCommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = true;
            } else {
                result = false;
                log.error("checkAccessToken FAIL errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        log.info("checkAccessToken END");
        return result;
    }

}
