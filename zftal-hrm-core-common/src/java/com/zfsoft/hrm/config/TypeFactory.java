package com.zfsoft.hrm.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 类型描述信息工厂
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-28
 * @version V1.0.0
 */
@SuppressWarnings("unchecked")
public final class TypeFactory {
	
	private static List<Types> _typeses = new ArrayList<Types>();
	
	/**
	 * 设置类型记录列表
	 * @param typeses 类型集合列表
	 */
	public void setTypes( List<Types> typeses ) {
		_typeses = typeses; 
	}
	
	/**
	 * 返回指定类型描述信息集合列表
	 * @param clazz 类型的Class类 (nerver null)
	 * @return
	 */
	public static Type[] getTypes( Class clazz ) {
		Type[] result = null;
		
		for ( Types types : _typeses ) {
			if( clazz.equals( types.getTypeClass() ) ) {
				result = types.getTypes();
				break;
			}
		}
			
		return result;
	}
	
	/**
	 * 返回指定的类型描述信息
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static Type getType( Class clazz, String name ) {
		Type result = null;
		
		for ( Type type : getTypes( clazz ) ) {
			if( name.equals( type.getName() ) ) {
				result = type;
				break;
			}
		}
		
		return result;
	}

}
