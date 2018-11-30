package com.zfsoft.hrm.baseinfo.audit.business.impl;

import java.util.List;

import com.zfsoft.hrm.baseinfo.audit.business.IAuditDefineBusiness;
import com.zfsoft.hrm.baseinfo.audit.dao.IAuditDefineDao;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditDefine;
import com.zfsoft.hrm.baseinfo.audit.query.AuditDefineQuery;

/** 
 * 审核定义
 * @author jinjj
 * @date 2012-9-28 下午01:55:38 
 *  
 */
public class AuditDefineBusinessImpl implements IAuditDefineBusiness {

	private IAuditDefineDao defineDao;
	
	@Override
	public void save(AuditDefine define) {
		AuditDefineQuery query = new AuditDefineQuery();
		query.setClassId(define.getClassId());
		List<AuditDefine> list = defineDao.getList(query);
		define.setOrder(list.size()+1);
		defineDao.insert(define);
	}

	@Override
	public void delete(String guid) {
		AuditDefine old = defineDao.getById(guid);
		defineDao.delete(guid);
		AuditDefineQuery query = new AuditDefineQuery();
		query.setClassId(old.getClassId());
		List<AuditDefine> list = defineDao.getList(query);
		int index = 0;//更新排序
		for(AuditDefine ad : list){
			index++;
			if(index>=old.getOrder()){
				ad.setOrder(index);
				defineDao.updateOrder(ad);
			}
		}
	}

	@Override
	public AuditDefine getById(String guid) {
		return defineDao.getById(guid);
	}

	@Override
	public List<AuditDefine> getList(AuditDefineQuery query) {
		return defineDao.getList(query);
	}

	public void setDefineDao(IAuditDefineDao defineDao) {
		this.defineDao = defineDao;
	}

}
