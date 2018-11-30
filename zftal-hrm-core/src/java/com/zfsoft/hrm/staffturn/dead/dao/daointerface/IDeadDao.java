package com.zfsoft.hrm.staffturn.dead.dao.daointerface;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.staffturn.dead.entities.DeadInfo;
import com.zfsoft.hrm.staffturn.dead.query.DeadInfoQuery;

/**
 * 离世
 * @author 沈鲁威 Patrick Shen
 * @since 2012-7-31
 * @version V1.0.0
 */
public interface IDeadDao {
	/**
	 * 插入
	 * @param DeadDao
	 */
	public void insert(DeadInfo deadInfo);
	/**
	 * 更新
	 * @param DeadDao
	 */
	public void update(DeadInfo deadInfo);
	/**
	 * 删除
	 * @param userId
	 */
	public void delete(DeadInfoQuery query);
	/**
	 * 查找
	 * @param query
	 */
	public PageList<DeadInfo> getPagedQuery(DeadInfoQuery query);
	/**
	 * 查找
	 * @param query
	 */
	public DeadInfo findById(DeadInfoQuery query);
}
