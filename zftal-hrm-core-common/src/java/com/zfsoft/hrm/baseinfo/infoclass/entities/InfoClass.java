package com.zfsoft.hrm.baseinfo.infoclass.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.zfsoft.hrm.config.TypeFactory;
import com.zfsoft.hrm.config.type.InfoClassType;
import com.zfsoft.util.base.StringUtil;

/**
 * 信息类实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-21
 * @version V1.0.0
 */
public class InfoClass implements Cloneable, Serializable {

	private static final long serialVersionUID = 7761270530567720794L;

	private String guid;						//信息类ID

	private String name;						//信息类名称（中文）

	private String identityName;				//标识名（英文）,用于建表

	private String type;						//信息类类型（比如综合信息类、单记录普通信息类、多记录普通信息类)

	private Boolean lessThanOne;				//最少一条记录

	private Catalog catalog;					//所属目录

	private Integer index;						//显示顺序

	private String  xxly="self";                       //信息来源 System:系统初始化 ;self:用户自增(默认)

	private Integer displayNum = 2;					//展示数/行

	private String menuId;

	private boolean  commitable;              	//是否申报表单

	private List<InfoProperty> properties = new ArrayList<InfoProperty>();		//信息类属性列表

	private List<InfoProperty> editables = new ArrayList<InfoProperty>();		//可编辑属性列表

	private List<InfoProperty> viewables = new ArrayList<InfoProperty>();		//可显示属性列表

	private List<InfoProperty> uniqables = new ArrayList<InfoProperty>();		//唯一性标识属性列表

	private String propertiesList;

	private String pxfs; //排序方式
	private String[] pxfsList; //排序方式

	private String pxsx; //排序属性

	private String scanStyle; //显示类型
	/**
	 * 返回所有唯一性标识属性列表
	 */
	public List<InfoProperty> getUniqables(){
		return uniqables;
	}

	/**
	 * 返回信息类类型
	 */
	public InfoClassType getTypeInfo() {
		if( type == null ) {
			return null;
		}

		return (InfoClassType) TypeFactory.getType( InfoClassType.class, type );
	}
	/**
	 * 返回信息类全局ID
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * 设置信息类全局ID
	 * @param guid 信息类全局ID
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 返回信息类名称（中文）
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置信息类名称（中文）
	 * @param name 信息类名称（中文）
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回标识名（英文）,用于建表
	 * @return 标识名一律转大写
	 */
	public String getIdentityName() {
		if( identityName != null ) {
			identityName = identityName.toUpperCase();
		}

		return identityName;
	}

	/**
	 * 设置标识名（英文）,用于建表
	 * @param identityName 标识名（英文）,用于建表
	 */
	public void setIdentityName(String identityName) {
		if( identityName != null ) {
			identityName = identityName.toUpperCase();
		}

		this.identityName = identityName;
	}

	/**
	 * 返回信息类类别 （比如综合信息类、单记录普通信息类、多记录普通信息类)
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置信息类类别 （比如综合信息类、单记录普通信息类、多记录普通信息类)
	 * @param type 信息类类别 （比如综合信息类、单记录普通信息类、多记录普通信息类)
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 返回所属目录
	 */
	public Catalog getCatalog() {
		return catalog;
	}

	/**
	 * 设置所属目录
	 * @param catalog 所属目录
	 */
	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	/**
	 * 返回显示顺序
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * 设置显示顺序
	 * @param index 显示顺序
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * 返回是否最少一条记录
	 */
	public Boolean getLessThanOne() {
		return lessThanOne;
	}

	/**
	 * 设置是否最少一条记录
	 * @param lessThanOne 是否最少一条记录
	 */
	public void setLessThanOne(Boolean lessThanOne) {
		this.lessThanOne = lessThanOne;
	}

	/**
	 * 返回信息类属性列表
	 */
	public List<InfoProperty> getProperties() {

		return properties;
	}

	/**
	 * 设置信息类属性列表
	 * @param properties 信息类属性列表
	 */
	public void setProperties(List<InfoProperty> properties) {
		Gson gson = new Gson();
		propertiesList = gson.toJson(properties);
		this.properties = new ArrayList<InfoProperty>();
		this.editables=new ArrayList<InfoProperty>();
		this.viewables=new ArrayList<InfoProperty>();
		this.uniqables=new ArrayList<InfoProperty>();

		for (InfoProperty property : properties) {
			addProperty( property );
		}

	}

