package com.zfsoft.workflow.action;

import java.util.HashMap;

import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.workflow.html.flow.WorkFlowViewHtmlCreator;
import com.zfsoft.workflow.model.SpWorkProcedure;
import com.zfsoft.workflow.service.ISpWorkFlowService;

/** 
 * @author jinjj
 * @date 2013-4-2 下午02:36:20 
 *  
 */
public class SpWorkFlowAction extends HrmAction {

	private static final long serialVersionUID = -2293067248035721056L;
	private ISpWorkFlowService spWorkFlowService;
	private String workId;
	private SpWorkProcedure procedure;
	
	public String detail() throws Exception{
		
		return "detail";
	}
	
	public String info() throws Exception{
		procedure = spWorkFlowService.queryWorkFlowByWorkId(workId);
		if(procedure == null){
			throw new RuleException("流程信息未找到或者流程还未开始");
		}else{
			WorkFlowViewHtmlCreator creator = new WorkFlowViewHtmlCreator(procedure);
			String html=creator.html();
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("html", html);
			map.put("success", true);
			getValueStack().set(DATA, map);
		}
		return DATA;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public void setSpWorkFlowService(ISpWorkFlowService spWorkFlowService) {
		this.spWorkFlowService = spWorkFlowService;
	}
	
}
