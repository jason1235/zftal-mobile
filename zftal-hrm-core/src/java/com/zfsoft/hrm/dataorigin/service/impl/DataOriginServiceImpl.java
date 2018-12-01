package com.zfsoft.hrm.dataorigin.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Encoder;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.dataorigin.dao.IDataOriginDao;
import com.zfsoft.hrm.dataorigin.entity.DataOriginConfig;
import com.zfsoft.hrm.dataorigin.entity.XmlDataOriginClass;
import com.zfsoft.hrm.dataorigin.entity.XmlDataOriginClasses;
import com.zfsoft.hrm.dataorigin.service.IDataOriginService;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.service.ISpBillExportService;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillClasses;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybill.xml.XmlCatchField;
import com.zfsoft.hrm.dybill.xml.XmlDefineCatch;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.ImageDBUtil;
import com.zfsoft.orcus.lang.TimeUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.base.Struts2Utils;
import com.zfsoft.workflow.model.SpWorkNode;
import com.zfsoft.workflow.model.SpWorkProcedure;
import com.zfsoft.workflow.service.ISpWorkFlowService;

public class DataOriginServiceImpl implements IDataOriginService {
    
    private IDataOriginDao dataOriginDao;

    /**
     * 取得表单
     */
    @Override
    public List<SpBillConfig> getBillConfigs() {
        return dataOriginDao.getBillConfigs();
    }
    
    /**
     * 取得表单
     */
    @Override
    public SpBillConfig getBillConfigById(String billId) {
        return dataOriginDao.getBillConfigById(billId);
    }

    /**
     * 保存
     */
    @Override
    public void saveXml(String ywId, XmlDataOriginClass dataorigin) {
        boolean isEdit = true;
        if (dataorigin.getId() == null) {
            isEdit = false;
        }
        //取得数据库配置对象
        DataOriginConfig dataOriginConfig = getDataOriginConfig("ywId", ywId);
        //解析配置内容转化成对象
        XmlDataOriginClasses xmlDataOriginClasses = null;
        if (dataOriginConfig != null) {
            xmlDataOriginClasses = dataOriginConfig.getXmlDataOriginClasses();
            if(!isEdit && idCheckRepeat(xmlDataOriginClasses, dataorigin)){
                throw new RuntimeException("标示名重复或者表单重复");
            }
        }
        //解析失败则新建xml映射对象
        if(xmlDataOriginClasses == null){
            xmlDataOriginClasses = new XmlDataOriginClasses();
        }
        //判断是否有信息类集合。没有则初始
        if (xmlDataOriginClasses.getDataOriginClasses() == null) {
            xmlDataOriginClasses.setDataOriginClasses(new ArrayList<XmlDataOriginClass>());
        }
        
        // 增加
        if (!isEdit) {
            //对新增的信息类设置ID
            dataorigin.setId(System.currentTimeMillis() );
            //追加新的信息类
            xmlDataOriginClasses.getDataOriginClasses().add(dataorigin);
            // 更新
        } else {
            XmlDataOriginClass old = xmlDataOriginClasses.getDataOriginClassById(dataorigin.getId());
            dataorigin.setBillPropertys(old.getBillPropertys());
            //定位配置内容
            int index = xmlDataOriginClasses.getDataOriginClasses().indexOf(old);
            xmlDataOriginClasses.getDataOriginClasses().set(index, dataorigin);
        }
        
        //更新到数据库
        if (dataOriginConfig == null || StringUtils.isEmpty(dataOriginConfig.getId())) {
            // 反解析成字串
            dataOriginConfig = new DataOriginConfig();
            dataOriginConfig.setXmlDataOriginClasses(xmlDataOriginClasses);
            dataOriginConfig.setYwid(ywId);
            // insert
            dataOriginDao.insertConfig(dataOriginConfig);
        } else {
            //反解析成字串
            dataOriginConfig.setXmlDataOriginClasses(xmlDataOriginClasses);
            // modify
            dataOriginDao.updateConfig(dataOriginConfig);
        }
    }
    
