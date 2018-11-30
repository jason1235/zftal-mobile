package com.zfsoft.hrm.staffturn.leaveschool.action;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveFlowInfo;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveProcess;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveStep;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveFlowInfoQuery;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveProcessQuery;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveStepQuery;
import com.zfsoft.hrm.staffturn.leaveschool.service.svcinterface.ILeaveFlowService;
import com.zfsoft.hrm.staffturn.leaveschool.service.svcinterface.ILeaveProcessService;
import com.zfsoft.hrm.staffturn.leaveschool.service.svcinterface.ILeaveStepService;
import com.zfsoft.hrm.util.WordExportUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * 离校流程管理action
 * 
 * @author jinjj
 * @date 2012-8-1 上午03:51:53
 * 
 */
public class LeaveFlowAction extends HrmAction {

	private static final long serialVersionUID = 1320700007399537687L;

	private ILeaveFlowService leaveFlowService;
	private ILeaveProcessService leaveProcessService;
	private ILeaveStepService leaveStepService;
	private IDynaBeanBusiness iDynaBeanBusiness;

	private PageList<LeaveFlowInfo> list;
	private LeaveFlowInfo flow = new LeaveFlowInfo();
	private LeaveFlowInfoQuery query = new LeaveFlowInfoQuery();

	private List<LeaveProcess> processList;
	private List<LeaveStep> stepList;

	private String sortFieldName = null;
	private String asc = "up";
	private String name = null;
	private String model = null;
	
	public String list() throws Exception {
		if (!StringUtil.isEmpty(sortFieldName)) {
			if (asc.equals("up")) {
				query.setOrderStr(sortFieldName);
			} else {
				query.setOrderStr(sortFieldName + " desc");
			}
		} else {
			query.setOrderStr("gh");
		}
		list = leaveFlowService.getPagingList(query);
		return "list";
	}

	@Override
	public String input() throws Exception {
		stepList = leaveStepService.getList(new LeaveStepQuery());
		return "input";
	}

	public String check(){
		checkIn();
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 打印审批表
	 * 
	 * @param:
	 * @return:
	 * @throws Exception 
	 */
	public String infoPrint() throws Exception {
		checkIn();
		String schoolName=SubSystemHolder.getPropertiesValue("school_name");
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("flow", flow);
		data.put("schoolName", schoolName);
		
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/msword");
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent,flow.getDynaBean().getValue("xm")+name+".doc");
		getResponse().setHeader("Content-Disposition", disposition);
		WordExportUtil.getInstance().exportTable(getResponse().getOutputStream(), "retire"+File.separator+model, data);
		return null;
	}

	private void checkIn(){
		flow = leaveFlowService.getById(flow.getAccountId());
		flow.setDynaBean(iDynaBeanBusiness.findUniqueByParam("gh", flow.getAccountId()));
		String type = flow.getType();
		if ("41".equals(type)) {
			name = "辞职审批表";
			model = "spb_cz.xml"; // 辞职审批表模版
		} else if ("21".equals(type) || "22".equals(type) || "23".equals(type)) {
			name = "调离审批表";
			model = "spb_dl.xml"; // 调离审批表模版
		} else {
			setErrorMessage("没有对应离校类型的模版导出！");
		}
	}
	/**
	 * 打印离校单
	 * 
	 * @param:
	 * @return:
	 */
	public String print() throws Exception {
		String param = this.getRequest().getParameter("globalid");
		list = leaveFlowService.getPagingList(query);
		LeaveFlowInfo info = null;
		int index = 0;
		for (LeaveFlowInfo leave : list) {
			String s = (String) leave.getDynaBean().getValues().get("globalid");
			if (s.equals(param)) {
				info = list.get(index);
				break;
			}
			index++;
		}
		LeaveProcessQuery processQuery = new LeaveProcessQuery();
		processQuery.setStatus(null);
		processQuery.setAccountId(flow.getAccountId());
		processList = leaveProcessService.getList(processQuery);
		flow = leaveFlowService.getById(flow.getAccountId());
		getRequest().setAttribute("userName", info.getAccountId());
		DynaBean selfInfo = DynaBeanUtil.getPerson(info.getAccountId());
		String leaveName = null;
		if (selfInfo != null) {
			leaveName = (String) selfInfo.getValue("xm");
		} else {
			leaveName = info.getAccountId();
		}
		getValueStack().set("leaveName", leaveName);
		getValueStack().set("info", info);
		return "print";
	}

	/**
	 * 查看处理信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String info() throws Exception {
		LeaveProcessQuery processQuery = new LeaveProcessQuery();
		processQuery.setStatus(null);
		processQuery.setAccountId(flow.getAccountId());
		processList = leaveProcessService.getList(processQuery);
		flow = leaveFlowService.getById(flow.getAccountId());
		return "info";
	}

	/**
	 * 查看处理信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String personal_info() throws Exception {
		LeaveProcessQuery processQuery = new LeaveProcessQuery();
		processQuery.setStatus(null);
		processQuery.setAccountId(SessionFactory.getUser().getYhm());
		processList = leaveProcessService.getList(processQuery);
		flow = leaveFlowService.getById(SessionFactory.getUser().getYhm());
		return "personal";
	}

	public String save() throws Exception {
		leaveFlowService.save(flow);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	/**
	 * 离校确认页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		flow = leaveFlowService.getById(flow.getAccountId());
		flow.setLeaveDate(new Date());
		LeaveProcessQuery processQuery = new LeaveProcessQuery();
		processQuery.setStatus(null);
		processQuery.setAccountId(flow.getAccountId());
		processList = leaveProcessService.getList(processQuery);
		return "confirm";
	}

	/**
	 * 离校确认
	 * 
	 * @return
	 * @throws Exception
	 */
	public String confirm() throws Exception {
		leaveFlowService.update(flow);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public String export() throws Exception {
		query.setPerPageSize(Integer.MAX_VALUE);
		list();
		return "export";
	}

	public LeaveFlowInfo getFlow() {
		return flow;
	}

	public void setFlow(LeaveFlowInfo flow) {
		this.flow = flow;
	}

	public PageList<LeaveFlowInfo> getList() {
		return list;
	}

	public void setLeaveFlowService(ILeaveFlowService leaveFlowService) {
		this.leaveFlowService = leaveFlowService;
	}

	public List<LeaveProcess> getProcessList() {
		return processList;
	}

	public void setLeaveProcessService(ILeaveProcessService leaveProcessService) {
		this.leaveProcessService = leaveProcessService;
	}

	public List<LeaveStep> getStepList() {
		return stepList;
	}

	public void setLeaveStepService(ILeaveStepService leaveStepService) {
		this.leaveStepService = leaveStepService;
	}

	public LeaveFlowInfoQuery getQuery() {
		return query;
	}

	public void setQuery(LeaveFlowInfoQuery query) {
		this.query = query;
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

	public void setiDynaBeanBusiness(IDynaBeanBusiness iDynaBeanBusiness) {
		this.iDynaBeanBusiness = iDynaBeanBusiness;
	}

}
