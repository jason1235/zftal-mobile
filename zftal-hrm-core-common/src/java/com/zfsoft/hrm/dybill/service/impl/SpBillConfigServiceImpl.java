package com.zfsoft.hrm.dybill.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.table.entities.Column;
import com.zfsoft.hrm.baseinfo.table.entities.Table;
import com.zfsoft.hrm.baseinfo.table.service.svcinterface.ITableService;
import com.zfsoft.hrm.dybill.dao.ISpBillConfigDao;
import com.zfsoft.hrm.dybill.dao.ISpBillConfigVersionDao;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillConfigVersion;
import com.zfsoft.hrm.dybill.entity.SpBillQuery;
import com.zfsoft.hrm.dybill.enums.BillConfigStatus;
import com.zfsoft.hrm.dybill.enums.ModeType;
import com.zfsoft.hrm.dybill.enums.PrivilegeType;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillClasses;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.util.base.StringUtil;

/**
 * 审批表单配置
 * @author Patrick Shen
 */
public class SpBillConfigServiceImpl implements ISpBillConfigService {

	private ISpBillConfigDao spBillConfigDao;
	private ISpBillConfigVersionDao spBillConfigVersionDao;
	private ITableService tableService;

	
	@Override
	public Integer getLastVersion(String billConfigId) {
		SpBillConfigVersion query=new SpBillConfigVersion();
		query.setBillConfigId(billConfigId);
		query.setVersion(null);
		List<SpBillConfigVersion> qlist=spBillConfigVersionDao.findList(query);
		SpBillConfigVersion maxVersion=new SpBillConfigVersion();
		
		for (SpBillConfigVersion spBillConfigVersion : qlist) {
			if(maxVersion.getVersion().compareTo(spBillConfigVersion.getVersion())<0){
				maxVersion=spBillConfigVersion;
			}
		}
		if(qlist.size()<=0){
			throw new RuntimeException("尚未有发布的版本");
		}
		return maxVersion.getVersion();
	}
	
	@Override
	public List<Integer> getSpBillConfigVersionList(String billConfigId) {
		SpBillConfigVersion query=new SpBillConfigVersion();
		query.setBillConfigId(billConfigId);
		query.setVersion(null);
		List<SpBillConfigVersion> qlist=spBillConfigVersionDao.findList(query);
		List<Integer> versions=new ArrayList<Integer>();
		for (SpBillConfigVersion spBillConfigVersion : qlist) {
			versions.add(spBillConfigVersion.getVersion());
		}
		Collections.sort(versions);
		return versions;
	}
	@Override
	public SpBillConfig getSpBillConfigLastVersion(String billConfigId) {
		SpBillConfigVersion query=new SpBillConfigVersion();
		query.setBillConfigId(billConfigId);
		query.setVersion(null);
		List<SpBillConfigVersion> qlist=spBillConfigVersionDao.findList(query);
		SpBillConfigVersion maxVersion=new SpBillConfigVersion();
		
		for (SpBillConfigVersion spBillConfigVersion : qlist) {
			if(maxVersion.getVersion().compareTo(spBillConfigVersion.getVersion())<0){
				maxVersion=spBillConfigVersion;
			}
		}
		if(qlist.size()<=0){
			throw new RuntimeException("尚未有发布的版本");
		}
		SpBillConfig config=getSpBillConfigById(billConfigId);
		config.setContent(maxVersion.getContent());
		return config;
	}

	@Override
	public SpBillConfig getSpBillConfigByVersion(String billConfigId,
			Integer version) {
		SpBillConfigVersion query=new SpBillConfigVersion();
		query.setBillConfigId(billConfigId);
		query.setVersion(version);
		List<SpBillConfigVersion> qlist=spBillConfigVersionDao.findList(query);
		if(qlist.size()<=0){
			throw new RuntimeException("未找到对应的版本");
		}
		
		SpBillConfigVersion versionResult = null;
		
		if(version==null){
			versionResult=qlist.get(0);
		}else{
			for (SpBillConfigVersion spBillConfigVersion : qlist) {
				if(spBillConfigVersion.getVersion().equals(version)){
					versionResult=spBillConfigVersion;
				}
			}
			
			if(versionResult==null){
				throw new RuntimeException("未找到对应的版本");
			}
		}
		
		SpBillConfig config=getSpBillConfigById(billConfigId);
		config.setContent(versionResult.getContent());
		return config;
	}

