package com.zfsoft.orcus.lang.eval.vars;

import java.io.Serializable;

/**
 * JavaBean属性名和变量名的映射
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-27
 * @version V1.0.0
 */
public interface NameMapping extends Serializable {
	
	/**
	 * 返回JavaBean属性名
	 */
	public String propertyName();

	/**
	 * 返回变量名
	 */
	public String variableName();

}
