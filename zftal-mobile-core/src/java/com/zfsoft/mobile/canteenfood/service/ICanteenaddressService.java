package com.zfsoft.mobile.canteenfood.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.canteenfood.entity.Canteenaddress;

/**
 *个人送餐地址service接口类 
 * @author yangbilin
 * @createtime 2017-07-18 11:00
 */
public interface ICanteenaddressService {
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
