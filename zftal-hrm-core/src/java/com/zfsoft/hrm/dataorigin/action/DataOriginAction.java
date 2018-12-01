package com.zfsoft.hrm.dataorigin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.dyna.html.EditParse;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.Type;
import com.zfsoft.hrm.config.TypeFactory;
import com.zfsoft.hrm.config.type.InfoPropertyType;
import com.zfsoft.hrm.dataorigin.entity.DataOriginConfig;
import com.zfsoft.hrm.dataorigin.entity.XmlDataOriginClass;
import com.zfsoft.hrm.dataorigin.entity.XmlDataOriginClasses;
import com.zfsoft.hrm.dataorigin.service.IDataOriginService;
import com.zfsoft.hrm.dybill.dto.ChoiceProperty;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybill.xml.XmlDefineCatch;

public class DataOriginAction extends HrmAction {
    /**
     * 
     */
    private static final long serialVersionUID = 8243172267257121236L;
    private IDataOriginService dataOriginService;
    private String jumpId;
    private String pgId;
    private XmlDataOriginClass dataorigin;
    private String ywId;
    private String xmlId;
    private XmlBillProperty xmlBillProperty;
    
    /**
     * 设置页面
     * @return
     */
    public String page() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("ywId", ywId);
        DataOriginConfig dataOriginConfig = dataOriginService.getConfigsByParam(param);
        XmlDataOriginClasses xmlDataOriginClasses = null;
        if (dataOriginConfig != null) {
            xmlDataOriginClasses = dataOriginConfig.getXmlDataOriginClasses();
        }
        
        if (xmlDataOriginClasses == null || xmlDataOriginClasses.getDataOriginClasses() == null || xmlDataOriginClasses.getDataOriginClasses().size() == 0) {
            this.getValueStack().set("xmlBean", null);
            return "page";
        }
        
