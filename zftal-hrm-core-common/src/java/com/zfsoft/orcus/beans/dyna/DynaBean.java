package com.zfsoft.orcus.beans.dyna;

import java.io.Serializable;

import com.zfsoft.orcus.lang.reflect.SelfDescribed;

/**
 * 动态JavaBean，该JavaBean的属性在可在运行时动态调整。
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public interface DynaBean extends SelfDescribed, Serializable {

	/**
     * 返回是否含有指定的属性
     * @param name 属性名
     * @return true表示含有指定的属性，false表示不含有指定的属性。
     */
	public boolean has( String name );
    
    /**
     * 返回指定属性的值
     * @param name 属性名
     * @return 属性值
     * @throws DynaPropertyNotFoundException (runtime)如果name指定的属性不存在
     */
	public Object get( String name );
    
    /**
     * 设置指定属性的值
     * @param name 属性名
     * @param value 属性值
     * @throws DynaPropertyNotFoundException (runtime)如果name指定的属性不存在
     * @throws NullPointerException (runtime)如果value==null而属性的类型为原始类型
     * @throws TypeMismatchException (runtime)如果value的类型与属性的类型不匹配
     */
	public void set( String name, Object value );
    
    /**
     * 返回动态JavaBean的类型
     */
	public DynaClass dynaClass();
}
