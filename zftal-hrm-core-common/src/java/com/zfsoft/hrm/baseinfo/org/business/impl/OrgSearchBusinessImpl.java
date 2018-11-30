package com.zfsoft.hrm.baseinfo.org.business.impl;

import java.util.HashMap;
import java.util.List;

import com.zfsoft.hrm.baseinfo.org.business.businessinterfaces.IOrgSearchBusiness;
import com.zfsoft.hrm.baseinfo.org.dao.daointerface.IOrgSearchDao;
import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;

public class OrgSearchBusinessImpl implements IOrgSearchBusiness {

	private IOrgSearchDao orgSearchDao;
	
	public void setOrgSearchDao(IOrgSearchDao orgSearchDao) {
		this.orgSearchDao = orgSearchDao;
	}
	
	@Override
	public int getExactCountByOrg(String oid) throws RuntimeException {
		return orgSearchDao.findExactCountByOrg(oid);
	}

	@Override
	public int getPeopleCount() throws RuntimeException {
		return orgSearchDao.findPeopleCount();
	}

	@Override
	public int getStepCountByOrg(String oid) throws RuntimeException {
		return orgSearchDao.findStepCountByOrg(oid);
	}

	@Override
	public List<OrgInfo> getOrgList(OrgQuery query) throws RuntimeException {
		return orgSearchDao.findOrgList(query);
	}
	
	@Override
	public List<HashMap<String, String>> getCountMapByOrg() throws RuntimeException {
		return orgSearchDao.findCountMapByOrg();
	}

	@Override
	public int getCountByType(String type) throws RuntimeException {
		return orgSearchDao.findByType(type);
	}
	
}
