package com.zfsoft.hrm.dybill.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.enums.EntityType;
import com.zfsoft.hrm.dybill.service.ISpBillExportService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillClasses;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybill.xml.XmlValueClass;
import com.zfsoft.hrm.dybill.xml.XmlValueEntity;
import com.zfsoft.hrm.dybill.xml.XmlValueProperty;
import com.zfsoft.util.base.StringUtil;

public class SpBillExportServiceImpl implements ISpBillExportService {

	@Override
	public Map<String, String> getValueMap(SpBillConfig spBillConfig,
			SpBillInstance spBillInstance) {

		return getValueMap(spBillConfig,spBillInstance,true);
	}
	
	public Map<String, String> getValueMap(SpBillConfig spBillConfig,
			SpBillInstance spBillInstance,boolean explain) {
		if(spBillInstance==null){
			return new HashMap<String, String>();
		}
		Map<String, String> valueMap=new HashMap<String, String>();
		XmlBillClasses xmlBillClasses=spBillConfig.getXmlBillClasses();
		XmlBillClass xmlBillClass;
		String identityName;
		String fieldName;
		for (XmlValueClass xmlValueClass : 
				spBillInstance.getXmlValueClasses().getValueClasses()) {
			xmlBillClass=xmlBillClasses.getBillClassById(xmlValueClass.getBillClassId());
			if(xmlBillClass == null){//排除修改表单模板后，实例对象中的历史数据，查询表单类为空的问题
				continue;
			}
			xmlValueClass.setXmlBillClass(xmlBillClass);
			List<XmlValueEntity> xmlValueEntityList=xmlValueClass.getLastValueEntity();
			if(xmlValueEntityList.size()==0){
				continue;
			}
			identityName=xmlBillClass.getIdentityName();
			for (XmlValueEntity xmlValueEntity : xmlValueEntityList) {
				if(xmlValueEntity.getValueProperties()==null){
					continue;
				}
				for (XmlValueProperty xmlValueProperty : xmlValueEntity.getValueProperties()) {
					XmlBillProperty property = xmlBillClass.getBillPropertyById(xmlValueProperty.getBillPropertyId());
					if(property ==null){
						continue;
					}
					fieldName=property.getFieldName();
					if("CODE".equals(property.getFieldType())&&explain){
						String value = CodeUtil.getItemValue(property.getCodeId(), xmlValueProperty.getValue());
						String key = StringUtils.lowerCase(identityName+"."+fieldName);
						if(StringUtil.isEmpty(valueMap.get(key))){
							valueMap.put(key, value);
						}
					}
					else{
						String key = StringUtils.lowerCase(identityName+"."+fieldName);
						if(StringUtil.isEmpty(valueMap.get(key))){
							valueMap.put(key, xmlValueProperty.getValue());
						}
					}
				}
			}
			
		}
		return valueMap;
	}

	@Override
	public Map<String,Map<String, String>> getValueMap(SpBillConfig spBillConfig,
			List<SpBillInstance> spBillInstanceList) {
		Map<String,Map<String, String>> map=new HashMap<String,Map<String, String>>();
		for (SpBillInstance spBillInstance : spBillInstanceList) {
			map.put(spBillInstance.getId(),getValueMap(spBillConfig, spBillInstance));
		}
		return map;
	}

