package com.zfsoft.mobile.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zfsoft.mobile.pushmsg.entity.AppTypeConfig;
import com.zfsoft.mobile.pushmsg.entity.PushMsg;


//Push通知栏消息Demo
//本示例程序中的appId,appSecret,deviceTokens以及appPkgName需要用户自行替换为有效值
public class HPushMsgImpl{
    private static  String appSecret = "531f5cfcf60cc51d426fa83de25353dd";
    private static  String appId = "100233703";//用户在华为开发者联盟申请的appId和appSecret（会员中心->应用管理，点击应用名称的链接）
    private static  String appPkgName = "com.zfsoft.mhxzyy";
    private static  String tokenUrl = "https://login.vmall.com/oauth2/token"; //获取认证Token的URL
    private static  String apiUrl = "https://api.push.hicloud.com/pushsend.do"; //应用级消息下发API
    private static  String accessToken;//下发通知消息的认证Token
    private static  long tokenExpiredTime;  //accessToken的过期时间
    
    public static void main(String[] args) throws IOException{
        //refreshToken();
    	PushMsg pushMsg = new PushMsg();
    	pushMsg.setTsbt("123");
		pushMsg.setTsnr("hello");
		pushMsg.setExtrasStr("{\"name\":\"liucb\"}");
		pushMsg.setTsdxArray(new String[]{"0865471036302922300001558700CN01"});
        new HPushMsgImpl().sendPushMessage(pushMsg);
    }
    
