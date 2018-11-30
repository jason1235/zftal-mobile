package com.zfsoft.hrm.dataorigin.entity;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.jaxb.JaxbUtil;

/**
 * 
 * @author Administrator
 *
 */
@Table("hrm_sjyszb")
public class DataOriginConfig extends MyBatisBean {
    /**
     * 表单编号
     */
    @SQLField(key=true)
    private String id;
    /**
     * 表单名称
     */
    @SQLField
    private String ywid;
    /**
     * 表单配置内容
     */
    @SQLField
    private String sjy;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the ywid
     */
    public String getYwid() {
        return ywid;
    }

    /**
     * @param ywid the ywid to set
     */
    public void setYwid(String ywid) {
        this.ywid = ywid;
    }

    /**
     * @return the sjy
     */
    public String getSjy() {
        return sjy;
    }

    /**
     * @param sjy the sjy to set
     */
    public void setSjy(String sjy) {
        this.sjy = sjy;
    }

    public XmlDataOriginClasses getXmlDataOriginClasses() {
        if (StringUtil.isNotEmpty(this.getSjy())) {
            return JaxbUtil.getObjectFromXml(this.getSjy(), XmlDataOriginClasses.class);
        } else {
            return null;
        }
    }
    
    public void setXmlDataOriginClasses(XmlDataOriginClasses xmlDataOriginClasses){
        this.setSjy(JaxbUtil.getXmlFromObject(xmlDataOriginClasses));
    }
    
}