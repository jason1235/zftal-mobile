package com.zfsoft.dataprivilege.service.impl;

import java.util.List;

import com.zfsoft.dataprivilege.dao.IDataPrivilegeDao;
import com.zfsoft.dataprivilege.dto.AbstractFilter;
import com.zfsoft.dataprivilege.entity.DataPrivilege;
import com.zfsoft.dataprivilege.filter.IDealFilter;
import com.zfsoft.dataprivilege.service.IDataPrivilegeService;
import com.zfsoft.dataprivilege.util.ResourceUtil;
import com.zfsoft.dataprivilege.xentity.Context;

public class DataPrivilegeServiceImpl implements IDataPrivilegeService{
	private IDataPrivilegeDao dataPrivilegeDao = null;
	
	public void setDataPrivilegeDao(IDataPrivilegeDao dataPrivilegeDao) {
		this.dataPrivilegeDao = dataPrivilegeDao;
	}
	
	@Override
	public DataPrivilege getDataPrivilegeById(DataPrivilege query) {
		List<DataPrivilege> dataPrivileges=dataPrivilegeDao.findByQuery(query);
		if(dataPrivileges!=null&&dataPrivileges.size()>0){
			return dataPrivileges.get(0);
		}
		return null;
	}

	@Override
	public AbstractFilter getValueObject(AbstractFilter filter) {
		DataPrivilege query=new DataPrivilege();
		query.setFilterId(filter.getFilterId());
		query.setRoleId(filter.getRoleId());
		query.setUserId(filter.getUserId());
		DataPrivilege dataPrivilege=getDataPrivilegeById(query);
		if(dataPrivilege==null){
			return filter;
		}
		Context context=ResourceUtil.getContextById(dataPrivilege.getFilterId());
		
		try {
			IDealFilter deal=(IDealFilter)Class.forName(context.getDealclass()).newInstance();
			
			return deal.getObjectFromXml(dataPrivilege.getXmlValue());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void saveValue(AbstractFilter filter) {
		if(filter==null){
			deleteDataPrivilege(filter);
		}
		DataPrivilege query=new DataPrivilege();
		query.setFilterId(filter.getFilterId());
		query.setRoleId(filter.getRoleId());
		query.setUserId(filter.getUserId());
		DataPrivilege dataPrivilege=getDataPrivilegeById(query);
		Context context=ResourceUtil.getContextById(filter.getFilterId());
		IDealFilter deal = null;
		try {
			deal=(IDealFilter)Class.forName(context.getDealclass()).newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		if(deal==null){
			return;
		}
		if(dataPrivilege==null){
			dataPrivilege=new DataPrivilege();
			dataPrivilege.setFilterId(filter.getFilterId());
			dataPrivilege.setRoleId(filter.getRoleId());
			dataPrivilege.setUserId(filter.getUserId());
			dataPrivilege.setXmlValue(deal.getXmlFromObject(filter));
			dataPrivilegeDao.insert(dataPrivilege);
		}else{
			dataPrivilege.setXmlValue(deal.getXmlFromObject(filter));
			dataPrivilegeDao.update(dataPrivilege);
		}
	}

	@Override
	public void deleteDataPrivilege(AbstractFilter filter) {
		DataPrivilege query=new DataPrivilege();
		query.setFilterId(filter.getFilterId());
		query.setRoleId(filter.getRoleId());
		query.setUserId(filter.getUserId());
		DataPrivilege dataPrivilege=getDataPrivilegeById(query);
		if(dataPrivilege!=null){
			dataPrivilegeDao.delete(dataPrivilege);
		}
	}

	@Override
	public List<DataPrivilege> getDataPrivilege(DataPrivilege query) {
		return dataPrivilegeDao.findByQuery(query);
	}

	
}
