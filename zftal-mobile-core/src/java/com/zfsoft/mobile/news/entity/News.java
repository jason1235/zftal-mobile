package com.zfsoft.mobile.news.entity;

import java.util.Date;


/**
 * 资讯类实体
 * @author wy
 *
 */
public class News {
	
	private String id; // 资讯ID
	private String title; // 标题
	private String source; // 来源
	private String author; // 作者
	
	private String newsTime; // 时间
	private String catalogCode; // 类别编码
	private String business; // 所属业务系统
	private String intro; // 简介
	private String content; // 内容
	private String picUrl; // 资讯图片地址
	private String picId; // 资讯图片ID
	private String status; // 使用状态
	private String top; // 置顶状态
	private String recommend; // 推荐状态
	private Date createTime; // 创建时间
	private String creator; //创建者ID
	private String updateTime; // 更新时间
	private String updater; // 更新者ID
	private String deleted; // 删除标志
	
	private String headline; //头条状态
	
	private String grabMethod; // 抓取方式
	
	
	private String businessName;
	private String catalogName;
	
	private String showType; //展现形式
	
	private String newsURL;//新闻url
	
	private String fwbm;//对应应用中心的服务编写，方便查询
	
	private String isremotelogo;//是否远程直接访问图片
	
	private String remotelogourl;//logo图片远程地址
	
	private String vedioId;//视频id
	
	private String vedioUrl;//视频url
	
	
	
	public String getVedioId() {
		return vedioId;
	}
	public void setVedioId(String vedioId) {
		this.vedioId = vedioId;
	}
	public String getVedioUrl() {
		return vedioUrl;
	}
	public void setVedioUrl(String vedioUrl) {
		this.vedioUrl = vedioUrl;
	}
	public String getIsremotelogo() {
		return isremotelogo;
	}
	public void setIsremotelogo(String isremotelogo) {
		this.isremotelogo = isremotelogo;
	}
	public String getRemotelogourl() {
		return remotelogourl;
	}
	public void setRemotelogourl(String remotelogourl) {
		this.remotelogourl = remotelogourl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getNewsTime() {
		return newsTime;
	}
	public void setNewsTime(String newsTime) {
		this.newsTime = newsTime;
	}
	public String getCatalogCode() {
		return catalogCode;
	}
	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getGrabMethod() {
		return grabMethod;
	}
	public void setGrabMethod(String grabMethod) {
		this.grabMethod = grabMethod;
	}
	public void setNewsURL(String newsURL) {
		this.newsURL = newsURL;
	}
	public String getNewsURL() {
		return newsURL;
	}
	public void setFwbm(String fwbm) {
		this.fwbm = fwbm;
	}
	public String getFwbm() {
		return fwbm;
	}
	
}
