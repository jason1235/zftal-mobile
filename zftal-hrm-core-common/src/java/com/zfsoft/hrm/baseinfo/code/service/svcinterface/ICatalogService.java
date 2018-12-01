/**   
 * @Title: ICatalogService.java 
 * @Package com.zfsoft.hrm.baseinfo.code.service.impl 
 * @author jinjj   
 * @date 2012-5-18 上午11:56:16 
 * @version V1.0   
 */
package com.zfsoft.hrm.baseinfo.code.service.svcinterface;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.query.CatalogQuery;


/** 
 * @ClassName: ICatalogService 
 * @author jinjj
 * @date 2012-5-18 上午11:56:16 
 *  
 */
public interface ICatalogService {

	/**
	 * 
	 * @Title: getEntity 
	 * @Description: 获取编目
	 * @param entity {@link Catalog}
	 * @return
	 * @throws RuntimeException
	 */
	public Catalog getEntity(Catalog entity)throws RuntimeException;
	
	/**
	 * 
	 * @Title: delete 
	 * @Description:删除编目 
	 * @param entity {@link Catalog}
	 * @return
	 * @throws RuntimeException
	 */
	public int delete(Catalog entity) throws RuntimeException;
	
	/**
	 * 
	 * @Title: update 
	 * @Description: 更新编目
	 * @param entity {@link Catalog}
	 * @return
	 * @throws RuntimeException
	 */
	public int update(Catalog entity) throws RuntimeException;
	
	/**
	 * 
	 * @Title: insert 
	 * @Description:插入编目 
	 * @param entity {@link Catalog}
	 * @return
	 * @throws RuntimeException
	 */
	public int insert(Catalog entity) throws RuntimeException;
	
	/**
	 * 
	 * @Title: getList 
	 * @Description: 编目列表（未分页）
	 * @param query {@link CatalogQuery}
	 * @return List<Catalog>
	 * @throws RuntimeException
	 */
	public List<Catalog> getList(CatalogQuery query) throws RuntimeException;
	
	/**
	 * 
	 * @Title: insertList 
	 * @Description: 插入多个编目
	 * @param list
	 * @return
	 * @throws RuntimeException
	 */
	public int insertList(List<Catalog> list) throws RuntimeException;
	
	/**
	 * 获取编目分页列表
	 * @param query
	 * @return
	 * @throws NormarInfoException
	 */
	public PageList searchPageList(CatalogQuery query)throws RuntimeException;
}