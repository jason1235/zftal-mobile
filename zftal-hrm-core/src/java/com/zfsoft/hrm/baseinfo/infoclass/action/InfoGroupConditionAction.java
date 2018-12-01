package com.zfsoft.hrm.baseinfo.infoclass.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoGroupCondition;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoGroupConditionService;
import com.zfsoft.hrm.common.HrmAction;

/**
 * 信息类组合条件
 * 
 * @author 沈鲁威
 * @since 2012-7-14
 * @version V1.0.0
 */
public class InfoGroupConditionAction extends HrmAction implements
		ModelDriven<InfoGroupCondition> {
	private static final long serialVersionUID = -26333673428473318L;

	private InfoGroupCondition model = new InfoGroupCondition();

	private IInfoGroupConditionService infoGroupConditionService;

	public String list() {
		List<InfoGroupCondition> list = infoGroupConditionService
				.getAllInfoGroupCondition();
		this.getValueStack().set("list", list);
		return LIST_PAGE;
	}

	public String edit() {
		InfoClass clazz = InfoClassCache.getOverallInfoClass();
		List<InfoProperty> propertyList = new ArrayList<InfoProperty>();
			List<InfoProperty> pList = clazz.getViewables();
			for(InfoProperty p : pList){
				if(p.getTypeInfo().getName().equals(Type.CODE)&&!p.getVirtual()){
					propertyList.add(p);
				}
			}
			this.getValueStack().set("list", propertyList);
		if (model == null || model.getGuid() == "" || model.getGuid() == null) {
			return EDIT_PAGE;
		} else {
			model = infoGroupConditionService.getInfoGroupConditionById(model
					.getGuid());
		}
		return EDIT_PAGE;
	}

	public String delete() {
		if (model.getGuid() != null && !"".equals(model.getGuid())) {
			infoGroupConditionService.removeInfoGroupCondition(model.getGuid());
			setSuccessMessage("删除成功");
			getValueStack().set(DATA, getMessage());
			return DATA;
		} else {
			setSuccessMessage("未知删除项");
			getValueStack().set(DATA, getMessage());
			return DATA;

		}
	}

	public String save() {
		if (model.getGuid() != null && !"".equals(model.getGuid())) {
			infoGroupConditionService.modifyInfoGroupCondition(model);
		} else {
			infoGroupConditionService.addInfoGroupCondition(model);
		}
		setSuccessMessage("保存成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	@Override
	public InfoGroupCondition getModel() {
		return model;
	}

	public void setInfoGroupConditionService(
			IInfoGroupConditionService infoGroupConditionService) {
		this.infoGroupConditionService = infoGroupConditionService;
	}

}
