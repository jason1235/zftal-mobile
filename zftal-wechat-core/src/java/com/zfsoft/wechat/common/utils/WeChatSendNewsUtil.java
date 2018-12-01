package com.zfsoft.wechat.common.utils;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zfsoft.wechat.common.entity.message.SendNews;
import com.zfsoft.wechat.common.enums.NewsTypeEnum;

/**
 * 
 * @author ChenMinming
 * @date 2015-7-27
 * @version V1.0.0
 */
public class WeChatSendNewsUtil {
	
    private static Logger log = LoggerFactory.getLogger(WeChatSendNewsUtil.class);
    
    /**
     * 组装文本群发消息
     * 
     * @param openId 消息发送对象
     * @param content 文本消息内容
     * @return
     */
    public static String makeNews(SendNews news) {
    	StringBuilder sb = new StringBuilder("{");
    	if(news.getOpenIds()!=null&&news.getOpenIds().size()>0)
    	{
    		sb.append("\"touser\":[");
    		for (String openId : news.getOpenIds()) {
    			sb.append("\""+openId+"\",");
			}
    		sb.deleteCharAt(sb.length()-1);
    		sb.append("],");
    	}
    	else{
    		sb.append("\"filter\":{\"is_to_all\":true},");
    	}
    	if(NewsTypeEnum.TEXT==news.getType()){
    		sb.append("\"text\":{\"content\":\"");
    		sb.append(news.getContent().replace("\"", "\\\""));
    		sb.append("\"},\"msgtype\":\"text\"}");
    	}else{
    		sb.append("\"");
    		sb.append(news.getType().getKey());
    		sb.append("\":{\"media_id\":\"");
    		sb.append(news.getMediaId());
    		sb.append("\"},\"msgtype\":\"");
    		sb.append(news.getType().getKey());
    		sb.append("\"}");
    	}
    	
       return sb.toString();
    }

    /**
     * 发送客服消息
     * 
     * @param accessToken 接口访问凭证
     * @param jsonMsg json格式的客服消息（包括touser、msgtype和消息内容）
     * @return true | false
     */
    public static boolean sendNews(String accessToken,SendNews news) {
        log.info("sendNews START");
        boolean result = false;
        // 拼接请求地址
        String requestUrl;
        if(news.getOpenIds()!=null&&news.getOpenIds().size()>0)
    	{
        	if(news.getOpenIds().size()==1)
        		requestUrl= WeChatConstants.SEND_NEWS_PREVIEW_URL;
        	else
        		requestUrl= WeChatConstants.SEND_NEWS_URL;
        }else{
    		requestUrl = WeChatConstants.SEND_NEWS_ALL_URL;
    	}
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        
//        System.out.println("--------------------JSON-----------------");
//        System.out.println(makeNews(news));
//        System.out.println("--------------------JSON-----------------");
        // 发送客服消息
        JSONObject jsonObject = WeChatCommonUtil.httpsRequest(requestUrl, "POST", makeNews(news));

        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
            	news.setMessageId(jsonObject.getString("msg_id"));
                result = true;
            } else {
                log.error("sendNews FAIL errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        log.info("sendNews END");
        return result;
    }
}