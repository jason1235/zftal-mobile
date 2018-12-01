package com.zfsoft.hrm.dataorigin.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.dataorigin.entity.DataOriginConfig;
import com.zfsoft.hrm.dataorigin.entity.XmlDataOriginClass;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;

/**
 * 
 * @author Administrator
 *
 */
public interface IDataOriginService {

    /**
     * 取得xml
     * @return
     */
    public List<SpBillConfig> getBillConfigs();
    
    /**
     * 保存
     * @param ywId
     * @param dataorigin
     */
    public void saveXml(String ywId, XmlDataOriginClass dataorigin);
    
    /**
     * 删除xml
     * @param xmlId
     * @param id
     */
    public void removeXml(String xmlId, Long id);
    
    /**
     * 取得业务xml
     * @param param
     * @return
     */
    public DataOriginConfig getConfigsByParam(Map<String, String> param);
    
    /**
     * 取得属性
     * @param billConfigId
     * @param billClassId
     * @return
     */
    public List<XmlBillProperty> getXmlBillPropertyList(String xmlId, Long billClassId);
    
    /**
     * 添加属性
     * @param xmlId
     * @param billClassId
     * @param xmlBillProperty
     */
    public void addXmlBillProperty(String xmlId, Long billClassId, XmlBillProperty xmlBillProperty);
    
    /**
     * 删除属性
     * @param xmlId
     * @param billClassId
     * @param propertyId
     */
    public void removeXmlBillProperty(String xmlId, Long billClassId, Long propertyId);
    
    /**
     * 取得属性
     * @param xmlId
     * @param id
     * @param id2
     * @return
     */
    public XmlBillProperty getXmlBillPropertyById(String xmlId, Long billClassId, Long propertyId);
    
    /**
     * 取得属性
     * @param param
     * @return
     */
    public List<XmlBillProperty> getPropertiesByParam(Map<String, String> param);
    
    /**
     * 取得数据
     * @param condition
     * @param billIds 
     * @return
     */
    public Map<String, Object> getOutPutData(Map<String, Object> condition, Map<String, String> billIds);
    
    /**
     * 取得表单
     * @param classId
     * @return
     */
    public SpBillConfig getBillConfigById(String billId);
    
    /**
     * 修改属性
     * @param xmlId
     * @param id
     * @param xmlBillProperty
     */
    public void modifyXmlBillProperty(String xmlId, Long billClassId, XmlBillProperty xmlBillProperty);
    
    /**
     * 取得版本号
     * @param id
     * @return
     */
    public List<String> getBillConfigsVersion(String id);
    
    /**
     * 上移
     * @param xmlId
     * @param id
     */
    public void xmlBillClassMoveUp(String xmlId, Long billClassId);
    
    /**
     * 下移
     * @param xmlId
     * @param id
     */
    public void xmlBillClassMoveDown(String xmlId, Long billClassId);

}
