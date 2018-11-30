package com.zfsoft.mobile.webcasts.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.webcasts.entity.WebcastsEntity;

/**
 *直播间service接口 IWebcastsService
 *@author yangbilin
 *@date 2017-09-11
 */
public interface IWebcastsService {
	
	/**
	 *获取直播列表
	 *@param query
	 *@return PageList<WebcastsEntity>  
	 */
	public PageList<WebcastsEntity> getPageList(WebcastsEntity query);
	
	/**
	 *根据id获取直播信息
	 *@param query
	 *@return WebcastsEntity
	 */
	public WebcastsEntity findById(String webcastId);
	/**
	 * 根据用户id获取直播间信息
	 * @param userid
	 * @return WebcastsEntity
	 */
	public WebcastsEntity getWebcastsByUserid(String userid);
	
	/**
	 *增加直播信息
	 *@param query
	 */
	public void insert(WebcastsEntity query);
	
	/**
	 *更新直播信息
	 *@param query
	 */
	public int update(WebcastsEntity query);
	
	/**
	 *更新直播状态  0创建  1直播中  2结束
	 *@param query
	 */
	public void updateStatus(WebcastsEntity query);
	
	/**
	 *修改直播点击量
	 *@param query
	 */
	public void updateDropNum(WebcastsEntity query);
	
	/**
	 *更新直播间状态  0禁用  1启用
	 *@param query
	 *@return int
	 */
	public int webcastsControl(WebcastsEntity query);
	
	/**
	 *删除直播间信息
	 *@param webcastId
	 */
	public void deleteWebcasts(String webcastId);

}
