package com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.infoclass.exception.InfoClassException;
import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.query.CatalogQuery;

/**
 * 信息类目录业务操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-18
 * @version V1.0.0
 */
public interface ICatalogService {

	/**
	 * 获取目录列表
	 * @param query 查询条件 null表示无任何查询条件
	 * @return 所有的信息类目录列表
	 * @throws RuntimeException 如果操作出现异常
	 */
	public List<Catalog> getList(CatalogQuery query) throws InfoClassException;
	
	/**
	 * 获取完整的目录列表
	 * <p>
	 * 各目录列表必须包含该目录下所有的信息类列表
	 * </p>
	 * @param query 查询条件 null表示无任何查询条件
	 * @return
	 * @throws RuntimeException 如果操作出现异常
	 */
	public List<Catalog> getFullList(CatalogQuery query) throws InfoClassException;
	
	/**
	 * 获取目录信息
	 * @param guid 目录的全局ID
	 * @return 指定的目录信息
	 * @throws InfoClassException 如果操作出现异常
	 */
	public Catalog getEntity(String guid) throws InfoClassException;
	
	/**
	 * 增加目录信息
	 * @param entity 增加的目录信息
	 * @throws InfoClassException 如果操作出现异常
	 */
	public void add(Catalog entity) throws InfoClassException;
	
	/**
	 * 修改目录信息
	 * @param entity 修改后的目录信息
	 * @throws InfoClassException 如果操作出现异常
	 */
	public void modify(Catalog entity) throws InfoClassException;
	
	/**
	 * 删除制定的目录信息
	 * @param guid 目录的全局ID
	 * @throws InfoClassException 如果操作出现异常
	 */
	public void remove(String guid) throws InfoClassException;
}
