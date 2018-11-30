package com.zfsoft.hrm.baseinfo.dyna.html;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;

/**
 * 动态类工厂
 * 
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-19
 * @version V1.0.0
 */
public class ParseFactory {
	
	/**
	 * 获取可编辑字段的解析，返回的用于是页面展示的网页脚本
	 * @param property
	 * @param value
	 * @return
	 */
	public static String EditParse(InfoProperty property, Object value) {
		return EditParse.parse(property, value);
	}
	/**
	 * 获取可显示字段的解析，返回的是对value解析后的值
	 * @param property
	 * @param value
	 * @return
	 */
	public static String ViewParse(InfoProperty property, Object value) {
		return ViewParse.parse(property, value);
	}
	
	/**
	 * 获取字段值的解析，返回的是对value类型解析转换后的值，对DATE类型特殊处理
	 * @param property
	 * @param value
	 * @return
	 */
	public static Object ValueParse(InfoProperty property, Object value) {
		return ValueParse.parse(property, value);
	}
}
