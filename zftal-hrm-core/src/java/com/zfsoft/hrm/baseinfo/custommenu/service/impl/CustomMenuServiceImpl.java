package com.zfsoft.hrm.baseinfo.custommenu.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.custommenu.business.ICustomMenuBusiness;
import com.zfsoft.hrm.baseinfo.custommenu.entity.Condition;
import com.zfsoft.hrm.baseinfo.custommenu.entity.CustomMenu;
import com.zfsoft.hrm.baseinfo.custommenu.query.CustomMenuQuery;
import com.zfsoft.hrm.baseinfo.custommenu.service.ICustomMenuService;
import com.zfsoft.hrm.config.Direction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.menu.business.IMenuBusiness;
import com.zfsoft.hrm.menu.business.IMenuOperateBusiness;
import com.zfsoft.hrm.menu.entity.MenuOperate;
import com.zfsoft.util.base.StringUtil;

/** 
 * @author jinjj
 * @date 2012-12-18 下午03:58:47 
 *  
 */
public class CustomMenuServiceImpl implements ICustomMenuService {

	private ICustomMenuBusiness customMenuBusiness;
	private IMenuBusiness menuBusiness;
	
	private void addMenuOperation(String gnmkdm) {
		IMenuOperateBusiness menuOperateBusiness = SpringHolder.getBean("menuOperateBusiness",IMenuOperateBusiness.class);
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
	
	@Override
	public void save(CustomMenu custom) {
		IndexModel model = new IndexModel();
		model.setFjgndm(IConstants.CATALOG_QUERY_ROOT_MENU);
		model.setGnmkmc(custom.getName());
		model.setDyym("");
		menuBusiness.addMenu(model);
		model.setDyym("/normal/catalogQuery_list.html?menuId="+model.getGnmkdm());
		menuBusiness.modify(model);
		custom.setMenuId(model.getGnmkdm());
		
		String json = "";
		List<Condition> conditions = getJsonValue(custom.getConditions());
		if(conditions != null && conditions.size() > 0){
			JSONArray jsonArray = JSONArray.fromObject(conditions);
			json = jsonArray.toString();
		}
		custom.setConditonJson(json);
		customMenuBusiness.save(custom);
		addMenuOperation(custom.getMenuId());
	}

	private List<Condition> getJsonValue(List<Condition> conditions){
		List<Condition> list = new ArrayList<Condition>();
		for(Condition condition : conditions){
			if(StringUtil.isNotEmpty(condition.getName()) && StringUtil.isNotEmpty(condition.getValue())){
				list.add(condition);
			}
		}
		return list;
	}
	
	@Override
	public void delete(String menuId) {
		customMenuBusiness.delete(menuId);
		menuBusiness.remove(menuId);
	}

	@Override
	public void update(CustomMenu custom) {
		List<Condition> conditions = getJsonValue(custom.getConditions());
		if(conditions != null && conditions.size() > 0){
			JSONArray jsonArray = JSONArray.fromObject(conditions);
			custom.setConditonJson(jsonArray.toString());
		}else{
			custom.setConditonJson("");
		}
		customMenuBusiness.update(custom);
		IndexModel model = menuBusiness.getById(custom.getMenuId());
		model.setGnmkmc(custom.getName());
		menuBusiness.modify(model);
		addMenuOperation(model.getGnmkdm());
	}

	@Override
	public CustomMenu getById(String menuId) {
		CustomMenu customMenu = customMenuBusiness.getById(menuId);
		if(customMenu.getConditonJson() != null){
			JSONArray jsonArray = JSONArray.fromObject(customMenu.getConditonJson());
			customMenu.setConditions(JSONArray.toList(jsonArray, Condition.class));
		}
		return customMenu;
	}

	@Override
	public PageList<CustomMenu> getPageList(CustomMenuQuery query) {
		return customMenuBusiness.getPageList(query);
	}
	
	public void doMenuMove(String menuId,Direction dirc){
		menuBusiness.menuMove(menuId,dirc);
	}

	public void setCustomMenuBusiness(ICustomMenuBusiness customMenuBusiness) {
		this.customMenuBusiness = customMenuBusiness;
	}

	public void setMenuBusiness(IMenuBusiness menuBusiness) {
		this.menuBusiness = menuBusiness;
	}

}
