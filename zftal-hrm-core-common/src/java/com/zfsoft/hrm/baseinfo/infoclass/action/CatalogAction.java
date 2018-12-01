package com.zfsoft.hrm.baseinfo.infoclass.action;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.ICatalogService;
import com.zfsoft.hrm.common.HrmAction;

/**
 * 信息类目录操作Action
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-18
 * @version V1.0.0
 */
public class CatalogAction extends HrmAction implements ModelDriven<Catalog>{

	private static final long serialVersionUID = -511755654966797851L;
	
	private static final String EDIT_PAGE = "edit";

	private Catalog model = new Catalog();
	
	private ICatalogService catalogService;

	/**分类描述     student 学生分类   teacher 老师分类 */
	private String type;
	
	/**
	 * 编辑页面
	 */
	public String edit() {
		model = catalogService.getEntity( model.getGuid() );
		if(model!=null){
			setType(model.getType());
			getValueStack().set("type", type);
		}else{
			setType(getRequest().getParameter("type"));
		}
		return EDIT_PAGE;
	}
	
	/**
	 * 编辑页面
	 */
	public String editNew() {
		model = catalogService.getEntity( model.getGuid() );
		if(model!=null){
			setType(model.getType());
			getValueStack().set("type", type);
		}else{
			setType(getRequest().getParameter("type"));
		}
		return "editNew";
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 删除操作
	 */
	public String remove() {
		try {
			catalogService.remove(model.getGuid());
		} catch (Exception e) {
			e.printStackTrace();
			
			setErrorMessage("操作失败！<br />提示："+e.getMessage());
		}
		
		getValueStack().set(DATA, getMessage());
		
		return DATA;
		
	}
	
	/**
	 * 保存操作
	 */
	public String save() {
		try {
			if( model.getGuid() == null || "".equals( model.getGuid() ) ) {
				catalogService.add(model);
			} else {
				catalogService.modify(model);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			setErrorMessage( "操作失败！<br />提示："+e.getMessage() );
		}
		
		getValueStack().set(DATA, getMessage());

		return DATA;
	}

	@Override
	public Catalog getModel() {
		return model;
	}

	public void setCatalogService( ICatalogService catalogService) {
		this.catalogService = catalogService;
	}

}
