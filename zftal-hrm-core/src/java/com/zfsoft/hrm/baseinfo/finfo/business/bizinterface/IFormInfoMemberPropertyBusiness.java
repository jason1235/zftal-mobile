package com.zfsoft.hrm.baseinfo.finfo.business.bizinterface;

import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMember;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMemberProperty;
import com.zfsoft.hrm.baseinfo.finfo.exception.FormInfoException;
import com.zfsoft.hrm.baseinfo.finfo.query.FormInfoMemberPropertyQuery;

/**
 * {@link FormInfoMemberProperty}Business的操作
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-22
 * @version V1.0.0
 */
public interface IFormInfoMemberPropertyBusiness {
	
	/**
	 * 获取符合条件的组成成员属性集合
	 * @param query 查询条件
	 * @return
	 * @throws FormInfoException 如果操作出现异常
	 */
	public FormInfoMemberProperty[] getMemberProperties( FormInfoMemberPropertyQuery query ) throws FormInfoException;
	
	/**
	 * 获取指定组成成员的属性集合
	 * @param member 组成成员
	 * @return
	 * @throws FormInfoException 如果操作出现异常
	 */
	public FormInfoMemberProperty[] getMemberProperties( FormInfoMember member ) throws FormInfoException;
	
	/**
	 * 获取指定的成员属性信息
	 * @param property 成员属性
	 * @return
	 * @throws FormInfoException 如果操作出现异常
	 */
	public FormInfoMemberProperty getMemberProperty( FormInfoMemberProperty property ) throws FormInfoException;
	
	/**
	 * 判断指定的成员属性是否存在
	 * @param property 成员属性
	 * @return true:存在；false:不存在
	 * @throws FormInfoException 如果操作出现异常
	 */
	public boolean exist( FormInfoMemberProperty property ) throws FormInfoException;
	
	/**
	 * 增加组成成员属性信息
	 * @param property 增加的组成成员属性
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void addProperty( FormInfoMemberProperty property ) throws FormInfoException;
	
	/**
	 * 批量增加组成成员属性信息
	 * @param properties 增加的组成成员属性
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void batchAddProperties( FormInfoMemberProperty[] properties ) throws FormInfoException;

	/**
	 * 重置指定组成成员下的所有成员属性信息
	 * <p>
	 * 对指定组成成员的属性进行重置<br>
	 * 重置时新的组成成员属性不采用现有或参数中的组成成员属性，
	 * 而是重新根据组成成员所使用的信息类进行计算
	 * </p>
	 * @param member 组成成员
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void resetPeoperties( FormInfoMember member ) throws FormInfoException;

	/**
	 * 修改组成成员属性信息
	 * @param property 修改的组成成员属性
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void modifyProperty( FormInfoMemberProperty property ) throws FormInfoException;
	
	/**
	 * 删除指定组成成员下的所有成员属性
	 * @param member 组成成员
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void removeProperty( FormInfoMember member ) throws FormInfoException;
	
	/**
	 * 删除指定的成员属性
	 * @param property 组成成员属性
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void removeProperty( FormInfoMemberProperty property ) throws FormInfoException;
	
	/**
	 * 修改组成成员属性的显示状态
	 * @param property 修改的组成成员属性
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void modifyViewable( FormInfoMemberProperty property ) throws FormInfoException;

	/**
	 * 修改成员属性的的索引
	 * @param property 成员属性
	 * @throws FormInfoException 如果操作出现异常
	 */
	public void modifyIndex( FormInfoMemberProperty property ) throws FormInfoException;
}
