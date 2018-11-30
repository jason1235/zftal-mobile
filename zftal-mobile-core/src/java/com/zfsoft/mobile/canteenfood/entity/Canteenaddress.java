package com.zfsoft.mobile.canteenfood.entity;

import java.io.Serializable;

import com.zfsoft.dao.query.BaseQuery;

/**
 *个人送餐地址实体封装类
 *@table("M_CANTEEN_ADDRESS") 
 * @author yangbilin
 * @createtime 2017-07-18 11 :46
 */
public class Canteenaddress extends BaseQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String addressId;
	private String userId;
	private String name;
	private String mobilePhone;
	private String schoolName;
	private String specificAddress;
	
	public Canteenaddress() {
		super();
	}
	
	public Canteenaddress(String userId, String name, String mobilePhone,
			String schoolName, String specificAddress) {
		super();
		this.userId = userId;
		this.name = name;
		this.mobilePhone = mobilePhone;
		this.schoolName = schoolName;
		this.specificAddress = specificAddress;
	}


	/**
	 *地址id
	 *@return String 
	 */
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	/**
	 *用户id
	 *@return String 
	 */
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 *用户名称
	 *@return String 
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 *联系方式
	 *@return String 
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	/**
	 *学校名称
	 *@return String 
	 */
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	/**
	 *具体地址
	 *@return String 
	 */
	public String getSpecificAddress() {
		return specificAddress;
	}
	public void setSpecificAddress(String specificAddress) {
		this.specificAddress = specificAddress;
	}
	
	
}
