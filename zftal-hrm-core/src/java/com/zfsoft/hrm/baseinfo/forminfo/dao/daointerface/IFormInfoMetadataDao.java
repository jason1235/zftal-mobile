package com.zfsoft.hrm.baseinfo.forminfo.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoMetadata;
/**
 * 登记类元数据dao
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-8
 * @version V1.0.0
 */
public interface IFormInfoMetadataDao {
	/**
	 * 插入元数据
	 * @param model
	 */
	void insert(FormInfoMetadata model);
	/**
	 * 更新元数据
	 * @param model
	 */
	void update(FormInfoMetadata model);
	/**
	 * 删除元数据
	 * @param guid
	 */
	void delete(String guid);
	/**
	 * 通过id查找元数据
	 * @param guid
	 * @return
	 */
	FormInfoMetadata findByGuid(String guid);
	/**
	 * 通过登记类别查找元数据
	 * @param formInfoTypeId
	 * @return
	 */
	List<FormInfoMetadata> findByFormInfoTypeId(String formInfoTypeId);
	/**
	 * 通过登记类别和信息类获取元数据
	 * @param formInfoTypeId
	 * @param infoClassId
	 * @return
	 */
	List<FormInfoMetadata> findByInfoClassOfType(String formInfoTypeId,
			String infoClassId);
	/**
	 * 身份证后6位作为密码
	 * @param param
	 */
	void updateMm(Map<String, String> param);
}
