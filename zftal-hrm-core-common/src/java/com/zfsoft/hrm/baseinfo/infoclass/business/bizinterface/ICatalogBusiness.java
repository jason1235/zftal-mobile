package com.zfsoft.hrm.baseinfo.infoclass.business.bizinterface;

import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.exception.InfoClassException;
import com.zfsoft.hrm.config.type.InfoCatalogType;

/**
 * 信息类目录业务操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-6
 * @version V1.0.0
 */
public interface ICatalogBusiness {
	
	/**
	 * 获取信息类目录信息
	 * @param guid 信息类目录ID
	 * @return 指定ID的信息类目录描述信息
	 * @throws InfoClassException 如果操作出现异常
	 */
	public Catalog getCatalogById( String guid ) throws InfoClassException;
	
	/**
	 * 获取信息类目录类型描述信息
	 * @param guid 信息类目录ID
	 * @return 指定ID的信息类目录的类型描述信息
	 * @throws InfoClassException 如果操作出现异常
	 */
	public InfoCatalogType getCatalogTypeInfo( String guid ) throws InfoClassException;

//	/**
//	 * 获取目录信息
//	 * @param guid 目录的全局ID
//	 * @return 指定的目录信息
//	 * @throws InfoClassException 如果操作出现异常
//	 */
//	public Catalog getEntity(String guid) throws InfoClassException;
//	
//	/**
//	 * 增加目录信息
//	 * @param entity 增加的目录信息
//	 * @throws InfoClassException 如果操作出现异常
//	 */
//	public void add(Catalog entity) throws InfoClassException;
//	
//	/**
//	 * 修改目录信息
//	 * @param entity 修改后的目录信息
//	 * @throws InfoClassException 如果操作出现异常
//	 */
//	public void modify(Catalog entity) throws InfoClassException;
//	
//	/**
//	 * 删除制定的目录信息
//	 * @param guid 目录的全局ID
//	 * @throws InfoClassException 如果操作出现异常
//	 */
//	public void remove(String guid) throws InfoClassException;

}
