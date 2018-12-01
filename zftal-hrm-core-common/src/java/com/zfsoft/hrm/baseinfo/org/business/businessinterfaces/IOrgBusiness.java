package com.zfsoft.hrm.baseinfo.org.business.businessinterfaces;

import java.util.HashMap;
import java.util.List;

import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;

public interface IOrgBusiness {

	/**
	 * 添加组织机构
	 * @param info
	 * @return
	 * @throws RuntimeException
	 */
	public boolean add(OrgInfo info) throws RuntimeException;
	
	/**
	 * 修改组织机构
	 * @param info
	 * @return
	 * @throws RuntimeException
	 */
	public boolean modify(OrgInfo info) throws RuntimeException;
	
	/**
	 * 废弃组织机构
	 * @param oid
	 * @return
	 * @throws RuntimeException
	 */
	public boolean disuse(List<OrgInfo> list) throws RuntimeException;
	
	/**
	 * 启用组织机构
	 * @param oid
	 * @return
	 * @throws RuntimeException
	 */
	public boolean use(List<OrgInfo> list) throws RuntimeException;
	
	/**
	 * 当一个部门信息变动时，若是部门类型发生变化，则调用本方法修改其全部子部门的部门类型
	 * @param map
	 * @return
	 * @throws RuntimeException
	 */
	public boolean updateType(HashMap<String, Object> map) throws RuntimeException;
	
	/**
	 * 彻底删除组织机构
	 * @param oid
	 * @throws RuntimeException
	 */
	public void remove(String oid) throws RuntimeException;
	
	/**
	 * 查询组织机构列表
	 * @param query
	 * @return
	 * @throws RuntimeException
	 */
	public List<OrgInfo> getList(OrgQuery query) throws RuntimeException;
	
	/**
	 * 根据部门代码查询组织机构
	 * @param oid
	 * @return
	 * @throws RuntimeException
	 */
	public OrgInfo getById(String oid) throws RuntimeException;
	
	/**
	 * 分页查询组织机构列表
	 * @param query
	 * @return
	 * @throws RuntimeException
	 */
	public List<OrgInfo> getPage(OrgQuery query) throws RuntimeException;
	
	/**
	 * 判断一个部门是否被系统其它模块引用，是则返回ture
	 * @param oid
	 * @return
	 * @throws RuntimeException
	 */
	
	public boolean disusedJudge(String oid) throws RuntimeException;
	public boolean deleteBefore(String oid) throws RuntimeException;
	
	public boolean deleteJudge(String oid) throws RuntimeException;

	List<OrgInfo> getDisusedList(OrgQuery query) throws RuntimeException;
	
	public List<OrgInfo> findOrgList(OrgQuery query);
	
}