	/**
	 * 增加信息类属性
	 * @param property 信息类属性描述信息
	 */
	public void addProperty( InfoProperty property ) {
		properties.add( property );

		if( property.getEditable() ) {
			editables.add(property);
		}

		if( property.getViewable() ) {
			viewables.add(property);
		}

		if( property.getUnique() ) {
			uniqables.add( property );
		}
	}

	/**
	 * 获取可编辑字段属性列表
	 * @return
	 */
	public List<InfoProperty> getEditables() {

		return editables;
	}
	public void setEditables(List<InfoProperty> editables) {

		this.editables=editables;
	}

	/**
	 * 获取可显示字段属性列表
	 * @return
	 */
	public List<InfoProperty> getViewables() {
		return viewables;
	}

	public String getPropString(){
		String str="";
		if(properties==null)return "";
		for(InfoProperty infoProp:this.properties){
			str+= infoProp.getName()+" ";
		}
		return str;
	}

	/**
	 * 获取指定名字的字段属性信息(忽略大小写)
	 * @param pName 字段属性名字，columnName
	 * @return
	 */
	public InfoProperty getPropertyByName( String pName ) {
		for ( InfoProperty property : properties ) {
			if( property.getFieldName().equalsIgnoreCase( pName ) ) {
				return property;
			}
		}

		return null;
	}

	/**
	 * 获取指定ID的字段属性信息
	 * @param pId 字段属性的全局ID
	 * @return
	 */
	public InfoProperty getPropertyById( String pId ) {
		for ( InfoProperty property : properties ) {
			if( property.getGuid().equalsIgnoreCase( pId ) ) {
				return property;
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public InfoClass clone() {
		try {
			InfoClass clone=(InfoClass)super.clone();
			List<InfoProperty> properties = new ArrayList<InfoProperty>();
			for (InfoProperty infoProperty : this.getProperties()) {
				properties.add(infoProperty.clone());
			}
			clone.setProperties(properties);
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return null;
	}
	public List<InfoProperty> getMarkInfoProperties() {
		List<InfoProperty> markList=new ArrayList<InfoProperty>();
		if(properties==null){
			return markList;
		}
		for(InfoProperty infoProperty:properties){
			if(infoProperty.getUnique()){
				markList.add(infoProperty);
			}
		}
		return markList;
	}
	/**
	 * 是否多条数据
	 */
	public boolean isMoreThanOne(){
		if( type == null || "".equals( type ) ) {
			return true;
		}

		return ( (InfoClassType) TypeFactory.getType( InfoClassType.class, type ) ).isMoreThanOne();
	}

	/**
	 * 信息来源
	 */
	public String getXxly() {
		return xxly;
	}

	public void setXxly(String xxly) {
		this.xxly = xxly;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Integer getDisplayNum() {
		return displayNum;
	}

	public void setDisplayNum(Integer displayNum) {
		this.displayNum = displayNum;
	}
	/**
	 * 是否申报表单
	 * @return
	 */
	public boolean isCommitable() {
		return commitable;
	}

	public String getCommitableText() {
		return commitable+"";
	}

	public void setCommitable(boolean commitable) {
		this.commitable = commitable;
	}

	public void setViewables(List<InfoProperty> viewables) {
		this.viewables = viewables;
	}

	public String getPxfs() {
		return pxfs;
	}

	public void setPxfs(String pxfs) {
		this.pxfs = pxfs;
	}

	public String getPxzd() {
		if(StringUtil.isEmpty(pxsx)){
			return null;
		}
		String pxzd=pxsx;
		/*if(!StringUtil.isEmpty(pxfs)){
			pxzd+=" "+pxfs;
		}*/
		return pxzd;
	}

	public void setPxzd(String pxzd) {
		if(!StringUtil.isEmpty(pxzd)){
			pxsx = pxzd;
			/*String[] arr=pxzd.split(",");
			pxsx = arr[0];
			if(arr.length>=2){
				pxfs = arr[1];
			}*/
		}
	}

	public String getPxsx() {
		return pxsx;
	}

	public void setPxsx(String pxsx) {
		this.pxsx = pxsx;
	}

	public String getScanStyle() {
		return scanStyle;
	}

	public void setScanStyle(String scanStyle) {
		this.scanStyle = scanStyle;
		pxfs = scanStyle;
	}

	public void setPxfsList(String[] pxfsList) {
		this.pxfsList = pxfsList;
	}

	public String[] getPxfsList() {
		return pxfsList;
	}

	public void setPropertiesList(String propertiesList) {
		this.propertiesList = propertiesList;
	}

	public String getPropertiesList() {
		return propertiesList;
	}





}
