package com.zfsoft.hrm.print.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Administrator
 *
 */
@XmlRootElement
public class PrintTemplateProperty {
    
    // 项目类别
    private String xmType;
    // 项目
    private String xm;
    // 内容
    private String nr;
    // 横坐标
    private String xzb;
    // 纵坐标
    private String yzb;
    // 项目宽
    private String xmk;
    // 项目高
    private String xmg;
    // 字体
    private String font;
    // 字体大小
    private String fontSize;
    // 字体位置
    private String fontLocation;
    // 粗体
    private String bold;
    // 斜体
    private String italic;
    // 下划线
    private String underline;
    // 标记
    private String mark;
    
    /**
     * @return the xmType
     */
    public String getXmType() {
        return xmType;
    }
    /**
     * @param xmType the xmType to set
     */
    public void setXmType(String xmType) {
        this.xmType = xmType;
    }
    /**
     * @return the xm
     */
    public String getXm() {
        return xm;
    }
    /**
     * @param xm the xm to set
     */
    public void setXm(String xm) {
        this.xm = xm;
    }
    /**
     * @return the nr
     */
    public String getNr() {
        return nr;
    }
    /**
     * @param nr the nr to set
     */
    public void setNr(String nr) {
        this.nr = nr;
    }
    /**
     * @return the xzb
     */
    public String getXzb() {
        return xzb;
    }
    /**
     * @param xzb the xzb to set
     */
    public void setXzb(String xzb) {
        this.xzb = xzb;
    }
    /**
     * @return the yzb
     */
    public String getYzb() {
        return yzb;
    }
    /**
     * @param yzb the yzb to set
     */
    public void setYzb(String yzb) {
        this.yzb = yzb;
    }
    /**
     * @return the xmk
     */
    public String getXmk() {
        return xmk;
    }
    /**
     * @param xmk the xmk to set
     */
    public void setXmk(String xmk) {
        this.xmk = xmk;
    }
    /**
     * @return the xmg
     */
    public String getXmg() {
        return xmg;
    }
    /**
     * @param xmg the xmg to set
     */
    public void setXmg(String xmg) {
        this.xmg = xmg;
    }
    /**
     * @return the font
     */
    public String getFont() {
        return font;
    }
    /**
     * @param font the font to set
     */
    public void setFont(String font) {
        this.font = font;
    }
    /**
     * @return the fontSize
     */
    public String getFontSize() {
        return fontSize;
    }
    /**
     * @param fontSize the fontSize to set
     */
    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }
    /**
     * @return the fontLocation
     */
    public String getFontLocation() {
        return fontLocation;
    }
    /**
     * @param fontLocation the fontLocation to set
     */
    public void setFontLocation(String fontLocation) {
        this.fontLocation = fontLocation;
    }
    /**
     * @return the bold
     */
    public String getBold() {
        return bold;
    }
    /**
     * @param bold the bold to set
     */
    public void setBold(String bold) {
        this.bold = bold;
    }
    /**
     * @return the italic
     */
    public String getItalic() {
        return italic;
    }
    /**
     * @param italic the italic to set
     */
    public void setItalic(String italic) {
        this.italic = italic;
    }
    /**
     * @return the underline
     */
    public String getUnderline() {
        return underline;
    }
    /**
     * @param underline the underline to set
     */
    public void setUnderline(String underline) {
        this.underline = underline;
    }
    /**
     * @return the mark
     */
    public String getMark() {
        return mark;
    }
    /**
     * @param mark the mark to set
     */
    public void setMark(String mark) {
        this.mark = mark;
    }
    
}
