package com.zfsoft.hrm.baseinfo.dyna.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.audit.util.AuditRecordHtmlGenerator;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanLogService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.IOperationConfig;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.core.util.SQLGenerateUtil;

/** 
 * @author jinjj
 * @date 2013-1-4 下午04:46:30 
 *  
 */
public class DynaBeanLogAction extends HrmAction {

	private IDynaBeanLogService dynaBeanLogService;
	private DynaBeanQuery query = initQuery();
	private String classId;
	private String logId;
	private String type="";
	private String gh;
	private String username;
	private String before;
	private String end;
	private String operator;
	private PageList<DynaBean> pageList;
	private List<InfoClass> classList;
	private String html = "";
	
	private DynaBeanQuery initQuery(){
		DynaBeanQuery init = new DynaBeanQuery(null);
		init.setPerPageSize(20);
		return init;
	}
	
	public String page() throws Exception{
		classList = InfoClassCache.getInfoClasses();
		if(StringUtils.isEmpty(classId)){
			classId = classList.get(0).getGuid();
//			query.setPerPageSize(20);
		}
		query.setOrderStr("t.operation_time_ desc");
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		query.setClazz(clazz);
		buildQueryExpress();
		pageList = dynaBeanLogService.getPagingList(query);
		return "page";
	}

	private void buildQueryExpress(){
		StringBuilder sb = new StringBuilder();
		SQLGenerateUtil.appendEquals(sb, "t.gh", gh);
		SQLGenerateUtil.appendLike(sb, "o.xm", username);
		SQLGenerateUtil.appendLike(sb, "t.operator_", operator);
		SQLGenerateUtil.appendEquals(sb, "t.operation_", type);
		SQLGenerateUtil.appendTime(sb, "t.operation_time_", before,end);
		query.setExpress(sb.toString());
	}
	
	public String detail(){
		DynaBean logBean = getLog();
		String operate = (String)logBean.getValue("operation_");
		if(IOperationConfig.ADD.equals(operate)){
			html = new AuditRecordHtmlGenerator().parseSaveHtml(logBean);
		}
		if(IOperationConfig.REMOVE.equals(operate)){
			html = new AuditRecordHtmlGenerator().parseDeleteHtml(logBean);
		}
		if(IOperationConfig.MODIFY.equals(operate)){
			DynaBean old = getOldLog(logBean);
			html = new AuditRecordHtmlGenerator().parseUpdateHtml(logBean, old);
		}
		if(StringUtils.isEmpty(html)){
			throw new RuleException("日志操作类型非法，无法解析");
		}
		return "detail";
	}
	
	private DynaBean getLog(){
		if(StringUtils.isEmpty(classId)){
			throw new RuleException("信息类ID缺失");
		}
		if(StringUtils.isEmpty(logId)){
			throw new RuleException("日志ID缺失");
		}
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		DynaBean logBean = new DynaBean(clazz);
		logBean.setValue("logid", logId);
		logBean = dynaBeanLogService.findById(logBean);
		return logBean;
	}
	
	private DynaBean getOldLog(DynaBean log){
		Date operateTime = (Date)log.getValue("operation_time_");
		String globalid = (String)log.getValue("globalid");
		query.setClazz(log.getClazz());
		query.setPerPageSize(2);
		query.setOrderStr("t.operation_time_ desc");
		StringBuilder sb = new StringBuilder();
		SQLGenerateUtil.appendEquals(sb, "t.globalid", globalid);
		SQLGenerateUtil.appendTime(sb, "t.operation_time_", null,operateTime);
		query.setExpress(sb.toString());
		pageList = dynaBeanLogService.getPagingList(query);
		if(pageList.size()>1){
			return pageList.get(1);
		}else{
			//DynaBean old = new DynaBean(log.getClazz());
			return log;
		}
	}
	
	public String userPage() throws Exception{
		classList = InfoClassCache.getInfoClasses();
//		query.setPerPageSize(20);
		query.setOrderStr("t.operation_time_ desc");
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		query.setClazz(clazz);
		buildQueryExpress();
		pageList = dynaBeanLogService.getPagingList(query);
		return "userPage";
	}
	
	public String userDetail(){
		DynaBean logBean = getLog();
		gh=(String)logBean.getValue("gh");
		String operate = (String)logBean.getValue("operation_");
		if(IOperationConfig.ADD.equals(operate)){
			html = new AuditRecordHtmlGenerator().parseSaveHtml(logBean);
		}
		if(IOperationConfig.REMOVE.equals(operate)){
			html = new AuditRecordHtmlGenerator().parseDeleteHtml(logBean);
		}
		if(IOperationConfig.MODIFY.equals(operate)){
			DynaBean old = getOldLog(logBean);
			html = new AuditRecordHtmlGenerator().parseUpdateHtml(logBean, old);
		}
		if(StringUtils.isEmpty(html)){
			throw new RuleException("日志操作类型非法，无法解析");
		}
		return "userDetail";
	}
	
	public DynaBeanQuery getQuery() {
		return query;
	}

	public void setQuery(DynaBeanQuery query) {
		this.query = query;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public PageList<DynaBean> getPageList() {
		return pageList;
	}

	public void setDynaBeanLogService(IDynaBeanLogService dynaBeanLogService) {
		this.dynaBeanLogService = dynaBeanLogService;
	}

	public List<InfoClass> getClassList() {
		return classList;
	}

	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getHtml() {
		return html;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
