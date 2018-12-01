package com.zfsoft.hrm.baseinfo.audit.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditInfo;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditProcess;
import com.zfsoft.hrm.baseinfo.audit.query.AuditProcessQuery;
import com.zfsoft.hrm.baseinfo.audit.service.IAuditProcessService;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;

/** 
 * 审核流程
 * @author jinjj
 * @date 2012-10-11 上午08:41:54 
 *  
 */
public class AuditProcessAction extends HrmAction {

	private static final long serialVersionUID = -3092091516018689734L;

	private IAuditProcessService processService;
	
	private AuditProcessQuery query = new AuditProcessQuery();
	private PageList<AuditProcess> pageList;
	private AuditProcess process;
	private List<AuditInfo> infoList;
	private AuditInfo info;
	
	/**
	 * 审核列表-教职工侧
	 * @return
	 * @throws Exception
	 */
	public String viewPage() throws Exception{
		query.setGh(SessionFactory.getUser().getYhm());
		pageList = processService.getPagingList(query);
		this.setInActionContext("paginator", pageList.getPaginator());
		return "viewPage";
	}
	
	/**
	 * 查看
	 * @return
	 * @throws Exception
	 */
	public String view() throws Exception{
		process = processService.getById(process.getGuid());
		infoList = processService.getInfoList(process.getGuid());
		return "view";
	}
	
	public String viewMore() throws Exception{
		process = processService.getById(process.getGuid());
		infoList = processService.getInfoList(process.getGuid());
		return "viewMore";
	}
	
	/**
	 * 审核列表-审核人侧
	 * @return
	 * @throws Exception
	 */
	public String auditPage() throws Exception{
		if(YhglModel.INNER_USER_ADMIN.equals(SessionFactory.getUser().getYhm())){
			return "adminPrompt";
		}
		
		//用户包含多个角色
		List<String> roleList = SessionFactory.getUser().getJsdms();
		query.setRoleId(roleList);
		query.setGh(getUser().getYhm());
		pageList = processService.getAuditPagingList(query);
		this.setInActionContext("paginator", pageList.getPaginator());
		return "auditPage";
	}
	
	/**
	 * 审核
	 * @return
	 * @throws Exception
	 */
	public String audit() throws Exception{
		process = processService.getById(process.getGuid());
		infoList = processService.getInfoList(process.getGuid());
		return "audit";
	}
	
	/**
	 * 通过
	 * @return
	 * @throws Exception
	 */
	public String pass() throws Exception{
		info.setOperator(SessionFactory.getUser().getYhm());
		processService.doPass(info);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 拒绝
	 * @return
	 * @throws Exception
	 */
	public String reject() throws Exception{
		info.setOperator(SessionFactory.getUser().getYhm());
		processService.doReject(info);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String detail() throws Exception{
		String html = processService.getRecordDetailHtml(process.getGuid());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", html);
		map.put("success", true);
		getValueStack().set(DATA, map);
		return DATA;
	}

	public AuditProcessQuery getQuery() {
		return query;
	}

	public void setQuery(AuditProcessQuery query) {
		this.query = query;
	}

	public PageList<AuditProcess> getPageList() {
		return pageList;
	}

	public void setProcessService(IAuditProcessService processService) {
		this.processService = processService;
	}

	public List<AuditInfo> getInfoList() {
		return infoList;
	}

	public AuditProcess getProcess() {
		return process;
	}

	public void setProcess(AuditProcess process) {
		this.process = process;
	}

	public void setInfo(AuditInfo info) {
		this.info = info;
	}

	public AuditInfo getInfo() {
		return info;
	}
	
}
