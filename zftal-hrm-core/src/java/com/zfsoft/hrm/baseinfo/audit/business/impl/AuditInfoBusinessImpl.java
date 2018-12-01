package com.zfsoft.hrm.baseinfo.audit.business.impl;

import java.util.List;

import com.zfsoft.hrm.baseinfo.audit.business.IAuditInfoBusiness;
import com.zfsoft.hrm.baseinfo.audit.dao.IAuditInfoDao;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditInfo;
import com.zfsoft.hrm.baseinfo.audit.query.AuditInfoQuery;

/** 
 * @author jinjj
 * @date 2012-10-10 下午01:40:21 
 *  
 */
public class AuditInfoBusinessImpl implements IAuditInfoBusiness {

	private IAuditInfoDao infoDao;
	
	@Override
	public void save(AuditInfo info) {
		infoDao.insert(info);
	}

	@Override
	public List<AuditInfo> getList(AuditInfoQuery query) {
		List<AuditInfo> list = infoDao.getList(query);
		if(list.size()>5){
			list = list.subList(0, 5);
		}
		return list;
	}

	public void setInfoDao(IAuditInfoDao infoDao) {
		this.infoDao = infoDao;
	}

}
