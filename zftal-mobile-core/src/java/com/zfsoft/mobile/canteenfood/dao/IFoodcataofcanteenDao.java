package com.zfsoft.mobile.canteenfood.dao;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.canteenfood.entity.Foodcataofcanteen;

/**
 *商家食品种类dao接口类
 *@author yangbilin
 *@createtime 2017-07-18 14:34
 */
public interface IFoodcataofcanteenDao {
	
	/**
	 *商家食品种类列表数量
	 *@param query
	 *@return int
	 */
	public int getPageCount(Foodcataofcanteen query);
	
	/**
	 *商家食品种类列表--分页
	 * @param query
	 *@return PageList<Foodcataofcanteen>
	 */
	public PageList<Foodcataofcanteen> getPageList(Foodcataofcanteen query);
	
	/**
	 *根据id获取商家食品种类信息
	 *@param foodcataId
	 *@return Foodcataofcanteen
	 */
	public Foodcataofcanteen findById(String foodcataId);
	
	/**
	 *获取该商家下所有的食品类别
	 * @param map
	 * @return int
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
	 *删除商家食品种类
	 *@param foodcataId
	 *@return int 
	 */
	public int deleteFoodcataById(String foodcataId);
	
	/**
	 * 更新状态
	 * @param query
	 * @return int
	 */
	public int control(Foodcataofcanteen query);

}
