package com.zfsoft.hrm.baseinfo.forminfo.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoMetadata;
/**
 * 登记类元数据服务接口
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-8
 * @version V1.0.0
 */
public interface IFormInfoMetadataService {
	/**
	 * 增加
	 * @param model
	 */
	public void add(FormInfoMetadata model);/**
	 * 增加
	 * @param classId 
	 * @param typeId2 
	 * @param model
	 */
	public void add(String classId, String typeId, String... items);
	
	/**
	 * 增加
	 * @param model
	 */
	public void batchAdd(List<FormInfoMetadata> models);
	/**
	 * 修改
	 * @param model
	 */
	public void modify(FormInfoMetadata model);
	/**
	 * 删除
	 * @param guid
	 */
	public void remove(String guid);
	/**
	 * 通过id获取属性
	 * @param guid
	 * @return
	 */
	public FormInfoMetadata getByGuid(String guid);
	/**
	 * 通过登记类型和信息类获取属性
	 * @param formInfoTypeId
	 * @param infoClassId
	 * @return
	 */
	public List<FormInfoMetadata> getByInfoClassOfType(String formInfoTypeId,String infoClassId);
	/**
	 * 通过登记类型获取属性
	 * @param formInfoTypeId
	 * @return
	 */
	public List<FormInfoMetadata> getByFormInfoTypeId(String formInfoTypeId);
}
