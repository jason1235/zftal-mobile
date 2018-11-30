package com.zfsoft.hrm.manoeuvre.configInfo.entities;

import java.io.Serializable;
import java.util.Date;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.entities.ManoeuvreInfo;
import com.zfsoft.orcus.lang.TimeUtil;

/**
 * 人员调动审核状态信息实体类
 * @author yongjun.fang
 * 编写时间: 2012-10-23 11:11
 *
 */
public class AuditStatus implements Serializable {

	private static final long serialVersionUID = 4296938209213920040L;

	private String sid;//状态信息编号
	
	private ManoeuvreInfo manoeuvreInfo;//人员调动信息
	
	private String taskNodeName;//所属环节名称
	
	private String result;//审核结果
	
	private String opinion;//审核意见
	
	private Date auditTime;//审核时间
	
	private String assessor;//审核人
	
	private String remark;//备注
	
	public static final String RESULT_UNPASS = "0";//审核结果——否决
	
	public static final String RESULT_PASS = "1";//审核结果——通过
	
	public static final String RESULT_RETREAD = "2";//审核结果——退回
	
	public static final String FINALRESULT_UNPASS = "0";//最终审核结果——否决
	
	public static final String FINALRESULT_PASS = "1";//最终审核结果——通过

	/**
	 * 返回
	 * @return 
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * 设置
	 * @param sid 
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getTaskNodeName() {
		return taskNodeName;
	}

	/**
	 * 设置
	 * @param taskNodeName 
	 */
	public void setTaskNodeName(String taskNodeName) {
		this.taskNodeName = taskNodeName;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getResult() {
		return result;
	}

	/**
	 * 设置
	 * @param result 
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getOpinion() {
		return opinion;
	}

	/**
	 * 设置
	 * @param opinion 
	 */
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	/**
	 * 返回
	 * @return 
	 */
	public Date getAuditTime() {
		return auditTime;
	}

	/**
	 * 设置
	 * @param auditTime 
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getAssessor() {
		return assessor;
	}

	/**
	 * 设置
	 * @param assessor 
	 */
	public void setAssessor(String assessor) {
		this.assessor = assessor;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置
	 * @param remark 
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 返回
	 * @return 
	 */
	public ManoeuvreInfo getManoeuvreInfo() {
		return manoeuvreInfo;
	}

	/**
	 * 设置
	 * @param manoeuvreInfo 
	 */
	public void setManoeuvreInfo(ManoeuvreInfo manoeuvreInfo) {
		this.manoeuvreInfo = manoeuvreInfo;
	}
	
	public String getResultText(){
		if(RESULT_UNPASS.equals(result.trim())){
			return "否决";
		}
		else if(RESULT_PASS.equals(result.trim())){
			return "通过";
		}
		else if(RESULT_RETREAD.equals(result.trim())){
			return "退回";
		}
		return "";
	}
	
	public String getAuditTimeText() {
		return TimeUtil.format(auditTime, "yyyy-MM-dd HH:mm:ss");
	}
	
	public DynaBean getPersonInfo(){
		DynaBean result = DynaBeanUtil.getPerson(assessor);
		
		if( result == null ) {
			result = new DynaBean( InfoClassCache.getOverallInfoClass() );
		}
		 
		return result;
	}

}
