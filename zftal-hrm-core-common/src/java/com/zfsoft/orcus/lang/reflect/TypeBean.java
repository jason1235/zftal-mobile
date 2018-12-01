package com.zfsoft.orcus.lang.reflect;

/**
 * 类型描述JavaBean，以JavaBean的方式维护类型描述信息，包括读取和设置
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class TypeBean implements Type {

	private static final long serialVersionUID = 6905403060640377907L;

	private int dimension;					//数组的维数
	
	private String dynamicBeanClassName;	//动态Bean的全限定类名
	
	private Type element;					//数组元素的类型描述
	
	private String name;					//类型的显示名，例 int, int[], Object, Object[]
	
	private String qulifiedName;			//类型的全限定名，例 int, int[], java.lang.Object, java.lang.Object[]
	
	private String qulifiedPackageName;		//类型所在包的全限定名，如果类型为数组，则该值为null。
	
	private boolean dynamic;				//是否是动态类型
	
	private boolean primitive;				//是否是原始类型

	/*
	 * (non-Javadoc)
	 * @see com.zfsoft.hrm.lang.reflect.Type#getDimension()
	 */
	public int getDimension() {
		return dimension;
	}

	/**
	 * 设置数组的维数
	 * @param dimension 数组的维数
	 */
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	/*
	 * (non-Javadoc)
	 * @see com.zfsoft.hrm.lang.reflect.Type#getDynamicBeanClassName()
	 */
	public String getDynamicBeanClassName() {
		return dynamicBeanClassName;
	}

	/**
	 * 设置动态Bean的全限定类名
	 * @param dynamicBeanClassName 动态Bean的全限定类名
	 */
	public void setDynamicBeanClassName(String dynamicBeanClassName) {
		this.dynamicBeanClassName = dynamicBeanClassName;
	}

	/*
	 * (non-Javadoc)
	 * @see com.zfsoft.hrm.lang.reflect.Type#getElement()
	 */
	public Type getElement() {
		return element;
	}

	/**
	 * 设置数组元素的类型描述
	 * @param element 数组元素的类型描述
	 */
	public void setElement(Type element) {
		this.element = element;
	}

	/*
	 * (non-Javadoc)
	 * @see com.zfsoft.hrm.lang.reflect.Type#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置设置类型的显示名，例 int, int[], Object, Object[]
	 * @param name 设置类型的显示名，例 int, int[], Object, Object[]
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.zfsoft.hrm.lang.reflect.Type#getQulifiedName()
	 */
	public String getQulifiedName() {
		return qulifiedName;
	}

	/**
	 * 设置类型的全限定名，例 int, int[], java.lang.Object, java.lang.Object[]
	 * @param qulifiedName 类型的全限定名，例 int, int[], java.lang.Object, java.lang.Object[]
	 */
	public void setQulifiedName(String qulifiedName) {
		this.qulifiedName = qulifiedName;
	}

	/*
	 * (non-Javadoc)
	 * @see com.zfsoft.hrm.lang.reflect.Type#getQulifiedPackageName()
	 */
	public String getQulifiedPackageName() {
		return qulifiedPackageName;
	}

	/**
	 * 设置类型所在包的全限定名，如果类型为数组，则该值为null。
	 * @param qulifiedPackageName 类型所在包的全限定名，如果类型为数组，则该值为null。
	 */
	public void setQulifiedPackageName(String qulifiedPackageName) {
		this.qulifiedPackageName = qulifiedPackageName;
	}

	/*
	 * (non-Javadoc)
	 * @see com.zfsoft.hrm.lang.reflect.Type#isDynamic()
	 */
	public boolean isDynamic() {
		return dynamic;
	}

	/**
	 * 设置是否是动态类型
	 * @param dynamic 是否是动态类型
	 */
	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	/*
	 * (non-Javadoc)
	 * @see com.zfsoft.hrm.lang.reflect.Type#isPrimitive()
	 */
	public boolean isPrimitive() {
		return primitive;
	}

	/**
	 * 设置类型是否是原始类型
	 * @param primitive 类型是否是原始类型
	 */
	public void setPrimitive(boolean primitive) {
		this.primitive = primitive;
	}
}
