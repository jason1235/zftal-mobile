package com.zfsoft.hrm.pendingAffair.service.svcinterface;

import java.util.List;

import com.zfsoft.common.log.User;
import com.zfsoft.hrm.pendingAffair.entities.PendingAffairInfo;

public interface IPendingAffairService {
	
	/**
	 * 获得待办事宜列表
	 * @param user
	 * @return
	 */
	public List<PendingAffairInfo> getListByUser(User user);
	/**
	 * 通过角色获取
	 * @param roleIds
	 * @return
	 */
	public List<PendingAffairInfo> getListByRoles(User user);
	
	/**
	 * 获得待办事宜列表
	 * @param query
	 * @return
	 */
	public List<PendingAffairInfo> getListByQuery(PendingAffairInfo query);
	
	/**
	 * 获得待办事宜列表
	 * @param id
	 * @return
	 */
	public PendingAffairInfo getById(String id);
	
	/**
	 * 新增代表事宜
	 * @param pendingAffairInfo
	 */
	public void addPendingAffairInfo(PendingAffairInfo pendingAffairInfo);
	
	/**
	 * 修改代表事宜
	 * @param pendingAffairInfo
	 */
	public void modifyPendingAffairInfo(PendingAffairInfo pendingAffairInfo);
	
	/**
	 * 通过业务ID修改代表事宜
	 * @param pendingAffairInfo
	 */
	public void modifyByYwId(PendingAffairInfo pendingAffairInfo);
	
	/**
	 * 删除代表事宜
	 * @param pendingAffairInfo
	 */
	public void removePendingAffairInfo(String id);
	
	/**
	 * 	通过业务ID删除代表事宜
	 * @param pendingAffairInfo
	 */
	public void removePendingAffairInfoByBid(String bid);
}
