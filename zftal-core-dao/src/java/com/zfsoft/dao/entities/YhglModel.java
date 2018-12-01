package com.zfsoft.dao.entities;

import com.zfsoft.common.query.ModelBase;



/**
 * 
* 
* 类名称：YhglModel 
* 类描述： 用户管理MODEL
* 创建人：Administrator 
* 创建时间：2012-4-10 下午06:46:43 
* 修改人：caozf 
* 修改时间：2012-7-4 下午13:46:43 
* 修改备注： 
* @version 
*
 */
public class YhglModel extends ModelBase {

	private static final long serialVersionUID = 1479974290911672755L;
	
	public static final String INNER_USER_ADMIN="hrmadmin";
	private String zgh;//职工号
	private String xm;//姓名
	private String mm;//密码
	private String errNum;//登录失败次数
	private String lxdh;//联系电话
	private String dzyx;//电子邮箱
	private String sfqy;//是否启用
	private String jgdm;//机构代码(部门代码)
	private String jgmc;//机构名称(部门名称)
	private String cydm_id_bmlb;//机构类别
	private String pkValue;
	
	private String scdljsdm;//上次登陆角色代码

	
	private String lxmc;//用户类型名称
	
	private String[] cbvjsxx; //角色信息主键
	
	private String jsdm;//角色代码
	
	private String jsmc;//角色名称
	
	private String jss;//拥有角色数
	
	private String ymm;//原密码
	

	
	private String sysCode;
	
	private String rylx;
	
	private String strKey;
	private int source;//用户积分（总积分）
	private String ranking;//排序
	private String graderanking;//年级排序
	private String wjlj;
	
	
	private String deviceid;
	
	
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public String getSfqy() {
		return sfqy;
	}
	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
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
	public String getMm() {
		return mm;
	}
	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getDzyx() {
		return dzyx;
	}
	public void setDzyx(String dzyx) {
		this.dzyx = dzyx;
	}
	public String[] getCbvjsxx() {
		return cbvjsxx;
	}
	public void setCbvjsxx(String[] cbvjsxx) {
		this.cbvjsxx = cbvjsxx;
	}
	public String getPkValue() {
		return pkValue;
	}
	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}
	
	public String getLxmc() {
		return lxmc;
	}
	public void setLxmc(String lxmc) {
		this.lxmc = lxmc;
	}
	public String getJsdm() {
		return jsdm;
	}
	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}
	public String getJsmc() {
		return jsmc;
	}
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}
	public String getJss() {
		return jss;
	}
	public void setJss(String jss) {
		this.jss = jss;
	}
	public String getYmm() {
		return ymm;
	}
	public void setYmm(String ymm) {
		this.ymm = ymm;
	}
	public String getJgdm() {
		return jgdm;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public String getJgmc() {
		return jgmc;
	}
	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
	}
	public String getCydm_id_bmlb() {
		return cydm_id_bmlb;
	}
	public void setCydm_id_bmlb(String cydmIdBmlb) {
		cydm_id_bmlb = cydmIdBmlb;
	}
	public String getScdljsdm() {
		return scdljsdm;
	}
	public void setScdljsdm(String scdljsdm) {
		this.scdljsdm = scdljsdm;
	}
	/**
	 * 返回
	 */
	public String getErrNum() {
		return errNum;
	}
	/**
	 * 设置
	 * @param errNum 
	 */
	public void setErrNum(String errNum) {
		this.errNum = errNum;
	}
	public void setRylx(String rylx) {
		this.rylx = rylx;
	}
	public String getRylx() {
		return rylx;
	}
	public void setStrKey(String strKey) {
		this.strKey = strKey;
	}
	public String getStrKey() {
		return strKey;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public String getWjlj() {
		return wjlj;
	}
	public void setWjlj(String wjlj) {
		this.wjlj = wjlj;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getGraderanking() {
		return graderanking;
	}
	public void setGraderanking(String graderanking) {
		this.graderanking = graderanking;
	}
	
	
}
