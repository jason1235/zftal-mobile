package com.zfsoft.common.query;

import java.io.Serializable;

import com.zfsoft.common.log.User;
import com.zfsoft.util.date.TimeUtil;


/**
 * 
* 类描述：所有model的基类 
* 创建人：hhy 
* 创建时间：2011-12-20 上午10:51:28 
* 修改人：caozf 
* 修改时间：2012-07-04 上午13:51:28 
* 修改备注：  
* @version 
*
 */
public class ModelBase  implements Serializable {

	private static final long serialVersionUID = -1172945282247419062L;

	private String doType;
	
	public QueryModel queryModel = new QueryModel();
	
	//学生公用属性
	private String bmdm_id;//部门代码
	private String bmmc;//部门名称
	private String zydm;//专业代码
	private String zymc;//专业名称
	private String bjdm;//班级代码
	private String bjmc;//班级名称
	
	private String zjr;  //增加人
	private String zjsj; //增加时间
	private String xgr;  //修改人
	private String xgsj; //修改时间
	
	/**
	 * 带参数构造函数，注入用户信息
	 */
	public void setModelBase(User user){
		this.setZjr(user.getYhm());
		this.setXgr(user.getYhm());
		this.setZjsj(TimeUtil.getDateTime());
		this.setXgsj(TimeUtil.getDateTime());
	}
	
	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getZydm() {
		return zydm;
	}

	public void setZydm(String zydm) {
		this.zydm = zydm;
	}

	public String getZymc() {
		return zymc;
	}

	public void setZymc(String zymc) {
		this.zymc = zymc;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public QueryModel getQueryModel() {
		return queryModel;
	}

	public void setQueryModel(QueryModel queryModel) {
		this.queryModel = queryModel;
	}

	public String getDoType() {
		return doType;
	}

	public void setDoType(String doType) {
		this.doType = doType;
	}

	public String getZjr() {
		return zjr;
	}

	public void setZjr(String zjr) {
		this.zjr = zjr;
	}

	public String getZjsj() {
		return zjsj;
	}

	public void setZjsj(String zjsj) {
		this.zjsj = zjsj;
	}

	public String getXgr() {
		return xgr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	public String getXgsj() {
		return xgsj;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}

	public String getBmdm_id() {
		return bmdm_id;
	}

	public void setBmdm_id(String bmdmId) {
		bmdm_id = bmdmId;
	}
}
