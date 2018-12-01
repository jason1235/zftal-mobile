package com.zfsoft.hrm.core.tag.workflow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.enumobject.WorkNodeEStatusEnum;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
import com.zfsoft.workflow.model.SpWorkNode;
import com.zfsoft.workflow.model.SpWorkProcedure;
import com.zfsoft.workflow.model.SpWorkTask;
import com.zfsoft.workflow.service.ISpWorkFlowService;

/**
 * 
 * @author ChenMinming
 * @date 2014-9-12
 * @version V1.0.0
 */
public class WorkFlowStatusTag  extends TagSupport {

	private static final long serialVersionUID = 5505794655560018687L;

	private String workID;
	
	private static ISpWorkFlowService spWorkFlowService 
		= SpringHolder.getBean("spWorkFlowService", ISpWorkFlowService.class);
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		SpWorkProcedure workProcedure = spWorkFlowService.queryWorkFlowByWorkId(workID);
		List<SpWorkNode> execNodeList = new ArrayList<SpWorkNode>();
		List<SpWorkNode> alreadyNodeList = null;
		int step = -1;
		boolean pass=true;
		boolean allNodeNoPass=false;
		if(workProcedure!=null){
			for (SpWorkNode wNode : workProcedure.getSpWorkNodeList()) {
				if(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey().equals(wNode.getEstatus())){
					execNodeList.add(wNode);
				}else if(WorkNodeEStatusEnum.ALREADY_EXECUTE.getKey().equals(wNode.getEstatus())){
					if(step!=wNode.getStep()){
						alreadyNodeList = new ArrayList<SpWorkNode>();
						step=wNode.getStep();
						allNodeNoPass=true;
					}
					if(WorkNodeStatusEnum.PASS_AUDITING.getKey().equals(wNode.getStatus())){
						allNodeNoPass=false;
					}
					alreadyNodeList.add(wNode);
				}else if(WorkNodeEStatusEnum.CLOSE.getKey().equals(wNode.getEstatus())){
					pass = false;
					break;
				}
			}
		}else{
			pass=false;
		}
		
		StringBuffer out = new StringBuffer(1024);
		out.append("<div>");
		if(!execNodeList.isEmpty()){
			out.append("<font color='#4686da'>环节:");
			for (SpWorkNode spWorkNode : execNodeList) {
				out.append("【"+spWorkNode.getNodeName()+"】");
			}
			out.append("审核中...</font>");
		}else if(allNodeNoPass||pass){
			out.append("<font color='#54ad61'>流程结束</font>");
		}else if(alreadyNodeList!=null&&alreadyNodeList.size()>0){
			out.append("<font color='#4686da'>任务：");
			for (SpWorkNode spWorkNode : alreadyNodeList) {
				String str="";
				for (SpWorkTask task : spWorkNode.getSpWorkTaskList()) {
					if(WorkNodeEStatusEnum.WAIT_EXECUTE.getKey().equals(task.getEstatus())){
						str+=""+task.getTaskName()+";";
					}
				}
				if(!StringUtil.isEmpty(str))
				out.append("【"+spWorkNode.getNodeName()+":"+str+"】");
			}
			out.append("执行中...</font>");
		}else{
			out.append("<font color='##a6a6a6'>流程未开始</font>");
		}
		out.append("</div>");
		try {
			pageContext.getOut().write(out.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public void setWorkID(String workID) {
		this.workID = workID;
	}
}

