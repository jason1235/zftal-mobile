package com.zfsoft.hrm.baseinfo.forminfo.action;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoType;
import com.zfsoft.hrm.baseinfo.forminfo.service.svcinterface.IFormInfoTypeService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.IConstants;

/**
 * 登记类管理Action
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-8
 * @version V1.0.0
 */
public class FormInfoTypeAction extends HrmAction implements ModelDriven<FormInfoType> {

	private static final long serialVersionUID = 6115002203907614671L;

	private static final String LIST_PAGE = "list";
	
	private static final String CLASS_LIST_PAGE = "class_list";
	
	private static final String EDIT_PAGE = "edit";
	
	private FormInfoType model = new FormInfoType();
	
	private IFormInfoTypeService formInfoTypeService;
	
	
	/**
	 * 获取登记类别
	 * @return
	 */
	public String list() {
		getValueStack().set("list", formInfoTypeService.getList());
		return LIST_PAGE;
	}
	
	/**
	 * 编辑登记类别
	 * @return
	 */
	public String edit(){
		if( model.getGuid() != null && !"".equals(model.getGuid()) ) {
			model = formInfoTypeService.getByGuid( model.getGuid() );
		}
		return EDIT_PAGE;
	}
	
	/**
	 * 获取信息类列表
	 * @return
	 */
	public String class_list(){
		getValueStack().set("formInfoClass", 
				formInfoTypeService.getFormInfoClass(model.getGuid(), InfoClassCache.getInfoClasses() ) );
		return CLASS_LIST_PAGE;
	}
	/**
	 * 保登记类别操作
	 * @return
	 */
	public String save() {
		try {
			if( model.getGuid() == null || "".equals(model.getGuid()) ) {
				formInfoTypeService.add(model);
				
				//新增完成后调整回列表，对新增的信息类进行操作，需在回话中设置当前操作的信息类ID
//				getSession().setAttribute(SESSION_INFO_CLASS_ID, model.getGuid());
			} else {
				formInfoTypeService.modify(model);
			}
			
//			InfoClassCache.instance().restart(model.getGuid());
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage("操作失败！<br />提示：" + e.getMessage() );
		}
		
		getValueStack().set(DATA, getMessage());
		
		return DATA;
	}
	
	/**
	 * 删除登记类别操作
	 * @return
	 */
	public String remove() {
		try {
			formInfoTypeService.remove( model.getGuid() );
			
		} catch (Exception e) {
			e.printStackTrace();
			
			setErrorMessage("操作失败！<br />提示："+e.getMessage());
		}
		
		getValueStack().set(DATA, getMessage());
		
		return DATA;
	}
	
	@Override
	public FormInfoType getModel() {
		return model;
	}
	
	public void setFormInfoTypeService(IFormInfoTypeService formInfoTypeService) {
		this.formInfoTypeService = formInfoTypeService;
	}
}