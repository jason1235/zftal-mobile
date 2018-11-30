package com.zfsoft.hrm.baseinfo.org.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.org.entities.OrgSearch;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;

public interface IOrgSearchService {

	/**
	 * 获取机构查询列表
	 * @param query
	 * @return
	 */
	public List<OrgSearch> getList(OrgQuery query);
	
	/**
	 * 根据部门编号获取机构查询对象
	 * @param oid
	 * @return
	 */
	public OrgSearch getById(String oid);
	
	/**
	 * 获取当前在职员工总数
	 * @return
	 */
	public int getPeopleCount();
	
	/**
	 * 获取指定类型下的在职人员数量
	 */
	public int getPeopleCountByType(String Type);
}