        this.getValueStack().set("xmlBean", xmlDataOriginClasses.getDataOriginClasses());
        xmlId = dataOriginConfig.getId();
        return "page";
    }
    
    /**
     * 添加修改
     * @return
     */
    public String modify() {
        if (dataorigin == null || dataorigin.getId() == null) {
            dataorigin = new XmlDataOriginClass();
            this.getValueStack().set("versionList", new ArrayList<String>());
        } else {
            Map<String, String> param = new HashMap<String, String>();
            param.put("xmlId", xmlId);
            DataOriginConfig dataOriginConfig = dataOriginService.getConfigsByParam(param);
            dataorigin = dataOriginConfig.getXmlDataOriginClasses().getDataOriginClassById(dataorigin.getId());
            
            if ("bill".equals(dataorigin.getOriginType())) {
                SpBillConfig config = dataOriginService.getBillConfigById(dataorigin.getClassId());
                this.getValueStack().set("versionList", dataOriginService.getBillConfigsVersion(config.getId()));
            } else {
                this.getValueStack().set("versionList", new ArrayList<String>());
            }
        }
        return "modify";
    }
    
    /**
     * 取得数据源
     */
    public String getInfoClassList() {
        String type = dataorigin.getOriginType();
        if(type == null) {
            type = "teacher";
        }
        Map<String,Object> map=new HashMap<String, Object>();
        this.setSuccessMessage("获取成功");
        map.put("message", getMessage());
        if ("teacher".equals(type) || "business".equals(type)) {
            map.put("classList", InfoClassCache.getInfoClasses(type));
            map.put("versionList", new ArrayList<String>());
        } else if ("bill".equals(type)) {
            List<SpBillConfig> configs = dataOriginService.getBillConfigs();
            map.put("classList", configs);
            if (configs != null && configs.size() > 0) {
                map.put("versionList", dataOriginService.getBillConfigsVersion(configs.get(0).getId()));
            } else {
                map.put("versionList", new ArrayList<String>());
            }
        }
        this.getValueStack().set(DATA, map);
        return DATA;
    }
    
    /**
     * 取得数据源
     * @return
     */
    public String getInfoClass() {
        Map<String,Object> map = new HashMap<String, Object>();
        String type = dataorigin.getOriginType();
        this.setSuccessMessage("获取成功");
        map.put("message", getMessage());
        if ("teacher".equals(type) || "business".equals(type)) {
            map.put("infoClass", InfoClassCache.getInfoClass(dataorigin.getClassId()));
            map.put("versionList", new ArrayList<String>());
        } else if ("bill".equals(type)) {
            SpBillConfig config = dataOriginService.getBillConfigById(dataorigin.getClassId());
            map.put("infoClass", config);
            map.put("versionList", dataOriginService.getBillConfigsVersion(config.getId()));
        }
        this.getValueStack().set(DATA, map);
        return DATA;
    }
    
    /**
     * 下移
     * @return
     */
    public String xmlBillClassMoveDown() {
        dataOriginService.xmlBillClassMoveDown(xmlId, dataorigin.getId());
        Map<String, String> param = new HashMap<String, String>();
        param.put("xmlId", xmlId);
        DataOriginConfig dataOriginConfig = dataOriginService.getConfigsByParam(param);
        this.getValueStack().set("xmlBean", dataOriginConfig.getXmlDataOriginClasses().getDataOriginClasses());
        return "page";
    }
    
    /**
     * 上移
     * @return
     */
    public String xmlBillClassMoveUp() {
        dataOriginService.xmlBillClassMoveUp(xmlId, dataorigin.getId());
        Map<String, String> param = new HashMap<String, String>();
        param.put("xmlId", xmlId);
        DataOriginConfig dataOriginConfig = dataOriginService.getConfigsByParam(param);
        this.getValueStack().set("xmlBean", dataOriginConfig.getXmlDataOriginClasses().getDataOriginClasses());
        return "page";
    }
    /**
     * 保存
     * @return
     */
    public String saveXml() {
        if (dataorigin.getId() != null) {
            Map<String, String> param = new HashMap<String, String>();
            param.put("ywId", ywId);
            DataOriginConfig dataOriginConfig = dataOriginService.getConfigsByParam(param);
            XmlDataOriginClass olddataorigin = dataOriginConfig.getXmlDataOriginClasses().getDataOriginClassById(dataorigin.getId());
            XmlDefineCatch defineCatch = olddataorigin.getDefineCatch();
            dataorigin.setDefineCatch(defineCatch);
        }
        if (dataorigin.getVersion() == null) {
            dataorigin.setVersion(0);
        }
        dataOriginService.saveXml(ywId, dataorigin);
        this.getValueStack().set(DATA, getMessage());
        return DATA;
    }
    
    /**
     * 删除
     * @return
     */
    public String remove() {
        dataOriginService.removeXml(xmlId, dataorigin.getId());
        this.setSuccessMessage("删除成功");
        this.getValueStack().set(DATA, getMessage());
        return DATA;
    }
    
    /**
     * 自定义配置
     * @return
     */
    public String userdefined() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("xmlId", xmlId);
        DataOriginConfig dataOriginConfig = dataOriginService.getConfigsByParam(param);
        dataorigin = dataOriginConfig.getXmlDataOriginClasses().getDataOriginClassById(dataorigin.getId());
        dataorigin.getFullDefineCatch();
        return "userdefined";
    }
    
    /**
     * 保存自定义
     * @return
     */
    public String saveDefineCatch() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("xmlId", xmlId);
        DataOriginConfig dataOriginConfig = dataOriginService.getConfigsByParam(param);
        XmlDataOriginClass billClass = dataOriginConfig.getXmlDataOriginClasses().getDataOriginClassById(dataorigin.getId());
        billClass.setDefineCatch(dataorigin.getDefineCatch());
        dataOriginService.saveXml(ywId, billClass);
        this.setSuccessMessage("保存成功");
        this.getValueStack().set(DATA, getMessage());
        return DATA;
    }
    
    /**
     * 选择属性
     * @return
     */
    public String choiceproperty() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("xmlId", xmlId);
        DataOriginConfig dataOriginConfig = dataOriginService.getConfigsByParam(param);
        dataorigin = dataOriginConfig.getXmlDataOriginClasses().getDataOriginClassById(dataorigin.getId());

        List<InfoProperty> infoPropertyList = InfoClassCache.getInfoClass(dataorigin.getClassId()).getViewables();
        List<XmlBillProperty> billPropertyList = dataOriginService.getXmlBillPropertyList(xmlId, dataorigin.getId());
        if (billPropertyList == null) {
            billPropertyList = new ArrayList<XmlBillProperty>();
        }
        List<ChoiceProperty> choicePropertyList = new ArrayList<ChoiceProperty>();
        for (InfoProperty infoProperty : infoPropertyList) {
            ChoiceProperty choice = new ChoiceProperty();
            choice.setInfoProperty(infoProperty);
            for (XmlBillProperty xmlBillProperty : billPropertyList) {
                if (xmlBillProperty.getPropertyId().equals(infoProperty.getGuid())) {
                    choice.setBillProperty(xmlBillProperty);
                    choice.setChecked(true);
                    continue;
                }
            }
            if (choice.getBillProperty() == null) {
                choice.setBillProperty(new XmlBillProperty());
                choice.setInfoProperty(infoProperty);
            }
            choicePropertyList.add(choice);
        }
        
        this.getValueStack().set("choicePropertyList", choicePropertyList);
        return "choiceproperty";
    }
    
    /**
     * 修改属性
     * @return
     */
    public String saveXmlBillProperty() {
        if(xmlBillProperty.getId() == null){
            dataOriginService.addXmlBillProperty(xmlId, dataorigin.getId(), xmlBillProperty);
        }else{
            dataOriginService.modifyXmlBillProperty(xmlId, dataorigin.getId(), xmlBillProperty);
        }
        this.setSuccessMessage("保存成功");
        this.getValueStack().set(DATA, getMessage());
        return DATA;
    }
    
    /**
     * 默认样式
     * @return
     */
    public String getDefInputStyle(){
        StringBuilder sb = new StringBuilder();
        if (!com.zfsoft.hrm.baseinfo.dyna.html.Type.FILE.equals(xmlBillProperty.getFieldType()) &&
                !com.zfsoft.hrm.baseinfo.dyna.html.Type.IMAGE.equals(xmlBillProperty.getFieldType())&&
                !com.zfsoft.hrm.baseinfo.dyna.html.Type.PHOTO.equals(xmlBillProperty.getFieldType())) {
            String str = xmlBillProperty.getFieldName();
            xmlBillProperty.setFieldName("xmlBillProperty.defaultValue");
            sb.append(EditParse.parse(xmlBillProperty.getInfoProperty(), xmlBillProperty.getDefaultValue()));
            xmlBillProperty.setFieldName(str);
        }

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("result", sb.toString());
        map.put("success", true);
        getValueStack().set(DATA, map);
        return DATA;
    }
    
    /**
     * 添加属性
     * @return
     */
    public String addChoiceXmlBillProperty() {
        dataOriginService.addXmlBillProperty(xmlId, dataorigin.getId(), xmlBillProperty);
        this.setSuccessMessage("选择成功");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("message", getMessage());
        map.put("xmlBillProperty", xmlBillProperty);
        this.getValueStack().set(DATA, map);
        return DATA;
    }
    
    /**
     * 删除属性
     * @return
     */
    public String removeChoiceXmlBillProperty() {
        dataOriginService.removeXmlBillProperty(xmlId, dataorigin.getId(), xmlBillProperty.getId());
        this.setSuccessMessage("删除成功");
        this.getValueStack().set(DATA, getMessage());
        return DATA;
    }
    
    /**
     * 增加修改属性
     * @return
     */
    public String modifyXmlBillProperty() {
        if (xmlBillProperty == null || xmlBillProperty.getId() == null) {
            xmlBillProperty = new XmlBillProperty();
        } else {
            xmlBillProperty = dataOriginService.getXmlBillPropertyById(xmlId, dataorigin.getId(), xmlBillProperty.getId());
        }
        Type[] types = TypeFactory.getTypes( InfoPropertyType.class );
        this.getValueStack().set("types", types);
        
        return "addproperty";
    }
    
    /**
     * 删除属性
     * @return
     */
    public String removeXmlBillProperty() {
        dataOriginService.removeXmlBillProperty(xmlId, dataorigin.getId(), xmlBillProperty.getId());
        this.setSuccessMessage("删除成功");
        this.getValueStack().set(DATA, getMessage());
        return DATA;
    }
    /**
     * @return the jumpId
     */
    public String getJumpId() {
        return jumpId;
    }

    /**
     * @param jumpId the jumpId to set
     */
    public void setJumpId(String jumpId) {
        this.jumpId = jumpId;
    }

    /**
     * @return the dataOriginService
     */
    public IDataOriginService getDataOriginService() {
        return dataOriginService;
    }

    /**
     * @param dataOriginService the dataOriginService to set
     */
    public void setDataOriginService(IDataOriginService dataOriginService) {
        this.dataOriginService = dataOriginService;
    }

    /**
     * @return the dataorigin
     */
    public XmlDataOriginClass getDataorigin() {
        return dataorigin;
    }

    /**
     * @param dataorigin the dataorigin to set
     */
    public void setDataorigin(XmlDataOriginClass dataorigin) {
        this.dataorigin = dataorigin;
    }

    /**
     * @return the ywId
     */
    public String getYwId() {
        return ywId;
    }

    /**
     * @param ywId the ywId to set
     */
    public void setYwId(String ywId) {
        this.ywId = ywId;
    }

    /**
     * @return the xmlId
     */
    public String getXmlId() {
        return xmlId;
    }

    /**
     * @param xmlId the xmlId to set
     */
    public void setXmlId(String xmlId) {
        this.xmlId = xmlId;
    }

    /**
     * @return the xmlBillProperty
     */
    public XmlBillProperty getXmlBillProperty() {
        return xmlBillProperty;
    }

    /**
     * @param xmlBillProperty the xmlBillProperty to set
     */
    public void setXmlBillProperty(XmlBillProperty xmlBillProperty) {
        this.xmlBillProperty = xmlBillProperty;
    }

    /**
     * @return the pgId
     */
    public String getPgId() {
        return pgId;
    }

    /**
     * @param pgId the pgId to set
     */
    public void setPgId(String pgId) {
        this.pgId = pgId;
    }
    
}
