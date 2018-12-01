package com.zfsoft.hrm.staffturn.dead.service.svcinterface;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.staffturn.dead.entities.DeadInfo;
import com.zfsoft.hrm.staffturn.dead.query.DeadInfoQuery;

/**
 * 离世
 * @author  沈鲁威 Patrick Shen
 * @since  2012-7-24
 * @version  V1.0.0
 */
public interface IDeadService {
	/**
	 * 获取死亡人员
	 * @param deptCode
	 * @param name
	 * @return
	 */
	public PageList<DeadInfo> getDeadInfoList(DeadInfoQuery query);
	
	/**
	 * 增加或修改预退休人员
	 * @param retireInfo
	 * @param type add增加 modify修改
	 */
	public void saveDeadInfo(DeadInfo deadInfo,String type);
	
	/**
	 * 移除预退休人员
	 * @param userId
	 */
	public void removeDeadInfo(DeadInfoQuery query);
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public DeadInfo getDeadInfoByUserId(String userId);
}
