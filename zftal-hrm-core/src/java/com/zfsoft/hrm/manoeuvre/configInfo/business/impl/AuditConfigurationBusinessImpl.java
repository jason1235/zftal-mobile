package com.zfsoft.hrm.manoeuvre.configInfo.business.impl;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface.IAuditConfigurationBusiness;
import com.zfsoft.hrm.manoeuvre.configInfo.dao.daointerface.IAuditConfigurationDao;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfiguration;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditConfigurationQuery;

public class AuditConfigurationBusinessImpl implements IAuditConfigurationBusiness {

	private IAuditConfigurationDao auditConfigurationDao;
	
	/**
	 * 设置dao
	 * @param auditConfigurationDao
	 */
	public void setAuditConfigurationDao(IAuditConfigurationDao auditConfigurationDao) {
		this.auditConfigurationDao = auditConfigurationDao;
	}
	
	//----------------------------------------------------------------------------------
	
	@Override
	public boolean add(AuditConfiguration info) throws RuntimeException {
		Assert.notNull(info);
		int cnt = auditConfigurationDao.isExist(info);
		if (cnt > 0) {
			throw new RuleException("该审核设置已近存在,请勿重复设置。");
		}
		return auditConfigurationDao.insert(info) > 0 ? true : false;
	}
	
	@Override
	public boolean modify(AuditConfiguration info) throws RuntimeException {
		Assert.notNull(info);
		Assert.notNull(info.getAid());
		return auditConfigurationDao.update(info) > 0 ? true : false;
	}
	
	@Override
	public boolean remove(String aid) throws RuntimeException {
		Assert.notNull(aid);
		return auditConfigurationDao.delete(aid) > 0 ? true : false;
	}
	
	@Override
	public List<AuditConfiguration> getList(AuditConfigurationQuery query) throws RuntimeException {
		Assert.notNull(query);
		return auditConfigurationDao.findAuditConfigurationList(query);
	}
	
	@Override
	public PageList<AuditConfiguration> getPage(AuditConfigurationQuery query) throws RuntimeException {
		PageList<AuditConfiguration> pageList = new PageList<AuditConfiguration>();
		if(query == null){
			return pageList;
		}
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(query.getPerPageSize());
		paginator.setPage((Integer)query.getToPage());
		paginator.setItems(auditConfigurationDao.findAuditConfigurationPageCount(query));
		pageList.setPaginator(paginator);
		if(paginator.getBeginIndex() <= paginator.getItems()){
			query.setStartRow(paginator.getBeginIndex());
			query.setEndRow(paginator.getEndIndex());
			pageList.addAll(auditConfigurationDao.findAuditConfigurationPage(query));
		}
		return pageList;
	}
	
	@Override
	public AuditConfiguration getInfoById(String aid) throws RuntimeException {
		Assert.notNull(aid);
		return auditConfigurationDao.findAuditConfigurationById(aid);
	}

	@Override
	public boolean removeWithTaskNode(String nid) throws RuntimeException {
		Assert.notNull(nid);
		return auditConfigurationDao.deleteWithTaskNode(nid) > 0 ? true : false;
	}

	/**
	 * 取得角色包含的人员
	 */
	@Override
	public List<String> findPersonByRole(AuditConfiguration info) {
		return auditConfigurationDao.findPersonByRole(info);
	}

	@Override
	public List<AuditConfiguration> getListById(String Nid)
			throws RuntimeException {
		Assert.notNull(Nid);
		return auditConfigurationDao.findAuditConfigurationListById(Nid);
	}

	/**
	 * 取得角色
	 */
	@Override
	public List<Map<String, String>> getRoles() {
		return auditConfigurationDao.getRoles();
	}

	@Override
	public String getBmdmByUser(AuditConfiguration info) {
		return auditConfigurationDao.getBmdmByUser(info);
	}
}
