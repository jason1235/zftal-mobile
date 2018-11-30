package com.zfsoft.wechat.common.utils;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zfsoft.wechat.common.entity.button.Menu;

/**
 * 微信菜单
 * @author Administrator
 *
 */
public class WeChatMenuUtil {

    private static Logger log = LoggerFactory.getLogger(WeChatMenuUtil.class);
    /**
     * 创建菜单
     * @param menu
     * @param accessToken
     * @return
     */
    public static boolean createMenu(Menu menu, String accessToken) {
        log.info("createMenu START");
        boolean result = false;
        String url = WeChatConstants.MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken);
        String jsonMenu = JSONObject.fromObject(menu).toString();
        
        JSONObject jsonObject = WeChatCommonUtil.httpsRequest(url, "POST", jsonMenu);

        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = true;
            } else {
                result = false;
                log.error("createMenu FAIL errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        log.info("createMenu END");
        
        return result;
    }
    
    /**
     * 创建菜单
     * @param menu
     * @param accessToken
     * @return
     */
    public static boolean createMenu(String jsonMenu, String accessToken) {
        log.info("createMenu START");
        boolean result = false;
        String url = WeChatConstants.MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken);
        
        JSONObject jsonObject = WeChatCommonUtil.httpsRequest(url, "POST", jsonMenu);

        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = true;
            } else {
                result = false;
                log.error("createMenu FAIL errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        log.info("createMenu END");
        
        return result;
    }
    /**
     * 取得菜单
     * @param accessToken
     * @return
     */
    public static String getMenu(String accessToken) {
        log.info("getMenu START");
        String result = null;
        String requestUrl = WeChatConstants.MENU_GET_URL.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject = WeChatCommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            result = jsonObject.toString();
        }
        log.info("getMenu END");
        return result;
    }
    
    /**
     * 删除菜单
     * @param accessToken
     * @return
     */
    public static boolean deleteMenu(String accessToken) {
        log.info("deleteMenu START");
        boolean result = false;
        String requestUrl = WeChatConstants.MENU_DELETE_URL.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject = WeChatCommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = true;
            } else {
                result = false;
                log.error("deleteMenu FAIL errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        log.info("deleteMenu END");
        return result;
    }

}
