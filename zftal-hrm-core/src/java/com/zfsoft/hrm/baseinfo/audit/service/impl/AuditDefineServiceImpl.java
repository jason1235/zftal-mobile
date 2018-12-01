package com.zfsoft.hrm.baseinfo.audit.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.hrm.baseinfo.audit.business.IAuditDefineBusiness;
import com.zfsoft.hrm.baseinfo.audit.business.IAuditInfoBusiness;
import com.zfsoft.hrm.baseinfo.audit.business.IAuditProcessBusiness;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditConfigInfo;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditDefine;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditInfo;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditProcess;
import com.zfsoft.hrm.baseinfo.audit.query.AuditDefineQuery;
import com.zfsoft.hrm.baseinfo.audit.query.AuditProcessQuery;
import com.zfsoft.hrm.baseinfo.audit.service.IAuditDefineService;
import com.zfsoft.hrm.baseinfo.audit.util.AuditDefineCacheUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.core.exception.RuleException;

/** 
 * @author jinjj
 * @date 2012-9-28 下午02:18:08 
 *  
 */
public class AuditDefineServiceImpl implements IAuditDefineService {

	private IAuditDefineBusiness defineBusiness;
	private IAuditProcessBusiness processBusiness;
	private IAuditInfoBusiness infoBusiness;
	
	@Override
	public void save(AuditDefine define) {
		defineBusiness.save(define);
		AuditProcessQuery query = new AuditProcessQuery();
		query.setClassId(define.getClassId());
		query.setStepType(1);
		List<AuditProcess> list = processBusiness.getList(query);
		if(list.size()>0){
			reset(list);
		}
		AuditDefineCacheUtil.clear();
	}
	
	/**
	 * 重置目标信息类所有审核中的流程
	 * @param list
	 */
	private void reset(List<AuditProcess> list){
		for(AuditProcess p : list){
			p.setStep(0);
			processBusiness.update(p);
			// 考虑添加重置信息
			AuditInfo info = new AuditInfo();
			info.setClassId(p.getClassId());
			info.setGuid(p.getGuid());
			info.setOperator("system");
			info.setRoleId("system");
			info.setStatus(-1);
			info.setInfo("审核流程变动，该信息重新审核.");
			infoBusiness.save(info);
		}
	}

	@Override
	public void delete(String guid) {
		AuditDefine define = defineBusiness.getById(guid);
		List<AuditDefine> defineList = AuditDefineCacheUtil.getDefine(define.getClassId());
		AuditProcessQuery query = new AuditProcessQuery();
		query.setClassId(define.getClassId());
		if(defineList.size()==1){//仅当最后一个审核人时，需判断是否有未审核完成的信息
			query.setStepType(0);
			List<AuditProcess> list = processBusiness.getList(query);
			if(list.size()>0){
				throw new RuleException("该信息类仍有待审核信息，请处理后再删除最后审核人");
			}
		}else{
			query.setStepType(1);
			List<AuditProcess> list = processBusiness.getList(query);
			if(list.size()>0){
				reset(list);
			}
		}
		defineBusiness.delete(guid);
		AuditDefineCacheUtil.clear();
	}

	@Override
	public List<AuditDefine> getList(AuditDefineQuery query) {
		return defineBusiness.getList(query);
	}
	
	public List<AuditConfigInfo> getPagingList(AuditDefineQuery query){
		List<InfoClass> classList = InfoClassCache.getInfoClasses();
		List<AuditConfigInfo> list = new ArrayList<AuditConfigInfo>();
		for(InfoClass clazz:classList){
			AuditConfigInfo info = new AuditConfigInfo();
			info.setClassId(clazz.getGuid());
			info.setClassName(clazz.getName());
			query.setClassId(clazz.getGuid());
			List<AuditDefine> l = defineBusiness.getList(query);
			info.setList(l);
			list.add(info);
		}
		return list;
	}

	public void setDefineBusiness(IAuditDefineBusiness defineBusiness) {
		this.defineBusiness = defineBusiness;
	}

	public void setProcessBusiness(IAuditProcessBusiness processBusiness) {
		this.processBusiness = processBusiness;
	}

	public void setInfoBusiness(IAuditInfoBusiness infoBusiness) {
		this.infoBusiness = infoBusiness;
	}

}
