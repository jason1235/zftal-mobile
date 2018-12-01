package com.zfsoft.hrm.baseinfo.finfo.service.svcinterface;

import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMember;
import com.zfsoft.hrm.baseinfo.finfo.exception.FormInfoException;

/**
 * {@link FormInfoMember}Service层业务操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-22
 * @version V1.0.0
 */
public interface IFormInfoMemberService {
	
	/**
	 * 获取指定信息维护描述信息的组成成员
	 * @param name 信息维护描述信息的名字
	 * @return
	 * @throws FormInfoException 如果操作出现异常
	 */
	public FormInfoMember[] getMembers( String name ,Boolean batch) throws FormInfoException;
	
	/**
	 * 获取指定的组成成员描述信息
	 * @param name 信息维护描述信息的名字
	 * @param classId 组成成员所使用的数据类的信息类ID
	 * @return
	 * @throws FInfoException如果操作出现异常
	 */
	public FormInfoMember getMember( String name, String classId ,Boolean batch) throws FormInfoException;
	
	
	/**
	 * 保存组成成员描述信息
	 * <p>
	 * 增加或修改组成成员信息，如果组成成员信息不经存在则做“增加”，否则做“ 修改”
	 * </p>
	 * @param member 组成成员描述信息
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void saveMember( FormInfoMember member ) throws FormInfoException;
	
	/**
	 * 修改组成成员信息开放状态
	 * <p>
	 * 如果修改的组成成员不存在，将会自动完成增加操作
	 * </p>
	 * @param member 组成成员信息
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void modifyMemberOpen( FormInfoMember member ) throws FormInfoException;

	/**
	 * 修改交换组成成员显示索引
	 * @param name 组成成员组名
	 * @param classIds 组成成员使用信息类ID集合
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void modifySwapMemberIndex(String name, String[] classIds,Boolean batch) throws FormInfoException;
	
}
