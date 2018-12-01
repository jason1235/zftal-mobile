package com.zfsoft.hrm.baseinfo.dyna.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.hibernate3.SessionHolder;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanLogBusiness;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanModifyAfterBusiness;
import com.zfsoft.hrm.baseinfo.dyna.dao.daointerface.IDynaBeanDao;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.entities.SyncBean;
import com.zfsoft.hrm.baseinfo.dyna.exception.DynaBeanException;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanValidateUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.exception.InfoClassException;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.config.IOperationConfig;
import com.zfsoft.hrm.config.TypeFactory;
import com.zfsoft.hrm.config.type.InfoCatalogType;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.file.util.AttachementUtil;
import com.zfsoft.hrm.file.util.ImageDBUtil;
import com.zfsoft.orcus.util.Sequencer;
import com.zfsoft.util.base.StringUtil;

/** 
 * {@link IDynaBeanBusiness}的实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-10
 * @version V1.0.0
 */
public class DynaBeanBusinessImpl implements IDynaBeanBusiness {

	private IDynaBeanDao dao;
	private IDynaBeanLogBusiness dynaBeanLogBusiness;
	
	public void setDao(IDynaBeanDao dao) {
		this.dao = dao;
	}
	
	private void removeFileAndImage(DynaBean bean) {
		String value;
		for(InfoProperty infoProperty:bean.getClazz().getProperties()){
			if(bean.getValues().get(infoProperty.getFieldName())!=null){
				value=bean.getValues().get(infoProperty.getFieldName()).toString();
			}else{
				continue;
			}
			if(Type.FILE.equals(infoProperty.getFieldType())){
				AttachementUtil.removeAttachement(value);
			}
			if(Type.IMAGE.equals(infoProperty.getFieldType())||
					Type.PHOTO.equals(infoProperty.getFieldType())){
				ImageDBUtil.deleteFileToDB(value);
			}
		}
	}
	@Override
	public List<DynaBean> queryBeans(DynaBeanQuery query) throws InfoClassException {
		
		List<DynaBean> beans = new ArrayList<DynaBean>();
		String old = orderStr2Query(query);
		List<Map<String, Object>> list = dao.findList( query );
		for ( Map<String, Object> values : list ) {
			DynaBean bean = new DynaBean( query.getClazz() );
			bean.setValues( values );
			beans.add( bean );
		}
		query.setOrderStr(old);
		return beans;
	}
	@Override
	public DynaBean findUniqueByParam(String paramName, Object value,InfoClass clazz) throws InfoClassException{
		DynaBeanQuery query=new DynaBeanQuery( clazz );
		query.setExpress( paramName + " = #{params." + paramName + "}" );
		query.setHistory(true);
		query.setParam( paramName, String.valueOf( value ) );
		List<DynaBean> dyBeans=this.queryBeans( query );
		
		if(dyBeans.size()>0){
			return dyBeans.get(0);
		}
		
		return null;
	}
	@Override
	public DynaBean findUniqueByParam(String paramName, Object value) throws InfoClassException {
		return findUniqueByParam(paramName, value, InfoClassCache.getOverallInfoClass());
	}

	@Override
	public DynaBean queryBeanByPK( String pkValue ) throws InfoClassException {
		
		InfoCatalogType type = (InfoCatalogType) TypeFactory.getType( InfoCatalogType.class, IConstants.INFO_CATALOG_TYPE_DEFAULT );
		
		String pName = type.getPrimaryFileName();
		
		return findUniqueByParam( pName, pkValue );
	}

	public DynaBean findById(DynaBean bean) throws DynaBeanException {
		Map<String, Object> obj = dao.findById( bean );
		DynaBean newBean = new DynaBean(bean.getClazz());
		newBean.setValues( obj );
		//数据修正，避免服务中连续调用findById后，modify及delete发生mybatis字段空错误
		for (InfoProperty property : newBean.getClazz().getEditables()) {
			Object value = newBean.getValue(property.getFieldName());
			if(value == null){
				newBean.setValue(property.getFieldName(), "");
			}
		}
		return newBean; 
	}

