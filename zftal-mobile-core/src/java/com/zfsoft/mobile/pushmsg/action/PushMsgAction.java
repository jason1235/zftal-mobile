package com.zfsoft.mobile.pushmsg.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.common.utils.JSONUtils;
import com.zfsoft.mobile.group.query.PushGroupQuery;
import com.zfsoft.mobile.group.service.IPushGroupService;
import com.zfsoft.mobile.pushmsg.entity.PushMsg;
import com.zfsoft.mobile.pushmsg.query.PushMsgQuery;
import com.zfsoft.mobile.pushmsg.service.IPushMsgService;
import com.zfsoft.mobile.services.entity.ExamDyJsEntity;
import com.zfsoft.mobile.services.service.IQuestionService;
import com.zfsoft.mobile.support.JPushAppTypeConfig;
import com.zfsoft.service.svcinterface.IYhglService;
import com.zfsoft.util.base.StringUtil;

public class PushMsgAction extends HrmAction {
	private static final long serialVersionUID = 4147955076217135704L;
	private IPushMsgService pushMsgService;
	private PushMsg pushMsg;
	private PageList<PushMsg> pageList;
	private PushMsgQuery query = new PushMsgQuery();
	private YhglModel yhglQuery=new YhglModel();
	private PushGroupQuery groupQuery=new PushGroupQuery();
	private String qzidList;
	private IQuestionService questionService;
	private String papermainid;

	public String send() throws Exception {
		pushMsg = new PushMsg();
		getValueStack().set("appList", JPushAppTypeConfig.getConfigList());
		return "send";
	}

	public String add() throws Exception {
		pushMsg.setTsry(getUser().getYhm());
		Gson gson = new Gson();
		pushMsg.setExtrasStr(gson.toJson(pushMsg.getExtras()));
		pushMsgService.save(pushMsg);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		getValueStack().set(DATA, map);
		//this.setSuccessMessage("操作成功！");
		//getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String fxptadd() throws Exception {
		Gson gson = new Gson();
		pushMsg.setExtrasStr(gson.toJson(pushMsg.getExtras()));
		pushMsgService.save(pushMsg);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String query() throws Exception {
		pushMsg = pushMsgService.getEntity(pushMsg);
		return EDIT_PAGE;
	}
	
	public String list() throws Exception {
		if (getInt("toPage") != -1) {
			query.setToPage(this.getInt("toPage"));
		}
		query.setOrderStr(" tssj desc");
		pageList = pushMsgService.getPageList(query);
		this.setInActionContext("paginator", pageList.getPaginator());
		return LIST_PAGE;
	}
	
	public String examGroupList(){
		if(!StringUtil.isEmpty(papermainid)){
			ExamDyJsEntity entity = new ExamDyJsEntity();
			entity.setPapermainid(papermainid);
			List<ExamDyJsEntity> ExamDyJsList = questionService.getExamDyJsList(entity);
			String qzid = "";
			if(ExamDyJsList!=null&&ExamDyJsList.size()>=0){
				for(ExamDyJsEntity dyEntity: ExamDyJsList){
					qzid += dyEntity.getQzid()+",";
				}
				if(!StringUtil.isEmpty(qzid)){
					qzid = qzid.substring(0, qzid.length()-1);
				}
			}
			
			if(!StringUtil.isEmpty(qzid)){
				String[] qzid2 = qzid.split(",");
				getValueStack().set("qzid", qzid2);
			}
		}
		
		
		
		groupQuery.setStatus("1");
		IPushGroupService pushGroupService = SpringHolder.getBean("pushGroupService",IPushGroupService.class);
		groupQuery.setPerPageSize(10);
		getValueStack().set("list", pushGroupService.getPageList(groupQuery));
		return "examGroupList";
	}
	public String groupList(){
		groupQuery.setStatus("1");
		IPushGroupService pushGroupService = SpringHolder.getBean("pushGroupService",IPushGroupService.class);
		getValueStack().set("list", pushGroupService.getPageList(groupQuery));
		return "groupList";
	}
	public String personList(){
		yhglQuery.getQueryModel().setShowCount(groupQuery.getPerPageSize());
		yhglQuery.getQueryModel().setCurrentPage(groupQuery.getToPage());
		IYhglService yhglService = SpringHolder.getBean("yhglService",IYhglService.class);
		PageList<YhglModel> pageList = new PageList<YhglModel>();
		pageList.addAll(yhglService.getPagedList(yhglQuery));
		pageList.setPaginator(yhglQuery.getQueryModel());
		getValueStack().set("list",pageList );
		return "personList";
	}

	public PushMsg getPushMsg() {
		return pushMsg;
	}

	public void setPushMsg(PushMsg pushMsg) {
		this.pushMsg = pushMsg;
	}

	public PushMsgQuery getQuery() {
		return query;
	}

	public PageList<PushMsg> getPageList() {
		return pageList;
	}

	public void setPageList(PageList<PushMsg> pageList) {
		this.pageList = pageList;
	}

	public void setQuery(PushMsgQuery query) {
		this.query = query;
	}

	public IPushMsgService getPushMsgService() {
		return pushMsgService;
	}

	public void setPushMsgService(IPushMsgService pushMsgService) {
		this.pushMsgService = pushMsgService;
	}
	/*
	 * public String add()throws Exception{ Map<String,Object> map = new
	 * HashMap<String,Object>(); map.put("result", "1");
	 * pushMsgService.save(pushMsg); getValueStack().set(DATA, map); return
	 * DATA; }
	 */

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
	public PushGroupQuery getGroupQuery() {
		return groupQuery;
	}

	/**
	 * 设置
	 * @param groupQuery 
	 */
	public void setGroupQuery(PushGroupQuery groupQuery) {
		this.groupQuery = groupQuery;
	}

	public void setQzidList(String qzidList) {
		this.qzidList = qzidList;
	}

	public String getQzidList() {
		return qzidList;
	}

	/*public void setQuestionService(IQuestionService questionService) {
		this.questionService = questionService;
	}

	public IQuestionService getQuestionService() {
		return questionService;
	}*/

	public IQuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionService(IQuestionService questionService) {
		this.questionService = questionService;
	}

	public void setPapermainid(String papermainid) {
		this.papermainid = papermainid;
	}

	public String getPapermainid() {
		return papermainid;
	}
}
