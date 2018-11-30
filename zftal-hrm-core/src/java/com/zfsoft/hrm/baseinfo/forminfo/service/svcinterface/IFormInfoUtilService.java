package com.zfsoft.hrm.baseinfo.forminfo.service.svcinterface;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoClass;

public interface IFormInfoUtilService {
	
	public FormInfoClass getAddForm(FormInfoClass model);
	/**
	 * session中添加
	 * @param model
	 * @param formInfoClass
	 * @param httpServletRequest 
	 * @return
	 */
	public FormInfoClass addDynaBean(FormInfoClass model,
									 FormInfoClass formInfoClass,DynaBean dynaBean, HttpServletRequest httpServletRequest);
	/**
	 * session中修改
	 * @param model
	 * @param formInfoClass
	 * @return
	 */
	public String modifyDynaBean(DynaBean dynaBean,HttpServletRequest request);
	/**
	 * 正式保存入数据库
	 * @param formInfoClass
	 * @return
	 */
	public String saveDynaBeans(FormInfoClass formInfoClass);
	/**
	 * 获取登记表某信息类对应的动态Bean
	 * @return
	 */
	public DynaBean getDynaBeanByInfoClass(String infoClassId,String formInfoTypeId,String dept);
	/**
	 * 获取已存在的登记表记录
	 * @return
	 */
	public FormInfoClass getDynaBeans(String formInfoId);
	public void removeDynaBeanPic(List<DynaBean> dynaBeans);
	public void removeDynaBeanPic(DynaBean dyBean);
	public boolean findUser(FormInfoClass formInfoClass);
}
