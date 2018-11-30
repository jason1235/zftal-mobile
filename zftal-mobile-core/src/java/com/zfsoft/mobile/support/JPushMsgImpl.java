package com.zfsoft.mobile.support;

/*
 import org.apache.log4j.Logger;*/

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.DeviceType;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.audience.AudienceType;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.PlatformNotification;

import com.common.Config;
import com.google.gson.JsonElement;
import com.zfsoft.mobile.pushmsg.entity.AppTypeConfig;
import com.zfsoft.mobile.pushmsg.entity.PushMsg;
import com.zfsoft.util.base.StringUtil;

public class JPushMsgImpl implements PushMsgSendInterface {

	private final static String pushType = "ALL";
	private final static int maxRetryTimes = 3;

	private static Logger logger = Logger.getLogger(JPushMsgImpl.class);

	public PushMsgResult send(PushMsg pushMsg) {
		PushMsgResult pmResult = new PushMsgResult();
		AppTypeConfig config = JPushAppTypeConfig.findConfig(pushMsg.getAppType());
		JPushClient jpushClient = new JPushClient(config.getMasterSecret(), config.getAppKey(),maxRetryTimes);
	//	JPushClient jpushClient = new JPushClient("e3c66652e178f97fd6050fb0", "0efc3123af158a73d3016f1d",maxRetryTimes);
	    PushPayload payload = buildPushObject(pushMsg);
		
		try {
			
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(payload.toJSON());
			logger.error("Got result - " + result);
			System.out.print(result);
			pmResult.setResult(result.toString());

		} catch (APIConnectionException e) {
			// Connection error, should retry later
			/* logger.error("Connection error, should retry later", e); */
			System.out.println("Connection error, should retry later");
			e.printStackTrace();
		} catch (APIRequestException e) {
			/*
			 * /Should review the error, and fix the request
			 * logger.error("Should review the error, and fix the request", e);
			 * logger.info("HTTP Status: " + e.getStatus());
			 * logger.info("Error Code: " + e.getErrorCode());
			 * logger.info("Error Message: " + e.getErrorMessage());
			 */
			System.out.println("HTTP Status: " + e.getStatus());
		}
		return pmResult;
	}

	public static PushPayload buildPushObject(PushMsg pushMsg) {
		boolean all = false;
		cn.jpush.api.push.model.PushPayload.Builder payload = PushPayload
				.newBuilder();
		cn.jpush.api.push.model.Platform.Builder platform = Platform
				.newBuilder();
		cn.jpush.api.push.model.audience.Audience.Builder audience = Audience
				.newBuilder();
		cn.jpush.api.push.model.Options.Builder options = Options.newBuilder();
		//options.setApnsProduction(true);
		String ApnsProduction = Config.getString("ApnsProduction");
		if(!StringUtil.isEmpty(ApnsProduction) && ApnsProduction.equals("no")){
			options.setApnsProduction(false);
		}else{
			options.setApnsProduction(true);
		}
		//设置推送内容
		if(pushMsg.isNotice()){
			cn.jpush.api.push.model.notification.Notification.Builder notification = Notification
					.newBuilder();
			buildNotification(pushMsg, notification);
			payload.setNotification(notification.build());
		}else{
			cn.jpush.api.push.model.Message.Builder message = Message.newBuilder();
			buildMessage(pushMsg,message);
			payload.setMessage(message.build());
		}
		//推送平台   iOS 开发环境     iOS 生产环境    Android    WinPhone
		buildPlatform(pushMsg, platform);
		//推送对象类型   设备标签(Tag)  设备别名(Alias)  Registration ID
		buildAudience(pushMsg, audience);
		//推送可选项  暂时只设置离线保留时长
		buildOptions(pushMsg, options);

		payload.setPlatform(platform.build());
		payload.setAudience(audience.build());
		payload.setOptions(options.build());
		
		
		return payload.build();
	}

	//
	public static void buildPlatform(final PushMsg pushMsg,
			final cn.jpush.api.push.model.Platform.Builder platform) {
		if (pushType.equals(pushMsg.getTspt())) {
			platform.setAll(true);
		} else {//   iOS 开发环境     iOS 生产环境    Android    WinPhone
			platform.addDeviceType(DeviceType.valueOf(pushMsg.getTspt())).build();
		}
	}

	public static void buildAudience(final PushMsg pushMsg,
			final cn.jpush.api.push.model.audience.Audience.Builder audience) {
		//广播(所有人)
		if (pushMsg.getTsdxlx().equals(pushType)) {
			audience.setAll(true);
		} else {//设备标签(Tag)  设备别名(Alias)  Registration ID
			audience.addAudienceTarget(AudienceTarget.newBuilder()
					.setAudienceType(AudienceType.valueOf(pushMsg.getTsdxlx()))
					.addAudienceTargetValues(pushMsg.getTsdxArray()).build());
		}
	}

	public static void buildNotification(
			final PushMsg pushMsg,
			final cn.jpush.api.push.model.notification.Notification.Builder notification) {
//		notification.setAlert(pushMsg.getTsnr());
//		if(pushMsg.getExtras()!=null&&!pushMsg.getExtras().isEmpty()){
		 Map<String, Boolean> bMap = new HashMap<String, Boolean>();
		 bMap.put("content-available", true);
			notification.addPlatformNotification(
					new PlatformNotification(pushMsg.getTsnr(), pushMsg.getExtras(), null, null, null) {
				@Override
				protected String getPlatform() {
					return "android";
				}
			});
			
			IosNotification ios = IosNotification.newBuilder().setAlert(pushMsg.getTsnr()).setSound("default").
											setContentAvailable(true).addExtras(pushMsg.getExtras()).
											setBadge(
													!StringUtil.isEmpty(pushMsg.getTswds()) ? 
													Integer.parseInt(pushMsg.getTswds()) : 0
													).build();
			notification.addPlatformNotification(ios);
			notification.addPlatformNotification(
					new PlatformNotification(pushMsg.getTsnr(), pushMsg.getExtras(), null, null, null) {
				@Override
				protected String getPlatform() {
					return "winphone";
				}
			});
//		}
	}

	public static void buildMessage(final PushMsg pushMsg,
			final cn.jpush.api.push.model.Message.Builder message) {
		message.setMsgContent(pushMsg.getTsnr()).build();
		if(pushMsg.getExtras()!=null&&!pushMsg.getExtras().isEmpty()){
			message.addExtras(pushMsg.getExtras());
		}
	}
	
	public static void buildOptions(final PushMsg pushMsg,
			final cn.jpush.api.push.model.Options.Builder options) {
		options.setTimeToLive(pushMsg.getTimeToLive());
	}
	
	 public static void main(String[] args) throws IOException{
	    	PushMsg pushMsg = new PushMsg();
	    	pushMsg.setTsbt("123");
			pushMsg.setTsnr("hello");
			pushMsg.setTspt("ALL");
			pushMsg.setTsdxlx("REGISTRATION_ID");
			pushMsg.setTsfs("1");
			pushMsg.setTsdxArray(new String[]{"999"});
	        System.out.println(new JPushMsgImpl().send(pushMsg));
	 }

}
