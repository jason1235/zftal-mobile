package com.zfsoft.common.log;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
* 
* 类名称：User 
* 类描述：存储用户信息 
* 创建人：hhy 
* 创建时间：2011-12-20 上午10:51:28 
* 修改人：caozf 
* 修改时间：2012-07-04 上午13:51:28 
* 修改备注：  
* @version 
*
 */
public class User implements Serializable {
	private static final long serialVersionUID = -1244035756450161717L;
	//用户共用信息（学生、老师）
	private String yhm; //用户名
	private String xm;	//姓名
	private String yhlx; //用户类型：student（学生）-teacher（老师）
	private String bmdm_id; //部门代码
	private String bmmc; //部门名称
	private String errNum;//输错次数
	//老师账户特别信息
	private String gwjbdm; //岗位级别
	private String gwjbmc; //级别名称
	private String fsYhm;//附属用户名
	private String fsJb;//附属级别[1]主用户:被附属,[0]副用户:附属于	
	private List<String> jsdms; //角色列表
	private String sfqy;//是否启用

	private String scdljsdm;//上次登陆角色代码
	private List<Role> allRoles; //用户拥有的角色
	
	private String deviceId; //分析平台用户登录绑定的设备id
	private String classGrade;//账号所属班级id
	private String grade;//账号所属学院id
	private String rylx;
	
	/**
	 * 单角色登陆时：得到上次登陆角色名称
	 * @return
	 */
	public String getScdljsmc(){
		if(allRoles==null||allRoles.size()==0){
			return "";
		}
		for(Role r:allRoles){
			if(StringUtils.equals(r.getJsdm(), this.scdljsdm)){
				return r.getJsmc();
			}
		}
		return "";
	}
	
	/**
	 * 单角色登陆时：得到上次登陆角色名称
	 * @return
	 */
	public int getMaxJsmcLength(){
		int i=0;
		for(Role r:allRoles){
			if(i<r.getJsmc().length()){
				i=r.getJsmc().length();
			}
		}
		return i;
	}
	
	/**
	 * 判断用户是否为管理员
	 * @return
	 */
	public boolean isAdmin(){
		if(getJsdms()==null||getJsdms().size()==0){
			return false;
		}
		for(String jsdm : getJsdms()) {
			if(jsdm.indexOf("admin")>-1){
				return true;
			}
		}
		return false;
	}
	public String getYhm() {
		return yhm;
	}
	public void setYhm(String yhm) {
		this.yhm = yhm;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getYhlx() {
		return yhlx;
	}
	public void setYhlx(String yhlx) {
		this.yhlx = yhlx;
	}
	public String getBmmc() {
		return bmmc;
	}
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	public String getGwjbdm() {
		return gwjbdm;
	}
	public void setGwjbdm(String gwjbdm) {
		this.gwjbdm = gwjbdm;
	}
	public String getGwjbmc() {
		return gwjbmc;
	}
	public void setGwjbmc(String gwjbmc) {
		this.gwjbmc = gwjbmc;
	}
	public String getFsYhm() {
		return fsYhm;
	}
	public void setFsYhm(String fsYhm) {
		this.fsYhm = fsYhm;
	}
	public String getFsJb() {
		return fsJb;
	}
	public void setFsJb(String fsJb) {
		this.fsJb = fsJb;
	}
	public String getBmdm_id() {
		return bmdm_id;
	}
	public void setBmdm_id(String bmdmId) {
		bmdm_id = bmdmId;
	}
	public List<String> getJsdms() {
		return jsdms;
	}
	public void setJsdms(List<String> jsdms) {
		this.jsdms = jsdms;
	}
	
	public List<Role> getAllRoles() {
		return allRoles;
	}
	public void setAllRoles(List<Role> allRoles) {
		this.allRoles = allRoles;
	}
	
	public String getScdljsdm() {
		return scdljsdm;
	}
	public void setScdljsdm(String scdljsdm) {
		this.scdljsdm = scdljsdm;
	}
	
	public String getSfqy() {
		return sfqy;
	}
	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}	
	public String getErrNum() {
		return errNum;
	}

	public void setErrNum(String errNum) {
		this.errNum = errNum;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getClassGrade() {
		return classGrade;
	}

	public void setClassGrade(String classGrade) {
		this.classGrade = classGrade;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getRylx() {
		return rylx;
	}

	public void setRylx(String rylx) {
		this.rylx = rylx;
	}
 
}

