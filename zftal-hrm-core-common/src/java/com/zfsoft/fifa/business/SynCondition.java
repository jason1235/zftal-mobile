package com.zfsoft.fifa.business;

import java.io.Serializable;

/**
 * 同步主表与概况表（OVERALL）需要的内容
 * <p>
 * 如何找到需要同步的记录以及同步那些字段
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-18
 * @version V1.0.0
 */
public class SynCondition implements Serializable {

	private static final long serialVersionUID = 2326584795967660894L;

	private String condition;		//寻找主记录的条件语句
	
	private String[][] properties;	//需要同步的字段
	
	/**
	 * 寻找主记录需要的条件
	 * <p>
	 * 拼凑主记录查询语句方法:<br>
	 * conditions[i].getCondition()返回的应是标识主记录的属性名<br>
	 * 若返回null或"",则将查询出的第一条记录的内容进行同步<br>
	 * 若查询结果记录数为0,则清空OVERALL的相应字段<br>
	 * </p>
	 */
	public final String getCondition() {
		
		return this.condition;
	}
	
	/**
	 * 设置寻找主记录需要的条件
	 * @param condition 寻找主记录需要的条件
	 */
	public final void setCondition(String condition) {
		
		this.condition = condition;
	}
	
	/**
	 * 需要与OVERALL同步的字段
	 * <p>
	 * String[i][0]是需要同步的字段属性名<br>
	 * String[i][1]是OVALL表中对应的属性名称
	 * </p>
	 */
	public final String[][] getProperties() {
		
		return this.properties;
	}

	/**
	 * 需要与OVERALL同步的字段
	 * @param properties 需要与OVERALL同步的字段
	 */
	public final void setProperties(String[][] properties) {
		
		this.properties = properties;
	}
}
