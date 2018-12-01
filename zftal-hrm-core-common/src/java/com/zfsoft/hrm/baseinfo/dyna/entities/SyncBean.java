package com.zfsoft.hrm.baseinfo.dyna.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;

/**
 * 同步实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-6
 * @version V1.0.0
 */
public class SyncBean implements Serializable {
	
	private static final long serialVersionUID = -5275534902844171505L;

	private InfoClass clazz;				//同步的目标信息类
	
	private List<String> fields;			//同步字段
	
	private Map<String, Object> values;		//参数
	
	/**
	 * 构造函数
	 * @param clazz 同步的目标信息类
	 * @param staffid 职工号
	 */
	public SyncBean( InfoClass clazz, String staffid ) {
		this.clazz = clazz;
		this.fields = new ArrayList<String>();
		this.values = new HashMap<String, Object>();
		this.values.put( "gh", staffid );
	}
	
	/**
	 * 增加同步字段
	 * @param field 同步的字段
	 * @param value 字段的值
	 */
	public void add( String field, Object value ) {
		if( has( field ) == false ) {
			return;
		}
		
		if( value == null ) {
			value = "";
		}
		
		this.fields.add( field );

		this.values.put( field, value );
	}

	/**
	 * 同步实体的类型描述中是否存在指定的字段
	 * @param pName
	 * @return
	 */
	public boolean has( String pName ) {
		
		return clazz.getPropertyByName( pName ) == null ? false : true;
	}
	/////////////////////////////
	//Getter and Setter methods//
	/////////////////////////////
	
	/**
	 * 返回同步的目标信息类
	 */
	public InfoClass getClazz() {
		return clazz;
	}

	/**
	 * 设置同步的目标信息类
	 * @param clazz 同步的目标信息类
	 */
	public void setClazz(InfoClass clazz) {
		this.clazz = clazz;
	}

	/**
	 * 返回同步字段
	 */
	public List<String> getFields() {
		return fields;
	}

	/**
	 * 设置同步字段
	 * @param fields 同步字段
	 */
	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	/**
	 * 返回参数
	 */
	public Map<String, Object> getValues() {
		return values;
	}

	/**
	 * 设置参数
	 * @param values 参数
	 */
	public void setValues(Map<String, Object> values) {
		this.values = values;
	}
	
}
