package com.zfsoft.hrm.baseinfo.forminfo.dao.daointerface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoType;
/**
 * 登记类管理dao
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-8
 * @version V1.0.0
 */
public interface IFormInfoTypeDao {
	/**
	 * 插入登记类别
	 * @param model
	 */
	void insert(FormInfoType model);
	/**
	 * 更新登记类别
	 * @param model
	 */
	void update(FormInfoType model);
	/**
	 * 删除登记类别
	 * @param guid
	 */
	void delete(String guid);
	/**
	 * 获取所有登记类别
	 * @return
	 */
	List<FormInfoType> findList();
	/**
	 * 通过id获取登记类别
	 * @param guid
	 * @return
	 */
	FormInfoType findByGuid(String guid);
}
