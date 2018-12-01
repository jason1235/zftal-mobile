package com.zfsoft.mobile.canteenfood.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zfsoft.common.query.QueryModel;
import com.zfsoft.dao.query.BaseQuery;

/**
 *食品种类封装类 
 * @table("M_CANTEEN_FOODCATALOG")
 * @author yangbilin
 * @createtime 2017-07-18 11:17
 */
public class Foodcataofcanteen extends BaseQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String foodcataId;
	private String canteenId;
	private String foodcataName;
	private String isactive;
	private Date createtime;
	private String canname;
	private String createtimeStr;
	private List<Foodofcanteen> foodlist=new ArrayList<Foodofcanteen>();

	/**
	 *食品种类id
	 *@return String 
	 */
	public String getFoodcataId() {
		return foodcataId;
	}

	public void setFoodcataId(String foodcataId) {
		this.foodcataId = foodcataId;
	}

	/**
	 *商家id  外键，对于商家类
	 *@return String 
	 */
	public String getCanteenId() {
		return canteenId;
	}

	public void setCanteenId(String canteenId) {
		this.canteenId = canteenId;
	}

	/**
	 *食品种类名称
	 *@return String 
	 */
	public String getFoodcataName() {
		return foodcataName;
	}

	public void setFoodcataName(String foodcataName) {
		this.foodcataName = foodcataName;
	}

	/**
	 *食品种类状态   0停用或售完，1为出售
	 *@return String 
	 */
	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
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

	public String getCanname() {
		return canname;
	}

	public void setCanname(String canname) {
		this.canname = canname;
	}

	/**
	 *类别下的食品集合
	 *@return List<Foodofcanteen>
	 */
	public List<Foodofcanteen> getFoodlist() {
		return foodlist;
	}

	public void setFoodlist(List<Foodofcanteen> foodlist) {
		this.foodlist = foodlist;
	}

}
