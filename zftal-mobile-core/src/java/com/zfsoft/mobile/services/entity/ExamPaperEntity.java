package com.zfsoft.mobile.services.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.common.Config;
import com.zfsoft.dao.query.BaseQuery;

public class ExamPaperEntity  extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6815825289651020609L;
	
	private String papermainid;//问卷主表id
	private String papermainname;//问卷主表名称
	private String instruction;//问卷介绍
	private Date   createtime;//创建时间或最后一次修改时间
	private Date   starttime;//问卷结束时间
	private Date   endtime;//问卷结束时间
	private String remark;//备注
	private String creater;//创建者
	private long   timeBetween;//时间差
	private String starttimeStr;//问卷开始时间
	private String endtimeStr;//问卷结束时间
	private String createtimeStr;//创建时间
	private List<ExamQuestionEntity>  questionList;
	private String resultUrl;
	private String flag;
	private String isOver;
	private String qn_owner;
	
	
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		SimpleDateFormat time=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
		starttimeStr = time.format(starttime); 
		this.starttime = starttime;
	}
	public String getStarttimeStr() {
		return starttimeStr;
	}
	public void setStarttimeStr(String starttimeStr) {
		this.starttimeStr = starttimeStr;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
		SimpleDateFormat time=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
		createtimeStr = time.format(createtime); 
	}
	public void setEndtime(Date endtime) {
		Date nowDate = new Date();
	    long diff = endtime.getTime() - nowDate.getTime();
	    setTimeBetween(diff);
	    SimpleDateFormat time=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
	    endtimeStr = time.format(endtime); 
		this.endtime = endtime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemark() {
		return remark;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getCreater() {
		return creater;
	}
	public void setPapermainid(String papermainid) {
		this.setResultUrl(Config.getString("suploadPath")+"serviceManager/question_getExamResult.html?papermainid="+papermainid);
		this.papermainid = papermainid;
	}
	public String getPapermainid() {
		return papermainid;
	}
	public void setPapermainname(String papermainname) {
		this.papermainname = papermainname;
	}
	public String getPapermainname() {
		return papermainname;
	}
	public void setTimeBetween(long timeBetween) {
		this.timeBetween = timeBetween;
	}
	public long getTimeBetween() {
		return timeBetween;
	}
	public void setEndtimeStr(String endtimeStr) {
		this.endtimeStr = endtimeStr;
	}
	public String getEndtimeStr() {
		return endtimeStr;
	}
	public void setQuestionList(List<ExamQuestionEntity> questionList) {
		this.questionList = questionList;
	}
	public List<ExamQuestionEntity> getQuestionList() {
		return questionList;
	}
	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}
	public String getCreatetimeStr() {
		return createtimeStr;
	}
	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
	}
	public String getResultUrl() {
		return resultUrl;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFlag() {
		return flag;
	}
	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}
	public String getIsOver() {
		return isOver;
	}
	public void setQn_owner(String qn_owner) {
		this.qn_owner = qn_owner;
	}
	public String getQn_owner() {
		return qn_owner;
	}
	

}