	public void addBean(DynaBean bean) throws DynaBeanException {
		if( bean == null ) {
			return;
		}
		List<InfoProperty> olfEditables=bean.getEditables();
		List<InfoProperty> editables=new ArrayList<InfoProperty>();
		for (InfoProperty infoProperty : bean.getClazz().getProperties()) {
			if(Type.CREATOR.equals(infoProperty.getFieldType())){
				if(StringUtil.isEmpty(bean.getValueString(infoProperty.getFieldName()))){
					bean.setValue(infoProperty.getFieldName(), SessionFactory.getUser().getXm());
				}
				editables.add(infoProperty);
			}
			else if(infoProperty.getEditable()&&bean.getValues().get(infoProperty.getFieldName())!=null){
				editables.add(infoProperty);
			}
		}
		bean.setEditables(editables);
		bean.setValue( "globalid", Sequencer.timeSequence() );
		DynaBeanValidateUtil.validate(bean);
		dao.insert(bean);
		bean.setEditables(olfEditables);
	}
	
	@Override
	public boolean addRecord(DynaBean bean) throws DynaBeanException {
		List<InfoProperty> olfEditables=bean.getEditables();
		List<InfoProperty> editables=new ArrayList<InfoProperty>();
		for (InfoProperty infoProperty : bean.getClazz().getProperties()) {
			if(Type.CREATOR.equals(infoProperty.getFieldType())){
				if(StringUtil.isEmpty(bean.getValueString(infoProperty.getFieldName()))){
					bean.setValue(infoProperty.getFieldName(), SessionFactory.getUser().getXm());
				}
				editables.add(infoProperty);
			}
			else if(infoProperty.getEditable()&&bean.getValues().get(infoProperty.getFieldName())!=null){
				editables.add(infoProperty);
			}
		}
		bean.setEditables(editables);
		if( createBusiness( bean ).isAdd() ) {
			bean.setValue( "globalid", Sequencer.timeSequence() );
			DynaBeanValidateUtil.validate(bean);
			doLog( bean, IOperationConfig.ADD );
			dao.insert( bean );
			bean.setEditables(olfEditables);
			return true;
		}
		bean.setEditables(olfEditables);
		throw new RuleException("该信息类最多保存一条记录，无法增加！");
	}
	
	@Override
	public boolean addRecordNoCheckGh(DynaBean bean) {
		List<InfoProperty> olfEditables=bean.getEditables();
		List<InfoProperty> editables=new ArrayList<InfoProperty>();
		for (InfoProperty infoProperty : bean.getClazz().getProperties()) {
			if(Type.CREATOR.equals(infoProperty.getFieldType())){
				if(StringUtil.isEmpty(bean.getValueString(infoProperty.getFieldName()))){
					bean.setValue(infoProperty.getFieldName(), SessionFactory.getUser().getXm());
				}
				editables.add(infoProperty);
			}
			else if(infoProperty.getEditable()&&bean.getValues().get(infoProperty.getFieldName())!=null){
				editables.add(infoProperty);
			}
		}
		
		String  staffid= (String)bean.getValue("gh");
		if(StringUtils.isNotEmpty(staffid)) {
			DynaBeanBusiness business = new DynaBeanBusiness( bean.getClazz(), existPerson( bean.getClazz(), staffid ) );
			if(!business.isAdd()){
				throw new RuleException("该信息类最多保存一条记录，无法增加！");
			}
		}
		
		bean.setEditables(editables);
		bean.setValue( "globalid", Sequencer.timeSequence() );
		DynaBeanValidateUtil.validate(bean);
		doLog( bean, IOperationConfig.ADD );
		dao.insert( bean );
		bean.setEditables(olfEditables);
		return true;
	}

	public void removeBean(DynaBean bean) throws DynaBeanException {
		if( bean == null ) {
			return;
		}
		removeFileAndImage(bean);
		dao.delete( bean );
	}

	@Override
	public boolean modifyRecord(DynaBean bean) throws DynaBeanException {
		List<InfoProperty> olfEditables=bean.getEditables();
		List<InfoProperty> editables=new ArrayList<InfoProperty>();
		for (InfoProperty infoProperty : bean.getEditables()) {
			if(bean.getValues().get(infoProperty.getFieldName())!=null){
				editables.add(infoProperty);
			}
		}
		bean.setEditables(editables);
		if( createBusiness( bean ).isModify() ) {
			doLog( bean, IOperationConfig.MODIFY );
			DynaBeanValidateUtil.validate(bean);
			dao.update( bean );
			bean.setEditables(olfEditables);
			return true;
		}
		bean.setEditables(olfEditables);
		throw new RuleException("该记录不允许修改！");
	}
	
