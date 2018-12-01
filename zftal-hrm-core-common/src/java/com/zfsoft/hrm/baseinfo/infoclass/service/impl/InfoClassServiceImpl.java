package com.zfsoft.hrm.baseinfo.infoclass.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.hrm.baseinfo.infoclass.business.bizinterface.ICatalogBusiness;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.dao.daointerface.IInfoClassDao;
import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.exception.InfoClassException;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoClassQuery;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoClassService;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoPropertyService;
import com.zfsoft.hrm.baseinfo.infoclass.util.InfoClassUtil;
import com.zfsoft.hrm.baseinfo.table.entities.Table;
import com.zfsoft.hrm.baseinfo.table.service.svcinterface.ITableService;
import com.zfsoft.hrm.baseinfo.table.util.TableUtil;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.config.type.InfoCatalogType;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.menu.business.IMenuBusiness;
import com.zfsoft.hrm.menu.business.IMenuOperateBusiness;
import com.zfsoft.hrm.menu.entity.MenuOperate;

/**
 * {@link IInfoClassService}缺省实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-21
 * @version V1.0.0
 */
public class InfoClassServiceImpl implements IInfoClassService {

	private IInfoClassDao dao;
	
	private IInfoPropertyService propertyService;
	
	private ITableService tableService;
	
	private ICatalogBusiness catalogBusiness;
	
	private IMenuBusiness menuBusiness;
	
	private IMenuOperateBusiness menuOperateBusiness;
	
	
	@Override
	public List<InfoClass> getList(InfoClassQuery query) throws InfoClassException {
		
		return dao.findList(query);
	}
	
	@Override
	public List<InfoClass> getFullList(InfoClassQuery query) throws InfoClassException {
		List<InfoClass> classes = getList(query);
		
		for (InfoClass clazz : classes) {
			
			clazz.setProperties( getProperties( clazz.getGuid() ) );
		}
		
		return classes;
	}

	@Override
	public int count(InfoClassQuery query) throws InfoClassException {
		
		return dao.findCount(query);
	}

	@Override
	public InfoClass getInfoClass(String classId) throws InfoClassException {
		if( classId == null || "".equals(classId) ) {
			return new InfoClass();
		}
		
		return dao.findById(classId);
	}
	
	@Override
	public InfoClass getFullInfoClass(String classId) throws InfoClassException {
		InfoClass clazz = getInfoClass( classId );
		
		clazz.setProperties( getProperties(classId) );
		
		return clazz;
	}

	@Override
	public List<InfoClass> getCatalogInfoClasses(String catalogId) throws InfoClassException {
		InfoClassQuery query = new InfoClassQuery();
		
		query.setCatalogId(catalogId);
		
		return getList(query);
	}
	
	@Override
	public void add(InfoClass entity) throws InfoClassException {
		/*
		 * 标识名必须保证唯一
		 * 检查相同标识名的信息类是否已存在
		 */
		InfoClassQuery query = new InfoClassQuery();
		
		query.setIdentityName( entity.getIdentityName() );
		
		int count = dao.findCount(query);
		
		if( count > 0 ) {
			throw new RuleException("已存在相同的标识名！");
		}
		
		query = new InfoClassQuery();
		
		query.setName( entity.getName() );
		
		count = dao.findCount(query);
		
		if( count > 0 ){
			throw new RuleException("已存在相同的名称！");
		}
		//顺序码改为页面输入（让用户可以自定义顺序 ） 不进行处理
//		/*
//		 * 生成信息类的在顺序号
//		 */
//		query = new InfoClassQuery();
//		query.setCatalogId( entity.getCatalog().getGuid() );
//		count = dao.findCount(query);
//		entity.setIndex(count);
		
		dao.insert(entity);
		InfoCatalogType typeInfo = catalogBusiness.getCatalogTypeInfo( entity.getCatalog().getGuid() );
		Properties properties = typeInfo.getMenus();
		if(properties.size()>0){
			Catalog c = catalogBusiness.getCatalogById(entity.getCatalog().getGuid());
			for (String m : properties.stringPropertyNames()) {
				//读取上级菜单
				IndexModel menuQuery = new IndexModel();
				menuQuery.setGnmkmc(c.getName());
				//menuQuery.setFjgndm(m);
				menuQuery.setFjgndm("N99");//要求有单独的模块来看信息类的数据，故这里用常量，张旭修改
				IndexModel parentModel = menuBusiness.getByName(menuQuery);
				if(parentModel == null){
					throw new RuleException("上级菜单不存在，操作终止");
				}
				IndexModel model = new IndexModel();
				model.setFjgndm(parentModel.getGnmkdm());
				model.setGnmkmc(entity.getName());
				model.setDyym(properties.getProperty(m)+"?classId="+entity.getGuid());
				model.setIsAuto("1");
				menuBusiness.addMenu(model, true, new String[0]);
				model.setXssx(entity.getIndex()+"");
				menuBusiness.modify(model);
				addMenuOperation(model.getGnmkdm());
			}
		}
		createTable( entity );
		
		addProperties( entity );
		
		//更新缓存信息
		InfoClassCache.refresh( entity.getGuid() );
	}

