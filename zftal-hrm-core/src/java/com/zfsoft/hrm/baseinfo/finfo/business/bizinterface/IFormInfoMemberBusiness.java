package com.zfsoft.hrm.baseinfo.finfo.business.bizinterface;

import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMember;
import com.zfsoft.hrm.baseinfo.finfo.exception.FormInfoException;

/**
 * {@link FormInfoMember}Business层业务操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-23
 * @version V1.0.0
 */
public interface IFormInfoMemberBusiness {

	/**
	 * 获取成组员的组成成员列表
	 * @param name 成员组组名
	 * @return
	 * @throws FormInfoException 如果操作出现异常
	 */
	public FormInfoMember[] getMembers( String name ,Boolean batch) throws FormInfoException;
	
	/**
	 * 获取组成成员信息
	 * <p>
	 * 如果无法获取组成成员信息则返回null
	 * </p>
	 * @param name 成员组组名
	 * @param classId 组成成员所使用信息类ID
	 * @return
	 * @throws FormInfoException 如果操作出现异常
	 */
	public FormInfoMember getMember( String name, String classId ,Boolean batch) throws FormInfoException;
	
	/**
	 * 判断组成成员是否存在
	 * @param member 组成成员
	 * @return true:存在		false:不存在
	 * @throws FormInfoException 如果操作出现异常
	 */
	public boolean exist( FormInfoMember member ) throws FormInfoException;
	
	/**
	 * 增加组成成员信息
	 * @param member 增加的组成成员信息
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void addMember( FormInfoMember member ) throws FormInfoException;
	
	/**
	 * 修改组成成员信息
	 * @param member 修改的组成成员信息
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void modifyMember( FormInfoMember member ) throws FormInfoException;
	
	/**
	 * 开发/关闭组成成员
	 * @param member 组成成员信息
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void openMember( FormInfoMember member ) throws FormInfoException;
	
	/**
	 * 更新组成成员序号
	 * @param member 组成成员
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void modifyMemberIndex( FormInfoMember member ) throws FormInfoException;
	
	/**
	 * 删除组成成员信息
	 * @param member 删除的组成成员
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void removeMember( FormInfoMember member ) throws FormInfoException;
}
