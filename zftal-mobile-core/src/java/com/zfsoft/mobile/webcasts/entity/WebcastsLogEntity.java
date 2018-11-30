package com.zfsoft.mobile.webcasts.entity;

import java.io.Serializable;
import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

/**
 *移动直播间日志实体封装类 
 * @table("M_WEBCASTS_LOG")
 * @author yangbilin
 * @createtime 2017-08-29 15:31
 */
public class WebcastsLogEntity extends BaseQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String webcastlogId;
	private String webcastId;
	private String userid;
	private String anchorName;//主播名称
	private String roomName;  //直播间名称
	private String picid;     //直播封面图片
	private String picPath;   //直播封面图片路径
	private int dropNum;	  //点击量
	private Date createTime;  //创建时间
		
	
	public WebcastsLogEntity() {
		super();
	}
	
	/**
	 *直播日志id 
	 *@return String
	 */
	public void setWebcastlogId(String webcastlogId) {
		this.webcastlogId = webcastlogId;
	}

	public void setWebcastId(String webcastId) {
		this.webcastId = webcastId;
	}
	/**
	 *直播间id 
	 *@return String
	 */
	public String getWebcastId() {
		return webcastId;
	}

	public String getWebcastlogId() {
		return webcastlogId;
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
	 *直播间封面图id
	 *@return String
	 */
	public String getPicid() {
		return picid;
	}
	public void setPicid(String picid) {
		this.picid = picid;
	}
	/**
	 *直播间封面图路径
	 *@return String
	 */
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
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
	 *创建时间
	 *@return String
	 */
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	


}
