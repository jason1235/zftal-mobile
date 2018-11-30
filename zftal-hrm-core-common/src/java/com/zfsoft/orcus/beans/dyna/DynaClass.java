package com.zfsoft.orcus.beans.dyna;

import com.zfsoft.orcus.lang.reflect.BeanClass;

/**
 * 动态JavaBean的类型
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public interface DynaClass extends BeanClass {

	/**
	 * 生成返回一个新的动态JavaBean实例
	 * @return
	 */
	public DynaBean createDynaBean();
}
