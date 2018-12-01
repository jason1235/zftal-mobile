package com.zfsoft.hrm.baseinfo.finfo.action;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.hrm.baseinfo.dyna.html.EditParse;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMemberProperty;
import com.zfsoft.hrm.baseinfo.finfo.service.svcinterface.IFormInfoMemberPropertyService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.common.HrmAction;

/**
 * 信息维护-成员属性定义Action
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-27
 * @version V1.0.0
 */
public class FormInfoMemberPropertyAction extends HrmAction implements ModelDriven<FormInfoMemberProperty> {

	private static final long serialVersionUID = -7471443173442672479L;
	
	private FormInfoMemberProperty property = new FormInfoMemberProperty();
	
	private IFormInfoMemberPropertyService formInfoMemberPropertyService;
	
	private String[] pnames;

	/**
	 * 成员属性列表页面
	 */
	public String list() {
		FormInfoMemberProperty[] properties = formInfoMemberPropertyService
				.getMemberProperties( property.getMember() );
		
		getValueStack().set( "properties", properties );
		
		return LIST_PAGE;
	}
	
	/**
	 * 成员属性编辑页面
	 */
	public String edit() {
		property = formInfoMemberPropertyService.getMemberProperty( property );
		InfoProperty infoProperty= InfoClassCache.getInfoClass(property.getMember().getClassId())
									.getPropertyByName(property.getpName());
		
		String str = infoProperty.getFieldName();
		infoProperty.setFieldName("defaultValue");
		String defStyle = EditParse.parse(infoProperty, infoProperty.getDefaultValue());
		infoProperty.setFieldName(str);
		getValueStack().set("defStyle", defStyle);
		return EDIT_PAGE;
	}
	
	/**
	 * 成员属性保存操作
	 */
	public String save() {
		try {
			boolean v = property.isViewable();
			formInfoMemberPropertyService.save( property );
			property = formInfoMemberPropertyService.getMemberProperty( property );
			if(v!=property.isViewable()){
				property.setViewable(v);
				formInfoMemberPropertyService.modifyViewable(property);
			}
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage( "操作失败！<br />提示：" + e.getMessage() );
		}
		getValueStack().set( DATA, getMessage() );
		
		return DATA;
	}
	
	/**
	 * 显示/不显示成员属性操作
	 */
	public String view() {
		try {
			formInfoMemberPropertyService.modifyViewable( property );
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage( "操作失败！<br />提示：" + e.getMessage() );
		}
		
		getValueStack().set( DATA, getMessage() );
		
		return DATA;
	}
	
	/**
	 * 显示/不显示成员属性操作
	 */
	public String saveProList() {
		try {
			formInfoMemberPropertyService.saveProList( property.getMember(), pnames );
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage( "操作失败！<br />提示：" + e.getMessage() );
		}
		getValueStack().set( DATA, getMessage() );
		
		return DATA;
	}
	
	/**
	 * 交换显示位置操作
	 */
	public String swap() {
		try {
			formInfoMemberPropertyService.modifyIndex( property.getMember(), pnames );
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage( "操作失败！<br />提示：" + e.getMessage() );
		}
		
		getValueStack().set( DATA, getMessage() );
		
		return DATA;
	}

	@Override
	public FormInfoMemberProperty getModel() {
		return property;
	}

	public void setFormInfoMemberPropertyService(
			IFormInfoMemberPropertyService formInfoMemberPropertyService) {
		this.formInfoMemberPropertyService = formInfoMemberPropertyService;
	}

	public void setPnames(String[] pnames) {
		this.pnames = pnames;
	}

}
