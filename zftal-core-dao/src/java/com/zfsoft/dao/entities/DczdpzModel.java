package com.zfsoft.dao.entities;


/**
 * 导出字段配置表
 * 
 * @author Administrator
 * 
 */
public class DczdpzModel{

	private String dcclbh;//导出处理编号
	
	private String dcclmc;//导出处理名称
	
	private String zd;//字段代码
	
	private String zdmc;//字段名称
	
	private String xssx;//显示顺序
	
	private String zgh;//职工号
	
	private String sfmrzd;//是否默认字段
	
	private String zt;//状态
	
	private String dczdcbv[];//选中字段
	
	private String fdczdcbv[];//非选中字段
	
	private String tableName;//表名

	public String getDcclbh() {
		return dcclbh;
	}

	public void setDcclbh(String dcclbh) {
		this.dcclbh = dcclbh;
	}

	public String getDcclmc() {
		return dcclmc;
	}

	public void setDcclmc(String dcclmc) {
		this.dcclmc = dcclmc;
	}

	public String getZd() {
		return zd;
	}

	public void setZd(String zd) {
		this.zd = zd;
	}

	public String getZdmc() {
		return zdmc;
	}

	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public String getZgh() {
		return zgh;
	}

	public void setZgh(String zgh) {
		this.zgh = zgh;
	}

	public String getSfmrzd() {
		return sfmrzd;
	}

	public void setSfmrzd(String sfmrzd) {
		this.sfmrzd = sfmrzd;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String[] getDczdcbv() {
		return dczdcbv;
	}

	public void setDczdcbv(String[] dczdcbv) {
		this.dczdcbv = dczdcbv;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String[] getFdczdcbv() {
		return fdczdcbv;
	}

	public void setFdczdcbv(String[] fdczdcbv) {
		this.fdczdcbv = fdczdcbv;
	}
}
