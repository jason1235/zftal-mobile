package com.zfsoft.mobile.webcasts.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.webcasts.entity.WebcastsAuditEntity;

/**
 *直播间申请service接口 IWebcastsAuditService
 *@author yangbilin
 *@date 2017-09-11
 */
public interface IWebcastsAuditService {

	/**
	 *获取直播间申请列表
	 *@param query
	 *@return PageList<WebcastsAuditEntity>
	 */
	public PageList<WebcastsAuditEntity> getPageList(WebcastsAuditEntity query);
	/**
	 *申请开通直播间权限
	 *@param query
	 */
	public void application(WebcastsAuditEntity query);
	/**
	 *审核用户申请
	 *@param query
	 */
	public void audit(WebcastsAuditEntity query);
	/**
	 *删除
	 *@param appid
	 */
	public void delete(String appid);
	/**
	 *根据当前登录用户获取其直播间申请信息
	 *@param userid
	 *@return WebcastsAuditEntity
	 */
	public WebcastsAuditEntity getResultByUserid(String userid);
	/**
	 *根据申请直播间id获取申请信息
	 *@param query
	 *@return WebcastsAuditEntity
	 */
	public WebcastsAuditEntity findById(WebcastsAuditEntity query);
}
