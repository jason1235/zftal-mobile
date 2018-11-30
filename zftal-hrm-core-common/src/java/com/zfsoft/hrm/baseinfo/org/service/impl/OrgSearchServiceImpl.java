package com.zfsoft.hrm.baseinfo.org.service.impl;

import java.util.List;

import com.zfsoft.hrm.baseinfo.org.business.businessinterfaces.IOrgBusiness;
import com.zfsoft.hrm.baseinfo.org.business.businessinterfaces.IOrgSearchBusiness;
import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.entities.OrgSearch;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;
import com.zfsoft.hrm.baseinfo.org.service.svcinterface.IOrgSearchService;
import com.zfsoft.hrm.baseinfo.org.util.OrgSearchUtil;

public class OrgSearchServiceImpl implements IOrgSearchService {
	
	private IOrgBusiness orgBusiness;
	
	private IOrgSearchBusiness orgSearchBusiness;
	
	public void setOrgBusiness(IOrgBusiness orgBusiness) {
		this.orgBusiness = orgBusiness;
	}
	
	public void setOrgSearchBusiness(IOrgSearchBusiness orgSearchBusiness) {
		this.orgSearchBusiness = orgSearchBusiness;
	}

	@Override
	public List<OrgSearch> getList(OrgQuery query) {
		List<OrgInfo> dataList = orgSearchBusiness.getOrgList(query);
		OrgQuery orgQuery = new OrgQuery();
		orgQuery.setType(query.getType());
		return OrgSearchUtil.supplySearchResult(orgBusiness.getList(orgQuery),dataList,orgSearchBusiness.getCountMapByOrg());
	}
	
	@Override
	public OrgSearch getById(String oid) {
		OrgInfo orgInfo = orgBusiness.getById(oid);
		if(orgInfo == null){
			return null;
		}
		OrgSearch orgSearch = new OrgSearch();
		orgSearch.setOrgInfo(orgInfo);
		orgSearch.getResult().setProperty("exactCount", String.valueOf(orgSearchBusiness.getExactCountByOrg(oid)));
		orgSearch.getResult().setProperty("stepCount", String.valueOf(orgSearchBusiness.getStepCountByOrg(oid)));
		return orgSearch;
	}
	
	@Override
	public int getPeopleCount() {
		return orgSearchBusiness.getPeopleCount();
	}

	@Override
	public int getPeopleCountByType(String Type) {
		return orgSearchBusiness.getCountByType(Type);
	}
	
}
