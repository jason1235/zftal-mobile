package com.zfsoft.hrm.baseinfo.infoclass.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.infoclass.exception.InfoClassException;
import com.zfsoft.hrm.baseinfo.infoclass.business.bizinterface.ICatalogBusiness;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.dao.daointerface.IInfoPropertyDao;
import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoPropertyService;
import com.zfsoft.hrm.baseinfo.table.entities.Column;
import com.zfsoft.hrm.baseinfo.table.service.svcinterface.IColumnService;
import com.zfsoft.hrm.baseinfo.table.service.svcinterface.ITableService;
import com.zfsoft.hrm.baseinfo.table.util.TableUtil;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.config.type.InfoCatalogType;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.util.base.StringUtil;

/**
 * {@link IInfoPropertyService }缺省实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-21
 * @version V1.0.0
 */
public class InfoPropertyServiceImpl implements IInfoPropertyService {

	protected IInfoPropertyDao dao;
	
	protected ICatalogBusiness catalogBusiness;
		
	protected IColumnService columnService;

	protected ITableService tableService;
	
	@Override
	public void add(InfoProperty entity) throws InfoClassException {
		InfoPropertyQuery query = new InfoPropertyQuery();
		
		query.setClassId( entity.getClassId() );
		query.setFieldName( entity.getFieldName() );
		
		int count = dao.findCount(query);

		
		if( count > 0 ) {
			throw new RuleException("已存在相同的字段名！");
		}
		
		query.setFieldName(null);
		count = dao.findCount(query);
		
		entity.setIndex(count);
		
		InfoClass clazz = InfoClassCache.getInfoClass( entity.getClassId() );
		
		if( clazz != null ) {
			entity.setClasz( clazz );
		}
		
		dao.insert(entity);
		
		addColumn( entity );
		
		InfoClassCache.refresh( entity.getClassId() );
	}
	
	/**
	 * 增加信息类属性描述信息对应的表字段
	 * @param entity 信息类属性描述信息
	 * @throws InfoClassException 如果操作出现异常
	 */
	private void addColumn( InfoProperty entity ) throws InfoClassException {
		if( entity.getVirtual() == true ) {//虚拟字段不处理物理字段
			return;
		}
		boolean createIndex = false;
		if((!StringUtil.isEmpty(entity.getFieldName()))
			&&"GH".equals(entity.getFieldName().toUpperCase())){
			createIndex = true;
		}
		columnService.add( TableUtil.infoPropertyToColumn( entity ),createIndex );
		InfoClass clasz=InfoClassCache.getInfoClass(entity.getClassId());
		
		InfoCatalogType type = getCatalogType( clasz );
		
		if( type.isCreateSnap() ) {
			Column c =  TableUtil.infoPropertyToColumn( entity, "_SNAP" );
			if(tableService.isExist(c.getTableName()))
				columnService.add(c,createIndex);
		}
		
		if( type.isCreateLog() ) {
			Column c =  TableUtil.infoPropertyToColumn( entity, "_LOG" );
			if(tableService.isExist(c.getTableName()))
				columnService.add(c);
		}
		
		
	}
	
	/**
	 * 返回信息类目录类型
	 * @param clazz 信息类描述信息
	 * @return
	 */
	private InfoCatalogType getCatalogType( InfoClass clazz ) {
		Catalog catalog = clazz.getCatalog();
		
		if( catalog == null || catalog.getGuid() == null || "".equals( catalog.getGuid() ) ) {
			catalog = InfoClassCache.getInfoClass( clazz.getGuid() ).getCatalog();
		}
		
		return catalogBusiness.getCatalogTypeInfo( catalog.getGuid() );
	}
	
	@Override
	public List<InfoProperty> getInfoProperties(InfoPropertyQuery query)
			throws InfoClassException {
		
		return dao.findList(query);
	}

	@Override
	public InfoProperty getProperty(String guid) throws InfoClassException {

		InfoProperty property = dao.findById(guid);
		
		return property;
	}

	@Override
	public void modify(InfoProperty entity) throws InfoClassException {

		dao.update(entity);
		
		entity = getProperty( entity.getGuid() );
		entity.setClasz(InfoClassCache.getInfoClass(entity.getClassId()));//为读取identityName（表名）
		modifyColumn( entity );
		
		InfoClassCache.refresh( entity.getClassId() );
	}
	
	public void updateSync(InfoProperty entity){
		dao.updateSync(entity);
		InfoClassCache.refresh( entity.getClassId() );
	}
	