	/** 
	 * @param model
	 */
	private void addMenuOperation(String gnmkdm) {
		MenuOperate operate = new MenuOperate();
		List<MenuOperate>  list = menuOperateBusiness.getByMenuId(gnmkdm);
		operate.setMenuId(gnmkdm);
		operate.setOperate("zj");
		if(!list.contains(operate)){
			menuOperateBusiness.insert(operate);
		}
		operate.setOperate("xg");
		if(!list.contains(operate)){
			menuOperateBusiness.insert(operate);
		}
		operate.setOperate("sc");
		if(!list.contains(operate)){
			menuOperateBusiness.insert(operate);
		}
	}

	/** 
	 * @param entity
	 * @return
	 */
	private IndexModel addPersonMenu(InfoClass entity) {
		//读取上级菜单
		Catalog c = catalogBusiness.getCatalogById(entity.getCatalog().getGuid());
		IndexModel query = new IndexModel();
		query.setGnmkmc(c.getName());
		query.setFjgndm(IConstants.PERSON_INFO_ROOT_MENU);
		IndexModel parentModel = menuBusiness.getByName(query);
		if(parentModel == null){
			throw new RuleException("上级菜单不存在，操作终止");
		}
//		query.setGnmkmc(entity.getName());
//		query.setFjgndm(parentModel.getGnmkdm());
//		IndexModel model = menuBusiness.getByName(query);
		IndexModel model = new IndexModel();
		model.setFjgndm(parentModel.getGnmkdm());
		model.setGnmkmc(entity.getName());
		model.setDyym("/normal/staffInfo_list.html?classId="+entity.getGuid());
		model.setIsAuto("1");
		menuBusiness.addMenu(model);
		model.setXssx(entity.getIndex()+"");
		menuBusiness.modify(model);
		return model;
	}
	
	private IndexModel addPersonBatchMenu(InfoClass entity) {
		//读取上级菜单
		Catalog c = catalogBusiness.getCatalogById(entity.getCatalog().getGuid());
		IndexModel query = new IndexModel();
		query.setGnmkmc(c.getName());
		query.setFjgndm(IConstants.PERSON_INFO_BATCH_ROOT_MENU);
		IndexModel parentModel = menuBusiness.getByName(query);
		if(parentModel == null){
			throw new RuleException("上级菜单不存在，操作终止");
		}
		
		IndexModel model = new IndexModel();
		model.setFjgndm(parentModel.getGnmkdm());
		model.setGnmkmc(entity.getName());
		model.setDyym("/normal/staffBatch_list.html?classId="+entity.getGuid());
		model.setIsAuto("1");
		menuBusiness.addMenu(model);
		model.setXssx(entity.getIndex()+"");
		menuBusiness.modify(model);
		return model;
	}
	
	/**
	 * 对指定的信息类描述信息创建表结构
	 * <p>
	 * 此必须在增加信息类字段之前，因为信息类字段增加是将会对表进行字段增加操作
	 * </p>
	 * <p>
	 * 此外创建表结构默认值创建主库表结构，同时将根据信息类所属的信息类目录的类型描述信息判断是否创建日志、快照的表结构信息
	 * </p>
	 * @param entity 信息类描述信息
	 */
	private void createTable( InfoClass entity ) {
		
		tableService.create( TableUtil.createBaseInfoTable( entity ) );

		InfoCatalogType typeInfo = catalogBusiness.getCatalogTypeInfo( entity.getCatalog().getGuid() );

		if( typeInfo.isCreateLog() ) {
			tableService.create( TableUtil.createLogInfoTable( entity ) );
		}
		
		if( typeInfo.isCreateSnap() ) {
			tableService.create( TableUtil.createSnapInfoTable( entity ) );
		}
	}
	
