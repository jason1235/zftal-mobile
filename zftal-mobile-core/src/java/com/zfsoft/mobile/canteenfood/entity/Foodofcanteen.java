package com.zfsoft.mobile.canteenfood.entity;

import java.io.Serializable;
import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

/**
 *食品实体封装类
 *@table("M_CANTEEN_FOOD") 
 *@author yangbilin
 *@createtime 2017-07-18 11：20
 */
public class Foodofcanteen extends BaseQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String foodId;
	private String foodName;
	private String foodCataid;
	private String picId;
	private String picPath;
	private String isactive;
	private int storage;
	private String price;
	private String description;
	private Date createtime;
	
	private String createtimeStr;
	private int famount;
	private String cataname;
	private String canname;
	
	public Foodofcanteen() {
		super();
	}
	
	
	public Foodofcanteen(String foodId, int storage) {
		super();
		this.foodId = foodId;
		this.storage = storage;
	}


	/**
	 *食品id
	 *@return String 
	 */
	public String getFoodId() {
		return foodId;
	}
	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}
	/**
	 *食品名称
	 *@return String 
	 */
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	
	/**
	 *食品种类id  外键，对应种类表
	 *@return String 
	 */
	public String getFoodCataid() {
		return foodCataid;
	}
	public void setFoodCataid(String foodCataid) {
		this.foodCataid = foodCataid;
	}
	
	/**
	 *食品图片id
	 *@return String 
	 */
	public String getPicId() {
		return picId;
	}
	public void setPicId(String picId) {
		this.picId = picId;
	}
	/**
	 *食品图片路径
	 *@return String 
	 */
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	/**
	 *食品状态  0为停售或售完   1为出售
	 *@return String 
	 */
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	
	/**
	 *食品库存
	 *@return int 
	 */
	public int getStorage() {
		return storage;
	}
	public void setStorage(int storage) {
		this.storage = storage;
	}
	
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 *创建时间
	 *@return Date 
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
	 *@return String 
	 */
	public String getCreatetimeStr() {
		return createtimeStr;
	}
	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}
	public int getFamount() {
		return famount;
	}
	public void setFamount(int famount) {
		this.famount = famount;
	}
	public String getCataname() {
		return cataname;
	}
	public void setCataname(String cataname) {
		this.cataname = cataname;
	}
	public String getCanname() {
		return canname;
	}
	public void setCanname(String canname) {
		this.canname = canname;
	}

}
