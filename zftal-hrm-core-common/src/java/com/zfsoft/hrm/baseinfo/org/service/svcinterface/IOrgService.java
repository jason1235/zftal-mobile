package com.zfsoft.hrm.baseinfo.org.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;

public interface IOrgService {

	/**
	 * 添加组织机构
	 * @param info
	 * @return
	 */
	public boolean add(OrgInfo info);
	
	/**
	 * 修改组织机构
	 * @param info
	 * @return
	 */
	public boolean modify(OrgInfo info);
	
	/**
	 * 废弃组织机构
	 * @param oid
	 * @return
	 */
	public boolean disuse(String oid);
	
	/**
	 * 启用组织机构
	 * @param oid
	 * @return
	 */
	public boolean use(String oid);
	
	/**
	 * 彻底删除组织机构
	 * @param oid
	 */
	public void remove(String oid);
	
	/**
	 * 查询组织机构列表
	 * @param query
	 * @return
	 */
	public List<OrgInfo> getList(OrgQuery query);
	
	/**
	 * 根据部门代码查询组织机构
	 * @param oid
	 * @return
	 */
	public OrgInfo getById(String oid);
	
	/**
	 * 分页查询组织机构列表
	 * @param query
	 * @return
	 */
	public List<OrgInfo> getPage(OrgQuery query);

	public List<OrgInfo> getDisusedList(OrgQuery query);
	
	/**
	 * 得到机构树
	 * @param query
	 * @return
	 */
	public OrgInfo getOrgTree(OrgQuery query);
}
