package com.zfsoft.dao.entities;


import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.query.ModelBase;

/**
 * 
* 
* 类名称：JsglModel 
* 类描述： 角色管理MODEL
* 创建人：Administrator 
* 创建时间：2012-4-1 下午05:22:38 
* 修改人：Administrator 
* 修改时间：2012-4-1 下午05:22:38 
* 修改备注： 
* @version 
*
 */
public class JsglModel extends ModelBase {

	private static final long serialVersionUID = 8666379269724783798L;
	private String jsmc;
	private String jsdm;
	private String jssm;
	private String gwjbdm;
	private String gwjbmc;
	private String pkValue;
	private String len;
	private String fjgndm;
	private String xssx;
	private String dyym;
	private String czdm;
	private String czmc;
	private String[] gnmkdmcbv;
	private String[] gnczcbv;
	private String[] btns;
	private String gnmkdm;
	private String gnmkmc;
	private String bmdm_id;
	private String zgh;
	private String xm;
	private String[] yhCbv;
	private String yhnum;
	private String color;
	private String sffpyh;
	private String[] sjgndmcbv;
	private String sfksc;
	private List<JsglModel> childList;// 子模块
	private List<JsglModel> btnList;// 按钮
	private String lv;// 级别
	private String childSize;
	private String sfmrjs;
	private String sjfwzmc;
	private String yhids;  //被排除的用户id
	private String xx_id;     //学校                                      
	private String xy_id;     //学院                                      
	private String zy_id;     //专业                                      
	private String nj_id;     //年级                                      
	private String bj_id;     //班级
	private String sfejsq;    //是否可二级授权(1是0否,默认为0)
	private String ids;       //ID集合
	private List<String> list;      //list集合
	
	/**角色创建人*/
	private String jscjr;
	/**是否是管理员*/
	private Boolean isAdmin;
	/**角色创建人姓名*/
	private String jscjrxm;
	
	/**子系统编码*/
	private String sysCode;
	
	public String[] getSjgndmcbv() {
		return sjgndmcbv;
	}
	public void setSjgndmcbv(String[] sjgndmcbv) {
		this.sjgndmcbv = sjgndmcbv;
	}
	public String getSffpyh() {
		return sffpyh;
	}
	public void setSffpyh(String sffpyh) {
		this.sffpyh = sffpyh;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getYhnum() {
		return yhnum;
	}
	public void setYhnum(String yhnum) {
		this.yhnum = yhnum;
	}
	public String[] getYhCbv() {
		return yhCbv;
	}
	public void setYhCbv(String[] yhCbv) {
		this.yhCbv = yhCbv;
	}
	public String getBmdm_id() {
		return bmdm_id;
	}
	public void setBmdm_id(String bmdmId) {
		bmdm_id = bmdmId;
	}
	public String getZgh() {
		return zgh;
	}
	public void setZgh(String zgh) {
		this.zgh = zgh;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String[] getGnmkdmcbv() {
		return gnmkdmcbv;
	}
	public void setGnmkdmcbv(String[] gnmkdmcbv) {
		this.gnmkdmcbv = gnmkdmcbv;
	}
	public String[] getGnczcbv() {
		return gnczcbv;
	}
	public void setGnczcbv(String[] gnczcbv) {
		this.gnczcbv = gnczcbv;
	}
	public String getCzdm() {
		return czdm;
	}
	public void setCzdm(String czdm) {
		this.czdm = czdm;
	}
	public String getCzmc() {
		return czmc;
	}
	public void setCzmc(String czmc) {
		this.czmc = czmc;
	}
	public String getDyym() {
		return dyym;
	}
	public void setDyym(String dyym) {
		this.dyym = dyym;
	}
	public String getFjgndm() {
		return fjgndm;
	}
	public void setFjgndm(String fjgndm) {
		this.fjgndm = fjgndm;
	}
	public String getXssx() {
		return xssx;
	}
	public void setXssx(String xssx) {
		this.xssx = xssx;
	}
	public String getLen() {
		return len;
	}
	public void setLen(String len) {
		this.len = len;
	}
	public String getGnmkdm() {
		return gnmkdm;
	}
	public void setGnmkdm(String gnmkdm) {
		this.gnmkdm = gnmkdm;
	}
	public String getGnmkmc() {
		return gnmkmc;
	}
	public void setGnmkmc(String gnmkmc) {
		this.gnmkmc = gnmkmc;
	}
	public String getPkValue() {
		return pkValue;
	}
	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}
	public String getJsmc() {
		return jsmc;
	}
	public String getGwjbmc() {
		return gwjbmc;
	}
	public void setGwjbmc(String gwjbmc) {
		this.gwjbmc = gwjbmc;
	}
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}
	public String getJsdm() {
		return jsdm;
	}
	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}
	public String getJssm() {
		return jssm;
	}
	public void setJssm(String jssm) {
		this.jssm = jssm;
	}
	public String getGwjbdm() {
		return gwjbdm;
	}
	public void setGwjbdm(String gwjbdm) {
		this.gwjbdm = gwjbdm;
	}
	public String getSfksc() {
		return sfksc;
	}
	public String getChildSize() {
		return childSize;
	}
	public void setChildSize(String childSize) {
		this.childSize = childSize;
	}
	public void setSfksc(String sfksc) {
		this.sfksc = sfksc;
	}
	public List<JsglModel> getChildList() {
		return childList;
	}
	public void setChildList(List<JsglModel> childList) {
		this.childList = childList;
	}
	public List<JsglModel> getBtnList() {
		return btnList;
	}
	public void setBtnList(List<JsglModel> btnList) {
		this.btnList = btnList;
	}
	public String getLv() {
		return lv;
	}
	public void setLv(String lv) {
		this.lv = lv;
	}
	public String[] getBtns() {
		return btns;
	}
	public void setBtns(String[] btns) {
		this.btns = btns;
	}
	public String getSfmrjs() {
		return sfmrjs;
	}
	public void setSfmrjs(String sfmrjs) {
		this.sfmrjs = sfmrjs;
	}
	public String getXx_id() {
		return xx_id;
	}
	public void setXx_id(String xxId) {
		xx_id = xxId;
	}
	public String getXy_id() {
		return xy_id;
	}
	public void setXy_id(String xyId) {
		xy_id = xyId;
	}
	public String getZy_id() {
		return zy_id;
	}
	public void setZy_id(String zyId) {
		zy_id = zyId;
	}
	public String getNj_id() {
		return nj_id;
	}
	public void setNj_id(String njId) {
		nj_id = njId;
	}
	public String getBj_id() {
		return bj_id;
	}
	public void setBj_id(String bjId) {
		bj_id = bjId;
	}
    
    public String getYhids() {
        return yhids;
    }
    
    public void setYhids(String yhids) {
        this.yhids = yhids;
    }
	public String getSjfwzmc() {
		return sjfwzmc;
	}
	public void setSjfwzmc(String sjfwzmc) {
		this.sjfwzmc = sjfwzmc;
	}
	public String getSfejsq() {
		return sfejsq;
	}
	public void setSfejsq(String sfejsq) {
		this.sfejsq = sfejsq;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	
	public String getJscjr() {
		return jscjr;
	}
	public void setJscjr(String jscjr) {
		this.jscjr = jscjr;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getJscjrxm() {
		return jscjrxm;
	}
	public void setJscjrxm(String jscjrxm) {
		this.jscjrxm = jscjrxm;
	}
	public String getSfejsqStr(){
		if(StringUtils.isEmpty(this.sfejsq)){
			return null;
		}
		if(this.sfejsq.equals("1")){
			return "是";
		}else{
			return "否";
		}
	}
}
