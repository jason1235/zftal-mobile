package com.zfsoft.hrm.baseinfo.finfo.entities;

import java.io.Serializable;

import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.util.base.StringUtil;

/**
 * 信息维护各信息类的描述信息
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-22
 * @version V1.0.0
 */
public class FormInfoMember implements Serializable {
	
	private static final long serialVersionUID = -7676196047496192114L;

	private String	name;						//组成成员所属组的组名称
	private String 	classId;					//组成成员所使用的数据类的信息类ID
	private String 	text;						//描述组成成员的文本信息
	private String 	title;						//标题
	private String 	comment;					//注释
	private String 	tooltip;					//提示信息
	private boolean open;						//是否开放
	private boolean editable;					//是否授权编辑
	private int 	index;						//序号
	private Boolean batch=false;				//是否批量
	
	private FormInfoMemberProperty[] properties;	//成员属性 
	
	/**
	 * （空）构造函数
	 */
	public FormInfoMember() {
		//do nothing
	}
	
	/**
	 * 构造函数
	 * @param classId 组成成员所使用的数据类的信息类ID
	 * @param title 标题
	 */
	public FormInfoMember( String classId, String title ) {
		this.classId 	= classId;
		this.title 		= title;
		this.name 		= "";
		this.text 		= "";
		this.comment 	= title;
		this.tooltip 	= "";
		this.open 		= false;
		this.editable 	= false;
		this.index 		= 99;
	}

	/**
	 * 返回组成成员所属组的组名称
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置组成成员所属组的组名称
	 * @param name 组成成员所属组的组名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回组成成员所使用的数据类的信息类ID
	 */
	public String getClassId() {
		return classId;
	}

	/**
	 * 返回组成成员所使用的数据类的信息类ID
	 */
	public InfoClass getInfoClass() {
		if(StringUtil.isEmpty(classId)){
			return null;
		}
		return InfoClassCache.getInfoClass(classId);
	}
	
	/**
	 * 设置组成成员所使用的数据类的信息类ID
	 * @param classId 组成成员所使用的数据类的信息类ID
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	/**
	 * 返回描述组成成员的文本信息
	 */
	public String getText() {
		return text;
	}

	/**
	 * 设置描述组成成员的文本信息
	 * @param text 描述组成成员的文本信息
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 返回标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * @param title 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 返回注释
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 设置注释
	 * @param comment 注释
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * 返回提示信息
	 */
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * 设置提示信息
	 * @param tooltip 提示信息
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	/**
	 * 返回是否开放
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * 设置是否开放
	 * @param open 是否开放
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

	/**
	 * 返回是否授权编辑
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * 设置是否授权编辑
	 * @param editable 是否授权编辑
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * 返回成员属性 
	 */
	public FormInfoMemberProperty[] getProperties() {
		return properties;
	}

	/**
	 * 设置成员属性 
	 * @param properties 成员属性 
	 */
	public void setProperties(FormInfoMemberProperty[] properties) {
		this.properties = properties;
	}

	/**
	 * 返回序号
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 设置序号
	 * @param index 序号
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the batch
	 */
	public Boolean getBatch() {
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setBatch(Boolean batch) {
		this.batch = batch;
	}

	
}
