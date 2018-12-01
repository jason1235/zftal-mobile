package com.zfsoft.hrm.baseinfo.forminfo.action;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.hrm.baseinfo.dyna.html.EditParse;
import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoMetadata;
import com.zfsoft.hrm.baseinfo.forminfo.service.svcinterface.IFormInfoMetadataService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.util.base.StringUtil;

/**
 * 登记类元数据管理Action
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-8
 * @version V1.0.0
 */
public class FormInfoMetadataAction extends HrmAction implements ModelDriven<FormInfoMetadata> {

	private static final long serialVersionUID = 6115002203907614671L;

	private static final String EDIT_PAGE = "edit";
	
	private static final String CLASS_LIST_PROP_PAGE = "prop_list";
	
	private FormInfoMetadata model = new FormInfoMetadata();
	
	private IFormInfoMetadataService formInfoMetadataService;
	
	private String[] propNames;
	private String typeId;
	private String classId;
	/**
	 * 获取元数据列表
	 * @return
	 */
	public String prop_list(){
		if(typeId==null)return CLASS_LIST_PROP_PAGE;
		List<FormInfoMetadata> formInfoMetadatas= formInfoMetadataService.getByInfoClassOfType(typeId,classId);
		getValueStack().set("metadata_prop_list", formInfoMetadatas);
		return CLASS_LIST_PROP_PAGE;
	}
	/**
	 * 编辑页面
	 * @return
	 */
	public String edit() {
		if( model.getGuid() != null && !"".equals(model.getGuid()) ) {
			model = formInfoMetadataService.getByGuid( model.getGuid() );
			StringBuilder html = new StringBuilder();
			if (!com.zfsoft.hrm.baseinfo.dyna.html.Type.FILE.equals(model.getInfoProperty().getFieldType()) &&
					!com.zfsoft.hrm.baseinfo.dyna.html.Type.IMAGE.equals(model.getInfoProperty().getFieldType())&&
					!com.zfsoft.hrm.baseinfo.dyna.html.Type.PHOTO.equals(model.getInfoProperty().getFieldType())) {
				String str = model.getInfoProperty().getFieldName();
				model.getInfoProperty().setFieldName("defaultValue");
				html.append(EditParse.parse(model.getInfoProperty(), model.getDefaultValue()));
				model.getInfoProperty().setFieldName(str);
			}
			getValueStack().set("defStyle", html);
		}
		 
		return EDIT_PAGE;
	}
	/**
	 * 编辑单个元数据
	 * @return
	 */
	public String save() {
		formInfoMetadataService.modify(model);
		
		setSuccessMessage("操作成功");
		
		getValueStack().set(DATA, getMessage());
		
		return DATA;
	}
	
	/**
	 * 保存选中的元数据
	 * @return
	 */
	public String save_list() {
		for(FormInfoMetadata formInfoMetadata:formInfoMetadataService.getByInfoClassOfType(typeId,classId))
			if(formInfoMetadata.getGuid()!=null){
				boolean found = false;
				if(propNames!=null&&propNames.length>0){
					for (int i = 0; i < propNames.length; i++) {
						if(formInfoMetadata.getInfoProperty().getFieldName().equals(propNames[i])){
							propNames = (String[]) ArrayUtils.remove(propNames, i);
							found= true;
							break;
						}
					}
				}
				if(!found)
					formInfoMetadataService.remove(formInfoMetadata.getGuid());
			}
		
		if(propNames!=null&&propNames.length>0)
			formInfoMetadataService.add(classId,typeId,propNames);
		
		setSuccessMessage("操作成功");
		
		getValueStack().set(DATA, getMessage());
		
		return DATA;
	}
	
	@Override
	public FormInfoMetadata getModel() {
		return model;
	}
	public void setFormInfoMetadataService(IFormInfoMetadataService formInfoMetadataService) {
		this.formInfoMetadataService = formInfoMetadataService;
	}
	public String[] getPropNames() {
		return propNames;
	}
	public void setPropNames(String[] propNames) {
		this.propNames = propNames;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
}