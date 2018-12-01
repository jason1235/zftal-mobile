package com.zfsoft.mobile.canteenfood.entity;

import java.io.Serializable;


/**
 *订单食品实体封装类
 *@table("M_ORDER_FOOD") 
 *@author yangbilin
 *@createtime 2017-07-18 11:35
 */
public class Foodorder  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String foodId;
	private String orderId;
	private int amount;
	
	
	
	
	public Foodorder() {
		super();
	}

	public Foodorder(String orderId) {
		super();
		this.orderId = orderId;
	}
	
	

	public Foodorder(String foodId, String orderId, int amount) {
		super();
		this.foodId = foodId;
		this.orderId = orderId;
		this.amount = amount;
	}

	/**
	 *食品id 
	 * @return String
	 */
	public String getFoodId() {
		return foodId;
	}
	
	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}
	/**
	 *订单id 
	 * @return String
	 */
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	/**
	 *食品数量
	 * @return int
	 */
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	

}
