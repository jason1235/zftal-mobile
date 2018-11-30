package com.zfsoft.hrm.baseinfo.infoclass.query;

import java.io.Serializable;

/**
 * 信息类属性查询条件
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-21
 * @version V1.0.0
 */
public class InfoPropertyQuery implements Serializable {
	
	private static final long serialVersionUID = 5283803491660130050L;

	private String name;		//属性名称（中文名称）
	
	private String fieldName;	//字段名称

	private String classId;		//信息类ID
	
	private Boolean unique;		//唯一标识
	
	private String express;      //显示、编辑、必填、同步、虚拟条件检索

	/**
	 * 返回属性名称（中文名称）
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置属性名称（中文名称）
	 * @param name 属性名称（中文名称）
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回字段名称
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 设置字段名称
	 * @param fieldName 字段名称
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 返回信息类ID
	 */
	public String getClassId() {
		return classId;
	}

	/**
	 * 设置信息类ID
	 * @param classId 信息类ID
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}

	/**
	 * 返回唯一标识
	 * @return 
	 */
	public Boolean getUnique() {
		return unique;
	}

	/**
	 * 设置唯一标识
	 * @param unique 唯一标识
	 */
	public void setUnique(Boolean unique) {
		this.unique = unique;
	}
	
	
	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

}