	/**
	 * 修改信息类属性描述信息对应的表字段
	 * @param entity 信息类属性描述信息
	 * @throws InfoClassException 如果操作出现异常
	 */
	private void modifyColumn( InfoProperty entity ) throws InfoClassException {
		if( entity.getVirtual() == true ) {//虚拟字段不处理物理字段
			return;
		}
		columnService.modify( TableUtil.infoPropertyToColumn( entity ) );
		InfoClass clasz=InfoClassCache.getInfoClass(entity.getClassId());
		
		
		InfoCatalogType type = getCatalogType( clasz );
		
		if( type.isCreateSnap() ) {
			Column c =  TableUtil.infoPropertyToColumn( entity, "_SNAP" );
			if(tableService.isExist(c.getTableName()))
				columnService.modify(c);
		}
		
		if( type.isCreateLog() ) {
			Column c =  TableUtil.infoPropertyToColumn( entity, "_LOG" );
			if(tableService.isExist(c.getTableName()))
				columnService.modify(c);
		}
	}

	@Override
	public void remove(String guid) throws InfoClassException {

		/*
		 * 信息类属性删除后，该属性下的信息类需要向上移动一位，
		 * 避免事物提交，导致无法获取删除的记录信息，在删除前先获取需要删除记录的信息，
		 */
		InfoProperty property = getProperty(guid);
		InfoClass clazz = InfoClassCache.getInfoClass( property.getClassId() );
		property.setClasz( clazz );
		
		dao.updateAllIndex(property);

		dao.delete(guid);
		
		dao.deleteMenuProperty(guid);
		
		removeColumn( property );

		InfoClassCache.refresh( clazz.getGuid() );
	}
	
	/**
	 * 删除信息类属性描述信息对应的字段
	 * @param property 信息类属性描述信息
	 * @throws InfoClassException 如果操作出现异常
	 */
	public void removeColumn( InfoProperty property ) throws InfoClassException {
		if( property.getVirtual() == true ) {//虚拟字段不处理物理字段
			return;
		}
		
		columnService.drop( TableUtil.infoPropertyToColumn( property ) );
		InfoClass clasz=InfoClassCache.getInfoClass(property.getClassId());
		
		
		InfoCatalogType type = getCatalogType( clasz );
		

		if( type.isCreateSnap() ) {
			Column c =  TableUtil.infoPropertyToColumn( property, "_SNAP" );
			if(tableService.isExist(c.getTableName()))
				columnService.drop(c);
		}
		
		if( type.isCreateLog() ) {
			Column c =  TableUtil.infoPropertyToColumn( property, "_LOG" );
			if(tableService.isExist(c.getTableName()))
				columnService.drop(c);
		}
		
	}

	@Override
	public void removeClassProperties(String classId) throws InfoClassException {
		/*
		 * 该方法在删除信息类时进行对其信息类的属性清除
		 * 缓存信息应在信息类删除的方法中进行更新
		 */
		dao.deleteByClassId(classId);
		
		dao.deleteMenuProperty(classId);
		
		//该方法应在信息类删除前进行调用，缓存信息在信息类删除后做更新，无需在此处做缓存刷新动作
	}

	@Override
	public void swapIndex(String[] guids) throws InfoClassException {
		InfoProperty property1 = getProperty(guids[0]);
		InfoProperty property2 = getProperty(guids[1]);
		
		Integer index = property1.getIndex();
		
		property1.setIndex( property2.getIndex() );
		property2.setIndex(index);
		
		dao.updateIndex(property1);
		dao.updateIndex(property2);
		
		InfoClassCache.refresh( getProperty( guids[0] ).getClassId() );
	}
	
	@Override
	public void syncFieldValue(String cId,String pId) throws InfoClassException {
		
		InfoClass overall = InfoClassCache.getOverallInfoClass( IConstants.INFO_CATALOG_TYPE_DEFAULT );
		InfoClass clazz = InfoClassCache.getInfoClass(cId );
		Map<String, String> syncData = new HashMap<String, String>();
		syncData.put("targetTable", overall.getIdentityName());
		syncData.put("infoTable", clazz.getIdentityName());
		//遍历所有可编辑的字段
		for ( InfoProperty property : clazz.getProperties() ) {
			//该字段无需同步
			if(!(StringUtil.isEmpty(pId)||pId.equals(property.getGuid())))
			{
				continue;
			}
			if( property.getSyncToField() == null || "".equals( property.getSyncToField() ) ) {
				continue;
			}
			InfoProperty targetProperty =  overall.getPropertyById( property.getSyncToField() );
			if(targetProperty == null){
				continue;
			}
			
			syncData.put("targetField", targetProperty.getFieldName());
			syncData.put("infoField", property.getFieldName());
			String condition="";
			if(!StringUtil.isEmpty(property.getSyncCondition())){
				condition = property.getSyncCondition()+" = '1'";
			}
			syncData.put("condition", condition);
			
			dao.syncProperty(syncData);
		}
	}
	
	
	public void setDao(IInfoPropertyDao dao) {
		this.dao = dao;
	}

	public void setColumnService(IColumnService columnService) {
		this.columnService = columnService;
	}

	public void setCatalogBusiness(ICatalogBusiness catalogBusiness) {
		this.catalogBusiness = catalogBusiness;
	}

	public void setTableService(ITableService tableService) {
		this.tableService = tableService;
	}

	
}

