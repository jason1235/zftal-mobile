package com.zfsoft.mobile.canteenfood.dao;


import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.canteenfood.entity.Orderofcanteen;

/**
 *食品订单dao接口类
 *@author yangbilin
 *@createtime 2017-07-18 15:18
 */
public interface IOrderofcanteenDao {
	
	/**
	 *订单列表数量
	 *@param query
	 *@return int
	 */
	public int getPageCount(Orderofcanteen query);
	
	/**
	 *订单列表--分页
	 * @param query
	 *@return PageList<Foodorder>
	 */
	public PageList<Orderofcanteen> getPageList(Orderofcanteen query);
	
	/**
	 * 根据id获取订单详情
	 * @param map
	 * @return Orderofcanteen
	 */
	public Orderofcanteen findById(String orderId);
	
	/**
	 *下单 
	 */
	public int placeOrder(Orderofcanteen query);
	
	/**
	 * 更新状态
	 * @param query
	 * @return int
	 */
	public int control(Orderofcanteen query);

}
