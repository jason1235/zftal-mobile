package com.zfsoft.hrm.config;

import java.io.Serializable;

/**
 * 类型描述信息集合
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-28
 * @version V1.0.0
 */
public class Types implements Serializable {

	private static final long serialVersionUID = 4918335416418576227L;
	
	private Type[] types;	//类型数组
	
	/**
	 * 返回类型集合所描述的类型Class
	 */
	@SuppressWarnings("unchecked")
	public Class getTypeClass() {
		if( types == null || types.length < 1 ) {
			return Type.class;
		}
		return types[0].getClass();
	}

	/**
	 * 返回类型数组
	 */
	public Type[] getTypes() {
		return types;
	}

	/**
	 * 设置类型数组
	 * @param types 类型数组
	 */
	public void setTypes(Type[] types) {
		this.types = types;
	}

}
