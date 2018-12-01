package com.zfsoft.hrm.baseinfo.dyna.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;

/** 
 * 审核日志
 * @author jinjj
 * @date 2012-10-8 下午06:56:24 
 *  
 */
public interface IDynaBeanLogDao {

	/**
	 * 记录日志
	 * @param logBean
	 */
	public void insert(DynaBean logBean);
	
	/**
	 * 修改日志
	 * @param logBean
	 */
	public void update(DynaBean logBean);
	
	/**
	 * 查询日志
	 * @param logBean
	 * @return
	 */
	public Map<String,Object> getById(DynaBean logBean);
	
	
	public List<Map<String, Object>>getPagingList(BaseQuery query);
	
	
	public int getPagingCount( BaseQuery query );
	
	/**
	 * 移除日志
	 * @param logBean
	 */
	public void removeById(DynaBean logBean);
}
