package com.zfsoft.mobile.webservices.entity;

import java.util.List;

import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.pushmsg.entity.PushMsg;

/**
 * 
 * @author ChenMinming
 * @date 2015-4-27
 * @version V1.0.0
 */
public class PushListPage {

	private List<PushMsg> msgList;

	private Paginator paginator;
	/**
	 * 返回
	 */
	public List<PushMsg> getMsgList() {
		return msgList;
	}
	/**
	 * 设置
	 * @param msgList 
	 */
	public void setMsgList(List<PushMsg> msgList) {
		this.msgList = msgList;
	}
	/**
	 * 返回
	 */
	public Paginator getPaginator() {
		return paginator;
	}
	/**
	 * 设置
	 * @param paginator 
	 */
	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}
}
