package com.zfsoft.hrm.baseinfo.org.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.org.business.businessinterfaces.IOrgBusiness;
import com.zfsoft.hrm.baseinfo.org.dao.daointerface.IOrgDao;
import com.zfsoft.hrm.baseinfo.org.dao.daointerface.IOrgSearchDao;
import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;
import com.zfsoft.util.base.StringUtil;

public class OrgBusinessImpl implements IOrgBusiness {

	private IOrgDao orgDao;
	private IOrgSearchDao orgSearchDao;
	
	public void setOrgDao(IOrgDao orgDao) {
		this.orgDao = orgDao;
	}
	
	@Override
	public boolean add(OrgInfo info) throws RuntimeException {
		Assert.notNull(info, "新增信息不可为空");
		return orgDao.insert(info) > 0 ? true : false;
	}

	@Override
	public boolean modify(OrgInfo info) throws RuntimeException {
		Assert.notNull(info, "修改信息不可为空");
		Assert.isTrue(!StringUtil.isEmpty(info.getOid()), "未指定任何信息");
		return orgDao.update(info) > 0 ? true : false;
	}

	@Override
	public void remove(String oid) throws RuntimeException {
		Assert.isTrue(!StringUtil.isEmpty(oid), "未指定任何信息");
		orgDao.delete(oid);
	}
	
	@Override
	public boolean disuse(List<OrgInfo> list) throws RuntimeException {
		Assert.notNull(list, "未指定任何部门");
		Assert.isTrue(list.size() > 0, "未指定任何部门");
		return orgDao.disuse(list) == list.size() ? true : false;
	}
	

	@Override
	public boolean deleteBefore(String oid) throws RuntimeException {
		return orgSearchDao.findExactCountByOrg(oid) > 0 ? true : false;
	}

	@Override
	public boolean use(List<OrgInfo> list) throws RuntimeException {
		Assert.notNull(list, "未指定任何部门");
		Assert.isTrue(list.size() > 0, "未指定任何部门");
		return orgDao.use(list) == list.size() ? true : false;
	}
	
	@Override
	public OrgInfo getById(String oid) throws RuntimeException {
		Assert.isTrue(!StringUtil.isEmpty(oid), "未指定任何信息");
		return orgDao.findById(oid);
	}

	@Override
	public List<OrgInfo> getList(OrgQuery query) throws RuntimeException {
		Assert.notNull(query, "查询信息不可为空");
		return orgDao.findList(query);
	}

	@Override
	public List<OrgInfo> getDisusedList(OrgQuery query) throws RuntimeException {
		Assert.notNull(query, "查询信息不可为空");
		return orgDao.findDisusedList(query);
	}
	
	@Override
	public List<OrgInfo> getPage(OrgQuery query) throws RuntimeException {
		PageList<OrgInfo> pageList = new PageList<OrgInfo>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(orgDao.findPageCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(orgDao.findPage(query));
			}
		}	
		
		return pageList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean updateType(HashMap<String, Object> map)
			throws RuntimeException {
		Assert.notNull(map, "修改子部门部门类型信息不可为空");
		Assert.notNull(map.get("orgList"), "修改部门类型子部门不可为空");
		Assert.isTrue(((ArrayList<OrgInfo>)map.get("orgList")).size() > 0, "修改部门类型子部门不可为空");
		Assert.isTrue(!StringUtil.isEmpty((String)map.get("type")), "修改部门类型不可为空");
		return orgDao.updateType(map) > 0 ? true : false;
	}

	@Override
	public boolean deleteJudge(String oid) throws RuntimeException {
		Assert.isTrue(!StringUtil.isEmpty(oid), "未指定任何记录");
		return orgDao.deleteJudge(oid) > 0 ? true : false;
	}

	public boolean disusedJudge(String oid) throws RuntimeException {
		Assert.isTrue(!StringUtil.isEmpty(oid), "未指定任何记录");
		return orgDao.disusedJudge(oid) > 0 ? true : false;
	}
	
	@Override
	public List<OrgInfo> findOrgList(OrgQuery query) {
		return orgDao.findOrgList(query);
	}

	/**
	 * @return the orgSearchDao
	 */
	public IOrgSearchDao getOrgSearchDao() {
		return orgSearchDao;
	}

	/**
	 * @param orgSearchDao the orgSearchDao to set
	 */
	public void setOrgSearchDao(IOrgSearchDao orgSearchDao) {
		this.orgSearchDao = orgSearchDao;
	}
}
