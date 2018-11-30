package com.zfsoft.hrm.staffturn.retire.dao.daointerface;

import java.util.List;

import com.zfsoft.hrm.staffturn.retire.entities.RetireInfo;
import com.zfsoft.hrm.staffturn.retire.query.RetireInfoQuery;

/**
 * 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-7-31
 * @version V1.0.0
 */
public interface IRetireDao {
	/**
	 * 插入
	 * @param retireInfo
	 */
	public void insert(RetireInfo retireInfo);
	/**
	 * 更新
	 * @param retireInfo
	 */
	public void update(RetireInfo retireInfo);
	/**
	 * 删除
	 * @param userId
	 */
	public void delete(String userId);
	/**
	 * 查找
	 * @param query
	 */
	public List<RetireInfo> findByQuery(RetireInfoQuery query);
	/**
	 * 查找 
	 * @param query
	 */
	public int getCountQuery(RetireInfoQuery query);
    /**
     *  delete preretire info add  by heyc on 20130806
     * @param retireInfo 信息
     * @return result int
     */
	
	public int delete(RetireInfo retireInfo);
	/**
	 * 批量修改退休文号(退休人员管理中的修改文号，只修改文号，不做其他操作)
	 * @param: 
	 * @return:
	 */
	public void updateXgwh(RetireInfo retireInfo);
	// 20140425 add start
	/**
	 * 退休通知人
	 */
	public String getReceiver();
	
	public int getReceiverCount();
	
	/**
	 * 插入
	 * @param retireInfo
	 */
	public void insertReceiver(RetireInfo retireInfo);
	/**
	 * 更新
	 * @param retireInfo
	 */
	public void updateReceiver(RetireInfo retireInfo);
	// 20140425 add end
}
