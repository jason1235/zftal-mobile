package com.zfsoft.mobile.support;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import com.zfsoft.mobile.pushmsg.entity.AppTypeConfig;
import com.zfsoft.mobile.pushmsg.entity.PushMsg;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * 小米推送工具
 * @author liucb
 *
 */
public class MPushMsgImpl{
	private static final String PACKAGENAME = "com.zfsoft.mhxzyy";
	private static final String AppSecret="pZW7BXbz7UbJW7vJO/OQSw==";
	
	/**
	 * 创建小米推送消息
	 * @param messagePayload
	 * @return
	 */
	public Message buildMessage(String messagePayload){
	    Message message = new Message.Builder()
	            .payload(messagePayload)
	            .restrictedPackageName(PACKAGENAME)
	            .passThrough(1)  // 消息使用透传方式
	            .notifyType(1)   // 使用默认提示音提示
	            .build();
	    return message;
	}
	
	/**
	 * 广播消息
	 * @param pushMsg
	 */
	public void sendBroadcast(PushMsg pushMsg){
	    try {
	    	Constants.useOfficial();
	    	AppTypeConfig config = JPushAppTypeConfig.findMIUIConfig(pushMsg.getAppType());
	    	Sender sender = new Sender(config.getMasterSecret());
	    	Message message = new Message.Builder()
	    	.title(pushMsg.getTsbt())
	    	.description(pushMsg.getTsnr())
	    	.payload(pushMsg.getTsnr())
	    	.restrictedPackageName(PACKAGENAME)
	    	.notifyType(1)     // 使用默认提示音提示
	    	.build();
			sender.broadcastAll(message, 3);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据regid推送消息
	 * @param pushMsg
	 */
	public void sendMessageByRegId(PushMsg pushMsg){
	    try {
	    	Constants.useOfficial();
	    	//AppTypeConfig config = JPushAppTypeConfig.findMIUIConfig(pushMsg.getAppType());
	    	//Sender sender = new Sender(config.getMasterSecret());
	    	Sender sender = new Sender(AppSecret);
	    	Message message = new Message.Builder()
	    	.title(pushMsg.getTsbt())
	    	.description(pushMsg.getTsnr())
	    	.payload(pushMsg.getTsnr())
	    	.restrictedPackageName(PACKAGENAME)
	    	.notifyType(1)     // 使用默认提示音提示
	    	.build();
			sender.send(message, "IiB1cqZ43aBQyjZ1dWXPsas8IRvWTSYSE9of60bB3ws=", 3);//根据regID，发送消息到指定设备上
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 根据regid数组推送消息
	 * @param pushMsg
	 */
	public PushMsgResult send(PushMsg pushMsg){
		PushMsgResult pmResult = new PushMsgResult();
	    try {
	    	Constants.useOfficial();
	    	AppTypeConfig config = JPushAppTypeConfig.findMIUIConfig(pushMsg.getAppType());
	    	Sender sender = new Sender(config.getMasterSecret());
	    	Message message = new Message.Builder()
	    	.title(pushMsg.getTsbt()==null||"".equals(pushMsg.getTsbt())? pushMsg.getAppType():pushMsg.getTsbt())
	    	.description(pushMsg.getTsnr())
	    	.payload(pushMsg.getExtrasStr())
	    	.restrictedPackageName(PACKAGENAME)
	    	.notifyType(1)     // 使用默认提示音提示
	    	.build();
	    	List<String> tsdxArray = Arrays.asList(pushMsg.getTsdxArray());
			Result result = sender.send(message,tsdxArray, 3);//根据regID，发送消息到指定设备上
			pmResult.setResult(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pmResult;
	}
	
	public static void main(String[] args) {
		PushMsg pushMsg = new PushMsg();
		pushMsg.setTsbt("123");
		pushMsg.setTsnr("hello");
		try {
			new MPushMsgImpl().sendMessageByRegId(pushMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