    /**
     * 取得业务xml
     */
    @Override
    public DataOriginConfig getConfigsByParam(Map<String, String> param) {
        return dataOriginDao.getConfigsByParam(param);
    }
    /**
     * 删除xml
     */
    @Override
    public void removeXml(String xmlId, Long id) {
        //取得数据库配置对象
        DataOriginConfig dataOriginConfig = getDataOriginConfig("xmlId", xmlId);
        //解析配置内容转化成对象
        XmlDataOriginClasses xmlDataOriginClasses = dataOriginConfig.getXmlDataOriginClasses();

        int index = xmlDataOriginClasses.getDataOriginClasses().indexOf(xmlDataOriginClasses.getDataOriginClassById(id));
        //删除被定位配置项
        xmlDataOriginClasses.getDataOriginClasses().remove(index);
        dataOriginConfig.setXmlDataOriginClasses(xmlDataOriginClasses);
        //更新到数据库
        dataOriginDao.updateConfig(dataOriginConfig);
    }
    
    /**
     * 取得属性
     */
    @Override
    public List<XmlBillProperty> getXmlBillPropertyList(String xmlId, Long billClassId) {
        //取得数据库配置对象
        DataOriginConfig dataOriginConfig = getDataOriginConfig("xmlId", xmlId);
        //解析配置内容转化成对象
        XmlDataOriginClasses xmlDataOriginClasses = dataOriginConfig.getXmlDataOriginClasses();
        XmlDataOriginClass xmlDataOriginClass = null;
        //判空
        if(xmlDataOriginClasses.getDataOriginClasses() == null || xmlDataOriginClasses.getDataOriginClasses().size() == 0){
            return null;
        } else {
            //取得billClassId对应信息类
            xmlDataOriginClass = xmlDataOriginClasses.getDataOriginClassById(billClassId);
            //判空
            if(xmlDataOriginClass == null || xmlDataOriginClass.getBillPropertys() == null ||  xmlDataOriginClass.getBillPropertys().size() == 0){
                return null;
            }
        }

        //返回处理对象
        return xmlDataOriginClass.getBillPropertys();
    }
    
    /**
     * 添加属性
     */
    @Override
    public void addXmlBillProperty(String xmlId, Long billClassId, XmlBillProperty xmlBillProperty) {
        //取得数据库配置对象
        DataOriginConfig dataOriginConfig = getDataOriginConfig("xmlId", xmlId);
        //解析配置内容转化成对象
        XmlDataOriginClasses xmlDataOriginClasses = dataOriginConfig.getXmlDataOriginClasses();
        //取得billClassId对应信息类
        XmlDataOriginClass xmlDataOriginClass = xmlDataOriginClasses.getDataOriginClassById(billClassId);
        //判断是否存在属性，没有则初始
        if (xmlDataOriginClass.getBillPropertys() == null) {
            xmlDataOriginClass.setBillPropertys(new ArrayList<XmlBillProperty>());
        }
        
        //如果property.getPropertyId()不空则注入信息类属性
        if(!StringUtil.isEmpty(xmlBillProperty.getPropertyId())){
            xmlBillProperty.setInfoProperty(InfoClassCache.getInfoClass(xmlDataOriginClass.getClassId()).getPropertyById(xmlBillProperty.getPropertyId()));
        }
        
        if(idCheckRepeat(xmlDataOriginClass.getBillPropertys(), xmlBillProperty)){
            throw new RuntimeException("字段名不能重复");
        }

        //对新属性赋id
        xmlBillProperty.setId(System.currentTimeMillis());
        
        //新增新属性
        xmlDataOriginClass.getBillPropertys().add(xmlBillProperty);
        dataOriginConfig.setXmlDataOriginClasses(xmlDataOriginClasses);
        //更新到数据库
        dataOriginDao.updateConfig(dataOriginConfig);
    }

