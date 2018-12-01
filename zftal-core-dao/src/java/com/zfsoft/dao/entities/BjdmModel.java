package com.zfsoft.dao.entities;

import com.zfsoft.common.query.ModelBase;

/**
 * 类描述：班级代码
 * 创建人：caozf
 * 创建时间：2012-6-11
 * @version 
 */
public class BjdmModel extends ModelBase {

	private static final long serialVersionUID = -3418198086311187767L;
	private String bjdm_id;  //班级代码                             
	private String bjdm;     //班级代码                                         
	private String bjmc;     //班级名称                                         
	private String zydm_id;  //所属专业（关联专业代码表ID)                      
	private String bmdm_id;  //所在学院（关联部门代码表ID）                     
	private String njdm_id;  //所属年级（关联年级代码表ID）                     
	private String xqdm_id;  //所属校区（关联校区代码表ID）                     
	private String jbny;     //建班年月                                         
	private String bzrgh_id; //班主任工号                                       
	private String bzxh_id;  //班长学号                                         
	private String fdyh_id;  //辅导员号                                         
	private String zxrs;     //在校人数                                         
	private String bz;       //备注                                             

	public String getBjdm_id() {
		return bjdm_id;
	}

	public void setBjdm_id(String bjdmId) {
		bjdm_id = bjdmId;
	}

	public String getBjdm() {
		return bjdm;
	}

	public void setBjdm(String bjdm) {
		this.bjdm = bjdm;
	}

	public String getBjmc() {
		return bjmc;
	}

	public void setBjmc(String bjmc) {
		this.bjmc = bjmc;
	}

	public String getZydm_id() {
		return zydm_id;
	}

	public void setZydm_id(String zydmId) {
		zydm_id = zydmId;
	}

	public String getBmdm_id() {
		return bmdm_id;
	}

	public void setBmdm_id(String bmdmId) {
		bmdm_id = bmdmId;
	}

	public String getNjdm_id() {
		return njdm_id;
	}

	public void setNjdm_id(String njdmId) {
		njdm_id = njdmId;
	}

	public String getXqdm_id() {
		return xqdm_id;
	}

	public void setXqdm_id(String xqdmId) {
		xqdm_id = xqdmId;
	}

	public String getJbny() {
		return jbny;
	}

	public void setJbny(String jbny) {
		this.jbny = jbny;
	}

	public String getBzrgh_id() {
		return bzrgh_id;
	}

	public void setBzrgh_id(String bzrghId) {
		bzrgh_id = bzrghId;
	}

	public String getBzxh_id() {
		return bzxh_id;
	}

	public void setBzxh_id(String bzxhId) {
		bzxh_id = bzxhId;
	}

	public String getFdyh_id() {
		return fdyh_id;
	}

	public void setFdyh_id(String fdyhId) {
		fdyh_id = fdyhId;
	}

	public String getZxrs() {
		return zxrs;
	}

	public void setZxrs(String zxrs) {
		this.zxrs = zxrs;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}
