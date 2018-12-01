package com.zfsoft.hrm.baseinfo.forminfo.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoPrivilege;
import com.zfsoft.hrm.baseinfo.forminfo.service.svcinterface.IFormInfoPrivilegeService;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoClassQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoClassService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.menu.entity.MenuOperate;
import com.zfsoft.hrm.menu.service.IMenuService;
import com.zfsoft.service.svcinterface.IJsglService;
import com.zfsoft.util.base.StringUtil;

public class ForminfoPrivilegeAction extends HrmAction implements ModelDriven<FormInfoPrivilege>{

	private static final long serialVersionUID = 1599263729418516363L;
	
	private FormInfoPrivilege model;
	
	private IFormInfoPrivilegeService formInfoPrivilegeService;
	
	private IInfoClassService infoClassService;
	
	private IMenuService menuService;
	
	public String list_claszes(){
		//读取到所有信息类
		List<InfoClass> infoClaszes=infoClassService.getList(new InfoClassQuery());
		//通过信息类读取到所有信息类对应的菜单
		List<IndexModel> menus=new ArrayList<IndexModel>();
		Map<String,String> menuClassMap=new HashMap<String, String>();
		IndexModel indexModel;
		for (InfoClass infoClass : infoClaszes) {
			if(!StringUtil.isEmpty(infoClass.getMenuId())){
				indexModel=menuService.getById(infoClass.getMenuId());
				if(indexModel!=null){
					menus.add(indexModel);
					menuClassMap.put(indexModel.getGnmkdm(), infoClass.getGuid());
				}
			}
		}
		
		//通过读取到的菜单，读取菜单对应的操作
		Map<String,List<MenuOperate>> menuFunctionMap=new HashMap<String, List<MenuOperate>>();
		List<MenuOperate> menuOps;
		for (IndexModel menu : menus) {
			menuOps=menuService.getOperateByMenuId(menu.getGnmkdm());
			for (MenuOperate menuOperate : menuOps) {
				//找到当前角色对应的已选信息类功能
				FormInfoPrivilege formInfoPrivilege=formInfoPrivilegeService.getEntity(model.getRoleId(),menuClassMap.get(menuOperate.getMenuId()),menuOperate.getOperate());
				if(formInfoPrivilege!=null){
					menuOperate.setChecked(true);
				}
			}
			menuFunctionMap.put(menu.getGnmkdm(), menuOps);
		}
		
		this.getValueStack().set("menus",menus);
		this.getValueStack().set("menuFunctionMap",menuFunctionMap);
		
		return LIST_PAGE;
	}
	
	public String addClassOp(){
		formInfoPrivilegeService.add(model);
		return DATA;
	}
	
	public String removeClassOp(){
		formInfoPrivilegeService.remove(model);
		return DATA;
	}
	
	public String removeClass(){
		List<FormInfoPrivilege> list=formInfoPrivilegeService.getListByRole(model.getRoleId());
		for (FormInfoPrivilege formInfoPrivilege : list) {
			formInfoPrivilegeService.remove(formInfoPrivilege);
		}
		return DATA;
	}
	
	@Override
	public FormInfoPrivilege getModel() {
		return model;
	}
	public void setModel(FormInfoPrivilege model) {
		this.model = model;
	}

	
}
