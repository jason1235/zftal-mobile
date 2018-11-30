package com.zfsoft.hrm.baseinfo.org.service.impl;

import java.util.List;

import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.org.config.IOrgConstent;
import com.zfsoft.hrm.baseinfo.org.entities.OrgPeople;
import com.zfsoft.hrm.baseinfo.org.service.svcinterface.IOrgPeopleService;

public class OrgPeopleServiceImpl implements IOrgPeopleService {

	private IDynaBeanBusiness dynaBeanBusiness;
	
	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}

	@Override
	public void peopleMove(OrgPeople orgPeople) {
		if(orgPeople == null || orgPeople.getStaffidList().size() == 0){
			return;
		}
		for (String staffid : orgPeople.getStaffidList()) {
			updatePeopleOrg(staffid, orgPeople.getPlanOrg());
		}
	}
	
	private void updatePeopleOrg(String staffid, String oid){
		InfoClass clazz = InfoClassCache.getInfoClass(IOrgConstent.BASEINFO_CLASS_ID);
		DynaBean bean = new DynaBean(clazz);
		DynaBeanQuery dyQuery=new DynaBeanQuery( clazz );
		dyQuery.setExpress( "gh = #{params.gh}" );
		dyQuery.setParam( "gh", staffid );
		List<DynaBean> dyBeans=dynaBeanBusiness.queryBeans( dyQuery );
		if(dyBeans.size()>0){
			bean = dyBeans.get(0);
		}else{
			return;
		}
		bean.setValue(IOrgConstent.ORG_COLUMN_NAME, oid);
		dynaBeanBusiness.modifyRecord(bean);
	}

}
