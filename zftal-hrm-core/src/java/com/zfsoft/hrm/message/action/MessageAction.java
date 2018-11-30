package com.zfsoft.hrm.message.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.query.MessageQuery;
import com.zfsoft.hrm.message.service.svcinterface.IMessageService;
import com.zfsoft.util.base.StringUtil;

/** 
 * 消息系统
 * @author jinjj
 * @date 2012-8-27 上午09:44:37 
 *  
 */
public class MessageAction extends HrmAction {

	private static final long serialVersionUID = 2064508473637521958L;
	private IMessageService messageService;
	private MessageQuery query = new MessageQuery();
	private PageList<Message> pageList;
	private Message msg;
	private String sortFieldName = null;
	private String asc = "up";
	
	/**
	 * AJAX请求消息列表
	 * @return
	 * @throws Exception
	 */
	public String listData() throws Exception{
		query.setStatus("0");
		User user = SessionFactory.getUser();
		query.setReceiver(user.getYhm());
		query.setPerPageSize(7);
		pageList = messageService.getPagingList(query);
		List<Message> list = pageList;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sumcount", pageList.getPaginator().getItems());
		map.put("success", true);
		map.put("result", list);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	/**
	 * 消息列表
	 * @return
	 * @throws Exception
	 */
	public String page() throws Exception{
		User user = SessionFactory.getUser();
		query.setReceiver(user.getYhm());
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " FSSJ DESC" );
		}
		pageList = messageService.getPagingList(query);
		return "page";
	}
	
	/**
	 * 删除消息
	 * @return
	 * @throws Exception
	 */
	public String del() throws Exception{
		User user = SessionFactory.getUser();
		query.setReceiver(user.getYhm());
		messageService.delete(msg.getGuid());
		setSuccessMessage("删除成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	/**
	 * 查看消息信息,并更新阅读状态
	 * @return
	 * @throws Exception
	 */
	public String info()throws Exception{
		msg = messageService.getById(msg.getGuid());
		return "info";
	}

	public MessageQuery getQuery() {
		return query;
	}

	public void setQuery(MessageQuery query) {
		this.query = query;
	}

	public PageList<Message> getPageList() {
		return pageList;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public Message getMsg() {
		return msg;
	}

	public void setMsg(Message msg) {
		this.msg = msg;
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
}
