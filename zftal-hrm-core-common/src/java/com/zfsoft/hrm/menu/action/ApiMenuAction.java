package com.zfsoft.hrm.menu.action;


import java.util.List;

import com.zfsoft.dao.entities.ApiIndexModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.menu.business.IApiMenuBusiness;
import com.zfsoft.util.base.StringUtil;

public class ApiMenuAction  extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1068685671186597358L;

	private IApiMenuBusiness apiMenuBusiness;
	/**
	 * 菜单列表（展现对象）
	 */
	private PageList<ApiIndexModel> menus;
	private ApiIndexModel model = new ApiIndexModel();
	private ApiIndexModel query = new ApiIndexModel();
	private String op;
	
	public String list(){
		List<ApiIndexModel> listOne = apiMenuBusiness.getAllList();
		getValueStack().set("listOne", listOne);
		List<ApiIndexModel> listTwo = apiMenuBusiness.getAllList();
		getValueStack().set("listTwo", listTwo);
		return "list";
	}
	
	public String page(){
		String menuId = "N";
		if (StringUtil.isEmpty(query.getFjgndm())) {
			query.setFjgndm(menuId);
		}
		menus = apiMenuBusiness.getMenu(query);
		model.setGnmkdm(menuId);
		return "page";
	}
	
	public String toEdit(){
		if (model!=null&&!StringUtil.isEmpty(model.getGnmkdm())) {
			query.setGnmkdm(model.getGnmkdm());
			model=apiMenuBusiness.getMenu(query).get(0);
			op = "modify";
		}else{
			String fjgndm="";
			if (model!=null) {
				fjgndm = model.getFjgndm();
			}
			model = new ApiIndexModel();
			model.setFjgndm(fjgndm);
			op = "add";
		}
		return "edit";
	}
	
	public String remove(){
		query.setFjgndm(model.getGnmkdm());
		menus = apiMenuBusiness.getMenu(query);
		if (menus!=null&&menus.size()>0) {
			setErrorMessage("不能删除含子菜单的菜单，请先删除所属子菜单");
		}else {
			apiMenuBusiness.remove(model.getGnmkdm());
			setSuccessMessage("删除成功");
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String updateSfqy(){
		apiMenuBusiness.updateSfqy(model);
		setSuccessMessage("操作成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String save(){
		if ("add".equals(op)) {
			model.setSfqy("1");
			apiMenuBusiness.insertMenu(model);
		}else{
			apiMenuBusiness.modify(model);
		}
		setSuccessMessage("保存成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public IApiMenuBusiness getApiMenuBusiness() {
		return apiMenuBusiness;
	}
	public void setApiMenuBusiness(IApiMenuBusiness apiMenuBusiness) {
		this.apiMenuBusiness = apiMenuBusiness;
	}
	public PageList<ApiIndexModel> getMenus() {
		return menus;
	}

	public void setMenus(PageList<ApiIndexModel> menus) {
		this.menus = menus;
	}

	public ApiIndexModel getModel() {
		return model;
	}
	public void setModel(ApiIndexModel model) {
		this.model = model;
	}
	public ApiIndexModel getQuery() {
		return query;
	}
	public void setQuery(ApiIndexModel query) {
		this.query = query;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	
}
