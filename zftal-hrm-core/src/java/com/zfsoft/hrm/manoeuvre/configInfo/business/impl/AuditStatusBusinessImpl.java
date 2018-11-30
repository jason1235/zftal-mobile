package com.zfsoft.hrm.manoeuvre.configInfo.business.impl;

import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface.IAuditStatusBusiness;
import com.zfsoft.hrm.manoeuvre.configInfo.dao.daointerface.IAuditStatusDao;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditStatus;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditStatusQuery;

public class AuditStatusBusinessImpl implements IAuditStatusBusiness {

	private IAuditStatusDao auditStatusDao;
	
	/**
	 * 设置dao
	 * @param auditStatusDao
	 */
	public void setAuditStatusDao(IAuditStatusDao auditStatusDao) {
		this.auditStatusDao = auditStatusDao;
	}
	
	//-------------------------------------------------------------------------------------
	
	@Override
	public boolean add(AuditStatus info) throws RuntimeException {
		Assert.notNull(info);
		return auditStatusDao.insert(info) > 0 ? true :false;
	}
	
	@Override
	public boolean modify(AuditStatus info) throws RuntimeException {
		Assert.notNull(info);
		return auditStatusDao.update(info) > 0 ? true :false;
	}

	@Override
	public boolean remove(String sid) throws RuntimeException {
		Assert.notNull(sid);
		return auditStatusDao.delete(sid) > 0 ? true :false;
	}

	@Override
	public AuditStatus getById(String sid) throws RuntimeException {
		Assert.notNull(sid);
		return auditStatusDao.findById(sid);
	}

	@Override
	public List<AuditStatus> getList(AuditStatusQuery query)
			throws RuntimeException {
		Assert.notNull(query);
		return auditStatusDao.findList(query);
	}

	@Override
	public PageList<AuditStatus> getPage(AuditStatusQuery query)
			throws RuntimeException {
		PageList<AuditStatus> pageList = new PageList<AuditStatus>();
		if(query == null){
			return pageList;
		}
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(query.getPerPageSize());
		paginator.setPage((Integer)query.getToPage());
		paginator.setItems(auditStatusDao.findPageCount(query));
		pageList.setPaginator(paginator);
		if(paginator.getBeginIndex() <= paginator.getItems()){
			query.setStartRow(paginator.getBeginIndex());
			query.setEndRow(paginator.getEndIndex());
			pageList.addAll(auditStatusDao.findPage(query));
		}
		return pageList;
	}

}