	/**
	 * 增加信息类属性信息
	 * @param clazz 信息类描述信息
	 */
	private void addProperties( InfoClass entity ) {
		InfoClass clazz = InfoClassUtil.createBaseInfoClass();
		
		for ( InfoProperty property : clazz.getProperties() ) {
			property.setClasz( entity );
			property.setSourceInit(true);
			propertyService.add( property );
		}
		
		addPrimaryProperty( entity );
	}
	
	/**
	 * 对信息类增加主键信息
	 * <p>
	 * 通过信息类目录主键获取信息类目录类型描述信息，
	 * 再通过信息类目录类型描述信息获取主键信息，并完成增加操作。
	 * </p>
	 * @param entity 信息类描述信息
	 */
	private void addPrimaryProperty( InfoClass entity ) {
		
		Catalog catalog = catalogBusiness.getCatalogById( entity.getCatalog().getGuid() );
		
		InfoProperty property = InfoClassUtil.createPrimary(
				catalog.getTypeInfo().getPrimaryName(),
				catalog.getTypeInfo().getPrimaryFileName() );
		
		property.setClasz( entity );
		property.setSourceInit(true);
		propertyService.add( property );
	}
	
	@Override
	public void modify(InfoClass entity) throws InfoClassException {
		InfoClassQuery query = new InfoClassQuery();
		
		query.setName( entity.getName() );
		
		List<InfoClass> list = dao.findList( query );
		
		for (InfoClass infoClass : list) {
			if( !infoClass.getGuid().equals(entity.getGuid() ) ) {
				throw new RuleException("已存在相同的名称！");
			}
		}
		InfoClass oldBean = dao.findById(entity.getGuid());
		commentTable( entity );

		dao.update( entity );
		InfoCatalogType typeInfo = catalogBusiness.getCatalogTypeInfo( entity.getCatalog().getGuid() );
		Properties properties = typeInfo.getMenus();
		if(properties.size()>0){
			List<IndexModel> modelList = menuBusiness.getByName(oldBean.getName());
			Catalog c = catalogBusiness.getCatalogById(entity.getCatalog().getGuid());
			for (String m : properties.stringPropertyNames()) {
				IndexModel menu=null;
				for(IndexModel model : modelList){
					//if(model.getGnmkdm().startsWith(m)
					if(model.getFjgndm()!=null
							&&model.getFjgndm().length()>m.length()){
						model.setGnmkmc(entity.getName());
						model.setXssx(entity.getIndex()+"");
						updateMenu(model);
						menu = model;
						break;
					}
				}
				if(menu==null){
					//读取上级菜单
					IndexModel menuQuery = new IndexModel();
					menuQuery.setGnmkmc(c.getName());
					//menuQuery.setFjgndm(m);//by zhangxu
					IndexModel parentModel = menuBusiness.getByName(menuQuery);
					if(parentModel == null){
						throw new RuleException("上级菜单不存在，操作终止");
					}
					menu = new IndexModel();
					menu.setFjgndm(parentModel.getGnmkdm());
					menu.setGnmkmc(entity.getName());
					menu.setDyym(properties.getProperty(m)+"?classId="+entity.getGuid());
					menu.setIsAuto("1");
					menuBusiness.addMenu(menu, true, new String[0]);
					menu.setXssx(entity.getIndex()+"");
					menuBusiness.modify(menu);
				}
				addMenuOperation(menu.getGnmkdm());
			}
		}
		//更新缓存信息
		InfoClassCache.refresh( entity.getGuid() );
	}

	/** 
	 * @param model
	 */
	private void updateMenu(IndexModel model) {
		menuBusiness.modify(model);
	}
	
	/**
	 * 备注表注释
	 * @param entity 信息类描述信息
	 * @throws InfoClassException 如果操作出现异常
	 */
	private void commentTable( InfoClass entity ) throws InfoClassException {
		String name = entity.getName();
		String identityName = entity.getIdentityName();
		
		tableService.comment( new Table( identityName, name ) );
		
		InfoCatalogType typeInfo = catalogBusiness.getCatalogTypeInfo( entity.getCatalog().getGuid() );
		
		if( typeInfo.isCreateLog() ) {
			tableService.comment( new Table( identityName + "_LOG", name + "_日志表" ) );
		}
		
		if( typeInfo.isCreateSnap() ) {
			tableService.comment( new Table( identityName + "_SNAP", name + "_快照表" ) );
		}
	}