    //获取下发通知消息的认证Token
    private void refreshToken(PushMsg pushMsg,AppTypeConfig config){
		try {
			String msgBody = MessageFormat.format(
					"grant_type=client_credentials&client_secret={0}&client_id={1}", 
					URLEncoder.encode(config.getMasterSecret(), "UTF-8"), config.getAppKey());
			String response = httpPost(tokenUrl, msgBody, 5000, 5000);
			JSONObject obj = JSONObject.parseObject(response);
			accessToken = obj.getString("access_token");
			tokenExpiredTime = System.currentTimeMillis() + obj.getLong("expires_in") - 5*60*1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    //发送Push消息
    public PushMsgResult sendPushMessage(PushMsg pushMsg){
    	PushMsgResult pmResult = new PushMsgResult();
		try {
			AppTypeConfig config = JPushAppTypeConfig.findEMUIConfig(pushMsg.getAppType());
			/*AppTypeConfig config = new AppTypeConfig();
			config.setAppKey("100233703");
			config.setMasterSecret("531f5cfcf60cc51d426fa83de25353dd");*/
			
			if (tokenExpiredTime <= System.currentTimeMillis()){
				refreshToken(pushMsg,config);
			}      
			
			//先发送透传消息
			sendPushTransMessage(pushMsg,config);
			
			JSONObject body = new JSONObject();//仅通知栏消息需要设置标题和内容，透传消息key和value为用户自定义
			body.put("title", pushMsg.getTsbt()==null||"".equals(pushMsg.getTsbt())? pushMsg.getAppType():pushMsg.getTsbt());//消息标题
			body.put("content", pushMsg.getTsnr());//消息内容体
			
			JSONObject param = new JSONObject();
			param.put("appPkgName",appPkgName);//定义需要打开的appPkgName
			//param.put("intent","#Intent;launchFlags=0x4000000;component=com.zfsoft.mhxzyy/com.zfsoft.affairs.business.affairs.view.New_AffairsListPage;end");//自定义intent
			
			JSONObject action = new JSONObject();
			action.put("type",3);//类型3为打开APP，其他行为请参考接口文档设置
			action.put("param", param);//消息点击动作参数
			
			JSONObject msg = new JSONObject();
			msg.put("type", 3);//3: 通知栏消息，异步透传消息请根据接口文档设置
			msg.put("action", action);//消息点击动作
			msg.put("body", body);//通知栏消息body内容
			
			JSONArray customize = new JSONArray();
			customize.add(pushMsg.getExtrasStr());
			
			JSONObject ext = new JSONObject();//扩展信息，含BI消息统计，特定展示风格，消息折叠。
		    //ext.put("biTag","Trump");//设置消息标签，如果带了这个标签，会在回执中推送给CP用于检测某种类型消息的到达率和状态
		    ext.put("customize",customize);
		    
			JSONObject hps = new JSONObject();//华为PUSH消息总结构体
			hps.put("msg", msg);
			hps.put("ext", ext);
			
			JSONObject payload = new JSONObject();
			payload.put("hps", hps);
			
			String postBody = MessageFormat.format(
					"access_token={0}&nsp_svc={1}&nsp_ts={2}&device_token_list={3}&payload={4}",
					URLEncoder.encode(accessToken,"UTF-8"),
					URLEncoder.encode("openpush.message.api.send","UTF-8"),
					URLEncoder.encode(String.valueOf(System.currentTimeMillis() / 1000),"UTF-8"),
					URLEncoder.encode(JSON.toJSONString(pushMsg.getTsdxArray()),"UTF-8"),
					URLEncoder.encode(payload.toString(),"UTF-8"));
			
			String postUrl = apiUrl + "?nsp_ctx=" + URLEncoder.encode("{\"ver\":\"1\", \"appId\":\"" + config.getAppKey() + "\"}", "UTF-8");
			
			String result = httpPost(postUrl, postBody, 5000, 5000);
			pmResult.setResult(result);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        return pmResult;
    }
    
    
    
    //发送透传消息
    private PushMsgResult sendPushTransMessage(PushMsg pushMsg,AppTypeConfig config){
    	PushMsgResult pmResult = new PushMsgResult();
    	try{
    		if (tokenExpiredTime <= System.currentTimeMillis()){
    			refreshToken(pushMsg,config);
    		}
    		
    		JSONObject body = new JSONObject();
    		body.put("extrasStr",pushMsg.getExtrasStr());//透传消息自定义body内容
    		
    		JSONObject msg = new JSONObject();
    		msg.put("type", 1);//1: 透传异步消息，通知栏消息请根据接口文档设置
    		msg.put("body", body.toString());//body内容不一定是JSON，可以是String，若为JSON需要转化为String发送
    		
    		JSONObject hps = new JSONObject();//华为PUSH消息总结构体
    		hps.put("msg", msg);
    		
    		JSONObject payload = new JSONObject();
    		payload.put("hps", hps);
    		
    		String postBody = MessageFormat.format(
    				"access_token={0}&nsp_svc={1}&nsp_ts={2}&device_token_list={3}&payload={4}",
    				URLEncoder.encode(accessToken,"UTF-8"),
    				URLEncoder.encode("openpush.message.api.send","UTF-8"),
    				URLEncoder.encode(String.valueOf(System.currentTimeMillis() / 1000),"UTF-8"),
    				URLEncoder.encode(JSON.toJSONString(pushMsg.getTsdxArray()),"UTF-8"),
    				URLEncoder.encode(payload.toString(),"UTF-8"));
    		
    		String postUrl = apiUrl + "?nsp_ctx=" + URLEncoder.encode("{\"ver\":\"1\", \"appId\":\"" + config.getAppKey() + "\"}", "UTF-8");
    		String result = httpPost(postUrl, postBody, 5000, 5000);
    		pmResult.setResult(result);
    	}catch (Exception e) {
            e.printStackTrace();
    	}
    	return pmResult;
    }
    
    /*
     * post请求方法
     */
    public static String httpPost(String httpUrl, String data, int connectTimeout, int readTimeout) throws IOException{
        OutputStream outPut = null;
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        
        try{
            URL url = new URL(httpUrl);
            urlConnection = (HttpURLConnection)url.openConnection();          
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            urlConnection.setConnectTimeout(connectTimeout);
            urlConnection.setReadTimeout(readTimeout);
            urlConnection.connect();
            
            // POST data
            outPut = urlConnection.getOutputStream();
            outPut.write(data.getBytes("UTF-8"));
            outPut.flush();
            
            // read response
            if (urlConnection.getResponseCode() < 400){
                in = urlConnection.getInputStream();
            }
            else{
                in = urlConnection.getErrorStream();
            }
            
            List<String> lines = IOUtils.readLines(in, urlConnection.getContentEncoding());
            StringBuffer strBuf = new StringBuffer();
            for (String line : lines){
                strBuf.append(line);
            }
            System.out.println(strBuf.toString());
            return strBuf.toString();
        }finally{
            IOUtils.closeQuietly(outPut);
            IOUtils.closeQuietly(in);
            if (urlConnection != null){
                urlConnection.disconnect();
            }
        }
    }
}

