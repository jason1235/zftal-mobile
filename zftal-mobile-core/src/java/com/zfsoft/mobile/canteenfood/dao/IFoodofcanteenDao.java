package com.zfsoft.mobile.canteenfood.dao;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.canteenfood.entity.Foodofcanteen;

/**
 *商家食品dao接口类
 *@author yangbilin
 *@createtime 2017-07-18 14:58
 */
public interface IFoodofcanteenDao {
	
	/**
	 *商家食品列表数量
	 *@param query
	 *@return int
	 */
	public int getPageCount(Foodofcanteen query);
	/**
	 *商家食品列表--分页
	 * @param query
	 *@return PageList<Foodofcanteen>
	 */
	public PageList<Foodofcanteen> getPageList(Foodofcanteen query);
	
	/**
	 *根据订单编号orderid获取该订单下的食品信息
	 *@param params
	 *@return  List<Foodofcanteen>
	 *@author yangbilin
	 */
	public List<Foodofcanteen>  getFoodsListFororder(Map<String,Object> params);
	/**
	 *根据食品类别id获取食品列表
	 *@param params
	 *@return  List<Foodofcanteen>
	 *@author yangbilin
	 */
	public List<Foodofcanteen> getFoodsList(Map<String,Object> params);
	
	/**
	 *根据食品类别遍历获取食品启用数量
	 *@param params
	 *@return  List<Foodofcanteen>
	 *@author yangbilin
	 */
	public int getFoodsListCount(Map<String,Object> params);
	
	/**
	 *根据id获取食品信息
	 *@param foodId
	 *@return Foodofcanteen
	 */
	public Foodofcanteen findById(String foodId);
	/**
	 *增加商家食品信息
	 *@param query
	 *@return int 
	 */
	public int insertFood(Foodofcanteen query);
	/**
	 *更新商家食品
	 *@param query
	 *@return int 
	 */
	public int updateFood(Foodofcanteen query);
	
	/**
	 *更新商家食品数量
	 *@param query
	 *@return int 
	 */
	public int updateStorage(Foodofcanteen query);
	
	/**
	 *删除商家食品
	 *@param query
	 *@return int 
	 */
	public int deleteFood(Foodofcanteen query);
	/**
	 *删除商家食品
	 *@param foodId
	 *@return int 
	 */
	public int deleteFoodById(String foodId);
	
	/**
	 * 更新状态
	 * @param query
	 * @return int
	 */
	public int control(Foodofcanteen query);
	

}
