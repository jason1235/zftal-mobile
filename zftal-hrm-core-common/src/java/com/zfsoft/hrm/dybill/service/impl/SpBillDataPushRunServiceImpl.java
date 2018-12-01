package com.zfsoft.hrm.dybill.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.dybill.dao.ISpBillDataPushRunDao;
import com.zfsoft.hrm.dybill.dto.DataPushBean;
import com.zfsoft.hrm.dybill.entity.SpBillDataPushEventConfig;
import com.zfsoft.hrm.dybill.entity.SpBillDataPushProperty;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.enums.DataPushEventOpType;
import com.zfsoft.hrm.dybill.service.ISpBillDataPushEventConfigService;
import com.zfsoft.hrm.dybill.service.ISpBillDataPushRunService;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceService;
import com.zfsoft.hrm.dybill.xml.XmlValueClass;
import com.zfsoft.hrm.dybill.xml.XmlValueClasses;
import com.zfsoft.hrm.dybill.xml.XmlValueEntity;
import com.zfsoft.hrm.dybill.xml.XmlValueProperty;
import com.zfsoft.util.base.StringUtil;

/**
 * TODO
 * @className: SpBillDataPushRunServiceImpl 
 * @author Patrick Shen shenluwei@126.com
 * @date 2013-8-14 下午03:51:30
 */
public class SpBillDataPushRunServiceImpl implements ISpBillDataPushRunService {
	
	private ISpBillDataPushRunDao spBillDataPushRunDao;
	
	private ISpBillDataPushEventConfigService spBillDataPushEventConfigService;
	
	private ISpBillInstanceService spBillInstanceService;
	
	private IDynaBeanBusiness dynaBeanBusiness;
	
	
	@Override
	public String pushData(String eventConfigId,String userId, String instanceId){
		return pushData(eventConfigId,userId, instanceId, false);
	}
	
	@Override
	public String pushData(String eventConfigId,String userId, String instanceId,Boolean newValue){
		
		SpBillDataPushEventConfig eventConfig=spBillDataPushEventConfigService.getById(eventConfigId);
		eventConfig.setProperties(spBillDataPushEventConfigService.getPropertyByConfigId(eventConfigId));
		SpBillInstance billInstance = spBillInstanceService.getSpBillInstanceById(eventConfig.getBillConfigId(), instanceId);
		XmlValueClasses xmlValueClasses=billInstance.getXmlValueClasses();
		XmlValueClass xmlValueClass=xmlValueClasses.getValueClassByClassId(eventConfig.getBillClassId());
		if(xmlValueClass == null) return "";
		List<XmlValueEntity> valueEntityList=xmlValueClass.getValueEntities();
		
		//动态信息
		if(StringUtils.hasText(eventConfig.getInfoClassId())){
			return pushToInfoClass(userId,eventConfig, valueEntityList);
		}else{
			return pushToLocalDB(userId,eventConfig, valueEntityList);
		}
	}
	/**
	 * 推向数据库
	 * @methodName pushToLocalDB 
	 * @param eventConfig
	 * @param valueEntityList
	 * @return String
	 */
	private String pushToLocalDB(String userId,SpBillDataPushEventConfig eventConfig,
			List<XmlValueEntity> valueEntityList) {
		DataPushBean dataPushBean=new DataPushBean();
		if(eventConfig.getEventOpType()==DataPushEventOpType.INSERT){
			dataPushBean.setProperties(eventConfig.getProperties());
			spBillDataPushRunDao.insert(dataPushBean);
		}else{
			spBillDataPushRunDao.update(dataPushBean);
		}
		return null;
	}
	/**
	 * 推向信息类
	 * @methodName pushToInfoClass 
	 * @param eventConfig
	 * @param valueEntityList
	 * @return String
	 */
	private String pushToInfoClass(String userId,SpBillDataPushEventConfig eventConfig,
			List<XmlValueEntity> valueEntityList) {
		if( valueEntityList == null|| valueEntityList.isEmpty())return "值对象列表为空";
		InfoClass infoClass=InfoClassCache.getInfoClass(eventConfig.getInfoClassId());
		InfoProperty property;
		XmlValueProperty valueProperty;
		if(eventConfig.getEventOpType()==DataPushEventOpType.INSERT){
			for (XmlValueEntity valueEntity : valueEntityList) {
				DynaBean dynaBean=new DynaBean(infoClass);
				for (SpBillDataPushProperty spBillDataPushProperty : eventConfig.getProperties()) {
					property = infoClass.getPropertyById(spBillDataPushProperty.getLocalPropertyId());
					valueProperty =valueEntity.getValuePropertyById(spBillDataPushProperty.getBillPropertyId());
					if(valueProperty!=null){					
						dynaBean.setValue(property.getFieldName(),valueProperty.getValue());
					}
				}
				dynaBean.setValue("gh", userId);
				dynaBeanBusiness.addBean(dynaBean);
			}
			return "插入成功";
		}else{
			DynaBeanQuery query=new DynaBeanQuery(infoClass);
			String expression = "gh = #{params.gh}";
			if(!StringUtil.isEmpty(eventConfig.getWhereExpression())){
				expression += "and " + eventConfig.getWhereExpression();
			}
			query.setExpress(expression);
			query.setParam("gh", userId);
			List<DynaBean> dynaBeans=dynaBeanBusiness.findList(query);
			for(XmlValueEntity valueEntity : valueEntityList){
				for (DynaBean dynaBean : dynaBeans) {
					Map<String, Object> values = new HashMap<String, Object>();
					for (SpBillDataPushProperty spBillDataPushProperty : eventConfig.getProperties()) {
						property = infoClass.getPropertyById(spBillDataPushProperty.getLocalPropertyId());
						valueProperty =valueEntity.getValuePropertyById(spBillDataPushProperty.getBillPropertyId());
						if(valueProperty!=null){					
							values.put(property.getFieldName(),valueProperty.getValue());
						}
					}
//					dynaBean.setValue("gh", userId);
					dynaBeanBusiness.modifyRecord(dynaBean, values, false);
				}
			}
			return "更新成功";
		}
	}

	public void setSpBillDataPushRunDao(ISpBillDataPushRunDao spBillDataPushRunDao) {
		this.spBillDataPushRunDao = spBillDataPushRunDao;
	}

	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}

	public void setSpBillDataPushEventConfigService(
			ISpBillDataPushEventConfigService spBillDataPushEventConfigService) {
		this.spBillDataPushEventConfigService = spBillDataPushEventConfigService;
	}

	public void setSpBillInstanceService(
			ISpBillInstanceService spBillInstanceService) {
		this.spBillInstanceService = spBillInstanceService;
	}

}
