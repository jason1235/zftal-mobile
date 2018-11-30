package com.zfsoft.mobile.canteenfood.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.canteenfood.entity.Foodcataofcanteen;

public interface IFoodcataofcanteenService {
	
	/**
	 *商家食品种类列表--分页
	 * @param query
	 *@return PageList<Foodcataofcanteen>
	 */
	public PageList<Foodcataofcanteen> getPageList(Foodcataofcanteen query);

	/**
	 *根据食品类别id获取该类别的信息
	 * @param foodcataId
	 * @return Foodcataofcanteen
	 */
	public Foodcataofcanteen findById(String foodcataId);
	/**
	 *根据id获取商家食品种类信息
	 *@param foodcataId
	 *@return Foodcataofcanteen
	 */
	public List<Foodcataofcanteen>  getFoodcataList(Map<String, Object> map);
	
	public List<String>  getFoodcataIdList(Map<String, Object> map);
	/**
	 *增加商家食品种类信息
	 *@param query
	 *@return int 
	 */
	public int insertFoodcata(Foodcataofcanteen query);
	
	/**
	 *更新商家食品种类
	 *@param query
	 *@return int 
	 */
	public int updateFoodcata(Foodcataofcanteen query);
	
	/**
	 *删除商家食品种类
	 *@param query
	 *@return int 
	 */
	public int deleteFoodcata(Foodcataofcanteen query);
	
	/**
	 * 更新状态
	 * @param query
	 * @return int
	 */
	public int control(Foodcataofcanteen query);
}
