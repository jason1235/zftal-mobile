package com.zfsoft.hrm.baseinfo.finfo.service.svcinterface;

import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMember;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMemberProperty;
import com.zfsoft.hrm.baseinfo.finfo.exception.FormInfoException;

/**
 * {@link FormInfoMemberProperty}Service层业务操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-27
 * @version V1.0.0
 */
public interface IFormInfoMemberPropertyService {

	/**
	 * 获取指定组成成员的属性集合
	 * @param member 组成成员
	 * @return
	 * @throws FormInfoException 如果操作出现异常
	 */
	public FormInfoMemberProperty[] getMemberProperties( FormInfoMember member ) throws FormInfoException;
	
	/**
	 * 获取指定组成成员的可编辑属性集合
	 * @param member 组成成员
	 * @return
	 * @throws FormInfoException 如果操作出现异常
	 */
	public FormInfoMemberProperty[] getEditMemberProperties( FormInfoMember member ) throws FormInfoException;
	
	/**
	 * 获取指定组成成员的可显示属性集合
	 * @param member 组成成员
	 * @return
	 * @throws FormInfoException 如果操作出现异常
	 */
	public FormInfoMemberProperty[] getViewMemberProperties( FormInfoMember member ) throws FormInfoException;
	
	/**
	 * 获取指定成员属性信息
	 * @param property 成员属性信息
	 * @return
	 * @throws FormInfoException 如果操作出现异常
	 */
	public FormInfoMemberProperty getMemberProperty( FormInfoMemberProperty property ) throws FormInfoException;
	
	/**
	 * 保存组成成员信息
	 * @param property 保存的组成成员属性
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void save( FormInfoMemberProperty property ) throws FormInfoException;
	
	/**
	 * 修改显示状态
	 * @param property
	 * @throws FormInfoException
	 */
	public void modifyViewable( FormInfoMemberProperty property ) throws FormInfoException;
	
	/**
	 * 修改显示索引
	 * <p>
	 * 对指定组成成员下的2个成员属性的显示索引进行互换
	 * </p>
	 * @param member 组成成员
	 * @param pNames 成员属性集合（Array[2]）
	 * @throws FormInfoException
	 */
	public void modifyIndex( FormInfoMember member, String[] pNames) throws FormInfoException;
	/**
	 * 修改显示索引
	 * <p>
	 * 对指定组成成员下的2个成员属性的显示索引进行互换
	 * </p>
	 * @param member 组成成员
	 * @param pNames 成员属性集合（Array[2]）
	 * @throws FormInfoException
	 */
	public void saveProList(FormInfoMember member, String[] pNames) throws FormInfoException;
}
