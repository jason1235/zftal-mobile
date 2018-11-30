package com.zfsoft.orcus.lang.reflect;

import java.io.Serializable;

/**
 * 类型描述, 包含了类型的许多重要信息，如是否动态、是否为原始类型、是否为数组等等
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public interface Type extends Serializable {

	/**
	 * 返回类型是否是原始类型
	 * @return
	 */
	public boolean isPrimitive();
	
	/**
	 * 返回是否是动态类型
	 * <p>
	 * 如果该类型为动态类型，则方法返回true;<br>
     * 如果该类型为数组且数组元素为动态类型则该方法返回true；<br>
     * 否则返回false
     * </p>
	 * @return
	 */
	public boolean isDynamic();
	
	/**
	 * 返回动态Bean的全限定类名, (如果类型不动态类型则返回null)
	 * @return
	 */
	public String getDynamicBeanClassName();
	
	/**
	 * 返回数组元素的类型描述（如果类型不为数组则为null）
	 * @return
	 */
	public Type getElement();
	
	/**
	 * 返回数组的维数（如果类型不为数组则为0）
	 */
	public int getDimension();
	
	/**
	 * 返回该类型的显示名，例 int, int[], Object, Object[]
	 */
	public String getName();
	
	/**
	 * 返回该类型的全限定名，例 int, int[], java.lang.Object, java.lang.Object[] 
	 */
	public String getQulifiedName();
	
	/**
	 * 返回该类型所在包的全限定名，如果类型为数组，则该值为null。
	 * 例 java.lang, java.io 
	 */
	public String getQulifiedPackageName();
}
