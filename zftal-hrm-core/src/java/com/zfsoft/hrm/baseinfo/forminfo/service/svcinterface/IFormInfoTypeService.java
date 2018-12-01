package com.zfsoft.hrm.baseinfo.forminfo.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoClass;
import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoType;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
/**
 * 登记类服务接口
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-8
 * @version V1.0.0
 */
public interface IFormInfoTypeService {
	
	/**
	 * 增加登记表
	 * @param model
	 */
	public void add(FormInfoType model);
	
	/**
	 * 修改登记表
	 * @param model
	 */
	public void modify(FormInfoType model);
	/**
	 * 删除登记表
	 * @param guid
	 */
	public void remove(String guid);
	
	/**
	 * 获取所有登记类别
	 * @return
	 */
	public List<FormInfoType> getList();
	
	/**
	 * 通过id获取登记类别
	 * @param guid
	 * @return
	 */
	public FormInfoType getByGuid(String guid);
	
	/**
	 * 
	 * @param guid
	 * @param classes
	 * @return
	 */
	public FormInfoClass getFormInfoClass(String guid, List<InfoClass> classes);
}
