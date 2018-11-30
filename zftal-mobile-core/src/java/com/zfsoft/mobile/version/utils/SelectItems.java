package com.zfsoft.mobile.version.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;



/**
 * <p>Description:反向获取对象属性名称与属性值hashmap列表类 </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2012-4-19 下午04:59:46
 * @author xuxinghua
 * @version 1.0
 */
public class SelectItems {
	/**
	 * <p>Description: </p>
	 * @param coll -- 集合，对象属性集合；如List<Obj>
	 * @param obj  -- 对象 ;如：User.class
	 * @param PropertyNames  --需获取对象属性及属性值的的hashmap的各 对象属性数组
	 * @return   ArrayList<HashMap<id, name>> 对象属性及属性值的的hashmap列表
	 * @throws Exception
	 *
	 * @since 2012-4-19 下午05:10:00
	 * @author xuxinghua
	 */
	public static List<HashMap<String,String>> getReflectObjPropertyValue(List<Object> coll, Class cls, String[] propertyNames){
		ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		try {
			//得到类对象
		   Class beanclass = cls;
	       /*
	        * 得到类中的所有属性集合
	        */
	       Iterator<Object> it = coll.iterator();
	       while(it.hasNext()){//列表对象
	    	   Object obj = it.next();
	    	   beanclass = cls;
	    	   HashMap<String, String> m = new HashMap<String, String>(); //一个对象obj，对应一个hashmap
	    	   for (; !beanclass.equals(Object.class); beanclass = beanclass.getSuperclass()) {//将类中，包含父类的字段取出
	    		   Field[] field = beanclass.getDeclaredFields();
	   				for (Field f : field) {//所有字段
				    	   for(int j=0;j<propertyNames.length;j++){//所有所选属性
						           f.setAccessible(true); //设置些属性是可以访问的
						           if(f.getName().equals(propertyNames[j])){
						        	  // String type = f.getType().toString();//得到此属性的类型
						        	   Object val =  f.get(obj);//得到此属性的值
						        	   String value = "";
						        	   //System.out.println("type"+f.getType());
									   if(val instanceof java.util.Date){
											   Date valt =  (Date)val;//得到此属性的值
											   value = DateUtil.toString(valt);
									   }else{
											 if(val!=null){
												   value = val.toString();
											 }
										}
										
						        	   m.put(f.getName(), value);
						        	   break;
						           }
				    	   }
				    //System.out.println(f.getName());
	   				} //for (Field f : field) {//所有字段 
	    	   }
	    	   resultList.add(m);
	        }
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return resultList;
	}
	/**
	 * <p>Description: 获取下拉框数据的key，value的hashmap列表 </p>
	 * @param coll -- 集合，对象属性集合；如List<Obj>
	 * @param obj  -- 对象 ;如：User.class
	 * @param PropertyNames  --需获取对象属性及属性值的的hashmap的各 对象属性数组
	 * @return   ArrayList<HashMap<id, name>> 对象属性及属性值的的hashmap列表
	 * @throws Exception
	 *
	 * @since 2012-4-19 下午05:10:00
	 * @author xuxinghua
	 */
	@SuppressWarnings("unchecked")
	public static List<HashMap<String, String>> autoSelect(Collection coll, Object obj, String[] PropertyNames)
		throws Exception{
		ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		Iterator it = coll.iterator();
		Method method = null;
		PropertyDescriptor descriptor = null;
		while(it.hasNext()){
			obj = it.next();
			HashMap<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < PropertyNames.length; i++) {
				descriptor = new PropertyDescriptor(PropertyNames[i], obj
						.getClass());
				method = descriptor.getReadMethod();
				Object objs = method.invoke(obj, new Object[0]);
					if ("id".equals(PropertyNames[i])) {
						map.put("column_value", objs.toString());
					} else if ("billtype".equals(PropertyNames[i])) {
						map.put("column_value", objs.toString());
					} else if ("bm".equals(PropertyNames[i])) {
						map.put("column_value", objs.toString());
					}else if ("jyflbm".equals(PropertyNames[i])) {
						map.put("column_value", objs.toString());
					}else if ("code".equals(PropertyNames[i])) {
						map.put("column_value", objs.toString());
					}
					else {
						map.put("column_text", objs.toString());
					}
			}
			resultList.add(map);
		}
		return resultList;
	}
}