    /**
     * 删除属性
     */
    @Override
    public void removeXmlBillProperty(String xmlId, Long billClassId, Long propertyId) {
        //取得数据库配置对象
        DataOriginConfig dataOriginConfig = getDataOriginConfig("xmlId", xmlId);
        //解析配置内容转化成对象
        XmlDataOriginClasses xmlDataOriginClasses = dataOriginConfig.getXmlDataOriginClasses();
        //取得billClassId对应信息类
        XmlDataOriginClass xmlDataOriginClass = xmlDataOriginClasses.getDataOriginClassById(billClassId);
        //定位配置内容
        int index = xmlDataOriginClass.getBillPropertys().indexOf(xmlDataOriginClass.getBillPropertyById(propertyId));
        //删除被定位配置项
        xmlDataOriginClass.getBillPropertys().remove(index);
        dataOriginConfig.setXmlDataOriginClasses(xmlDataOriginClasses);
        //更新到数据库
        dataOriginDao.updateConfig(dataOriginConfig);
    }
    
    /**
     * 取得属性
     */
    @Override
    public XmlBillProperty getXmlBillPropertyById(String xmlId, Long billClassId, Long propertyId) {
        //取得数据库配置对象
        DataOriginConfig dataOriginConfig = getDataOriginConfig("xmlId", xmlId);
        //解析配置内容转化成对象
        XmlDataOriginClasses xmlDataOriginClasses = dataOriginConfig.getXmlDataOriginClasses();
        //判空
        XmlDataOriginClass xmlDataOriginClass = null;
        if(xmlDataOriginClasses.getDataOriginClasses() == null) {
            return null;
        } else {
            //取得billClassId对应信息类
            xmlDataOriginClass = xmlDataOriginClasses.getDataOriginClassById(billClassId);
            //判空
            if(xmlDataOriginClass == null){
                return null;
            }
        }
        //判空
        if(xmlDataOriginClass.getBillPropertys() == null){
            return null;
        }
        //取得propertyFieldName对应的属性对象
        XmlBillProperty xmlBillProperty = xmlDataOriginClass.getBillPropertyById(propertyId);
        //判空
        if(xmlBillProperty==null){
            return null;
        }
        //返回处理对象
        return xmlBillProperty;
    }

