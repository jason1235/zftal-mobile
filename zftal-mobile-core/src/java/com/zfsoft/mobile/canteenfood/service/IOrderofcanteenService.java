package com.zfsoft.mobile.canteenfood.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.canteenfood.entity.Orderofcanteen;

public interface IOrderofcanteenService {
	
	/**
	 *根据用户userid获取该用户的所有订单列表--分页
	 *@param query
	 *@return PageList<Orderofcanteen>
	 *@author yangbilin 
	 */
	public PageList<Orderofcanteen> getPageList(Orderofcanteen query);
	
	/**
	 *下单
	 *@param query
	 *@return int
	 *@author yangbilin 
	 */
	public int placeOrder(Orderofcanteen query);
	
	/**
	 *根据订单编号orderid获取订单信息
	 *@param orderId
	 *@return Orderofcanteen
	 *@author yangbilin 
	 */
	public Orderofcanteen findById(String orderId);
	/**
	 * 更新状态
	 * @param query
	 * @return int
	 */
	public int control(Orderofcanteen query);
}
