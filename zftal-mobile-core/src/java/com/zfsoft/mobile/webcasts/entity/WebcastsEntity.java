package com.zfsoft.mobile.webcasts.entity;

import java.io.Serializable;
import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

/**
 *移动直播间实体封装类 
 * @table("M_WEBCASTS")
 * @author yangbilin
 * @createtime 2017-08-29 15:30
 */
public class WebcastsEntity extends BaseQuery implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String webcastId;
	private String userid;
	private String anchorName;//主播名称
	private String roomName;  //直播间名称
	private byte[] piccon;    //直播封面图片
	private String picPath;   //直播封面图片路径
	private String isactive;  //直播间状态  0禁用  1启用
	private String status;    //直播状态  0创建  1直播中  2结束
	private String screenMode; //直播间屏幕:0竖屏  1 横屏  2无
	private int dropNum;	  //点击量
	private String description;//直播间描述
	private Date createTime;  //创建时间
	private Date updateTime;  //更新时间
	
	private String createtimeStr;
	private String updatetimeStr;
	private String username;
	
	public WebcastsEntity() {
		super();
	}
					
	public WebcastsEntity(String userid, String anchorName, String roomName,
			byte[] piccon, String picPath, String description) {
		super();
		this.userid = userid;
		this.anchorName = anchorName;
		this.roomName = roomName;
		this.piccon = piccon;
		this.picPath = picPath;
		this.description = description;
	}				
					
	
	/**
	 *直播间id 
	 *@return String
	 */
	public String getWebcastId() {
		return webcastId;
	}

	public void setWebcastId(String webcastId) {
		this.webcastId = webcastId;
	}
	/**
	 *用户id 
	 *@return String
	 */
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 *主播名称
	 *@return String
	 */
	public String getAnchorName() {
		return anchorName;
	}
	public void setAnchorName(String anchorName) {
		this.anchorName = anchorName;
	}
	/**
	 *直播间名称
	 *@return String
	 */
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	/**
	 *直播间封面图路径
	 *@return String
	 */
	public String getPicPath() {
		return picPath;
	}
	public byte[] getPiccon() {
		return piccon;
	}
	/**
	 *直播间封面图
	 *@return String
	 */
	
	public void setPiccon(byte[] piccon) {
		this.piccon = piccon;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	/**
	 *直播间状态  0禁用  1启用
	 *@return String
	 */
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	/**
	 *直播状态  0创建  1直播中  2结束
	 *@return String
	 */
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 *点击量
	 *@return String
	 */
	public int getDropNum() {
		return dropNum;
	}
	public void setDropNum(int dropNum) {
		this.dropNum = dropNum;
	}
	/**
	 *直播间描述
	 *@return String
	 */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 *创建时间
	 *@return Date
	 */
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		createtimeStr = format.format(createTime);
		this.createTime = createTime;
	}
	
	/**
	 *创建时间
	 *@return  String
	 */
	public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}
	
	/**
	 *更新时间
	 *@return Date
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		updatetimeStr = format.format(updateTime);
		this.updateTime = updateTime;
	}

	public String getUpdatetimeStr() {
		return updatetimeStr;
	}

	public void setUpdatetimeStr(String updatetimeStr) {
		this.updatetimeStr = updatetimeStr;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 *屏幕方式 0竖屏  1 横屏  2无
	 *@return String
	 */
	public String getScreenMode() {
		return screenMode;
	}

	public void setScreenMode(String screenMode) {
		this.screenMode = screenMode;
	}

}
