/**   
 * @Title: Item.java 
 * @Package com.zfsoft.hrm.baseinfo.code.entities 
 * @author jinjj   
 * @date 2012-5-18 上午11:29:23 
 * @version V1.0   
 */
package com.zfsoft.hrm.baseinfo.code.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** 
 * @ClassName: ItemModel 
 * @Description: 代码条目实体
 * @author jinjj
 * @date 2012-5-18 上午11:29:23 
 *  
 */
public class Item implements Serializable{

	private static final long serialVersionUID = -4297739291601794923L;

	private String guid;//条目编号
	
	private String description;//条目信息
	
	private String comment;//条目注释
	
	private Integer visible;//是否显示
	
	private Integer dumped;//是否弃用
	
	private Integer checked;//是否可选中
	
	private Integer hasParentNodeInfo;//是否包含父节点信息
	
	private String order;//顺序码
	
	private String catalogId;//所属编目编号
	
	private String tip;//提示信息
	
	private String parentId;//条目父节点编号

	private List<Item> children = new ArrayList<Item>();
	/**
	 * Get 条目编号
	 * @return 
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Set 条目编号
	 * @param guid 
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * Get 条目信息
	 * @return 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set 条目信息
	 * @param decription 
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get 条目注释
	 * @return 
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Set 条目注释
	 * @param comment 
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Get是否显示
	 * @return 
	 */
	public Integer getVisible() {
		return visible;
	}

	/**
	 * Set是否显示
	 * @param visible 
	 */
	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	/**
	 * Get是否弃用
	 * @return 
	 */
	public Integer getDumped() {
		return dumped;
	}

	/**
	 * Set是否弃用
	 * @param dumped 
	 */
	public void setDumped(Integer dumped) {
		this.dumped = dumped;
	}

	/**
	 * Get是否可选中
	 * @return 
	 */
	public Integer getChecked() {
		return checked;
	}

	/**
	 * Set是否可选中
	 * @param checked 
	 */
	public void setChecked(Integer checked) {
		this.checked = checked;
	}

	/**
	 * Get是否包含父节点信息
	 * @return 
	 */
	public Integer getHasParentNodeInfo() {
		return hasParentNodeInfo;
	}

	/**
	 * Set是否包含父节点信息
	 * @param hasParentNodeInfo 
	 */
	public void setHasParentNodeInfo(Integer hasParentNodeInfo) {
		this.hasParentNodeInfo = hasParentNodeInfo;
	}

	/**
	 * Get顺序码
	 * @return 
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * Set顺序码
	 * @param order 
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * Get所属编目编号
	 * @return 
	 */
	public String getCatalogId() {
		return catalogId;
	}

	/**
	 * Set所属编目编号
	 * @param catalogId 
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	/**
	 * Get提示信息
	 * @return 
	 */
	public String getTip() {
		return tip;
	}

	/**
	 * Set提示信息
	 * @param tip 
	 */
	public void setTip(String tip) {
		this.tip = tip;
	}

	/**
	 * Get条目父节点编号
	 * @return 
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Set条目父节点编号
	 * @param parentId 
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * Get
	 * @return 
	 */
	public List<Item> getChildren() {
		return children;
	}

	/**
	 * Set
	 * @param children 
	 */
	public void setChildren(List<Item> children) {
		this.children = children;
	}
	
	public boolean getHasChild(){
		if(children.size()>0){
			return true;
		}else{
			return false;
		}
	}
}