	@Override
	public boolean modifyRecordNoCheckGh(DynaBean bean) {
		List<InfoProperty> olfEditables=bean.getEditables();
		List<InfoProperty> editables=new ArrayList<InfoProperty>();
		for (InfoProperty infoProperty : bean.getEditables()) {
			if(bean.getValues().get(infoProperty.getFieldName())!=null){
				editables.add(infoProperty);
			}
		}
		
		String  staffid= (String)bean.getValue("gh");
		if(StringUtils.isNotEmpty(staffid)) {
			DynaBeanBusiness business = new DynaBeanBusiness( bean.getClazz(), existPerson( bean.getClazz(), staffid ) );
			if(!business.isModify()){
				throw new RuleException("该记录不允许修改！");
			}
		}
		
		bean.setEditables(editables);
		doLog( bean, IOperationConfig.MODIFY );
		DynaBeanValidateUtil.validate(bean);
		dao.update( bean );
		bean.setEditables(olfEditables);
		return true;
	}

	@Override
	public boolean modifyRecord(DynaBean bean, Map<String, Object> values, boolean compel) throws DynaBeanException {
		List<InfoProperty> olfEditables=bean.getEditables();
		List<InfoProperty> editables=new ArrayList<InfoProperty>();
		for(String pName : values.keySet()){
			InfoProperty property = bean.getClazz().getPropertyByName(pName);
			if(property!=null&&(compel||property.getEditable())){
				Object val = values.get(pName);
				if(property.getNeed()
						&&(val==null || StringUtils.isEmpty(val.toString()))){
						throw new RuleException(property.getDescription() + "不能为空");
					
				}
				bean.setValue(pName, val);
				editables.add(property);
			}
		}
		if( createBusiness( bean ).isModify() ) {
			doLog( bean, IOperationConfig.MODIFY );
			bean.setEditables(editables);
			dao.update( bean );
			bean.setEditables(olfEditables);
			return true;
		}
		bean.setEditables(olfEditables);
		throw new RuleException("该记录不允许修改！");
	}
	
	@Override
	public boolean modifyRecordNoCheckGh(DynaBean bean,Map<String, Object> values, boolean compel) {
		List<InfoProperty> olfEditables=bean.getEditables();
		List<InfoProperty> editables=new ArrayList<InfoProperty>();
		for(String pName : values.keySet()){
			InfoProperty property = bean.getClazz().getPropertyByName(pName);
			if(property!=null&&(compel||property.getEditable())){
				Object val = values.get(pName);
				if(property.getNeed()
						&&(val==null || StringUtils.isEmpty(val.toString()))){
						throw new RuleException(property.getDescription() + "不能为空");
					
				}
				bean.setValue(pName, val);
				editables.add(property);
			}
		}
		
		String  staffid= (String)bean.getValue("gh");
		if(StringUtils.isNotEmpty(staffid)) {
			DynaBeanBusiness business = new DynaBeanBusiness( bean.getClazz(), existPerson( bean.getClazz(), staffid ) );
			if(!business.isModify()){
				throw new RuleException("该记录不允许修改！");
			}
		}
		
		doLog( bean, IOperationConfig.MODIFY );
		bean.setEditables(editables);
		dao.update( bean );
		bean.setEditables(olfEditables);
		return true;
	}

	@Override
	public void modifyBean(DynaBean bean) throws DynaBeanException {
		if( bean == null ) {
			return;
		}
		dao.update( bean );
	}
	
	/**
	 * 创建业务操作类
	 * @param bean 动态Bean
	 * @return
	 */
	private DynaBeanBusiness createBusiness(DynaBean bean) {
		String staffid = (String)bean.getValue("gh");
		if( staffid == null || "".equals(staffid) ) {
			throw new RuleException("职工号不得为空！");
		}
		
		DynaBeanBusiness business = new DynaBeanBusiness( bean.getClazz(), existPerson( bean.getClazz(), staffid ) );
		return business;
	}
	