	public Map<String, String> getValueMapForExport(SpBillConfig spBillConfig,
			SpBillInstance spBillInstance) {
		if(spBillInstance==null || spBillInstance.getXmlValueClasses().getValueClasses() == null){
			return new HashMap<String, String>();
		}
		Map<String, String> valueMap=new HashMap<String, String>();
		XmlBillClasses xmlBillClasses=spBillConfig.getXmlBillClasses();
		XmlBillClass xmlBillClass;
		String identityName;
		String fieldName;
		for (XmlValueClass xmlValueClass : 
				spBillInstance.getXmlValueClasses().getValueClasses()) {
			xmlBillClass=xmlBillClasses.getBillClassById(xmlValueClass.getBillClassId());
			if(xmlBillClass == null){//排除修改表单模板后，实例对象中的历史数据，查询表单类为空的问题
				continue;
			}
			xmlValueClass.setXmlBillClass(xmlBillClass);
			List<XmlValueEntity> xmlValueEntityList=xmlValueClass.getValueEntities();
			if(xmlValueEntityList==null||xmlValueEntityList.size()==0){
				continue;
			}
			int num=0;
			if(xmlBillClass.getMaxLength()>1){
				num=1;
			}
			identityName=xmlBillClass.getIdentityName();
			for (XmlValueEntity xmlValueEntity : xmlValueEntityList) {
				if(xmlValueEntity.getValueProperties()==null){
					continue;
				}
				for (XmlValueProperty xmlValueProperty : xmlValueEntity.getValueProperties()) {
					XmlBillProperty property = xmlBillClass.getBillPropertyById(xmlValueProperty.getBillPropertyId());
					if(property ==null){
						continue;
					}
					fieldName=property.getFieldName();
					String key = StringUtils.lowerCase(identityName+"."+fieldName);
					if(num>0){
						key+="("+num;
					}
					if("CODE".equals(property.getFieldType())){
						String value = CodeUtil.getItemValue(property.getCodeId(), xmlValueProperty.getValue());
						if(StringUtil.isEmpty(valueMap.get(key))){
							valueMap.put(key, value);
						}
					}
					if("SIGLE_SEL".equals(property.getFieldType())){
						String value =  "1".equals(xmlValueProperty.getValue())?"是":"否";
						if(StringUtil.isEmpty(valueMap.get(key))){
							valueMap.put(key, value);
						}
					}
					else{
						if(StringUtil.isEmpty(valueMap.get(key))){
							valueMap.put(key, xmlValueProperty.getValue());
						}
					}
				}
				if(num>0)
					num++;
			}
			
		}
		return valueMap;
	}

	@Override
	public List<Map<String, String>> getCatchMap(SpBillConfig spBillConfig,
			SpBillInstance spBillInstance) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if(spBillInstance==null || spBillInstance.getXmlValueClasses().getValueClasses() == null){
			return list;
		}
		XmlBillClasses xmlBillClasses=spBillConfig.getXmlBillClasses();
		XmlBillClass xmlBillClass;
		for (XmlValueClass xmlValueClass : 
				spBillInstance.getXmlValueClasses().getValueClasses()) {
			xmlBillClass=xmlBillClasses.getBillClassById(xmlValueClass.getBillClassId());
			if(xmlBillClass == null){//排除修改表单模板后，实例对象中的历史数据，查询表单类为空的问题
				continue;
			}
			xmlValueClass.setXmlBillClass(xmlBillClass);
			List<XmlValueEntity> xmlValueEntityList=xmlValueClass.getValueEntities();
			if(xmlValueEntityList==null||xmlValueEntityList.size()==0){
				continue;
			}
			String uniqueField;
			String source;
			if(xmlBillClass.getDefineCatch()!=null){
				uniqueField = xmlBillClass.getDefineCatch().getUniqueField();
				source = xmlBillClass.getDefineCatch().getTableName();
			}
			else if(!StringUtil.isEmpty(xmlBillClass.getClassId())){
				uniqueField = "globalid";
				InfoClass infoClass=InfoClassCache.getInfoClass(xmlBillClass.getClassId());
				source = infoClass.getIdentityName();
			}else{
				continue;
			}
			for (XmlValueEntity xmlValueEntity : xmlValueEntityList) {
				if(!EntityType.INFOCLASS.equals(xmlValueEntity.getEntityType())){
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("data_key", uniqueField);
				map.put("data_source", source);
				map.put("data_id", xmlValueEntity.getInfoEntityId());
				list.add(map);
			}
		}
		return list;
	}
}
