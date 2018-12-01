package com.zfsoft.mobile.suggest.entity;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;

import com.common.Config;
import com.zfsoft.dao.query.BaseQuery;

public class SuggestEntity extends BaseQuery{
	private static final Logger log = Logger.getLogger(SuggestEntity.class);
	
	private String id;//id值,唯一性标识
	private String userName;//用户名
	private Date createTime;//创建时间
	private String schoolCode;//学校编码
	private String versionNumber;//版本号
	private String telephone;//手机号
	private String qq;//qq号
	private String textContent;//意见反馈文本内容
	private String replyContent;//意见反馈回复内容
	private String isApp;//是否app访问
	private String detailUrl;//详情访问url，给学校使用

	private List<suggestPictureEntity> suggestPictureList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.detailUrl = Config.getString("suploadPath", "http://portal.zfsoft.com:9090/zftal-mobile/") 
					+ "suggest/suggest_getDetail.html?query.id=" + id;
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public List<suggestPictureEntity> getSuggestPictureList() {
		return suggestPictureList;
	}

	public void setSuggestPictureList(List<suggestPictureEntity> suggestPictureList) {
		this.suggestPictureList = suggestPictureList;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getReplyContent() {
//		String portal_adress =  Config.getString("portal_adress","http://10.71.33.72:8078/zftal-mobile/")+"attached/";
//		replyContent = replyContent.replaceAll("/zftal-mobile/attached/", portal_adress);
		return replyContent;
	}

	public void setIsApp(String isApp) {
		this.isApp = isApp;
	}

	public String getIsApp() {
		return isApp;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getDetailUrl() {
		//return detailUrl;
		try {
			return java.net.URLDecoder.decode(detailUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e, e.fillInStackTrace());
			return null;
		}
	}
	
	
	
	
}
