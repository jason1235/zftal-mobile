package com.zfsoft.hrm.staffturn.retire.entities;

import java.util.Date;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;
/**
 * 退休规则
 * 
 * @author gonghui
 * 2013-9-23
 */
@Table("RYYD_TXGZB")
public class RetireRule extends MyBatisBean {
	@SQLField(key=true)
	private String id;
	@SQLField
	private String cond_col;//条件字段
	@SQLField
	private String cond_val;//条件值
	@SQLField
	private String sex;//性别
	@SQLField
	private Integer retire_age;//退休年龄
	@SQLField
	private Integer seq;//序号
	@SQLField
	private Date modify_time;//修改时间
	// 20140423 add start
	@SQLField
	private String identity; // 人员身份
	@SQLField
	private String duty_level; // 行政职务级别
	@SQLField
	private String receiver; // 通知处理人
	
	private String receiverNm;
	// 20140423 add end
	
	private String cond_col_name;
	private String cond_col_code;
	
	private String manual_time;//手动执行时间  
	
	public String getManual_time() {
		return manual_time;
	}
	public void setManual_time(String manual_time) {
		this.manual_time = manual_time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCond_col() {
		return cond_col;
	}
	public void setCond_col(String cond_col) {
		this.cond_col = cond_col;
	}
	public String getCond_val() {
		return cond_val;
	}
	public void setCond_val(String cond_val) {
		this.cond_val = cond_val;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getRetire_age() {
		return retire_age;
	}
	public void setRetire_age(Integer retire_age) {
		this.retire_age = retire_age;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Date getModify_time() {
		return modify_time;
	}
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}
	
	public String getCond_col_name() {
		return cond_col_name;
	}
	public void setCond_col_name(String cond_col_name) {
		this.cond_col_name = cond_col_name;
	}
	public String getCond_col_code() {
		return cond_col_code;
	}
	public void setCond_col_code(String cond_col_code) {
		this.cond_col_code = cond_col_code;
	}
	// 20140423 add start
	/**
	 * @return the identity
	 */
	public String getIdentity() {
		return identity;
	}
	/**
	 * @param identity the identity to set
	 */
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	/**
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}
	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	/**
	 * @return the duty_level
	 */
	public String getDuty_level() {
		return duty_level;
	}
	/**
	 * @param duty_level the duty_level to set
	 */
	public void setDuty_level(String duty_level) {
		this.duty_level = duty_level;
	}
	/**
	 * @return the receiverNm
	 */
	public String getReceiverNm() {
		return receiverNm;
	}
	/**
	 * @param receiverNm the receiverNm to set
	 */
	public void setReceiverNm(String receiverNm) {
		this.receiverNm = receiverNm;
	}
	// 20140423 add end
}
