package com.zfsoft.hrm.dybill.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.dybill.dao.ISpBillInstanceDao;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.enums.ApprovStatus;
import com.zfsoft.hrm.dybill.enums.BillInstanceStatus;
import com.zfsoft.hrm.dybill.enums.EntityType;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.service.ISpBillDefineCatchService;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceLogService;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceService;
import com.zfsoft.hrm.dybill.util.BillValueUtil;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybill.xml.XmlValueClass;
import com.zfsoft.hrm.dybill.xml.XmlValueClasses;
import com.zfsoft.hrm.dybill.xml.XmlValueEntity;
import com.zfsoft.hrm.dybill.xml.XmlValueProperty;
import com.zfsoft.hrm.file.util.AttachementUtil;
import com.zfsoft.hrm.file.util.ImageDBUtil;
import com.zfsoft.util.base.StringUtil;
/**
 * 审批表单实例
 * @author Patrick Shen
 */
public class SpBillInstanceServiceImpl implements ISpBillInstanceService {
	private ISpBillInstanceDao spBillInstanceDao;
	private ISpBillConfigService spBillConfigService;
	private IDynaBeanBusiness dynaBeanBusiness;
	private BillValueUtil billValueUtil;
	private ISpBillInstanceLogService spBillInstanceLogService;

	
	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}

	public void setBillValueUtil(BillValueUtil billValueUtil) {
		this.billValueUtil = billValueUtil;
	}

	public void setSpBillConfigService(ISpBillConfigService spBillConfigService) {
		this.spBillConfigService = spBillConfigService;
	}
	
	public void setSpBillInstanceDao(ISpBillInstanceDao spBillInstanceDao) {
		this.spBillInstanceDao = spBillInstanceDao;
	}
	public void setSpBillInstanceLogService(ISpBillInstanceLogService spBillInstanceLogService) {
		this.spBillInstanceLogService = spBillInstanceLogService;
	}
	
	@Override
	public XmlValueClass appendCatchRecord(String billConfigId,String billInstanceId,
			XmlBillClass xmlBillClass,String staffId) {
		//取得值的实例
		SpBillInstance spBillInstance = getSpBillInstanceById(billConfigId,billInstanceId);
		//解析表单值实例转化成对象
		XmlValueClasses xmlValueClasses =spBillInstance.getXmlValueClasses();
		
		XmlValueClass xmlValueClass=xmlValueClasses.getValueClassByClassId(xmlBillClass.getId());
		
		XmlValueEntity xmlValueEntity;
		if(xmlValueClass==null){
			xmlValueClass=new XmlValueClass();
			xmlValueClass.setBillClassId( xmlBillClass.getId());
			xmlValueClass.setValueEntities(new ArrayList<XmlValueEntity>());
			if(xmlValueClasses.getValueClasses()==null){
				xmlValueClasses.setValueClasses(new ArrayList<XmlValueClass>());
			}
			xmlValueClasses.getValueClasses().add(xmlValueClass);
		}
		List<DynaBean> dynaBeanList = null;
		if(StringUtil.isEmpty(xmlBillClass.getClassId())&&xmlBillClass.getDefineCatch()!=null){
			SpBillConfig spBillConfig = spBillConfigService.getSpBillConfigByVersion(billConfigId, spBillInstance.getVersion());
			dynaBeanList=((ISpBillDefineCatchService)SpringHolder.getBean("spBillDefineCatchService")).getListToView(xmlBillClass.getFullDefineCatch(), spBillConfig, spBillInstance);
		}else{
			InfoClass infoClass=InfoClassCache.getInfoClass(xmlBillClass.getClassId());
			DynaBeanQuery query=new DynaBeanQuery(infoClass);
			if(StringUtil.isEmpty(staffId)){
				if(SessionFactory.getSession()!=null){
					query.setExpress("gh='"+SessionFactory.getUser().getYhm()+"'");
				}
			}else{
				query.setExpress("gh='"+staffId+"'");
			}
			dynaBeanList=dynaBeanBusiness.findList(query);
		}
		int i=0;
		for (DynaBean dynaBean : dynaBeanList) {
			xmlValueEntity=new XmlValueEntity();
			xmlValueEntity.setApprovStatus(ApprovStatus.PASS);
			xmlValueEntity.setInfoEntityId(dynaBean.getValue("globalid").toString());
			xmlValueEntity.setXmlBillClass(xmlBillClass);
			xmlValueEntity.setId(System.currentTimeMillis());
			if(xmlValueClass.getValueEntities()==null){
				xmlValueClass.setValueEntities(new ArrayList<XmlValueEntity>());
			}
			xmlValueEntity.setValueProperties(
					billValueUtil.getValuesAndCheck(billConfigId,spBillInstance.getVersion(),xmlBillClass.getId(), dynaBean));
			xmlValueClass.getValueEntities().add(xmlValueEntity);
			i++;
			if(i>=xmlBillClass.getCatchRecordNum()){
				break;
			}
		}
		
		spBillInstance.setXmlValueClasses(xmlValueClasses);
		
		modifySpBillInstance(spBillInstance);
		
		return xmlValueClass;
	}
	
	private void appendCatchRecords(String billConfigId,String billInstanceId,
			List<XmlBillClass> xmlBillClassList,String staffId) {
		
		for (XmlBillClass xmlBillClass : xmlBillClassList) {
			if(xmlBillClass.getCatchRecordNum()>0){
				appendCatchRecord(billConfigId, billInstanceId, xmlBillClass,staffId);
			}
		}
	}
	/**
	 * 新建一张表单
	 */
	@Override
	public SpBillInstance getNewSpBillInstance(String billConfigId) {
		//取得信息类配置对象
		List<XmlBillClass> xmlBillClassList=spBillConfigService.getXmlBillClassListLastVersion(billConfigId);
		SpBillInstance spBillInstance=new SpBillInstance();
		spBillInstance.setVersion(spBillConfigService.getLastVersion(billConfigId));
		spBillInstance.setStatus(BillInstanceStatus.INITIALIZE);
		spBillInstance.setBillConfigId(billConfigId);
		if(spBillInstance.getCreateDate()==null){
			spBillInstance.setCreateDate(new Date());
		}
		addSpBillInstance(spBillInstance);
		
		appendCatchRecords(billConfigId,spBillInstance.getId(), xmlBillClassList,null);
		
		spBillInstance=getSpBillInstanceById(billConfigId,spBillInstance.getId());
		
		return spBillInstance;
	}
	
	@Override
	public SpBillInstance getNewSpBillInstance(String billConfigId,
			String staffId) {
		//取得信息类配置对象
		List<XmlBillClass> xmlBillClassList=spBillConfigService.getXmlBillClassListLastVersion(billConfigId);
		SpBillInstance spBillInstance=new SpBillInstance();
		spBillInstance.setStatus(BillInstanceStatus.INITIALIZE);
		spBillInstance.setBillConfigId(billConfigId);
		spBillInstance.setVersion(spBillConfigService.getLastVersion(billConfigId));
		if(spBillInstance.getCreateDate()==null){
			spBillInstance.setCreateDate(new Date());
		}
		addSpBillInstance(spBillInstance);
		
		appendCatchRecords(billConfigId,spBillInstance.getId(), xmlBillClassList,staffId);
		
		spBillInstance=getSpBillInstanceById(billConfigId,spBillInstance.getId());
		
		return spBillInstance;
	}
	
	private XmlValueClass appendCatchRecordLocal(String billConfigId,String billInstanceId,
			XmlBillClass xmlBillClass,String guid) {
		//取得值的实例
		SpBillInstance spBillInstance = getSpBillInstanceById(billConfigId,billInstanceId);
		//解析表单值实例转化成对象
		XmlValueClasses xmlValueClasses =spBillInstance.getXmlValueClasses();
		
		XmlValueClass xmlValueClass=xmlValueClasses.getValueClassByClassId(xmlBillClass.getId());
		
		XmlValueEntity xmlValueEntity;
		if(xmlValueClass==null){
			xmlValueClass=new XmlValueClass();
			xmlValueClass.setBillClassId( xmlBillClass.getId());
			xmlValueClass.setValueEntities(new ArrayList<XmlValueEntity>());
			if(xmlValueClasses.getValueClasses()==null){
				xmlValueClasses.setValueClasses(new ArrayList<XmlValueClass>());
			}
			xmlValueClasses.getValueClasses().add(xmlValueClass);
		}
		InfoClass infoClass=InfoClassCache.getInfoClass(xmlBillClass.getClassId());
		DynaBeanQuery query=new DynaBeanQuery(infoClass);
		if(!StringUtil.isEmpty(guid)){
			query.setExpress("globalId='"+guid+"'");
			query.setHistory(true);
			List<DynaBean> dynaBeanList=dynaBeanBusiness.findList(query);
			int i=0;
			for (DynaBean dynaBean : dynaBeanList) {
				xmlValueEntity=new XmlValueEntity();
				xmlValueEntity.setEntityType(EntityType.SELFADD);
				xmlValueEntity.setApprovStatus(ApprovStatus.PASS);
				xmlValueEntity.setInfoEntityId(dynaBean.getValue("globalid").toString());
				xmlValueEntity.setXmlBillClass(xmlBillClass);
				xmlValueEntity.setId(System.currentTimeMillis());
				if(xmlValueClass.getValueEntities()==null){
					xmlValueClass.setValueEntities(new ArrayList<XmlValueEntity>());
				}
				xmlValueEntity.setValueProperties(
						billValueUtil.getValuesAndCheck(billConfigId,spBillInstance.getVersion(),xmlBillClass.getId(), dynaBean));
				xmlValueClass.getValueEntities().add(xmlValueEntity);
				i++;
				if(i>=xmlBillClass.getCatchRecordNum()){
					break;
				}
			}
			
			spBillInstance.setXmlValueClasses(xmlValueClasses);
			
			modifySpBillInstance(spBillInstance);
			
			return xmlValueClass;
		}else{
			if(xmlBillClass.getCatchRecordNum()>=1){
				xmlValueEntity=new XmlValueEntity();
				xmlValueEntity.setApprovStatus(ApprovStatus.PASS);
				xmlValueEntity.setEntityType(EntityType.SELFADD);
				xmlValueEntity.setInfoEntityId(null);
				xmlValueEntity.setId(System.currentTimeMillis());
				xmlValueEntity.setXmlBillClass(xmlBillClass);
				if(SessionFactory.getSession()!=null && xmlBillClass.getBillPropertyByFieldName("gh") != null){
					XmlValueProperty valueProperty=new XmlValueProperty();
					valueProperty.setBillPropertyId(xmlBillClass.getBillPropertyByFieldName("gh").getId());
					valueProperty.setValue(SessionFactory.getUser().getYhm());
					xmlValueEntity.getValueProperties().add(valueProperty);
				}
				xmlValueClass.getValueEntities().add(xmlValueEntity);
			}
			spBillInstance.setXmlValueClasses(xmlValueClasses);
			
			modifySpBillInstance(spBillInstance);
			return xmlValueClass;
		}
		
	}
	@Override
	public SpBillInstance getNewSpBillInstanceLocal(String billConfigId,
			String guid) {
		//取得信息类配置对象
		List<XmlBillClass> xmlBillClassList=spBillConfigService.getXmlBillClassListLastVersion(billConfigId);
		SpBillInstance spBillInstance=new SpBillInstance();
		spBillInstance.setStatus(BillInstanceStatus.INITIALIZE);
		spBillInstance.setBillConfigId(billConfigId);
		spBillInstance.setVersion(spBillConfigService.getLastVersion(billConfigId));
		if(spBillInstance.getCreateDate()==null){
			spBillInstance.setCreateDate(new Date());
		}
		addSpBillInstance(spBillInstance);
		
		appendCatchRecordLocal(billConfigId,spBillInstance.getId(), xmlBillClassList.get(0),guid);
		
		spBillInstance=getSpBillInstanceById(billConfigId,spBillInstance.getId());
		
		return spBillInstance;
	}

	@Override
	public void addSpBillInstance(SpBillInstance entity) {
		entity.setXmlValueClasses(new XmlValueClasses());
		SpBillConfig billConfig = spBillConfigService.getSpBillConfigById(entity.getBillConfigId());
		entity.setTableName(billConfig.getIdName());
		if(entity.getCreateDate()==null){
			entity.setCreateDate(new Date());
		}
		spBillInstanceDao.insert(entity);
	}

	@Override
	public void setSpBillInstanceCommitDate(SpBillInstance entity) {
		if(entity.getCommitDate()==null){
			entity.setCommitDate(new Date());
		}
		SpBillConfig billConfig = spBillConfigService.getSpBillConfigById(entity.getBillConfigId());
		entity.setTableName(billConfig.getIdName());
		spBillInstanceDao.update(entity);
	}

	@Override
	public void modifySpBillInstance(SpBillInstance entity) {
		SpBillConfig billConfig = spBillConfigService.getSpBillConfigByVersion(entity.getBillConfigId(), entity.getVersion());
		entity.setTableName(billConfig.getIdName());
		spBillInstanceDao.update(entity);
	}

	@Override
	public void removeSpBillInstance(String billConfigId,String instanceId) {
		SpBillInstance query = new SpBillInstance();
		query.setId(instanceId);
		SpBillConfig billConfig = spBillConfigService.getSpBillConfigById(billConfigId);
		query.setTableName(billConfig.getIdName());
		spBillInstanceDao.delete(query);
		spBillInstanceLogService.deleteByInstanceId(instanceId);
	}

	@Override
	public List<SpBillInstance> getSpBillInstanceList(SpBillInstance query) {
		SpBillConfig billConfig = spBillConfigService.getSpBillConfigById(query.getBillConfigId());
		query.setTableName(billConfig.getIdName());
		return spBillInstanceDao.findList(query);
	}
	@Override
	public List<SpBillInstance> getSpBillInstanceListByIds(List<String> ids) {
		SpBillInstance query = new SpBillInstance();
		query.setIds(ids);
		return spBillInstanceDao.findListByIds(query);
	}

	@Override
	public SpBillInstance getSpBillInstanceById(String billConfigId,String instanceId) {
		SpBillInstance query = new SpBillInstance();
		query.setId(instanceId);
		SpBillConfig billConfig = spBillConfigService.getSpBillConfigById(billConfigId);
		query.setTableName(billConfig.getIdName());
		return spBillInstanceDao.findById(query);
	}

	/**
	 * 更新到数据库
	 * @param spBillInstance
	 * @param xmlValueClasses
	 */
	private void updateBillInstance(SpBillInstance entity,
			XmlValueClasses xmlValueClasses) {
		//反解析成字串
		entity.setXmlValueClasses(xmlValueClasses);
		
		SpBillConfig billConfig = spBillConfigService.getSpBillConfigById(entity.getBillConfigId());
		entity.setTableName(billConfig.getIdName());
		//更新到数据库
		spBillInstanceDao.update(entity);
	}

	@Override
	public void addXmlValueInstances(String billConfigId,String billInstanceId, Long billClassId,
			List<XmlValueProperty> values) {
		XmlValueEntity xmlValueEntity = new XmlValueEntity();
		xmlValueEntity.setId(System.currentTimeMillis());
		addXmlValueInstances(billConfigId,billInstanceId, billClassId,
				 values,xmlValueEntity);
	}

	@Override
	public void addXmlValueInstances(String billConfigId,String billInstanceId, Long billClassId, 
			List<XmlValueProperty> values, XmlValueEntity xmlValueEntity) {
		//取得值的实例
		SpBillInstance spBillInstance = getSpBillInstanceById(billConfigId,billInstanceId);
		//取得信息类配置对象
		XmlBillClass xmlBillClass=spBillConfigService.getXmlBillClassByVersion(spBillInstance.getBillConfigId(),spBillInstance.getVersion(), billClassId);
		if(!xmlBillClass.getAppend()&&!xmlBillClass.getChoice()){
			throw new RuntimeException("禁止增加");
		}
		//解析表单值实例转化成对象
		XmlValueClasses xmlValueClasses =  spBillInstance.getXmlValueClasses();
		//空则初始化
		if (xmlValueClasses == null) {
			xmlValueClasses=new XmlValueClasses();
		}
		//空则初始化
		if (xmlValueClasses.getValueClasses() == null) {
			xmlValueClasses.setValueClasses(new ArrayList<XmlValueClass>());
		}
		//取得billClassId对应的信息类值对象
		XmlValueClass xmlValueClass = xmlValueClasses.getValueClassByClassId(billClassId);
		//空则初始化
		if (xmlValueClass == null) {
			xmlValueClass = new XmlValueClass();
			xmlValueClass.setBillClassId(billClassId);
			//加入到信息类值对象序列
			xmlValueClasses.getValueClasses().add(xmlValueClass);
		}
		//空则初始化
		if(xmlValueClass.getValueEntities()==null){
			xmlValueClass.setValueEntities(new ArrayList<XmlValueEntity>());
		}
		//判断限制条数最大限制
		if(xmlBillClass.getMaxLength().compareTo(xmlValueClass.getValueEntities().size())==0){
			throw new RuntimeException("超出最大条数，不能再添加");
		}
		
		//初始化值实例
		xmlValueEntity.setValueProperties(values);
		
		if(xmlBillClass.getAppend()){
			if(StringUtil.isEmpty(xmlValueEntity.getInfoEntityId())){
				xmlValueEntity.setEntityType(EntityType.SELFADD);
			}else{
				xmlValueEntity.setEntityType(EntityType.INFOCLASS);
			}
		}
		
		xmlValueClass.getValueEntities().add(xmlValueEntity);
		//更新到数据库
		updateBillInstance(spBillInstance, xmlValueClasses);
	}

	@Override
	public void modifyXmlValueInstance(String billConfigId,String billInstanceId, Long billClassId,
			Long entityId, List<XmlValueProperty> values) {
		//取得值的实例
		SpBillInstance spBillInstance = getSpBillInstanceById(billConfigId,billInstanceId);
		//解析表单值实例转化成对象
		XmlValueClasses xmlValueClasses =spBillInstance.getXmlValueClasses();
		//空则初始化序列
		if (xmlValueClasses.getValueClasses() == null) {
			xmlValueClasses.setValueClasses(new ArrayList<XmlValueClass>());
		}
		//取得billClassId对应的信息类值对象
		XmlValueClass xmlValueClass = xmlValueClasses.getValueClassByClassId(billClassId);
		//取得entityId对应的信息类值对象实例
		XmlValueEntity xmlValueEntity = xmlValueClass.getValueEntityById(entityId);
		//判断来源，控制修改
		if(xmlValueEntity.getEntityType()==EntityType.INFOCLASS){
			throw new RuntimeException("抓取自信息类，禁止修改");
		}
		//判空
		if (xmlValueEntity.getValueProperties() == null) {
			xmlValueEntity.setValueProperties(new ArrayList<XmlValueProperty>());
		}
		//空则初始化序列
		xmlValueEntity.setValueProperties(values);
		//更新到数据库
		updateBillInstance(spBillInstance, xmlValueClasses);

	}
	@Override
	public void modifyXmlValueInstanceLocal(String billConfigId,String billInstanceId, Long billClassId,
			Long entityId, List<XmlValueProperty> values) {
		//取得值的实例
		SpBillInstance spBillInstance = getSpBillInstanceById(billConfigId,billInstanceId);
		//解析表单值实例转化成对象
		XmlValueClasses xmlValueClasses =spBillInstance.getXmlValueClasses();
		//空则初始化序列
		if (xmlValueClasses.getValueClasses() == null) {
			xmlValueClasses.setValueClasses(new ArrayList<XmlValueClass>());
		}
		//取得billClassId对应的信息类值对象
		XmlValueClass xmlValueClass = xmlValueClasses.getValueClassByClassId(billClassId);
		//取得entityId对应的信息类值对象实例
		XmlValueEntity xmlValueEntity = xmlValueClass.getValueEntityById(entityId);
		//判空
		if (xmlValueEntity.getValueProperties() == null) {
			xmlValueEntity.setValueProperties(new ArrayList<XmlValueProperty>());
		}
		//空则初始化序列
		xmlValueEntity.setValueProperties(values);
		//更新到数据库
		updateBillInstance(spBillInstance, xmlValueClasses);

	}

	@Override
	public void removeXmlValueInstance(String billConfigId,String billInstanceId, Long billClassId,
			Long entityId) {
		//取得值的实例
		SpBillInstance spBillInstance = getSpBillInstanceById(billConfigId,billInstanceId);
		//解析表单值实例转化成对象
		XmlValueClasses xmlValueClasses = spBillInstance.getXmlValueClasses();
		XmlValueClass xmlValueClass = xmlValueClasses.getValueClassByClassId(billClassId);
		XmlValueEntity xmlValueEntity = xmlValueClass.getValueEntityById(entityId);
		if(xmlValueEntity != null && xmlValueEntity.getValueProperties()!=null){
			for(XmlValueProperty xmlValueProperty:xmlValueEntity.getValueProperties()){
				XmlBillProperty xmlBillProperty=
					spBillConfigService.getXmlBillPropertyByVersion(spBillInstance.getBillConfigId(),spBillInstance.getVersion(),
							billClassId, xmlValueProperty.getBillPropertyId());
				if(Type.FILE.equals(xmlBillProperty.getFieldType())){
					AttachementUtil.removeAttachement(xmlValueProperty.getValue());
				}
				if(Type.IMAGE.equals(xmlBillProperty.getFieldType())||
						Type.PHOTO.equals(xmlBillProperty.getFieldType())){
					ImageDBUtil.deleteFileToDB(xmlValueProperty.getValue());
				}
			}
		}
		xmlValueClass.getValueEntities().remove(xmlValueEntity);
		
		//更新到数据库
		updateBillInstance(spBillInstance, xmlValueClasses);

	}

	@Override
	public List<XmlValueClass> getXmlValueClassList(String billConfigId,String billInstanceId) {
		//取得值的实例
		SpBillInstance spBillInstance = getSpBillInstanceById(billConfigId,billInstanceId);
		//解析表单值实例转化成对象
		XmlValueClasses xmlValueClasses = spBillInstance.getXmlValueClasses();
		//判空
		if(xmlValueClasses.getValueClasses()==null){
			return xmlValueClasses.getValueClasses();
		}
		for (XmlValueClass xmlValueClass : xmlValueClasses.getValueClasses()) {
			//取得billConfigId，billClassId对应的信息类对象
			XmlBillClass xmlBillClass = spBillConfigService.getXmlBillClassByVersion(spBillInstance.getBillConfigId(),spBillInstance.getVersion(),
					xmlValueClass.getBillClassId());
			//判空
			if(xmlValueClass.getValueEntities()==null){
				continue;
			}
			if(xmlBillClass==null){
				continue;
			}
			XmlBillProperty xmlBillProperty;
			for (XmlValueEntity xmlValueEntity : xmlValueClass.getValueEntities()) {
				xmlValueEntity.setXmlBillClass(xmlBillClass);
				//判空
				if(xmlValueEntity.getValueProperties()==null){
					continue;
				}
				for (XmlValueProperty xmlValueProperty : xmlValueEntity.getValueProperties()) {
					xmlBillProperty = xmlBillClass.getBillPropertyById(xmlValueProperty.getBillPropertyId());
					if(xmlBillProperty!=null){
						xmlValueProperty.setBillProperty(xmlBillProperty);
					}
				}
			}
		}
		return xmlValueClasses.getValueClasses();
	}

	@Override
	public XmlValueClass getXmlValueClassById(String billConfigId,String billInstanceId, Long billClassId) {
		//取得值的实例
		SpBillInstance spBillInstance = getSpBillInstanceById(billConfigId,billInstanceId);
		//解析表单值实例转化成对象
		XmlValueClasses xmlValueClasses = spBillInstance.getXmlValueClasses();
		//取得billConfigId，billClassId对应的信息类对象
		XmlBillClass xmlBillClass = spBillConfigService.getXmlBillClassByVersion(spBillInstance.getBillConfigId(),spBillInstance.getVersion(), billClassId);
		
		XmlValueClass xmlValueClass = xmlValueClasses.getValueClassByClassId(billClassId);
		//判空
		if(xmlValueClass==null||xmlValueClass.getValueEntities()==null){
			return xmlValueClass;
		}
		XmlBillProperty xmlBillProperty;
		for (XmlValueEntity xmlValueEntity : xmlValueClass.getValueEntities()) {
			xmlValueEntity.setXmlBillClass(xmlBillClass);
			//判空
			if(xmlValueEntity.getValueProperties()==null){
				continue;
			}
			for (XmlValueProperty xmlValueProperty : xmlValueEntity.getValueProperties()) {
				xmlBillProperty = xmlBillClass.getBillPropertyById(xmlValueProperty.getBillPropertyId());
				if(xmlBillProperty!=null){
					xmlValueProperty.setBillProperty(xmlBillProperty);
				}
			}
		}
		
		return xmlValueClass;
	}

	@Override
	public List<XmlValueEntity> getXmlValueEntityList(String billConfigId,String billInstanceId, Long billClassId) {
		//取得值的实例
		SpBillInstance spBillInstance = getSpBillInstanceById(billConfigId,billInstanceId);
		//解析表单值实例转化成对象
		XmlValueClasses xmlValueClasses = spBillInstance.getXmlValueClasses();
		//取得billConfigId，billClassId对应的信息类对象
		XmlBillClass xmlBillClass = spBillConfigService.getXmlBillClassByVersion(spBillInstance.getBillConfigId(),spBillInstance.getVersion(), billClassId);
		//取得billClassId对应的信息类值对象
		XmlValueClass xmlValueClass = xmlValueClasses.getValueClassByClassId(billClassId);
		//判空
		if(xmlValueClass==null||xmlValueClass.getValueEntities()==null){
			return new ArrayList<XmlValueEntity>();
		}
		//信息类属性配置注入
		XmlBillProperty xmlBillProperty;
		for (XmlValueEntity xmlValueEntity : xmlValueClass.getValueEntities()) {
			xmlValueEntity.setXmlBillClass(xmlBillClass);
			//判空
			if(xmlValueEntity.getValueProperties()==null){
				continue;
			}
			for (XmlValueProperty xmlValueProperty : xmlValueEntity.getValueProperties()) {
				xmlBillProperty = xmlBillClass.getBillPropertyById(xmlValueProperty.getBillPropertyId());
				if(xmlBillProperty!=null){
					xmlValueProperty.setBillProperty(xmlBillProperty);
				}
			}
		}
		//返回处理对象
		return xmlValueClass.getValueEntities();
	}

	@Override
	public XmlValueEntity getXmlValueEntityById(String billConfigId,String billInstanceId, Long billClassId, Long entityId) {

		//取得值的实例
		SpBillInstance spBillInstance = getSpBillInstanceById(billConfigId,billInstanceId);
		//解析表单值实例转化成对象
		XmlValueClasses xmlValueClasses = spBillInstance.getXmlValueClasses();
		//取得billConfigId，billClassId对应的信息类对象
		XmlBillClass xmlBillClass = spBillConfigService.getXmlBillClassByVersion(spBillInstance.getBillConfigId(),spBillInstance.getVersion(), billClassId);
		//取得billClassId对应的信息类值对象
		XmlValueClass xmlValueClass = xmlValueClasses.getValueClassByClassId(billClassId);
		//判空
		if(xmlValueClass==null||xmlValueClass.getValueEntities()==null){
			return null;
		}
		XmlValueEntity xmlValueEntity=xmlValueClass.getValueEntityById(entityId);
		//判空
		if(xmlValueEntity==null){
			return null;
		}
		//表单信息类注入
		xmlValueEntity.setXmlBillClass(xmlBillClass);
		
		if(xmlValueEntity.getValueProperties()==null){
			//返回处理对象
			return xmlValueEntity;
		}
	
		//信息类属性配置注入
		XmlBillProperty xmlBillProperty;
		for (XmlValueProperty xmlValueProperty : xmlValueEntity.getValueProperties()) {
			xmlBillProperty = xmlBillClass.getBillPropertyById(xmlValueProperty.getBillPropertyId());
			if(xmlBillProperty!=null){
				xmlValueProperty.setBillProperty(xmlBillProperty);
			}
		}
		//返回处理对象
		return xmlValueEntity;
	}

	@Override
	public XmlValueEntity getXmlValueEntityByInfoEntityId(String billConfigId,String billInstanceId,
			Long billClassId, String infoEntityId) {
		//取得值的实例
		SpBillInstance spBillInstance = getSpBillInstanceById(billConfigId,billInstanceId);
		//解析表单值实例转化成对象
		XmlValueClasses xmlValueClasses = spBillInstance.getXmlValueClasses();
		//取得billConfigId，billClassId对应的信息类对象
		XmlBillClass xmlBillClass = spBillConfigService.getXmlBillClassByVersion(spBillInstance.getBillConfigId(),spBillInstance.getVersion(), billClassId);
		//取得billClassId对应的信息类值对象
		XmlValueClass xmlValueClass = xmlValueClasses.getValueClassByClassId(billClassId);
		//判空
		if(xmlValueClass==null||xmlValueClass.getValueEntities()==null){
			return null;
		}
		XmlValueEntity xmlValueEntity=xmlValueClass.getValueEntityByInfoEntityId(infoEntityId);
		//判空
		if(xmlValueEntity==null){
			return null;
		}
		//表单信息类注入
		xmlValueEntity.setXmlBillClass(xmlBillClass);
		if(xmlValueEntity.getValueProperties()==null){
			//返回处理对象
			return xmlValueEntity;
		}
		//信息类属性配置注入
		XmlBillProperty xmlBillProperty;
		for (XmlValueProperty xmlValueProperty : xmlValueEntity.getValueProperties()) {
			xmlBillProperty = xmlBillClass.getBillPropertyById(xmlValueProperty.getBillPropertyId());
			if(xmlBillProperty!=null){
				xmlValueProperty.setBillProperty(xmlBillProperty);
			}
		}
		//返回处理对象
		return xmlValueEntity;
	}

	@Override
	public SpBillInstance doCopySpBillInstance(String billConfigId,
			String billInstanceId) {
		SpBillInstance entity = getSpBillInstanceById(billConfigId, billInstanceId);
		SpBillConfig billConfig = spBillConfigService.getSpBillConfigById(billConfigId);
		entity.setTableName(billConfig.getIdName());
		entity.setCreateDate(new Date());
		spBillInstanceDao.insert(entity);
		return entity;
	}

}
