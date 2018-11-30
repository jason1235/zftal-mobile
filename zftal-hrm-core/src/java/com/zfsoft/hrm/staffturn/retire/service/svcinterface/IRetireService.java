package com.zfsoft.hrm.staffturn.retire.service.svcinterface;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.staffturn.retire.entities.RetireInfo;
import com.zfsoft.hrm.staffturn.retire.query.RetireInfoQuery;

/**
 * 退休服务
 * @author  沈鲁威 Patrick Shen
 * @since  2012-7-24
 * @version  V1.0.0
 */
public interface IRetireService {
	/**
	 * 获取预退休人员 通过部门编码和姓名
	 * @param rowBounds 
	 * @param deptCode
	 * @param name
	 * @return
	 */
	public PageList<RetireInfo> getPreRetireInfoList(RetireInfoQuery query);
	
	/**
	 * 增加或修改预退休人员
	 * @param retireInfo
	 * @param type add增加 modify修改
	 */
	public void saveRetire(RetireInfo retireInfo,String type);
	
	/**
	 * 移除预退休人员
	 * @param userId
	 */
	public void removeRetire(RetireInfo retireInfo);
	/**
	 *撤销预退休人员 add by heyc on 20130806
	 * @param retireInfo
	 */
	public void cancelPreRetire(RetireInfo retireInfo);
	
	/**
	 * 将与退休人员转为退休人员
	 * @param userId
	 */
	public void modifyPreRetireToRetire(RetireInfo retireInfo);
	
	/**
	 * 根据退休部门，退休职务，退休时间，生日，性别查询退休信息
	 * @param query
	 * @return
	 */
	public PageList<RetireInfo> getRetireInfoList(RetireInfoQuery query);
	/**
	 * 获取预退休人员通过工号
	 * @param query
	 * @return
	 */
	public RetireInfo getPreRetireByUserId(String userId);
	/**
	 * 获取退休人员通过工号
	 * @param userId 
	 * @return
	 */
	public RetireInfo getRetireInfoByUserId(String userId) ;
	/**
	 * 根据工号，获取退休人员个数
	 * @param userId
	 * @return
	 */
	public int getCountByUserId(String userId);
	/**
	 * 获取预退休人员
	 * @param query
	 * @return
	 */
	public List<RetireInfo> getPreList(RetireInfoQuery query);
	/**
	 * 修改
	 * @param query
	 * @return
	 */
	public void modify(RetireInfo retireInfo);
	
	/**
	 * 批量修改退休文号
	 * @param query
	 * @return
	 */
	public void doPlxgtxwh(List<RetireInfo> list);

	// 20140425 add start
	/**
	 * 发送通知
	 * @param retireInfo
	 */
	public void saveMessage(RetireInfo retireInfo);
	
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
	/**
	 * 批量修改退休文号(退休人员管理中的修改文号，只修改文号，不做其他操作)
	 * @param query
	 * @return
	 */
	public void doPlxgtxwh2(RetireInfo retireInfo);
}
