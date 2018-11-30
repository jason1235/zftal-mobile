package com.zfsoft.hrm.baseinfo.data.action;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.data.entity.Template;
import com.zfsoft.hrm.baseinfo.data.service.ITemplateService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.common.HrmAction;

public class TemplateAction extends HrmAction {
	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = -7290787742920546056L;
	private String classId; 
	private String oper="add";
	private PageList<Template> pageList = new PageList<Template>();
	private Template template = new Template();
	private ITemplateService templateService;

	//选择属性
	public String choiceProperty() throws Exception{
		List<InfoProperty> infoPropertyList=InfoClassCache.getInfoClass(classId).getViewables();
		this.getValueStack().set(DATA, SUCCESS);
		this.getValueStack().set(DATA, infoPropertyList);
		return DATA;
	}
	/**
	 * 方法描述：删除
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 */
	public String delete() throws Exception{
		templateService.delete(template.getId());
		this.setSuccessMessage("删除成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	/**
	 * 方法描述：编辑模版
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 */
	public String input() throws Exception{
		oper="add";
		return EDIT_PAGE;
	}
	/**
	 * 方法描述：模版列表
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 */
	public String list() throws Exception{
		pageList = templateService.getPagedTemplate(template);
		return LIST_PAGE;
	}
	/**
	 * 方法描述：修改
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 */
	public String modify() throws Exception{
		oper="modify";
		template=templateService.getById(template.getId());
		return EDIT_PAGE;
	}
	/**
	 * 
	 * 方法描述：属性配置
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 */
	public String property() throws Exception{
		if(classId==null||"".equals(classId)){
			classId="C393FE11C4DC8E46E040007F01003F39";
		}
		List<InfoProperty> infoPropertyList=InfoClassCache.getInfoClass(classId).getViewables();
		this.getValueStack().set("infoPropertyList", infoPropertyList);
		this.getValueStack().set("infoClasses", InfoClassCache.getInfoClasses());
		return "property";
	}
	/**
	 * 方法描述：编辑保存
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 */
	public String save() throws Exception{
		try {
			if ("add".equals(oper)) {
				templateService.add(template);
			} else {
				templateService.modify(template);
			}
		} catch (Exception e) {
			this.setErrorMessage(e.getMessage());
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		//templateService.add(template);
		this.setSuccessMessage("保存成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	public void setOper(String oper) {
		this.oper = oper;
	}
	
	public void setPageList(PageList<Template> pageList) {
		this.pageList = pageList;
	}
	
	public void setTemplate(Template template) {
		this.template = template;
	}
	
	public Template getTemplate() {
		return template;
	}
	
	public void setTemplateService(ITemplateService templateService) {
		this.templateService = templateService;
	}
	
	public String getClassId() {
		return classId;
	}

	public String getOper() {
		return oper;
	}
	
	public PageList<Template> getPageList() {
		return pageList;
	}
}
