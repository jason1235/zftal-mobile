package com.zfsoft.mobile.canteenfood.entity;

import java.io.Serializable;
import java.util.Date;

import com.zfsoft.common.query.QueryModel;
import com.zfsoft.dao.query.BaseQuery;

/**
 *商家实体封装类 
 * @table("M_CANTEEN")
 * @author yangbilin
 * @createtime 2017-07-18 11:00
 */
public class Canteen extends BaseQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String canteenId;
	private String canteenName;
	private String description;
	private String picId;
	private String picPath = null;

	private String isactive;
	private Date createtime;
	private String telephone;

	private double distance;
	
	private String sum;
	private double jl;
	private String createtimeStr;
	
	public Canteen() {
		super();
	}
	
	
	public Canteen(String canteenName,String isactive) {
		super();
		this.canteenName = canteenName;
		this.isactive=isactive;
	}


	/**
	 *商家id 
	 *@return String
	 */
	public String getCanteenId() {
		return canteenId;
	}

	public void setCanteenId(String canteenId) {
		this.canteenId = canteenId;
	}

	/**
	 *商家名称 
	 *@return String
	 */
	public String getCanteenName() {
		return canteenName;
	}

	public void setCanteenName(String canteenName) {
		this.canteenName = canteenName;
	}

	/**
	 *商家描述
	 *@return String
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 *商家图片id
	 *@return String
	 */
	public String getPicId() {
		return picId;
	}

	/**
	 *商家图片id
	 *@return String
	 */
	public void setPicId(String picId) {
		this.picId = picId;
	}

	/**
	 *状态   0为停用或打烊，1启用或营业中
	 *@return int
	 */
	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	
	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}


	/**
	 *创建时间
	 *@return  Date
	 */
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		createtimeStr = format.format(createtime);
		this.createtime = createtime;
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
	 *商家电话 
	 *@return String
	 */
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	/**
	 *距离
	 *@return double 
	 */
	public double getJl() {
		return jl;
	}


	public void setJl(double jl) {
		this.jl = jl;
	}


	public String getPicPath() {
		return picPath;
	}


	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}



}
