package com.zfsoft.mobile.canteenfood.dao;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.canteenfood.entity.Canteenaddress;


/**
 *用户地址dao接口类
 *@author yangbilin
 *@createtime 2017-07-18 17:27
 */
public interface ICanteenaddressDao {
	
	/**
	 *地址列表数量
	 *@param query
	 *@return int
	 */
	public int getPageCount(Canteenaddress query);
	/**
	 *地址列表--分页
	 * @param query
	 *@return PageList<Canteenaddress>
	 */
	public PageList<Canteenaddress> getPageList(Canteenaddress query);
	
	/**
	 *根据id获取地址具体信息
	 *@param addressId
	 *@return Canteenaddress
	 */
	public Canteenaddress findById(String addressId);
	/**
	 *增加地址具体信息
	 *@param query
	 *@return int 
	 */
	public int insertAddress(Canteenaddress query);
	
	/**
	 *更新地址具体信息
	 *@param query
	 *@return int 
	 */
	public int updateAddress(Canteenaddress query);
	
	/**
	 *删除地址具体信息
	 *@param query
	 *@return int 
	 */
	public int deleteAddress(Canteenaddress query);
	

}
