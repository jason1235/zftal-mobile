package com.zfsoft.hrm.baseinfo.forminfo.action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoClass;
import com.zfsoft.hrm.baseinfo.forminfo.service.svcinterface.IFormInfoUtilService;
import com.zfsoft.hrm.common.HrmAction;

public class FormInfoUtilAction extends HrmAction implements ModelDriven<FormInfoClass> {
	private static final long serialVersionUID = -5299371417949031113L;

	
	private FormInfoClass model=new FormInfoClass();
	
	private IFormInfoUtilService formInfoUtilService;
	
	private String infoClassId;				//登记类别
	
	private int index;
	
	private boolean newSession=false;
	
	private String fileGuid;
	
	private DynaBean dynaBean; 
	
	private String userId;
	
	/**
	 * 获取新增的初始界面
	 * @return
	 */
	public String list(){
		setSessionFormInfoClasss();
		
		return LIST_PAGE;
	}
	/**
	 * 获取新增的初始界面
	 * @return
	 */
	public String listDetail(){
		this.newSession=false;
		setSessionFormInfoClasss();
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("result", model);
		getValueStack().set(DATA, map);
		
		return DATA;
	}
	/**
	 * 增加动态bean
	 * @return
	 */
	public String addDynaBean(){
		this.newSession=false;
		setSessionFormInfoClasss();
		String dept=getRequest().getParameter("dept");
		dynaBean=formInfoUtilService.getDynaBeanByInfoClass(infoClassId, model.getFormInfoTypeId(),dept);
		
		dynaBean.setCacheNew(false);
		
		model.addDynaBean(dynaBean);
		
		dynaBean.setIndex(model.getInfoClassOfDynaBeanMap().get(dynaBean.getClazz().getGuid()).size()-1);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("result", dynaBean);
//		dynaBean.getClazz().setEditables(null);
//		dynaBean.getClazz().setProperties(null);
//		JSONObject oo=JSONObject.fromObject(map);
//		oo.toString();
		getValueStack().set(DATA, map);
		
		return DATA;
	}
	/**
	 * 保存动态bean
	 * @return
	 */
	public String saveDynaBean(){
		this.newSession=false;
		setSessionFormInfoClasss();
		
		dynaBean=model.getDynaBeanByClassId(infoClassId, index);
		
		formInfoUtilService.modifyDynaBean( dynaBean,this.getRequest());
		
		setSuccessMessage("操作成功");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("message",  getMessage());
		map.put("dynaBean", dynaBean);
		getValueStack().set(DATA, map);
		
		return DATA;
	}
	/**
	 * 删除动态bean
	 * @return
	 */
	public String removeDynaBean(){
		
		model.copy((FormInfoClass)this.getSession().getAttribute("FormInfoClass"+model.getFormInfoTypeId()));
		
		DynaBean dyBean=model.getDynaBeanByClassId(infoClassId, index);
		if(!dyBean.isCacheNew()){
			dyBean.setValue("zp", fileGuid);
			formInfoUtilService.removeDynaBeanPic(dyBean);
		}
		
		model.removeDynaBean(infoClassId,index);
		
		
		
		return LIST_PAGE;
	}
	
	public String modifyDynaBean(){
		this.newSession=false;
		setSessionFormInfoClasss();
		
		dynaBean=model.getDynaBeanByClassId(infoClassId,index);
		
		if(dynaBean==null)
			dynaBean=formInfoUtilService.getDynaBeanByInfoClass(infoClassId, model.getFormInfoTypeId(),null);
		String message=formInfoUtilService.modifyDynaBean(dynaBean,this.getRequest());
		dynaBean.setCacheNew(true);
		
		if(message.equals("success")){
			setSuccessMessage("操作成功");
			Map<String,Object>  map=new HashMap<String,Object>();
			map.put("message",  getMessage());
			map.put("dynaBean", dynaBean);
			getValueStack().set(DATA, map);
		}else{
			setErrorMessage(message);
			Map<String,Object>  map=new HashMap<String,Object>();
			map.put("message",  getMessage());
			getValueStack().set(DATA, map);
		}
		
		return DATA;
	}
	/**
	 * 获取对应信息类对应索引的动态bean
	 * @return
	 */
	public String getDynaBeanByInfoClassId(){
		this.newSession=false;
		setSessionFormInfoClasss();
		dynaBean=model.getDynaBeanByClassId(infoClassId,index);
		
		setSuccessMessage("操作成功");
		Map<String,Object>  map=new HashMap<String,Object>();
		map.put("message",  getMessage());
		map.put("dynaBean", dynaBean);
		getValueStack().set(DATA, map);
		
		return DATA;
	}
	
	public String findUser(){
		setSessionFormInfoClasss();
		if(formInfoUtilService.findUser(model)){
			setErrorMessage("存在同名用户");
		}else{
			setSuccessMessage("未找到同名用户");
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String save(){
		setSessionFormInfoClasss();
		try{
			userId=formInfoUtilService.saveDynaBeans( model );
		}catch(com.zfsoft.hrm.core.exception.RuleException e){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("success",false);
			getMessage().setText(e.getMessage());
			map.put("message", getMessage());
			getValueStack().set(DATA, map);
			return DATA;
		}catch (Exception e) {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("success",false);
			getMessage().setText(e.getMessage());
			map.put("message", getMessage());
			getValueStack().set(DATA, map);
			e.printStackTrace();
			return DATA;
		}
		
		setSuccessMessage("人员信息录入成功！");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success",true);
		map.put("message", getMessage());
		map.put("userId", userId);
		
		getValueStack().set(DATA, map);
		
		this.newSession=true;
		
		setSessionFormInfoClasss();
		
		return DATA;
	}
	
	public String delete(){
		setSessionFormInfoClasss();
		formInfoUtilService.removeDynaBeanPic(model.getDynaBeans());
		this.newSession=true;
		setSessionFormInfoClasss();
		
		setSuccessMessage("操作成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	private void setSessionFormInfoClasss() {
		if(newSession){
			FormInfoClass formInfoClass=(FormInfoClass)this.getSession().getAttribute("FormInfoClass"+model.getFormInfoTypeId());
			if(formInfoClass!=null){
				formInfoUtilService.removeDynaBeanPic(formInfoClass.getDynaBeans());
			}
			this.getSession().removeAttribute("FormInfoClass"+model.getFormInfoTypeId());
			model=formInfoUtilService.getAddForm(model);
			this.getSession().setAttribute("FormInfoClass"+model.getFormInfoTypeId(),model);
		}else{
			model=(FormInfoClass)this.getSession().getAttribute("FormInfoClass"+model.getFormInfoTypeId());
		}
	}
	
	@Override
	public FormInfoClass getModel() {
		return model;
	}
	public void setFormInfoUtilService(IFormInfoUtilService formInfoUtilService) {
		this.formInfoUtilService = formInfoUtilService;
	}
	
	public String getInfoClassId() {
		return infoClassId;
	}

	public void setInfoClassId(String infoClassId) {
		this.infoClassId = infoClassId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public DynaBean getDynaBean() {
		return dynaBean;
	}

	public void setDynaBean(DynaBean dynaBean) {
		this.dynaBean = dynaBean;
	}
	public boolean isNewSession() {
		return newSession;
	}
	public void setNewSession(boolean newSession) {
		this.newSession = newSession;
	}
	public String getFileGuid() {
		return fileGuid;
	}
	public void setFileGuid(String fileGuid) {
		this.fileGuid = fileGuid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
