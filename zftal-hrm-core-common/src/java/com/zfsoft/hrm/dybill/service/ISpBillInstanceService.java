package com.zfsoft.hrm.dybill.service;

import java.util.List;

import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlValueClass;
import com.zfsoft.hrm.dybill.xml.XmlValueEntity;
import com.zfsoft.hrm.dybill.xml.XmlValueProperty;
/**
 * 审批表单实例
 * @author Patrick Shen
 */
public interface ISpBillInstanceService {
	/**
	 * 获取新的表单实例，经过自动抓取处理的，并自动增加表单实例记录到数据库中
	 * @param billConfigId
	 * @param tableName
	 * @return
	 */
	public SpBillInstance getNewSpBillInstance(String billConfigId);
	/**
	 * 获取新的表单实例，经过自动抓取处理的，并自动增加表单实例记录到数据库中
	 * @param billConfigId
	 * @param tableName
	 * @return
	 */
	public SpBillInstance getNewSpBillInstance(String billConfigId,String staffId);
	/**
	 * 
	 * @param billConfigId
	 * @param staffId
	 * @return
	 */
	public SpBillInstance getNewSpBillInstanceLocal(String billConfigId,String guid);
	/**
	 * 
	 * @param billConfigId
	 * @param billInstanceId
	 * @param xmlBillClass
	 * @param tableName
	 * @return
	 */
	public XmlValueClass appendCatchRecord(String billConfigId,String billInstanceId,
			XmlBillClass xmlBillClass,String staffId);
	/**
	 * 新增表单实例
	 * @param entity
	 * @param tableName
	 */
	public void addSpBillInstance(SpBillInstance entity);
	/**
	 * 设置表单实例的提交日期
	 * @param entity
	 * @param tableName
	 */
	public void setSpBillInstanceCommitDate(SpBillInstance entity);
	/**
	 * 修改表单实例
	 * @param entity
	 * @param tableName
	 */
	public void modifySpBillInstance(SpBillInstance entity);
	/**
	 * 移除表单实例
	 * @param billConfigId 模板ID
	 * @param instanceId 实体ID
	 */
	public void removeSpBillInstance(String billConfigId,String instanceId);
	/**
	 * 获取表单实例集合
	 * @param entity
	 * @return
	 */
	public List<SpBillInstance> getSpBillInstanceList(SpBillInstance entity);
	/**
	 * 获取表单实例集合
	 * @param ids
	 * @return
	 */
	public List<SpBillInstance> getSpBillInstanceListByIds(List<String> ids);
	/**
	 * 获取表单实例
	 * @param billConfigId
	 * @param instanceId
	 * @return
	 */
	public SpBillInstance getSpBillInstanceById(String billConfigId,String instanceId);
	/**
	 * 往表单实例中新增billClassId对应表单类的实例条目
	 * @param billConfigId 表单配置编号
	 * @param billInstanceId 表单实例编号
	 * @param billClassId信息类
	 * @param entityType
	 * @param values
	 * @param tableName
	 */
	public void addXmlValueInstances(String billConfigId,String billInstanceId, Long billClassId,
			 List<XmlValueProperty> values);
	/**
	 * 往表单实例中新增billClassId对应表单类的实例条目
	 * @param billConfigId
	 * @param billInstanceId
	 * @param billClassId
	 * @param values
	 * @param xmlValueEntity
	 * @param tableName
	 */
	public void addXmlValueInstances(String billConfigId,String billInstanceId, Long billClassId,
			 List<XmlValueProperty> values,XmlValueEntity xmlValueEntity);
	/**
	 * 在表单实例billInstanceId中修改表单类billClassId的实例条目
	 * @param billInstanceId
	 * @param billClassId
	 * @param entityId
	 * @param values
	 * @param tableName
	 */
	public void modifyXmlValueInstance(String billConfigId,String billInstanceId, Long billClassId,
			Long entityId, List<XmlValueProperty> values);
	public void modifyXmlValueInstanceLocal(String billConfigId,String billInstanceId, Long billClassId,
			Long entityId, List<XmlValueProperty> values);
	/**
	 * 在表单实例billInstanceId中移除表单类billClassId的实例条目
	 * @param billInstanceId
	 * @param billClassId
	 * @param entityId
	 * @param tableName
	 */
	public void removeXmlValueInstance(String billConfigId,String billInstanceId, Long billClassId,
			Long entityId);
	/**
	 * 在表单实例billInstanceId中获取表单类的集合
	 * @param billConfigId
	 * @param billInstanceId
	 * @param tableName
	 * @return
	 */
	public List<XmlValueClass> getXmlValueClassList(String billConfigId,String billInstanceId);
	/**
	 * 在表单实例billInstanceId中获取表单类
	 * @param billConfigId
	 * @param billInstanceId
	 * @param billClassId
	 * @param tableName
	 * @return
	 */
	public XmlValueClass getXmlValueClassById(String billConfigId,String billInstanceId,
			Long billClassId);
	/**
	 * 在表单实例billInstanceId中获取表单类billClassId的实例条目集合
	 * @param billConfigId
	 * @param billInstanceId
	 * @param billClassId
	 * @param tableName
	 * @return
	 */
	public List<XmlValueEntity> getXmlValueEntityList(String billConfigId,String billInstanceId,
			Long billClassId);
	/**
	 * 在表单实例billInstanceId中获取表单类billClassId的实例条目
	 * @param billConfigId
	 * @param billInstanceId
	 * @param billClassId
	 * @param entityId
	 * @param tableName
	 * @return
	 */
	public XmlValueEntity getXmlValueEntityById(String billConfigId,String billInstanceId,
			Long billClassId, Long entityId);
	/**
	 * 在表单实例billInstanceId中修改表单类billClassId的实例条目
	 * @param billConfigId
	 * @param billInstanceId
	 * @param billClassId
	 * @param infoEntityId
	 * @param tableName
	 * @return
	 */
	public XmlValueEntity getXmlValueEntityByInfoEntityId(String billConfigId,String billInstanceId,
			Long billClassId, String infoEntityId);
	
	/**
	 * 复制一个表单
	 * @param billInstanceId 要复制的表单id
	 * @return
	 */
	public SpBillInstance doCopySpBillInstance(String billConfigId,String billInstanceId);
}
