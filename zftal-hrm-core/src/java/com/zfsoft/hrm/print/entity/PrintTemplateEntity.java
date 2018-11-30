package com.zfsoft.hrm.print.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.zfsoft.util.jaxb.JaxbUtil;

/**
 * 
 * @author Administrator
 *
 */
public class PrintTemplateEntity {
    // ID
    private String id;
    // 模板名称
    private String mbmc;
    
    // 模板标识名
    private String mbbzm;
    
    // 内容
    private String nr;
    
    // 背景路径
    private String bjlj;
    
    // 背景宽度
    private Integer bjkd;
    
    // 背景高度
    private Integer bjgd;
    
    // 顺序码
    private String sxm;
    
    private List<PrintTemplateProperty> properties;
    
    // 背景ID
    private String bjid;
    
    // HTML
    private String imageHtml;
    
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
     * @return the mbmc
     */
    public String getMbmc() {
        return mbmc;
    }

    /**
     * @param mbmc the mbmc to set
     */
    public void setMbmc(String mbmc) {
        this.mbmc = mbmc;
    }

    /**
     * @return the mbbzm
     */
    public String getMbbzm() {
        return mbbzm;
    }

    /**
     * @param mbbzm the mbbzm to set
     */
    public void setMbbzm(String mbbzm) {
        this.mbbzm = mbbzm;
    }

    /**
     * @return the nr
     */
    public String getNr() {
        nr = JaxbUtil.getXmlFromObject(new PrintTemplatePropertyList(properties));
        return nr;
    }

    /**
     * @param nr the nr to set
     */
    public void setNr(String nr) {
        properties = JaxbUtil.getObjectFromXml(nr, PrintTemplatePropertyList.class).getPrintTemplateProperties();
        this.nr = nr;
    }

    /**
     * @return the bjlj
     */
    public String getBjlj() {
        return bjlj;
    }

    /**
     * @param bjlj the bjlj to set
     */
    public void setBjlj(String bjlj) {
        this.bjlj = bjlj;
    }

    /**
     * @return the bjkd
     */
    public Integer getBjkd() {
        return bjkd;
    }

    /**
     * @param bjkd the bjkd to set
     */
    public void setBjkd(Integer bjkd) {
        this.bjkd = bjkd;
    }

    /**
     * @return the bjgd
     */
    public Integer getBjgd() {
        return bjgd;
    }

    /**
     * @param bjgd the bjgd to set
     */
    public void setBjgd(Integer bjgd) {
        this.bjgd = bjgd;
    }

    /**
     * @return the properties
     */
    public List<PrintTemplateProperty> getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(List<PrintTemplateProperty> properties) {
        this.properties = properties;
    }

    /**
     * @return the sxm
     */
    public String getSxm() {
        return sxm;
    }

    /**
     * @param sxm the sxm to set
     */
    public void setSxm(String sxm) {
        this.sxm = sxm;
    }

    /**
     * @return the bjid
     */
    public String getBjid() {
        return bjid;
    }

    /**
     * @param bjid the bjid to set
     */
    public void setBjid(String bjid) {
        this.bjid = bjid;
    }

    /**
     * @return the imageHtml
     */
    public String getImageHtml() {
        return imageHtml;
    }

    /**
     * @param imageHtml the imageHtml to set
     */
    public void setImageHtml(String imageHtml) {
        this.imageHtml = imageHtml;
    }

    @XmlRootElement
    static class PrintTemplatePropertyList {
        
        private List<PrintTemplateProperty> printTemplateProperties;
        
        PrintTemplatePropertyList() {}
        
        PrintTemplatePropertyList(List<PrintTemplateProperty> printTemplateProperties) {
            this.printTemplateProperties = printTemplateProperties;
        }

        /**
         * @return the printTemplateProperties
         */
        @XmlElement
        public List<PrintTemplateProperty> getPrintTemplateProperties() {
            return printTemplateProperties;
        }

        /**
         * @param printTemplateProperties the printTemplateProperties to set
         */
        public void setPrintTemplateProperties(List<PrintTemplateProperty> printTemplateProperties) {
            this.printTemplateProperties = printTemplateProperties;
        }
    }
}
