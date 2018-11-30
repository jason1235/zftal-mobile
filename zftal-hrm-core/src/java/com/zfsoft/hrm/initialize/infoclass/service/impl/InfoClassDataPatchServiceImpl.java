package com.zfsoft.hrm.initialize.infoclass.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.hrm.baseinfo.infoclass.dao.daointerface.ICatalogDao;
import com.zfsoft.hrm.baseinfo.infoclass.dao.daointerface.IInfoClassDao;
import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.query.CatalogQuery;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoClassQuery;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.config.enums.UserType;
import com.zfsoft.hrm.initialize.infoclass.service.IInfoClassDataPatchService;
import com.zfsoft.hrm.menu.business.IMenuBusiness;
import com.zfsoft.hrm.menu.business.IMenuOperateBusiness;
import com.zfsoft.hrm.menu.entity.MenuOperate;

/** 
 * 
 * 信息类数据补丁(功能扩展，数据修正)
 * @author jinjj
 * @date 2013-1-29 下午03:45:49 
 *  
 */
public class InfoClassDataPatchServiceImpl implements IInfoClassDataPatchService {

	private IMenuBusiness menuBusiness;
	
	private IMenuOperateBusiness menuOperateBusiness;
	
	private ICatalogDao catalogDao;
	
	private IInfoClassDao infoClassDao;
	
	Log log = LogFactory.getLog(InfoClassDataPatchServiceImpl.class);
	
	private String[] excludeMenu = new String[]{"N1180"};//信息审核功能
	
	/**
	 * 补丁处理(信息类菜单数据修复)
	 */
	@Override
	public void doPatch(){
		IndexModel model = menuBusiness.getById(IConstants.PERSON_INFO_ROOT_MENU+"01");
		if(model == null){
			log.info("信息类菜单数据缺失，将进行自动处理...");
			clearMenu();
			updateCatalogMenu();
		}else{
			log.info("信息类菜单数据验证通过");
		}
	}
	
	private void clearMenu(){
		List<IndexModel> list = menuBusiness.getMenuByFartherId(IConstants.PERSON_INFO_ROOT_MENU);
		for(IndexModel model : list){
			boolean include = true;
			for(String str : excludeMenu){
				if(model.getGnmkdm().equals(str)){
					include = false;
				}
			}
			if(include){
				clearSubMenu(model.getGnmkdm());
				menuBusiness.remove(model.getGnmkdm());
			}
		}
	}
	
	private void clearSubMenu(String fartherMenuId){
		List<IndexModel> list = menuBusiness.getMenuByFartherId(fartherMenuId);
		for(IndexModel model : list){
			menuBusiness.remove(model.getGnmkdm());
			menuOperateBusiness.deleteByMenuId(model.getGnmkdm());
		}
	}
	
	private void updateCatalogMenu(){
		CatalogQuery query = new CatalogQuery();
		query.setType(UserType.TEACHER.name().toLowerCase());
		List<Catalog> list = catalogDao.findList(query);
		for(Catalog c : list){
			IndexModel model = new IndexModel();
			model.setGnmkmc(c.getName());
			model.setDyym("");
			model.setFjgndm(IConstants.PERSON_INFO_ROOT_MENU);
			menuBusiness.addMenu(model, false, excludeMenu);
			c.setMenuId(model.getGnmkdm());
			catalogDao.update(c);
			updateInfoClassMenu(c);
		}
	}
	
	private void updateInfoClassMenu(Catalog c){
		InfoClassQuery query = new InfoClassQuery();
		query.setCatalogId(c.getGuid());
		List<InfoClass> list = infoClassDao.findList(query);
		
		for(InfoClass clazz : list){
			if(!clazz.getTypeInfo().isEditable()){
				continue;//排除OVERALL的信息类
			}
			IndexModel model = new IndexModel();
			model.setGnmkmc(clazz.getName());
			model.setDyym("/normal/staffInfo_list.html?classId="+clazz.getGuid());
			model.setFjgndm(c.getMenuId());
			menuBusiness.addMenu(model, false, excludeMenu);
			clazz.setMenuId(model.getGnmkdm());
			infoClassDao.update(clazz);
			
			//处理菜单操作
			MenuOperate operate = new MenuOperate();
			operate.setMenuId(model.getGnmkdm());
			operate.setOperate("xg");
			menuOperateBusiness.insert(operate);
			if(!model.getGnmkdm().equals(IConstants.PERSON_INFO_ROOT_MENU+"0101")){
				operate.setOperate("zj");
				menuOperateBusiness.insert(operate);
				operate.setOperate("sc");
				menuOperateBusiness.insert(operate);
			}
		}
	}

	public void setMenuBusiness(IMenuBusiness menuBusiness) {
		this.menuBusiness = menuBusiness;
	}

	public void setCatalogDao(ICatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}

	public void setInfoClassDao(IInfoClassDao infoClassDao) {
		this.infoClassDao = infoClassDao;
	}

	public void setMenuOperateBusiness(IMenuOperateBusiness menuOperateBusiness) {
		this.menuOperateBusiness = menuOperateBusiness;
	}

}
