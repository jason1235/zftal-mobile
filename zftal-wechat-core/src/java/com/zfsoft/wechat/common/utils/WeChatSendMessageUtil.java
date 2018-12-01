package com.zfsoft.wechat.common.utils;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zfsoft.wechat.common.entity.message.Article;
import com.zfsoft.wechat.common.entity.message.Music;
import com.zfsoft.wechat.common.entity.message.Video;

/**
 * 发送消息
 * @author Administrator
 *
 */
public class WeChatSendMessageUtil {
    private static Logger log = LoggerFactory.getLogger(WeChatSendMessageUtil.class);
    
    /**
     * 组装文本客服消息
     * 
     * @param openId 消息发送对象
     * @param content 文本消息内容
     * @return
     */
    public static String makeTextCustomMessage(String openId, String content) {
        // 对消息内容中的双引号进行转义
        content = content.replace("\"", "\\\"");
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
        return String.format(jsonMsg, openId, content);
    }

    /**
     * 组装图片客服消息
     * 
     * @param openId 消息发送对象
     * @param mediaId 媒体文件id
     * @return
     */
    public static String makeImageCustomMessage(String openId, String mediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"%s\"}}";
        return String.format(jsonMsg, openId, mediaId);
    }

    /**
     * 组装语音客服消息
     * 
     * @param openId 消息发送对象
     * @param mediaId 媒体文件id
     * @return
     */
    public static String makeVoiceCustomMessage(String openId, String mediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"voice\",\"voice\":{\"media_id\":\"%s\"}}";
        return String.format(jsonMsg, openId, mediaId);
    }

    /**
     * 组装视频客服消息
     * 
     * @param openId 消息发送对象
     * @param video 视频信息
     * @return
     */
    public static String makeVideoCustomMessage(String openId, Video video) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"video\",\"video\":%s}";
        return String.format(jsonMsg, openId, JSONObject.fromObject(video).toString().replaceAll("\"", "\\\""));
    }

    /**
     * 组装音乐客服消息
     * 
     * @param openId 消息发送对象
     * @param music 音乐对象
     * @return
     */
    public static String makeMusicCustomMessage(String openId, Music music) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"music\",\"music\":%s}";
        jsonMsg = String.format(jsonMsg, openId, JSONObject.fromObject(music).toString().replaceAll("\"", "\\\""));
        return jsonMsg;
    }

    /**
     * 组装图文客服消息
     * 
     * @param openId 消息发送对象
     * @param articleList 图文消息列表
     * @return
     */
    public static String makeNewsCustomMessage(String openId, List<Article> articleList) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
        jsonMsg = String.format(jsonMsg, openId, JSONArray.fromObject(articleList).toString().replaceAll("\"", "\\\""));
        return jsonMsg;
    }
    
    /**
     * 发送客服消息
     * 
     * @param accessToken 接口访问凭证
     * @param jsonMsg json格式的客服消息（包括touser、msgtype和消息内容）
     * @return true | false
     */
    public static boolean sendCustomMessage(String accessToken, String jsonMsg) {
        log.info("sendCustomMessage START");
        boolean result = false;
        // 拼接请求地址
        String requestUrl = WeChatConstants.MESSAGE_SEND_URL;
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        // 发送客服消息
        JSONObject jsonObject = WeChatCommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);

        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = true;
            } else {
                log.error("sendCustomMessage FAIL errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        log.info("sendCustomMessage END");
        return result;
    }

}
