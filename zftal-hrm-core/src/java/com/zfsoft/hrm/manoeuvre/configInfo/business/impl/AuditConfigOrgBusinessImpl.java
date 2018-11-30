package com.zfsoft.hrm.manoeuvre.configInfo.business.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface.IAuditConfigOrgBusiness;
import com.zfsoft.hrm.manoeuvre.configInfo.dao.daointerface.IAuditConfigOrgDao;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfigOrgInfo;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditConfigOrgQuery;

public class AuditConfigOrgBusinessImpl implements IAuditConfigOrgBusiness {

	private IAuditConfigOrgDao auditConfigOrgDao;
	
	public void setAuditConfigOrgDao(IAuditConfigOrgDao auditConfigOrgDao) {
		this.auditConfigOrgDao = auditConfigOrgDao;
	}
	
	@Override
	public boolean add(AuditConfigOrgInfo info) throws RuntimeException {
		return auditConfigOrgDao.insert(info) > 0 ? true : false;
	}
	
	@Override
	public boolean modify(AuditConfigOrgInfo info) throws RuntimeException {
		return auditConfigOrgDao.update(info) > 0 ? true : false;
	}

	@Override
	public void remove(String aoid) throws RuntimeException {
		auditConfigOrgDao.delete(aoid);
	}

	@Override
	public AuditConfigOrgInfo getById(String aoid) throws RuntimeException {
		return auditConfigOrgDao.findById(aoid);
	}

	@Override
	public List<AuditConfigOrgInfo> getList(AuditConfigOrgQuery query)
			throws RuntimeException {
		return auditConfigOrgDao.findList(query);
	}

	@Override
	public PageList<AuditConfigOrgInfo> getPage(AuditConfigOrgQuery query)
			throws RuntimeException {
		PageList<AuditConfigOrgInfo> pageList = new PageList<AuditConfigOrgInfo>();
		if(query == null){
			return pageList;
		}
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(query.getPerPageSize());
		paginator.setPage((Integer)query.getToPage());
		paginator.setItems(auditConfigOrgDao.findPageCount(query));
		pageList.setPaginator(paginator);
		if(paginator.getBeginIndex() <= paginator.getItems()){
			query.setStartRow(paginator.getBeginIndex());
			query.setEndRow(paginator.getEndIndex());
			pageList.addAll(auditConfigOrgDao.findPage(query));
		}
		return pageList;
	}

	@Override
	public void removeByAid(String aid) throws RuntimeException {
		auditConfigOrgDao.deleteByAid(aid);
	}

}
