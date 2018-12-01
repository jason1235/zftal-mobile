package com.zfsoft.hrm.normal.resume.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanValidateUtil;
import com.zfsoft.hrm.baseinfo.finfo.util.FormInfoUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.normal.resume.service.svcinterface.IStaffResumeService;

/**
 * 员工详细信息(简历)服务实现类
 * @ClassName: StaffResumeServiceImpl 
 * @author jinjj
 * @date 2012-6-21 上午09:50:47 
 *
 */
public class StaffResumeServiceImpl implements IStaffResumeService {

	private IDynaBeanService dynaBeanService;
	
	@Override
	public void delete(DynaBean bean) {
		dynaBeanService.removeRecord( bean );
	}
	
	@Override
	public DynaBean getById(DynaBean bean) {
		return dynaBeanService.findById(bean);
	}
	
	public List<List<DynaBean>> getInfoByCatalog(String catalogId,DynaBeanQuery query){
		List<List<DynaBean>> list = new ArrayList<List<DynaBean>>();
		for(InfoClass clazz : InfoClassCache.getInfoClasses()){
			if(clazz.getCatalog().getGuid().equals(catalogId)){
				InfoClass c = FormInfoUtil.reFillPropertyByRole(SessionFactory.getUser().getJsdms(), clazz.getGuid());
				query.setClazz(c);
				List<DynaBean> dynaList = getInfoByClass(query);
				list.add(dynaList);
			}
		}
		return list;
	}
	
	public List<DynaBean> getInfoByClass(DynaBeanQuery query) {
		List<DynaBean> list = dynaBeanService.findList(query);
		if(list.size()==0){
			DynaBean bean = new DynaBean(query.getClazz());
			bean.setValues(null);
			list.add(bean);
		}
		return list;
	}

	@Override
	public void insert(DynaBean bean) {
		DynaBeanValidateUtil.validate(bean);
		dynaBeanService.addRecord( bean );
	}

	@Override
	public void update(DynaBean bean) {
		DynaBeanValidateUtil.validate(bean);
		dynaBeanService.modifyRecord( bean );
	}

	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
	}


}
