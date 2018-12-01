package com.zfsoft.hrm.baseinfo.forminfo.dao.daointerface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoPrivilege;
/**
 * 登记类管理dao
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-8
 * @version V1.0.0
 */
public interface IFormInfoPrivilegeDao {
	/**
	 * 插入登记类别
	 * @param model
	 */
	void insert(FormInfoPrivilege model);
	/**
	 * 删除登记类别
	 * @param guid
	 */
	void delete(FormInfoPrivilege model);
	/**
	 * 获取所有登记类别
	 * @return
	 */
	List<FormInfoPrivilege> findListByRole(String roleId);
	FormInfoPrivilege findEntity(String roleId, String classId, String opType);
}
