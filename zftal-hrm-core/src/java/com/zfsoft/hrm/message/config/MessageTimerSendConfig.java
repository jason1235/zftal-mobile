package com.zfsoft.hrm.message.config;

import java.io.Serializable;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;
/**
 * 消息定时发送配置
 * @author gonghui
 *
 */
@Table("HRM_XXFSPZB")
public class MessageTimerSendConfig extends MyBatisBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@SQLField(key=true)
	private String id;
	
	/**消息类型*/
	@SQLField
	private String xxlx;
	
	/**是否及时推送*/
	@SQLField
	private Integer sfjsts;
	
	/**业务类型*/
	@SQLField
	private String ywlx;
	
	/**数据提取sql*/
	@SQLField
	private String sql;
	
	/**状态(1开启，0关闭)*/
	@SQLField
	private String zt;
	
	/**接收人*/
	@SQLField
	private String jsr;
	
	/**消息标题模版_接收人*/
	@SQLField
	private String xxbtmb_jsr;
	
	/**消息内容模版_接收人*/
	@SQLField
	private String xxnrmb_jsr;
	
	/**直接显示个数*/
	@SQLField
	private Integer zjxsgs;
	
	/**是否通知本人(1是，0否)*/
	@SQLField
	private String sftzbr;
	
	/**消息标题模版_本人*/
	@SQLField
	private String xxbtmb_br;
	
	/**消息内容模版_本人*/
	@SQLField
	private String xxnrmb_br;
	
	/**信息类表名称*/
	@SQLField
	private String tablename;
	
	/**信息类推送周期*/
	@SQLField
	private String xxlzq;

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getXxlx() {
		return xxlx;
	}

	public void setXxlx(String xxlx) {
		this.xxlx = xxlx;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getJsr() {
		return jsr;
	}

	public void setJsr(String jsr) {
		this.jsr = jsr;
	}

	public String getXxbtmb_jsr() {
		return xxbtmb_jsr;
	}

	public void setXxbtmb_jsr(String xxbtmb_jsr) {
		this.xxbtmb_jsr = xxbtmb_jsr;
	}

	public String getXxnrmb_jsr() {
		return xxnrmb_jsr;
	}

	public void setXxnrmb_jsr(String xxnrmb_jsr) {
		this.xxnrmb_jsr = xxnrmb_jsr;
	}

	public Integer getZjxsgs() {
		return zjxsgs;
	}

	public void setZjxsgs(Integer zjxsgs) {
		this.zjxsgs = zjxsgs;
	}

	public String getSftzbr() {
		return sftzbr;
	}

	public void setSftzbr(String sftzbr) {
		this.sftzbr = sftzbr;
	}

	public String getXxbtmb_br() {
		return xxbtmb_br;
	}

	public void setXxbtmb_br(String xxbtmb_br) {
		this.xxbtmb_br = xxbtmb_br;
	}

	public String getXxnrmb_br() {
		return xxnrmb_br;
	}

	public void setXxnrmb_br(String xxnrmb_br) {
		this.xxnrmb_br = xxnrmb_br;
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

	public String getYwlx() {
		return ywlx;
	}

	public void setXxlzq(String xxlzq) {
		this.xxlzq = xxlzq;
	}

	public String getXxlzq() {
		return xxlzq;
	}

	public void setSfjsts(Integer sfjsts) {
		this.sfjsts = sfjsts;
	}

	public Integer getSfjsts() {
		return sfjsts;
	}

	
}