	@Override
	public void addSpBillConfigVersion(SpBillConfigVersion entity) {
		SpBillConfigVersion query=new SpBillConfigVersion();
		query.setBillConfigId(entity.getBillConfigId());
		query.setVersion(null);
		List<SpBillConfigVersion> qlist=spBillConfigVersionDao.findList(query);
		if(qlist.size()<=0){
			entity.setVersion(1);
		}else{
			entity.setVersion(qlist.size()+1);
		}
		entity.setCreateDate(new Date());
		SpBillConfig config=getSpBillConfigById(entity.getBillConfigId());
		config.setStatus(BillConfigStatus.USING);
		spBillConfigDao.update(config);
		entity.setContent(config.getContent());
		//冗余存储实例表表名（防止后面增加中途修改存储的实例表时造成数据丢失）
		entity.setIdName(config.getIdName());
		spBillConfigVersionDao.insert(entity);
	}

	@Override
	public void addSpBillConfig(SpBillConfig entity) {
		if(entity.getCreateDate()==null){
			entity.setCreateDate(new Date());
		}
		spBillConfigDao.insert(entity);
		//如果字段实例存储表不为空 则创建相应的实例存储表
		if (!StringUtil.isEmpty(entity.getIdName())) {
			Table table = getSpBillTable(entity);
			if (!tableService.isExist(table.getTableName())) {
				tableService.create(table);
			}
		}
	}
	/**
	 * 创建实例存储表对象
	 * @param entity
	 * @return
	 */
	private Table getSpBillTable(SpBillConfig entity){
		//表名自动加前缀 BILL_
		Table table = new Table("bill_"+entity.getIdName(), "自定义表单_"+entity.getName());
		List<Column> columns = new ArrayList<Column>(0);
		//设置实例字段属性
		columns.add( new Column("ID", "表单实例编号", "VARCHAR2(32)", table.getTableName() ) );
		columns.add( new Column("STATUS", "表单状态", "VARCHAR2(32)", table.getTableName() ) );
		columns.add( new Column("CONTENT", "表单内容", "CLOB", table.getTableName() ) );
		columns.add( new Column("CREATE_DATE", "创建时间", "DATE", table.getTableName() ) );
		columns.add( new Column("COMMIT_DATE", "提交时间", "DATE", table.getTableName() ) );
		columns.add( new Column("BILL_CONFIG_ID", "表单配置编号", "VARCHAR2(32)", table.getTableName() ) );
		columns.add( new Column("VERSION", "所对应配置的版本号", "NUMBER", table.getTableName() ) );
		table.setColumns(columns);
		return table;
	}

	@Override
	public void modifySpBillConfig(SpBillConfig entity) {
		spBillConfigDao.update(entity);
	}

	@Override
	public void copySpBillConfig(SpBillConfig spBillConfig) {
		SpBillConfig copyTarget=getSpBillConfigById(spBillConfig.getId());
		spBillConfig.setContent(copyTarget.getContent());
		spBillConfigDao.insert(spBillConfig);
	}

	@Override
	public void removeSpBillConfig(String id) {
		SpBillConfig query = new SpBillConfig();
		query.setId(id);
		query.setStatus(BillConfigStatus.UNUSE);
		spBillConfigDao.update(query);
	}

	@Override
	public List<SpBillConfig> getSpBillConfigList(SpBillConfig entity) {
		return spBillConfigDao.findList(entity);
	}

	@Override
	public SpBillConfig getSpBillConfigById(String id) {
		SpBillConfig query = new SpBillConfig();
		query.setId(id);
		return spBillConfigDao.findById(query);
	}

	@Override
	public void addXmlBillClass(String billConfigId, XmlBillClass xmlBillClass) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlBillClasses = spBillConfig.getXmlBillClasses();
		
		if(idCheckRepeat(xmlBillClasses,xmlBillClass)){
			throw new RuntimeException("标示名重复");
		}
		//解析失败则新建xml映射对象
		if(xmlBillClasses==null){
			xmlBillClasses = new XmlBillClasses();
		}
		//判断是否有信息类集合。没有则初始
		if (xmlBillClasses.getBillClasses() == null) {
			xmlBillClasses.setBillClasses(new ArrayList<XmlBillClass>());
		}
		
