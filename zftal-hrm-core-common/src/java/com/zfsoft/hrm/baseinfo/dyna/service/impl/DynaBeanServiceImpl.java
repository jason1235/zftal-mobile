package com.zfsoft.hrm.baseinfo.dyna.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.exception.DynaBeanException;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.config.IConstants;

/** 
 * 
 * {@link IDynaBeanService }的缺省实现
 * @author jinjj
 * @date 2012-6-20 上午10:42:53 
 *  
 */
public class DynaBeanServiceImpl implements IDynaBeanService {
	
	private IDynaBeanBusiness business;

	public void setBusiness(IDynaBeanBusiness business) {
		this.business = business;
	}

	@Override
	public DynaBean findById(DynaBean bean) throws DynaBeanException {
		return business.findById(bean); 
	}

	@Override
	public List<DynaBean> findList(DynaBeanQuery query) throws DynaBeanException {
		return business.queryBeans(query);
	}
	
	@Override
	public DynaBean findUniqueByParam(String paramName,Object value) throws DynaBeanException {
		return business.findUniqueByParam(paramName, value);
	}
	
	@Override
	public void addPerson(String staffid) {
		business.addPerson(staffid);
	}
	
	@Override
	public void deletePerson(String staffid) {
		business.deletePerson(staffid);
	}
	
	@Override
	public void addBean(DynaBean bean) throws DynaBeanException {
		business.addBean(bean);
	}
	
	@Override
	public void modifyBean(DynaBean bean) throws DynaBeanException {
		business.modifyBean(bean);
	}
	
	@Override
	public void removeBean(DynaBean bean) throws DynaBeanException {
		business.removeBean(bean);
	}

	@Override
	public boolean addRecord(DynaBean bean) throws DynaBeanException {
		return business.addRecord(bean);
	}
	
	@Override
	public boolean addRecordNoCheckGh(DynaBean bean) throws DynaBeanException {
		return business.addRecordNoCheckGh(bean);
	}

	@Override
	public boolean modifyRecord(DynaBean bean) throws DynaBeanException {
		return business.modifyRecord(bean);
	}
	
	@Override
	public boolean modifyRecordNoChecked(DynaBean bean)
			throws DynaBeanException {
		return business.modifyRecordNoCheckGh(bean);
	}

	@Override
	public boolean removeRecord(DynaBean bean) throws DynaBeanException {
		return business.removeRecord(bean);
	}
	
	@Override
	public boolean removeRecordNoCheckGh(DynaBean bean)
			throws DynaBeanException {
		return business.removeRecordNoCheckGh(bean);
	}

	@Override
	public PageList<DynaBean> findPagingInfoList(DynaBeanQuery query)
			throws DynaBeanException {
		return business.findPagingInfoList(query);
	}

	@Override
	public int findCount(DynaBeanQuery query) throws DynaBeanException {
		return business.findCount(query);
	}

	@Override
	public void doFpgh(String classId, String globalid, String gh) throws DynaBeanException {
		Assert.hasText(classId,"信息类id不能为空");
		Assert.hasText(globalid,"globalid不能为空");
		Assert.hasText(gh,"职工号不得为空");
		
		//overall
		business.addPerson(gh);
		
		//本信息类
		InfoClass clazzOld = InfoClassCache.getInfoClass(classId);
		InfoClass clazz = clazzOld.clone();
		InfoProperty property_gh= clazz.getPropertyByName("gh").clone();
		property_gh.setEditable(true);
		clazz.setProperties(new ArrayList<InfoProperty>());
		clazz.addProperty(property_gh);
		DynaBean model=new DynaBean(clazz);
		model.setValue("gh", gh);
		model.setValue("globalid", globalid);
		business.modifyBean(model);
		model=business.findById(model);
		business.removeRecordNoCheckGh(model);
		
		//jbxx
		InfoClass jbxx=InfoClassCache.getInfoClass(IConstants.INFO_CATALOG_PERSON_JBXX);
		List<InfoProperty> jbxxProperties=new ArrayList<InfoProperty>();
		for (InfoProperty property : clazzOld.getViewables()) {
			InfoProperty propertyTmp=null;
			for (InfoProperty jbxxp : jbxx.getProperties()) {
				if(StringUtils.equals(property.getFieldName(), jbxxp.getFieldName())){
					propertyTmp=jbxxp;
					break;
				}
			}
			if(propertyTmp!=null){
				propertyTmp.setEditable(property.getEditable());
				jbxxProperties.add(propertyTmp);
			}
		}
		jbxx.setProperties(jbxxProperties);
		DynaBean dynaBean=new DynaBean(jbxx);
		dynaBean.setValues(model.getValues());
		dynaBean.setValue("gh", gh);
		business.addRecord(dynaBean);
	}

}
