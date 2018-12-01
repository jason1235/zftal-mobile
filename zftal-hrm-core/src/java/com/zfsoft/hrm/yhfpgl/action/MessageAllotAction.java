package com.zfsoft.hrm.yhfpgl.action;

import java.util.List;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.yhfpgl.entity.MessageAllot;
import com.zfsoft.hrm.yhfpgl.service.IMessageAllotService;
import com.zfsoft.service.svcinterface.IJsglService;
import com.zfsoft.service.svcinterface.IYhglService;

/**
 * 
 * @author ChenMinming
 * @date 2015-7-20
 * @version V1.0.0
 */
public class MessageAllotAction extends HrmAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4961466772342477187L;
	private IMessageAllotService messageAllotService;
	private List<MessageAllot> list;
	private MessageAllot messageAllot = new MessageAllot();
	
	private YhglModel yhglQuery = new YhglModel();
	private JsglModel jsglQuery = new JsglModel();
	public String page(){
		list = messageAllotService.findList(messageAllot);
		return "page";
	}

	public String save(){
		messageAllotService.save(list, messageAllot);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String jsglList(){
		jsglQuery.getQueryModel().setShowCount(messageAllot.getPerPageSize());
		jsglQuery.getQueryModel().setCurrentPage(messageAllot.getToPage());
		IJsglService jsglService = SpringHolder.getBean("jsglService",IJsglService.class);
		PageList<JsglModel> pageList = new PageList<JsglModel>();
		
		pageList.addAll(jsglService.getPagedList(jsglQuery));
		pageList.setPaginator(jsglQuery.getQueryModel());
		getValueStack().set("pageList",pageList );
		return "roleList";
	}
	public String personList(){
		yhglQuery.getQueryModel().setShowCount(messageAllot.getPerPageSize());
		yhglQuery.getQueryModel().setCurrentPage(messageAllot.getToPage());
		IYhglService yhglService = SpringHolder.getBean("yhglService",IYhglService.class);
		PageList<YhglModel> pageList = new PageList<YhglModel>();
		pageList.addAll(yhglService.getPagedList(yhglQuery));
		pageList.setPaginator(yhglQuery.getQueryModel());
		getValueStack().set("pageList",pageList );
		return "personList";
	}

	/**
	 * 返回
	 */
	public List<MessageAllot> getList() {
		return list;
	}
	/**
	 * 设置
	 * @param list 
	 */
	public void setList(List<MessageAllot> list) {
		this.list = list;
	}
	/**
	 * 返回
	 */
	public MessageAllot getMessageAllot() {
		return messageAllot;
	}
	/**
	 * 设置
	 * @param messageAllot 
	 */
	public void setMessageAllot(MessageAllot messageAllot) {
		this.messageAllot = messageAllot;
	}
	/**
	 * 设置
	 * @param messageAllotService 
	 */
	public void setMessageAllotService(IMessageAllotService messageAllotService) {
		this.messageAllotService = messageAllotService;
	}

	/**
	 * 返回
	 */
	public YhglModel getYhglQuery() {
		return yhglQuery;
	}

	/**
	 * 设置
	 * @param yhglQuery 
	 */
	public void setYhglQuery(YhglModel yhglQuery) {
		this.yhglQuery = yhglQuery;
	}

	/**
	 * 返回
	 */
	public JsglModel getJsglQuery() {
		return jsglQuery;
	}

	/**
	 * 设置
	 * @param jsglQuery 
	 */
	public void setJsglQuery(JsglModel jsglQuery) {
		this.jsglQuery = jsglQuery;
	}
	
	
	
}
