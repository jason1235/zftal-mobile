package com.zfsoft.dao.entities;

import com.zfsoft.common.query.ModelBase;

/**
 * 类描述：学生管理范围代码
 * 创建人：caozf
 * 创建时间：2012-6-11
 * @version 
 */
public class XsglfwModel extends ModelBase {

	private static final long serialVersionUID = 8742231640608635171L;
	private String xsglfw_id; //学生管理范围代码  
	private String xx_id;     //学校                                      
	private String xy_id;     //学院                                      
	private String zy_id;     //专业                                      
	private String nj_id;     //年级                                      
	private String bj_id;     //班级
	
	private String jsdm;      //角色代码

	public String getXsglfw_id() {
		return xsglfw_id;
	}

	public void setXsglfw_id(String xsglfwId) {
		xsglfw_id = xsglfwId;
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

	public String getJsdm() {
		return jsdm;
	}

	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}

}
