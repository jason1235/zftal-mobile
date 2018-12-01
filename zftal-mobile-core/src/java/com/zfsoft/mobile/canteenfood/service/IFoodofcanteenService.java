package com.zfsoft.mobile.canteenfood.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.canteenfood.entity.Foodofcanteen;

public interface IFoodofcanteenService {

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
	 * 根据订单orderid获得购买的食品列表信息
	 * @param params
	 * @return List<Foodofcanteen>
	 * @author yangbilin
	 */
	public List<Foodofcanteen>  getFoodsListFororder(Map<String,Object> params);
	
	/**
	 * 根据食品编号foodid食品信息
	 * @param foodId
	 * @return Foodofcanteen
	 * @author yangbilin
	 */
	public Foodofcanteen findById(String foodId);
	
	/**
	 * 更新食品库存
	 * @param query
	 * @return int
	 * @author yangbilin
	 */
	public int updateStorage(Foodofcanteen query);
	
	/**
	 *商家食品列表--分页
	 * @param query
	 *@return PageList<Foodofcanteen>
	 */
	public PageList<Foodofcanteen> getPageList(Foodofcanteen query);
	
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
