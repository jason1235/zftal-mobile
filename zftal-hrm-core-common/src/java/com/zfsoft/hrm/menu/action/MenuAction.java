package com.zfsoft.hrm.menu.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.dao.entities.IndexModelNew;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.entities.NewJsglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.menu.business.IMenuBusiness;
import com.zfsoft.service.svcinterface.IJsglService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2013-11-15
 * @version V1.0.0
 */
public class MenuAction extends HrmAction{
	private static final long serialVersionUID = 15656565326L;
	/**
	 * 顶级菜单（查询条件）
	 */
	private IMenuBusiness menuBusiness;
	private IJsglService jsglService;//角色管理SERVICE
	/**
	 * 菜单列表（展现对象）
	 */
	private List<IndexModel> menus;
	private PageList<IndexModelNew> pageList;
	private List<JsglModel> jsglModelList;
	private String jsid;
	private IndexModel model = new IndexModel();
	private IndexModelNew query = new IndexModelNew();
	private String op;
	String[] weignmkdm;
	String[] yignmkdm;
	
	
	public IMenuBusiness getMenuBusiness() {
		return menuBusiness;
	}

	public String[] getWeignmkdm() {
		return weignmkdm;
	}

	public void setWeignmkdm(String[] weignmkdm) {
		this.weignmkdm = weignmkdm;
	}

	public String[] getYignmkdm() {
		return yignmkdm;
	}

	public void setYignmkdm(String[] yignmkdm) {
		this.yignmkdm = yignmkdm;
	}
	
	 /**
	 * 为当前角色增加菜单
	 * @return
	 */
	public String insertMenuByJsdm(){
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> tids = new ArrayList<String>();
        for (String idCheck : weignmkdm) {
            tids.add(idCheck.trim());
            
        }
        param.put("tids", tids);
        param.put("jsid", query.getJsid());
        jsglService.insertMenuByJsdm(param);
    	this.setMessage(true, "成功为当前角色增加菜单");
    	getValueStack().set(DATA, getMessage());
		return DATA;
    }
	
	/**
	 * 删除当前角色菜单
	 * @return
	 */
    public String deleteMenuByJsdm(){
    	Map<String, Object> param = new HashMap<String, Object>();
		List<String> tids = new ArrayList<String>();
        for (String idCheck : yignmkdm) {
            tids.add(idCheck.trim());
            
        }
        param.put("tids", tids);
        param.put("jsid", query.getJsid());
        jsglService.deleteMenuByJsdm(param);
    	this.setMessage(true, "成功为当前角色删除菜单");
    	getValueStack().set(DATA, getMessage());
		return DATA;
    }

	public String fpmenu(){
		
		NewJsglModel newJsglQuery = new NewJsglModel();
		newJsglQuery.setJsdm(query.getJsid());
		PageList<NewJsglModel> JsglModelList = jsglService.getJsglModelList(newJsglQuery);
		getValueStack().set("jsglModel", JsglModelList.get(0));
		query.setPerPageSize(10);
		pageList = menuBusiness.getThirdMenuList(query);//未分配菜单列表
		
		JsglModel jsglModel = new JsglModel();
		jsglModel.setJsdm(query.getJsid());
		jsglModel.setGnmkmc(query.getGnmkmc());
		jsglModelList = jsglService.cxJsgnqxListNew(jsglModel);
		return "fpmenu";
	}
	
	public String page(){
		if(	getUser()==null
				||getUser().getJsdms()==null
				||getUser().getJsdms().isEmpty()
				||!getUser().getJsdms().get(0).equals("admin")){
			return "404";
		}
		String menuId = "N";
		if (model!=null&&!StringUtil.isEmpty(model.getGnmkdm())) {
			menuId = model.getGnmkdm();
		}
		menus = menuBusiness.getMenuByFartherId(menuId);
		model.setGnmkdm(menuId);
		return "page";
	}
	
	public String toEdit(){
		if(	getUser()==null
				||getUser().getJsdms()==null
				||getUser().getJsdms().isEmpty()
				||!getUser().getJsdms().get(0).equals("admin")){
			return "404";
		}
		if (model!=null&&!StringUtil.isEmpty(model.getGnmkdm())) {
			model=menuBusiness.getById(model.getGnmkdm());
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
		getValueStack().set("businessInfoClasses",InfoClassCache.getInfoClasses("business"));
		return "edit";
	}
	
	public String remove(){
		if(	getUser()==null
				||getUser().getJsdms()==null
				||getUser().getJsdms().isEmpty()
				||!getUser().getJsdms().get(0).equals("admin")){
			return "404";
		}
		menus = menuBusiness.getMenuByFartherId(model.getGnmkdm());
		if (menus!=null&&menus.size()>0) {
			setErrorMessage("不能删除含子菜单的菜单，请先删除所属子菜单");
		}else {
			menuBusiness.remove(model.getGnmkdm());
			setSuccessMessage("删除成功");
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String save(){
		if(	getUser()==null
				||getUser().getJsdms()==null
				||getUser().getJsdms().isEmpty()
				||!getUser().getJsdms().get(0).equals("admin")){
			return "404";
		}
		if ("add".equals(op)) {
			menuBusiness.insertMenu(model);
		}else{
			menuBusiness.modify(model);
		}
		setSuccessMessage("保存成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 返回
	 */
	public List<IndexModel> getMenus() {
		return menus;
	}
	/**
	 * 设置
	 * @param menus 
	 */
	public void setMenus(List<IndexModel> menus) {
		this.menus = menus;
	}
	/**
	 * 设置
	 * @param menuBusiness 
	 */
	public void setMenuBusiness(IMenuBusiness menuBusiness) {
		this.menuBusiness = menuBusiness;
	}

	/**
	 * 返回
	 */
	public IndexModel getModel() {
		return model;
	}

	/**
	 * 设置
	 * @param model 
	 */
	public void setModel(IndexModel model) {
		this.model = model;
	}

	/**
	 * 返回
	 */
	public String getOp() {
		return op;
	}

	/**
	 * 设置
	 * @param op 
	 */
	public void setOp(String op) {
		this.op = op;
	}

	public void setJsglService(IJsglService jsglService) {
		this.jsglService = jsglService;
	}

	public IJsglService getJsglService() {
		return jsglService;
	}

	public void setJsglModelList(List<JsglModel> jsglModelList) {
		this.jsglModelList = jsglModelList;
	}

	public List<JsglModel> getJsglModelList() {
		return jsglModelList;
	}

	public void setJsid(String jsid) {
		this.jsid = jsid;
	}

	public String getJsid() {
		return jsid;
	}

	public void setPageList(PageList<IndexModelNew> pageList) {
		this.pageList = pageList;
	}

	public PageList<IndexModelNew> getPageList() {
		return pageList;
	}

	public void setQuery(IndexModelNew query) {
		this.query = query;
	}

	public IndexModelNew getQuery() {
		return query;
	}
	
}
