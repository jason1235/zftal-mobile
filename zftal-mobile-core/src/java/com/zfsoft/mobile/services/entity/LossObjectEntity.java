package com.zfsoft.mobile.services.entity;

import java.util.Date;
import java.util.List;

import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.query.BaseQuery;

public class LossObjectEntity extends BaseQuery{

	private String id;//id，唯一性标识
	private String title;//主题
	private String content;//内容
	private String place;//失物招领放置位置
	private Date timecreate;//创建时间
	private String ispass;//是否审核通过
	private String rejectreason;//审核未通过理由
	private String isover;//是否已被招领
	private String username;//发布者名称
	private String name;//发布者姓名
	private String lossuser;//招领人（丢失此物品的人）
	private Date overtime;//招领时间
	private Date timestart;//创建最小时间
	private Date timeend;//创建最大时间
	private String flag;//是丢失还是捡到 0丢失，1找到
	private String timecreatestr;//创建时间
	private String overtimestr;//招领时间；
	private String detaiURL;//详情URL
	private String telephone;//手机号
	
	private String goodsName;//新版失物招领-物品名称
	private String qq;//新版失物招领-发布者qq号
	private List<LossObjectPictureEntity> pictures;//新版失物招领-相关图片
	
	private String getImageHost() {
		String url = BaseHolder.getPropertiesValue("suploadPath");
		if (url == null) {
			return "/";
		}
        url = url.replace("\\", "/");
        if (!url.endsWith("/")) {
        	url += "/";
        }
		return url;
	}
	public Date getTimestart() {
		return timestart;
	}
	public void setTimestart(Date timestart) {
		this.timestart = timestart;
	}
	public Date getTimeend() {
		return timeend;
	}
	public void setTimeend(Date timeend) {
		this.timeend = timeend;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLossuser() {
		return lossuser;
	}
	public void setLossuser(String lossuser) {
		this.lossuser = lossuser;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		detaiURL = getImageHost() + "serviceManager/loss_detail.html?query.id=" + id;
		
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public Date getTimecreate() {
		return timecreate;
	}
	public void setTimecreate(Date timecreate) {
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		timecreatestr = format.format(timecreate);
		this.timecreate = timecreate;
	}
	public String getIspass() {
		return ispass;
	}
	public void setIspass(String ispass) {
		this.ispass = ispass;
	}
	public String getRejectreason() {
		return rejectreason;
	}
	public void setRejectreason(String rejectreason) {
		this.rejectreason = rejectreason;
	}
	public String getIsover() {
		return isover;
	}
	public void setIsover(String isover) {
		this.isover = isover;
	}
	public void setOvertime(Date overtime) {
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		overtimestr = format.format(overtime);
		this.overtime = overtime;
	}
	public Date getOvertime() {
		return overtime;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFlag() {
		return flag;
	}
	public void setTimecreatestr(String timecreatestr) {
		this.timecreatestr = timecreatestr;
	}
	public String getTimecreatestr() {
		return timecreatestr;
	}
	public void setOvertimestr(String overtimestr) {
		this.overtimestr = overtimestr;
	}
	public String getOvertimestr() {
		return overtimestr;
	}
	public void setDetaiURL(String detaiURL) {
		this.detaiURL = detaiURL;
	}
	public String getDetaiURL() {
		return detaiURL;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getTelephone() {
		return telephone;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public List<LossObjectPictureEntity> getPictures() {
		return pictures;
	}
	public void setPictures(List<LossObjectPictureEntity> pictures) {
		this.pictures = pictures;
	}
	
}
