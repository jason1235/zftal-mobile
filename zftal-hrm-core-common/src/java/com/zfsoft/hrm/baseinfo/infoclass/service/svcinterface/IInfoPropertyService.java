package com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.infoclass.exception.InfoClassException;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyQuery;

/**
 * 信息类属性业务操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-21
 * @version V1.0.0
 */
public interface IInfoPropertyService {
	
	/**
	 * 获取符合条件的信息类属性列表
	 * @param 查询条件
	 * @return
	 * @throws InfoClassException 如果操作出现异常
	 */
	public List<InfoProperty> getInfoProperties(InfoPropertyQuery query) throws InfoClassException;
	
	/**
	 * 获取信息类属性
	 * @param guid 信息类属性ID
	 * @return
	 * @throws InfoClassException 如果操作出现异常
	 */
	public InfoProperty getProperty(String guid) throws InfoClassException; 
	
	/**
	 * 增加信息类属性
	 * @param entity 信息类属性
	 * @throws InfoClassException 如果操作出现异常
	 */
	public void add(InfoProperty entity) throws InfoClassException;
	
	/**
	 * 修改信息类属性
	 * @param entity 信息类属性
	 * @throws InfoClassException 如果操作出现异常
	 */
	public void modify(InfoProperty entity) throws InfoClassException;
	
	/**
	 * 修改信息类同步属性
	 * @param entity
	 */
	public void updateSync(InfoProperty entity);
	/**
	 * 删除信息类属性
	 * @param guid 信息类属性ID
	 * @throws InfoClassException 如果操作出现异常
	 */
	public void remove(String guid) throws InfoClassException;
	
	/**
	 * 删除指定信息类的所有属性
	 * @param classId 信息类ID
	 * @throws InfoClassException 如果操作出现异常
	 */
	public void removeClassProperties(String classId) throws InfoClassException;
	
	/**
	 * 交换信息类属性的索引
	 * @param guids 互相交换的信息类属性ID(length = 2)
	 * @throws InfoClassException 如果操作出现异常
	 */
	public void swapIndex(String[] guids) throws InfoClassException;
	
	public void syncFieldValue(String cId,String pId) throws InfoClassException;
	
}
