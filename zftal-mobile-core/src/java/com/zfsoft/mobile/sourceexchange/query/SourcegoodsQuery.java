package com.zfsoft.mobile.sourceexchange.query;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

public class SourcegoodsQuery extends BaseQuery{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String goodsid;//商品id
	private String goodsname;//商品名称
	private String price;//价格排序，1降序，2升序
	private Integer numbericvalue;//价格
	private String description;//商品描述
	private String picids;//商品图片id集合，以逗号隔开
	private String picpaths;//商品图片路径，以逗号隔开
	private Integer storage;//商品剩余库存
	private String isactive;//是否启用，1为启用，0为停用
	private Date createtime;
	
	public SourcegoodsQuery() {
		super();
	}
	
	
	public SourcegoodsQuery(String goodsid) {
		super();
		this.goodsid = goodsid;
	}


	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	
	public String getPicids() {
		return picids;
	}
	public void setPicids(String picids) {
		this.picids = picids;
	}
	public String getPicpaths() {
		return picpaths;
	}
	public void setPicpaths(String picpaths) {
		this.picpaths = picpaths;
	}
	public Integer getStorage() {
		return storage;
	}
	public void setStorage(Integer storage) {
		this.storage = storage;
	}
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public Integer getNumbericvalue() {
		return numbericvalue;
	}
	public void setNumbericvalue(Integer numbericvalue) {
		this.numbericvalue = numbericvalue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
