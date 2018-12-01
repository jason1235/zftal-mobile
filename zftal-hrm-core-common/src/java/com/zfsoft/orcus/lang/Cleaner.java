package com.zfsoft.orcus.lang;

import java.util.Collection;
import java.util.Map;

/**
 * 清空容器数据的工具类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-11
 * @version V1.0.0
 */
@SuppressWarnings("unchecked")
public class Cleaner {

	/**
	 * 清空Collection
	 * @param collection
	 */
	public static void clean(Collection collection) {
		if( collection == null ) {
			return;
		}
		
		collection.clear();
	}
	
	/**
	 * 清空Map
	 * @param map
	 */
	public static void clean(Map map) {
		if (map == null) {
			return;
		}
		
		map.clear();
	}
}
