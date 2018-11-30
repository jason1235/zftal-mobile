package com.zfsoft.hrm.baseinfo.forminfo.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoPrivilege;
/**
 * 登记类元数据服务接口
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-8
 * @version V1.0.0
 */
public interface IFormInfoPrivilegeService {
	/**
	 * 增加
	 * @param model
	 */
	public void add(FormInfoPrivilege model);/**
	/**
	 * 删除
	 * @param guid
	 */
	public void remove(FormInfoPrivilege model);
	/**
	 * 通过角色获取
	 * @param roleId
	 * @return
	 */
	public List<FormInfoPrivilege> getListByRole(String roleId);
	/**
	 * 通过角色获取
	 * @param roleId
	 * @return
	 */
	public FormInfoPrivilege getEntity(String roleId,String classId,String opType);
}
