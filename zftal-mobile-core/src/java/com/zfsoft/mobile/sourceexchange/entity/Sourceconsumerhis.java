package com.zfsoft.mobile.sourceexchange.entity;

import java.io.Serializable;
import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

/**
 *用户兑换商品历史记录表实体类
 * Table(value="M_SOURCE_CONSUMERHIS")
 * @author yangbilin
 * @createtime 20170708 15:54
 */
public class Sourceconsumerhis extends BaseQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;//id，作为生成的订单编号
	private String userid;//用户id
	private String goodsid;//商品id
	private int amount;//兑换数量
	private String flag;//是否兑换   0未领取   1已领取
	private Date createtime;//创建时间
	private Date exhangetime;//兑换时间
	
	private String createtimeStr;
	private String exhangetimeStr;
	private String goodsname;
	private String goodspicPath;
	private String username;
	private String status;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		createtimeStr = format.format(createtime);
		this.createtime = createtime;
	}
	public Date getExhangetime() {
		return exhangetime;
	}
	public void setExhangetime(Date exhangetime) {
		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		exhangetimeStr = format.format(exhangetime);
		this.exhangetime = exhangetime;
	}
	public String getGoodspicPath() {
		return goodspicPath;
	}
	public void setGoodspicPath(String goodspicPath) {
		this.goodspicPath = goodspicPath;
	}
	public String getCreatetimeStr() {
		return createtimeStr;
	}
	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
	}
	public String getExhangetimeStr() {
		return exhangetimeStr;
	}
	public void setExhangetimeStr(String exhangetimeStr) {
		this.exhangetimeStr = exhangetimeStr;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
