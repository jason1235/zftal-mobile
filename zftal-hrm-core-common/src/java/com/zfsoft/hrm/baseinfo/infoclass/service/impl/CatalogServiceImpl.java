package com.zfsoft.hrm.baseinfo.infoclass.service.impl;

import java.util.List;
import java.util.Properties;

import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.hrm.baseinfo.infoclass.dao.daointerface.ICatalogDao;
import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.exception.InfoClassException;
import com.zfsoft.hrm.baseinfo.infoclass.query.CatalogQuery;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoClassQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.ICatalogService;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoClassService;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.menu.business.IMenuBusiness;

/**
 * {@link ICatalogService }的缺省实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-18
 * @version V1.0.0
 */
public class CatalogServiceImpl implements ICatalogService {

	private ICatalogDao dao;
	
	private IInfoClassService infoClassService;
	
	private IMenuBusiness menuBusiness;
	
	@Override
	public void add(Catalog entity) throws InfoClassException {
		
		int count = dao.findCount( new CatalogQuery( entity.getName(), entity.getType() ) );
		
		if( count > 0 ) {
			throw new RuleException("已存在相同的目录名称！");
		}
		Properties properties = entity.getTypeInfo().getMenus();
		if(properties.size()>0){
			for (String m : properties.stringPropertyNames()) {
				addMenu(entity,m,new String[0]);
			}
		}
		count = dao.findCount( new CatalogQuery( null, entity.getType() ) );
		entity.setIndex(count);
//		entity.setMenuId(model.getGnmkdm());
		dao.insert(entity);
	}



	/** 
	 * @param entity
	 */
	private void addMenu(Catalog entity,String ROOT_MENU,String[] excludeMenu) {
		IndexModel model = new IndexModel();
		model.setGnmkmc(entity.getName());
		//model.setFjgndm(ROOT_MENU);
		model.setFjgndm("N99");//要求有单独的模块来看信息类的数据，故这里用常量，张旭修改
		model.setDyym("");
		model.setIsAuto("1");
		menuBusiness.addMenu(model,false,excludeMenu);//排除审核反馈二级菜单
	}
	
	

	@Override
	public Catalog getEntity(String guid) throws InfoClassException {
		if( guid == null || "".equals(guid) ){
			return null;
		}
		
		return dao.findById(guid);
	}

	@Override
	public List<Catalog> getList(CatalogQuery query) throws InfoClassException {
		
		return dao.findList(query);
	}
	
	@Override
	public List<Catalog> getFullList(CatalogQuery query) throws InfoClassException {
		List<Catalog> catalogs = dao.findList(query);
		
		for (Catalog catalog : catalogs) {
			List<InfoClass> classes = infoClassService.getCatalogInfoClasses( catalog.getGuid() );
			catalog.setClasses( classes );
		}
		
		return catalogs;
	}

	@Override
	public void modify(Catalog entity) throws InfoClassException {
		List<Catalog> list = dao.findList( new CatalogQuery( entity.getName(), null ) );
		
		for (Catalog catalog : list) {
			if( !catalog.getGuid().equals(entity.getGuid())) {
				throw new RuleException("已存在相同的目录名称！");
			}
		}

		Catalog oldBean = dao.findById(entity.getGuid());
		List<IndexModel> menuList = menuBusiness.getByName(oldBean.getName());
		boolean flag = false;
		//更新已有菜单
		Properties properties = entity.getTypeInfo().getMenus();
		if(properties.size()>0){
			for (String m : properties.stringPropertyNames()) {
				for(IndexModel model : menuList){
					//if(model.getFjgndm().equals(m)){
					if(model.getFjgndm().equals("N99")){
						model.setGnmkmc(entity.getName());
						updateMenu(model);
						flag = true;
					}
				}
				if(!flag){
					addMenu(entity,m,new String[0]);
				}
			}
		}
		dao.update(entity);
	}



	/** 
	 * @param entity
	 */
	private void updateMenu(IndexModel model) {
		model.setDyym("");
		menuBusiness.modify(model);
	}

	@Override
	public void remove(String guid) throws InfoClassException {
		
		if( hasInfoClass( guid ) ) {
			throw new RuleException("该目录下存在信息类！");
		}
		
		preIndex( getEntity( guid ).getIndex() );
		
		Catalog c = dao.findById(guid);
		dao.delete(guid);
		List<IndexModel> menuList = menuBusiness.getByName(c.getName());
		
		Properties properties = c.getTypeInfo().getMenus();
		if(properties.size()>0){
			for (String m : properties.stringPropertyNames()) {
				for(IndexModel model : menuList){
					//if(model.getFjgndm().startsWith(m)){
					if(model.getFjgndm().startsWith("N99")){//要求有单独的模块来看信息类的数据，故这里用常量，张旭修改
						menuBusiness.remove(model.getGnmkdm());
					}
				}
			}
		}
		
	}
	
	/**
	 * 从开startIndex开始，对其后面的索引上移一位
	 * @param startIndex 开始索引
	 * @throws InfoClassException 如果操作异常
	 */
	private void preIndex( int startIndex ) throws InfoClassException {
		
		dao.updateIndex( startIndex );
	}
	
	/**
	 * 判断指定的目录是否包含信息类信息
	 * @param guid 目录ID
	 * @return
	 * @throws InfoClassException 如果操作出现异常
	 */
	private boolean hasInfoClass( String guid ) throws InfoClassException {
		InfoClassQuery query = new InfoClassQuery();
		
		query.setCatalogId(guid);
		
		if( infoClassService.count(query) > 0) {
			return true;
		}
		
		return false;
	}

	/**
	 * 注入信息类目录数据操作
	 * @param dao 信息类目录数据操作
	 */
	public void setDao(ICatalogDao dao) {
		this.dao = dao;
	}

	public void setInfoClassService(IInfoClassService infoClassService) {
		this.infoClassService = infoClassService;
	}

	public void setMenuBusiness(IMenuBusiness menuBusiness) {
		this.menuBusiness = menuBusiness;
	}
	
}
