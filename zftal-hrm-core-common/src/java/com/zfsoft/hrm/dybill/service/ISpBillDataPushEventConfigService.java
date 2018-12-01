package com.zfsoft.hrm.dybill.service;

import java.util.List;

import com.zfsoft.hrm.dybill.entity.SpBillDataPushEventConfig;
import com.zfsoft.hrm.dybill.entity.SpBillDataPushProperty;
/**
 * 表单数据推送事件配置服务
 * @className: ISpBillDataPushEventConfigService 
 * @author Patrick Shen shenluwei@126.com
 * @date 2013-8-9 下午02:03:40
 */
public interface ISpBillDataPushEventConfigService {
	/**
	 * 批量获取表单推送事件
	 * @methodName getList 
	 * @param query
	 * @return List<SpBillDataPushEventConfig>
	 */
	public List<SpBillDataPushEventConfig> getList(SpBillDataPushEventConfig query);
	/**
	 * 获取表单推送事件，通过事件id
	 * @methodName getById 
	 * @param id
	 * @return SpBillDataPushEventConfig
	 */
	public SpBillDataPushEventConfig getById(String id);
	/**
	 * 批量获取表单推送事件，通过表单Id
	 * @methodName getByBillClassId 
	 * @param billClassId
	 * @return List<SpBillDataPushEventConfig>
	 */
	public List<SpBillDataPushEventConfig> getByBillClassId(Long billClassId);
	/**
	 * 增加表单数据推送事件
	 * @methodName addDataPushEvent 
	 * @param config void
	 */
	public void addDataPushEvent(SpBillDataPushEventConfig config);
	/**
	 * 修改表单数据推送事件
	 * @methodName modifyDataPushEvent 
	 * @param config void
	 */
	public void modifyDataPushEvent(SpBillDataPushEventConfig config);
	/**
	 * 删除表单数据推向事件，通过事件id
	 * @methodName removeDataPushEvent 
	 * @param id void
	 */
	public void removeDataPushEvent(String id);
	/**
	 * 批量删除表单数据推向事件，通过表单id
	 * @methodName removeDataPushEvents 
	 * @param billClassId void
	 */
	public void removeDataPushEvents(Long billClassId);
	/**
	 * 获取属性，通过属性编号
	 * @methodName getPropertyById 
	 * @param propertyId
	 * @return SpBillDataPushProperty
	 */
	public SpBillDataPushProperty getPropertyById(String propertyId);
	/**
	 * 批量获取属性，通过事件配置编号
	 * @methodName getPropertyByConfigId 
	 * @param billClassId
	 * @return List<SpBillDataPushProperty>
	 */
	public List<SpBillDataPushProperty> getPropertyByConfigId(String configId);
	/**
	 * 增加属性
	 * @methodName addDataPushProperty 
	 * @param configId
	 * @param property void
	 */
	public void addDataPushProperty(SpBillDataPushProperty property);
	/**
	 * 修改属性
	 * @methodName modifyDataPushProperty 
	 * @param configId
	 * @param property void
	 */
	public void modifyDataPushProperty(SpBillDataPushProperty property);
	/**
	 * 删除属性
	 * @methodName removeDataPushProperty 
	 * @param configId
	 * @param propertyId void
	 */
	public void removeDataPushProperty(String propertyId);
	/**
	 * 批量删除属性
	 * @methodName removeDataPushPropertys 
	 * @param configId void
	 */
	public void removeDataPushPropertys(String configId);
}
