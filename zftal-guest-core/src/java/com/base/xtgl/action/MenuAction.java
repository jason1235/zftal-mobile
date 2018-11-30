package com.base.xtgl.action;


import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.util.base.StringUtil;
import com.base.xtgl.entity.IndexModel;
import com.base.xtgl.service.IMenuBusiness;


public class MenuAction  extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1068685671186597358L;

	private IMenuBusiness menubusiness;
	/**
	 * 菜单列表（展现对象）
	 */
	private PageList<IndexModel> menus;
	private IndexModel model = new IndexModel();
	private IndexModel query = new IndexModel();
	private String op;
	
	
	public String page(){
		String menuId = "N";
		if (StringUtil.isEmpty(query.getFjgndm())) {
			query.setFjgndm(menuId);
		}
		menus = menubusiness.getMenu(query);
		model.setGnmkdm(menuId);
		return "page";
	}
	
	public String toEdit(){
		if (model!=null&&!StringUtil.isEmpty(model.getGnmkdm())) {
			query.setGnmkdm(model.getGnmkdm());
			model=menubusiness.getMenu(query).get(0);
			op = "modify";
		}else{
			String fjgndm="";
			if (model!=null) {
				fjgndm = model.getFjgndm();
			}
			model = new IndexModel();
			model.setFjgndm(fjgndm);
			op = "add";
		}
		return "edit";
	}
	
	public String remove(){
		query.setFjgndm(model.getGnmkdm());
		menus = menubusiness.getMenu(query);
		if (menus!=null&&menus.size()>0) {
			setErrorMessage("不能删除含子菜单的菜单，请先删除所属子菜单");
		}else {
			menubusiness.remove(model.getGnmkdm());
			setSuccessMessage("删除成功");
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String updateSfqy(){
		menubusiness.updateSfqy(model);
		setSuccessMessage("操作成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String save(){
		if ("add".equals(op)) {
			model.setSfqy("1");
			menubusiness.insertMenu(model);
		}else{
			menubusiness.modify(model);
		}
		setSuccessMessage("保存成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	

	public IMenuBusiness getMenubusiness() {
		return menubusiness;
	}

	public void setMenubusiness(IMenuBusiness menubusiness) {
		this.menubusiness = menubusiness;
	}

	public PageList<IndexModel> getMenus() {
		return menus;
	}

	public void setMenus(PageList<IndexModel> menus) {
		this.menus = menus;
	}

	public IndexModel getModel() {
		return model;
	}
	public void setModel(IndexModel model) {
		this.model = model;
	}
	public IndexModel getQuery() {
		return query;
	}
	public void setQuery(IndexModel query) {
		this.query = query;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	
}