	public boolean existPerson(String staffid) {
		InfoClass clazz = ( InfoClass ) InfoClassCache.getOverallInfoClass( IConstants.INFO_CATALOG_TYPE_DEFAULT );
		
		return existPerson( clazz, staffid );
	}
	
	@Override
	public void addPerson(String staffid) {
		if( existPerson( staffid ) ) {
			throw new RuleException("人员已存在，不能新增！");
		}
		InfoClass clazz = ( InfoClass ) InfoClassCache.getOverallInfoClass( IConstants.INFO_CATALOG_TYPE_DEFAULT );
		InfoProperty p = clazz.getPropertyByName("xm");
		clazz.setProperties( new ArrayList<InfoProperty>() );
		clazz.addProperty(p);
		DynaBean bean = new DynaBean( clazz );
		bean.setValue( "gh", staffid );
		bean.setValue( "xm", staffid );
		addBean( bean );
	}
	
	@Override
	public void deletePerson(String staffid) {
		if( existPerson(staffid) ) {
			for (InfoClass clazz : InfoClassCache.getInfoClasses( IConstants.INFO_CATALOG_TYPE_DEFAULT ) ) {
				DynaBean bean = new DynaBean( clazz );
				bean.setValue( "gh", staffid );
				removeFileAndImage(bean);
				dao.delete(bean);
			}
		}
	}
	
	/**
	 * 判断教职工是否已存在于指定的信息类
	 * @param clazz
	 * @param staffid
	 * @return
	 */
	private boolean existPerson( InfoClass clazz, String staffid ) {
		DynaBeanQuery query = new DynaBeanQuery( clazz );
		
		query.setDeleted( null );
		query.setExpress("gh == #{params.staffid}");
		query.setParam( "staffid", staffid );
		query.setDeleted(false);
		
		return dao.findCount(query) > 0 ? true : false;
	}
	
	/**
	 * 执行新增、修改、删除操作引发的日志记录变更
	 * @throws DynaBeanException
	 */
	private void doLog( DynaBean bean, String operation ) throws DynaBeanException {
//		bean.setValue( "operator_", SessionFactory.getUser().getYhm() );
//		bean.setValue( "operation_", operation );
		
//		logDao.insert( bean );
		if(bean.getClazz().getCatalog().getTypeInfo().isCreateLog())
			dynaBeanLogBusiness.doLog(bean, operation);
		doPass( bean );
	}
	
	/**
	 * 记录生效后的操作
	 * @throws DynaBeanException 如果操作出现异常
	 */
	public void doPass( DynaBean bean ) throws DynaBeanException {
		
		doSync( bean );
		doUniquely( bean );
		doAfterModify(bean);
	}
	
	private void doAfterModify(DynaBean bean){
		Map<String, IDynaBeanModifyAfterBusiness>  map = 
			SpringHolder.getBeansOfType(IDynaBeanModifyAfterBusiness.class);
		for (IDynaBeanModifyAfterBusiness business : map.values()) {
			business.doAfterModify(bean);
		}
	} 
	/**
	 * 保证数据唯一性
	 * <p>
	 * 唯一字段保证唯一
	 * </p>
	 * @param bean 动态Bean
	 * @throws DynaBeanException 如果操作出现异常
	 */
	private void doUniquely( DynaBean bean ) throws DynaBeanException {
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put( "table", bean.getClazz().getIdentityName() );
		params.put( "staffid", String.valueOf( bean.getValue("gh") ) );
		params.put( "globalid", String.valueOf( bean.getValue("globalid") ) );
		
		for ( InfoProperty property : bean.getClazz().getUniqables() ) {
			if( property.getEditable() && "1".equals( bean.getValue( property.getFieldName() ) ) ) {
				params.put( "column", property.getFieldName() );
				dao.updateUniquely(params);
			}
		}
	}
	
