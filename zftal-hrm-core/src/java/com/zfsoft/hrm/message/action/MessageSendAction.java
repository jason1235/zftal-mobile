package com.zfsoft.hrm.message.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.query.MessageQuery;
import com.zfsoft.hrm.message.service.svcinterface.IMessageSendService;
import com.zfsoft.hrm.message.service.svcinterface.IMessageService;
import com.zfsoft.util.base.StringUtil;

/** 
 * 发送消息
 * @author jinjj
 * @date 2012-11-15 上午08:59:18 
 *  
 */
public class MessageSendAction extends HrmAction {

	private static final long serialVersionUID = -5016169948627683028L;
	private IMessageService messageService;
	private IMessageSendService messageSendService;
	private PageList<Message> pageList;
	private MessageQuery query = new MessageQuery();
	private Message msg;
	private String sortFieldName = null;
	private String asc = "up";
	private String hidaudittype;
	
	public String page() throws Exception{
		String username = SessionFactory.getUser().getYhm();
		query.setSender(username);
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " FSSJ DESC" );
		}
		pageList = messageSendService.getPagingList(query);
		return "page";
	}
	
	public String input() throws Exception{
		getValueStack().set("roles", messageSendService.getRoles());
		return "input";
	}
	
	public String info() throws Exception{
		msg = messageSendService.getById(msg.getGuid());
		return "info";
	}
	
	public String delete() throws Exception{
		messageSendService.delete(msg.getGuid());
		setSuccessMessage("删除成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String save() throws Exception{
		String username = SessionFactory.getUser().getYhm();
		msg.setSender(username);
		if ("2".equals(hidaudittype)) {
			msg.setReceiverType("0");
			if(msg != null && StringUtil.isNotEmpty(msg.getReceiver())){
				String[] receviers = msg.getReceiver().split(";");
				int smc = 20;
				String ssmc = SubSystemHolder.getPropertiesValue("sendMessageCnt");
				if (!StringUtils.isEmpty(ssmc)) {
					smc = Integer.parseInt(ssmc);
				}
				if (receviers.length > smc) {
					setErrorMessage("发送对象已超过" + smc + "人，建议选择按角色发送消息。");
					getValueStack().set(DATA, getMessage());
					return DATA;
				}
			}
			messageService.save(msg);
		} else {
			List<String> persons = messageSendService.findPersonByRole(msg);
			String receivers = "";
			for (String receiver : persons) {
				if (StringUtils.isEmpty(receivers)) {
					receivers += receiver;
				} else {
					receivers += ";" + receiver;
				}
			}
			msg.setReceiverType("1");
			msg.setReceiver(receivers);
			messageService.save(msg);
		}
		
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public MessageQuery getQuery() {
		return query;
	}

	public void setQuery(MessageQuery query) {
		this.query = query;
	}

	public Message getMsg() {
		return msg;
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}

	public PageList<Message> getPageList() {
		return pageList;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setMessageSendService(IMessageSendService messageSendService) {
		this.messageSendService = messageSendService;
	}

	public String getSortFieldName() {
		return sortFieldName;
	}

	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}

	/**
	 * @return the hidaudittype
	 */
	public String getHidaudittype() {
		return hidaudittype;
	}

	/**
	 * @param hidaudittype the hidaudittype to set
	 */
	public void setHidaudittype(String hidaudittype) {
		this.hidaudittype = hidaudittype;
	}
	
}
