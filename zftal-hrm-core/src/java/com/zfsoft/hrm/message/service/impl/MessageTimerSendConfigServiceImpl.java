package com.zfsoft.hrm.message.service.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.message.business.IMessageBusiness;
import com.zfsoft.hrm.message.config.MessageTimerSendConfig;
import com.zfsoft.hrm.message.dao.daointerface.MessageTimerSendConfigDao;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.service.svcinterface.MessageTimerSendConfigService;
import com.zfsoft.service.base.BaseAnServiceImpl;
/**
 * 消息定时发送配置service
 * @author gonghui
 *
 */
public class MessageTimerSendConfigServiceImpl extends BaseAnServiceImpl<MessageTimerSendConfig> 
				implements MessageTimerSendConfigService,Job{
	
	private static final Pattern PATTERN_CONTENT=Pattern.compile("#.*#");
	
	private MessageTimerSendConfigDao messageTimerSendConfigDao;
	private IMessageBusiness messageBusiness;
	
	@Override
	public MessageTimerSendConfigDao getDao() {
		return messageTimerSendConfigDao;
	}

	@Override
	public void sendMessages(MessageTimerSendConfig messageTimerSendConfig) {
		List<Map<String,Object>> list= messageTimerSendConfigDao.executeSelectSql(messageTimerSendConfig.getSql());
		
		if(list==null||list.size()==0){
			return;
		}
		if("1".equals(messageTimerSendConfig.getSftzbr())){
			//通知本人
			this.sendMessage2Self(list, messageTimerSendConfig);
		}
		
		if(StringUtils.isNotEmpty(messageTimerSendConfig.getJsr())){
			//通知接收人
			this.sendMessage2Others(list, messageTimerSendConfig);
		}
		
	}
	
	@Override
	public void sendMessage(MessageTimerSendConfig messageTimerSendConfig,List<Map<String,Object>> list) {
		
		if(list==null||list.size()==0){
			return;
		}
		if("1".equals(messageTimerSendConfig.getSftzbr())){
			//通知本人
			this.sendMessage2Self(list, messageTimerSendConfig);
		}
		
		if(StringUtils.isNotEmpty(messageTimerSendConfig.getJsr())){
			//通知接收人
			this.sendMessage2Others(list, messageTimerSendConfig);
		}
		
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		MessageTimerSendConfigService messageTimerSendConfigService=(MessageTimerSendConfigService) SpringHolder.getBean("messageTimerSendConfigService");
		JobDataMap map =arg0.getTrigger().getJobDataMap();
		MessageTimerSendConfig messageTimerSendConfig=(MessageTimerSendConfig)map.get("data");
		messageTimerSendConfigService.sendMessages(messageTimerSendConfig);
		
	}
	
	@Override
	public void executeSelectSql(String sql) {
		messageTimerSendConfigDao.executeSelectSql("select * from ("+sql+") where rownum<1");
		
	}

	/**
	 * 通知本人
	 * @param list
	 * @param title
	 * @param content
	 */
	private void sendMessage2Self(List<Map<String,Object>> list,MessageTimerSendConfig messageTimerSendConfig){
		String title=messageTimerSendConfig.getXxbtmb_br();
		String content=messageTimerSendConfig.getXxnrmb_br();
		for (Map<String, Object> map : list) {
			String realContent=this.parseTeplate(content, map);
			Message message=this.buildMessage(title, realContent, String.valueOf(map.get("GH")));
			messageBusiness.save(message);
		}
		
	}
	/**
	 * 通知接收人
	 * @param list
	 * @param messageTimerSendConfig
	 */
	private void sendMessage2Others(List<Map<String,Object>> list,MessageTimerSendConfig messageTimerSendConfig){
		String title=messageTimerSendConfig.getXxbtmb_jsr();
		String content=messageTimerSendConfig.getXxnrmb_jsr();
		//显示个数
		int num=(messageTimerSendConfig.getZjxsgs()==null||messageTimerSendConfig.getZjxsgs()==0) ? 10:messageTimerSendConfig.getZjxsgs();
		Matcher matcher= PATTERN_CONTENT.matcher(content);
		StringBuilder sb=new StringBuilder();
		if(matcher.find()){
			String head=content.substring(0,matcher.start());
			String fragment=content.substring(matcher.start()+1,matcher.end()-1);
			String tail=content.substring(matcher.end());
			int i=1;
			for (Map<String, Object> map : list) {
				if(i>num){
					break;
				}
				i++;
				String realContent=this.parseTeplate(fragment, map);
				if(sb.length()>0){
					sb.append(",");
				}
				sb.append(realContent);
			}
			if(list.size()>num){
				sb.append("等");
			}
			sb.append(tail);
			sb.insert(0, head);
		}else{
			sb.append(content);
		}
		for (String receiver : messageTimerSendConfig.getJsr().split(",")) {
			Message message=this.buildMessage(title, sb.toString(),receiver);
			messageBusiness.save(message);
		}
		
	}

	private String parseTeplate(String template,Map<String,Object> map){
		String rtn=template;
		for (String key : map.keySet()) {
			rtn=Pattern.compile("\\$"+key+"", Pattern.CASE_INSENSITIVE).matcher(rtn).replaceAll(String.valueOf(map.get(key)));
		}
		return rtn;
	}
	
	private Message buildMessage(String title,String content,String receiver){
		Message message = new Message();
		message.setSender("system");
		message.setTitle(title);
		message.setContent(content);
		message.setReceiver(receiver);
		return message;
	}
	
	public MessageTimerSendConfigDao getMessageTimerSendConfigDao() {
		return messageTimerSendConfigDao;
	}

	public void setMessageTimerSendConfigDao(
			MessageTimerSendConfigDao messageTimerSendConfigDao) {
		this.messageTimerSendConfigDao = messageTimerSendConfigDao;
	}

	public IMessageBusiness getMessageBusiness() {
		return messageBusiness;
	}

	public void setMessageBusiness(IMessageBusiness messageBusiness) {
		this.messageBusiness = messageBusiness;
	}
	
}