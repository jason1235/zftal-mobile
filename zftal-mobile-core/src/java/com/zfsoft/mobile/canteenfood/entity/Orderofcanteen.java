package com.zfsoft.mobile.canteenfood.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zfsoft.dao.query.BaseQuery;

/**
 *商家订单实体封装类 
 * @table("M_CANTEEN_ORDER")
 * @author yangbilin
 * @createtime 2017-07-18 11:27
 */
public class Orderofcanteen extends BaseQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String orderId;
	private String userId;
	private String addressid;
	private int personnum;
	private String description;
	private String flag;
	private Date createtime;
	private Date deliverytime;
	private String canteenId;
	private String summation;
	
	private String username;
	private String addressname;
	private String foodname;
	private int famount;
	private String createtimeStr;
	private String deliverytimeStr;
	private Canteen canteen = new Canteen();
	private Canteenaddress address = new Canteenaddress();
	private List<Foodofcanteen> foodlist = new ArrayList<Foodofcanteen>();
	
	
	public Orderofcanteen() {
		super();
	}
	
	
	public Orderofcanteen(String orderId,String userId, String addressid, int personnum,
			String description, String canteenId,Date deliverytime, String summation) {
		super();
		this.orderId=orderId;
		this.userId = userId;
		this.addressid = addressid;
		this.personnum = personnum;
		this.description = description;
		this.canteenId=canteenId;
		this.deliverytime = deliverytime;
		this.summation = summation;
	}

	/**
	 *商家订单id
	 *@return String 
	 */
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 *订单用户id
	 *@return String 
	 */
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 *订单地址id
	 *@return String 
	 */
	public String getAddressid() {
		return addressid;
	}
	public void setAddressid(String addressid) {
		this.addressid = addressid;
	}
	
	/**
	 *用餐人数
	 *@return int 
	 */
	public int getPersonnum() {
		return personnum;
	}
	public void setPersonnum(int personnum) {
		this.personnum = personnum;
	}
	
	/**
	 *订单备注
	 *@return String 
	 */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 *订单状态  0商家未接单，1商家已接单，2商家拒单
	 *@return String 
	 */
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
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
	 *送达时间
	 *@return  Date
	 */
	public Date getDeliverytime() {
		return deliverytime;
	}
	public void setDeliverytime(Date deliverytime) {
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		deliverytimeStr = format.format(createtime);
		this.deliverytime = deliverytime;
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
	 *送达时间
	 *@return String
	 */
	public String getDeliverytimeStr() {
		return deliverytimeStr;
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
	public void setDeliverytimeStr(String deliverytimeStr) {
		this.deliverytimeStr = deliverytimeStr;
	}
	/**
	 *总价 
	 * @return long
	 */
	public String getSummation() {
		return summation;
	}
	public void setSummation(String summation) {
		this.summation = summation;
	}
	
	/**
	 *订单所属商家信息 
	 * @return Canteen
	 */
	public Canteen getCanteen() {
		return canteen;
	}
	public void setCanteen(Canteen canteen) {
		this.canteen = canteen;
	}
	public Canteenaddress getAddress() {
		return address;
	}
	public void setAddress(Canteenaddress address) {
		this.address = address;
	}
	public List<Foodofcanteen> getFoodlist() {
		return foodlist;
	}
	public void setFoodlist(List<Foodofcanteen> foodlist) {
		this.foodlist = foodlist;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddressname() {
		return addressname;
	}

	public void setAddressname(String addressname) {
		this.addressname = addressname;
	}

	public String getFoodname() {
		return foodname;
	}

	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}

	public int getFamount() {
		return famount;
	}

	public void setFamount(int famount) {
		this.famount = famount;
	}
	
}
