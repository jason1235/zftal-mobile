package com.zfsoft.hrm.baseinfo.finfo.entities;

import java.io.Serializable;

import com.zfsoft.hrm.baseinfo.dyna.html.ViewParse;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.util.base.StringUtil;

/**
 * 信息维护各信息类的属性描述信息
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-22
 * @version V1.0.0
 */
public class FormInfoMemberProperty implements Serializable {
	
	private static final long serialVersionUID = -8398481140781431505L;

	private String	pName;			//属性名
	private boolean created = true;		//是否已创建
	private boolean editable;		//是否可编辑
	private boolean viewable;		//是否显示
	private boolean highlight;		//是否高亮显示
	private boolean need;			//是否必填
	private int 	minLength;		//最小代码长度
	private String 	defaultValue;	//默认值
	private String 	alert;			//警告信息
	private int 	index;			//序号
	
	private FormInfoMember member;	//隶属成员信息
	
	/**
	 * （空）构造函数
	 */
	public FormInfoMemberProperty(){
		//do nothing
	}
	
	/**
	 * 构造函数
	 * @param pName 属性名字
	 * @param viewable 是否可显示
	 */
	public FormInfoMemberProperty( String pName, boolean viewable ) {
		this.pName			= pName;
		this.viewable 		= viewable;
		this.editable 		= true;
		this.highlight 		= false;
		this.need 			= false;
		this.minLength 		= 0;
		this.alert 			= "";
		this.defaultValue 	= null;
		this.index			= 99;
	}
	
	/**
	 * 返回属性名
	 */
	public String getpName() {
		return pName;
	}

	/**
	 * 设置属性名
	 * @param pName 属性名
	 */
	public void setpName(String pName) {
		this.pName = pName;
	}
	
	/**
	 * 设置属性名（struts2 bean 第二个字母不得为大小，特此增加一个方法）
	 * @param pName
	 */
	public void setPname( String pName ) {
		this.pName = pName;
	}

	/**
	 * 返回是否可编辑
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * 设置是否可编辑
	 * @param editable 是否可编辑
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * 返回是否显示
	 */
	public boolean isViewable() {
		return viewable;
	}

	/**
	 * 设置是否显示
	 * @param viewable 是否显示
	 */
	public void setViewable(boolean viewable) {
		this.viewable = viewable;
	}

	/**
	 * 返回是否高亮显示
	 */
	public boolean isHighlight() {
		return highlight;
	}

	/**
	 * 设置是否高亮显示
	 * @param highlight 是否高亮显示
	 */
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}

	/**
	 * 返回是否必填
	 */
	public boolean isNeed() {
		return need;
	}

	/**
	 * 设置是否必填
	 * @param need 是否必填
	 */
	public void setNeed(boolean need) {
		this.need = need;
	}

	/**
	 * 返回默认值
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * 返回默认值
	 */
	public String getDefaultValueView() {
		if(StringUtil.isEmpty(defaultValue)){return "";}
		try {
			InfoClass clazz = InfoClassCache.getInfoClass( member.getClassId() );
			InfoProperty property = clazz.getPropertyByName( pName );
			return ViewParse.parse(property, defaultValue);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	/**
	 * 设置默认值
	 * @param defaultValue 默认值
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * 返回警告信息
	 */
	public String getAlert() {
		return alert;
	}

	/**
	 * 设置警告信息
	 * @param alert 警告信息
	 */
	public void setAlert(String alert) {
		this.alert = alert;
	}

	/**
	 * 返回最小代码长度
	 */
	public int getMinLength() {
		return minLength;
	}

	/**
	 * 设置最小代码长度
	 * @param minLength 最小代码长度
	 */
	public void setMinLength(int minLength) {
		this.minLength = minLength;
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
	 * 返回隶属成员信息
	 */
	public FormInfoMember getMember() {
		return member;
	}

	/**
	 * 设置隶属成员信息
	 * @param member 隶属成员信息
	 */
	public void setMember(FormInfoMember member) {
		this.member = member;
	}

	/**
	 * 读取字段名称
	 * @return
	 */
	public String getName(){
		String name = pName;
		try {
			InfoClass clazz = InfoClassCache.getInfoClass( member.getClassId() );
			InfoProperty property = clazz.getPropertyByName( pName );
			name = property.getName();
		} catch (Exception e) {
		}
		return name;
	}

	/**
	 * 返回
	 */
	public boolean isCreated() {
		return created;
	}

	/**
	 * 设置
	 * @param created 
	 */
	public void setCreated(boolean created) {
		this.created = created;
	}
}
