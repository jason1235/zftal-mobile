package com.zfsoft.mobile.canteenfood.dao;

import java.util.List;

import com.zfsoft.mobile.canteenfood.entity.Foodorder;

/**
 *订单中包含食品的一对多dao类
 *@author yangbilin
 *@createtime 2017-07-20 15:30  
 */
public interface IFoodorderDao {
	/**
	 *根据订单id获取食品id
	 *@param query
	 *@return  List<String>
	 *@author yangbilin
	 */
	public List<String> getFoodIds(Foodorder query);
	
	/**
	 *下单，保存订单与食品的一对多信息
	 * @param query
	 * @author yangbilin
	 */
	public void insertOrders(Foodorder query);
	/**
	 *删除或取消订单与食品的一对多信息
	 * @param query
	 * @author yangbilin
	 */
	public void deleteOrders(Foodorder query);
}
