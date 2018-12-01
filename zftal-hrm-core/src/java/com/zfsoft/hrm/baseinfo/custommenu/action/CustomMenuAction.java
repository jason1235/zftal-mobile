package com.zfsoft.hrm.baseinfo.custommenu.action;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.custommenu.entity.CustomMenu;
import com.zfsoft.hrm.baseinfo.custommenu.query.CustomMenuQuery;
import com.zfsoft.hrm.baseinfo.custommenu.service.ICustomMenuService;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.Direction;

/** 
 * @author jinjj
 * @date 2012-12-19 上午08:38:38 
 *  
 */
public class CustomMenuAction extends HrmAction {

	private static final long serialVersionUID = -7119201866035399408L;

	private ICustomMenuService customMenuService;
	
	private PageList<CustomMenu> pageList;
	private CustomMenuQuery query = new CustomMenuQuery();
	private CustomMenu custom;
	private List<InfoProperty> propertyList = new ArrayList<InfoProperty>();
	private String catalogId;
	private int i = 0;
	
	public String page() throws Exception{
		pageList = customMenuService.getPageList(query);
		return "page";
	}
	
	public String input() throws Exception{
		initProperty();
		return "edit";
	}
	
	public String save() throws Exception{
		customMenuService.save(custom);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String edit() throws Exception{
		initProperty();
		custom = customMenuService.getById(custom.getMenuId());
		return "edit";
	}
	
	private void initProperty(){
		InfoClass clazz = InfoClassCache.getOverallInfoClass();
		List<InfoProperty> pList = clazz.getViewables();
		for(InfoProperty p : pList){
			if(p.getTypeInfo().getName().equals(Type.CODE)){
				String name = p.getName().replace("*", "");
				p.setName(name);
				propertyList.add(p);
			}
		}
	}
	
	public String update() throws Exception{
		customMenuService.update(custom);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete() throws Exception{
		customMenuService.delete(custom.getMenuId());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String loadCodeSelect() throws Exception{
//		InfoClass clazz = InfoClassCache.getOverallInfoClass();
//		InfoProperty p = clazz.getPropertyById(custom.getPropertyName());
		catalogId = custom.getCondition().getCodeId();
		return "codeSelect";
	}
	
	public String moveUp() throws Exception{
		customMenuService.doMenuMove(custom.getMenuId(), Direction.UP);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String moveDown() throws Exception{
		customMenuService.doMenuMove(custom.getMenuId(), Direction.DOWN);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public CustomMenuQuery getQuery() {
		return query;
	}

	public void setQuery(CustomMenuQuery query) {
		this.query = query;
	}

	public CustomMenu getCustom() {
		return custom;
	}

	public void setCustom(CustomMenu custom) {
		this.custom = custom;
	}

	public PageList<CustomMenu> getPageList() {
		return pageList;
	}

	public void setCustomMenuService(ICustomMenuService customMenuService) {
		this.customMenuService = customMenuService;
	}

	public List<InfoProperty> getPropertyList() {
		return propertyList;
	}

	public String getCatalogId() {
		return catalogId;
	}

	/**
	 * @return the i
	 */
	public int getI() {
		return i;
	}

	/**
	 * @param i the i to set
	 */
	public void setI(int i) {
		this.i = i;
	}
	
}