	/**
	 * 执行同步操作
	 * @param bean 动态Bean对象
	 * @throws DynaBeanException 
	 */
	private void doSync( DynaBean bean ) throws DynaBeanException {
		InfoClass overall = InfoClassCache.getOverallInfoClass( IConstants.INFO_CATALOG_TYPE_DEFAULT );
		
		SyncBean sync = new SyncBean( overall, bean.getValue("gh").toString() );
		
		//遍历所有可编辑的字段
		for ( InfoProperty property : bean.getClazz().getEditables() ) {
			
			//该字段无需同步
			if( property.getSyncToField() == null || "".equals( property.getSyncToField() ) ) {
				continue;
			}
			
			/*
			 * 同步条件为空，表示直接进行同步操作
			 * 如果同步条件的字段值为1，也进行同步操作
			 */
			if( property.getSyncCondition() == null || "".equals( property.getSyncCondition() ) 
					|| "1".equals( bean.getValue( property.getSyncCondition() ) ) ) {
				
				sync.add( overall.getPropertyById( property.getSyncToField() ).getFieldName()
						, bean.getValue( property.getFieldName() ) );
			}
		}
		
		if( sync.getFields().size() > 0 ) {
			dao.updateSync( sync );
			DynaBeanUtil.clear(bean.getValue("gh").toString());
		}
	}
	
