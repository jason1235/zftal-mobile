package com.zfsoft.mobile.canteenfood.dao;


import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.canteenfood.entity.Canteen;

/**
 *商家dao接口类
 *@author yangbilin
 *@createtime 2017-07-18 11:55
 */
public interface ICanteenDao {
	
	/**
	 *商家列表数量
	 *@param query
	 *@return int
	 */
	public int getPageCount(Canteen query);
	
	/**
	 *商家列表--分页
	 * @param query
	 *@return PageList<Canteen>
	 */
	public PageList<Canteen> getPageList(Canteen query);
	public PageList<Canteen> getPageList2(Canteen query);
	
	/**
	 *商家名称列表--不分页
	 * @param query
	 *@return List<Canteen>
	 */
	public List<Canteen> getCanteennameList(Map<String,Object> params);
 
	/**
	 *根据id获取商家信息
	 *@param canteeId
	 *@return Canteen
	 */
	public Canteen findById(String canteenId);
	
	/**
	 *增加商家信息
	 *@param query
	 *@return int 
	 */
	public int insertCanteen(Canteen query);
	
	/**
	 *更新商家
	 *@param query
	 *@return int 
	 */
	public int updateCanteen(Canteen query);
	
	/**
	 *删除商家信息
	 *@param query
	 *@return int 
	 */
	public int deleteCanteen(Canteen query);
	
	/**
	 *删除商家信息
	 *@param canteeId
	 *@return int 
	 */
	public int deleteCanteenById(String canteeId);
	
	/**
	 * 更新状态
	 * @param query
	 * @return int
	 */
	public int control(Canteen query);
	
//	/**
//	 *根据经纬度计算距离 
//	 * @param map
//	 * @return String
//	 */
//	public double getDistance(Map<String,Double> map);

}
