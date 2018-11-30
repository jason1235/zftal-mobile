package com.zfsoft.hrm.expertvote.expertmanage.action;

import java.util.HashMap;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.dybill.enums.ModeType;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertAudit;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertDeclare;
import com.zfsoft.hrm.expertvote.expertmanage.service.IExpertAuditService;
import com.zfsoft.hrm.expertvote.expertmanage.service.IExpertDeclareService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.enumobject.BusinessEnum;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpBusiness;
import com.zfsoft.workflow.service.ISpBusinessService;

public class ExpertDeclareAction extends HrmAction {
	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = -6270041411715618340L;
	private IExpertDeclareService expertDeclareService;
	private PageList<ExpertDeclare> pageList = new PageList<ExpertDeclare>();
	private ExpertDeclare expertDeclare = new ExpertDeclare();
	
	private ISpBusinessService spBusinessService;
	private IExpertAuditService expertAuditService;

	public ISpBusinessService getSpBusinessService() {
		return spBusinessService;
	}

	public void setSpBusinessService(ISpBusinessService spBusinessService) {
		this.spBusinessService = spBusinessService;
	}

	public String page(){
		expertDeclare.setSbz(getUser().getYhm());
		pageList = expertDeclareService.getPagedList(expertDeclare);
		getValueStack().set("statusArray", WorkNodeStatusEnum.values());
		int beginIndex = pageList.getPaginator().getBeginIndex();
		getValueStack().set("beginIndex",beginIndex);
		return "page";
	}
	
	public String input(){
		return "input";
	}
	
	public String save(){
		if(expertDeclare != null && !"".equals(expertDeclare.getTjrgh()) ){
			String[] tjrgh = expertDeclare.getTjrgh().split(";");
			for(int i=0;i<=tjrgh.length-1;i++){
				expertDeclare.setTjrgh(tjrgh[i]);
				expertDeclare.setSbz(getUser().getYhm());
				expertDeclare.setStatus(WorkNodeStatusEnum.INITAIL.getKey());
				expertDeclareService.insert(expertDeclare);
			}
		}
		this.setSuccessMessage("保存成功！");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete(){
		if(expertDeclare.getId() != null && expertDeclare.getId()!=""){
			expertDeclareService.delete(expertDeclare.getId());
		}
		this.setSuccessMessage("删除成功！");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
/*	public String declare(){
		expertDeclare = expertDeclareService.getById(expertDeclare.getId());
		expertDeclareService.doDeclare(expertDeclare,getUser().getYhm());
		setSuccessMessage("上报成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}*/
	
	/**
	 * 进入自定义表单编辑页面
	 * @return
	 */
	public String detail() {
		expertDeclare = expertDeclareService.getById(expertDeclare.getId());
		SpBusiness spb = spBusinessService.findSpBusinessByBcode(BusinessEnum.ZJ_TJSH.getKey(),null);
		if(spb == null || spb.getProcedure().getSpCommitBillList() == null || spb.getProcedure().getSpCommitBillList().size() == 0){
			return "prompt2";
		}
		expertDeclare.setConfig_id(spb.getProcedure().getSpCommitBillList().get(0).getBillId());
		try {
			if(StringUtil.isNotEmpty(expertDeclare.getConfig_id())){
				expertDeclareService.doInput(expertDeclare,getUser().getYhm());
			}else{
				throw new WorkFlowException("未配置自定义表单！");
			}
		}catch (WorkFlowException e) {
			setErrorMessage(e.getMessage());
		}
		getValueStack().set("forward", getMessage());
		return "forward";
	}
	
	/**
	 * 查看专家推荐预报表单
	 * @return
	 */
	public String add() {
		String privileges = expertDeclareService.doAdd(expertDeclare,getUser().getYhm());
		getValueStack().set("privileges", StringUtil.isEmpty(privileges) ? ModeType.NORMAL.toString() + "[]" : privileges);
		getValueStack().set("path", "list");
		getValueStack().set("code", loadCodeInPage());
		return "declareEdit";
	}
	
	/**
	 * 申报
	 * @return
	 */
	public String push() {
		try{
			expertDeclareService.doPush(expertDeclare,getUser().getYhm());			
		} catch (WorkFlowException e) {
			setErrorMessage(e.getMessage());
		}
		this.setSuccessMessage("上报成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	/**
	 * 加载页面中使用的代码编号
	 * @return
	 */
	private Map<String,String> loadCodeInPage(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("department", ICodeConstants.DM_DEF_ORG);
		//map.put("NEW_AUDITING", StatusEnum.NEW_AUDITING.getKey());
		map.put("WAIT_AUDITING", WorkNodeStatusEnum.WAIT_AUDITING.getKey());
		map.put("IN_AUDITING", WorkNodeStatusEnum.IN_AUDITING.getKey());
		map.put("PASS_AUDITING", WorkNodeStatusEnum.PASS_AUDITING.getKey());
		map.put("NO_PASS_AUDITING", WorkNodeStatusEnum.NO_PASS_AUDITING.getKey());
		map.put("RETURN_AUDITING", WorkNodeStatusEnum.RETURN_AUDITING.getKey());
		return map;
	}
	/**
	 * 查看操作
	 * @param: 
	 * @return:
	 */
	public String view() throws Exception{
		expertDeclare = expertDeclareService.getById(expertDeclare.getId());
		ExpertAudit audit = expertAuditService.getAudit(expertDeclare.getId());
		getValueStack().set("excutedList",audit.getExcutedList());
		getValueStack().set("logList",audit.getLogList());
		SpBusiness spb = spBusinessService.findSpBusinessByBcode(BusinessEnum.ZJ_TJSH.getKey(),expertDeclare.getId());
		getValueStack().set("privilegeExpression",ModeType.SEARCH+"["+spb.getClassesPrivilege()+"]");
		return "view";
	}
	
	public String cancel() throws Exception{
		expertDeclareService.doCancel(expertDeclare.getId());
		setSuccessMessage("取消成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	/**
	 * 返回
	 */
	public PageList<ExpertDeclare> getPageList() {
		return pageList;
	}

	/**
	 * 设置
	 * @param pageList 
	 */
	public void setPageList(PageList<ExpertDeclare> pageList) {
		this.pageList = pageList;
	}

	/**
	 * 设置
	 * @param expertDeclareService 
	 */
	public void setExpertDeclareService(IExpertDeclareService expertDeclareService) {
		this.expertDeclareService = expertDeclareService;
	}

	/**
	 * 设置
	 * @param expertDeclare 
	 */
	public void setExpertDeclare(ExpertDeclare expertDeclare) {
		this.expertDeclare = expertDeclare;
	}
	/**
	 * 设置
	 * @param expertDeclare 
	 */
	public ExpertDeclare getExpertDeclare() {
		return expertDeclare;
	}
	/**
	 * 设置
	 * @param expertAuditService 
	 */
	public void setExpertAuditService(IExpertAuditService expertAuditService) {
		this.expertAuditService = expertAuditService;
	}
}
