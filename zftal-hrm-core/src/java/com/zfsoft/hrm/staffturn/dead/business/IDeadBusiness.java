package com.zfsoft.hrm.staffturn.dead.business;

import java.util.List;

import com.zfsoft.hrm.staffturn.retire.entities.RetireInfo;
/**
 * 离世 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-8-7
 * @version V1.0.0
 */
public interface IDeadBusiness {
	/**
	 * 获取预退休人员 通过部门编码和姓名
	 * @param deptCode
	 * @param name
	 * @return
	 */
	public List<RetireInfo> getPreRetireInfoList(String deptCode,String name);
	
	/**
	 * 增加或修改预退休人员
	 * @param retireInfo
	 */
	public void saveRetire(RetireInfo retireInfo);
	
	/**
	 * 移除预退休人员
	 * @param userId
	 */
	public void removeRetire(String userId);
	
	/**
	 * 将与退休人员转为退休人员
	 * @param userId
	 */
	public void dealPreRetireToRetire(RetireInfo retireInfo);
	
	/**
	 * 根据退休部门，退休职务，退休时间，生日，性别查询退休信息
	 * @param retireInfo
	 * @param bothday
	 * @param sex
	 * @return
	 */
	public List<RetireInfo> getRetireInfoList(RetireInfo retireInfo, 
			String bothday ,String sex);
}