	@Override
	public PageList<DynaBean> findPagingInfoList(DynaBeanQuery query){
		PageList<DynaBean> pageList = new PageList<DynaBean>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			String old = orderStr2Query(query);
			paginator.setItems( dao.findCount(query) );
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				//获得DynaBean集合
				List<DynaBean> beans = new ArrayList<DynaBean>();
				List<Map<String, Object>> list = dao.findPagingInfoList( query );
				for (Map<String, Object> map : list) {
					DynaBean bean = new DynaBean( query.getClazz() );
					bean.setValues( map );
					beans.add(bean);
				}
				pageList.addAll(beans);
			}
			query.setOrderStr(old);
		}
		return pageList;
	}
	
	@Override
	public PageList<DynaBean> findPagingInfoLeftJoinOverall(DynaBeanQuery query){
		PageList<DynaBean> pageList = new PageList<DynaBean>();
		Paginator paginator = new Paginator();
		if(query!=null){
			String old = orderStr2Query(query);
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems( dao.findCount(query) );
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				//获得DynaBean集合
				List<DynaBean> beans = new ArrayList<DynaBean>();
				List<Map<String, Object>> list = dao.findPagingInfoListLeftJoinOverAll( query );
				for (Map<String, Object> map : list) {
					DynaBean bean = new DynaBean( query.getClazz() );
					bean.setValues( map );
					beans.add(bean);
				}
				pageList.addAll(beans);
			}
			query.setOrderStr(old);
		}
		return pageList;
	}

	@Override
	public int findCount(DynaBeanQuery query){
		return dao.findCount( query );
	}
	@Override
	public int findCountNoUniqable(Map<String,Object> paraMap) {
		return dao.findCountNoUniqable(paraMap);
	}
	@Override
	public int findCountNoUniqableForCJ(Map<String,Object> paraMap) {
		return dao.findCountNoUniqableForCJ(paraMap);
	}
	public void setDynaBeanLogBusiness(IDynaBeanLogBusiness dynaBeanLogBusiness) {
		this.dynaBeanLogBusiness = dynaBeanLogBusiness;
	}
	@Override
	public List<Map<String, Object>> findListNoUniqable(Map<String,Object> paraMap)
			throws DynaBeanException {
		DynaBeanQuery query = (DynaBeanQuery)paraMap.get("query");
		String old="";
		if(query!=null){
			old=orderStr2Query(query);
		}
		//System.out.println("list执行前"+TimeUtil.current("yyyy-MM-dd HH:mm:ss"));
		List<Map<String, Object>> list = dao.findListNoUniqable(paraMap);
		//System.out.println("list执行后"+TimeUtil.current("yyyy-MM-dd HH:mm:ss"));
		if(query!=null){
			query.setOrderStr(old);
		}
		return list;
	}
	@Override
	public List<Map<String, Object>> findListNoUniqableForCJ(Map<String,Object> paraMap)
			throws DynaBeanException {
		DynaBeanQuery query = (DynaBeanQuery)paraMap.get("query");
		String old="";
		if(query!=null){
			old=orderStr2Query(query);
		}
		List<Map<String, Object>> list = dao.findListNoUniqableForCJ(paraMap);
		if(query!=null){
			query.setOrderStr(old);
		}
		return list;
	}

	@Override
	public List<DynaBean> findList(DynaBeanQuery query)
			throws DynaBeanException {
		List<DynaBean> beans = new ArrayList<DynaBean>();
		String old =orderStr2Query(query);
		List<Map<String, Object>> list = dao.findList( query );
		query.setOrderStr(old);
		for (Map<String, Object> map : list) {
			DynaBean bean = new DynaBean( query.getClazz() );
			bean.setValues( map );
			beans.add(bean);
		}
		return beans;
	}

	@Override
	public boolean removeRecord(DynaBean bean) throws DynaBeanException {
		bean = findById(bean);//bean信息不全，为记录日志，请求完整bean
		List<InfoProperty> olfEditables=bean.getEditables();
		List<InfoProperty> editables=new ArrayList<InfoProperty>();
		for (InfoProperty infoProperty : bean.getEditables()) {
			if(bean.getValues().get(infoProperty.getFieldName())!=null){
				editables.add(infoProperty);
			}
		}
		if( createBusiness( bean ).isRemove() ) {
			bean.setEditables(editables);
			dao.update( bean );
			doLog( bean, "remove" );
			removeFileAndImage(bean);
			dao.delete( bean );
			bean.setEditables(olfEditables);
			
			return true;
		}
		
		throw new RuleException("该信息类最少保流一条记录，无法删除！");
		
	}
	
	
	
	@Override
	public boolean removeRecordNoCheckGh(DynaBean bean)
			throws DynaBeanException {
		
		if(bean.getClazz().getCatalog().getTypeInfo().isCreateLog())
			dynaBeanLogBusiness.doLog(bean, "remove");
		removeFileAndImage(bean);
		dao.delete( bean );
		return true;
	}

	private String orderStr2Query(DynaBeanQuery query){
		String oldStr = query.getOrderStr();
		String pxzd=query.getClazz().getPxzd();
		if (!StringUtil.isEmpty(pxzd)) {
			pxzd="t."+pxzd;
			if(!StringUtil.isEmpty(oldStr))
			{
				pxzd=oldStr+","+pxzd;
			}
			query.setOrderStr(pxzd);
		}
		return oldStr;
	}
	
	@Override
	public void deleteForCJ(Map<String, Object> paraMap){
		dao.deleteForCJ(paraMap);
	}

	@Override
	public void doImportData(List<DynaBean> beans, String gh, boolean compel) {
		for (DynaBean dynaBean : beans) {
			dynaBean.setValue("gh", gh);
			if(!dynaBean.getClazz().getTypeInfo().isMoreThanOne()){
				DynaBeanQuery query = new DynaBeanQuery( dynaBean.getClazz() );
				query.setDeleted( null );
				query.setExpress("gh == #{params.staffid}");
				query.setParam( "staffid", gh );
				query.setDeleted(false);
				List<DynaBean> list = queryBeans(query);
				if(list.size()>0){
					modifyRecord(list.get(0), dynaBean.getValues(), compel);
					continue;
				}
			}
			if("JBXXB".equals(dynaBean.getClazz().getIdentityName())){
				addPerson(gh);
			}
			if(!existPerson(gh)){
				throw new RuleException("工号为"+gh+"的教职工不存在！");
			}
			List<InfoProperty> olfEditables=dynaBean.getEditables();
			List<InfoProperty> editables=new ArrayList<InfoProperty>();
			for (String pName : dynaBean.getValues().keySet()) {
				InfoProperty property = dynaBean.getClazz().getPropertyByName(pName);
				if(property!=null&&(compel||property.getEditable())){
					Object val = dynaBean.getValues().get(pName);
					if(property.getNeed()
							&&(val==null || StringUtils.isEmpty(val.toString()))){
							throw new RuleException(property.getDescription() + "不能为空");
						
					}
					editables.add(property);
				}
			}
			dynaBean.setEditables(editables);
			
			if( createBusiness( dynaBean ).isAdd() ) {
				dynaBean.setValue( "globalid", Sequencer.timeSequence() );
				doLog( dynaBean, IOperationConfig.ADD );
				dao.insert( dynaBean );
				dynaBean.setEditables(olfEditables);
			}
			
			dynaBean.setEditables(olfEditables);
		}
		
	}
}
