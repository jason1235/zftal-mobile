package com.zfsoft.dao.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.util.reflect.ReflectionUtils;

/**
 * 注解bean的解析工具类和静态缓冲区
 * @author 沈鲁威 Patrick Shen
 * @since 2012-8-8
 * @version V1.0.0
 */
public class MybatisBeanContext {
	
	private static Map<Class<?>, StackHelper> contextMap=new HashMap<Class<?>, StackHelper>();

	/**
	 * 获取静态的类结构，没有则创建
	 * @param clasz
	 * @return
	 */
	public static StackHelper getClassContextMap(Class<?> clasz) {
		if(contextMap.get(clasz)==null){
			contextMap.put(clasz, initStack(clasz));
		}
		return contextMap.get(clasz);
	}
	
	/**
	 * 构建类的结构类对象
	 * @param clasz
	 * @return
	 */
	private static StackHelper initStack(Class<?> clasz) {
		StackHelper helper = new StackHelper();
		Table table=clasz.getAnnotation(Table.class);
		if(table!=null){
			helper.setTableName(table.value());
		}
			
		Field[] fields=null;
		Class<?> cla=clasz;
		while(cla!=null){
			fields=cla.getDeclaredFields();
			for(Field field:fields)
				helper.getFieldMap().put(field.getName(),field);
			cla=cla.getSuperclass();
		}
		
		for(Field field:helper.getFieldMap().values()){
			if(field.getAnnotation(SQLField.class)!=null){
				SQLField sqlField=field.getAnnotation(SQLField.class);
				if(sqlField.key()){
					if(sqlField.value().equals("[unsignedField]")){
						helper.setKeyOf(field.getName());
					}else{
						helper.setKeyOf(sqlField.value());
					}
				}
				helper.getSqlMap().put(field.getName(),sqlField);
			}
		}
		
		for(String fieldName:helper.getSqlMap().keySet()){
			if(helper.getSqlMap().get(fieldName).value().equals("[unsignedField]")){
				helper.getSqlLocalNameMap().put(fieldName,fieldName);
			}
			helper.getSqlLocalNameMap().put(helper.getSqlMap().get(fieldName).value(),fieldName);
		}
		Method[] methods=clasz.getMethods();
		for(Method method:methods){
			helper.getMethodMap().put(method.getName(), method);
		}
		return helper;
	}

	/**
	 * 获取类sql变量名
	 * @param hasKey true 包含主键，false 不包含主键
	 * @param clasz
	 * @return
	 */
	public static List<String> sqlNames(boolean hasKey,Class<?> clasz){
		StackHelper helper=MybatisBeanContext.getClassContextMap(clasz);
		List<String> sqlFields=new ArrayList<String>();
		for(String fieldName:helper.getSqlMap().keySet()){
			if(!hasKey){//不包含id
				if(helper.getSqlMap().get(fieldName).key())continue;
			}
			String sqlName=helper.getSqlMap().get(fieldName).value();
			
			if(sqlName.equals("[unsignedField]")){
				sqlFields.add(fieldName);
			}else{
				sqlFields.add(sqlName);
			}
			
		}
		return sqlFields;
	}
	/**
	 * 获取对象实例sql变量名
	 * @param hasKey true 包含主键，false 不包含主键
	 * @param isNull true 获取空值，false 不获取空值
	 * @param bean
	 * @return
	 */
	public static List<String> sqlNames(boolean hasKey,boolean isNull,Object bean){
		StackHelper helper=MybatisBeanContext.getClassContextMap(bean.getClass());
		List<String> sqlFields=new ArrayList<String>();
		for(String fieldName:helper.getSqlMap().keySet()){
			if(!hasKey){//不包含id
				if(helper.getSqlMap().get(fieldName).key())continue;
			}
			if(!isNull){//不包含空置字段
				Object value = getValue(fieldName,bean);
				if(value==null)	continue; 
			}
			String sqlName=helper.getSqlMap().get(fieldName).value();
			
			if(sqlName.equals("[unsignedField]")){
				sqlFields.add(fieldName);
			}else{
				sqlFields.add(sqlName);
			}
		}
		return sqlFields;
	}
	
	/**
	 * 获取对象属性对应的值
	 * @param fieldName
	 * @param bean
	 * @return
	 */
	private static Object getValue(String fieldName,Object bean) {
		StackHelper helper=MybatisBeanContext.getClassContextMap(bean.getClass());
		Object value;
		Field field=helper.getFieldMap().get(fieldName);
		if(field.getType().isPrimitive()&&field.getType().getName().equals("boolean")){
			value=ReflectionUtils.invokeGetterMethod(bean,fieldName);
		}else{
			value=ReflectionUtils.invokeGetterMethod(bean,fieldName);
		}
		return value;
	}
}
