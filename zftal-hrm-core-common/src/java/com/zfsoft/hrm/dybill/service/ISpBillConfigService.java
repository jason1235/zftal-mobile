package com.zfsoft.hrm.dybill.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillConfigVersion;
import com.zfsoft.hrm.dybill.entity.SpBillQuery;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
/**
 * 审批表单配置
 * @author Patrick Shen
 */
public interface ISpBillConfigService {
	/**
	 * 获取最后版本
	 * @methodName getSpBillConfigLastVersion 
	 * @param billConfigId
	 * @return SpBillConfig
	 */
	public SpBillConfig getSpBillConfigLastVersion(String billConfigId);
	public Integer getLastVersion(String billConfigId);
	public List<Integer> getSpBillConfigVersionList(String billConfigId);
	/**
	 * 获取指定版本
	 * @methodName getSpBillConfigVersion 
	 * @param billConfigId
	 * @param version
	 * @return SpBillConfig
	 */
	public SpBillConfig getSpBillConfigByVersion(String billConfigId,Integer version);
	/**
	 * 发布一个版本
	 * @methodName addSpBillConfigVersion 
	 * @param entity void
	 */
	public void addSpBillConfigVersion(SpBillConfigVersion entity);
	/**
	 * 增加表单配置数据库实体
	 * @param entity
	 */
	public void addSpBillConfig(SpBillConfig entity);
	/**
	 * 修改表单配置数据库实体
	 * @param entity
	 */
	public void modifySpBillConfig(SpBillConfig entity);
	/**
	 * 拷贝
	 * @param spBillConfig
	 */
	public void copySpBillConfig(SpBillConfig spBillConfig);
	/**
	 * 删除表单配置数据库实体
	 * @param id
	 */
	public void removeSpBillConfig(String id);
	/**
	 * 获取表单配置数据库实体
	 * @param entity
	 * @return
	 */
	public List<SpBillConfig> getSpBillConfigList(SpBillConfig entity);
	
	/**
	 * 获取表单配置数据库实体,分页显示
	 * @param entity
	 * @return
	 */
	public PageList<SpBillQuery> getPagingSpBillConfigList(SpBillQuery query);
	/**
	 * 获取表单配置数据库实体
	 * @param id
	 * @return
	 */
	public SpBillConfig getSpBillConfigById(String id);
	
	/**
	 * 对数据库实体增加表单类
	 * @param billConfigId
	 * @param xmlBillclass
	 */
	public void addXmlBillClass(String billConfigId,XmlBillClass xmlBillClass);
	/**
	 * 表单复制
	 * @param id
	 * @param copyBillConfigId
	 * @param copyBillClassId
	 * @param string 
	 */
	public void saveCopyXmlBillClass(String id, String copyBillConfigId,
			Long copyBillClassId, String idName);
	/**
	 * 对数据库实体修改表单类
	 * @param billConfigId
	 * @param xmlBillClass
	 */
	public void modifyXmlBillClass(String billConfigId,XmlBillClass xmlBillClass);
	/**
	 * 对数据库实体修改表单类(包括属性)
	 * @param billConfigId
	 * @param xmlBillclass
	 */
	public void modifyXmlBillClassFull(String billConfigId,XmlBillClass xmlBillclass);
	/**
	 * 对数据库实体删除表单类
	 * @param billConfigId
	 * @param xmlBillClass
	 */
	public void removeXmlBillClass(String billConfigId,Long billClassId);
	/**
	 * 对数据库实体的表单类向前移动
	 * @param billConfigId
	 * @param billClassId
	 */
	public void xmlBillClassMoveUp(String billConfigId,Long billClassId);
	/**
	 * 对数据库实体的表单类向后移动
	 * @param billConfigId
	 * @param billClassId
	 */
	public void xmlBillClassMoveDown(String billConfigId,Long billClassId);
	/**
	 * 对数据库实体获取表单类
	 * @param billConfigId
	 * @param billClassId
	 * @return
	 */
	public XmlBillClass getXmlBillClassById(String billConfigId,Long billClassId);
	public XmlBillClass getXmlBillClassByVersion(String billConfigId,Integer version,Long billClassId);
	public XmlBillClass getXmlBillClassLastVersion(String billConfigId,Long billClassId);
	/**
	 * 对数据库实体获取表单类集合
	 * @param billConfigId
	 * @return
	 */
	public List<XmlBillClass> getXmlBillClassList(String billConfigId);
	/**
	 * 获取表单类集合带版本号
	 * @methodName getXmlBillClassListByVersion 
	 * @param billConfigId
	 * @param version
	 * @return List<XmlBillClass>
	 */
	public List<XmlBillClass> getXmlBillClassListByVersion(String billConfigId,Integer version);
	/**
	 * 对数据库实体获取表单类集合
	 * @param billConfigId
	 * @return
	 */
	public List<XmlBillClass> getXmlBillClassListLastVersion(String billConfigId);
	/**
	 * 带授权和版本的获取表单集合
	 * @param billConfigId
	 * @param billClassIdsAndPrivilege
	 * @return
	 */
	public List<XmlBillClass> getXmlBillClassListByVersion(String billConfigId,Integer version,String privilegeExpression);
	/**
	 * 带授权的获取最后版本的表单集合
	 * @param billConfigId
	 * @param billClassIdsAndPrivilege
	 * @return
	 */
	public List<XmlBillClass> getXmlBillClassListLastVersion(String billConfigId,String privilegeExpression);
	/**
	 * 对数据库实体的表单类增加属性
	 * @param billConfigId
	 * @param billClassId
	 * @param property
	 */
	public void addXmlBillProperty(String billConfigId,Long billClassId,XmlBillProperty property);
	/**
	 * 对数据库实体的表单类修改属性
	 * @param billConfigId
	 * @param billClassId
	 * @param property
	 */
	public void modifyXmlBillProperty(String billConfigId,Long billClassId,XmlBillProperty property);
	/**
	 * 对数据库实体的表单类删除属性
	 * @param billConfigId
	 * @param billClassId
	 * @param property
	 */
	public void removeXmlBillProperty(String billConfigId,Long billClassId,Long propertyId);
	/**
	 * 对数据库实体的表单类获取属性
	 * @param billConfigId
	 * @param billClassId
	 * @param propertyFieldName
	 * @return
	 */
	public XmlBillProperty getXmlBillPropertyById(String billConfigId,Long billClassId,Long propertyId);
	public XmlBillProperty getXmlBillPropertyByVersion(String billConfigId,Integer version,Long billClassId,Long propertyId );
	public XmlBillProperty getXmlBillPropertyLastVersion(String billConfigId,Long billClassId,Long propertyId);
	/**
	 * 对数据库实体的表单类获取属性集合
	 * @param billConfigId
	 * @param billClassId
	 * @return
	 */
	public List<XmlBillProperty> getXmlBillPropertyList(String billConfigId,Long billClassId);
	public List<XmlBillProperty> getXmlBillPropertyListByVersion(String billConfigId,Integer version,Long billClassId);
	public List<XmlBillProperty> getXmlBillPropertyListLastVersion(String billConfigId,Long billClassId);
	/**
	 * 对数据库实体的表单类属性向前移动
	 * @param billConfigId
	 * @param billClassId
	 * @param propertyId
	 */
	public void xmlBillPropertyMoveLeft(String billConfigId,Long billClassId, Long propertyId);
	/**
	 * 对数据库实体的表单类属性向后移动
	 * @param billConfigId
	 * @param billClassId
	 * @param propertyId
	 */
	public void xmlBillPropertyMoveRight(String billConfigId,Long billClassId, Long propertyId);
}
