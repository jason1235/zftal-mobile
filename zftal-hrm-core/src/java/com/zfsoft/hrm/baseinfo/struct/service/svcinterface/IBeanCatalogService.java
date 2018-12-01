package com.zfsoft.hrm.baseinfo.struct.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.struct.entities.BeanCatalog;
import com.zfsoft.hrm.baseinfo.struct.exception.StructException;

/**
 * 信息类目录业务处理接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-3
 * @version V1.0.0
 */
public interface IBeanCatalogService {
	
	/**
	 * 获取所有的信息类目录列表
	 * @return 信息类目录列表
	 * @throws StructException 如果操作出现异常
	 */
	public List<BeanCatalog> getCatalogs() throws StructException;
	
	/**
	 * 获取指定的信息类目录
	 * @param guid 信息类目录ID
	 * @return 指定的信息类目录
	 * @throws StructException 如果操作出现异常
	 */
	public BeanCatalog getCatalogById( String guid ) throws StructException;

	/**
	 * 增加信息类目录
	 * @param catalog 信息类目录
	 * @throws StructException 如果操作出现异常
	 */
	public void add( BeanCatalog catalog ) throws StructException;
	
	/**
	 * 
	 * @param catalog
	 * @throws StructException
	 */
	public void modify( BeanCatalog catalog ) throws StructException;
	
	/**
	 * 
	 * @param guid
	 * @throws StructException
	 */
	public void removeById( String guid ) throws StructException;
}
