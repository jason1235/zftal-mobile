package com.zfsoft.mobile.reportFix.entity;

import java.util.Date;
import java.util.List;

/**
 * 报修实体
 * @author liucb
 *
 */
public class ReportFixEntity {
   private String id; //唯一主键
   private String problem; //问题描述
   private String telephone; //联系方式
   private String status; //状态
   private Date createTime; //创建时间
   private String score; //评价打分
   private String evaluate; //评价内容
   private String userId; //报修人id
   private String userName; //报修人名称
   private String address; //报修地址
   private String repairId; //维修人id
   private String repairName; //
   private String type; //报修类型
   private String content; //备注
   private String picPath;//图片路基
   private String evaPic;//评价图片路径
   private String typeName;// 分类名称
   
   private List<ReportFixPicsEntity> pictures; //图片信息
   
   
   private String bxr;//报修人
   private String wxr;//维修人
   private String bxbt;//报修标题
   
   
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<ReportFixPicsEntity> getPictures() {
		return pictures;
	}
	public void setPictures(List<ReportFixPicsEntity> pictures) {
		this.pictures = pictures;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRepairId() {
		return repairId;
	}
	public void setRepairId(String repairId) {
		this.repairId = repairId;
	}
	public String getRepairName() {
		return repairName;
	}
	public void setRepairName(String repairName) {
		this.repairName = repairName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getEvaPic() {
		return evaPic;
	}
	public void setEvaPic(String evaPic) {
		this.evaPic = evaPic;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getBxr() {
		return bxr;
	}
	public void setBxr(String bxr) {
		this.bxr = bxr;
	}
	public String getWxr() {
		return wxr;
	}
	public void setWxr(String wxr) {
		this.wxr = wxr;
	}
	public String getBxbt() {
		return bxbt;
	}
	public void setBxbt(String bxbt) {
		this.bxbt = bxbt;
	}
	
}
