package com.zfsoft.hrm.baseinfo.org.business.businessinterfaces;

import java.util.HashMap;
import java.util.List;

import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;


public interface IOrgSearchBusiness {

	/**
	 * 获取指定部门下在职人员数
	 * @param oid
	 * @return
	 * @throws RuntimeException
	 */
	public int getExactCountByOrg(String oid) throws RuntimeException;
	
	/**
	 * 获取指定部门及其子部门下在职人员数
	 * @param oid
	 * @return
	 * @throws RuntimeException
	 */
	public int getStepCountByOrg(String oid) throws RuntimeException;
	
	/**
	 * 获得全体在职人员数
	 * @return
	 * @throws RuntimeException
	 */
	public int getPeopleCount() throws RuntimeException;
	
	/**
	 * Search for a list of departments
	 * @param query
	 * @return
	 * @throws RuntimeException
	 */
	public List<OrgInfo> getOrgList(OrgQuery query) throws RuntimeException;
	
	public List<HashMap<String, String>> getCountMapByOrg() throws RuntimeException;
	
	/**
	 * 获取指定类型下的在职人员数量
	 * @param type
	 * @return 数量
	 * @throws RuntimeException
	 */
	public int getCountByType(String type) throws RuntimeException;
}
