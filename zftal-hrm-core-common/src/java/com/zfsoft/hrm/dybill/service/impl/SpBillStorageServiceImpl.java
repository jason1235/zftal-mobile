package com.zfsoft.hrm.dybill.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceService;
import com.zfsoft.hrm.dybill.service.ISpBillStorageService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybill.xml.XmlValueClass;
import com.zfsoft.hrm.dybill.xml.XmlValueEntity;
import com.zfsoft.hrm.dybill.xml.XmlValueProperty;
import com.zfsoft.util.base.StringUtil;

public class SpBillStorageServiceImpl implements ISpBillStorageService {

	private ISpBillInstanceService spBillInstanceService;
	private ISpBillConfigService spBillConfigService;
	private IDynaBeanBusiness dynaBeanBusiness;

	public void setSpBillInstanceService(
			ISpBillInstanceService spBillInstanceService) {
		this.spBillInstanceService = spBillInstanceService;
	}

	public void setSpBillConfigService(ISpBillConfigService spBillConfigService) {
		this.spBillConfigService = spBillConfigService;
	}

	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}

	private String addRecord(SpBillInstance spBillInstance,
			XmlBillClass xmlBillClass, XmlValueEntity valueEntity,String staffId, String deptId) {

		valueEntity.setXmlBillClass(xmlBillClass);

		if (StringUtil.isEmpty(valueEntity.getXmlBillClass().getClassId())) {
			throw new RuntimeException(valueEntity.getXmlBillClass().getName()
					+ "信息类没有对应的人事信息类!");
		}
		InfoClass infoClass = InfoClassCache.getInfoClass(valueEntity
				.getXmlBillClass().getClassId());

		DynaBean bean = null;
		try{
			bean = new DynaBean(infoClass);
		}catch(NullPointerException e){
			e.printStackTrace();
			return null;
		}
		
		for (XmlBillProperty xmlBillProperty : xmlBillClass.getBillPropertys()) {
			bean.setValue(xmlBillProperty.getFieldName(), valueEntity
					.getFieldNameStorageValueMap().get(xmlBillProperty.getFieldName()));
		}
		
		if(StringUtil.isNotEmpty(staffId)&&
				infoClass.getPropertyByName("gh")!=null){
			bean.setValue("gh",staffId);
		}
		
		if(StringUtil.isNotEmpty(deptId)&&
				infoClass.getPropertyByName("dwh")!=null){
			bean.setValue("dwh",deptId);
		}
		bean.setValue("dqztm","11");

		if(StringUtil.isEmpty(staffId)){
			dynaBeanBusiness.addRecord(bean);
		}else{
			if(!dynaBeanBusiness.existPerson(staffId)){
				dynaBeanBusiness.addPerson(staffId);
				DynaBean dynaBean=dynaBeanBusiness.findUniqueByParam("gh", staffId);
				bean.setValue("globalid", dynaBean.getValue("globalid"));
				dynaBeanBusiness.addRecord(bean);
			}else{
				dynaBeanBusiness.addRecord(bean);
			}
		}
		return (String)bean.getValue("globalid");
		
	}

	private void modifyRecord(SpBillInstance spBillInstance,
			XmlBillClass xmlBillClass, XmlValueEntity valueEntity,String guid, String userId) {

		valueEntity.setXmlBillClass(xmlBillClass);

		if (StringUtil.isEmpty(valueEntity.getXmlBillClass().getClassId())) {
			throw new RuntimeException(valueEntity.getXmlBillClass().getName()
					+ "信息类没有对应的人事信息类!");
		}
		InfoClass infoClass = InfoClassCache.getInfoClass(valueEntity
				.getXmlBillClass().getClassId());

		DynaBean dynaBean=dynaBeanBusiness.findUniqueByParam("globalid", guid,infoClass);
		Map<String, Object> values = new HashMap<String, Object>();
//		for (XmlBillProperty xmlBillProperty : xmlBillClass.getBillPropertys()) {
//			dynaBean.setValue(xmlBillProperty.getFieldName(), valueEntity
//					.getFieldNameStorageValueMap().get(xmlBillProperty.getFieldName()));
//		}
		for(XmlValueProperty valueProperty:valueEntity.getValueProperties()){
			if(valueProperty.getNewValue()!=null&&!valueProperty.getNewValue().equals(valueProperty.getValue())){
				try{
					
					values.put(xmlBillClass.getBillPropertyById(valueProperty.getBillPropertyId()).getFieldName(), valueProperty.getNewValue());
				}catch(Exception e){
					
				}
			}
		}
		dynaBean.setValue("gh", userId);
		dynaBean.setValue("globalid", guid);
//		dynaBeanBusiness.modifyRecord(dynaBean);
		dynaBean=dynaBeanBusiness.findById(dynaBean);
		dynaBeanBusiness.modifyRecord(dynaBean, values, false);
		
	}
	private void removeRecord(SpBillInstance spBillInstance,
			XmlBillClass xmlBillClass, XmlValueEntity valueEntity, String userId) {

		valueEntity.setXmlBillClass(xmlBillClass);

		if (StringUtil.isEmpty(valueEntity.getXmlBillClass().getClassId())) {
			throw new RuntimeException(valueEntity.getXmlBillClass().getName()
					+ "信息类没有对应的人事信息类!");
		}
		InfoClass infoClass = InfoClassCache.getInfoClass(valueEntity
				.getXmlBillClass().getClassId());

		DynaBean bean = new DynaBean(infoClass);

		for (XmlBillProperty xmlBillProperty : xmlBillClass.getBillPropertys()) {
			bean.setValue(xmlBillProperty.getFieldName(), valueEntity
					.getFieldNameValueMap().get(xmlBillProperty.getFieldName()));
		}
		DynaBean dynaBean=dynaBeanBusiness.findUniqueByParam("gh", userId);
		bean.setValue("globalid", dynaBean.getValue("globalid"));
		dynaBeanBusiness.removeRecord(bean);
		
	}
	@Override
	public void addValueEntityToStorage(String billConfigId,String billInstanceId,
			Long billClassId, Long entityId) {
		SpBillInstance spBillInstance = spBillInstanceService
				.getSpBillInstanceById(billConfigId,billInstanceId);

		XmlValueEntity valueEntity = spBillInstanceService
				.getXmlValueEntityById(billConfigId,spBillInstance.getId(), billClassId,
						entityId);

		XmlBillClass xmlBillClass = spBillConfigService.getXmlBillClassByVersion(
				spBillInstance.getBillConfigId(),spBillInstance.getVersion(), billClassId);

		addRecord(spBillInstance, xmlBillClass, valueEntity,null,null);
	}

	@Override
	public void addInstanceToStorage(String billConfigId,String billInstanceId) {
		SpBillInstance spBillInstance = spBillInstanceService
				.getSpBillInstanceById(billConfigId,billInstanceId);

		List<XmlValueClass> xmlValueClasses = spBillInstanceService
				.getXmlValueClassList(billConfigId,billInstanceId);

		for (XmlValueClass xmlValueClass : xmlValueClasses) {
			XmlBillClass xmlBillClass = spBillConfigService
					.getXmlBillClassByVersion(spBillInstance.getBillConfigId(),spBillInstance.getVersion(),
							xmlValueClass.getBillClassId());
			for (XmlValueEntity xmlValueEntity : xmlValueClass
					.getValueEntities()) {
				addRecord(spBillInstance, xmlBillClass, xmlValueEntity,null,null);
			}
		}
	}
	
	@Override
	public String hrmInstanceToStorage(String billConfigId,String billInstanceId,String guid,String type,String userId) {
		
		if(userId==null){
			throw new RuntimeException("未找到工号");
		}
		String retStr = null;
		SpBillInstance spBillInstance = spBillInstanceService
				.getSpBillInstanceById(billConfigId,billInstanceId);

		List<XmlValueClass> xmlValueClasses = spBillInstanceService
				.getXmlValueClassList(billConfigId,billInstanceId);

		for (XmlValueClass xmlValueClass : xmlValueClasses) {
			XmlBillClass xmlBillClass = spBillConfigService
					.getXmlBillClassByVersion(spBillInstance.getBillConfigId(),spBillInstance.getVersion(),
							xmlValueClass.getBillClassId());
			for (XmlValueEntity xmlValueEntity : xmlValueClass
					.getValueEntities()) {
				if("add".equals(type)){
					retStr = addRecord(spBillInstance, xmlBillClass, xmlValueEntity,userId,null);
				}
				if("remove".equals(type)){
					removeRecord(spBillInstance, xmlBillClass, xmlValueEntity,userId);
					return null;
				}
				if("modify".equals(type)){
					modifyRecord(spBillInstance, xmlBillClass, xmlValueEntity,guid,userId);
					return guid;
				}
			}
		}
		return retStr;
	}

	@Override
	public void addPersonToStorage(String billConfigId,String billInstanceId,String staffId,String deptId) {
		SpBillInstance spBillInstance = spBillInstanceService
		.getSpBillInstanceById(billConfigId,billInstanceId);

		List<XmlValueClass> xmlValueClasses = spBillInstanceService
				.getXmlValueClassList(billConfigId,billInstanceId);
		
		for (XmlValueClass xmlValueClass : xmlValueClasses) {
			XmlBillClass xmlBillClass = spBillConfigService
					.getXmlBillClassByVersion(spBillInstance.getBillConfigId(),spBillInstance.getVersion(),
							xmlValueClass.getBillClassId());
			for (XmlValueEntity xmlValueEntity : xmlValueClass
					.getValueEntities()) {
				if (StringUtil.isEmpty(xmlValueEntity.getXmlBillClass().getClassId())) {
					continue;
				}
				addRecord(spBillInstance, xmlBillClass, xmlValueEntity, staffId, deptId);
			}
		}
	}

}
