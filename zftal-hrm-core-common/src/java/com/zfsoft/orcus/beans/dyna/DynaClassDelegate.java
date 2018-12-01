package com.zfsoft.orcus.beans.dyna;

import java.io.Serializable;

/**
 * {@link DynaClass}的代理
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public interface DynaClassDelegate extends Serializable {
	
	/**
	 * 返回该对象所代理的动态JavaBean的类型
	 * @return
	 */
	public DynaClass getDynaClass();

}
