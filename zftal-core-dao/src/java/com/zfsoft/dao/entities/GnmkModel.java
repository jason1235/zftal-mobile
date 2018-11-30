package com.zfsoft.dao.entities;

import com.zfsoft.common.query.ModelBase;


/**
 * 
 * 
 * 类名称：
 * 类描述：
 * 创建人：huangxp
 * 创建时间：2012-6-28
 * 修改人：huangxp
 * 修改时间：2012-6-28
 * 修改备注： 
 * @version 
 *
 */
public class GnmkModel extends ModelBase {
    private static final long serialVersionUID = -7045393232088090639L;
    private String gnmkdm;      //功能模块代码
    private String czdm;       //操作代码
    private String gnmkmc;      //功能模块名称
    private String dyym;        //对应页面
    private String jsdm;        //角色代码
    
    public String getGnmkdm() {
        return gnmkdm;
    }
    
    public void setGnmkdm(String gnmkdm) {
        this.gnmkdm = gnmkdm;
    }
    
    public String getCzdm() {
        return czdm;
    }
    
    public void setCzdm(String czdm) {
        this.czdm = czdm;
    }
    
    public String getGnmkmc() {
        return gnmkmc;
    }
    
    public void setGnmkmc(String gnmkmc) {
        this.gnmkmc = gnmkmc;
    }
    
    public String getDyym() {
        return dyym;
    }
    
    public void setDyym(String dyym) {
        this.dyym = dyym;
    }

    
    public String getJsdm() {
        return jsdm;
    }

    
    public void setJsdm(String jsdm) {
        this.jsdm = jsdm;
    }
    
    
}