		//对新增的信息类设置ID
		xmlBillClass.setId(System.currentTimeMillis() );
		//如果property.getPropertyId()不空则注入信息类属性
		if(!StringUtil.isEmpty(xmlBillClass.getClassId())){
			xmlBillClass.setInfoclass(
					InfoClassCache.getInfoClass(xmlBillClass.getClassId()));
		}
		//追加新的信息类
		xmlBillClasses.getBillClasses().add(xmlBillClass);
		//更新到数据库
		updateBillConfig(spBillConfig, xmlBillClasses);
		
	}

	@Override
	public void saveCopyXmlBillClass(String id, String copyBillConfigId,
			Long copyBillClassId,String idName) {
		SpBillConfig targetSpbillconfig=this.getSpBillConfigById(id);
		XmlBillClass copySpBillClass=this.getXmlBillClassById(copyBillConfigId, copyBillClassId);
		XmlBillClasses xmlBillClasses=targetSpbillconfig.getXmlBillClasses();
		copySpBillClass.setId(System.currentTimeMillis());
		copySpBillClass.setIdentityName(idName);
		if(idCheckRepeat(xmlBillClasses,copySpBillClass)){
			throw new RuntimeException("标示名重复");
		}
		if(xmlBillClasses==null){
			xmlBillClasses=new XmlBillClasses();
		}
		if(xmlBillClasses.getBillClasses()==null){
			xmlBillClasses.setBillClasses(new ArrayList<XmlBillClass>());
		}
		xmlBillClasses.getBillClasses().add(copySpBillClass);
		targetSpbillconfig.setXmlBillClasses(xmlBillClasses);
		this.modifySpBillConfig(targetSpbillconfig);
	}

	@Override
	public void modifyXmlBillClass(String billConfigId,
			XmlBillClass xmlBillclass) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlBillClasses = spBillConfig.getXmlBillClasses();
		XmlBillClass oldXmlBillClass =xmlBillClasses.getBillClassById(xmlBillclass.getId());
		xmlBillclass.setBillPropertys(oldXmlBillClass.getBillPropertys());
		//定位配置内容
		int index = xmlBillClasses.getBillClasses().indexOf(oldXmlBillClass);
		//向定位处新增配置项
		xmlBillClasses.getBillClasses().set(index, xmlBillclass);
		//更新到数据库
		updateBillConfig(spBillConfig, xmlBillClasses);

	}
	@Override
	public void modifyXmlBillClassFull(String billConfigId,
			XmlBillClass xmlBillclass) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlBillClasses = spBillConfig.getXmlBillClasses();
		XmlBillClass oldXmlBillClass =xmlBillClasses.getBillClassById(xmlBillclass.getId());
		//定位配置内容
		int index = xmlBillClasses.getBillClasses().indexOf(oldXmlBillClass);
		//向定位处新增配置项
		xmlBillClasses.getBillClasses().set(index, xmlBillclass);
		//更新到数据库
		updateBillConfig(spBillConfig, xmlBillClasses);

	}

	@Override
	public void removeXmlBillClass(String billConfigId,
			Long billClassId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlBillClasses = spBillConfig.getXmlBillClasses();
		//定位配置内容
		int index = xmlBillClasses.getBillClasses().indexOf(
				xmlBillClasses.getBillClassById(billClassId));
		//删除被定位配置项
		xmlBillClasses.getBillClasses().remove(index);
		//更新到数据库
		updateBillConfig(spBillConfig, xmlBillClasses);
	}

	@Override
	public XmlBillClass getXmlBillClassById(String billConfigId,
			Long billClassId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlBillClasses = spBillConfig.getXmlBillClasses();
		//取得billClassId对应信息类
		XmlBillClass xmlBillclass = xmlBillClasses
				.getBillClassById(billClassId);
		//返回处理信息类
		return xmlBillclass;
	}
	

	@Override
	public XmlBillClass getXmlBillClassByVersion(String billConfigId,
			Integer version, Long billClassId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigByVersion(billConfigId, version);
		//解析配置内容转化成对象
		XmlBillClasses xmlBillClasses = spBillConfig.getXmlBillClasses();
		//取得billClassId对应信息类
		XmlBillClass xmlBillclass = xmlBillClasses
				.getBillClassById(billClassId);
		//返回处理信息类
		return xmlBillclass;
	}

	@Override
	public XmlBillClass getXmlBillClassLastVersion(String billConfigId,
			Long billClassId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigLastVersion(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlBillClasses = spBillConfig.getXmlBillClasses();
		//取得billClassId对应信息类
		XmlBillClass xmlBillclass = xmlBillClasses
				.getBillClassById(billClassId);
		//返回处理信息类
		return xmlBillclass;
	}

	@Override
	public List<XmlBillClass> getXmlBillClassList(String billConfigId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlbillClasses = spBillConfig.getXmlBillClasses();
		if(xmlbillClasses==null){
			return new ArrayList<XmlBillClass>();
		}
		//无处理返回
		if(xmlbillClasses.getBillClasses()==null){
			return null;
		}
		//处理和返回
		return xmlbillClasses.getBillClasses();
	}
	
	@Override
	public List<XmlBillClass> getXmlBillClassListLastVersion(String billConfigId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigLastVersion(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlbillClasses = spBillConfig.getXmlBillClasses();
		if(xmlbillClasses==null){
			return new ArrayList<XmlBillClass>();
		}
		//无处理返回
		if(xmlbillClasses.getBillClasses()==null){
			return null;
		}
		//处理和返回
		return xmlbillClasses.getBillClasses();
	}

	@Override
	public List<XmlBillClass> getXmlBillClassListByVersion(String billConfigId,
			Integer version) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigByVersion(billConfigId,version);
		//解析配置内容转化成对象
		XmlBillClasses xmlbillClasses = spBillConfig.getXmlBillClasses();
		if(xmlbillClasses==null){
			return new ArrayList<XmlBillClass>();
		}
		//无处理返回
		if(xmlbillClasses.getBillClasses()==null){
			return null;
		}
		//处理和返回
		return xmlbillClasses.getBillClasses();
	}

	@Override
	public List<XmlBillClass> getXmlBillClassListByVersion(String billConfigId,
			Integer version, String privilegeExpression) {
		if(StringUtil.isEmpty(privilegeExpression)){
			return getXmlBillClassListByVersion(billConfigId,version);
		}
		List<XmlBillClass> billClasses=new ArrayList<XmlBillClass>();
		
		Map<Long,XmlBillClass> billClassesCache=new LinkedHashMap<Long,XmlBillClass>();
		
		ModeType modeType = null;
		String[] billClassPrivileges = null;
		privilegeExpression=privilegeExpression.toUpperCase();
		if(privilegeExpression.startsWith(ModeType.ALL_SEARCH.toString())){
			modeType=ModeType.ALL_SEARCH;
		}else if(privilegeExpression.startsWith(ModeType.SEARCH.toString())){
			modeType=ModeType.SEARCH;
			billClassPrivileges=privilegeExpression.replace("SEARCH[", "").replace("]", "").split(",");
		}else  if(privilegeExpression.startsWith(ModeType.NORMAL.toString())){
			modeType=ModeType.NORMAL;
			billClassPrivileges=privilegeExpression.replace("NORMAL[", "").replace("]", "").split(",");
		}else{
			throw new RuntimeException("模式串配置错误！");
		}
		
		if((modeType!=ModeType.ALL_SEARCH)&&(billClassPrivileges==null||billClassPrivileges.length==0)){
			return billClasses;
		}
		if(modeType!=ModeType.ALL_SEARCH &&(billClassPrivileges.length == 1 && "".equals(billClassPrivileges[0].trim()))){
			return billClasses;
		}
		
		for (XmlBillClass xmlBillClass : getXmlBillClassListByVersion(billConfigId, version)) {
			billClassesCache.put(xmlBillClass.getId(), xmlBillClass);
		}
		
		if(modeType==ModeType.ALL_SEARCH){
			for(XmlBillClass xmlBillClass:billClassesCache.values()){
				xmlBillClass.setPrivilegeType(PrivilegeType.SEARCH);
				billClasses.add(xmlBillClass);
			}
			return billClasses;
		}
		
		return getPrivilegeBillClass(billClassesCache, billClassPrivileges, modeType);
	}

	@Override
	public List<XmlBillClass> getXmlBillClassListLastVersion(String billConfigId,String privilegeExpression) {
		
		if(StringUtil.isEmpty(privilegeExpression)){
			return getXmlBillClassListLastVersion(billConfigId);
		}
		List<XmlBillClass> billClasses=new ArrayList<XmlBillClass>();
		
		Map<Long,XmlBillClass> billClassesCache=new LinkedHashMap<Long,XmlBillClass>();
		
		ModeType modeType = null;
		String[] billClassPrivileges = null;
		privilegeExpression=privilegeExpression.toUpperCase();
		if(privilegeExpression.startsWith(ModeType.ALL_SEARCH.toString())){
			modeType=ModeType.ALL_SEARCH;
		}else if(privilegeExpression.startsWith(ModeType.SEARCH.toString())){
			modeType=ModeType.SEARCH;
			billClassPrivileges=privilegeExpression.replace("SEARCH[", "").replace("]", "").split(",");
		}else  if(privilegeExpression.startsWith(ModeType.NORMAL.toString())){
			modeType=ModeType.NORMAL;
			billClassPrivileges=privilegeExpression.replace("NORMAL[", "").replace("]", "").split(",");
		}else{
			throw new RuntimeException("模式串配置错误！");
		}
		
		if((modeType!=ModeType.ALL_SEARCH)&&(billClassPrivileges==null||billClassPrivileges.length==0)){
			return billClasses;
		}
		if(modeType!=ModeType.ALL_SEARCH &&(billClassPrivileges.length == 1 && "".equals(billClassPrivileges[0].trim()))){
			return billClasses;
		}
		
		for (XmlBillClass xmlBillClass : getXmlBillClassList(billConfigId)) {
			billClassesCache.put(xmlBillClass.getId(), xmlBillClass);
		}
		
		if(modeType==ModeType.ALL_SEARCH){
			for(XmlBillClass xmlBillClass:billClassesCache.values()){
				xmlBillClass.setPrivilegeType(PrivilegeType.SEARCH);
				billClasses.add(xmlBillClass);
			}
			return billClasses;
		}
		
		return getPrivilegeBillClass(billClassesCache, billClassPrivileges, modeType);
	}
	private boolean idCheckRepeat(XmlBillClasses xmlBillClasses,XmlBillClass newSpBillClass){
		if(xmlBillClasses==null||xmlBillClasses.getBillClasses()==null){
			return false;
		}
		for(XmlBillClass copBillClass:xmlBillClasses.getBillClasses()){
			if(copBillClass.getIdentityName().equals(newSpBillClass.getIdentityName())){
				return true;
			}
		}
		return false;
	}
	
	private boolean idCheckRepeat(List<XmlBillProperty> billPropertys,XmlBillProperty newBillProperty){
		if(billPropertys==null||billPropertys.size()==0){
			return false;
		}
		for(XmlBillProperty copXmlBillProperty:billPropertys){
			if(copXmlBillProperty.getFieldName().equals(newBillProperty.getFieldName())){
				return true;
			}
		}
		return false;
	}
	
	private List<XmlBillClass> getPrivilegeBillClass(Map<Long,XmlBillClass> billClassesCache,String[] billClassPrivileges,ModeType modeType){
		Long billClassId = null;
		String privilegeTypeStr;
		String[] billClassAndPrivilege;
		
		Map<XmlBillClass,Boolean> billClassesFlag=new LinkedHashMap<XmlBillClass, Boolean>();
		
		for (XmlBillClass xmlBillClass : billClassesCache.values()) {
			billClassesFlag.put(xmlBillClass, false);
		}
		
		XmlBillClass xmlBillClass;
		for (String billClassMode : billClassPrivileges) {
			billClassAndPrivilege=billClassMode.split("-");
			if(billClassAndPrivilege.length!=2){
				throw new RuntimeException("权限串配置错误！");
			}
			try{
				billClassId=new Long(billClassAndPrivilege[0]);
			}catch(Exception e){
				throw new RuntimeException("表单类编号必需是Long类型！");
			}
			xmlBillClass=billClassesCache.get(billClassId);
			
			if(xmlBillClass==null){
				//throw new RuntimeException("表单类编号不存在请检查配置！");
				continue;
			}
			privilegeTypeStr=billClassAndPrivilege[1];
			
			if(modeType==ModeType.SEARCH){
				billClassesFlag.put(xmlBillClass,true);
				xmlBillClass.setPrivilegeType(PrivilegeType.SEARCH);
			}else{
				PrivilegeType privilegeType=PrivilegeType.valueOf(privilegeTypeStr);
				if(privilegeType==null){
					throw new RuntimeException("权限配置错误！");
				}
				if(!billClassesFlag.get(xmlBillClass)){
					xmlBillClass.setPrivilegeType(privilegeType);
				}
				
				if(xmlBillClass.getPrivilegeType().getIndex() < privilegeType.getIndex()){
					xmlBillClass.setPrivilegeType(privilegeType);
				}
			}
			billClassesFlag.put(xmlBillClass,true);
		}
		
		List<XmlBillClass> billClasses=new ArrayList<XmlBillClass>();
		
		for(XmlBillClass billClass:billClassesFlag.keySet()){
			if(billClassesFlag.get(billClass)){
				billClasses.add(billClass);
			}
		}
		return billClasses;
	}

	@Override
	public void xmlBillClassMoveUp(String billConfigId, Long billClassId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlbillClasses = spBillConfig.getXmlBillClasses();
		
		XmlBillClass xmlBillclass ;
		XmlBillClass swapXmlBillclass ;
		for(int i=0; i<xmlbillClasses.getBillClasses().size();i++){
			xmlBillclass=xmlbillClasses.getBillClasses().get(i);
			if(xmlBillclass.getId().equals(billClassId)){
				if(i==0){
					return;
				}
				swapXmlBillclass=xmlbillClasses.getBillClasses().get(i-1);
				xmlbillClasses.getBillClasses().set(i-1, xmlBillclass);
				xmlbillClasses.getBillClasses().set(i, swapXmlBillclass);
				break;
			}
		}
		
		//更新到数据库
		updateBillConfig(spBillConfig, xmlbillClasses);
		
	}

	@Override
	public void xmlBillClassMoveDown(String billConfigId, Long billClassId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlbillClasses = spBillConfig.getXmlBillClasses();
		
		XmlBillClass xmlBillclass ;
		XmlBillClass swapXmlBillclass ;
		for(int i=0; i<xmlbillClasses.getBillClasses().size();i++){
			xmlBillclass=xmlbillClasses.getBillClasses().get(i);
			if(xmlBillclass.getId().equals(billClassId)){
				if(i==xmlbillClasses.getBillClasses().size()-1){
					return;
				}
				swapXmlBillclass=xmlbillClasses.getBillClasses().get(i+1);
				xmlbillClasses.getBillClasses().set(i+1, xmlBillclass);
				xmlbillClasses.getBillClasses().set(i, swapXmlBillclass);
				break;
			}
		}
		
		//更新到数据库
		updateBillConfig(spBillConfig, xmlbillClasses);
		
	}

	@Override
	public void addXmlBillProperty(String billConfigId, Long billClassId,
			XmlBillProperty property) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlBillClasses = spBillConfig.getXmlBillClasses();
		//取得billClassId对应信息类
		XmlBillClass xmlBillclass = xmlBillClasses
				.getBillClassById(billClassId);
		//判断是否存在属性，没有则初始
		if (xmlBillclass.getBillPropertys() == null) {
			xmlBillclass.setBillPropertys(new ArrayList<XmlBillProperty>());
		}
		if(idCheckRepeat(xmlBillclass.getBillPropertys(),property)){
			throw new RuntimeException("字段名不能重复");
		}

		//对新属性赋id
		property.setId(System.currentTimeMillis());
		//如果property.getPropertyId()不空则注入信息类属性
		if(!StringUtil.isEmpty(property.getPropertyId())){
			property.setInfoProperty(
					InfoClassCache.getInfoClass(xmlBillclass.getClassId())
					.getPropertyById(property.getPropertyId()));
		}
		//新增新属性
		xmlBillclass.getBillPropertys().add(property);
		//更新到数据库
		updateBillConfig(spBillConfig, xmlBillClasses);
	}

	@Override
	public void modifyXmlBillProperty(String billConfigId, Long billClassId,
			XmlBillProperty property) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlbillClasses = spBillConfig.getXmlBillClasses();
		//取得billClassId对应信息类
		XmlBillClass xmlBillclass = xmlbillClasses
				.getBillClassById(billClassId);
		//定位配置内容
		int index = xmlBillclass.getBillPropertys().indexOf(
				xmlBillclass.getBillPropertyById(property.getId()));
		//如果property.getPropertyId()不空则注入信息类属性
		if(!StringUtil.isEmpty(property.getPropertyId())){
			property.setInfoProperty(
					InfoClassCache.getInfoClass(xmlBillclass.getClassId())
					.getPropertyById(property.getPropertyId()));
		}
		//向定位处新增配置项
		xmlBillclass.getBillPropertys().set(index, property);
		//更新到数据库
		updateBillConfig(spBillConfig, xmlbillClasses);
	}
	/**
	 * 更新到数据库
	 * @param spBillConfig
	 * @param xmlbillClasses
	 */
	private void updateBillConfig(SpBillConfig spBillConfig,
			XmlBillClasses xmlbillClasses) {
		//反解析成字串
		spBillConfig.setXmlBillClasses(xmlbillClasses);
		//更新到数据库
		spBillConfigDao.update(spBillConfig);
	}

	@Override
	public void removeXmlBillProperty(String billConfigId, Long billClassId,
			Long propertyId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlbillClasses = spBillConfig.getXmlBillClasses();
		//取得billClassId对应信息类
		XmlBillClass xmlBillclass = xmlbillClasses
				.getBillClassById(billClassId);
		//定位配置内容
		int index = xmlBillclass.getBillPropertys().indexOf(
				xmlBillclass.getBillPropertyById(propertyId));
		//删除被定位配置项
		xmlBillclass.getBillPropertys().remove(index);
		//更新到数据库
		updateBillConfig(spBillConfig, xmlbillClasses);
	}

	@Override
	public XmlBillProperty getXmlBillPropertyById(String billConfigId,
			Long billClassId, Long propertyId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlbillClasses = spBillConfig.getXmlBillClasses();
		//判空
		if(xmlbillClasses.getBillClasses()==null){
			return null;
		}
		
		XmlBillClass xmlBillclass = null;
		if (xmlbillClasses.getBillClasses() != null) {
			//取得billClassId对应信息类
			xmlBillclass = xmlbillClasses.getBillClassById(billClassId);
			//判空
			if(xmlBillclass==null){
				return null;
			}
		}
		//判空
		if(xmlBillclass.getBillPropertys()==null){
			return null;
		}
		//取得propertyFieldName对应的属性对象
		XmlBillProperty xmlBillProperty = xmlBillclass.getBillPropertyById(propertyId);
		//判空
		if(xmlBillProperty==null){
			return null;
		}
		//返回处理对象
		return xmlBillProperty;

	}
	

	@Override
	public XmlBillProperty getXmlBillPropertyByVersion(String billConfigId,
			Integer version, Long billClassId, Long propertyId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigByVersion(billConfigId, version);
		//解析配置内容转化成对象
		XmlBillClasses xmlbillClasses = spBillConfig.getXmlBillClasses();
		//判空
		if(xmlbillClasses.getBillClasses()==null){
			return null;
		}
		
		XmlBillClass xmlBillclass = null;
		if (xmlbillClasses.getBillClasses() != null) {
			//取得billClassId对应信息类
			xmlBillclass = xmlbillClasses.getBillClassById(billClassId);
			//判空
			if(xmlBillclass==null){
				return null;
			}
		}
		//判空
		if(xmlBillclass.getBillPropertys()==null){
			return null;
		}
		//取得propertyFieldName对应的属性对象
		XmlBillProperty xmlBillProperty = xmlBillclass.getBillPropertyById(propertyId);
		//判空
		if(xmlBillProperty==null){
			return null;
		}
		//返回处理对象
		return xmlBillProperty;
	}

	@Override
	public XmlBillProperty getXmlBillPropertyLastVersion(String billConfigId,
			Long billClassId, Long propertyId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigLastVersion(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlbillClasses = spBillConfig.getXmlBillClasses();
		//判空
		if(xmlbillClasses.getBillClasses()==null){
			return null;
		}
		
		XmlBillClass xmlBillclass = null;
		if (xmlbillClasses.getBillClasses() != null) {
			//取得billClassId对应信息类
			xmlBillclass = xmlbillClasses.getBillClassById(billClassId);
			//判空
			if(xmlBillclass==null){
				return null;
			}
		}
		//判空
		if(xmlBillclass.getBillPropertys()==null){
			return null;
		}
		//取得propertyFieldName对应的属性对象
		XmlBillProperty xmlBillProperty = xmlBillclass.getBillPropertyById(propertyId);
		//判空
		if(xmlBillProperty==null){
			return null;
		}
		//返回处理对象
		return xmlBillProperty;
	}

	@Override
	public List<XmlBillProperty> getXmlBillPropertyList(String billConfigId,
			Long billClassId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlbillClasses = spBillConfig.getXmlBillClasses();
		//判空
		if(xmlbillClasses.getBillClasses()==null){
			return null;
		}
		
		XmlBillClass xmlBillclass = null;
		if (xmlbillClasses.getBillClasses() != null) {
			//取得billClassId对应信息类
			xmlBillclass = xmlbillClasses.getBillClassById(billClassId);
			//判空
			if(xmlBillclass==null){
				return null;
			}
		}
		//判空
		if(xmlBillclass==null||xmlBillclass.getBillPropertys()==null){
			return null;
		}
		//返回处理对象
		return xmlBillclass.getBillPropertys();

	}

	@Override
	public List<XmlBillProperty> getXmlBillPropertyListByVersion(
			String billConfigId, Integer version, Long billClassId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigByVersion(billConfigId, version);
		//解析配置内容转化成对象
		XmlBillClasses xmlbillClasses = spBillConfig.getXmlBillClasses();
		//判空
		if(xmlbillClasses.getBillClasses()==null){
			return null;
		}
		
		XmlBillClass xmlBillclass = null;
		if (xmlbillClasses.getBillClasses() != null) {
			//取得billClassId对应信息类
			xmlBillclass = xmlbillClasses.getBillClassById(billClassId);
			//判空
			if(xmlBillclass==null){
				return null;
			}
		}
		//判空
		if(xmlBillclass==null||xmlBillclass.getBillPropertys()==null){
			return null;
		}
		//返回处理对象
		return xmlBillclass.getBillPropertys();
	}

	@Override
	public List<XmlBillProperty> getXmlBillPropertyListLastVersion(
			String billConfigId, Long billClassId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigLastVersion(billConfigId);
		XmlBillClasses xmlbillClasses = spBillConfig.getXmlBillClasses();
		//判空
		if(xmlbillClasses.getBillClasses()==null){
			return null;
		}
		
		XmlBillClass xmlBillclass = null;
		if (xmlbillClasses.getBillClasses() != null) {
			//取得billClassId对应信息类
			xmlBillclass = xmlbillClasses.getBillClassById(billClassId);
			//判空
			if(xmlBillclass==null){
				return null;
			}
		}
		//判空
		if(xmlBillclass==null||xmlBillclass.getBillPropertys()==null){
			return null;
		}
		//返回处理对象
		return xmlBillclass.getBillPropertys();
	}

	@Override
	public void xmlBillPropertyMoveLeft(String billConfigId, Long billClassId,
			Long propertyId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlbillClasses = spBillConfig.getXmlBillClasses();
		
		XmlBillClass xmlBillclass = xmlbillClasses.getBillClassById(billClassId);
		XmlBillProperty xmlBillProperty;
		XmlBillProperty swapXmlBillProperty;
		for(int i=0; i<xmlBillclass.getBillPropertys().size();i++){
			 xmlBillProperty=xmlBillclass.getBillPropertys().get(i);
			if(xmlBillProperty.getId().equals(propertyId)){
				if(i==0){
					return;
				}
				swapXmlBillProperty=xmlBillclass.getBillPropertys().get(i-1);
				xmlBillclass.getBillPropertys().set(i-1, xmlBillProperty);
				xmlBillclass.getBillPropertys().set(i, swapXmlBillProperty);
				break;
			}
		}
		
		//更新到数据库
		updateBillConfig(spBillConfig, xmlbillClasses);
	}

	@Override
	public void xmlBillPropertyMoveRight(String billConfigId, Long billClassId,
			Long propertyId) {
		//取得数据库配置对象
		SpBillConfig spBillConfig = getSpBillConfigById(billConfigId);
		//解析配置内容转化成对象
		XmlBillClasses xmlbillClasses = spBillConfig.getXmlBillClasses();
		
		XmlBillClass xmlBillclass = xmlbillClasses.getBillClassById(billClassId);
		XmlBillProperty xmlBillProperty;
		XmlBillProperty swapXmlBillProperty;
		for(int i=0; i<xmlBillclass.getBillPropertys().size();i++){
			 xmlBillProperty=xmlBillclass.getBillPropertys().get(i);
			if(xmlBillProperty.getId().equals(propertyId)){
				if(i==xmlBillclass.getBillPropertys().size()-1){
					return;
				}
				swapXmlBillProperty=xmlBillclass.getBillPropertys().get(i+1);
				xmlBillclass.getBillPropertys().set(i+1, xmlBillProperty);
				xmlBillclass.getBillPropertys().set(i, swapXmlBillProperty);
				break;
			}
		}
		
		//更新到数据库
		updateBillConfig(spBillConfig, xmlbillClasses);
	}


	//表单配置分页显示
	@Override
	public PageList<SpBillQuery> getPagingSpBillConfigList(SpBillQuery query) {
		PageList<SpBillQuery> pageList = new PageList<SpBillQuery>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(spBillConfigDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<SpBillQuery> list = spBillConfigDao.getPagingList(query);
				pageList.addAll(list);
			}
		}
		for (SpBillQuery spBillQuery : pageList) {
			spBillQuery.setVersions(getSpBillConfigVersionList(spBillQuery.getId()));
		}
		return pageList;
	}
	
	public void setSpBillConfigDao(ISpBillConfigDao spBillConfigDao) {
		this.spBillConfigDao = spBillConfigDao;
	}
	
	
	public void setSpBillConfigVersionDao(
			ISpBillConfigVersionDao spBillConfigVersionDao) {
		this.spBillConfigVersionDao = spBillConfigVersionDao;
	}

	/**
	 * 返回
	 */
	public ITableService getTableService() {
		return tableService;
	}

	/**
	 * 设置
	 * @param tableService 
	 */
	public void setTableService(ITableService tableService) {
		this.tableService = tableService;
	}
}