	@Override
	public void remove(String guid) throws InfoClassException {
		
		propertyService.removeClassProperties(guid);
		InfoClass clazz = InfoClassCache.getInfoClass( guid );
		
		dao.delete(guid);
		dao.removeProceduresById(guid);
		dao.deleteMenuProperty(guid);
		//删除菜单
		List<IndexModel> modelList = menuBusiness.getByName(clazz.getName());
		Properties properties = clazz.getCatalog().getTypeInfo().getMenus();
		if(properties.size()>0){
			for (String m : properties.stringPropertyNames()) {
				for(IndexModel model : modelList){
					//if(model.getGnmkdm().startsWith(m)){
					if(model.getGnmkdm().startsWith("N99")){//要求有单独的模块来看信息类的数据，故这里用常量，张旭修改
						menuBusiness.remove(model.getGnmkdm());
						//删除菜单的操作
						menuOperateBusiness.deleteByMenuId(model.getGnmkdm());
					}
				}
			}
		}
		dao.removeService(guid);
		
		dao.updateAllIndex(clazz);
		
		dropTable( clazz );
		
		//更新缓存信息
		InfoClassCache.deregister( guid );
	}
	
	/**
	 * 删除表结构信息
	 * @param entity 信息类描述信息
	 */
	private void dropTable( InfoClass entity ) {
		InfoCatalogType typeInfo = catalogBusiness.getCatalogTypeInfo( entity.getCatalog().getGuid() );
		
		tableService.drop( new Table( entity.getIdentityName(), "" ) );
		
		if( typeInfo.isCreateLog() ) {
			tableService.drop( new Table( entity.getIdentityName() + "_LOG", "" ) );
		}
		
		if( typeInfo.isCreateSnap() ) {
			tableService.drop( new Table( entity.getIdentityName() + "_SNAP", "" ) );
		}
	}
	
	/**
	 * 返回指定信息类的信息类属性列表
	 * @param classId 信息类ID
	 * @return 信息类属性列表
	 */
	private List<InfoProperty> getProperties(String classId) {

		InfoPropertyQuery query = new InfoPropertyQuery();
		
		query.setClassId(classId);
		
		return propertyService.getInfoProperties( query );
	}
	
	public void setDao(IInfoClassDao dao) {
		this.dao = dao;
	}

	public void setPropertyService(IInfoPropertyService propertyService) {
		this.propertyService = propertyService;
	}

	public void setTableService(ITableService tableService) {
		this.tableService = tableService;
	}

	public void setCatalogBusiness(ICatalogBusiness catalogBusiness) {
		this.catalogBusiness = catalogBusiness;
	}

	public void setMenuBusiness(IMenuBusiness menuBusiness) {
		this.menuBusiness = menuBusiness;
	}

	public void setMenuOperateBusiness(IMenuOperateBusiness menuOperateBusiness) {
		this.menuOperateBusiness = menuOperateBusiness;
	}

	@Override
	public InfoClass getFullInfoClass(String classId, String express) {
		InfoClass clazz = getInfoClass( classId );
		
		clazz.setProperties( getProperties(classId,express) );
		
		return clazz;
	}

	
	/**
	 * 返回指定信息类的信息类属性列表
	 * @param classId 信息类ID  express  显示、编辑、同步、虚拟的查询条件
	 * @return 信息类属性列表
	 */
	private List<InfoProperty> getProperties(String classId,String express) {

		InfoPropertyQuery query = new InfoPropertyQuery();
		
		query.setClassId(classId);
		query.setExpress(express);
		
		return propertyService.getInfoProperties( query );
	}

	@Override
	public void modifyProcedure(Map<String, String> map) {
		dao.modifyProcedure(map);
	}

	@Override
	public String getProceduresMhcx(String xxlid) {
		return dao.getProceduresMhcx(xxlid);
	}

	@Override
	public String getProceduresXxlall(String xxlid) {
		return dao.getProceduresXxlall(xxlid);
	}
}