    /**
     * 取得属性
     */
    @Override
    public List<XmlBillProperty> getPropertiesByParam(Map<String, String> param) {
        List<XmlBillProperty> items = new ArrayList<XmlBillProperty>();
        List<XmlBillProperty> tempItems = null;
        //取得数据库配置对象
        DataOriginConfig dataOriginConfig = this.getConfigsByParam(param);
        if(dataOriginConfig==null) return null;
        //解析配置内容转化成对象
        XmlDataOriginClasses xmlDataOriginClasses = dataOriginConfig.getXmlDataOriginClasses();
        //判空
        List<XmlDataOriginClass> docs = new ArrayList<XmlDataOriginClass>();
        if(xmlDataOriginClasses.getDataOriginClasses() == null) {
            return null;
        } else {
            //取得billClassId对应信息类
            docs = xmlDataOriginClasses.getDataOriginClasses();
            //判空
            if(docs.size() == 0){
                return null;
            }
        }
        
        for (XmlDataOriginClass xmlDataOriginClass : docs) {
            // 取表单数据
            if ("bill".equals(xmlDataOriginClass.getOriginType())) {
                ISpBillConfigService spBillConfigService = SpringHolder.getBean("spBillConfigService", ISpBillConfigService.class);
                SpBillConfig spBillConfig;
                if (xmlDataOriginClass.getVersion() > 0) {
                    spBillConfig = spBillConfigService.getSpBillConfigByVersion(xmlDataOriginClass.getClassId(), xmlDataOriginClass.getVersion());
                } else {
                    spBillConfig = dataOriginDao.getBillConfigById(xmlDataOriginClass.getClassId());
                }
                XmlBillClasses xmlBillClasses = spBillConfig.getXmlBillClasses();
                List<XmlBillClass> xmls = new ArrayList<XmlBillClass>();
                if(xmlBillClasses.getBillClasses() == null) {
                    continue;
                } else {
                    //取得billClassId对应信息类
                    xmls = xmlBillClasses.getBillClasses();
                    //判空
                    if(xmls.size() == 0){
                        continue;
                    }
                }
                for (XmlBillClass xmlBillClass : xmls) {
                    tempItems = xmlBillClass.getBillPropertys();
                    //判空
                    if(tempItems == null){
                        continue;
                    } else {
                        for (XmlBillProperty tp : tempItems) {
                            tp.setFieldName(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + xmlBillClass.getIdentityName() + "."+ tp.getFieldName()));
                            tp.setName(tp.getName() + "(" + xmlDataOriginClass.getName() + "-" + xmlBillClass.getName() + ")");
                            items.add(tp);
                        }
                    }
                }
            } else if ("workflow".equals(xmlDataOriginClass.getOriginType())) {
                XmlBillProperty tp = new XmlBillProperty();
                tp.setFieldName(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + ".node"));
                tp.setName("意见(" + xmlDataOriginClass.getName() + ")");
                items.add(tp);
            } else {
                tempItems = xmlDataOriginClass.getBillPropertys();
                //判空
                if(tempItems == null){
                    continue;
                } else {
                    for (XmlBillProperty tp : tempItems) {
                        tp.setFieldName(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + tp.getFieldName()));
                        tp.setName(tp.getName() + "(" + xmlDataOriginClass.getName() + ")");
                        items.add(tp);
                    }
                }
            }
        }
        //返回处理对象
        return items;
    }
    
    /**
     * 修改属性
     */
    @Override
    public void modifyXmlBillProperty(String xmlId, Long billClassId, XmlBillProperty xmlBillProperty) {
        //取得数据库配置对象
        DataOriginConfig dataOriginConfig = getDataOriginConfig("xmlId", xmlId);
        //解析配置内容转化成对象
        XmlDataOriginClasses xmlDataOriginClasses = dataOriginConfig.getXmlDataOriginClasses();
        //取得billClassId对应信息类
        XmlDataOriginClass xmlDataOriginClass = xmlDataOriginClasses.getDataOriginClassById(billClassId);
        //定位配置内容
        int index = xmlDataOriginClass.getBillPropertys().indexOf(xmlDataOriginClass.getBillPropertyById(xmlBillProperty.getId()));
        //如果property.getPropertyId()不空则注入信息类属性
        if(!StringUtil.isEmpty(xmlBillProperty.getPropertyId())){
            xmlBillProperty.setInfoProperty(InfoClassCache.getInfoClass(xmlDataOriginClass.getClassId()).getPropertyById(xmlBillProperty.getPropertyId()));
        }
        //向定位处新增配置项
        xmlDataOriginClass.getBillPropertys().set(index, xmlBillProperty);
        dataOriginConfig.setXmlDataOriginClasses(xmlDataOriginClasses);
        //更新到数据库
        dataOriginDao.updateConfig(dataOriginConfig);
    }
    
    /**
     * 取得数据
     */
    @Override
    public Map<String, Object> getOutPutData(Map<String, Object> condition, Map<String, String> billIds) {
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> getData = new HashMap<String, Object>();
        List<Map<String, Object>> data;
        Map<String, Object> param;
        List<XmlBillProperty> items;
        String dataSql = "";
        String selectSql = "";
        String tableSql = "";
        String whereSql = "";
        String spBillInstanceId = "";
        String spBillConfigId = "";
        XmlDefineCatch defineCatch;
        List<XmlCatchField> fields;
        String bm = "";
        String sqlField = "";
        
        //取得数据库配置对象
        DataOriginConfig dataOriginConfig = getDataOriginConfig("ywId", condition.get("ywId").toString());
        //解析配置内容转化成对象
        XmlDataOriginClasses xmlDataOriginClasses = dataOriginConfig.getXmlDataOriginClasses();
        //判空
        List<XmlDataOriginClass> docs = new ArrayList<XmlDataOriginClass>();
        if(xmlDataOriginClasses.getDataOriginClasses() == null) {
            return null;
        } else {
            //取得billClassId对应信息类
            docs = xmlDataOriginClasses.getDataOriginClasses();
            //判空
            if(docs.size() == 0){
                return null;
            }
        }
        
        for (XmlDataOriginClass xmlDataOriginClass : docs) {
            param = new HashMap<String, Object>();
            items = xmlDataOriginClass.getBillPropertys();
            dataSql = "";
            selectSql = "";
            tableSql = "";
            whereSql = "";
            // 取表单数据
            if ("bill".equals(xmlDataOriginClass.getOriginType())) {
                spBillConfigId = xmlDataOriginClass.getClassId();
                // 有表单的时候，取得幅画面选定数据对应的表单ID和版本
                if (billIds != null && billIds.containsKey(spBillConfigId)) {
                    spBillInstanceId = billIds.get(spBillConfigId);
                    ISpBillInstanceService spBillInstanceService = SpringHolder.getBean("spBillInstanceService", ISpBillInstanceService.class);
                    ISpBillConfigService spBillConfigService = SpringHolder.getBean("spBillConfigService", ISpBillConfigService.class);
                    ISpBillExportService spBillExportService = SpringHolder.getBean("spBillExportService", ISpBillExportService.class);
                    SpBillInstance spBillInstance = spBillInstanceService.getSpBillInstanceById(spBillConfigId, spBillInstanceId);
                    SpBillConfig spBillConfig = spBillConfigService.getSpBillConfigByVersion(spBillConfigId, spBillInstance.getVersion());
                    Map<String, String> valueMaps = spBillExportService.getValueMapForExport(spBillConfig, spBillInstance);

                    if (!spBillInstance.getVersion().toString().equals(xmlDataOriginClass.getVersion().toString())) {
                        ret.put("warn", "模板配置的表单版本和选中使用的表单版本不一致，可能会导致部分数据丢失。");
                    }
                    Set<String> keys = valueMaps.keySet();
                    for (Iterator<String> it = keys.iterator(); it.hasNext();) {
                        String key = (String)it.next();
                        ret.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + key), valueMaps.get(key));
                    }
                }
            } else if ("workflow".equals(xmlDataOriginClass.getOriginType())) {
                ISpWorkFlowService spWorkFlowService = SpringHolder.getBean("spWorkFlowService", ISpWorkFlowService.class);
                SpWorkProcedure work = spWorkFlowService.queryWorkFlowByWorkId(condition.get("workId").toString());
                List<SpWorkNode> nodes = work.getSpWorkNodeList();
                int i = 1;
                for (SpWorkNode n : nodes) {
                    if (!StringUtils.isEmpty(n.getSuggestion())) {
                        ret.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + ".node(" + i), n.getSuggestion());
                    }
                    i++;
                }
            // 自定义
            } else if ("".equals(xmlDataOriginClass.getOriginType())) {
                // 默认抓取
                defineCatch = xmlDataOriginClass.getDefineCatch();
                // 判空
                if (items == null || defineCatch == null) {
                    continue;
                } else {
                    // 抓取字段
                    fields = defineCatch.getCatchFields();
                    // 拼接SQL
                    for (XmlCatchField p1 : fields) {
                        for (XmlBillProperty it : items) {
                            if (it.getId().equals(p1.getBillPropertyId())) {
                                bm = it.getFieldName();
                                if (StringUtils.isEmpty(p1.getFieldName())) {
                                    sqlField = it.getDefaultValue();
                                } else {
                                    sqlField = p1.getFieldName();
                                }
                                
                                
                                break;
                            }
                        }
                        
                        if (StringUtils.isEmpty(selectSql)) {
                            selectSql = sqlField + " as " + bm;
                        } else {
                            selectSql += ", " + sqlField + " as " + bm;
                        }
                    }
                    
                    if (StringUtils.isEmpty(selectSql)) {
                        continue;
                    } else {
                        selectSql = " select " + selectSql + " ";
                        tableSql = " from " + defineCatch.getTableName() + " ";
                        whereSql = " where " + getFullExpress(defineCatch.getExpression(), getData, condition);
                        
                        dataSql = "select * from (select a.*, rownum rn from(" + selectSql + tableSql + whereSql + ") a )" + "where rn >= 1 and rn <= " + xmlDataOriginClass.getRecordCnt();
                        
                        param.put("dataSql", dataSql);
                        data = dataOriginDao.getOutPutData(param);
                    }
                    
                    Integer recordCnt = xmlDataOriginClass.getRecordCnt();
                    if (data == null) {
                        for (Integer index = 1; index <= recordCnt; index++) {
                            // SQL取得内容和属性关联
                            for (XmlCatchField p2 : fields) {
                                for (XmlBillProperty p3 : items) {
                                    if (p2.getBillPropertyId().equals(p3.getId())) {
                                        if (recordCnt > 1) {
                                            getData.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p3.getFieldName()) + "(" + index, "");
                                            ret.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p3.getFieldName()) + "(" + index, "");
                                        } else {
                                            getData.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p3.getFieldName()), "");
                                            ret.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p3.getFieldName()), "");
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Integer index = 1;
                        for (Map<String, Object> m : data) {
                            // SQL取得内容和属性关联
                            for (XmlCatchField p2 : fields) {
                                for (XmlBillProperty p3 : items) {
                                    if (p2.getBillPropertyId().equals(p3.getId())) {
                                        if (recordCnt > 1) {
                                            getData.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p3.getFieldName()) + "(" + index,
                                                m.get(StringUtils.upperCase(p3.getFieldName())));
                                            ret.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p3.getFieldName()) + "(" + index,
                                                getValue(p3, m.get(StringUtils.upperCase(p3.getFieldName()))));
                                        } else {
                                            getData.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p3.getFieldName()),
                                                m.get(StringUtils.upperCase(p3.getFieldName())));
                                            ret.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p3.getFieldName()),
                                                getValue(p3, m.get(StringUtils.upperCase(p3.getFieldName()))));
                                        }
                                    }
                                }
                            }
                            index++;
                        }
                    }
                }
            } else {
                // 判空
                if (items == null) {
                    continue;
                } else {
                    // 拼接SQL
                    for (XmlBillProperty p1 : items) {
                        if (StringUtils.isEmpty(selectSql)) {
                            selectSql = p1.getFieldName();
                        } else {
                            selectSql += ", " + p1.getFieldName();
                        }
                    }
                    
                    if (StringUtils.isEmpty(selectSql)) {
                        continue;
                    } else {
                        selectSql = " select " + selectSql + " ";
                        tableSql = " from " + xmlDataOriginClass.getIdentityName() + " ";
                        whereSql = " where gh = '" + condition.get("gh") + "'";
                        
                        dataSql = "select * from (select a.*, rownum rn from(" + selectSql + tableSql + whereSql + ") a )" + "where rn >= 1 and rn <= " + xmlDataOriginClass.getRecordCnt();
                        
                        param.put("dataSql", dataSql);
                        data = dataOriginDao.getOutPutData(param);
                    }
                    
                    Integer recordCnt = xmlDataOriginClass.getRecordCnt();
                    if (data == null) {
                        for (Integer index = 1; index <= recordCnt; index++) {
                            // SQL取得内容和属性关联
                            for (XmlBillProperty p2 : items) {
                                if (recordCnt > 1) {
                                    getData.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p2.getFieldName()) + "(" + index, "");
                                    ret.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p2.getFieldName()) + "(" + index, "");
                                } else {
                                    getData.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p2.getFieldName()), "");
                                    ret.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p2.getFieldName()), "");
                                }
                            }
                        }
                    } else {
                        Integer index = 1;
                        for (Map<String, Object> m : data) {
                            // SQL取得内容和属性关联
                            for (XmlBillProperty p2 : items) {
                                if (recordCnt > 1) {
                                    getData.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p2.getFieldName()) + "(" + index,
                                        m.get(StringUtils.upperCase(p2.getFieldName())));
                                    ret.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p2.getFieldName()) + "(" + index,
                                        getValue(p2, m.get(StringUtils.upperCase(p2.getFieldName()))));
                                } else {
                                    getData.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p2.getFieldName()),
                                        m.get(StringUtils.upperCase(p2.getFieldName())));
                                    ret.put(StringUtils.lowerCase(xmlDataOriginClass.getIdentityName() + "." + p2.getFieldName()),
                                        getValue(p2, m.get(StringUtils.upperCase(p2.getFieldName()))));
                                }
                            }
                            index++;
                        }
                    }
                }
            }
        }
        
        return ret;
    }
    
    /**
     * 取得版本
     */
    @Override
    public List<String> getBillConfigsVersion(String id) {
        return dataOriginDao.getBillConfigsVersion(id);
    }
    
    /**
     * 上移
     */
    @Override
    public void xmlBillClassMoveUp(String xmlId, Long billClassId) {
        //取得数据库配置对象
        DataOriginConfig dataOriginConfig = getDataOriginConfig("xmlId", xmlId);
        //解析配置内容转化成对象
        XmlDataOriginClasses xmlDataOriginClasses = dataOriginConfig.getXmlDataOriginClasses();
        
        XmlDataOriginClass xmlBillclass;
        XmlDataOriginClass swapXmlBillclass;
        for (int i = 0; i < xmlDataOriginClasses.getDataOriginClasses().size(); i++) {
            xmlBillclass = xmlDataOriginClasses.getDataOriginClasses().get(i);
            if (xmlBillclass.getId().equals(billClassId)) {
                if (i == 0) {
                    return;
                }
                swapXmlBillclass = xmlDataOriginClasses.getDataOriginClasses().get(i - 1);
                xmlDataOriginClasses.getDataOriginClasses().set(i - 1, xmlBillclass);
                xmlDataOriginClasses.getDataOriginClasses().set(i, swapXmlBillclass);
                break;
            }
        }
        
        //反解析成字串
        dataOriginConfig.setXmlDataOriginClasses(xmlDataOriginClasses);
        // modify
        dataOriginDao.updateConfig(dataOriginConfig);
    }

    /**
     * 下移
     */
    @Override
    public void xmlBillClassMoveDown(String xmlId, Long billClassId) {
        //取得数据库配置对象
        DataOriginConfig dataOriginConfig = getDataOriginConfig("xmlId", xmlId);
        //解析配置内容转化成对象
        XmlDataOriginClasses xmlDataOriginClasses = dataOriginConfig.getXmlDataOriginClasses();
        
        XmlDataOriginClass xmlBillclass;
        XmlDataOriginClass swapXmlBillclass;
        for (int i = 0; i < xmlDataOriginClasses.getDataOriginClasses().size(); i++) {
            xmlBillclass = xmlDataOriginClasses.getDataOriginClasses().get(i);
            if (xmlBillclass.getId().equals(billClassId)){ 
                if (i == xmlDataOriginClasses.getDataOriginClasses().size() - 1) {
                    return;
                }
                swapXmlBillclass = xmlDataOriginClasses.getDataOriginClasses().get(i + 1);
                xmlDataOriginClasses.getDataOriginClasses().set(i + 1, xmlBillclass);
                xmlDataOriginClasses.getDataOriginClasses().set(i, swapXmlBillclass);
                break;
            }
        }
        
        //反解析成字串
        dataOriginConfig.setXmlDataOriginClasses(xmlDataOriginClasses);
        // modify
        dataOriginDao.updateConfig(dataOriginConfig);
    }
    
    /**
     * 
     * @return
     */
    private String getValue(XmlBillProperty property, Object value) {
        byte[] fileContent = null;
        byte[] def_photo = null;
        if (value == null) {
            if (Type.PHOTO.equals(property.getFieldType()) || Type.IMAGE.equals(property.getFieldType())) {
                ImageDB image = ImageDBUtil.getImageDBByGuid("default_" + property.getFieldType());
                def_photo = getContent(image, property.getFieldType());
            } else {
                return "";
            }
        }
        if(Type.CODE.equals(property.getFieldType())){
            return CodeUtil.getItemValue(property.getCodeId(), value.toString());
        }
        if(Type.SIGLE_SEL.equals(property.getFieldType())){
            return "1".equals(value.toString()) ? "是" : "否";
        }
        
        if(Type.DATE.equals(property.getFieldType())){
            return TimeUtil.format(value.toString(), "yyyy-MM-dd");
        }
        
        if(Type.MONTH.equals(property.getFieldType())){
            return TimeUtil.format(value.toString(), "yyyy-MM");
        }

        if(Type.YEAR.equals(property.getFieldType())){
            return TimeUtil.format(value.toString(), "yyyy");
        }


        if (Type.PHOTO.equals(property.getFieldType()) || Type.IMAGE.equals(property.getFieldType())) {
            
            if (value == null) {
                fileContent = def_photo;
            } else {
                ImageDB image = ImageDBUtil.getImageDBByGuid(value.toString());
                fileContent = getContent(image, property.getFieldType());
            }
            return new BASE64Encoder().encode(fileContent);
        }
        return value.toString();
    }
    
    /**
     * 
     * @param imageDB
     * @param type
     * @return
     */
    private static byte[] getContent(ImageDB imageDB, String type){
        String path = Struts2Utils.getSession().getServletContext().getRealPath("/");
        if (imageDB == null) {
            path += "upload" + File.separator+"default_" + type.toLowerCase() + ".jpg";
            return Byte_File_Object.getBytesFromFile(new File(path));
        } else {
            if (StringUtil.isEmpty(imageDB.getPath())) {
                return imageDB.getFileContent();
            } else {
                if (imageDB.getFileContent() == null && !StringUtil.isEmpty(imageDB.getPath())) {
                    path += "upload" + File.separator + imageDB.getGuid() + "." + imageDB.getSuffixs();
                    return Byte_File_Object.getBytesFromFile(new File(path));
                } else {
                    return imageDB.getFileContent();
                }
            }
        }
    }
    
    /**
     * 取得抓取条件
     * @param expresstion
     * @param ret
     * @param condition
     * @return
     */
    private String getFullExpress(String expresstion, Map<String, Object> ret, Map<String, Object> condition){
        if (expresstion == null || "".equals(expresstion.trim())) {
            return " 1=1";
        }
        
        if (ret == null || condition == null) {
            return expresstion;
        }
        
        List<String> billField = getFields(expresstion);
        String reString = expresstion; 
        if (!billField.isEmpty()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.putAll(ret);
            map.putAll(condition);
            for (String str : billField) {
                if (str.length() < 3) {
                    continue;
                }
                String key = StringUtils.lowerCase(str.substring(2, str.length() - 1));
                String value = "";
                if (map.containsKey(key)) {
                    value = map.get(key).toString();
                }
                reString = reString.replaceAll(Pattern.quote(str), value);
            }
        }
        
        return reString;
    }
    
    /**
     * 取得参数
     * @param expresstion
     * @param regex
     * @return
     */
    private static List<String> getFields(String expresstion) {
        Pattern p = Pattern.compile("\\$\\{[^\\}]*\\}");
        Matcher m = p.matcher(expresstion);
        List<String> fields = new ArrayList<String>();
        while (m.find()) {
            fields.add(m.group());
        }
        return fields;
    }
    
    /**
     * 校验
     * @param datas
     * @param newdata
     * @return
     */
    private boolean idCheckRepeat(XmlDataOriginClasses datas, XmlDataOriginClass newdata){
        if (datas == null || datas.getDataOriginClasses() == null) {
            return false;
        }
        for (XmlDataOriginClass c : datas.getDataOriginClasses()){
            if (StringUtils.lowerCase(c.getIdentityName()).equals(StringUtils.lowerCase(newdata.getIdentityName()))) {
                return true;
            } else {
                if ("bill".equals(newdata.getOriginType()) && c.getClassId().equals(newdata.getClassId())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 校验
     * @param billPropertys
     * @param xmlBillProperty
     * @return
     */
    private boolean idCheckRepeat(List<XmlBillProperty> billPropertys, XmlBillProperty xmlBillProperty) {
        if (billPropertys == null || billPropertys.size() == 0) {
            return false;
        }
        for (XmlBillProperty copXmlBillProperty : billPropertys){
            if(StringUtils.lowerCase(copXmlBillProperty.getFieldName()).equals(StringUtils.lowerCase(xmlBillProperty.getFieldName()))) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 取得xml
     * @return
     */
    private DataOriginConfig getDataOriginConfig(String key, String value) {
        Map<String, String> param = new HashMap<String, String>();
        param.put(key, value);
        //取得数据库配置对象
        return this.getConfigsByParam(param);
    }
    
    /**
     * @return the dataOriginDao
     */
    public IDataOriginDao getDataOriginDao() {
        return dataOriginDao;
    }

    /**
     * @param dataOriginDao the dataOriginDao to set
     */
    public void setDataOriginDao(IDataOriginDao dataOriginDao) {
        this.dataOriginDao = dataOriginDao;
    }

}
