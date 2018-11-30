package com.zfsoft.hrm.dybill.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.enums.PrivilegeType;
import com.zfsoft.hrm.dybill.service.ISpBillCheckService;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybill.xml.XmlValueClass;

public class SpBillCheckServiceImpl implements ISpBillCheckService {
	
	private ISpBillConfigService spBillConfigService;
	private ISpBillInstanceService spBillInstanceService;
	
	public void setSpBillConfigService(ISpBillConfigService spBillConfigService) {
		this.spBillConfigService = spBillConfigService;
	}
	public void setSpBillInstanceService(
			ISpBillInstanceService spBillInstanceService) {
		this.spBillInstanceService = spBillInstanceService;
	}

	@Override
	public String intanceCheck(String billConfigId, String billInstanceId,String billClassIdsAndPrivilege) {
		List<XmlBillClass> xmlBillClassList=spBillConfigService.getXmlBillClassListLastVersion(billConfigId,billClassIdsAndPrivilege);
		if(xmlBillClassList==null){
			return null;
		}
		List<XmlValueClass> xmlValueClassList=spBillInstanceService.getXmlValueClassList(billConfigId, billInstanceId);
		
		Map<Long,XmlValueClass> valueClassMap=new HashMap<Long, XmlValueClass>();
		if(xmlValueClassList!=null){
			for (XmlValueClass xmlValueClass : xmlValueClassList) {
				valueClassMap.put(xmlValueClass.getBillClassId(), xmlValueClass);
			}
		}
		
		String error="";
		
		for (XmlBillClass xmlBillClass : xmlBillClassList) {
			String singleError=intanceCheckSingle(xmlBillClass, valueClassMap.get(xmlBillClass.getId()));
			if("".equals(singleError)){
				continue;
			}
			error+=singleError+"<br/>";
		}
		
		if("".equals(error)){
			return null;
		}
		
		return error;
	}

	@Override
	public String intanceCheckSingle(String billConfigId, String billInstanceId,
			Long billClassId) {
		SpBillInstance spBillInstance=spBillInstanceService.getSpBillInstanceById(billConfigId, billInstanceId);
		XmlBillClass xmlBillClass = spBillConfigService.getXmlBillClassByVersion(billConfigId,spBillInstance.getVersion(), billClassId);
		XmlValueClass xmlValueClass = spBillInstanceService.getXmlValueClassById(billConfigId, billInstanceId, billClassId);
		
		return intanceCheckSingle(xmlBillClass, xmlValueClass);
	}
	
	private String intanceCheckSingle(XmlBillClass xmlBillClass,XmlValueClass xmlValueClass){
		String error="";
		
		if(xmlBillClass.getPrivilegeType().equals(PrivilegeType.SEARCH)){
			return "";
		}
		
		String desc=xmlBillClass.getName()+"未满足最小限制要求,至少需要"+xmlBillClass.getMinLength()+"条;";
		if(xmlValueClass==null)
		{
			if(xmlBillClass.getMinLength()>0)
			{
				error+=desc;
			}
			return error;
		}
		if(xmlBillClass.getMinLength()>0){
			if(xmlValueClass.getValueEntities()==null||
					xmlValueClass.getValueEntities().size()<=0){
				//未满足最小限制要求
				error+=desc;
			}else{
				if(xmlValueClass.getValueEntities().size()<
						xmlBillClass.getMinLength()){
					//未满足最小限制要求
					error+=desc;
				}
			}
		}
		if(!"".equals(error)){
			return error;
		}
		
		if(xmlBillClass.getMarkBillPropertys().size()>=1){
			String propNames="";
			for (XmlBillProperty xmlBillProperty : xmlBillClass.getMarkBillPropertys()) {
				if(xmlValueClass.getLastValueEntity(xmlBillProperty.getId())==null){
					propNames+="("+xmlBillProperty.getName()+")";
				}
			}
			if(!"".equals(propNames)){
				error+=propNames+"至少有一个是选择是的";
			}
		}
		
		return error;
	}

}
